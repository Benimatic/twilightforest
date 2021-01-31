package twilightforest.structures.start;

import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.ComponentTFHedgeMaze;
import twilightforest.structures.StructureTFComponentTemplate;
import twilightforest.structures.courtyard.ComponentNagaCourtyardMain;

import static twilightforest.TFFeature.HEDGE_MAZE;

public class StructureHedgeMaze extends Structure<NoFeatureConfig> {

	public static final ResourceLocation NAME = TwilightForestMod.prefix("hedgemaze");

	public StructureHedgeMaze(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public GenerationStage.Decoration getDecorationStage() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return Start::new;
	}

	@Override
	protected boolean func_230363_a_(ChunkGenerator generator, BiomeProvider provider, long seed, SharedSeedRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos structurePos, NoFeatureConfig config) {
		return TFFeature.isInFeatureChunk(chunkX << 4, chunkZ << 4) && TFFeature.generateFeature(chunkX, chunkZ, biome, seed) == TFFeature.HEDGE_MAZE;
	}

	public static class Start extends StructureStart<NoFeatureConfig> {

		public Start(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_, MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) {
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator p_230364_2_, TemplateManager p_230364_3_, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, NoFeatureConfig p_230364_7_) {
			int x = (p_230364_4_ << 4);
			int z = (p_230364_5_ << 4);
			int y = p_230364_2_.getHeight(p_230364_4_, p_230364_5_, Heightmap.Type.WORLD_SURFACE_WG);
			ComponentTFHedgeMaze start = new ComponentTFHedgeMaze(HEDGE_MAZE, rand, 0, x, y, z);
			components.add(start);
			start.buildComponent(start, components, rand);
			this.recalculateStructureSize();
			//this.func_214626_a(this.rand, 42, 42);
		}
	}
}