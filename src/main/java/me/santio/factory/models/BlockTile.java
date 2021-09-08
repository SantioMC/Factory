package me.santio.factory.models;

import lombok.Getter;
import me.santio.factory.FactoryLib;
import me.santio.factory.utils.NBTUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class BlockTile {
    
    @Getter private final Location location;
    @Getter private final Location entityLocation;
    @Getter private final FactoryBlock block;
    @Getter private ArmorStand entity;
    
    public BlockTile(Location location, FactoryBlock block) {
        this.location = location;
        this.entityLocation = location.add(0, -1.19, 0);
        this.block = block;
        
        if (location.getWorld() == null) return;
        this.entity = location.getWorld().spawn(entityLocation, ArmorStand.class, (armorStand) -> {
            armorStand.setInvisible(true);
            armorStand.setGravity(false);
            armorStand.setSilent(true);
            
            if (armorStand.getEquipment() == null) armorStand.remove();
            else armorStand.getEquipment().setHelmet(block.generateItem());
        });
    
        NBTUtils.setBlockTile(entity, block);
        FactoryLib.getTiles().put(this.location, this);
    }
    
    public BlockTile(ArmorStand armorStand) {
        this.entityLocation = armorStand.getLocation();
        this.location = entityLocation.add(0, 1.19, 0);
        this.entity = armorStand;
        this.block = FactoryLib.getBlocks().get(NBTUtils.getBlockTile(armorStand));
        
        FactoryLib.getTiles().put(this.location, this);
    }
    
    //<editor-fold desc="Persistent Data" defaultstate="collapsed">
    /**
     * Saves persistent data in this block.
     * This will never be wiped.
     *
     * @param key The key of the value
     * @param value A string
     */
    public void set(String key, String value) {
        NBTUtils.write(this.entity, key, value);
    }
    
    /**
     * Saves persistent data in this block.
     * This will never be wiped.
     *
     * @param key The key of the value
     * @param value A integer
     */
    public void set(String key, Integer value) {
        NBTUtils.write(this.entity, key, value);
    }
    
    /**
     * Saves persistent data in this block.
     * This will never be wiped.
     *
     * @param key The key of the value
     * @param value A double
     */
    public void set(String key, Double value) {
        NBTUtils.write(this.entity, key, value);
    }
    
    /**
     * Saves persistent data in this block.
     * This will never be wiped.
     *
     * @param key The key of the value
     * @param value A float
     */
    public void set(String key, Float value) {
        NBTUtils.write(this.entity, key, value);
    }
    
    /**
     * Saves persistent data in this block.
     * This will never be wiped.
     *
     * @param key The key of the value
     * @param value A boolean
     */
    public void set(String key, Boolean value) {
        NBTUtils.write(this.entity, key, value);
    }
    
    /**
     * Saves persistent data in this block.
     * This will never be wiped.
     *
     * @param key The key of the value
     * @param value A long
     */
    public void set(String key, Long value) {
        NBTUtils.write(this.entity, key, value);
    }
    
    /**
     * Gets the saved persistent data from this block.
     *
     * @param key The key of the value
     * @return A string
     */
    public String readString(String key) {
        return NBTUtils.readString(this.entity, key);
    }
    
    /**
     * Gets the saved persistent data from this block.
     *
     * @param key The key of the value
     * @return A int
     */
    public int readInt(String key) {
        return NBTUtils.readInt(this.entity, key);
    }
    
    /**
     * Gets the saved persistent data from this block.
     *
     * @param key The key of the value
     * @return A double
     */
    public double readDouble(String key) {
        return NBTUtils.readDouble(this.entity, key);
    }
    
    /**
     * Gets the saved persistent data from this block.
     *
     * @param key The key of the value
     * @return A float
     */
    public Float readFloat(String key) {
        return NBTUtils.readFloat(this.entity, key);
    }
    
    /**
     * Gets the saved persistent data from this block.
     *
     * @param key The key of the value
     * @return A boolean
     */
    public boolean readBoolean(String key) {
        return NBTUtils.readBoolean(this.entity, key);
    }
    
    /**
     * Gets the saved persistent data from this block.
     *
     * @param key The key of the value
     * @return A long
     */
    public Long readLong(String key) {
        return NBTUtils.readLong(this.entity, key);
    }
    //</editor-fold>
}
