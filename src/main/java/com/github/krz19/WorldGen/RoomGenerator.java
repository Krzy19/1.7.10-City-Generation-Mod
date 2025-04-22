package com.github.krz19.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import static com.github.krz19.WorldGen.StructureDefs.roomPattern;

public class RoomGenerator
{
    public static void generateRoom(Chunk chunk, int height, int pattern,
                                     Block wallMaterial, Block blockadeMaterial, Block stairsMaterial,
                                     int wallMeta, int blockadeMeta)
    {

        for(int x =1;x<15;x++)
            for(int z=1;z<15;z++)
            {
                switch (roomPattern[pattern][x-1][z-1])
                {
                    case 1:
                        for(int y=height;y<height+4;y++)
                            chunk.func_150807_a(x, y, z, wallMaterial, wallMeta);
                        break;
                    case 2:
                        for(int i=0;i<5;i++)
                            chunk.func_150807_a(x-i, height+4, z, Blocks.air, 0);
                        for(int y=height;y<height+5;y++)
                            chunk.func_150807_a(x-y+height, y, z, stairsMaterial, 1);
                        break;
                    case 3:
                        chunk.func_150807_a(x, height, z, blockadeMaterial, blockadeMeta);
                        break;
                    case 4:
                        for(int y=height;y<height+5;y++)
                            chunk.func_150807_a(x, y, z, Blocks.ladder, 5);
                        break;
                    default:
                        break;
                }
            }
    }
}
