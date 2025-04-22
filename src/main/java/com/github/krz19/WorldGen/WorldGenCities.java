package com.github.krz19.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.*;

public class WorldGenCities extends WorldType{

    public WorldGenCities(String name) {
        super("AbandonedCities");
    }

    @Override
    public GenLayer getBiomeLayer(long seed, GenLayer parentLayer)
    {
        GenLayer layer=new GenLayerBiome(seed,parentLayer,this);

        layer = GenLayerZoom.magnify(seed,layer,3);

        layer= new GenLayerBiomeEdge(seed,layer);

        return layer;
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions)
    {
        return new CitiesGenChunkProvider(world, world.getSeed());
    }
}
