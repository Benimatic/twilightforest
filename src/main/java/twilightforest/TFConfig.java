package twilightforest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import twilightforest.world.WorldProviderTwilightForest;
import twilightforest.world.feature.TFGenCaveStalactite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("WeakerAccess")
@Config(modid = TwilightForestMod.ID)
@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class TFConfig {
	@Config.Ignore
	private static final String config = TwilightForestMod.ID + ".config.";

	@Config.LangKey(config + "dimension")
	@Config.Comment("Settings that are not reversible without consequences.")
	public static Dimension dimension = new Dimension();

	public static class Dimension {
		@Config.LangKey(config + "dimension_id")
		@Config.RequiresMcRestart
		@Config.Comment("What ID number to assign to the Twilight Forest dimension. Change if you are having conflicts with another mod.")
		public int dimensionID = 7;

		@Config.LangKey(config + "dimension_seed")
		@Config.RequiresWorldRestart
		@Config.Comment("If set, this will override the normal world seed when generating parts of the Twilight Forest Dimension.")
		public String twilightForestSeed = "";

		@Config.LangKey(config + "spawn_in_tf")
		@Config.Comment("If true, players spawning for the first time will spawn in the Twilight Forest.")
		public boolean newPlayersSpawnInTF = false;

		@Config.LangKey(config + "skylight_forest")
		@Config.RequiresWorldRestart
		@Config.Comment("If true, Twilight Forest will generate as a void except for Major Structures")
		public boolean skylightForest = false;

		@Config.LangKey(config + "skylight_oaks")
		@Config.RequiresWorldRestart
		@Config.Comment("If true, giant Twilight Oaks will also spawn in void worlds")
		public boolean skylightOaks = true;

		@Config.LangKey(config + "world_gen_weights")
		@Config.Comment("Weights for various small features")
		@Config.RequiresMcRestart
		public WorldGenWeights worldGenWeights = new WorldGenWeights();

		public static class WorldGenWeights {

			@Config.LangKey(config + "stone_circle_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int stoneCircleWeight = 10;

			@Config.LangKey(config + "well_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int wellWeight = 10;

			@Config.LangKey(config + "stalagmite_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int stalagmiteWeight = 12;

			@Config.LangKey(config + "foundation_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int foundationWeight = 10;

			@Config.LangKey(config + "monolith_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int monolithWeight = 10;

			@Config.LangKey(config + "grove_ruins_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int groveRuinsWeight = 5;

			@Config.LangKey(config + "hollow_stump_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int hollowStumpWeight = 12;

			@Config.LangKey(config + "fallen_hollow_log_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int fallenHollowLogWeight = 10;

			@Config.LangKey(config + "fallen_small_log_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int fallenSmallLogWeight = 10;

			@Config.LangKey(config + "druid_hut_weight")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0)
			public int druidHutWeight = 10;
		}

		@Config.LangKey(config + "hollow_hill_stalactites")
		@Config.Comment("Defines custom stalactites generated in hollow hills." +
				"\nFormat is \"modid:block<:meta> size maxLength minHeight weight\", where the properties are:" +
				"\nSize - the maximum length of the stalactite relative to the space between hill floor and ceiling," +
				"\nMax length - maximum length of a stalactite in blocks," +
				"\nMin height - minimum space between the hill floor and the stalactite to let it generate," +
				"\nWeight - how often it generates." +
				"\n\nFor example: \"minecraft:iron_ore 0.7 8 1 24\" would add a stalactite equal to the default iron ore stalactite.")
		@Config.RequiresMcRestart
		public HollowHillStalactites hollowHillStalactites = new HollowHillStalactites();

		public static class HollowHillStalactites {
			@Config.LangKey(config + "large_hill")
			@Config.RequiresMcRestart
			@Config.Comment("Blocks generating as stalactites in large hills only")
			public String[] largeHill = {};

			@Config.LangKey(config + "medium_hill")
			@Config.RequiresMcRestart
			@Config.Comment("Blocks generating as stalactites in medium and large hills")
			public String[] mediumHill = {};

			@Config.LangKey(config + "small_hill")
			@Config.RequiresMcRestart
			@Config.Comment("Blocks generating as stalactites in all hills")
			public String[] smallHill = {};

			@Config.LangKey(config + "stalactite_config_only")
			@Config.RequiresMcRestart
			@Config.Comment("If true, default stalactites and stalactites defined by other mods will not be used.")
			public boolean useConfigOnly = false;

			public void load() {
				registerHill(smallHill, 1);
				registerHill(mediumHill, 2);
				registerHill(largeHill, 3);
			}

			private void registerHill(String[] definitions, int tier) {
				for (String definition : definitions) {
					if (!parseStalactite(definition, tier)) {
						TwilightForestMod.LOGGER.warn("Invalid hollow hill stalactite definition: {}", definition);
					}
				}
			}

			private boolean parseStalactite(String definition, int tier) {
				String[] split = definition.split(" ");
				if (split.length != 5) return false;

				Optional<IBlockState> state = parseBlockState(split[0]);
				if (!state.isPresent()) return false;

				try {
					TFGenCaveStalactite.addStalactite(tier, state.get(),
							Float.parseFloat(split[1]),
							Integer.parseInt(split[2]),
							Integer.parseInt(split[3]),
							Integer.parseInt(split[4])
					);
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			}
		}
	}

	@Config.RequiresMcRestart
	@Config.LangKey(config + "compat")
	@Config.Comment("Should TF Compatibility load? Turn off if TF's Compatibility is causing crashes or if not desired.")
	public static boolean doCompat = true;

	@Config.LangKey(config + "performance")
	@Config.Comment("Lets you sacrifice various things to improve world performance.")
	public static Performance performance = new Performance();

	public static class Performance {
		@Config.LangKey(config + "canopy_coverage")
		@Config.RangeDouble(min = 0)
		@Config.Comment("Amount of canopy coverage. Lower numbers improve chunk generation speed at the cost of a thinner forest.")
		public float canopyCoverage = 1.7F;

		@Config.LangKey(config + "twilight_oaks")
		@Config.RangeInt(min = 1)
		@Config.Comment("Chance that a chunk in the Twilight Forest will contain a twilight oak tree. Higher numbers reduce the number of trees, increasing performance.")
		public int twilightOakChance = 48;

		@Config.LangKey(config + "leaves_light_opacity")
		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("This controls the opacity of leaves, changing the amount of light blocked. Can be used to decrease complexity in some lighting checks.")
		public int leavesLightOpacity = 1;

		@Config.LangKey(config + "glacier_packed_ice")
		@Config.Comment("Setting this true will make Twilight Glaciers generate with Packed Ice instead of regular translucent Ice, decreasing amount of light checking calculations.")
		public boolean glacierPackedIce = false;

		@Config.LangKey(config + "enable_skylight")
		@Config.Comment("If the dimension has per-block skylight values. Disabling this will significantly improve world generation performance, at the cost of flat lighting everywhere." +
				"\nWARNING: Once chunks are loaded without skylight, that data is lost and cannot easily be regenerated. Be careful!")
		@Config.RequiresWorldRestart
		public boolean enableSkylight = true;

		@Config.Ignore
		public boolean shadersSupported = true;
	}

	@Config.LangKey(config + "silent_cicadas")
	@Config.Comment("Make cicadas silent for those having sound library problems, or otherwise finding them annoying.")
	public static boolean silentCicadas = false;

	@Config.LangKey(config + "first_person_effects")
	@Config.Comment("Controls whether various effects from the mod are rendered while in first-person view. Turn this off if you find them distracting.")
	public static boolean firstPersonEffects = true;

	@Config.LangKey(config + "origin_dimension")
	@Config.Comment("The dimension you can always travel to the Twilight Forest from, as well as the dimension you will return to. Defaults to the overworld.")
	public static int originDimension = 0;

	@Config.LangKey(config + "portals_in_other_dimensions")
	@Config.Comment("Allow portals to the Twilight Forest to be made outside of the 'origin' dimension. May be considered an exploit.")
	public static boolean allowPortalsInOtherDimensions = false;

	@Config.LangKey(config + "admin_portals")
	@Config.Comment("Allow portals only for admins (Operators). This severely reduces the range in which the mod usually scans for valid portal conditions, and it scans near ops only.")
	public static boolean adminOnlyPortals = false;

	@Config.LangKey(config + "portals")
	@Config.Comment("Disable Twilight Forest portal creation entirely. Provided for server operators looking to restrict action to the dimension.")
	public static boolean disablePortalCreation = false;

	@Config.LangKey(config + "portal_creator")
	@Config.Comment("Registry String IDs of items used to create the Twilight Forest Portal. (domain:regname:meta) meta is optional.")
	public static String[] portalCreationItems = {"minecraft:diamond"};

	@Config.LangKey(config + "check_portal_destination")
	@Config.Comment("Determines if new portals should be pre-checked for safety. If enabled, portals will fail to form rather than redirect to a safe alternate destination." +
			"\nNote that enabling this also reduces the rate at which portal formation checks are performed.")
	public static boolean checkPortalDestination = false;

	@Config.LangKey(config + "portal_lighting")
	@Config.Comment("Set this true if you want the lightning that zaps the portal to not set things on fire. For those who don't like fun.")
	public static boolean portalLightning = false;

	@Config.LangKey(config + "portal_return")
	@Config.Comment("If false, the return portal will require the activation item.")
	public static boolean shouldReturnPortalBeUsable = true;

	@Config.LangKey(config + "progression_default")
	@Config.Comment("Sets the default value of the game rule controlling enforced progression.")
	public static boolean progressionRuleDefault = true;

	@Config.RequiresMcRestart
	@Config.LangKey(config + "uncrafting")
	@Config.Comment("Disable the uncrafting function of the uncrafting table. Provided as an option when interaction with other mods produces exploitable recipes.")
	public static boolean disableUncrafting = false;

	@Config.LangKey(config + "antibuilder_blacklist")
	@Config.Comment("Anti-Builder blacklist. (domain:block:meta) meta is optional.")
	public static String[] antibuilderBlacklist = {"minecraft:bedrock", "tombmanygraves:grave_block"};

	@Config.LangKey(config + "animate_trophyitem")
	@Config.Comment("Rotate trophy heads on item model. Has no performance impact at all. For those who don't like fun.")
	public static boolean rotateTrophyHeadsGui = true;

	@Config.LangKey(config + "shield_parry")
	@Config.Comment("We recommend downloading the Shield Parry mod for parrying, but these controls remain for without.")
	public static ShieldInteractions shieldInteractions = new ShieldInteractions();

	public static class ShieldInteractions {
		@Config.LangKey(config + "parry_non_twilight")
		@Config.Comment("Set to true to parry non-Twilight projectiles.")
		public boolean parryNonTwilightAttacks = false;

		@Config.LangKey(config + "parry_window_arrow")
		@Config.RangeInt(min = 0)
		@Config.Comment("The amount of ticks after raising a shield that makes it OK to parry an arrow.")
		public int shieldParryTicksArrow = 40;

		@Config.LangKey(config + "parry_window_fireball")
		@Config.RangeInt(min = 0)
		@Config.Comment("The amount of ticks after raising a shield that makes it OK to parry a fireball.")
		public int shieldParryTicksFireball = 40;

		@Config.LangKey(config + "parry_window_throwable")
		@Config.RangeInt(min = 0)
		@Config.Comment("The amount of ticks after raising a shield that makes it OK to parry a thrown item.")
		public int shieldParryTicksThrowable = 40;

		@Config.LangKey(config + "parry_window_beam")
		@Config.RangeInt(min = 0)
		public int shieldParryTicksBeam = 10;
	}

	@Config.LangKey(config + "loading_screen")
	@Config.Comment("Client only: Controls for the Loading screen")
	public static final LoadingScreen loadingScreen = new LoadingScreen();

	public static class LoadingScreen {
		@Config.LangKey(config + "loading_icon_enable")
		@Config.Comment("Wobble the Loading icon. Has no performance impact at all. For those who don't like fun.")
		public boolean enable = true;

		@Config.LangKey(config + "loading_screen_swap_frequency")
		@Config.RangeInt(min = 0)
		@Config.Comment("How many ticks between each loading screen change. Set to 0 to not cycle at all.")
		public int cycleLoadingScreenFrequency = 0;

		@Config.LangKey(config + "loading_icon_wobble_bounce_frequency")
		@Config.RangeDouble(min = 0F)
		@Config.Comment("Frequency of wobble and bounce.")
		public float frequency = 5F;

		@Config.LangKey(config + "loading_icon_scale")
		@Config.RangeDouble(min = 0F)
		@Config.Comment("Scale of whole bouncy loading icon.")
		public float scale = 3F;

		@Config.LangKey(config + "loading_icon_bounciness")
		@Config.RangeDouble(min = 0F)
		@Config.Comment("How much the loading icon bounces.")
		public float scaleDeviation = 5.25F;

		@Config.LangKey(config + "loading_icon_tilting")
		@Config.RangeDouble(min = 0F, max = 360F)
		@Config.Comment("How far the loading icon wobbles.")
		public float tiltRange = 11.25F;

		@Config.LangKey(config + "loading_icon_tilt_pushback")
		@Config.RangeDouble(min = 0F, max = 360F)
		@Config.Comment("Pushback value to re-center the wobble of loading icon.")
		public float tiltConstant = 22.5F;

		@Config.LangKey(config + "loading_icon_stacks")
		@Config.Comment("List of items to be used for the wobbling Loading Icon. (domain:item:meta) meta is optional.")
		public String[] loadingIconStacks = {
				"twilightforest:experiment_115",
				"twilightforest:magic_map",
				"twilightforest:charm_of_life_2",
				"twilightforest:charm_of_keeping_3",
				"twilightforest:phantom_helmet",
				"twilightforest:lamp_of_cinders",
				"twilightforest:carminite",
				"twilightforest:block_and_chain",
				"twilightforest:yeti_helmet",
				"twilightforest:hydra_chop",
				"twilightforest:magic_beans",
				"twilightforest:ironwood_raw",
				"twilightforest:naga_scale",
				"twilightforest:experiment_115:2",
				"twilightforest:miniature_structure",
				"twilightforest:miniature_structure:6",
				"twilightforest:knightmetal_block",
				"twilightforest:tower_device:10",
				"twilightforest:twilight_sapling:5",
				"twilightforest:twilight_sapling:6",
				"twilightforest:twilight_sapling:7",
				"twilightforest:twilight_sapling:8",
				"twilightforest:twilight_sapling:9",
				"twilightforest:borer_essence"
		};

		@Config.Ignore
		private ImmutableList<ItemStack> loadingScreenIcons;

		public ImmutableList<ItemStack> getLoadingScreenIcons() {
			return loadingScreenIcons;
		}

		void loadLoadingScreenIcons() {
			ImmutableList.Builder<ItemStack> iconList = ImmutableList.builder();

			iconList.addAll(IMCHandler.getLoadingIconStacks());

			for (String s : loadingIconStacks) {
				parseItemStack(s, 0).ifPresent(iconList::add);
			}

			loadingScreenIcons = iconList.build();
		}
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(TwilightForestMod.ID)) {
			TwilightForestMod.checkOriginDimension();
			ConfigManager.sync(TwilightForestMod.ID, Config.Type.INSTANCE);
			if (!event.isWorldRunning()) {
				WorldProviderTwilightForest.syncFromConfig();
			}
			build();
		}
	}

	public static void build() {
		loadAntiBuilderBlacklist();
		buildPortalIngredient();
		loadingScreen.loadLoadingScreenIcons();
	}

	private static void loadAntiBuilderBlacklist() {
		ImmutableSet.Builder<IBlockState> builder = ImmutableSet.builder();

		builder.addAll(IMCHandler.getBlacklistedBlocks());

		for (String s : antibuilderBlacklist) {
			parseBlockState(s).ifPresent(builder::add);
		}

		disallowBreakingBlockList = builder.build();
	}

	@Config.Ignore
	private static ImmutableSet<IBlockState> disallowBreakingBlockList;

	public static ImmutableSet<IBlockState> getDisallowedBlocks() {
		return disallowBreakingBlockList;
	}

	@Config.Ignore
	public static Ingredient portalIngredient;

	private static void buildPortalIngredient() {

		List<ItemStack> stacks = new ArrayList<>();

		for (String s : portalCreationItems) {
			parseItemStack(s, OreDictionary.WILDCARD_VALUE).ifPresent(stacks::add);
		}

		if (stacks.isEmpty()) {
			stacks.add(new ItemStack(Items.DIAMOND));
		}

		portalIngredient = Ingredient.fromStacks(stacks.toArray(new ItemStack[0]));
	}

	private static Optional<ItemStack> parseItemStack(String string, int defaultMeta) {

		String[] split = string.split(":");
		if (split.length < 2) return Optional.empty();

		Item item = Item.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
		if (item == null || item == Items.AIR) return Optional.empty();

		int meta = defaultMeta;
		if (split.length > 2) {
			try {
				meta = Integer.parseInt(split[2]);
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		}
		if (meta < 0 || meta > Short.MAX_VALUE) return Optional.empty();

		return Optional.of(new ItemStack(item, 1, meta));
	}

	private static Optional<IBlockState> parseBlockState(String string) {

		String[] split = string.split(":");
		if (split.length < 2) return Optional.empty();

		Block block = Block.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
		if (block == Blocks.AIR) return Optional.empty();

		if (split.length == 2) {
			return Optional.of(block.getDefaultState());
		}

		int meta;
		try {
			meta = Integer.parseInt(split[2]);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
		if (meta < 0 || meta > 15) return Optional.empty();

		return Optional.of(block.getStateFromMeta(meta));
	}
}
