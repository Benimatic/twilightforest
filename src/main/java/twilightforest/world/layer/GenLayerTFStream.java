// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package twilightforest.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import twilightforest.biomes.TFBiomeBase;



// Referenced classes of package net.minecraft.src:
//            GenLayer, IntCache, BiomeGenBase

public class GenLayerTFStream extends GenLayer
{

    public GenLayerTFStream(long l, GenLayer genlayer)
    {
        super(l);
        super.parent = genlayer;
    }

    public int[] getInts(int x, int z, int width, int depth)
    {
        int nx = x - 1;
        int nz = z - 1;
        int nwidth = width + 2;
        int ndepth = depth + 2;
        int input[] = parent.getInts(nx, nz, nwidth, ndepth);
        int output[] = IntCache.getIntCache(width * depth);
        for(int dz = 0; dz < depth; dz++)
        {
            for(int dx = 0; dx < width; dx++)
            {
                int left = input[dx + 0 + (dz + 1) * nwidth];
                int right = input[dx + 2 + (dz + 1) * nwidth];
                int down = input[dx + 1 + (dz + 0) * nwidth];
                int up = input[dx + 1 + (dz + 2) * nwidth];
                int mid = input[dx + 1 + (dz + 1) * nwidth];
//                if(mid == 0 || left == 0 || right == 0 || down == 0 || up == 0)
//                {
//                    output[dx + dz * width] = TFBiomeBase.stream.biomeID;
//                    continue;
//                }
                if(shouldStream(mid, left, down, right, up))
                {
                    output[dx + dz * width] = TFBiomeBase.stream.biomeID;
                } else
                {
                    output[dx + dz * width] = -1;
                }
            }

        }

        return output;
    }
    
    boolean shouldStream (int mid, int left, int down, int right, int up) {
    	if (shouldStream(mid, left)) {
    		return true;
    	}
    	else if (shouldStream(mid, right)) {
    		return true;
    	}
    	else if (shouldStream(mid, down)) {
    		return true;
    	}
    	else if (shouldStream(mid, up)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    boolean shouldStream (int biome1, int biome2) {
       	if (biome1 == biome2) {
    		return false;
    	}
       	if (biome1 == -biome2) {
    		return false;
    	}
    	// glacier and snow have no border
       	if (biome1 == TFBiomeBase.glacier.biomeID && biome2 == TFBiomeBase.tfSnow.biomeID) {
    		return false;
    	}
    	if (biome1 == TFBiomeBase.tfSnow.biomeID && biome2 == TFBiomeBase.glacier.biomeID) {
    		return false;
    	}
       	// mushrooms
    	if (biome1 == TFBiomeBase.deepMushrooms.biomeID && biome2 == TFBiomeBase.mushrooms.biomeID) {
    		return false;
    	}
    	if (biome1 == TFBiomeBase.mushrooms.biomeID && biome2 == TFBiomeBase.deepMushrooms.biomeID) {
    		return false;
    	}
       	// fire swamp
    	if (biome1 == TFBiomeBase.tfSwamp.biomeID && biome2 == TFBiomeBase.fireSwamp.biomeID) {
    		return false;
    	}
    	if (biome1 == TFBiomeBase.fireSwamp.biomeID && biome2 == TFBiomeBase.tfSwamp.biomeID) {
    		return false;
    	}
       	// highlands
    	if (biome1 == TFBiomeBase.highlands.biomeID && biome2 == TFBiomeBase.highlandsCenter.biomeID) {
    		return false;
    	}
    	if (biome1 == TFBiomeBase.highlandsCenter.biomeID && biome2 == TFBiomeBase.highlands.biomeID) {
    		return false;
    	}
       	// dark forest
    	if (biome1 == TFBiomeBase.darkForest.biomeID && biome2 == TFBiomeBase.darkForestCenter.biomeID) {
    		return false;
    	}
    	if (biome1 == TFBiomeBase.darkForestCenter.biomeID && biome2 == TFBiomeBase.darkForest.biomeID) {
    		return false;
    	}
    	// no lake border
    	if (biome1 == TFBiomeBase.tfLake.biomeID || biome2 == TFBiomeBase.tfLake.biomeID) {
    		return false;
    	}
    	// clearing
    	if (biome1 == TFBiomeBase.clearing.biomeID || biome2 == TFBiomeBase.oakSavanna.biomeID) {
    		return false;
    	}
    	if (biome1 == TFBiomeBase.oakSavanna.biomeID || biome2 == TFBiomeBase.clearing.biomeID) {
    		return false;
    	}
    	// thorns need no stream
    	if (biome1 == TFBiomeBase.thornlands.biomeID || biome2 == TFBiomeBase.thornlands.biomeID) {
    		return false;
    	}

    	
    	return true;
    }
    
}
