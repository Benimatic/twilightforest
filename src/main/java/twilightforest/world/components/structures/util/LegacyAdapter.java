package twilightforest.world.components.structures.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import twilightforest.world.components.structures.LegacyLandmarkGetter;

import java.util.List;

public interface LegacyAdapter extends AdvancementLockedStructure, StructureHints, DecorationClearance, ControlledSpawns, LegacyLandmarkGetter {
    @Override
    default ItemStack createHintBook() {
        return this.getFeatureType().createHintBook();
    }

    @Override
    default void addBookInformation(ItemStack book, ListTag bookPages) {
        this.getFeatureType().addBookInformation(book, bookPages);
    }

    @Override
    default void trySpawnHintMonster(Level world, Player player, BlockPos pos) {
        this.getFeatureType().trySpawnHintMonster(world, player, pos);
    }

    @Override
    default boolean isSurfaceDecorationsAllowed() {
        return this.getFeatureType().isSurfaceDecorationsAllowed();
    }

    @Override
    default boolean isUndergroundDecoAllowed() {
        return this.getFeatureType().isUndergroundDecoAllowed();
    }

    @Override
    default boolean shouldAdjustToTerrain() {
        return this.getFeatureType().shouldAdjustToTerrain();
    }

    @Override
    default List<ResourceLocation> getRequiredAdvancements() {
        return this.getFeatureType().getRequiredAdvancements();
    }

    @Override
    default List<MobSpawnSettings.SpawnerData> getCombinedMonsterSpawnableList() {
        return this.getFeatureType().getCombinedMonsterSpawnableList();
    }

    @Override
    default List<MobSpawnSettings.SpawnerData> getCombinedCreatureSpawnableList() {
        return this.getFeatureType().getCombinedCreatureSpawnableList();
    }

    @Override
    default List<MobSpawnSettings.SpawnerData> getSpawnableList(MobCategory creatureType) {
        return this.getFeatureType().getSpawnableList(creatureType);
    }

    @Override
    default List<MobSpawnSettings.SpawnerData> getSpawnableMonsterList(int index) {
        return this.getFeatureType().getSpawnableMonsterList(index);
    }
}
