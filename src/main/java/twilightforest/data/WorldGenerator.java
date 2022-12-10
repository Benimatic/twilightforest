package twilightforest.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import twilightforest.init.TFConfiguredFeatures;
import twilightforest.init.TFPlacedFeatures;

public class WorldGenerator extends DatapackBuiltinEntriesProvider {

	//biomes, carvers, structure sets, structures, noise settings, levelstem, dim type
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, TFConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, TFPlacedFeatures::bootstrap);

	public WorldGenerator(PackOutput output) {
		super(output, BUILDER);
	}
}
