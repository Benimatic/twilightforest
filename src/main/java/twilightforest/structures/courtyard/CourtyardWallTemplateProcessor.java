package twilightforest.structures.courtyard;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import twilightforest.block.BlockTFNagastoneStairs;
import twilightforest.block.TFBlocks;
import twilightforest.structures.RandomizedTemplateProcessor;

import javax.annotation.Nullable;

public class CourtyardWallTemplateProcessor extends RandomizedTemplateProcessor {

    public CourtyardWallTemplateProcessor(BlockPos pos, PlacementSettings settings) {
        super(pos, settings);
    }

    @Nullable
    @Override
    public Template.BlockInfo process(IWorldReader worldIn, BlockPos pos, Template.BlockInfo p_215194_3_, Template.BlockInfo blockInfo, PlacementSettings placementSettingsIn, @Nullable Template template) {
		if (shouldPlaceBlock()) {
			BlockState state = blockInfo.state;
			Block block = state.getBlock();

			if (state == Blocks.STONE_BRICKS.getDefaultState())
				//TODO: Flattened
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, state.with(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.values()[random.nextInt(3)]), null);

			if (state == Blocks.SMOOTH_STONE_SLAB.getDefaultState())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, Blocks.COBBLESTONE_SLAB.getDefaultState(), null);

			if (block == TFBlocks.etched_nagastone.get())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.etched_nagastone_mossy.get(), TFBlocks.etched_nagastone_weathered.get()), DirectionalBlock.FACING), null);

			if (block == TFBlocks.nagastone_pillar.get())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_pillar_mossy.get(), TFBlocks.nagastone_pillar_weathered.get()), RotatedPillarBlock.AXIS), null);

			if (block == TFBlocks.nagastone_stairs.get())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_stairs.get(), TFBlocks.nagastone_stairs.get()), BlockTFNagastoneStairs.DIRECTION, BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);

			return blockInfo;
		}

		return null;
    }
}