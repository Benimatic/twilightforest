package twilightforest.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import twilightforest.biomes.TFBiomeBase;


public class GenLayerTFCompanionBiomes extends GenLayer {

    public GenLayerTFCompanionBiomes(long l, GenLayer genlayer)
    {
        super(l);
        parent = genlayer;
    }

    /**
     * If we are next to one of the 4 "key" biomes, we randomly turn into a companion biome for that center biome
     */
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
                int right = input[dx + 0 + (dz + 1) * nwidth];
                int left = input[dx + 2 + (dz + 1) * nwidth];
                int up = input[dx + 1 + (dz + 0) * nwidth];
                int down = input[dx + 1 + (dz + 2) * nwidth];
                int center = input[dx + 1 + (dz + 1) * nwidth];
                if (isKey(TFBiomeBase.fireSwamp.biomeID, center, right, left, up, down))
                {
                    output[dx + dz * width] = TFBiomeBase.tfSwamp.biomeID;
                }
                else if (isKey(TFBiomeBase.glacier.biomeID, center, right, left, up, down))
                {
                    output[dx + dz * width] = TFBiomeBase.tfSnow.biomeID;
                }
                else if (isKey(TFBiomeBase.darkForestCenter.biomeID, center, right, left, up, down))
                {
                    output[dx + dz * width] = TFBiomeBase.darkForest.biomeID;
                }
                else if (isKey(TFBiomeBase.highlandsCenter.biomeID, center, right, left, up, down))
                {
                    output[dx + dz * width] = TFBiomeBase.highlands.biomeID;
                }
                else
                {
                    output[dx + dz * width] = center;
                }
            }

        }

        return output;
    }
	
	/**
	 * Returns true if any of the surrounding biomes is the specified biome
	 */
	boolean isKey(int biome, int center, int right, int left, int up, int down) {
		
		return center != biome && (right == biome || left == biome || up == biome || down == biome);
	}

}
