package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import twilightforest.TFAchievementPage;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFGenThorns;
import twilightforest.world.TFWorld;

public class TFBiomeThornlands extends TFBiomeBase {

	private TFGenThorns tfGenThorns;


	public TFBiomeThornlands(int i) {
		super(i);
		
        this.topBlock = TFBlocks.deadrock;
        this.field_150604_aj = 0;
        this.fillerBlock = TFBlocks.deadrock;
        this.field_76754_C = 1;
        
        this.temperature = 0.3F;
        this.rainfall = 0.2F;
        
        getTFBiomeDecorator().canopyPerChunk = -999;
		getTFBiomeDecorator().setTreesPerChunk(-999);
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.cactiPerChunk = -9999;
        this.spawnableCreatureList.clear();
        
        this.tfGenThorns = new TFGenThorns();
        
		this.theBiomeDecorator.generateLakes = false;
	}
	
	
    public void decorate(World world, Random rand, int mapX, int mapZ)
    {
        super.decorate(world, rand, mapX, mapZ);
        
        // add thorns!
        for (int i = 0; i < 128; i++)
        {
			int rx = mapX + rand.nextInt(16) + 8;
			int rz = mapZ + rand.nextInt(16) + 8;
			int ry = getGroundLevel(world, rx, rz);

			this.tfGenThorns.generate(world, rand, rx, ry, rz);
        } 
    }

	public int getGroundLevel(World world, int x, int z) {
		// go from sea level up.  If we get grass, return that, otherwise return the last dirt, stone or gravel we got
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		int lastDirt = TFWorld.SEALEVEL;
		for (int y = TFWorld.SEALEVEL; y < TFWorld.CHUNKHEIGHT - 1; y++) {
			Block blockID = chunk.getBlock(x & 15, y, z & 15);
			// grass = return immediately
			if (blockID == Blocks.GRASS) {
				return y + 1;
			}
			else if (blockID == Blocks.DIRT || blockID == Blocks.STONE || blockID == Blocks.GRAVEL || blockID == Blocks.SANDSTONE || blockID == Blocks.SAND || blockID == Blocks.CLAY || blockID == TFBlocks.deadrock) {
				lastDirt = y + 1;
			}
		}
		
		return lastDirt;
	}
	
    /**
     * Return a block if you want it to replace stone in the terrain generation
     */
	public Block getStoneReplacementBlock() {
		return TFBlocks.deadrock;
	}
	
    /**
     * Metadata for the stone replacement block
     */
	public byte getStoneReplacementMeta() {
		return 2;
	}
	
	protected Achievement getRequiredAchievement() {
		return TFAchievementPage.twilightProgressGlacier;
	}
}
