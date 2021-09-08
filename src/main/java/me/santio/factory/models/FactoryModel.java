package me.santio.factory.models;

import lombok.Getter;
import me.santio.factory.FactoryLib;
import me.santio.factory.mods.FactoryMod;

public class FactoryModel {
    
    @Getter private final FactoryMod mod;
    @Getter private final String model;
    @Getter private final int id;
    
    public FactoryModel(FactoryMod mod, String model, int id) {
        this.mod = mod;
        this.model = model;
        this.id = id;
    
        FactoryLib.getModels().put(mod.getDescription().getId()+"/"+model, this);
    }
    
}
