package me.santio.factory.oredict;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.santio.factory.models.FactoryItem;
import me.santio.factory.mods.FactoryMod;

public class Dictionary {
    
    @Getter private final FactoryMod mod;
    @Getter private final String identifier;
    
    public Dictionary(FactoryMod mod) {
        this.mod = mod;
        this.identifier = null;
    }
    public Dictionary(String identifier) {
        this.mod = null;
        this.identifier = identifier.toLowerCase().replaceAll(" ", "_");
    }
    public Dictionary(FactoryMod mod, String identifier) {
        this.mod = mod;
        this.identifier = identifier.toLowerCase().replaceAll(" ", "_");
    }
    
    @SuppressWarnings("RedundantIfStatement")
    public boolean matches(FactoryItem item) {
        if (mod != null && item.getMod() != mod) return false;
        else if (identifier != null && !OreDictionary.getID(item).equals(identifier)) return false;
        return true;
    }
}
