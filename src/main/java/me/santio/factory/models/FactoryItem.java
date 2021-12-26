package me.santio.factory.models;

import me.santio.factory.FactoryLib;
import me.santio.factory.mods.FactoryMod;
import me.santio.factory.oredict.OreDictionary;
import me.santio.factory.utils.NBTUtils;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class FactoryItem extends ItemContext {
    
    public FactoryItem(FactoryMod mod, String name) {
        super(mod, name);
        
        // Save to Factory
        FactoryLib.getItems().put(this.getID(), this);
    }
    
    public FactoryItem registerOreDict(String id) {
        OreDictionary.registerItem(this, id);
        return this;
    }
    
    public static FactoryItem fromItemStack(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        
        String id = NBTUtils.getCustomItem(item.getItemMeta());
        if (id == null) return null;
        
        return FactoryLib.getItems().get(id);
    }
    
}
