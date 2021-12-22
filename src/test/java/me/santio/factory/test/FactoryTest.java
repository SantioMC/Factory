package me.santio.factory.test;

import me.santio.factory.Factory;
import me.santio.factory.events.FactoryBlockInteractEvent;
import me.santio.factory.events.FactoryBlockPlaceEvent;
import me.santio.factory.models.FactoryBlock;
import me.santio.factory.mods.FactoryMod;
import org.bukkit.event.EventHandler;

public class FactoryTest extends FactoryMod {
    
    @Override
    public void onPreEnable() {
        // Register resources
        Factory.createTexture(this, "debug", "textures/debug.png");
        Factory.createModel(this, "debug", "models/debug.json");
    
        Factory.createTexture(this, "copper", "textures/copper.png");
        Factory.createModel(this, "copper_golem", "models/copper_golem.json");
    }
    
    @Override
    public void onEnable() {
        // Create block
        Factory.createBlock(this, "Debug Block")
                .setModel("debug")
                .setBaseBlock(FactoryBlock.BaseBlock.GLASS);
     
        // Create Item
        Factory.createItem(this, "Copper Golem")
                .setModel("copper_golem");
    }
    
    @EventHandler
    public void onBlockPlace(FactoryBlockPlaceEvent event) {
        if (!event.getTile().getBlock().getName().equals("Debug Block")) return;
        getLogger().info("Block: "+event.getLocation().getBlock().getType().name());
        
        event.getTile().set("energy", 1000); // Make block start with 1000 energy
    }
    
    @EventHandler
    public void onBlockInteract(FactoryBlockInteractEvent event) {
        if (!event.getTile().getBlock().getName().equals("Debug Block")) return;
        event.getPlayer().sendMessage("§7Power: §3" + event.getTile().readInt("energy"));
    }
    
}


