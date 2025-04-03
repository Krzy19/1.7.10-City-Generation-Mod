package com.github.krz19.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Materials
{
    //Highrise

    public static final Block[] HIGHRISE_WALLS = {
            Blocks.hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.stonebrick,
            Blocks.nether_brick
    };

    public static final Block[] HIGHRISE_FLOOR = {
            Blocks.planks,
            Blocks.quartz_block
    };

    //Medium

    public static final Block[] MEDIUM_WALLS = {
            Blocks.stained_hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.stonebrick,
            Blocks.sandstone,
            Blocks.brick_block
    };

    public static final Block[] MEDIUM_FLOOR = {
            Blocks.planks,
            Blocks.cobblestone,
            Blocks.wool
    };

    //suburb

    public static final Block[] SUB_WALLS = {
            Blocks.stained_hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.stained_hardened_clay,
            Blocks.sandstone,
            Blocks.brick_block,
            Blocks.planks
    };

    public static final Block[] SUB_FLOOR = {
            Blocks.planks,
            Blocks.cobblestone,
            Blocks.wool
    };

    public static final Block[] SUB_ROOF={
            Blocks.spruce_stairs,
            Blocks.stone_brick_stairs,
            Blocks.stone_stairs,
            Blocks.acacia_stairs,
            Blocks.oak_stairs,
            Blocks.jungle_stairs,
            Blocks.dark_oak_stairs,
            Blocks.brick_stairs,
            Blocks.nether_brick_stairs
    };

    //room

    public static final Block[] ROOM_WALL_MAT = {
            Blocks.stained_hardened_clay,
            Blocks.wool
    };

    public static final Block[] ROOM_BLOCKADE_MAT={
            Blocks.planks,
            Blocks.quartz_block,
            Blocks.double_stone_slab
    };

    public static final Block[] WINDOWS = {
            Blocks.glass,
            Blocks.stained_glass,
            Blocks.stained_glass,
            Blocks.stained_glass,
            Blocks.glass_pane,
            Blocks.stained_glass_pane
    };

    public static final Block[] STAIRS={
            Blocks.birch_stairs,
            Blocks.spruce_stairs,
            Blocks.stone_brick_stairs,
            Blocks.stone_stairs,
            Blocks.acacia_stairs,
            Blocks.oak_stairs,
            Blocks.jungle_stairs,
            Blocks.quartz_stairs,
            Blocks.dark_oak_stairs
    };
}
