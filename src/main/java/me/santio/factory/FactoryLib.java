package me.santio.factory;

import lombok.Getter;
import me.santio.factory.commands.FactoryCommand;
import me.santio.factory.commands.ModCommand;
import me.santio.factory.listeners.CustomBlockListener;
import me.santio.factory.listeners.ResourcepackListener;
import me.santio.factory.models.*;
import me.santio.factory.mods.FactoryMod;
import me.santio.factory.mods.ModLoader;
import me.santio.factory.web.PackWebpage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored"})
public final class FactoryLib extends JavaPlugin {
    
    @Getter private static FactoryLib instance;
    @Getter private static File modsDirectory;
    @Getter private static boolean doneLoading = false;
    
    @Getter private static final HashMap<String, FactoryMod> mods = new HashMap<>();
    @Getter private static final HashMap<String, FactoryBlock> blocks = new HashMap<>();
    @Getter private static final HashMap<String, FactoryItem> items = new HashMap<>();
    @Getter private static final HashMap<String, FactoryModel> models = new HashMap<>();
    @Getter private static final HashMap<String, FactoryDimension> dimensions = new HashMap<>();
    @Getter private static final HashMap<Location, BlockTile> tiles = new HashMap<>();
    @Getter private static final ArrayList<String> textures = new ArrayList<>();
    
    private final FactoryConfig config = new FactoryConfig(this);
    private final String[] modJarFilter = new String[]{"jar"};
    private ModLoader modLoader;
    
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        instance = this;
        
        // Create plugin directory
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        modsDirectory = getDataFolder().toPath().resolve("mods/").toFile();
        if (!modsDirectory.exists()) modsDirectory.mkdirs();
        
        modLoader = new ModLoader();
        
        for (File file : FileUtils.listFiles(modsDirectory, modJarFilter, true )) {
            FactoryMod mod = modLoader.load(file);
            if (mod == null) getLogger().severe("Failed to load Factory Mod: " + file.getName());
            else {
                getServer().getPluginManager().registerEvents(mod, this);
                mod.onPreEnable();
                mods.put(mod.getDescription().getName(), mod);
                getLogger().info("Loaded Factory Mod: " + mod.getDescription().getName());
            }
        }
        
        createResourcePack();
        FactoryCommand command = new FactoryCommand();
        
        getServer().getPluginManager().registerEvents(new ResourcepackListener(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
        
        getServer().getPluginCommand("factory").setExecutor(command);
        getServer().getPluginCommand("factory").setTabCompleter(command);
    
        getServer().getPluginCommand("mods").setExecutor(new ModCommand());
        
        // Enable Mods
        new BukkitRunnable() {
            @Override public void run() {
                for (FactoryMod mod : mods.values()) mod.onEnable();
                doneLoading = true;
                getLogger().info("Successfully finished loading Factory");
            }
        }.runTaskLater(this, 1);
    }
    
    @Override
    public void onDisable() {
        // Disable Mods
        for (FactoryMod mod : mods.values()) mod.onDisable();
        PackWebpage.close();
    }
    
    private void createResourcePack() {
        File minecraft = getDataFolder().toPath().resolve("pack/assets/minecraft").toFile();
        if (!minecraft.exists()) minecraft.mkdirs();
        
        File textures = minecraft.toPath().resolve("textures/").toFile();
        File models = minecraft.toPath().resolve("models/item").toFile();
        
        if (!textures.exists()) textures.mkdir();
        if (!models.exists()) models.mkdirs();
    
        // Import resources from mods
        try {
            File directory = getDataFolder().toPath().resolve("resources/").toFile();
            File[] directories = directory.listFiles(File::isDirectory);
            if (directories == null) return;
            for (File mod : directories) {
                if (!mod.isDirectory()) continue;
                String modID = mod.getName();
                Collection<File> modModels = FileUtils.listFiles(mod.toPath().resolve("models/").toFile(), new String[]{"json"}, true);
                for (File move : modModels) FileUtils.moveFile(move, models.toPath().resolve("custom/"+move.getName()).toFile());
                Collection<File> modTextures = FileUtils.listFiles(mod.toPath().resolve("textures/").toFile(), new String[]{"png"}, true);
                for (File move : modTextures) FileUtils.moveFile(move, textures.toPath().resolve(modID+"/"+move.getName()).toFile());
            }
            
            // Prepare model data
            StringBuilder modelData = new StringBuilder();
            for (FactoryModel model : getModels().values()
                    .stream()
                    .sorted(Comparator.comparingInt(FactoryModel::getId))
                    .collect(Collectors.toList())) {
                
                if (model.getId() != 1) modelData.append(",\n\t\t");
                modelData.append("{\"predicate\": {\"custom_model_data\":")
                        .append(model.getId())
                        .append("}, \"model\": \"item/custom/")
                        .append(model.getModel())
                        .append("\"}");
                
            }
    
            // Create custom model data file
            InputStream in = getResource("pack/modeldata.json");
            if (in == null) return;
            String template = IOUtils.toString(in, StandardCharsets.UTF_8);
            template = template.replace("{{MODEL}}", modelData);
            
            File item = models.toPath().resolve("barrier.json").toFile();
            if (!item.exists()) item.createNewFile();
            
            FileUtils.writeStringToFile(item, template, StandardCharsets.UTF_8);
            
            // Delete resources directory
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            InputStream in = getResource("pack/pack.mcmeta");
            if (in == null) return;
            
            FileUtils.copyInputStreamToFile(in, minecraft.getParentFile().getParentFile().toPath().resolve("pack.mcmeta").toFile());
            
            // Make the pack into a zip
            File directory = getDataFolder().toPath().resolve("generated/").toFile();
            if (!directory.exists()) directory.mkdir();
            ZipUtil.pack(getDataFolder().toPath().resolve("pack/").toFile(), directory.toPath().resolve("pack.zip").toFile());
            
            // Delete pack directory
            FileUtils.deleteDirectory(getDataFolder().toPath().resolve("pack/").toFile());
            
            // Start webserver
            PackWebpage.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
