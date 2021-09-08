package me.santio.factory.mods;

public class FactoryLogger {
    
    private final String id;
    
    public FactoryLogger(FactoryMod plugin) {
        this.id = plugin.getDescription().getId();
    }
    
    public void log(String... messages) {
        System.out.println("[Factory@"+id+"] " + String.join("   ", messages));
    }
    
}
