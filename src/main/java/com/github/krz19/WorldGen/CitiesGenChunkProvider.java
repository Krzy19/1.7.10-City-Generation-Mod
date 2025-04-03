package com.github.krz19.WorldGen;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class CitiesGenChunkProvider extends ChunkProviderGenerate {
    private World wrld;
    private NoiseGeneratorPerlin noiseGenerator;

    //platforms parameters
    private double threshold = 0.35; // Próg, powyżej którego chunk będzie wypełniony kamieniem
    private double scale = 0.025; // Skala szumu - im mniejsza, tym większe "wyspy" kamienia
    private int stepHeight = 5;

    private final int maxGenerationHeight = 70;
    private final int maxDeleteHeight = maxGenerationHeight + 20;

    private double featureSpawnchance=0.2;
    private double BuildingSpawnChance=0.5;

    public CitiesGenChunkProvider(World world, long seed) {
        super(world, seed, false);
        this.wrld = world;
        Random rand = new Random(seed);
        this.noiseGenerator = new NoiseGeneratorPerlin(rand, 1);
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        Chunk chunk = super.provideChunk(chunkX, chunkZ);

        // Generuj szum Perlina dla tego chunka
        double noiseValue = noiseGenerator.func_151601_a(chunkX * scale, chunkZ * scale);

        int maxheight = 0;
        boolean blacklistedBiome = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (chunk.getHeightValue(x, z) > maxheight)
                    maxheight = chunk.getHeightValue(x, z);

                BiomeGenBase biome = wrld.getBiomeGenForCoords((chunkX << 4) + x, (chunkZ << 4) + z);

                if (biome == BiomeGenBase.ocean ||
                        biome == BiomeGenBase.deepOcean ||
                        biome == BiomeGenBase.frozenOcean||
                        biome == BiomeGenBase.extremeHills||
                        biome == BiomeGenBase.extremeHillsEdge||
                        biome == BiomeGenBase.extremeHillsPlus||
                        biome == BiomeGenBase.savannaPlateau||
                        biome == BiomeGenBase.mesaPlateau||
                        biome == BiomeGenBase.mesaPlateau_F)
                    blacklistedBiome = true;
            }
        }

        // Jeśli wartość szumu przekracza próg, wypełnij chunk kamieniem
        if (noiseValue > threshold && !blacklistedBiome)
        {
            Chunk northChunk = super.provideChunk(chunkX, chunkZ - 1);
            Chunk southChunk = super.provideChunk(chunkX, chunkZ + 1);
            Chunk westChunk = super.provideChunk(chunkX - 1, chunkZ);
            Chunk eastChunk = super.provideChunk(chunkX + 1, chunkZ);

            fillChunkWithStone(chunk, maxheight, chunkX, chunkZ,noiseValue,northChunk,southChunk,westChunk,eastChunk);
        }

        //chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {

        Chunk chunk = super.provideChunk(chunkX, chunkZ);
        double noiseValue = noiseGenerator.func_151601_a(chunkX * scale, chunkZ * scale);
        int maxheight=0;
        boolean blacklistedBiome=false;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (chunk.getHeightValue(x, z) > maxheight)
                    maxheight = chunk.getHeightValue(x, z);

                BiomeGenBase biome = wrld.getBiomeGenForCoords((chunkX << 4) + x, (chunkZ << 4) + z);

                if (biome == BiomeGenBase.ocean ||
                        biome == BiomeGenBase.deepOcean ||
                        biome == BiomeGenBase.frozenOcean||
                        biome == BiomeGenBase.extremeHills||
                        biome == BiomeGenBase.extremeHillsEdge||
                        biome == BiomeGenBase.extremeHillsPlus||
                        biome == BiomeGenBase.savannaPlateau||
                        biome == BiomeGenBase.mesaPlateau||
                        biome == BiomeGenBase.mesaPlateau_F)
                    blacklistedBiome = true;
            }
        }
        if(maxheight>maxDeleteHeight||blacklistedBiome||noiseValue<threshold)
            super.populate(chunkProvider, chunkX, chunkZ);
    }

    private void fillChunkWithStone(Chunk chunk, int maxheight, int chunkX, int chunkZ, double noiseValue,
                                    Chunk northNeighbour,Chunk southNeighbour,Chunk westNeighbour,Chunk eastNeighbour) {
        int height = Math.min(maxheight + stepHeight - maxheight % stepHeight, maxGenerationHeight);
        boolean blocked = maxheight >= maxDeleteHeight;
        boolean isHole=false;

        int higherNeig=0;

        if(checkIfHigher(southNeighbour,height))higherNeig++;
        if(checkIfHigher(northNeighbour,height))higherNeig++;
        if(checkIfHigher(eastNeighbour,height))higherNeig++;
        if(checkIfHigher(westNeighbour,height))higherNeig++;

        if(higherNeig>=4)
            isHole=true;

        boolean northNeighbor = isStoned(northNeighbour,chunkX, chunkZ - 1, height);
        boolean southNeighbor = isStoned(southNeighbour,chunkX, chunkZ + 1, height);
        boolean westNeighbor = isStoned(westNeighbour,chunkX - 1, chunkZ, height);
        boolean eastNeighbor = isStoned(eastNeighbour,chunkX + 1, chunkZ, height);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 10; y < height; y++) {

                    if (height - y <= 1) {
                        if (blocked||isHole)
                            chunk.func_150807_a(x, y, z, Blocks.grass, 0);
                        else
                            chunk.func_150807_a(x, y, z, Blocks.double_stone_slab, 0);
                    } else
                        chunk.func_150807_a(x, y, z, Blocks.stone, 0);

                    if (x == 0 && !westNeighbor)
                        chunk.func_150807_a(x, y, z, Blocks.stonebrick, 0);
                    if (x == 15 && !eastNeighbor)
                        chunk.func_150807_a(x, y, z, Blocks.stonebrick, 0);
                    if (z == 0 && !northNeighbor)
                        chunk.func_150807_a(x, y, z, Blocks.stonebrick, 0);
                    if (z == 15 && !southNeighbor)
                        chunk.func_150807_a(x, y, z, Blocks.stonebrick, 0);
                }
                if (maxheight < maxDeleteHeight) {
                    for (int y = height; y < maxDeleteHeight; y++)
                        chunk.func_150807_a(x, y, z, Blocks.air, 0);
                }
            }
        }

        if(!blocked&&!isHole)
        {
            if(wrld.rand.nextDouble()<=BuildingSpawnChance)
                BuildingGen.genereateBuidling(noiseValue,chunk,height);
            else if(wrld.rand.nextDouble()<=featureSpawnchance)
                BuildingGen.generateFeature(chunk,height);
        }
    }

    private boolean checkIfHigher(Chunk chunk, int height)
    {
        int maxHeight=0;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (maxHeight < chunk.getHeightValue(x, z))
                    maxHeight = chunk.getHeightValue(x, z);
            }
        }

        int localHeight = Math.min(maxHeight + stepHeight - maxHeight % stepHeight, maxGenerationHeight);

        if(localHeight>height)
            return true;
        else
            return false;
    }

    private boolean isStoned(Chunk chunk,int chunkX, int chunkZ, int height) {

        int maxheight = 0;
        double noiseValue = noiseGenerator.func_151601_a(chunkX * scale, chunkZ * scale);

        if (noiseValue <= threshold)
            return false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (maxheight < chunk.getHeightValue(x, z))
                    maxheight = chunk.getHeightValue(x, z);

                System.out.println(maxheight);

                BiomeGenBase biome = wrld.getBiomeGenForCoords((chunkX << 4) + x, (chunkZ << 4) + z);

                if (biome == BiomeGenBase.ocean ||
                        biome == BiomeGenBase.deepOcean ||
                        biome == BiomeGenBase.frozenOcean||
                        biome == BiomeGenBase.extremeHills||
                        biome == BiomeGenBase.extremeHillsEdge||
                        biome == BiomeGenBase.extremeHillsPlus||
                        biome == BiomeGenBase.savannaPlateau||
                        biome == BiomeGenBase.mesaPlateau||
                        biome == BiomeGenBase.mesaPlateau_F)
                    return false;
            }
        }

        int heightToCheck = Math.min(maxheight + stepHeight - maxheight % stepHeight, maxGenerationHeight);

        if(maxheight>maxDeleteHeight)
        {
            heightToCheck=999; //temp value so that all terrain that is too high is connected
            height=999;
        }

        if (height==heightToCheck)
            return true;
        else
            return false;
    }
}