package twilightforest.world.layer;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import twilightforest.biomes.TFBiomeBase;

public class GenLayerTF7x7Preset extends GenLayer {
	
	
	Biome preset[][] = new Biome[9][9];

	public GenLayerTF7x7Preset(long par1) {
		super(par1);
		initPresets();
	}
	
	@Override
	public int[] getInts(int x, int z, int width, int depth)
    {
        int dest[] = IntCache.getIntCache(width * depth);
        for(int dz = 0; dz < depth; dz++)
        {
            for(int dx = 0; dx < width; dx++)
            {
            	int sx = x + dx + 4;
            	int sz = z + dz + 4;
            	
                if (sx >= 0 && sx < 8 && sz >= 0 && sz < 8)
                {
                	dest[dx + dz * width] = preset[sx][sz].biomeID;
                }
                else
                {
                	dest[dx + dz * width] = Biome.ocean.biomeID;
                }
            }

        }
        
        return dest;

    }

	private void initPresets()
	{
		char[][] map = { 
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
				{ 'P', 'H', 'H', 'H', 'H', 'D', 'D', 'D', 'P' },
				{ 'P', 'H', 'H', 'H', 'H', 'D', 'D', 'D', 'P' },
				{ 'P', 'F', 'f', 'F', 'm', 'M', 'D', 'D', 'P' },
				{ 'P', 'F', 'F', 'F', 'C', 'm', 'D', 'D', 'P' },
				{ 'P', 'F', 'f', 'f', 'F', 'E', 'O', 'O', 'P' },
				{ 'P', 'S', 'S', 'S', 'L', 'L', 'O', 'G', 'P' },
				{ 'P', 'Y', 'S', 'S', 'L', 'L', 'O', 'G', 'P' },
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' } };

		
		for (int x = 0; x < map.length; x++)
		{
			for (int z = 0; z < map[x].length; z++)
			{
				preset[x][z] = getBiomeFor(map[z][x]);
			}
		}
		
	}

	protected Biome getBiomeFor(char c)
	{
		switch (c)
		{
		default :
			return Biome.ocean;
//		case 'P' :
//			return Biome.plains;
		case 'F' :
			return TFBiomeBase.twilightForest;
		case 'f' :
			return TFBiomeBase.twilightForest2;
		case 'E' :
			return TFBiomeBase.enchantedForest;
		case 'm' :
			return TFBiomeBase.mushrooms;
		case 'M' :
			return TFBiomeBase.deepMushrooms;
		case 'C' :
			return TFBiomeBase.clearing;
		case 'S' :
			return TFBiomeBase.tfSwamp;
		case 'Y' :
			return TFBiomeBase.fireSwamp;
		case 'D' :
			return TFBiomeBase.darkForest;
		case 'L' :
			return TFBiomeBase.tfLake;
		case 'O' :
			return TFBiomeBase.tfSnow;
		case 'G' :
			return TFBiomeBase.glacier;
		case 'H' :
			return TFBiomeBase.highlands;
		}
	}


}
