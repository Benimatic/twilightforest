package twilightforest.world.components.layer;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.TFBiomes;
import twilightforest.world.components.layer.vanillalegacy.context.Context;
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer0;

import java.util.List;

/**
 * Applies the twilight forest biomes to the map
 *
 * @author Ben
 */
public enum GenLayerTFBiomes implements AreaTransformer0 {
	INSTANCE(15, ImmutableList.of(
			TFBiomes.FOREST,
			TFBiomes.DENSE_FOREST,
			TFBiomes.MUSHROOM_FOREST,
			TFBiomes.OAK_SAVANNAH,
			TFBiomes.FIREFLY_FOREST
	), ImmutableList.of(
			TFBiomes.LAKE,
			TFBiomes.DENSE_MUSHROOM_FOREST,
			TFBiomes.ENCHANTED_FOREST,
			TFBiomes.CLEARING,
			TFBiomes.SPOOKY_FOREST
	));

	private final int rareBiomeChance;

	private final List<ResourceKey<Biome>> commonBiomes;
	private final List<ResourceKey<Biome>> rareBiomes;

	GenLayerTFBiomes(int rareBiomeChance, List<ResourceKey<Biome>> commonBiomes, List<ResourceKey<Biome>> rareBiomes) {
		this.rareBiomeChance = rareBiomeChance;
		this.commonBiomes = commonBiomes;
		this.rareBiomes = rareBiomes;
	}

	@Override
	public ResourceKey<Biome> applyPixel(Context context, int x, int z) {
		if (context.nextRandom(rareBiomeChance) == 0) {
			// make specialBiomes biome
			return rareBiomes.get(context.nextRandom(rareBiomes.size()));
		} else {
			// make common biome
			return commonBiomes.get(context.nextRandom(commonBiomes.size()));
		}
	}
}
