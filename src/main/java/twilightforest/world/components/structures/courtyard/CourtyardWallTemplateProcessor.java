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
				return (random.nextBoolean() ? newInfo : random.nextBoolean() ? new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, Blocks.COBBLESTONE_SLAB, SlabBlock.TYPE), null) : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, Blocks.MOSSY_COBBLESTONE_SLAB, SlabBlock.TYPE), null));

			if (block == TFBlocks.ETCHED_NAGASTONE.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.MOSSY_ETCHED_NAGASTONE.get(), TFBlocks.CRACKED_ETCHED_NAGASTONE.get()), DirectionalBlock.FACING), null);

			if (block == TFBlocks.NAGASTONE_PILLAR.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.MOSSY_NAGASTONE_PILLAR.get(), TFBlocks.CRACKED_NAGASTONE_PILLAR.get()), RotatedPillarBlock.AXIS), null);

			if (block == TFBlocks.NAGASTONE_STAIRS_LEFT.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get(), TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get()), StairBlock.FACING, StairBlock.HALF, StairBlock.SHAPE), null);

			if (block == TFBlocks.NAGASTONE_STAIRS_RIGHT.get())
				return random.nextBoolean() ? newInfo : new StructureTemplate.StructureBlockInfo(newInfo.pos, translateState(state, randomBlock(random, TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get(), TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get()), StairBlock.FACING, StairBlock.HALF, StairBlock.SHAPE), null);

			return newInfo;
		}

		return null;
	}
}
