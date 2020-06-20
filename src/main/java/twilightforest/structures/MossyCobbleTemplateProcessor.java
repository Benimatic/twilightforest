package twilightforest.structures;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.Random;

public class MossyCobbleTemplateProcessor extends RandomizedTemplateProcessor {

    public MossyCobbleTemplateProcessor(float integrity) {
        super(integrity);
    }

	public MossyCobbleTemplateProcessor(Dynamic<?> dynamic) {
    	this(dynamic.get("integrity").asFloat(1.0F));
	}

	@Override
	protected IStructureProcessorType getType() {
		return TFStructureProcessors.MOSSY_COBBLE;
	}

	@Nullable
    @Override
    public Template.BlockInfo process(IWorldReader worldReaderIn, BlockPos pos, Template.BlockInfo p_215194_3_, Template.BlockInfo blockInfo, PlacementSettings placementSettingsIn, @Nullable Template template) {
		Random random = placementSettingsIn.getRandom(pos);

    	if (shouldPlaceBlock(random)) {
			BlockState state = blockInfo.state;
			Block block = state.getBlock();

			if (block == Blocks.COBBLESTONE)
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), null);

			if (block == Blocks.COBBLESTONE_WALL)
				return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, Blocks.MOSSY_COBBLESTONE_WALL.getDefaultState(), null);

			return blockInfo;
		}

		return null;
    }
}