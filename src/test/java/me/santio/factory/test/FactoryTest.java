package me.santio.factory.test;

import me.santio.factory.Factory;
import me.santio.factory.events.FactoryBlockInteractEvent;
import me.santio.factory.events.FactoryBlockPlaceEvent;
import me.santio.factory.events.FactoryItemUseEvent;
import me.santio.factory.models.FactoryBlock;
import me.santio.factory.models.FactoryDimension;
import me.santio.factory.models.FactoryItem;
import me.santio.factory.mods.FactoryMod;
import me.santio.factory.oredict.Dictionary;
import me.santio.factory.oredict.OreDictionary;
import org.bukkit.event.EventHandler;

import java.util.List;

public class FactoryTest extends FactoryMod {
    
    @Override
    public void onPreEnable() {
        // Register resources
        Factory.createTexture(this, "debug", "textures/debug.png");
        Factory.createModel(this, "debug", "models/debug.json");
    
        Factory.createTexture(this, "copper", "textures/copper.png");
        Factory.createModel(this, "copper_golem", "models/copper_golem.json");
        
        // Register dimension
        FactoryDimension dimension = Factory.createDimension(this, "test");
        if (dimension == null) return;
        dimension
            .setBedrock(true)
            .setCaves(true)
            .setDecorations(true)
            .setSurface(true);
    }
    
    @Override
    public void onEnable() {
        // Create block
        Factory.createBlock(this, "Debug Block")
                .setModel("debug")
                .setBaseBlock(FactoryBlock.BaseBlock.GLASS);
     
        // Create Item
        Factory.createItem(this, "Copper Golem")
                .registerOreDict("copper golem")
                .setModel("copper_golem");
        
        Dictionary dict = new Dictionary("copper golem");
        List<FactoryItem> copperGolems = OreDictionary.findItem(dict);
        
        boolean matches = OreDictionary.matches(dict, Factory.getItem("copper_golem"));
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
    
    @EventHandler
    public void onItemUse(FactoryItemUseEvent event) {
        if (!event.getItem().getName().equals("Copper Golem")) return;
        Dictionary dict = new Dictionary("copper golem");
        event.getPlayer().sendMessage("§3Dict Results:§7 " + OreDictionary.findItem(dict).size());
    }
    
}


