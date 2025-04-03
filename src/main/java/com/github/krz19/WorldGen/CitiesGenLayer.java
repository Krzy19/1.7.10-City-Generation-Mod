package com.github.krz19.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class CitiesGenLayer extends GenLayer {
    public CitiesGenLayer(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] parentInts = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] resultInts = IntCache.getIntCache(areaWidth * areaHeight);

        for (int y = 0; y < areaHeight; y++) {
            for (int x = 0; x < areaWidth; x++)
            {
                int index = x + y * areaWidth;
                resultInts[index] = 1; // Plains biome ID
            }
        }
        return resultInts;
    }


}
