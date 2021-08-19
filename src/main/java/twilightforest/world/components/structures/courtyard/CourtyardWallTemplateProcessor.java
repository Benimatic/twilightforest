package twilightforest.world.components.structures.courtyard;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import twilightforest.block.TFBlocks;
import twilightforest.world.components.processors.RandomizedTemplateProcessor;
import twilightforest.world.registration.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CourtyardWallTemplateProcessor extends RandomizedTemplateProcessor {

	public static final Codec<CourtyardWallTemplateProcessor> codecWallProcessor = Codec.FLOAT.fieldOf("integrity").orElse(1.0F).xmap(CourtyardWallTemplateProcessor::new, (obj) -> obj.integrity).codec();

	public CourtyardWallTemplateProcessor(float integrity) {
        super(integrity);
    }

	@Override
	protected StructureProcessorType<?> getType() {
		return TFStructureProcessors.COURTYARD_WALL;
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader worldIn, BlockPos pos, BlockPos piecepos, StructureTemplate.StructureBlockInfo oldInfo, StructureTemplate.StructureBlockInfo newInfo, StructurePlaceSettings placementSettingsIn, @Nullable StructureTemplate template) {
		Random random = placementSettingsIn.getRandom(newInfo.pos);

		if (shouldPlaceBlock(random)) {
			BlockState state = newInfo.state;
			Block block = state.getBlock();

			if (state == Blocks.STONE_BRICKS.defaultBlockState())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, random.nextInt(2) == 0 ? Blocks.CRACKED_STONE_BRICKS.defaultBlockState() : Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), null);

			if (state == Blocks.SMOOTH_STONE_SLAB.defaultBlockState())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, Blocks.COBBLESTONE_SLAB, SlabBlock.TYPE), null);

			if (block == TFBlocks.etched_nagastone.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.etched_nagastone_mossy.get(), TFBlocks.etched_nagastone_weathered.get()), DirectionalBlock.FACING), null);

			if (block == TFBlocks.nagastone_pillar.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.nagastone_pillar_mossy.get(), TFBlocks.nagastone_pillar_weathered.get()), RotatedPillarBlock.AXIS), null);

			if (block == TFBlocks.nagastone_stairs_left.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_weathered_left.get()), StairBlock.FACING, StairBlock.HALF, StairBlock.SHAPE), null);

			if (block == TFBlocks.nagastone_stairs_right.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.nagastone_stairs_mossy_right.get(), TFBlocks.nagastone_stairs_weathered_right.get()), StairBlock.FACING, StairBlock.HALF, StairBlock.SHAPE), null);

			return newInfo;
		}

		return null;
	}
}
