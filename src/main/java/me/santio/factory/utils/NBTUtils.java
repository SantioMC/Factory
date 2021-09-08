package me.santio.factory.utils;

import me.santio.factory.FactoryLib;
import me.santio.factory.models.FactoryBlock;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.persistence.PersistentDataType;

/**
 * This is for internal use.
 */
@SuppressWarnings("unused")
public class NBTUtils {
    
    private static NamespacedKey generateKey(String key) {
        return new NamespacedKey(FactoryLib.getInstance(), key);
    }
    public static void setBlockTile(ArmorStand entity, FactoryBlock block) {
        entity.getPersistentDataContainer().set(generateKey("factoryTile"), PersistentDataType.STRING, block.getID());
    }
    public static String getBlockTile(ArmorStand entity) {
        return entity.getPersistentDataContainer().get(generateKey("factoryTile"), PersistentDataType.STRING);
    }
    public static void write(ArmorStand entity, String key, String value) {
        entity.getPersistentDataContainer().set(generateKey("factory_"+key), PersistentDataType.STRING, value);
    }
    public static void write(ArmorStand entity, String key, Integer value) {
        entity.getPersistentDataContainer().set(generateKey("factory_"+key), PersistentDataType.INTEGER, value);
    }
    public static void write(ArmorStand entity, String key, Double value) {
        entity.getPersistentDataContainer().set(generateKey("factory_"+key), PersistentDataType.DOUBLE, value);
    }
    public static void write(ArmorStand entity, String key, Float value) {
        entity.getPersistentDataContainer().set(generateKey("factory_"+key), PersistentDataType.FLOAT, value);
    }
    public static void write(ArmorStand entity, String key, Boolean value) {
        entity.getPersistentDataContainer().set(generateKey("factory_"+key), PersistentDataType.INTEGER, value ? 1 : 0);
    }
    public static void write(ArmorStand entity, String key, Long value) {
        entity.getPersistentDataContainer().set(generateKey("factory_"+key), PersistentDataType.LONG, value);
    }
    public static String readString(ArmorStand entity, String key) {
        return entity.getPersistentDataContainer().get(generateKey("factory_" + key), PersistentDataType.STRING);
    }
    public static Integer readInt(ArmorStand entity, String key) {
        return entity.getPersistentDataContainer().get(generateKey("factory_" + key), PersistentDataType.INTEGER);
    }
    public static Double readDouble(ArmorStand entity, String key) {
        return entity.getPersistentDataContainer().get(generateKey("factory_" + key), PersistentDataType.DOUBLE);
    }
    public static Float readFloat(ArmorStand entity, String key) {
        return entity.getPersistentDataContainer().get(generateKey("factory_" + key), PersistentDataType.FLOAT);
    }
    public static Boolean readBoolean(ArmorStand entity, String key) {
        return entity.getPersistentDataContainer().getOrDefault(generateKey("factory_" + key), PersistentDataType.INTEGER, 0) == 1;
    }
    public static Long readLong(ArmorStand entity, String key) {
        return entity.getPersistentDataContainer().get(generateKey("factory_" + key), PersistentDataType.LONG);
    }
    
}
