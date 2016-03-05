package twilightforest.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;


public class GenLayerTFFeatureZoom extends GenLayer
{
	
    public GenLayerTFFeatureZoom(long l, GenLayer genlayer)
    {
        super(l);
        this.parent = genlayer;
    }

    public int[] getInts(int x, int z, int width, int depth)
    {
        int sx = x >> 1;
        int sz = z >> 1;
        int swidth = (width >> 1) + 3;
        int sdepth = (depth >> 1) + 3;
        int src[] = parent.getInts(sx, sz, swidth, sdepth);
        int dest[] = IntCache.getIntCache(swidth * 2 * (sdepth * 2));
        int doubleWidth = swidth << 1;
        for (int dz = 0; dz < sdepth - 1; dz++)
        {
            for (int dx = 0; dx < swidth - 1; dx++)
            {
            	dest[(dx * 2 + 0) + (dz * 2 + 0) * doubleWidth] = src[dx + (dz * swidth)];
            	dest[(dx * 2 + 1) + (dz * 2 + 0) * doubleWidth] = 0;
            	dest[(dx * 2 + 0) + (dz * 2 + 1) * doubleWidth] = 0;
            	dest[(dx * 2 + 1) + (dz * 2 + 1) * doubleWidth] = 0;
            }
        }

        int output[] = IntCache.getIntCache(width * depth);
        for (int copyZ = 0; copyZ < depth; copyZ++)
        {
            System.arraycopy(dest, (copyZ + (z & 1)) * (swidth << 1) + (x & 1), output, copyZ * width, width);
        }

        return output;
    }


    public static GenLayer multipleZoom(long seed, GenLayer genlayer, int loops)
    {
    	GenLayer layer = genlayer;
        for (int i = 0; i < loops; i++)
        {
            layer = new GenLayerTFFeatureZoom(seed + (long)i, layer);
        }

        return layer;
    }
}
