package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;
import twilightforest.util.FeaturePlacers;
import twilightforest.world.components.processors.StoneBricksTemplateProcessor;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class QuestGrove extends TwilightTemplateStructurePiece {
	public QuestGrove(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.TFQuestGrove, compoundTag, serverLevel, readSettings(compoundTag).addProcessor(StoneBricksTemplateProcessor.INSTANCE));
	}

	public QuestGrove(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.TFQuestGrove, 0, structureManager, TwilightForestMod.prefix("quest_grove"), makeSettings(Rotation.NONE).addProcessor(StoneBricksTemplateProcessor.INSTANCE), templatePosition);
	}

	@Override
	public boolean postProcess(WorldGenLevel level, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
		return this.placePieceAdjusted(level, structureFeatureManager, chunkGenerator, random, boundingBox, chunkPos, pos, -2);
	}

	@Override
	protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor levelAccessor, Random random, BoundingBox boundingBox) {
		if (!boundingBox.isInside(pos)) return;

		if ("quest_ram".equals(name)) {
			FeaturePlacers.placeEntity(TFEntities.QUEST_RAM, pos, levelAccessor);
		} else if ("dispenser".equals(name)) {
			TFTreasure.QUEST_GROVE.generateLootContainer(levelAccessor, pos, Blocks.DROPPER.defaultBlockState().setValue(DispenserBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)), 16 | 4 | 2, random.nextLong());
		}
	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}
}
