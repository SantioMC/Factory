package me.santio.factory.models;

import me.santio.factory.FactoryLib;
import me.santio.factory.mods.FactoryMod;

public class FactoryItem extends ItemContext {
    
    public FactoryItem(FactoryMod mod, String name) {
        super(mod, name);
        
        // Save to Factory
        FactoryLib.getItems().put(this.getID(), this);
    }
    
}
