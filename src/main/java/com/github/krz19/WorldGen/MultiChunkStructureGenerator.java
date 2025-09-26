package com.github.krz19.WorldGen;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.github.krz19.WorldGen.BuildingGen.random;
import static com.github.krz19.WorldGen.Materials.*;
import static com.github.krz19.WorldGen.Materials.MEDIUM_FLOOR;
import static com.github.krz19.lib.Constants.*;

public class MultiChunkStructureGenerator
{
    private static final int MULTI_CHUNK_TYPES = 3;
    private final World world;

    public MultiChunkStructureGenerator(World world)
    {
        this.world=world;
    }

    public void generateMultiChunk(Chunk chunk, int height)
    {
        generateTest(chunk,height);
    }

    public static int chunkPackMod(int pos)
    {
        return (((pos) % CHUNK_PACK_SIZE) + CHUNK_PACK_SIZE) % CHUNK_PACK_SIZE;
    }

    public int getBuildingType(int chunkX,int chunkZ)
    {
        while(chunkX%CHUNK_PACK_SIZE!=0)
        {
            chunkX-=1;
        }

        while(chunkZ%CHUNK_PACK_SIZE!=0)
        {
            chunkZ-=1;
        }

        long seed = (((long) chunkX * 341873128712L) ^ ((long) chunkZ * 132897987541L) ^ world.getSeed() ^ 0x5DEECE66DL);
        Random random=new Random(seed);

        return random.nextInt(MULTI_CHUNK_TYPES);
    }

    private void generateTest(Chunk chunk,int height)
    {
        int buildingType = getBuildingType(chunk.xPosition-1,chunk.zPosition-1);
        int meta = chunkPackMod(chunk.xPosition-1) + chunkPackMod(chunk.zPosition-1) * (CHUNK_PACK_SIZE-1);

        switch (buildingType)
        {
            case 0:
                generateParking(chunk,meta,height);
                break;
            case 1:
                generateMall(chunk,meta,height);
                break;
            case 2:
                generateParkingWShops(chunk,meta,height);
            default:
                break;
        }
    }

    private void generateParking(Chunk chunk,int chunkID,int height)
    {
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
            {
                if(z==0||z==15||(x%4==0&&(z<=4||z>=11)))
                {
                    chunk.func_150807_a(x, height, z, Blocks.carpet, 0);
                }

                chunk.func_150807_a(x, height-1, z, Blocks.stained_hardened_clay, 9);
            }
    }

    private void generateParkingWShops(Chunk chunk,int chunkID,int height)
    {
        if(chunkID<12)
        {
            for (int x = 0; x < 16; x++)
                for (int z = 0; z < 16; z++) {
                    if (z == 0 || z == 15 || (x % 4 == 0 && (z <= 4 || z >= 11))) {
                        chunk.func_150807_a(x, height, z, Blocks.carpet, 0);
                    }

                    chunk.func_150807_a(x, height - 1, z, Blocks.stained_hardened_clay, 9);
                }
        }
        else
        {
            MidBuildingGenerator.generateMidBuilding(chunk,1,height,
                    MEDIUM_WALLS[random.nextInt(MEDIUM_WALLS.length)],
                    WINDOWS[random.nextInt(WINDOWS.length)],
                    MEDIUM_FLOOR[random.nextInt(MEDIUM_FLOOR.length)]);

            int meta = random.nextInt(16);
            for(int x=1;x<=14;x++)
            {
                chunk.func_150807_a(x, height+5, 0, Blocks.wool, meta);
                chunk.func_150807_a(x, height+6, 0, Blocks.wool, meta);
            }
        }

    }

    private void generateMall(Chunk chunk,int chunkID,int height)
    {
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
            {
                for(int y=0;y<16;y++)
                    chunk.func_150807_a(x, y, z, Blocks.stained_glass, chunkID);
            }
    }
}
