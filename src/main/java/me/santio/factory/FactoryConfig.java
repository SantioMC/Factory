package me.santio.factory;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FactoryConfig {
    
    public FactoryConfig(JavaPlugin plugin) {
        plugin.getDataFolder().mkdirs();
        if (!plugin.getDataFolder().toPath().resolve("config.yml").toFile().exists()) {
            plugin.saveResource("config.yml", false);
        }
    }
    
}
