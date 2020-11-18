package twilightforest.biomes;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import twilightforest.TwilightForestMod;

public class TFBiomes {

	public static final RegistryKey<Biome> tfLake = makeKey("lake");
	public static final RegistryKey<Biome> twilightForest = makeKey("forest");
	public static final RegistryKey<Biome> denseTwilightForest = makeKey("dense_forest");
	public static final RegistryKey<Biome> highlands = makeKey("highlands");
	public static final RegistryKey<Biome> mushrooms = makeKey("mushroom_forest");
	public static final RegistryKey<Biome> tfSwamp = makeKey("swamp");
	public static final RegistryKey<Biome> stream = makeKey("stream");
	public static final RegistryKey<Biome> snowy_forest = makeKey("snowy_forest");
	public static final RegistryKey<Biome> glacier = makeKey("glacier");
	public static final RegistryKey<Biome> clearing = makeKey("clearing");
	public static final RegistryKey<Biome> oakSavanna = makeKey("oak_savannah");
	public static final RegistryKey<Biome> fireflyForest = makeKey("firefly_forest");
	public static final RegistryKey<Biome> deepMushrooms = makeKey("dense_mushroom_forest");
	public static final RegistryKey<Biome> darkForest = makeKey("dark_forest");
	public static final RegistryKey<Biome> enchantedForest = makeKey("enchanted_forest");
	public static final RegistryKey<Biome> fireSwamp = makeKey("fire_swamp");
	public static final RegistryKey<Biome> darkForestCenter = makeKey("dark_forest_center");
	public static final RegistryKey<Biome> finalPlateau = makeKey("final_plateau");
	public static final RegistryKey<Biome> thornlands = makeKey("thornlands");
	public static final RegistryKey<Biome> spookyForest = makeKey("spooky_forest");

	private static RegistryKey<Biome> makeKey(String name) {
		return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, TwilightForestMod.prefix(name));
	}

	public static final BiomeDictionary.Type TWILIGHT = BiomeDictionary.Type.getType("TWILIGHT");

	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(tfLake, TWILIGHT, BiomeDictionary.Type.OCEAN);
		BiomeDictionary.addTypes(twilightForest, TWILIGHT, BiomeDictionary.Type.FOREST);
		BiomeDictionary.addTypes(denseTwilightForest, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE);
		BiomeDictionary.addTypes(highlands, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(mushrooms, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MUSHROOM);
		BiomeDictionary.addTypes(tfSwamp, TWILIGHT, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET);
		BiomeDictionary.addTypes(stream, TWILIGHT, BiomeDictionary.Type.RIVER);
		BiomeDictionary.addTypes(snowy_forest, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD, BiomeDictionary.Type.CONIFEROUS);
		BiomeDictionary.addTypes(glacier, TWILIGHT, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(clearing, TWILIGHT, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPARSE);
		BiomeDictionary.addTypes(oakSavanna, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPARSE);
		BiomeDictionary.addTypes(fireflyForest, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.LUSH);
		BiomeDictionary.addTypes(deepMushrooms, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MUSHROOM);
		BiomeDictionary.addTypes(darkForest, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.SPOOKY);
		BiomeDictionary.addTypes(enchantedForest, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL);
		BiomeDictionary.addTypes(fireSwamp, TWILIGHT, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.HOT);
		BiomeDictionary.addTypes(darkForestCenter, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.MAGICAL);
		BiomeDictionary.addTypes(finalPlateau, TWILIGHT, BiomeDictionary.Type.MESA, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(thornlands, TWILIGHT, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(spookyForest, TWILIGHT, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.SPOOKY);
	}
}
