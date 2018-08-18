package twilightforest.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.enums.CastleBrickVariant;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public final class RegisterBlockEvent {
	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		BlockRegistryHelper blocks = new BlockRegistryHelper(event.getRegistry());

		blocks.register("twilight_log", (new BlockTFLog()).setTranslationKey("TFLog"));
		blocks.register("root", (new BlockTFRoots()).setTranslationKey("TFRoots"));
		blocks.register("twilight_leaves", (new BlockTFLeaves()).setTranslationKey("TFLeaves"));
		blocks.register("firefly", (new BlockTFFirefly()).setTranslationKey("TFFirefly"));
		blocks.register("cicada", (new BlockTFCicada()).setTranslationKey("TFCicada"));
		blocks.register("twilight_portal", (new BlockTFPortal()).setTranslationKey("TFPortal"));
		blocks.register("maze_stone", (new BlockTFMazestone()).setTranslationKey("TFMazestone"));
		blocks.register("hedge", (new BlockTFHedge()).setTranslationKey("TFHedge"));
		blocks.register("boss_spawner", (new BlockTFBossSpawner()).setTranslationKey("TFBossSpawner").setBlockUnbreakable());
		blocks.register("firefly_jar", (new BlockTFFireflyJar()).setTranslationKey("TFFireflyJar"));
		blocks.register("twilight_plant", (new BlockTFPlant()).setTranslationKey("TFPlant"));
		blocks.register("uncrafting_table", (new BlockTFUncraftingTable()).setTranslationKey("TFUncraftingTable"));
		blocks.register("fire_jet", (new BlockTFFireJet()).setTranslationKey("TFFireJet"));
		blocks.register("naga_stone", (new BlockTFNagastone()).setTranslationKey("TFNagastone"));
		blocks.register("twilight_sapling", (new BlockTFSapling()).setTranslationKey("TFSapling"));
		blocks.register("moonworm", (new BlockTFMoonworm()).setTranslationKey("TFMoonworm"));
		blocks.register("magic_log", (new BlockTFMagicLog()).setTranslationKey("TFMagicLog"));
		blocks.register("magic_leaves", (new BlockTFMagicLeaves()).setTranslationKey("TFMagicLeaves"));
		blocks.register("magic_log_core", (new BlockTFMagicLogSpecial()).setTranslationKey("TFMagicLogSpecial"));
		blocks.register("tower_wood", (new BlockTFTowerWood()).setTranslationKey("TFTowerStone"));
		blocks.register("tower_device", (new BlockTFTowerDevice()).setTranslationKey("TFTowerDevice"));
		blocks.register("tower_translucent", (new BlockTFTowerTranslucent()).setTranslationKey("TFTowerTranslucent"));
		blocks.register("trophy", (new BlockTFTrophy()).setTranslationKey("TFTrophy"));
		blocks.register("stronghold_shield", (new BlockTFShield()).setTranslationKey("TFShield"));
		blocks.register("trophy_pedestal", (new BlockTFTrophyPedestal()).setTranslationKey("TFTrophyPedestal"));
		blocks.register("aurora_block", (new BlockTFAuroraBrick()).setTranslationKey("TFAuroraBrick"));
		blocks.register("underbrick", (new BlockTFUnderBrick()).setTranslationKey("TFUnderBrick"));
		blocks.register("thorns", (new BlockTFThorns()).setTranslationKey("TFThorns"));
		blocks.register("burnt_thorns", (new BlockTFBurntThorns()).setTranslationKey("TFBurntThorns"));
		blocks.register("thorn_rose", (new BlockTFThornRose()).setTranslationKey("TFThornRose"));
		blocks.register("twilight_leaves_3", (new BlockTFLeaves3()).setTranslationKey("TFLeaves3"));
		blocks.register("deadrock", (new BlockTFDeadrock()).setTranslationKey("TFDeadrock"));
		blocks.register("dark_leaves", (new BlockTFDarkLeaves()).setTranslationKey("DarkLeaves"));
		blocks.register("aurora_pillar", (new BlockTFAuroraPillar()).setTranslationKey("AuroraPillar"));
		blocks.register("aurora_slab", (new BlockTFAuroraSlab(false)).setTranslationKey("AuroraSlab"));
		blocks.register("double_aurora_slab", (new BlockTFAuroraSlab(true)).setTranslationKey("AuroraDoubleSlab"));
		blocks.register("trollsteinn", (new BlockTFTrollSteinn()).setTranslationKey("TrollSteinn"));
		blocks.register("wispy_cloud", (new BlockTFWispyCloud()).setTranslationKey("WispyCloud"));
		blocks.register("fluffy_cloud", (new BlockTFFluffyCloud()).setTranslationKey("FluffyCloud"));
		blocks.register("giant_cobblestone", (new BlockTFGiantCobble()).setTranslationKey("GiantCobble"));
		blocks.register("giant_log", (new BlockTFGiantLog()).setTranslationKey("GiantLog"));
		blocks.register("giant_leaves", (new BlockTFGiantLeaves()).setTranslationKey("GiantLeaves"));
		blocks.register("giant_obsidian", (new BlockTFGiantObsidian()).setTranslationKey("GiantObsidian"));
		blocks.register("uberous_soil", (new BlockTFUberousSoil()).setTranslationKey("UberousSoil"));
		blocks.register("huge_stalk", (new BlockTFHugeStalk()).setTranslationKey("HugeStalk"));
		blocks.register("huge_mushgloom", (new BlockTFHugeGloomBlock()).setTranslationKey("HugeGloomBlock"));
		blocks.register("trollvidr", (new BlockTFTrollRoot()).setTranslationKey("TrollVidr"));
		blocks.register("unripe_trollber", (new BlockTFUnripeTorchCluster()).setTranslationKey("UnripeTrollBer"));
		blocks.register("trollber", (new BlockTFRipeTorchCluster()).setTranslationKey("TrollBer"));
		blocks.register("knightmetal_block", (new BlockTFKnightmetalBlock()).setTranslationKey("KnightmetalBlock"));
		blocks.register("huge_lilypad", (new BlockTFHugeLilyPad()).setTranslationKey("HugeLilyPad"));
		blocks.register("huge_waterlily", (new BlockTFHugeWaterLily()).setTranslationKey("HugeWaterLily"));
		blocks.register("slider", (new BlockTFSlider()).setTranslationKey("Slider"));
		Block castleBrick = new BlockTFCastleBlock().setTranslationKey("CastleBrick");
		IBlockState castleState = castleBrick.getDefaultState();
		blocks.register("castle_brick", castleBrick);
		blocks.register("castle_stairs_brick", new BlockTFStairs(castleState).setTranslationKey("CastleStairsBrick"));
		blocks.register("castle_stairs_worn", new BlockTFStairs(castleState.withProperty(BlockTFCastleBlock.VARIANT, CastleBrickVariant.WORN)).setTranslationKey("CastleStairsWorn"));
		blocks.register("castle_stairs_cracked", new BlockTFStairs(castleState.withProperty(BlockTFCastleBlock.VARIANT, CastleBrickVariant.CRACKED)).setTranslationKey("CastleStairsCracked"));
		blocks.register("castle_stairs_mossy", new BlockTFStairs(castleState.withProperty(BlockTFCastleBlock.VARIANT, CastleBrickVariant.MOSSY)).setTranslationKey("CastleStairsMossy"));
		Block castlePillar = new BlockTFCastlePillar();
		blocks.register("castle_pillar", castlePillar.setTranslationKey("CastlePillar"));
		blocks.register("castle_stairs", (new BlockTFCastleStairs(castlePillar.getDefaultState())).setTranslationKey("CastleStairs"));
		blocks.register("castle_rune_brick", (new BlockTFCastleMagic()).setTranslationKey("CastleMagic"));
		blocks.register("force_field", (new BlockTFForceField()).setTranslationKey("ForceField"));
		blocks.register("cinder_furnace", (new BlockTFCinderFurnace(false)).setTranslationKey("CinderFurnaceIdle"));
		blocks.register("cinder_furnace_lit", (new BlockTFCinderFurnace(true)).setTranslationKey("CinderFurnaceLit"));
		blocks.register("cinder_log", (new BlockTFCinderLog()).setTranslationKey("CinderLog"));
		blocks.register("castle_door", (new BlockTFCastleDoor(false)).setTranslationKey("CastleDoor"));
		blocks.register("castle_door_vanished", (new BlockTFCastleDoor(true)).setTranslationKey("CastleDoorVanished"));
		blocks.register("castle_unlock", (new BlockTFCastleUnlock()).setTranslationKey("CastleUnlock"));
		blocks.register("experiment_115", (new BlockTFExperiment115()).setTranslationKey("experiment115").setHardness(0.5F));
		blocks.register("miniature_structure", (new BlockTFMiniatureStructure()).setTranslationKey("MiniatureStructure").setHardness(0.75F));
		blocks.register("block_storage", new BlockTFCompressed().setTranslationKey("BlockOfStorage"));
		blocks.register("lapis_block", new BlockTFLapisBlock().setTranslationKey("BlockOfLapisTF"));
		blocks.register("spiral_bricks", new BlockTFSpiralBrick().setTranslationKey("SpiralBricks"));
		Block etchedNagastone = new BlockTFNagastoneEtched().setTranslationKey("EtchedNagastone").setHardness(1.5F).setResistance(10.0F);
		blocks.register("etched_nagastone", etchedNagastone);
		blocks.register("nagastone_stairs", new BlockTFNagastoneStairs(etchedNagastone.getDefaultState()).setTranslationKey("NagastoneStairs").setHardness(1.5F).setResistance(10.0F));
		blocks.register("nagastone_pillar", new BlockTFNagastonePillar().setTranslationKey("NagastonePillar").setHardness(1.5F).setResistance(10.0F));
		Block etchedNagastoneMossy = new BlockTFNagastoneEtched().setTranslationKey("EtchedNagastoneMossy").setHardness(1.5F).setResistance(10.0F);
		blocks.register("etched_nagastone_mossy", etchedNagastoneMossy);
		blocks.register("nagastone_stairs_mossy", new BlockTFNagastoneStairs(etchedNagastoneMossy.getDefaultState()).setTranslationKey("NagastoneStairsMossy").setHardness(1.5F).setResistance(10.0F));
		blocks.register("nagastone_pillar_mossy", new BlockTFNagastonePillar().setTranslationKey("NagastonePillarMossy").setHardness(1.5F).setResistance(10.0F));
		Block etchedNagastoneWeathered = new BlockTFNagastoneEtched().setTranslationKey("EtchedNagastoneWeathered").setHardness(1.5F).setResistance(10.0F);
		blocks.register("etched_nagastone_weathered", etchedNagastoneWeathered);
		blocks.register("nagastone_stairs_weathered", new BlockTFNagastoneStairs(etchedNagastoneWeathered.getDefaultState()).setTranslationKey("NagastoneStairsWeathered").setHardness(1.5F).setResistance(10.0F));
		blocks.register("nagastone_pillar_weathered", new BlockTFNagastonePillar().setTranslationKey("NagastonePillarWeathered").setHardness(1.5F).setResistance(10.0F));
		blocks.register("auroralized_glass", new BlockTFAuroralizedGlass().setTranslationKey("AuroralizedGlass"));

		registerFluidBlock(blocks, moltenFiery);
		registerFluidBlock(blocks, moltenKnightmetal);
		registerFluidBlock("fiery_essence", blocks, essenceFiery);
	}

	public static List<ModelRegisterCallback> getBlockModels() {
		return ImmutableList.copyOf(BlockRegistryHelper.blockModels);
	}

	private static class BlockRegistryHelper {
		private final IForgeRegistry<Block> registry;

		private static List<ModelRegisterCallback> blockModels = new ArrayList<>();

		BlockRegistryHelper(IForgeRegistry<Block> registry) {
			this.registry = registry;
		}

		private void register(String registryName, Block block) {
			block.setRegistryName(TwilightForestMod.ID, registryName);
			if (block instanceof ModelRegisterCallback)
				blockModels.add((ModelRegisterCallback) block);
			registry.register(block);
		}
	}

	// our internal fluid instances
	public static final Fluid moltenFiery;
	public static final Fluid moltenKnightmetal;
	public static final Fluid essenceFiery;

	public static final ResourceLocation moltenFieryStill = new ResourceLocation(TwilightForestMod.ID, "blocks/molten_fiery_still");
	public static final ResourceLocation moltenFieryFlow = new ResourceLocation(TwilightForestMod.ID,"blocks/molten_fiery_flow");

	public static final ResourceLocation moltenKnightmetalStill = new ResourceLocation(TwilightForestMod.ID, "blocks/molten_knightmetal_still");
	public static final ResourceLocation moltenKnightmetalFlow = new ResourceLocation(TwilightForestMod.ID,"blocks/molten_knightmetal_flow");

	public static final ResourceLocation essenceFieryStill = new ResourceLocation(TwilightForestMod.ID, "blocks/fluid_fiery_still");
	public static final ResourceLocation essenceFieryFlow = new ResourceLocation(TwilightForestMod.ID,"blocks/fluid_fiery_flow");

	static {
		moltenFiery       = registerFluid(new Fluid("fierymetal" , moltenFieryStill, moltenFieryFlow).setTemperature(1000).setLuminosity(15));
		moltenKnightmetal = registerFluid(new Fluid("knightmetal", moltenKnightmetalStill, moltenKnightmetalFlow).setTemperature(1000).setLuminosity(15));
		essenceFiery      = registerFluid(new Fluid("fiery_essence", essenceFieryStill, essenceFieryFlow).setTemperature(1000));
	}

	private static Fluid registerFluid(Fluid fluid) {
		fluid.setUnlocalizedName(fluid.getName());
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	private static void registerFluidBlock(BlockRegistryHelper blocks, Fluid fluid) {
		registerFluidBlock("molten_" + fluid.getName(), blocks, fluid);
	}

	private static void registerFluidBlock(String registryName, BlockRegistryHelper blocks, Fluid fluid) {
		Block block = new BlockTFFluid(fluid, Material.LAVA).setTranslationKey(TwilightForestMod.ID + "." + fluid.getName()).setLightLevel(1.0F);
		blocks.register(registryName, block);
	}
}
