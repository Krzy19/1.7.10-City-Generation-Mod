package com.github.krz19.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.github.krz19.WorldGen.BuildingGen.FLOOR_HEIGHT;
import static com.github.krz19.WorldGen.Materials.*;
import static com.github.krz19.WorldGen.Materials.STAIRS;
import static com.github.krz19.WorldGen.RoomGenerator.generateRoom;
import static com.github.krz19.WorldGen.StructureDefs.roomPattern;
import static com.github.krz19.WorldGen.StructureDefs.wallPattern;

public class MidBuildingGenerator
{
    private static Random random;


    public  MidBuildingGenerator(Random random)
    {
        this.random=random;
    }

    public static void generateMidBuilding(Chunk chunk, int n_floors, int height,
                                            Block wallMaterial, Block windowMaterial, Block floorMaterial)
    {
        //building params
        int wallPatternChoice = random.nextInt(wallPattern.length);
        int roomPatternChoice = random.nextInt(roomPattern.length);

        boolean roofCorner= random.nextBoolean();
        int wallMeta = 0;
        int floorMeta = 0;
        int windowMeta = 0;
        int[] possibleGlassMeta = {0, 3, 7, 8, 9, 11, 15};
        int[] possibleClayMeta={1,7,8,9,12,14,15};
        int[] possibleWoolMeta={0,1,3,7,8,9,11,12,13,15};

        //room_params
        Block r_wall_mat=ROOM_WALL_MAT[random.nextInt(ROOM_WALL_MAT.length)];
        Block r_blockade_mat=ROOM_BLOCKADE_MAT[random.nextInt(ROOM_BLOCKADE_MAT.length)];
        Block r_stairs=STAIRS[random.nextInt(STAIRS.length)];

        int[] wallChoices={0,1,3,7,8,9,11,12,13,14,15};
        int r_wallMeta=wallChoices[random.nextInt(wallChoices.length)];
        int r_blockadeMeta=0;

        if (r_blockade_mat == Blocks.planks)
            r_blockadeMeta = random.nextInt(5);

        if (wallMaterial == Blocks.stained_hardened_clay)
            wallMeta = possibleClayMeta[random.nextInt(possibleClayMeta.length)];
        if (floorMaterial == Blocks.planks)
            floorMeta = random.nextInt(5);
        if(floorMaterial == Blocks.wool)
            floorMeta = possibleWoolMeta[random.nextInt(possibleWoolMeta.length)];
        if (windowMaterial == Blocks.stained_glass || windowMaterial == Blocks.stained_glass_pane)
            windowMeta = possibleGlassMeta[random.nextInt(possibleGlassMeta.length)];

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                for (int y = height - 1; y < n_floors * 5 + height; y++)
                {
                    //basic structure
                    if (y % FLOOR_HEIGHT == 0)
                    {
                        if(x!=15 && x!=0 && z!=0 && z!=15)
                            chunk.func_150807_a(x, y, z, floorMaterial, floorMeta);
                        if (y != n_floors * FLOOR_HEIGHT + height - 1)
                            generateRoom(chunk,y+1,roomPatternChoice,
                                    r_wall_mat,r_blockade_mat,r_stairs,r_wallMeta,r_blockadeMeta);
                    }

                    //roof
                    if (y == n_floors * FLOOR_HEIGHT + height - 1) {
                        if (roofCorner && (x == 0 || x == 15 || z == 0 || z == 15))
                            chunk.func_150807_a(x, y, z, wallMaterial, wallMeta);
                        else
                            chunk.func_150807_a(x, y, z, Blocks.stonebrick, 0);
                    }


                    //walls
                    if (y != height-1 && y != n_floors * 5 + height - 1) {
                        if (x == 0 || x == 15) {
                            chunk.func_150807_a(x, y, z, wallMaterial, wallMeta);
                            if (wallPattern[wallPatternChoice][z] == 1)
                                if( y % FLOOR_HEIGHT != 0 && y % FLOOR_HEIGHT != 1)
                                    chunk.func_150807_a(x, y, z, windowMaterial, windowMeta);
                        }
                        if (z == 0 || z == 15) {
                            chunk.func_150807_a(x, y, z, wallMaterial, wallMeta);
                            if (wallPattern[wallPatternChoice][x] == 1)
                                if( y % FLOOR_HEIGHT != 0 && y % FLOOR_HEIGHT != 1)
                                    chunk.func_150807_a(x, y, z, windowMaterial, windowMeta);
                        }
                    }
                }

        for(int i=0;i<2;i++)
        {
            chunk.func_150807_a(7, height+i, 15, Blocks.air, wallMeta);
            chunk.func_150807_a(8, height+i, 15, Blocks.air, wallMeta);
            chunk.func_150807_a(7, height+i, 0, Blocks.air, wallMeta);
            chunk.func_150807_a(8, height+i, 0, Blocks.air, wallMeta);
            chunk.func_150807_a(0, height+i, 7, Blocks.air, wallMeta);
            chunk.func_150807_a(0, height+i, 8, Blocks.air, wallMeta);
            chunk.func_150807_a(15, height+i, 7, Blocks.air, wallMeta);
            chunk.func_150807_a(15, height+i, 8, Blocks.air, wallMeta);
        }

    }
}
