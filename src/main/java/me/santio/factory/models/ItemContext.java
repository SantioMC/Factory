package me.santio.factory.models;

import lombok.Getter;
import lombok.Setter;
import me.santio.factory.Factory;
import me.santio.factory.FactoryLib;
import me.santio.factory.mods.FactoryMod;
import me.santio.factory.utils.NBTUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemContext {
    
    @Setter @Getter private String name;
    @Setter @Getter private int modelData = 0;
    @Getter private final FactoryMod mod;
    @Getter private String ID;
    @Getter private String model;
    
    public ItemContext(FactoryMod mod, String name) {
        this.mod = mod;
        this.name = name;
        this.ID = name.toLowerCase().replaceAll(" ", "_");
        
        int attempt = 0;
        while (Factory.blockExists(this.getID())) {
            this.ID = attempt == 0 ? this.getID() + "1" : this.getID().substring(0, this.getID().length()-((int) Math.ceil(attempt / 10D)))+attempt;
            attempt++;
        }
    }
    
    public ItemStack generateItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        
        meta.setCustomModelData(getModelData());
        meta.setDisplayName(ChatColor.WHITE + getName());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        
        NBTUtils.setCustomItem(meta, getID());
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemContext setModel(String model) {
        String id = model.contains("/") ? model : getMod().getDescription().getId()+"/"+model;
        FactoryModel factoryModel = FactoryLib.getModels().get(id);
        
        if (factoryModel == null) {
            new IllegalArgumentException("The model \""+model+"\" does not exist!").printStackTrace();
            return this;
        }
        
        this.model = model;
        this.setModelData(factoryModel.getId());
        return this;
    }
    
}
