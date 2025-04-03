package com.github.krz19.WorldGen;

import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.github.krz19.WorldGen.Materials.*;
import static com.github.krz19.WorldGen.StructureDefs.*;

public class BuildingGen {
    private static final Random random = new Random();

    //building gen parameters
    public static int minFloorsHigh=1;
    public static int maxFloorsHigh=7;

    public static int minFloorsMedium=1;
    public static int maxFloorsMedium=3;

    public static int getMinFloorsSub=1;
    public static int maxFloorsSub=2;
   
    private static double suburbMaxTreshold=0.45;
    private static double mediumMaxTreshold=0.6;



    public enum cityArea {
        SUBURBS,
        MEDIUM,
        HIGHRISE
    }



    public static void genereateBuidling(double cityDensity, Chunk chunk, int height) {

        cityArea area = cityArea.HIGHRISE;

        if(cityDensity<=mediumMaxTreshold)
            area=cityArea.MEDIUM;
        if(cityDensity<=suburbMaxTreshold)
            area=cityArea.SUBURBS;


        switch (area) {
            case SUBURBS:
                break;
            case MEDIUM:
                generateMidBuilding(chunk,minFloorsMedium+random.nextInt(maxFloorsMedium),height,
                        MEDIUM_WALLS[random.nextInt(MEDIUM_WALLS.length)],
                        WINDOWS[random.nextInt(WINDOWS.length)],
                        MEDIUM_FLOOR[random.nextInt(MEDIUM_FLOOR.length)]);
                break;
            case HIGHRISE:

                generateTallBuilding(chunk, minFloorsHigh + random.nextInt(maxFloorsHigh), height,
                        HIGHRISE_WALLS[random.nextInt(HIGHRISE_WALLS.length)],
                        WINDOWS[random.nextInt(WINDOWS.length)],
                        HIGHRISE_FLOOR[random.nextInt(HIGHRISE_FLOOR.length)]);
                break;
        }
    }

    public static void generateFeature(Chunk chunk,int height)
    {
        int parkVariant=random.nextInt(3);
        int leavesMeta=4+random.nextInt(3);

        switch (parkVariant)
        {
            case 0:
                for (int x = 0; x < 16; x++)
                    for (int z = 0; z < 16; z++)
                    {
                        if(x>=2&&x<=13&&z>=2&&z<=13)
                            chunk.func_150807_a(x, height-1, z, Blocks.grass, 0);
                        if((x>=3&&x<=12&&z>=3&&z<=12)&&((x<=5||x>=10)||(z<=5||z>=10)))
                            chunk.func_150807_a(x, height, z, Blocks.leaves, leavesMeta);
                    }
                break;
            case 1:
                for (int x = 0; x < 16; x++)
                    for (int z = 0; z < 16; z++) {
                        if ((x > 0 && x < 15 && z > 0 && z < 15) && ((x < 6 || x > 9) && (z < 6 || z > 9)))
                            chunk.func_150807_a(x, height - 1, z, Blocks.grass, 0);
                        if ((x > 1 && x < 14 && z > 1 && z < 14) && ((x < 5 || x > 10) && (z < 5 || z > 10)))
                            chunk.func_150807_a(x, height, z, Blocks.leaves, leavesMeta);
                    }
                break;
            case 2:
                for (int x = 0; x < 16; x++)
                    for (int z = 0; z < 16; z++) {
                        if ((x > 0 && x < 15 && z > 0 && z < 15) && ((x < 5 || x > 10)))
                            chunk.func_150807_a(x, height - 1, z, Blocks.grass, 0);
                        if ((x >= 1 && x <= 14 && z >= 1 && z <= 14) && ((x < 4 || x > 11)))
                            chunk.func_150807_a(x, height, z, Blocks.leaves, leavesMeta);
                    }
                break;
        }
    }

    private static void generateTallBuilding(Chunk chunk, int n_floors, int height,
                                            Block wallMaterial, Block windowMaterial, Block floorMaterial)
    {
        //building params
        int wallPatternChoice = random.nextInt(wallPattern.length);
        int roomPatternChoice = random.nextInt(roomPattern.length);

        boolean fullGlass=random.nextBoolean();
        boolean roofCorner= random.nextBoolean();
        int wallMeta = 0;
        int floorMeta = 0;
        int windowMeta = 0;
        int[] possibleGlassMeta = {0, 3, 7, 8, 9, 11, 15};
        int[] possibleClayMeta={1,7,8,9,12,14,15};

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
        if (windowMaterial == Blocks.stained_glass || windowMaterial == Blocks.stained_glass_pane)
            windowMeta = possibleGlassMeta[random.nextInt(possibleGlassMeta.length)];

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                for (int y = height - 1; y < n_floors * 5 + height; y++)
                {
                    //basic structure
                    if (y % 5 == 4)
                    {
                        if(x!=15 && x!=0 && z!=0 && z!=15)
                            chunk.func_150807_a(x, y, z, floorMaterial, floorMeta);
                        if (y != n_floors * 5 + height - 1)
                            generateRoom(chunk,y+1,roomPatternChoice,false,
                                    r_wall_mat,r_blockade_mat,r_stairs,r_wallMeta,r_blockadeMeta);
                    }

                    //roof
                    if (y == n_floors * 5 + height - 1) {
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
                                if(fullGlass || y % 5 != 4)
                                    chunk.func_150807_a(x, y, z, windowMaterial, windowMeta);
                        }
                        if (z == 0 || z == 15) {
                            chunk.func_150807_a(x, y, z, wallMaterial, wallMeta);
                            if (wallPattern[wallPatternChoice][x] == 1)
                                if(fullGlass || y % 5 != 4)
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

    private static void generateRoom(Chunk chunk,int height,int pattern,boolean flipped,
                                     Block wallMaterial, Block blockadeMaterial,Block stairsMaterial,
                                     int wallMeta,int blockadeMeta)
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

    private static void generateMidBuilding(Chunk chunk, int n_floors, int height,
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
            floorMeta = random.nextInt(15);
        if (windowMaterial == Blocks.stained_glass || windowMaterial == Blocks.stained_glass_pane)
            windowMeta = possibleGlassMeta[random.nextInt(possibleGlassMeta.length)];

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                for (int y = height - 1; y < n_floors * 5 + height; y++)
                {
                    //basic structure
                    if (y % 5 == 4)
                    {
                        if(x!=15 && x!=0 && z!=0 && z!=15)
                            chunk.func_150807_a(x, y, z, floorMaterial, floorMeta);
                        if (y != n_floors * 5 + height - 1)
                            generateRoom(chunk,y+1,roomPatternChoice,false,
                                    r_wall_mat,r_blockade_mat,r_stairs,r_wallMeta,r_blockadeMeta);
                    }

                    //roof
                    if (y == n_floors * 5 + height - 1) {
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
                                if( y % 5 != 4 && y % 5 != 0)
                                    chunk.func_150807_a(x, y, z, windowMaterial, windowMeta);
                        }
                        if (z == 0 || z == 15) {
                            chunk.func_150807_a(x, y, z, wallMaterial, wallMeta);
                            if (wallPattern[wallPatternChoice][x] == 1)
                                if( y % 5 != 4 && y % 5 != 0)
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

