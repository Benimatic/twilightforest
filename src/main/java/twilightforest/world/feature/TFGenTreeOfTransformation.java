package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.TFBlocks;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class TFGenTreeOfTransformation<T extends TFTreeFeatureConfig> extends TFGenCanopyTree<T> {

//	public TFGenTreeOfTransformation() {
//		this(false);
//	}
//
//	public TFGenTreeOfTransformation(boolean notify) {
//		super(notify);
//
//		this.treeState = TFBlocks.magic_log.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.TRANS);
//		this.branchState = treeState.with(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE);
//		this.leafState = TFBlocks.magic_leaves.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.TRANS).with(BlockLeaves.CHECK_DECAY, false);
//
//		this.minHeight = 11;
//		this.chanceAddFirstFive = Integer.MAX_VALUE;
//		this.chanceAddSecondFive = Integer.MAX_VALUE;
//	}

	public TFGenTreeOfTransformation(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	protected boolean generate(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, MutableBoundingBox mbb, T config) {
		if (super.generate(world, random, pos, trunk, leaves, mbb, config)) {
			// heart of transformation
			setBlockAndNotifyAdequately(world, pos.up(3), TFBlocks.magic_log_core.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.TRANS));
			return true;
		} else {
			return false;
		}
	}
}
