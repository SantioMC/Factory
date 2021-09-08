package me.santio.factory.test;

import me.santio.factory.Factory;
import me.santio.factory.models.BlockTile;
import me.santio.factory.models.FactoryBlock;
import me.santio.factory.mods.FactoryMod;

public class FactoryTest extends FactoryMod {
    
    @Override
    public void onPreEnable() {
        // Register resources
        Factory.createTexture(this, "debug", "textures/debug.png");
        Factory.createModel(this, "debug", "models/debug.json");
    }
    
    @Override
    public void onEnable() {
        // Create block
        FactoryBlock block = (FactoryBlock) Factory.createBlock(this, "Debug Block").setModel("debug");
        
        BlockTile tile = new BlockTile(null);
        tile.set("energy", 10000);
        
        int energy = tile.readInt("energy");
    }
    
}


