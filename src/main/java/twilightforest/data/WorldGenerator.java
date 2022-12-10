package twilightforest.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import twilightforest.init.TFConfiguredFeatures;
import twilightforest.init.TFPlacedFeatures;
import twilightforest.init.TFStructureSets;
import twilightforest.init.TFStructures;

public class WorldGenerator extends DatapackBuiltinEntriesProvider {

	//biomes, carvers, noise settings, levelstem, dim type
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, TFConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, TFPlacedFeatures::bootstrap)
			.add(Registries.STRUCTURE, TFStructures::bootstrap)
			.add(Registries.STRUCTURE_SET, TFStructureSets::bootstrap);

	public WorldGenerator(PackOutput output) {
		super(output, BUILDER);
	}
}
