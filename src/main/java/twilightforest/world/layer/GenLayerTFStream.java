// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package twilightforest.world.layer;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import twilightforest.biomes.TFBiomeBase;



// Referenced classes of package net.minecraft.src:
//            GenLayer, IntCache, Biome

public class GenLayerTFStream extends GenLayer
{

    public GenLayerTFStream(long l, GenLayer genlayer)
    {
        super(l);
        super.parent = genlayer;
    }

    @Override
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
//                    output[dx + dz * width] = Biome.getIdForBiome(TFBiomeBase.stream);
//                    continue;
//                }
                if(shouldStream(mid, left, down, right, up))
                {
                    output[dx + dz * width] = Biome.getIdForBiome(TFBiomeBase.stream);
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
       	if (biome1 == Biome.getIdForBiome(TFBiomeBase.glacier) && biome2 == Biome.getIdForBiome(TFBiomeBase.tfSnow)) {
    		return false;
    	}
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.tfSnow) && biome2 == Biome.getIdForBiome(TFBiomeBase.glacier)) {
    		return false;
    	}
       	// mushrooms
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.deepMushrooms) && biome2 == Biome.getIdForBiome(TFBiomeBase.mushrooms)) {
    		return false;
    	}
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.mushrooms) && biome2 == Biome.getIdForBiome(TFBiomeBase.deepMushrooms)) {
    		return false;
    	}
       	// fire swamp
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.tfSwamp) && biome2 == Biome.getIdForBiome(TFBiomeBase.fireSwamp)) {
    		return false;
    	}
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.fireSwamp) && biome2 == Biome.getIdForBiome(TFBiomeBase.tfSwamp)) {
    		return false;
    	}
       	// highlands
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.highlands) && biome2 == Biome.getIdForBiome(TFBiomeBase.highlandsCenter)) {
    		return false;
    	}
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.highlandsCenter) && biome2 == Biome.getIdForBiome(TFBiomeBase.highlands)) {
    		return false;
    	}
       	// dark forest
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.darkForest) && biome2 == Biome.getIdForBiome(TFBiomeBase.darkForestCenter)) {
    		return false;
    	}
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.darkForestCenter) && biome2 == Biome.getIdForBiome(TFBiomeBase.darkForest)) {
    		return false;
    	}
    	// no lake border
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.tfLake) || biome2 == Biome.getIdForBiome(TFBiomeBase.tfLake)) {
    		return false;
    	}
    	// clearing
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.clearing) || biome2 == Biome.getIdForBiome(TFBiomeBase.oakSavanna)) {
    		return false;
    	}
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.oakSavanna) || biome2 == Biome.getIdForBiome(TFBiomeBase.clearing)) {
    		return false;
    	}
    	// thorns need no stream
    	if (biome1 == Biome.getIdForBiome(TFBiomeBase.thornlands) || biome2 == Biome.getIdForBiome(TFBiomeBase.thornlands)) {
    		return false;
    	}

    	
    	return true;
    }
    
}
