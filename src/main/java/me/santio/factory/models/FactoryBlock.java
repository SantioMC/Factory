package me.santio.factory.models;

import lombok.Getter;
import me.santio.factory.FactoryLib;
import me.santio.factory.mods.FactoryMod;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FactoryBlock extends ItemContext {
    
    @Getter private BaseBlock baseBlock = BaseBlock.DEFAULT;
    @Getter private ItemStack dropItem;
    
    public FactoryBlock(FactoryMod mod, String name) {
        super(mod, name);
        dropItem = generateItem();
        
        // Save to Factory
        FactoryLib.getBlocks().put(this.getID(), this);
    }
    
    @Override
    public FactoryBlock setModel(String model) {
        super.setModel(model);
        dropItem = generateItem();
        return this;
    }
    
    public FactoryBlock setBaseBlock(BaseBlock baseBlock) {
        this.baseBlock = baseBlock;
        return this;
    }
    
    public FactoryBlock setDropItem(ItemStack dropItem) {
        this.dropItem = dropItem;
        return this;
    }
    
    public enum BaseBlock {
        DEFAULT(Material.LIGHT_GRAY_SHULKER_BOX),
        LIGHT(Material.SEA_LANTERN),
        GLASS(Material.LIGHT_GRAY_STAINED_GLASS);
    
        @Getter private final Material material;
        BaseBlock(Material material) {
            this.material = material;
        }
    }
    
}
