package twilightforest.world;


import twilightforest.block.BlockTFLeaves;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.LeavesVariant;

public class TFGenSmallRainboak extends TFGenSmallTwilightOak
{

	public TFGenSmallRainboak() 
	{
		this(false);
	}


	public TFGenSmallRainboak(boolean notify) 
	{
		super(notify);
		this.leafState = TFBlocks.leaves.getDefaultState().withProperty(BlockTFLeaves.VARIANT, LeavesVariant.RAINBOAK);
	}

}
