package twilightforest.util;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.StructureTagGenerator;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.world.components.structures.start.TFStructureStart;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("OptionalIsPresent")
public final class LandmarkUtil {
    public static Optional<StructureStart> locateNearestLandmarkStart(LevelAccessor level, int chunkX, int chunkZ) {
        return locateNearestMatchingLandmark(level, StructureTagGenerator.LANDMARK, chunkX, chunkZ);
    }

    public static Optional<StructureStart> locateNearestMatchingLandmark(LevelAccessor level, TagKey<Structure> matching, int chunkX, int chunkZ) {
        var structureRegistry = level.registryAccess().registry(Registries.STRUCTURE);
        if (structureRegistry.isEmpty()) return Optional.empty();
        var holders = structureRegistry.get().getTag(matching);
        if (holders.isEmpty()) return Optional.empty();

        return locateNearestMatchingLandmark(level, holders.get(), chunkX, chunkZ);
    }

    public static Optional<StructureStart> locateNearestMatchingLandmark(LevelAccessor level, HolderSet<Structure> matching, int chunkX, int chunkZ) {
        Set<Structure> structures = matching.stream().map(Holder::get).collect(Collectors.toSet());
        return locateNearestMatchingLandmark(level, structures::contains, chunkX, chunkZ);
    }

    public static Optional<StructureStart> locateNearestMatchingLandmark(LevelAccessor level, Predicate<Structure> filter, int chunkX, int chunkZ) {
        BlockPos nearestFeature = LegacyLandmarkPlacements.getNearestCenterXZ(chunkX, chunkZ);
        int centerX = SectionPos.blockToSectionCoord(nearestFeature.getX());
        int centerZ =  SectionPos.blockToSectionCoord(nearestFeature.getZ());

        if (!level.hasChunk(centerX, centerZ)) return Optional.empty();

        ChunkAccess chunkAccess = level.getChunk(centerX, centerZ, ChunkStatus.STRUCTURE_STARTS);

        for (Map.Entry<Structure, StructureStart> structureEntry : chunkAccess.getAllStarts().entrySet())
            if (filter.test(structureEntry.getKey()))
                return Optional.of(structureEntry.getValue());

        return Optional.empty();
    }

    public static void markStructureConquered(LevelAccessor level, EnforcedHomePoint mobHome, ResourceKey<Structure> structureKey, boolean conquered) {
        // FIXME If a boss travels to a different level, this won't take effect. Add Home dimension to EnforcedHomePoint?
        markStructureConquered(level, mobHome.getRestrictionCenter(), structureKey, conquered);
    }

    public static void markStructureConquered(LevelAccessor level, BlockPos pos, ResourceKey<Structure> structureKey, boolean conquered) {
        Optional<StructureStart> nearStart = locateNearestLandmarkStart(level, structureKey, pos);

        if (nearStart.isEmpty() || !(nearStart.get() instanceof TFStructureStart twilightStart)) return;

        twilightStart.setConquered(conquered, level);
    }

    @Nullable
    public static Structure structureForKey(LevelReader level, ResourceKey<Structure> structureKey) {
        Optional<Registry<Structure>> registry = level.registryAccess().registry(Registries.STRUCTURE);

        return registry.isPresent() ? registry.get().get(structureKey) : null;
    }

    public static Optional<StructureStart> locateNearestLandmarkStart(LevelAccessor level, ResourceKey<Structure> structureKey, BlockPos pos) {
        return locateNearestLandmarkStart(level, structureKey, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
    }

    public static Optional<StructureStart> locateNearestLandmarkStart(LevelAccessor level, ResourceKey<Structure> structureKey, int chunkX, int chunkZ) {
        Structure structure = structureForKey(level, structureKey);

        if (structure == null) return Optional.empty();

        return locateNearestLandmarkStart(level, structure, chunkX, chunkZ);
    }

    public static Optional<StructureStart> locateNearestLandmarkStart(LevelAccessor level, Structure structure, int chunkX, int chunkZ) {
        BlockPos nearestLandmark = LegacyLandmarkPlacements.getNearestCenterXZ(chunkX, chunkZ);
        ChunkAccess chunkAccess = level.getChunk(SectionPos.blockToSectionCoord(nearestLandmark.getX()), SectionPos.blockToSectionCoord(nearestLandmark.getZ()), ChunkStatus.STRUCTURE_STARTS);

        for (Long packedChunkPos : chunkAccess.getReferencesForStructure(structure)) {
            int packedX = ChunkPos.getX(packedChunkPos);
            int packedZ = ChunkPos.getZ(packedChunkPos);

            if (level.hasChunk(packedX, packedZ)) {
                StructureStart structureStart = level.getChunk(packedX, packedZ, ChunkStatus.STRUCTURE_STARTS).getStartForStructure(structure);

                if (structureStart != null && structureStart.isValid()) return Optional.of(structureStart);
            }
        }

        return Optional.empty();
    }

    public static boolean isProgressionEnforced(Level world) {
        return world.getGameRules().getBoolean(TwilightForestMod.ENFORCED_PROGRESSION_RULE);
    }

    private LandmarkUtil () {}
}
