package twilightforest.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import twilightforest.biomes.TFBiomeBase;

public class GenLayerTFCheckBad extends GenLayer {

    private String stage;

	public GenLayerTFCheckBad(long par1, GenLayer par3GenLayer, String stage)
    {
        super(par1);
        super.parent = par3GenLayer;
        
        this.stage = stage;
    }
	
	@Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        int input[] = parent.getInts(x, z, width, depth);

		
		for (int i = 0; i < width * depth; i++)
		{
			if (input[i] < 0 || input[i] > TFBiomeBase.fireSwamp.biomeID)
			{
				System.err.printf("Got a bad ID, %d at %d, %d while checking during stage %s\n", input[i], x, z, this.stage);
			}
		}
		

		return input;
	}

}
