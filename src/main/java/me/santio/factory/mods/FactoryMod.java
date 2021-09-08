package me.santio.factory.mods;

import lombok.Getter;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class FactoryMod implements Listener {
    
    @Getter private FactoryLogger logger = null;
    @Getter private ModDescription description = null;
    @Getter private File dataFolder = null;
    @Getter private ModClassLoader classLoader = null;
    
    /**
     * This method is ran after the vanilla world is generated
     */
    public void onEnable() {}
    
    /**
     * This method is ran before the vanilla world is generated
     * @apiNote This method won't be ran if Factory reloads your mod
     */
    public void onPreEnable() {}
    
    /**
     * This method is ran on server shutdown
     */
    public void onDisable() {}
    
    final void initiate(ModDescription description, File dataFolder, ModClassLoader classLoader) {
        this.description = description;
        this.logger = new FactoryLogger(this);
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
    }
    
    public InputStream getResource(String path) {
        try {
            URL url = getClassLoader().getResource(path);
        
            if (url == null) return null;
        
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }
    
}
