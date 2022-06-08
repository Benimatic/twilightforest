package twilightforest.world.components.structures.start;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.structures.TFStructureComponent;

import java.util.List;

public class TFStructureStart<C extends FeatureConfiguration> extends StructureStart {
	private boolean conquered = false;

	public TFStructureStart(Structure structure, ChunkPos chunkPos, int references, PiecesContainer pieces) {
		super(structure, chunkPos, references, pieces);
	}

	@Override
	public CompoundTag createTag(StructurePieceSerializationContext level, ChunkPos chunkPos) {
		CompoundTag tag = super.createTag(level, chunkPos);
		if (this.isValid())
			tag.putBoolean("conquered", this.conquered);
		return tag;
	}

	public void load(CompoundTag nbt) {
		this.conquered = nbt.getBoolean("conquered");
	}

	public final void setConquered(boolean flag) {
		this.conquered = flag;
	}

	public final boolean isConquered() {
		return this.conquered;
	}

	private static int getSpawnListIndexAt(StructureStart start, BlockPos pos) {
		int highestFoundIndex = -1;
		for (StructurePiece component : start.getPieces()) {
			if (component.getBoundingBox().isInside(pos)) {
				if (component instanceof TFStructureComponent tfComponent) {
					if (tfComponent.spawnListIndex > highestFoundIndex)
						highestFoundIndex = tfComponent.spawnListIndex;
				} else
					return 0;
			}
		}
		return highestFoundIndex;
	}

	public static List<MobSpawnSettings.SpawnerData> gatherPotentialSpawns(StructureManager structureManager, MobCategory classification, BlockPos pos) {
		for (Structure structure : structureManager.registryAccess().ownedRegistryOrThrow(Registry.STRUCTURE_REGISTRY).stream()
				.filter(feature -> {
					ResourceLocation location = feature.feature.getRegistryName();
					return location != null && TwilightForestMod.ID.equals(location.getNamespace());
				}).toList()) {
			StructureStart start = structureManager.getStructureAt(pos, structure);
			if (!start.isValid())
				continue;

			if (!(structure instanceof LegacyStructureFeature legacyData)) continue;

			if (classification != MobCategory.MONSTER)
				return legacyData.feature.getSpawnableList(classification);
			if (start instanceof TFStructureStart<?> s && s.conquered)
				return null;
			final int index = getSpawnListIndexAt(start, pos);
			if (index < 0)
				return null;
			return legacyData.feature.getSpawnableMonsterList(index);
		}
		return null;
	}
}