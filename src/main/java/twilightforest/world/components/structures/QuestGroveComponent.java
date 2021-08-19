package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import twilightforest.world.registration.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;
import twilightforest.entity.passive.QuestRamEntity;
import twilightforest.util.ColorUtil;
import twilightforest.world.components.processors.StoneBricksTemplateProcessor;

import java.util.Random;

// FIXME Terrain Deformation
public class QuestGroveComponent extends TemplateStructurePiece implements TwilightFeature {
	public QuestGroveComponent(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.TFQuestGrove, compoundTag, serverLevel, rl -> makeSettings(Rotation.valueOf(compoundTag.getString("Rot"))));
	}

	public QuestGroveComponent(StructureManager structureManager, Random random, BlockPos templatePosition) {
		this(structureManager, TwilightForestMod.prefix("quest_grove"), makeSettings(random), templatePosition);
	}

	public QuestGroveComponent(StructureManager structureManager, ResourceLocation templateLocation, StructurePlaceSettings structurePlaceSettings, BlockPos templatePosition) {
		super(TFFeature.TFQuestGrove, 0, structureManager, templateLocation, templateLocation.toString(), structurePlaceSettings, templatePosition);
	}

	public static StructurePlaceSettings makeSettings(Random random) {
		return makeSettings(Rotation.getRandom(random));
	}

	public static StructurePlaceSettings makeSettings(Rotation rotation) {
		return new StructurePlaceSettings().setRotation(rotation).addProcessor(StoneBricksTemplateProcessor.INSTANCE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
	}

	@Override
	public TFFeature getFeatureType() {
		return TFFeature.QUEST_GROVE;
	}

	@Override
	protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor levelAccessor, Random random, BoundingBox boundingBox) {
		if (!boundingBox.isInside(pos)) return;

		if ("quest_ram".equals(name)) {
			QuestRamEntity ram = TFEntities.quest_ram.create(levelAccessor.getLevel());

			if (ram == null) return;

			ram.setPersistenceRequired();
			ram.moveTo(pos, 0.0F, 0.0F);
			ram.finalizeSpawn(levelAccessor, levelAccessor.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null ,null);
			levelAccessor.addFreshEntityWithPassengers(ram);
			levelAccessor.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
		} else if ("dispenser".equals(name)) {
			BlockState dropperState = Blocks.DROPPER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.NORTH).rotate(this.placeSettings.getRotation());
			levelAccessor.setBlock(pos, dropperState, 22);

			// FIXME Use loot table
			BlockEntity tile = levelAccessor.getBlockEntity(pos);

			if (tile instanceof DropperBlockEntity dropperTile) {
				// add 4 random wool blocks
				for (int i = 0; i < 4; i++) {
					dropperTile.setItem(i, new ItemStack(ColorUtil.WOOL.getRandomColor(random), 1));
				}
			}
		}
	}
}
