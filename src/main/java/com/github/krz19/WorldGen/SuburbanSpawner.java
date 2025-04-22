package com.github.krz19.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.github.krz19.WorldGen.StructureDefs.suburbStructures;

public class SuburbanSpawner
{
    private static Random random;

    public  SuburbanSpawner(Random random)
    {
        this.random=random;
    }

    static boolean suburbEmptyCheck(int structureType, int x, int z)
    {
        int sx=x-2;
        int sz=z-2;

        if(x>=2&&x<=14&&z>=2&&z<=14)
            return suburbStructures[structureType][sx][sz]==9;

        return true;
    }

    public static void generateSuburb(Chunk chunk, int height,
                                       Block wallMaterial, Block windowMaterial, Block floorMaterial, Block blockadeMaterial)
    {
        int structureType= random.nextInt(suburbStructures.length);

        int wallMeta = 0;
        int floorMeta = 0;
        int windowMeta = 0;
        int blockadeMeta=0;
        int[] possibleGlassMeta = {0, 3, 7, 8, 9, 11, 15};
        int[] possibleClayMeta={1,7,8,9,12,14,15};
        int[] possibleWoolMeta={0,1,3,7,8,9,11,12,13,15};

        if(wallMaterial== Blocks.stained_hardened_clay)
            wallMeta=possibleClayMeta[random.nextInt(possibleClayMeta.length)];
        if(floorMaterial==Blocks.wool)
            floorMeta=possibleWoolMeta[random.nextInt(possibleClayMeta.length)];
        if(floorMaterial==Blocks.planks)
            floorMeta= random.nextInt(5);
        if (windowMaterial == Blocks.stained_glass || windowMaterial == Blocks.stained_glass_pane)
            windowMeta = possibleGlassMeta[random.nextInt(possibleGlassMeta.length)];
        if (blockadeMaterial == Blocks.planks)
            blockadeMeta = random.nextInt(5);

        for(int x=0;x<16;x++)
            for(int z=0;z<16;z++)
                chunk.func_150807_a(x, height-1, z, Blocks.grass, 0);

        for(int x=2;x<15;x++)
            for(int z=2;z<15;z++)
            {
                switch(suburbStructures[structureType][x-2][z-2])
                {
                    case 0:
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 1:
                        chunk.func_150807_a(x, height, z, wallMaterial, wallMeta);
                        chunk.func_150807_a(x, height+1, z, wallMaterial, wallMeta);
                        chunk.func_150807_a(x, height+2, z, wallMaterial, wallMeta);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 2:
                        chunk.func_150807_a(x, height, z, wallMaterial, wallMeta);
                        chunk.func_150807_a(x, height+1, z, windowMaterial, windowMeta);
                        chunk.func_150807_a(x, height+2, z, windowMaterial, windowMeta);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 3:
                        chunk.func_150807_a(x, height+2, z, wallMaterial, wallMeta);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 4:
                        chunk.func_150807_a(x, height, z, blockadeMaterial, blockadeMeta);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 5:
                        chunk.func_150807_a(x, height, z, Blocks.crafting_table, 0);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 6:
                        chunk.func_150807_a(x, height, z, Blocks.furnace, 0);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 7:
                        chunk.func_150807_a(x, height, z, Blocks.bed, 0);
                        chunk.func_150807_a(x, height, z+1, Blocks.bed, 8);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    case 8:
                        chunk.func_150807_a(x, height, z, Blocks.cauldron, 0);
                        chunk.func_150807_a(x, height-1, z, floorMaterial, floorMeta);
                        break;
                    default:
                        break;
                }
            }

        //roof
        for(int x=2;x<15;x++)
            for(int z=2;z<15;z++)
            {
                int sx=x-2;
                int sz=z-2;
                if(suburbStructures[structureType][sx][sz]!=9)
                {
                    chunk.func_150807_a(x, height+3, z, Blocks.brick_block, 0);

                    if(suburbEmptyCheck(structureType,x-1,z-1))
                        chunk.func_150807_a(x-1, height+3, z-1, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x-1,z))
                        chunk.func_150807_a(x-1, height+3, z, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x-1,z+1))
                        chunk.func_150807_a(x-1, height+3, z+1, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x,z-1))
                        chunk.func_150807_a(x, height+3, z-1, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x,z+1))
                        chunk.func_150807_a(x, height+3, z+1, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x+1,z-1))
                        chunk.func_150807_a(x+1, height+3, z-1, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x+1,z))
                        chunk.func_150807_a(x+1, height+3, z, Blocks.stone_slab, 4);
                    if(suburbEmptyCheck(structureType,x+1,z+1))
                        chunk.func_150807_a(x+1, height+3, z+1, Blocks.stone_slab, 4);
                }

                if(z>2&&x>2&&x<14&&z<14)
                {
                    if(suburbStructures[structureType][sx-1][sz-1]!=9&&
                            suburbStructures[structureType][sx][sz-1]!=9&&
                            suburbStructures[structureType][sx+1][sz-1]!=9&&
                            suburbStructures[structureType][sx-1][sz]!=9&&
                            suburbStructures[structureType][sx+1][sz]!=9&&
                            suburbStructures[structureType][sx-1][sz+1]!=9&&
                            suburbStructures[structureType][sx][sz+1]!=9&&
                            suburbStructures[structureType][sx+1][sz+1]!=9
                    )
                    {
                        chunk.func_150807_a(x, height+4, z, Blocks.stone_slab, 4);
                        chunk.func_150807_a(x, height+3, z, Blocks.planks, 0);
                    }
                }
            }
    }
}
