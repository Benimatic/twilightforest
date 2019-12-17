package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.BlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.enums.CastleBrickVariant;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.enums.WoodVariant;
import twilightforest.item.TFItems;
import twilightforest.util.IMapColorSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public final class RegisterBlockEvent {
	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		BlockRegistryHelper blocks = new BlockRegistryHelper(event.getRegistry());

		blocks.register("firefly", "Firefly", new BlockTFFirefly());
		blocks.register("cicada", "Cicada", new BlockTFCicada());
		blocks.register("twilight_portal", "Portal", new BlockTFPortal());
		blocks.register("hedge", "Hedge", new BlockTFHedge());
		blocks.register("firefly_jar", "FireflyJar", new BlockTFFireflyJar());
		blocks.register("uncrafting_table", "UncraftingTable", new BlockTFUncraftingTable());
		blocks.register("fire_jet", "FireJet", new BlockTFFireJet());
		blocks.register("naga_stone", "Nagastone", new BlockTFNagastone());
		blocks.register("moonworm", "Moonworm", new BlockTFMoonworm());
		blocks.register("magic_log_core", "MagicLogSpecial", new BlockTFMagicLogSpecial());
		blocks.register("tower_device", "TowerDevice", new BlockTFTowerDevice());
		blocks.register("tower_translucent", "TowerTranslucent", new BlockTFTowerTranslucent());
		blocks.register("trophy", "Trophy", new BlockTFTrophy());
		blocks.register("stronghold_shield", "Shield", new BlockTFShield());
		blocks.register("trophy_pedestal", "TrophyPedestal", new BlockTFTrophyPedestal());
		blocks.register("aurora_block", "AuroraBrick", new BlockTFAuroraBrick());
		blocks.register("thorns", "Thorns", new BlockTFThorns());
		blocks.register("burnt_thorns", "BurntThorns", new BlockTFBurntThorns());
		blocks.register("thorn_rose", "ThornRose", new BlockTFThornRose());
		blocks.register("dark_leaves", "DarkLeaves", new BlockTFDarkLeaves());
		blocks.register("aurora_pillar", "AuroraPillar", new BlockTFPillar(Material.PACKED_ICE).setHardness(2.0F).setResistance(10.0F));
		blocks.register("trollsteinn", "TrollSteinn", new BlockTFTrollSteinn());
		blocks.register("wispy_cloud", "WispyCloud", new BlockTFWispyCloud());
		blocks.register("fluffy_cloud", "FluffyCloud", new BlockTFFluffyCloud());
		blocks.register("giant_cobblestone", "GiantCobble", new BlockTFGiantCobble());
		blocks.register("giant_log", "GiantLog", new BlockTFGiantLog());
		blocks.register("giant_leaves", "GiantLeaves", new BlockTFGiantLeaves());
		blocks.register("giant_obsidian", "GiantObsidian", new BlockTFGiantObsidian());
		blocks.register("uberous_soil", "UberousSoil", new BlockTFUberousSoil());
		blocks.register("huge_stalk", "HugeStalk", new BlockTFHugeStalk());
		blocks.register("huge_mushgloom", "HugeGloomBlock", new BlockTFHugeGloomBlock());
		blocks.register("trollvidr", "TrollVidr", new BlockTFTrollRoot());
		blocks.register("unripe_trollber", "UnripeTrollBer", new BlockTFUnripeTorchCluster());
		blocks.register("trollber", "TrollBer", new BlockTFRipeTorchCluster());
		blocks.register("knightmetal_block", "KnightmetalBlock", new BlockTFKnightmetalBlock());
		blocks.register("huge_lilypad", "HugeLilyPad", new BlockTFHugeLilyPad());
		blocks.register("huge_waterlily", "HugeWaterLily", new BlockTFHugeWaterLily());
		blocks.register("slider", "Slider", new BlockTFSlider());
		Block castleBrick = new BlockTFCastleBlock();
		BlockState castleState = castleBrick.getDefaultState();
		blocks.register("castle_stairs_brick", "CastleStairsBrick", new BlockTFStairs(castleState));
		blocks.register("castle_stairs_worn", "CastleStairsWorn", new BlockTFStairs(castleState.with(BlockTFCastleBlock.VARIANT, CastleBrickVariant.WORN)));
		blocks.register("castle_stairs_cracked", "CastleStairsCracked", new BlockTFStairs(castleState.with(BlockTFCastleBlock.VARIANT, CastleBrickVariant.CRACKED)));
		blocks.register("castle_stairs_mossy", "CastleStairsMossy", new BlockTFStairs(castleState.with(BlockTFCastleBlock.VARIANT, CastleBrickVariant.MOSSY)));
		blocks.register("castle_rune_brick", "CastleMagic", new BlockTFCastleMagic());
		blocks.register("force_field", "ForceField", new BlockTFForceField());
		blocks.register("cinder_furnace", "CinderFurnaceIdle", new BlockTFCinderFurnace(false));
		blocks.register("cinder_furnace_lit", "CinderFurnaceLit", new BlockTFCinderFurnace(true));
		blocks.register("cinder_log", "CinderLog", new BlockTFCinderLog());
		blocks.register("castle_door", "CastleDoor", new BlockTFCastleDoor(false));
		blocks.register("castle_door_vanished", "CastleDoorVanished", new BlockTFCastleDoor(true));
		blocks.register("castle_unlock", "CastleUnlock", new BlockTFCastleUnlock());
		blocks.register("experiment_115", "experiment115", new BlockTFExperiment115().setHardness(0.5F));
		blocks.register("miniature_structure", "MiniatureStructure", new BlockTFMiniatureStructure().setHardness(0.75F));
		blocks.register("lapis_block", "BlockOfLapisTF", new BlockTFLapisBlock());
		blocks.register("spiral_bricks", "SpiralBricks", new BlockTFSpiralBrick());
		Block etchedNagastone = new BlockTFNagastoneEtched().setHardness(1.5F).setResistance(10.0F);
		blocks.register("etched_nagastone", "EtchedNagastone", etchedNagastone);
		blocks.register("nagastone_stairs", "NagastoneStairs", new BlockTFNagastoneStairs(etchedNagastone.getDefaultState()).setHardness(1.5F).setResistance(10.0F));
		blocks.register("nagastone_pillar", "NagastonePillar", new BlockTFNagastonePillar().setHardness(1.5F).setResistance(10.0F));
		Block etchedNagastoneMossy = new BlockTFNagastoneEtched().setHardness(1.5F).setResistance(10.0F);
		blocks.register("etched_nagastone_mossy", "EtchedNagastoneMossy", etchedNagastoneMossy);
		blocks.register("nagastone_stairs_mossy", "NagastoneStairsMossy", new BlockTFNagastoneStairs(etchedNagastoneMossy.getDefaultState()).setHardness(1.5F).setResistance(10.0F));
		blocks.register("nagastone_pillar_mossy", "NagastonePillarMossy", new BlockTFNagastonePillar().setHardness(1.5F).setResistance(10.0F));
		Block etchedNagastoneWeathered = new BlockTFNagastoneEtched().setHardness(1.5F).setResistance(10.0F);
		blocks.register("etched_nagastone_weathered", "EtchedNagastoneWeathered", etchedNagastoneWeathered);
		blocks.register("nagastone_stairs_weathered", "NagastoneStairsWeathered", new BlockTFNagastoneStairs(etchedNagastoneWeathered.getDefaultState()).setHardness(1.5F).setResistance(10.0F));
		blocks.register("nagastone_pillar_weathered", "NagastonePillarWeathered", new BlockTFNagastonePillar().setHardness(1.5F).setResistance(10.0F));
		blocks.register("auroralized_glass", "AuroralizedGlass", new BlockTFAuroralizedGlass());
		blocks.register("iron_ladder", "IronLadder", new BlockTFLadderBars().setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F));

		blocks.register("terrorcotta_circle", "TerrorCottaCircle", new BlockTFHorizontal(Material.ROCK, MaterialColor.SAND).setSoundType(SoundType.STONE).setHardness(1.7F));
		blocks.register("terrorcotta_diagonal", "TerrorCottaDiagonal", new BlockTFDiagonal(Material.ROCK, MaterialColor.SAND).setSoundType(SoundType.STONE).setHardness(1.7F));
		blocks.register("stone_twist", "StonePillar", new BlockTFPillar(Material.ROCK).setHardness(1.5F).setResistance(10.0F));
		blocks.register("stone_twist_thin", "StonePillarThin", new BlockTFWallPillar(Material.ROCK, 12, 16).setHardness(1.5F).setResistance(10.0F));

