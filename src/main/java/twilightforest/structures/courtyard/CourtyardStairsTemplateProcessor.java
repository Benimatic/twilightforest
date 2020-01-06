package twilightforest.structures.courtyard;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import twilightforest.block.BlockTFNagastoneStairs;
import twilightforest.block.TFBlocks;
import twilightforest.structures.RandomizedTemplateProcessor;

import javax.annotation.Nullable;

public class CourtyardStairsTemplateProcessor extends RandomizedTemplateProcessor {
    public CourtyardStairsTemplateProcessor(BlockPos pos, PlacementSettings settings) {
        super(pos, settings);
    }

	@Nullable
	@Override
	public Template.BlockInfo process(IWorldReader worldReaderIn, BlockPos pos, Template.BlockInfo p_215194_3_, Template.BlockInfo blockInfo, PlacementSettings placementSettingsIn, @Nullable Template template) {
		if (shouldPlaceBlock()) {
			BlockState state = blockInfo.state;
			Block block = state.getBlock();

			if (block == TFBlocks.nagastone_stairs.get())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_stairs_mossy.get(), TFBlocks.nagastone_stairs_weathered.get()), BlockTFNagastoneStairs.DIRECTION, BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);

			return blockInfo;
		}

		return null;
	}
}