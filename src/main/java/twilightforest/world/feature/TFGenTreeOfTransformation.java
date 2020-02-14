package twilightforest.world.feature;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.TFBlocks;
import twilightforest.enums.MagicWoodVariant;

import java.util.Random;

public class TFGenTreeOfTransformation extends TFGenCanopyTree {

	public TFGenTreeOfTransformation() {
		this(false);
	}

	public TFGenTreeOfTransformation(boolean notify) {
		super(notify);

		this.treeState = TFBlocks.magic_log.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.TRANS);
		this.branchState = treeState.with(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE);
		this.leafState = TFBlocks.magic_leaves.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.TRANS).with(BlockLeaves.CHECK_DECAY, false);

		this.minHeight = 11;
		this.chanceAddFirstFive = Integer.MAX_VALUE;
		this.chanceAddSecondFive = Integer.MAX_VALUE;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (super.generate(world, random, pos)) {
			// heart of transformation
			setBlockAndNotifyAdequately(world, pos.up(3), TFBlocks.magic_log_core.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.TRANS));
			return true;
		} else {
			return false;
		}
	}


}