//		registerFluidBlock(blocks, moltenFiery);
//		registerFluidBlock(blocks, moltenKnightmetal);
//		registerFluidBlock("fiery_essence", blocks, essenceFiery);
	}

	public static List<ModelRegisterCallback> getBlockModels() {
		return Collections.unmodifiableList(BlockRegistryHelper.blockModels);
	}

	private static class BlockRegistryHelper {

		static final List<ModelRegisterCallback> blockModels = new ArrayList<>();

		private final IForgeRegistry<Block> registry;

		BlockRegistryHelper(IForgeRegistry<Block> registry) {
			this.registry = registry;
		}

		<T extends Block> T register(String registryName, String translationKey, T block) {
			block.setTranslationKey(TwilightForestMod.ID + "." + translationKey);
			register(registryName, block);
			return block;
		}

		<T extends Block> T register(String registryName, T block) {
			block.setRegistryName(TwilightForestMod.ID, registryName);
			if (block instanceof ModelRegisterCallback) {
				blockModels.add((ModelRegisterCallback) block);
			}
			block.setCreativeTab(TFItems.creativeTab);
			registry.register(block);
			return block;
		}
	}

	// our internal fluid instances
	// TODO: Not necessary as TiCon2 does not exist on 1.14 yet
//	public static final Fluid moltenFiery;
//	public static final Fluid moltenKnightmetal;
//	public static final Fluid essenceFiery;
//
//	public static final ResourceLocation moltenFieryStill = TwilightForestMod.prefix("blocks/molten_fiery_still");
//	public static final ResourceLocation moltenFieryFlow  = TwilightForestMod.prefix("blocks/molten_fiery_flow");
//
//	public static final ResourceLocation moltenKnightmetalStill = TwilightForestMod.prefix("blocks/molten_knightmetal_still");
//	public static final ResourceLocation moltenKnightmetalFlow  = TwilightForestMod.prefix("blocks/molten_knightmetal_flow");
//
//	public static final ResourceLocation essenceFieryStill = TwilightForestMod.prefix("blocks/fluid_fiery_still");
//	public static final ResourceLocation essenceFieryFlow  = TwilightForestMod.prefix("blocks/fluid_fiery_flow");
//
//	static {
//		moltenFiery       = registerFluid(new Fluid("fierymetal" , moltenFieryStill, moltenFieryFlow).setTemperature(1000).setLuminosity(15));
//		moltenKnightmetal = registerFluid(new Fluid("knightmetal", moltenKnightmetalStill, moltenKnightmetalFlow).setTemperature(1000).setLuminosity(15));
//		essenceFiery      = registerFluid(new Fluid("fiery_essence", essenceFieryStill, essenceFieryFlow).setTemperature(1000));
//	}
//
//	private static Fluid registerFluid(Fluid fluid) {
//		fluid.setUnlocalizedName(fluid.getName());
//		FluidRegistry.registerFluid(fluid);
//		FluidRegistry.addBucketForFluid(fluid);
//		return fluid;
//	}
//
//	private static void registerFluidBlock(BlockRegistryHelper blocks, Fluid fluid) {
//		registerFluidBlock("molten_" + fluid.getName(), blocks, fluid);
//	}
//
//	private static void registerFluidBlock(String registryName, BlockRegistryHelper blocks, Fluid fluid) {
//		Block block = new BlockTFFluid(fluid, Material.LAVA).setTranslationKey(TwilightForestMod.ID + "." + fluid.getName()).setLightLevel(1.0F);
//		blocks.register(registryName, block);
//	}
}
