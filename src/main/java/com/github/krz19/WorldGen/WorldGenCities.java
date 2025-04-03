package com.github.krz19.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;

public class WorldGenCities extends WorldType{

    /**
     * Creates a new world type, the ID is hidden and should not be referenced by modders.
     * It will automatically expand the underlying workdType array if there are no IDs left.
     *
     * @param name
     */
    public WorldGenCities(String name) {
        super("AbandonedCities");
    }

    @Override
    public GenLayer getBiomeLayer(long seed, GenLayer parentLayer)
    {
        return new GenLayerBiome(200L,parentLayer,this);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions)
    {
        return new CitiesGenChunkProvider(world, world.getSeed());
    }
}
