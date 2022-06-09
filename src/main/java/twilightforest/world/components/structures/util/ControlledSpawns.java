package twilightforest.world.components.structures.util;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.List;

// FIXME Using IDs to enumerate lists of mob spawn tables is a bad idea...
public interface ControlledSpawns {
    List<MobSpawnSettings.SpawnerData> getCombinedMonsterSpawnableList();

    List<MobSpawnSettings.SpawnerData> getCombinedCreatureSpawnableList();

    /**
     * Returns a list of hostile monsters.  Are we ever going to need passive or water creatures?
     */
    List<MobSpawnSettings.SpawnerData> getSpawnableList(MobCategory creatureType);

    /**
     * Returns a list of hostile monsters in the specified indexed category
     */
    List<MobSpawnSettings.SpawnerData> getSpawnableMonsterList(int index);
}
