package twilightforest.world.registration.surface_rules;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import twilightforest.block.TFBlocks;
import twilightforest.world.registration.biomes.BiomeKeys;

public class TFSurfaceRules {

	private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
	private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
	private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
	private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
	private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
	private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
	private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
	private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
	private static final SurfaceRules.RuleSource SNOW = makeStateRule(Blocks.SNOW_BLOCK);
	private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
	private static final SurfaceRules.RuleSource LAVA = makeStateRule(Blocks.LAVA);
	private static final SurfaceRules.RuleSource WEATHERED_DEADROCK = makeStateRule(TFBlocks.WEATHERED_DEADROCK.get());
	private static final SurfaceRules.RuleSource CRACKED_DEADROCK = makeStateRule(TFBlocks.CRACKED_DEADROCK.get());
	private static final SurfaceRules.RuleSource DEADROCK = makeStateRule(TFBlocks.DEADROCK.get());

	private static SurfaceRules.RuleSource makeStateRule(Block block) {
		return SurfaceRules.state(block.defaultBlockState());
	}

	public static SurfaceRules.RuleSource basicOverworldGen() {
		SurfaceRules.ConditionSource y62 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
		SurfaceRules.ConditionSource y63 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
		SurfaceRules.ConditionSource checkWaterBelow = SurfaceRules.waterBlockCheck(-1, 0);
		SurfaceRules.ConditionSource checkWaterAt = SurfaceRules.waterBlockCheck(0, 0);
		SurfaceRules.ConditionSource checkWater6Below = SurfaceRules.waterStartCheck(-6, -1);
		SurfaceRules.ConditionSource isHole = SurfaceRules.hole();
		SurfaceRules.RuleSource sandPlacer = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
		SurfaceRules.RuleSource gravelPlacer = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);

		SurfaceRules.RuleSource overworldLike = SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.isBiome(BiomeKeys.SWAMP),
										SurfaceRules.ifTrue(y62,
												SurfaceRules.ifTrue(
														SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D), WATER))),
								SurfaceRules.ifTrue(
										SurfaceRules.isBiome(BiomeKeys.FIRE_SWAMP),
										SurfaceRules.ifTrue(y62,
												SurfaceRules.ifTrue(
														SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D), LAVA))))),
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
						SurfaceRules.ifTrue(checkWaterBelow,
								SurfaceRules.ifTrue(isHole,
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(checkWaterAt, AIR),
												SurfaceRules.ifTrue(
														SurfaceRules.temperature(), ICE), WATER)))),
				SurfaceRules.ifTrue(checkWater6Below,
						SurfaceRules.ifTrue(
								SurfaceRules.stoneDepthCheck(0, true, true, CaveSurface.FLOOR), SANDSTONE)),
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.isBiome(BiomeKeys.LAKE, BiomeKeys.STREAM), sandPlacer), gravelPlacer)));

		ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
		builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
		SurfaceRules.RuleSource surfacerules$rulesource9 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), overworldLike);
		builder.add(surfacerules$rulesource9);
		return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
	}

	public static SurfaceRules.RuleSource tfSurface() {
		//surface is a normal overworld surface as the base
		//snowy forest is all snow on the top layers
		//glacier has 1 ice layer, 30 packed ice layers, gravel for a few layers, then stone
		//highlands has a noise-based mixture of podzol and coarse dirt
		//thornlands/plateau has no caves and deadrock instead of stone

		SurfaceRules.RuleSource highlandsNoise = SurfaceRules.sequence(
				//check if we're in the highlands
				SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeKeys.HIGHLANDS),
						SurfaceRules.ifTrue(
								//check if we're on the surface
								SurfaceRules.abovePreliminarySurface(),
								SurfaceRules.sequence(
										//mix coarse dirt and podzol like we used to
										SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), COARSE_DIRT),
										SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95D), PODZOL)))));

		SurfaceRules.RuleSource deadrockLands = SurfaceRules.sequence(
				SurfaceRules.ifTrue(
						//check if we're in the deadrock biomes
						SurfaceRules.isBiome(BiomeKeys.THORNLANDS, BiomeKeys.FINAL_PLATEAU),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), WEATHERED_DEADROCK),
								SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.bottom(), -1), DEADROCK))));

		SurfaceRules.RuleSource snowyForest = SurfaceRules.sequence(
				SurfaceRules.ifTrue(
						//check if we're in the snowy forest
						SurfaceRules.isBiome(BiomeKeys.SNOWY_FOREST),
						SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SNOW)));

		SurfaceRules.RuleSource glacier = SurfaceRules.sequence(
				SurfaceRules.ifTrue(
						//check if we're in the snowy forest
						SurfaceRules.isBiome(BiomeKeys.SNOWY_FOREST),
						SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SNOW)));


		ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
		builder
				//overworld generation
				.add(basicOverworldGen())
				.add(highlandsNoise)
				.add(deadrockLands)
				.add(snowyForest)
				.add();
		return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
	}

	private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
		return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
	}
}
