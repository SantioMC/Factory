package me.santio.factory.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.santio.factory.mods.FactoryMod;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

@Accessors(chain = true)
public class FactoryDimension extends ChunkGenerator {
    
    @Getter private final String name;
    @Getter private final FactoryMod owner;
    @Setter @Getter private boolean caves = false;
    @Setter @Getter private boolean mobs = false;
    @Setter @Getter private boolean structures = false;
    @Setter @Getter private boolean decorations = false;
    @Setter @Getter private boolean bedrock = false;
    @Setter @Getter private boolean noise = false;
    @Setter @Getter private boolean surface = false;
    
    public FactoryDimension(FactoryMod owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    
    @Override public boolean shouldGenerateCaves() {
        return caves;
    }
    @Override public boolean shouldGenerateMobs() {
        return mobs;
    }
    @Override public boolean shouldGenerateStructures() {
        return structures;
    }
    @Override public boolean shouldGenerateDecorations() {
        return decorations;
    }
    @Override public boolean shouldGenerateBedrock() {
        return bedrock;
    }
    @Override public boolean shouldGenerateNoise() {
        return noise;
    }
    @Override public boolean shouldGenerateSurface() {
        return surface;
    }
    
    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int x, int z, ChunkData chunkData) {
        // TODO: Experiment with this function and figure out how the new ChunkGenerator works
    }
}
