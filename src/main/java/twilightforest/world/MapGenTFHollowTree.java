package twilightforest.world;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import twilightforest.TFConfig;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomes;
import twilightforest.structures.hollowtree.StructureTFHollowTreeStart;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapGenTFHollowTree extends Structure<NoFeatureConfig> {

	//public static final int SPAWN_CHANCE = 48;

//	private static final List<Supplier<Supplier<Biome>>> oakSpawnBiomes = Arrays.asList(
//			() -> TFBiomes.twilightForest,
//			() -> TFBiomes.denseTwilightForest,
//			() -> TFBiomes.mushrooms,
//			() -> TFBiomes.tfSwamp,
//			() -> TFBiomes.clearing,
//			() -> TFBiomes.oakSavanna,
//			() -> TFBiomes.fireflyForest,
//			() -> TFBiomes.deepMushrooms,
//			() -> TFBiomes.enchantedForest,
//			() -> TFBiomes.fireSwamp
//	);

	public MapGenTFHollowTree(Function<Dynamic<?>, NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public String getStructureName() {
		return TwilightForestMod.ID + ":TFHollowTree";
	}

	@Override
	public int getSize() {
		return 3; //idk, most structures use this. Might be bigger.
	}

//	@Nullable
//	@Override
//	public BlockPos findNearest(World worldIn, ChunkGenerator generator, BlockPos pos, int p_211405_4_, boolean findUnexplored) {
//		this.world = worldIn;
//		return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
//	}

	@Override
	public boolean shouldStartAt(BiomeManager manager, ChunkGenerator generator, Random rand, int chunkX, int chunkZ, Biome biome) {
		return rand.nextInt(TFConfig.COMMON_CONFIG.PERFORMANCE.twilightOakChance.get()) == 0
				/*&& TFFeature.getNearestFeature(chunkX, chunkZ, world).areChunkDecorationsEnabled*/
				/*&& generator.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 0, getCurrentSpawnBiomes())*/;
	}

	@Override
	public IStartFactory getStartFactory() {
		return StructureTFHollowTreeStart::new;
	}

	//TODO: Handled via Biome Decorator
//	private static List<Biome> getCurrentSpawnBiomes() {
//		List<Biome> biomes = new ArrayList<>(oakSpawnBiomes.size());
//		for (Supplier<Supplier<Biome>> biomeSupplier : oakSpawnBiomes) {
//			biomes.add(biomeSupplier.get().get());
//		}
//		return biomes;
//	}
}
