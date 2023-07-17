package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import twilightforest.block.HollowLogHorizontal;
import twilightforest.init.TFBlocks;
import twilightforest.enums.HollowLogVariants;
import twilightforest.util.FeatureUtil;
import twilightforest.world.components.feature.config.HollowLogConfig;

import org.jetbrains.annotations.Nullable;

public class SmallFallenLogFeature extends Feature<HollowLogConfig> {

	public SmallFallenLogFeature(Codec<HollowLogConfig> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<HollowLogConfig> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		RandomSource rand = ctx.random();
		HollowLogConfig config = ctx.config();
		boolean shouldMakeAllHollow = rand.nextBoolean();

		// determine direction
		boolean goingX = rand.nextBoolean();

		// length
		int length = rand.nextInt(4) + 3;

		// check area clear
		if (goingX) {
			if (!FeatureUtil.isAreaSuitable(world, pos, length, 2, 2, true)) {
				return false;
			}
		} else {
			if (!FeatureUtil.isAreaSuitable(world, pos, 2, 2, length, true)) {
				return false;
			}
		}

		// determine wood type
		BlockState logState = config.normal();
		@Nullable
		BlockState hollowLogState = config.hollow();
		BlockState branchState;

		if(config.hollow().isAir()) hollowLogState = null;

		//sometimes make floating logs
		if(rand.nextInt(5) == 0 && world.getBlockState(pos).getMaterial() == Material.WATER) {
			BlockPos.MutableBlockPos floatingPos = pos.mutable();
			for(int i = 0; i < 10; i++) {
				if(world.getBlockState(floatingPos.above()).isAir()) {
					pos = floatingPos.immutable();
					break;
				} else {
					floatingPos.move(0, 1, 0);
				}
			}
		}

		// make log
		if (goingX) {
			logState = logState.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X);
			if(hollowLogState != null) {
				hollowLogState = hollowLogState.setValue(HollowLogHorizontal.HORIZONTAL_AXIS, Direction.Axis.X)
						.setValue(HollowLogHorizontal.VARIANT, determineHollowProperties(world, pos, rand));
			}
			branchState = logState.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z);

			for (int lx = 0; lx < length; lx++) {
				world.setBlock(pos.offset(lx, 0, 1), hollowOrNormal(world, shouldMakeAllHollow, hollowLogState, logState), 3);
				if (rand.nextInt(3) > 0) {
					world.setBlock(pos.offset(lx, 1, 1), mossOrSeagrass(world, pos.offset(lx, 1, 1)), 3);
					this.markAboveForPostProcessing(world, pos.offset(lx, 0, 1));
				}
			}
		} else {
			logState = logState.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z);
			if(hollowLogState != null) {
				hollowLogState = hollowLogState.setValue(HollowLogHorizontal.HORIZONTAL_AXIS, Direction.Axis.Z)
						.setValue(HollowLogHorizontal.VARIANT, determineHollowProperties(world, pos, rand));
			}
			branchState = logState.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X);

			for (int lz = 0; lz < length; lz++) {
				world.setBlock(pos.offset(1, 0, lz), hollowOrNormal(world, shouldMakeAllHollow, hollowLogState, logState), 3);
				if (rand.nextInt(3) > 0) {
					world.setBlock(pos.offset(1, 1, lz), mossOrSeagrass(world, pos.offset(1, 1, lz)), 3);
					this.markAboveForPostProcessing(world, pos.offset(1, 0, lz));
				}
			}
		}

		// possibly make branch
		if (rand.nextInt(3) > 0) {
			int bx;
			int bz;
			if (goingX) {
				bx = rand.nextInt(length);
				bz = rand.nextBoolean() ? 2 : 0;
			} else {
				bx = rand.nextBoolean() ? 2 : 0;
				bz = rand.nextInt(length);

			}
			world.setBlock(pos.offset(bx, 0, bz), branchState, 3);
			if (rand.nextBoolean()) {
				world.setBlock(pos.offset(bx, 1, bz), mossOrSeagrass(world, pos.offset(bx, 1, bz)), 3);
				this.markAboveForPostProcessing(world, pos.offset(bx, 0, bz));
			}
		}

		return true;
	}

	private BlockState mossOrSeagrass(WorldGenLevel level, BlockPos pos) {
		//no moss if we're cold
		if(level.getBlockState(pos.below(2)).getMaterial() == Material.SNOW) {
			return Blocks.AIR.defaultBlockState();
		}
		return level.getBlockState(pos).getMaterial() == Material.WATER ? Blocks.SEAGRASS.defaultBlockState() : TFBlocks.MOSS_PATCH.get().defaultBlockState();
	}

	private BlockState hollowOrNormal(WorldGenLevel level, boolean shouldBeHollow, BlockState hollow, BlockState normal) {
		return ((shouldBeHollow || level.getRandom().nextInt(3) == 0) && hollow != null) ? hollow : normal;
	}

	private HollowLogVariants.Horizontal determineHollowProperties(WorldGenLevel world, BlockPos pos, RandomSource rand) {
		return  //If we're underwater, submerge in water
				world.getBlockState(pos).getMaterial() == Material.WATER ? HollowLogVariants.Horizontal.WATERLOGGED :
						//if we're in snow, add some snow
						world.getBlockState(pos).is(BlockTags.SNOW) ? HollowLogVariants.Horizontal.SNOW :
								//maybe add moss + grass
								rand.nextInt(5) == 0 ? HollowLogVariants.Horizontal.MOSS_AND_GRASS :
										//maybe some moss
										rand.nextInt(3) == 0 ? HollowLogVariants.Horizontal.MOSS :
												//or maybe nothing
												HollowLogVariants.Horizontal.EMPTY;
	}
}
