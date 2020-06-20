package twilightforest.structures.courtyard;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import twilightforest.block.BlockTFNagastoneStairs;
import twilightforest.block.TFBlocks;
import twilightforest.structures.RandomizedTemplateProcessor;
import twilightforest.structures.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.Random;

public class CourtyardStairsTemplateProcessor extends RandomizedTemplateProcessor {

    public CourtyardStairsTemplateProcessor(float integrity) {
        super(integrity);
    }

	public CourtyardStairsTemplateProcessor(Dynamic<?> dynamic) {
    	this(dynamic.get("integrity").asFloat(1.0F));
	}

	@Override
	protected IStructureProcessorType getType() {
		return TFStructureProcessors.COURTYARD_STAIRS;
	}

	@Nullable
	@Override
	public Template.BlockInfo process(IWorldReader worldReaderIn, BlockPos pos, Template.BlockInfo p_215194_3_, Template.BlockInfo blockInfo, PlacementSettings placementSettingsIn, @Nullable Template template) {
		Random random = placementSettingsIn.getRandom(pos);

    	if (shouldPlaceBlock(random)) {
			BlockState state = blockInfo.state;
			Block block = state.getBlock();

			if (block == TFBlocks.nagastone_stairs_left.get())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(random, TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_weathered_left.get()), BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);
			if (block == TFBlocks.nagastone_stairs_right.get())
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(random, TFBlocks.nagastone_stairs_mossy_right.get(), TFBlocks.nagastone_stairs_weathered_right.get()), BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);

			return blockInfo;
		}

		return null;
	}
}