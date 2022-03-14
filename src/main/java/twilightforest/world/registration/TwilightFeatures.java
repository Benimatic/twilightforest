package twilightforest.world.registration;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.feature.trees.treeplacers.*;
import twilightforest.world.components.placements.ChunkBlanketingModifier;
import twilightforest.world.components.placements.ChunkCenterModifier;
import twilightforest.world.components.placements.OutOfStructureFilter;

import java.util.List;

public final class TwilightFeatures {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, TwilightForestMod.ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, TwilightForestMod.ID);

    public static TrunkPlacerType<BranchingTrunkPlacer> TRUNK_BRANCHING;
    public static TrunkPlacerType<TrunkRiser> TRUNK_RISER;

    public static final RegistryObject<FoliagePlacerType<LeafSpheroidFoliagePlacer>> FOLIAGE_SPHEROID = FOLIAGE_PLACERS.register("spheroid_foliage_placer", () -> new FoliagePlacerType<>(LeafSpheroidFoliagePlacer.CODEC));

    public static final RegistryObject<TreeDecoratorType<TreeCorePlacer>> CORE_PLACER = TREE_DECORATORS.register("core_placer", () -> new TreeDecoratorType<>(TreeCorePlacer.CODEC));
    public static final RegistryObject<TreeDecoratorType<TrunkSideDecorator>> TRUNKSIDE_DECORATOR = TREE_DECORATORS.register("trunkside_decorator", () -> new TreeDecoratorType<>(TrunkSideDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<TreeRootsDecorator>> TREE_ROOTS = TREE_DECORATORS.register("tree_roots", () -> new TreeDecoratorType<>(TreeRootsDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<DangleFromTreeDecorator>> DANGLING_DECORATOR = TREE_DECORATORS.register("dangle_from_tree_decorator", () -> new TreeDecoratorType<>(DangleFromTreeDecorator.CODEC));

    public static PlacementModifierType<OutOfStructureFilter> NO_STRUCTURE;
    public static PlacementModifierType<ChunkCenterModifier> CHUNK_CENTERER;
    public static PlacementModifierType<ChunkBlanketingModifier> CHUNK_BLANKETING;

    private static <P extends PlacementModifier> PlacementModifierType<P> registerPlacer(String name, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, TwilightForestMod.prefix(name), () -> codec);
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(ResourceLocation name, Codec<P> codec) {
        // TRUNK_REPLACER is wrong, it only places, not replacing
        return Registry.register(Registry.TRUNK_PLACER_TYPES, name, new TrunkPlacerType<>(codec));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> registerWorldFeature(ResourceLocation rl, F feature, FC featureConfiguration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, rl.toString(), new ConfiguredFeature<>(feature, featureConfiguration));
    }

    public static Holder<PlacedFeature> registerWorldFeature(ResourceLocation rl, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.PLACED_FEATURE, rl.toString(), new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placements)));
    }

    public static void init() {
        TRUNK_BRANCHING = registerTrunk(TwilightForestMod.prefix("branching_trunk_placer"), BranchingTrunkPlacer.CODEC);
        TRUNK_RISER = registerTrunk(TwilightForestMod.prefix("trunk_mover_upper"), TrunkRiser.CODEC);
        NO_STRUCTURE = registerPlacer("no_structure", OutOfStructureFilter.CODEC);
        CHUNK_CENTERER = registerPlacer("chunk_centerer", ChunkCenterModifier.CODEC);
        CHUNK_BLANKETING = registerPlacer("chunk_blanketing", ChunkBlanketingModifier.CODEC);
    }
}
