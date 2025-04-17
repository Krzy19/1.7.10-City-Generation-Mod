package com.github.krz19.WorldGen;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import org.lwjgl.Sys;

import java.util.Random;

import static java.lang.Math.abs;

public class CitiesGenChunkProvider extends ChunkProviderGenerate
{
    final int CHUNK_PACK_SIZE=5;
    final double BUILDING_SPAWN_CHANCE=0.9;
    final double FEATURE_SPAWN_CHANCE=0.1;

    public World world;
    private final NoiseGeneratorPerlin cityNoise;

    private final double citySpawnThreshold=0.35;
    private final double cityNoiseScale=0.025;

    private final int baseCityHeight=70;

    private static final BiomeGenBase[] blacklistedBiomes={
            BiomeGenBase.deepOcean,BiomeGenBase.ocean,BiomeGenBase.frozenOcean,
            BiomeGenBase.extremeHills,BiomeGenBase.desertHills,BiomeGenBase.extremeHillsEdge,BiomeGenBase.extremeHillsEdge,
            BiomeGenBase.extremeHillsEdge,BiomeGenBase.iceMountains,BiomeGenBase.jungleHills,BiomeGenBase.forestHills,
            BiomeGenBase.mesaPlateau,BiomeGenBase.taigaHills,BiomeGenBase.savannaPlateau,BiomeGenBase.mesaPlateau_F,
            BiomeGenBase.megaTaigaHills
    };

    public CitiesGenChunkProvider(World world, long seed) {
        super(world, seed, false);
        this.world = world;

        Random rand = new Random(seed);
        this.cityNoise = new NoiseGeneratorPerlin(rand, 1);
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        Chunk chunk = super.provideChunk(chunkX, chunkZ);

        if(validCityPosition(chunkX,chunkZ))
        {
                fillPlatform(chunk);
        }
        if(validRoadPosition(chunkX,chunkZ))
        {
                generateRoad(chunk,chunkX,chunkZ);
        }

        return chunk;
    }



    public boolean validCityPosition(int chunkX,int chunkZ)
    {
        double noiseValue=cityNoisePack(chunkX,chunkZ);

        return (noiseValue >= citySpawnThreshold && chunkX%CHUNK_PACK_SIZE!=0 && chunkZ%CHUNK_PACK_SIZE!=0);
    }

    public boolean validRoadPosition(int chunkX,int chunkZ)
    {
        if(chunkX%50==0||chunkZ%50==0)
            return true;

        if(chunkX%CHUNK_PACK_SIZE!=0&&chunkZ%CHUNK_PACK_SIZE!=0)
            return false;
        else
        {
            double noiseValue=cityNoisePack(chunkX,chunkZ);
            double noiseAddX=cityNoisePack(chunkX-1,chunkZ);
            double noiseAddZ=cityNoisePack(chunkX,chunkZ-1);
            boolean noiseAddCorner=(cityNoise.func_151601_a(
                    (chunkX-CHUNK_PACK_SIZE)*cityNoiseScale,(chunkZ-CHUNK_PACK_SIZE)*cityNoiseScale)>=citySpawnThreshold)&&
                    chunkX%CHUNK_PACK_SIZE==0&&chunkZ%CHUNK_PACK_SIZE==0;

            System.out.println("Noise: "+noiseValue+" AddX :"+noiseAddX+" AddZ "+noiseAddZ);
            return(noiseValue>=citySpawnThreshold||noiseAddZ>=citySpawnThreshold||noiseAddX>=citySpawnThreshold||noiseAddCorner);
        }
    }

    public double cityNoisePack(int chunkX,int chunkZ)
    {
        while(chunkX%CHUNK_PACK_SIZE!=0)
        {
            chunkX-=1;
        }

        while(chunkZ%CHUNK_PACK_SIZE!=0)
        {
            chunkZ-=1;
        }
        return cityNoise.func_151601_a(chunkX*cityNoiseScale,chunkZ*cityNoiseScale);
    }

    private void fillPlatform(Chunk chunk)
    {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 20; y <= baseCityHeight; y++) {
                    if(y==baseCityHeight)
                        chunk.func_150807_a(x, y, z, Blocks.double_stone_slab, 0);
                    else
                        chunk.func_150807_a(x, y, z, Blocks.stone, 0);
                }
                for (int y = baseCityHeight+1; y <= 255; y++)
                {
                    chunk.func_150807_a(x, y, z, Blocks.air, 0);
                }
            }
        }

        //Building Generation
        if((abs(chunk.xPosition%CHUNK_PACK_SIZE)==1||
                abs(chunk.xPosition%CHUNK_PACK_SIZE)==4||
                abs(chunk.zPosition%CHUNK_PACK_SIZE)==1||
                abs(chunk.zPosition%CHUNK_PACK_SIZE)==4)&&
                world.rand.nextDouble()<=BUILDING_SPAWN_CHANCE)
            BuildingGen.generateBuilding(cityNoisePack(chunk.xPosition,chunk.zPosition),chunk,baseCityHeight+1);
        else if(world.rand.nextDouble()<=FEATURE_SPAWN_CHANCE)
            BuildingGen.generateFeature(chunk,baseCityHeight+1);
    }


    private void generateRoad(Chunk chunk,int chunkX,int chunkZ)
    {
        boolean northConnect=validRoadPosition(chunkX, chunkZ - 1);
        boolean southConnect=validRoadPosition(chunkX, chunkZ + 1);
        boolean westConnect=validRoadPosition(chunkX - 1, chunkZ);
        boolean eastConnect=validRoadPosition(chunkX + 1, chunkZ);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 20; y <= baseCityHeight; y++) {
                    if(y==baseCityHeight)
                    {
                        chunk.func_150807_a(x, y, z, Blocks.double_stone_slab, 0);

                        if((z>=2&&z<=13)&&(x>=2&&x<=13))
                            chunk.func_150807_a(x, y, z, Blocks.stained_hardened_clay, 9);
                        if(z>=2&&z<=13)
                        {
                            if(westConnect&&x<8)
                                chunk.func_150807_a(x, y, z, Blocks.stained_hardened_clay, 9);
                            if(eastConnect&&x>8)
                                chunk.func_150807_a(x, y, z, Blocks.stained_hardened_clay, 9);
                        }
                        if(x>=2&&x<=13)
                        {
                            if(northConnect&&z<8)
                                chunk.func_150807_a(x, y, z, Blocks.stained_hardened_clay, 9);
                            if(southConnect&&z>8)
                                chunk.func_150807_a(x, y, z, Blocks.stained_hardened_clay, 9);
                        }
                    }
                    else
                        chunk.func_150807_a(x, y, z, Blocks.stone, 0);
                }
                for (int y = baseCityHeight+1; y <= 255; y++)
                {
                    chunk.func_150807_a(x, y, z, Blocks.air, 0);
                }
            }
        }
    }

    private int calculateHeight(Chunk chunk)
    {
        return 0;
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        if(!validCityPosition(chunkX,chunkZ)&&!validRoadPosition(chunkX,chunkZ))
            super.populate(chunkProvider,chunkX,chunkZ);
    }
}