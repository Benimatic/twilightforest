package twilightforest.world.components.structures.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import twilightforest.world.components.structures.TwilightFeature;

import java.util.List;

public interface LegacyStructureAdapter extends LandmarkStructure, TwilightFeature {
    // TODO bridge getFeatureType to the interface methods

    @Override
    default ItemStack createHintBook() {
        return this.getFeatureType().createHintBook();
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
}
