package com.github.krz19.WorldGen;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.github.krz19.WorldGen.Materials.*;
import static com.github.krz19.WorldGen.MidBuildingGenerator.*;
import static com.github.krz19.WorldGen.StructureDefs.*;
import static com.github.krz19.WorldGen.SuburbanSpawner.*;
import static com.github.krz19.WorldGen.TallBuildingGenerator.*;

public class BuildingGen {
    public static final Random random = new Random();

    //building gen parameters
    public static int minFloorsHigh=1;
    public static int maxFloorsHigh=7;

    public static int minFloorsMedium=1;
    public static int maxFloorsMedium=3;

    private static double suburbMaxTreshold=0.5;
    private static double mediumMaxTreshold=0.7;

    public static final int FLOOR_HEIGHT=5;

    public enum cityArea {
        SUBURBS,
        MEDIUM,
        HIGHRISE
    }



    public static void generateBuilding(double cityDensity, Chunk chunk, int height) {

        cityArea area = cityArea.HIGHRISE;

        if(cityDensity<=mediumMaxTreshold)
            area=cityArea.MEDIUM;
        if(cityDensity<=suburbMaxTreshold)
            area=cityArea.SUBURBS;


        switch (area) {
            case SUBURBS:
                generateSuburb(chunk,height,
                        SUB_WALLS[random.nextInt(SUB_WALLS.length)],
                        WINDOWS[random.nextInt(WINDOWS.length)],
                        SUB_FLOOR[random.nextInt(SUB_FLOOR.length)],
                        ROOM_BLOCKADE_MAT[random.nextInt(ROOM_BLOCKADE_MAT.length)]);
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
        int parkVariant=random.nextInt(parkPattern.length);
        int leavesMeta=4+random.nextInt(3);

        for(int x=1;x<=7;x++)
            for(int z=1;z<=7;z++)
            {
                switch (parkPattern[parkVariant][x-1][z-1])
                {
                    case 2:
                        chunk.func_150807_a(x, height, z, Blocks.leaves, leavesMeta);
                        chunk.func_150807_a(15-x, height, z, Blocks.leaves, leavesMeta);
                        chunk.func_150807_a(x, height, 15-z, Blocks.leaves, leavesMeta);
                        chunk.func_150807_a(15-x, height, 15-z, Blocks.leaves, leavesMeta);
                    case 1:
                        chunk.func_150807_a(x, height-1, z, Blocks.grass, 0);
                        chunk.func_150807_a(15-x, height-1, z, Blocks.grass, 0);
                        chunk.func_150807_a(x, height-1, 15-z, Blocks.grass, 0);
                        chunk.func_150807_a(15-x, height-1, 15-z, Blocks.grass, 0);
                        break;
                    default:
                        break;
                }

            }
    }
}

