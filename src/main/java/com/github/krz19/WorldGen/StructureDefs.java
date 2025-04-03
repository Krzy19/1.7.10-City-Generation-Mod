package com.github.krz19.WorldGen;

public class StructureDefs {
    //0=window 1=wall
    public static int[][] wallPattern = {
            {0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
            {0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0}
    };

    //0=nothing, 1 = wall, 2 =stairs (facing up),3 blockade (table),4=ladder

    public static int [][][]roomPattern={
            {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,1,1,1,1,0,0,1,1,1,1,0,0},
                    {0,0,1,0,0,0,0,0,0,0,0,1,0,0},
                    {0,0,1,0,3,3,0,0,0,0,0,1,0,0},
                    {0,0,1,0,3,3,0,0,0,0,0,1,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                    {0,0,1,0,3,3,0,0,0,0,0,1,0,0},
                    {0,0,1,0,3,3,0,0,0,0,2,1,0,0},
                    {0,0,1,0,0,0,0,0,0,0,0,1,0,0},
                    {0,0,1,1,1,1,0,0,1,1,1,1,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            },
            {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {3,3,3,0,0,1,1,1,1,0,0,3,3,3},
                    {0,0,0,0,0,1,4,4,1,0,0,0,0,0},
                    {0,0,0,0,0,1,0,0,1,0,0,0,0,0},
                    {3,3,3,0,0,1,0,0,1,0,0,3,3,3},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {3,3,3,0,0,0,0,0,0,0,0,3,3,3},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {3,3,3,0,0,1,0,0,1,0,0,3,3,3},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            },
            {
                    {0,0,0,0,1,0,0,0,0,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,3,0,0,0,0,0,0,0,0,0,0,3,0},
                    {0,3,0,0,1,0,0,0,0,1,0,0,3,0},
                    {1,1,1,1,1,0,0,0,0,1,1,1,1,1},
                    {0,0,0,0,1,0,0,0,0,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,1,0,0,0,0,1,0,0,0,0},
                    {1,1,1,1,1,0,2,2,0,1,1,1,1,1},
                    {0,3,0,0,1,0,0,0,0,1,0,0,3,0},
                    {0,3,0,0,0,0,0,0,0,0,0,0,3,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,1,0,0,0,0,1,0,0,0,0}
            }
    };
}
