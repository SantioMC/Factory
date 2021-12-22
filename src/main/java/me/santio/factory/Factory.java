package me.santio.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.santio.factory.exceptions.FactoryLoadedException;
import me.santio.factory.models.FactoryBlock;
import me.santio.factory.models.FactoryItem;
import me.santio.factory.models.FactoryModel;
import me.santio.factory.mods.FactoryMod;
import me.santio.factory.web.PackWebpage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the main API for Factory
 */
@SuppressWarnings({"unused", "UnusedReturnValue", "ResultOfMethodCallIgnored"})
public class Factory {
    /**
     * Retrieves all existing block contexts.
     *
     * @return The list of custom blocks
     * @since v1.0
     */
    public static FactoryBlock[] getFactoryBlocks() {
        return FactoryLib.getBlocks().values().toArray(new FactoryBlock[0]);
    }
    
    /**
     * Checks if a block with the supplied ID exists.
     *
     * @param id The ID of the block context to check
     * @return If a block with the supplied ID exists
     * @since v1.0
     */
    public static boolean blockExists(String id) {
        return FactoryLib.getBlocks().containsKey(id);
    }
    
    /**
     * Begins creation of a FactoryBlock
     *
     * @param mod The mod registering the block.
     * @param name The name of the block
     * @since v1.0
     */
    public static FactoryBlock createBlock(FactoryMod mod, String name) {
        return new FactoryBlock(mod, name);
    }
    
    /**
     * Begins creation of a FactoryItem
     *
     * @param mod The mod registering the item.
     * @param name The name of the item
     * @since v1.0
     */
    public static FactoryItem createItem(FactoryMod mod, String name) {
        return new FactoryItem(mod, name);
    }
    
    /**
     * Gets a custom block from Factory's API and returns the FactoryBlock.
     *
     * @param id The ID of the FactoryBlock
     * @return The FactoryBlock or null if that ID does not exist
     * @since v1.0
     */
    public static FactoryBlock getBlock(String id) {
        return FactoryLib.getBlocks().get(id);
    }
    
    /**
     * Sends the generated resource pack to the player.
     * @apiNote This method is automatically ran on join.
     *
     * @param player The player to send the resource pack
     * @since v1.0
     */
    public static void sendResource(Player player) {
        player.setResourcePack("http://"+ PackWebpage.getIp()+":"+PackWebpage.getPort());
    }
    
    /**
     * Checks if Factory is done loading.
     * This can be used to check if you can still import textures or not.
     *
     * @return If Factory is done loading
     * @since v1.0
     */
    public static boolean isDoneLoading() {
        return FactoryLib.isDoneLoading();
    }
    
    /**
     * Creates a texture to be used in the future, either a block or item
     * @apiNote This method is only available in the onPreEnable event.
     *
     * @param mod The mod creating the texture
     * @param name The name of the texture (used in models, ModID:TextureID)
     * @param path The path in your resources directory (ex: /textures/image.png)
     * @since v1.0
     */
    public static void createTexture(FactoryMod mod, String name, String path) {
        InputStream texture = mod.getResource(path);
        if (texture == null) {
            new NullPointerException("Texture "+name+" does not exist (Path "+path+")").printStackTrace();
            return;
        }
        if (isDoneLoading()) {
            new FactoryLoadedException("Factory is already loaded, you can no longer import textures, try putting it in FactoryMod#onPreEnable").printStackTrace();
            return;
        }
        
        try {
            String[] pathData = path.split("/");
            String id = pathData[pathData.length - 1];
            
            String dir = mod.getDescription().getId() + "/textures/";
            File directory = FactoryLib.getInstance().getDataFolder().toPath().resolve("resources/"+dir).toFile();
            if (!directory.exists()) directory.mkdirs();
            
            FileUtils.copyInputStreamToFile(texture, directory.toPath().resolve(id).toFile());
            FactoryLib.getTextures().add(mod.getDescription().getId()+"/"+id.split("\\.")[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a model to be used in the future, this is used when creating
     * a new item or block.
     * @apiNote This method is only available in the onPreEnable event.
     *
     * @param mod The mod creating the texture
     * @param name The name of the texture (used when referencing)
     * @param path The path in your resources directory (ex: /models/file.json)
     * @since v1.0
     */
    public static void createModel(FactoryMod mod, String name, String path) {
        InputStream model = mod.getResource(path);
        if (model == null) {
            new NullPointerException("Model "+name+" does not exist (Path "+path+")").printStackTrace();
            return;
        }
        if (isDoneLoading()) {
            new FactoryLoadedException("Factory is already loaded, you can no longer import models, try putting it in FactoryMod#onPreEnable").printStackTrace();
            return;
        }
        
        try {
            String[] pathData = path.split("/");
            String id = pathData[pathData.length - 1];
            
            String dir = mod.getDescription().getId() + "/models/";
            File directory = FactoryLib.getInstance().getDataFolder().toPath().resolve("resources/"+dir).toFile();
            if (!directory.exists()) directory.mkdirs();
            
            String text = IOUtils.toString(model, StandardCharsets.UTF_8);
            Pattern p = Pattern.compile("\\{\\{(\\w+)}}");
            Matcher m = p.matcher(text);
            while (m.find()) {
                String texture = m.group(1);
                if (texture.contains("/")) continue;
                text = text.replace("{{"+texture+"}}", mod.getDescription().getId()+"/"+texture);
            }
    
            JsonObject jsonObject = new JsonParser().parse(text).getAsJsonObject();
            JsonArray size = new JsonArray();
            for (int i = 0; i<3; i++) size.add(1.61d);

            if (jsonObject.getAsJsonObject("display") == null) {
                jsonObject.add("display", new JsonObject());
            }
            JsonObject display = jsonObject.getAsJsonObject("display");

            if (display.getAsJsonObject("head") == null) {
                display.add("head", new JsonObject());
            }
            JsonObject part = display.getAsJsonObject("head");
            part.add("scale", size);
            
            jsonObject.addProperty("ambientocclusion", false);
            
            text = jsonObject.toString();
            
            File file = directory.toPath().resolve(id).toFile();
            file.createNewFile();
            FileUtils.writeStringToFile(file, text, StandardCharsets.UTF_8);
    
            new FactoryModel(mod, id.split("\\.")[0], FactoryLib.getModels().size()+1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
