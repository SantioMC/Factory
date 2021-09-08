package me.santio.factory.mods;

import me.santio.factory.exceptions.InvalidModDescriptionException;
import me.santio.factory.exceptions.InvalidModException;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader {
    
    public FactoryMod load(File file) {
    
        ModDescription description; // Information about the mod
        
        try {
            description = getModDescription(file);
        } catch (InvalidModDescriptionException e) {
            e.printStackTrace();
            return null;
        }
    
        final ModClassLoader loader;
        try {
            loader = new ModClassLoader(this, getClass().getClassLoader(), description, file);
        } catch (MalformedURLException | InvalidModException ex) {
            ex.printStackTrace();
            return null;
        }
    
        return loader.getMod();
    }
    
    private static ModDescription getModDescription(File file) throws InvalidModDescriptionException {
        JarFile jar = null;
        InputStream stream = null;
        
        try {
            jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("mod.yml");
            
            if (entry == null) throw new FileNotFoundException(file.getName() + " does not contain mod.yml");
            
            stream = jar.getInputStream(entry);
            
            return new ModDescription(stream);
        } catch (IOException | YAMLException ex) {
            throw new InvalidModDescriptionException(ex);
        } finally {
            // Close jar
            if (jar != null) {
                try { jar.close(); }
                catch (IOException ignored) { }
            }
            
            // Close stream
            if (stream != null) {
                try { stream.close(); }
                catch (IOException ignored) {}
            }
        }
    }
    
}
