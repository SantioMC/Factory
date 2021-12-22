package me.santio.factory.mods;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class FactoryLogger extends Logger {
    
    private final String id;
    
    public FactoryLogger(FactoryMod plugin) {
        super(plugin.getClass().getCanonicalName(), null);
        this.id = plugin.getDescription().getId();
        setLevel(Level.ALL);
    }
    
    @Override
    public void log(LogRecord record) {
        record.setMessage("[Factory@"+id+"] "+record.getMessage());
        super.log(record);
    }
}
