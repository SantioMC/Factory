package me.santio.factory.mods;

import lombok.Getter;
import me.santio.factory.FactoryLib;
import me.santio.factory.exceptions.InvalidModException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@SuppressWarnings("FieldCanBeLocal")
public class ModClassLoader extends URLClassLoader {
    private final ModLoader loader;
    private final File file;
    private final ModDescription description;
    private final File dataFolder;
    
    @Getter private final FactoryMod mod;
    
    @SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
    public ModClassLoader(ModLoader loader, ClassLoader parent, ModDescription description, File file) throws MalformedURLException, InvalidModException {
        super(new URL[] {file.toURI().toURL()}, parent);
        
        this.loader = loader;
        this.description = description;
        this.dataFolder = FactoryLib.getModsDirectory().getParentFile().toPath().resolve("/configs/"+description.getName().replaceAll(" ", "_")).toFile();
        this.file = file;
        
        this.dataFolder.mkdirs();
        
        try {
            Class<?> jarClass;
            try {
                jarClass = Class.forName(description.getMainClass(), true, this);
            } catch (ClassNotFoundException ex) {
                throw new InvalidModException("Invalid main path: " + description.getMainClass());
            }
            
            Class<? extends FactoryMod> pluginClass;
            try {
                pluginClass = jarClass.asSubclass(FactoryMod.class);
            } catch (ClassCastException ex) {
                throw new InvalidModException("The main class `" + description.getMainClass() + "' does not extend FactoryMod (Name: "+description.getName()+"). Please note that Factory does NOT support forge/fabric mods but instead provides its own modding api.");
            }
            
            mod = pluginClass.newInstance();
            mod.initiate(description, dataFolder, this);
        } catch (IllegalAccessException | InvalidModException | InstantiationException ex) {
            throw new InvalidModException("Invalid mod file provided: "+ex.getMessage());
        }
    }
    
}
