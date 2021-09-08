package me.santio.factory.models;

import me.santio.factory.FactoryLib;
import me.santio.factory.mods.FactoryMod;

public class FactoryBlock extends ItemContext {
    
    public FactoryBlock(FactoryMod mod, String name) {
        super(mod, name);
        
        // Save to Factory
        FactoryLib.getBlocks().put(this.getID(), this);
    }
    
}
