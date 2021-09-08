package me.santio.factory.mods;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ModDescription {
    
    @Getter private final String mainClass;
    @Getter private final String name;
    @Getter private final String id;
    @Getter private String description = "No description provided.";
    @Getter private final String version;
    @Getter private final List<String> authors;
    
    public ModDescription(InputStream stream) throws IOException {
        Reader in = new StringReader(IOUtils.toString(stream, StandardCharsets.UTF_8));
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(in);
        this.mainClass = configuration.getString("main");
        this.name = configuration.getString("name");
        this.description = configuration.getString("description");
        this.version = configuration.getString("version");
        this.authors = configuration.getStringList("authors");
        
        if (this.name == null) this.id = "null";
        else this.id = this.name.toLowerCase().replaceAll(" ", "-");
    }
    
}
