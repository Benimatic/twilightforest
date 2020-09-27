package twilightforest;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.entity.*;
import twilightforest.structures.*;
import twilightforest.structures.courtyard.NagaCourtyardPieces;
import twilightforest.structures.darktower.TFDarkTowerPieces;
import twilightforest.structures.finalcastle.TFFinalCastlePieces;
import twilightforest.structures.icetower.TFIceTowerPieces;
import twilightforest.structures.lichtower.TFLichTowerPieces;
import twilightforest.structures.minotaurmaze.TFMinotaurMazePieces;
import twilightforest.structures.mushroomtower.TFMushroomTowerPieces;
import twilightforest.structures.start.*;
import twilightforest.structures.stronghold.TFStrongholdPieces;
import twilightforest.structures.trollcave.TFTrollCavePieces;
import twilightforest.util.IntPair;
import twilightforest.util.PlayerHelper;
import twilightforest.world.MapGenTFMajorFeature;
import twilightforest.world.TFWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Arbiting class that decides what feature goes where in the world, in terms of the major features in the world
 */
public enum TFFeature {

	NOTHING    ( 0, "no_feature"       , false) { { this.enableDecorations().disableStructure(); } },
	SMALL_HILL ( 1, "small_hollow_hill", true ) {
		{
			this.enableDecorations().enableTerrainAlterations();

			MapGenStructureIO.registerStructure(StructureStartHollowHill.class, "TFHill");
			MapGenStructureIO.registerStructureComponent(ComponentTFHollowHill.class, "TFHill");

			this.addMonster(EntitySpider.class, 10, 4, 4)
					.addMonster(EntityZombie.class, 10, 4, 4)
					.addMonster(EntityTFRedcap.class, 10, 4, 4)
					.addMonster(EntityTFSwarmSpider.class, 10, 4, 4)
					.addMonster(EntityTFKobold.class, 10, 4, 8);
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartHollowHill(world, this, rand, chunkX, chunkZ);
		}
	},
	MEDIUM_HILL ( 2, "medium_hollow_hill", true ) {
		{
			this.enableDecorations().enableTerrainAlterations();

			this.addMonster(EntityTFRedcap.class, 10, 4, 4)
					.addMonster(EntityTFRedcapSapper.class, 1, 1, 4)
					.addMonster(EntityTFKobold.class, 10, 4, 8)
					.addMonster(EntitySkeleton.class, 10, 4, 4)
					.addMonster(EntityTFSwarmSpider.class, 10, 4, 4)
					.addMonster(EntitySpider.class, 10, 4, 4)
					.addMonster(EntityCreeper.class, 10, 4, 4)
					.addMonster(EntityTFFireBeetle.class, 5, 4, 4)
					.addMonster(EntityTFSlimeBeetle.class, 5, 4, 4)
					.addMonster(EntityWitch.class, 1, 1, 1);
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartHollowHill(world, this, rand, chunkX, chunkZ);
		}
	},
	LARGE_HILL ( 3, "large_hollow_hill", true ) {
		{
			this.enableDecorations().enableTerrainAlterations();

			this.addMonster(EntityTFRedcap.class, 10, 4, 4)
					.addMonster(EntityTFRedcapSapper.class, 2, 1, 4)
					.addMonster(EntitySkeleton.class, 10, 4, 4)
					.addMonster(EntityCaveSpider.class, 10, 4, 4)
					.addMonster(EntityCreeper.class, 10, 4, 4)
					.addMonster(EntityEnderman.class, 1, 1, 4)
					.addMonster(EntityTFWraith.class, 2, 1, 4)
					.addMonster(EntityTFFireBeetle.class, 10, 4, 4)
					.addMonster(EntityTFSlimeBeetle.class, 10, 4, 4)
					.addMonster(EntityTFPinchBeetle.class, 10, 2, 4)
					.addMonster(EntityWitch.class, 1, 1, 1);
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartHollowHill(world, this, rand, chunkX, chunkZ);
		}
	},
	HEDGE_MAZE ( 2, "hedge_maze", true ) {
		{
			this.enableTerrainAlterations();

			MapGenStructureIO.registerStructure(StructureStartHedgeMaze.class, "TFHedge");
			MapGenStructureIO.registerStructureComponent(ComponentTFHedgeMaze.class, "TFHedge");
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartHedgeMaze(world, this, rand, chunkX, chunkZ);
		}
	},
	NAGA_COURTYARD ( 3, "naga_courtyard", true ) {
		{
			this.enableTerrainAlterations();

			//MapGenStructureIO.registerStructureComponent(ComponentNagaCourtyardMain.class, "TFNaga");
			NagaCourtyardPieces.registerPieces();
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartCourtyard(world, this, rand, chunkX, chunkZ);
		}
	},
	LICH_TOWER ( 1, "lich_tower", true, TwilightForestMod.prefix("progress_naga") ) {
		{
			TFLichTowerPieces.registerPieces();

			this.addMonster(EntityZombie.class, 10, 4, 4)
					.addMonster(EntitySkeleton.class, 10, 4, 4)
					.addMonster(EntityCreeper.class, 1, 4, 4)
					.addMonster(EntityEnderman.class, 1, 1, 4)
					.addMonster(EntityTFDeathTome.class, 10, 4, 4)
					.addMonster(EntityWitch.class, 1, 1, 1);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.lichtower", 4);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on a Pointy Tower"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartLichTower(world, this, rand, chunkX, chunkZ);
		}
	},
	ICE_TOWER ( 2, "ice_tower", true, TwilightForestMod.prefix("progress_yeti") ) {
		{
			TFIceTowerPieces.registerPieces();

			this.addMonster(EntityTFSnowGuardian.class, 10, 4, 4)
					.addMonster(EntityTFIceShooter.class, 10, 4, 4)
					.addMonster(EntityTFIceExploder.class, 5, 4, 4);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.icetower", 3);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on Auroral Fortification"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartAuroraPalace(world, this, rand, chunkX, chunkZ);
		}
	},
	QUEST_ISLAND ( 1, "quest_island", false ) { { this.disableStructure(); } },
	QUEST_GROVE  ( 1, "quest_grove" , true  ) {
		{
			this.enableTerrainAlterations();

			MapGenStructureIO.registerStructure(StructureStartQuestGrove.class, "TFQuest1");
			MapGenStructureIO.registerStructureComponent(ComponentTFQuestGrove.class, "TFQuest1");
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartQuestGrove(world, this, rand, chunkX, chunkZ);
		}
	},
	DRUID_GROVE    ( 1, "druid_grove"   , false ) { { this.disableStructure(); } },
	FLOATING_RUINS ( 3, "floating_ruins", false ) { { this.disableStructure(); } },
	HYDRA_LAIR     ( 2, "hydra_lair"    , true , TwilightForestMod.prefix("progress_labyrinth") ) {
		{
			this.enableTerrainAlterations();

			MapGenStructureIO.registerStructure(StructureStartHydraLair.class, "TFHydra");
			MapGenStructureIO.registerStructureComponent(ComponentTFHydraLair.class, "TFHydra");
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.hydralair", 4);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on the Fire Swamp"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartHydraLair(world, this, rand, chunkX, chunkZ);
		}
	},
	LABYRINTH ( 3, "labyrinth", true, TwilightForestMod.prefix("progress_lich") ) {
		{
			this.enableDecorations();

			TFMinotaurMazePieces.registerPieces();

			this.addMonster(EntityTFMinotaur.class, 20, 2, 4)
					.addMonster(EntityCaveSpider.class, 10, 4, 4)
					.addMonster(EntityCreeper.class, 10, 4, 4)
					.addMonster(EntityTFMazeSlime.class, 10, 4, 4)
					.addMonster(EntityEnderman.class, 1, 1, 4)
					.addMonster(EntityTFFireBeetle.class, 10, 4, 4)
					.addMonster(EntityTFSlimeBeetle.class, 10, 4, 4)
					.addMonster(EntityTFPinchBeetle.class, 10, 2, 4);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.labyrinth", 5);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on a Swampy Labyrinth"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartLabyrinth(world, this, rand, chunkX, chunkZ);
		}
	},
	DARK_TOWER ( 1, "dark_tower", true, TwilightForestMod.prefix("progress_knights") ) {
		{
			TFDarkTowerPieces.registerPieces();

			this.addMonster(EntityTFTowerGolem.class, 10, 4, 4)
					.addMonster(EntitySkeleton.class, 10, 4, 4)
					.addMonster(EntityCreeper.class, 10, 4, 4)
					.addMonster(EntityEnderman.class, 2, 1, 4)
					.addMonster(EntityWitch.class, 1, 1, 1)
					.addMonster(EntityTFMiniGhast.class, 10, 1, 4)
					.addMonster(EntityTFTowerBroodling.class, 10, 8, 8)
					.addMonster(EntityTFPinchBeetle.class, 10, 2, 4)
					// roof ghasts
					.addMonster(1, EntityTFTowerGhast.class, 10, 1, 4)
					// aquarium squids (only in aquariums between y = 35 and y = 64. :/
					.addWaterCreature(EntitySquid.class, 10, 4, 4);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.darktower", 3);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on a Wooden Tower"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartDarkTower(world, this, rand, chunkX, chunkZ);
		}
	},
	KNIGHT_STRONGHOLD ( 3, "knight_stronghold", true, TwilightForestMod.prefix("progress_trophy_pedestal") ) {
		{
			this.enableDecorations().disableProtectionAura();

			TFStrongholdPieces.registerPieces();

			this.addMonster(EntityTFBlockGoblin.class, 10, 4, 4)
					.addMonster(EntityTFGoblinKnightLower.class, 5, 1, 2)
					.addMonster(EntityTFHelmetCrab.class, 10, 4, 4)
					.addMonster(EntityTFSlimeBeetle.class, 10, 4, 4)
					.addMonster(EntityTFRedcapSapper.class, 2, 1, 4)
					.addMonster(EntityTFKobold.class, 10, 4, 8)
					.addMonster(EntityCreeper.class, 10, 4, 4)
					.addMonster(EntitySlime.class, 5, 4, 4);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.tfstronghold", 5);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on a Stronghold"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartKnightStronghold(world, this, rand, chunkX, chunkZ);
		}
	},
	WORLD_TREE ( 3, "world_tree", false ) { { this.disableStructure(); } },
	YETI_CAVE  ( 2, "yeti_lairs", true , TwilightForestMod.prefix("progress_lich") ) {
		{
			this.enableDecorations().enableTerrainAlterations();

			MapGenStructureIO.registerStructure(StructureStartYetiCave.class, "TFYeti");
			MapGenStructureIO.registerStructureComponent(ComponentTFYetiCave.class, "TFYeti");

			this.addMonster(EntityTFYeti.class, 10, 4, 4);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.yeticave", 3);

			book.setTagInfo("pages" , bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title" , new NBTTagString("Notes on an Icy Cave"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartYetiCave(world, this, rand, chunkX, chunkZ);
		}
	},
	// TODO split cloud giants from this
	TROLL_CAVE ( 4, "troll_lairs", true, TwilightForestMod.prefix("progress_merge") ) {
		{
			this.enableDecorations().enableTerrainAlterations().disableProtectionAura();

			TFTrollCavePieces.registerPieces();

			this.addMonster(EntityCreeper.class, 5, 4, 4)
					.addMonster(EntitySkeleton.class, 10, 4, 4)
					.addMonster(EntityTFTroll.class, 20, 4, 4)
					.addMonster(EntityWitch.class, 5, 1, 1)
					// cloud monsters
					.addMonster(1, EntityTFGiantMiner.class, 10, 1, 4)
					.addMonster(1, EntityTFArmoredGiant.class, 10, 1, 4);
		}

		@Override
		protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

			addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.trollcave", 3);

			book.setTagInfo("pages", bookPages);
			book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
			book.setTagInfo("title", new NBTTagString("Notes on the Highlands"));
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartTrollCave(world, this, rand, chunkX, chunkZ);
		}
	},
	FINAL_CASTLE ( 4, "final_castle", true, TwilightForestMod.prefix("progress_troll") ) {
		{
			TFFinalCastlePieces.registerFinalCastlePieces();

			// plain parts of the castle, like the tower maze
			this.addMonster(EntityTFKobold.class, 10, 4, 4)
					.addMonster(EntityTFAdherent.class, 10, 1, 1)
					.addMonster(EntityTFHarbingerCube.class, 10, 1, 1)
					.addMonster(EntityEnderman.class, 10, 1, 1)
					// internal castle
					.addMonster(1, EntityTFKobold.class, 10, 4, 4)
					.addMonster(1, EntityTFAdherent.class, 10, 1, 1)
					.addMonster(1, EntityTFHarbingerCube.class, 10, 1, 1)
					.addMonster(1, EntityTFArmoredGiant.class, 10, 1, 1)
					// dungeons
					.addMonster(2, EntityTFAdherent.class, 10, 1, 1)
					// forge
					.addMonster(3, EntityBlaze.class, 10, 1, 1);
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartFinalCastle(world, this, rand, chunkX, chunkZ);
		}
	},
	MUSHROOM_TOWER ( 2, "mushroom_tower", true ) {
		{
			TFMushroomTowerPieces.registerPieces();
		}

		@Override
		public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
			return new StructureStartMushroomTower(world, this, rand, chunkX, chunkZ);
		}
	};

	public final int size;
	public final String name;
	private final boolean shouldHaveFeatureGenerator;
	public boolean areChunkDecorationsEnabled;
	public boolean isStructureEnabled;
	public boolean isTerrainAltered;
	private List<List<SpawnListEntry>> spawnableMonsterLists;
	private List<SpawnListEntry> ambientCreatureList;
	private List<SpawnListEntry> waterCreatureList;
	private final ResourceLocation[] requiredAdvancements;
	public boolean hasProtectionAura;

	private long lastSpawnedHintMonsterTime;

	private static final String BOOK_AUTHOR = "A Forgotten Explorer";

	private static final TFFeature[] VALUES = values();

	private static final int maxSize = Arrays.stream(VALUES).mapToInt(v -> v.size).max().orElse(0);

	TFFeature(int size, String name, boolean featureGenerator, ResourceLocation... requiredAdvancements) {
		this.size = size;
		this.name = name;
		this.areChunkDecorationsEnabled = false;
		this.isStructureEnabled = true;
		this.isTerrainAltered = false;
		this.spawnableMonsterLists = new ArrayList<>();
		this.ambientCreatureList = new ArrayList<>();
		this.waterCreatureList = new ArrayList<>();
		this.hasProtectionAura = true;

		ambientCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));

		this.requiredAdvancements = requiredAdvancements;

		shouldHaveFeatureGenerator = featureGenerator;
	}

	static void init() {}

	public static int getCount() {
		return VALUES.length;
	}

	public static int getMaxSize() {
		return maxSize;
	}

	@Nullable
	public MapGenTFMajorFeature createFeatureGenerator() {
		return this.shouldHaveFeatureGenerator ? new MapGenTFMajorFeature(this) : null;
	}

	/**
	 * doesn't require modid
	 */
	public static TFFeature getFeatureByName(String name) {
		for (TFFeature feature : VALUES) {
			if (feature.name.equalsIgnoreCase(name)) {
				return feature;
			}
		}
		return NOTHING;
	}

	/**
	 * modid sensitive
	 */
	public static TFFeature getFeatureByName(ResourceLocation name) {
		if (name.getNamespace().equalsIgnoreCase(TwilightForestMod.ID)) {
			return getFeatureByName(name.getPath());
		}
		return NOTHING;
	}

	public static TFFeature getFeatureByID(int id) {
		return id < VALUES.length ? VALUES[id] : NOTHING;
	}

	public static int getFeatureID(int mapX, int mapZ, World world) {
		return getFeatureAt(mapX, mapZ, world).ordinal();
	}

	public static TFFeature getFeatureAt(int mapX, int mapZ, World world) {
		return generateFeature(mapX >> 4, mapZ >> 4, world);
	}

	public static boolean isInFeatureChunk(World world, int mapX, int mapZ) {
		int chunkX = mapX >> 4;
		int chunkZ = mapZ >> 4;
		BlockPos cc = getNearestCenterXYZ(chunkX, chunkZ, world);

		return chunkX == (cc.getX() >> 4) && chunkZ == (cc.getZ() >> 4);
	}

	/**
	 * Turns on biome-specific decorations like grass and trees near this feature.
	 */
	public TFFeature enableDecorations() {
		this.areChunkDecorationsEnabled = true;
		return this;
	}

	/**
	 * Tell the chunkgenerator that we don't have an associated structure.
	 */
	public TFFeature disableStructure() {
		this.isStructureEnabled = false;
		return this;
	}

	/**
	 * Tell the chunkgenerator that we want the terrain changed nearby.
	 */
	public TFFeature enableTerrainAlterations() {
		this.isTerrainAltered = true;
		return this;
	}

	public TFFeature disableProtectionAura() {
		this.hasProtectionAura = false;
		return this;
	}

	/**
	 * Add a monster to spawn list 0
	 */
	public TFFeature addMonster(Class<? extends EntityLiving> monsterClass, int weight, int minGroup, int maxGroup) {
		this.addMonster(0, monsterClass, weight, minGroup, maxGroup);
		return this;
	}

	/**
	 * Add a monster to a specific spawn list
	 */
	public TFFeature addMonster(int listIndex, Class<? extends EntityLiving> monsterClass, int weight, int minGroup, int maxGroup) {
		List<SpawnListEntry> monsterList;
		if (this.spawnableMonsterLists.size() > listIndex) {
			monsterList = this.spawnableMonsterLists.get(listIndex);
		} else {
			monsterList = new ArrayList<>();
			this.spawnableMonsterLists.add(listIndex, monsterList);
		}

		monsterList.add(new SpawnListEntry(monsterClass, weight, minGroup, maxGroup));
		return this;
	}

	/**
	 * Add a water creature
	 */
	public TFFeature addWaterCreature(Class<? extends EntityLiving> monsterClass, int weight, int minGroup, int maxGroup) {
		this.waterCreatureList.add(new SpawnListEntry(monsterClass, weight, minGroup, maxGroup));
		return this;
	}

	/**
	 * @return The type of feature directly at the specified Chunk coordinates
	 */
	public static TFFeature getFeatureDirectlyAt(int chunkX, int chunkZ, World world) {
		if (isInFeatureChunk(world, chunkX << 4, chunkZ << 4)) {
			return getFeatureAt(chunkX << 4, chunkZ << 4, world);
		}
		return NOTHING;
	}

	/**
	 * What feature would go in this chunk.  Called when we know there is a feature, but there is no cache data,
	 * either generating this chunk for the first time, or using the magic map to forecast beyond the edge of the world.
	 */
	public static TFFeature generateFeature(int chunkX, int chunkZ, World world) {
		// FIXME Remove block comment start-marker to enable debug
		/*if (true) {
			return NAGA_COURTYARD;
		}//*/

		// set the chunkX and chunkZ to the center of the biome
		chunkX = Math.round(chunkX / 16F) * 16;
		chunkZ = Math.round(chunkZ / 16F) * 16;

		// what biome is at the center of the chunk?
		Biome biomeAt = world.getBiome(new BlockPos((chunkX << 4) + 8, 0, (chunkZ << 4) + 8));

		// does the biome have a feature?
		if (biomeAt instanceof TFBiomeBase) {
			TFFeature biomeFeature = ((TFBiomeBase) biomeAt).containedFeature;
			if (biomeFeature != NOTHING) {
				return biomeFeature;
			}
		}

		int regionOffsetX = Math.abs((chunkX + 64 >> 4) % 8);
		int regionOffsetZ = Math.abs((chunkZ + 64 >> 4) % 8);

		// plant two lich towers near the center of each 2048x2048 map area
		if ((regionOffsetX == 4 && regionOffsetZ == 5) || (regionOffsetX == 4 && regionOffsetZ == 3)) {
			return LICH_TOWER;
		}

		// also two nagas
		if ((regionOffsetX == 5 && regionOffsetZ == 4) || (regionOffsetX == 3 && regionOffsetZ == 4)) {
			return NAGA_COURTYARD;
		}

		// get random value
		// okay, well that takes care of most special cases
		switch (new Random(world.getSeed() + chunkX * 25117 + chunkZ * 151121).nextInt(16)) {
			default:
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return SMALL_HILL;
			case 6:
			case 7:
			case 8:
				return MEDIUM_HILL;
			case 9:
				return LARGE_HILL;
			case 10:
			case 11:
				return HEDGE_MAZE;
			case 12:
			case 13:
				return NAGA_COURTYARD;
			case 14:
			case 15:
				return LICH_TOWER;
		}
	}

	/**
	 * Returns the feature nearest to the specified chunk coordinates.
	 */
	public static TFFeature getNearestFeature(int cx, int cz, World world) {
		return getNearestFeature(cx, cz, world, null);
	}

	/**
	 * Returns the feature nearest to the specified chunk coordinates.
	 *
	 * If a non-null {@code center} is provided and a valid feature is found,
	 * it will be set to relative block coordinates indicating the center of
	 * that feature relative to the current chunk block coordinate system.
	 */
	public static TFFeature getNearestFeature(int cx, int cz, World world, @Nullable IntPair center) {

		int diam = maxSize * 2 + 1;
		TFFeature[] features = new TFFeature[diam * diam];

		for (int rad = 1; rad <= maxSize; rad++) {
			for (int x = -rad; x <= rad; x++) {
				for (int z = -rad; z <= rad; z++) {

					int idx = (x + maxSize) * diam + (z + maxSize);
					TFFeature directlyAt = features[idx];
					if (directlyAt == null) {
						features[idx] = directlyAt = getFeatureDirectlyAt(x + cx, z + cz, world);
					}

					if (directlyAt.size == rad) {
						if (center != null) {
							center.x = (x << 4) + 8;
							center.z = (z << 4) + 8;
						}
						return directlyAt;
					}
				}
			}
		}

		return NOTHING;
	}

	// [Vanilla Copy] from MapGenStructure#findNearestStructurePosBySpacing; changed 2nd param to be TFFeature instead of MapGenStructure
	@Nullable
	public static BlockPos findNearestFeaturePosBySpacing(World worldIn, TFFeature feature, BlockPos blockPos, int p_191069_3_, int p_191069_4_, int p_191069_5_, boolean p_191069_6_, int p_191069_7_, boolean findUnexplored) {
		int i = blockPos.getX() >> 4;
		int j = blockPos.getZ() >> 4;
		int k = 0;

		for (Random random = new Random(); k <= p_191069_7_; ++k) {
			for (int l = -k; l <= k; ++l) {
				boolean flag = l == -k || l == k;

				for (int i1 = -k; i1 <= k; ++i1) {
					boolean flag1 = i1 == -k || i1 == k;

					if (flag || flag1) {
						int j1 = i + p_191069_3_ * l;
						int k1 = j + p_191069_3_ * i1;

						if (j1 < 0) {
							j1 -= p_191069_3_ - 1;
						}

						if (k1 < 0) {
							k1 -= p_191069_3_ - 1;
						}

						int l1 = j1 / p_191069_3_;
						int i2 = k1 / p_191069_3_;
						Random random1 = worldIn.setRandomSeed(l1, i2, p_191069_5_);
						l1 = l1 * p_191069_3_;
						i2 = i2 * p_191069_3_;

						if (p_191069_6_) {
							l1 = l1 + (random1.nextInt(p_191069_3_ - p_191069_4_) + random1.nextInt(p_191069_3_ - p_191069_4_)) / 2;
							i2 = i2 + (random1.nextInt(p_191069_3_ - p_191069_4_) + random1.nextInt(p_191069_3_ - p_191069_4_)) / 2;
						} else {
							l1 = l1 + random1.nextInt(p_191069_3_ - p_191069_4_);
							i2 = i2 + random1.nextInt(p_191069_3_ - p_191069_4_);
						}

						MapGenBase.setupChunkSeed(worldIn.getSeed(), random, l1, i2);
						random.nextInt();

						// Check changed for TFFeature
						if (getFeatureAt(l1 << 4, i2 << 4, worldIn) == feature) {
							if (!findUnexplored || !worldIn.isChunkGeneratedAt(l1, i2)) {
								return new BlockPos((l1 << 4) + 8, 64, (i2 << 4) + 8);
							}
						} else if (k == 0) {
							break;
						}
					}
				}

				if (k == 0) {
					break;
				}
			}
		}

		return null;
	}

	/**
	 * @return The feature in the chunk "region"
	 */
	public static TFFeature getFeatureForRegion(int chunkX, int chunkZ, World world) {
		//just round to the nearest multiple of 16 chunks?
		int featureX = Math.round(chunkX / 16F) * 16;
		int featureZ = Math.round(chunkZ / 16F) * 16;

		return generateFeature(featureX, featureZ, world);
	}

	/**
	 * @return The feature in the chunk "region"
	 */
	public static TFFeature getFeatureForRegionPos(int posX, int posZ, World world) {
		return getFeatureForRegion(posX >> 4, posZ >> 4, world);
	}

	/**
	 * Given some coordinates, return the center of the nearest feature.
	 * <p>
	 * At the moment, with how features are distributed, just get the closest multiple of 256 and add +8 in both directions.
	 * <p>
	 * Maybe in the future we'll have to actually search for a feature chunk nearby, but for now this will work.
	 */
	public static BlockPos getNearestCenterXYZ(int cx, int cz, World world) {
		// generate random number for the whole biome area
		int regionX = (cx + 8) >> 4;
		int regionZ = (cz + 8) >> 4;

		long seed = (long) (regionX * 3129871) ^ (long) regionZ * 116129781L;
		seed = seed * seed * 42317861L + seed * 7L;

		int num0 = (int) (seed >> 12 & 3L);
		int num1 = (int) (seed >> 15 & 3L);
		int num2 = (int) (seed >> 18 & 3L);
		int num3 = (int) (seed >> 21 & 3L);

		// slightly randomize center of biome (+/- 3)
		int centerX = 8 + num0 - num1;
		int centerZ = 8 + num2 - num3;

		// centers are offset strangely depending on +/-
		int ccz;
		if (regionZ >= 0) {
			ccz = (regionZ * 16 + centerZ - 8) * 16 + 8;
		} else {
			ccz = (regionZ * 16 + (16 - centerZ) - 8) * 16 + 9;
		}

		int ccx;
		if (regionX >= 0) {
			ccx = (regionX * 16 + centerX - 8) * 16 + 8;
		} else {
			ccx = (regionX * 16 + (16 - centerX) - 8) * 16 + 9;
		}

		return new BlockPos(ccx, TFWorld.SEALEVEL, ccz);//  Math.abs(chunkX % 16) == centerX && Math.abs(chunkZ % 16) == centerZ;
	}

	/**
	 * Returns a list of hostile monsters.  Are we ever going to need passive or water creatures?
	 */
	public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
		switch (creatureType) {
			case MONSTER:
				return this.getSpawnableList(EnumCreatureType.MONSTER, 0);
			case AMBIENT:
				return this.ambientCreatureList;
			case WATER_CREATURE:
				return this.waterCreatureList;
			default:
				return new ArrayList<>();
		}
	}

	/**
	 * Returns a list of hostile monsters in the specified indexed category
	 */
	public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType, int index) {
		if (creatureType != EnumCreatureType.MONSTER) {
			return getSpawnableList(creatureType);
		}
		if (index >= 0 && index < this.spawnableMonsterLists.size()) {
			return this.spawnableMonsterLists.get(index);
		}
		return new ArrayList<>();
	}

	public boolean doesPlayerHaveRequiredAdvancements(EntityPlayer player) {
		return PlayerHelper.doesPlayerHaveRequiredAdvancements(player, requiredAdvancements);
	}

	/**
	 * Try to spawn a hint monster near the specified player
	 */
	public void trySpawnHintMonster(World world, EntityPlayer player) {
		this.trySpawnHintMonster(world, player, new BlockPos(player));
	}

	/**
	 * Try several times to spawn a hint monster
	 */
	public void trySpawnHintMonster(World world, EntityPlayer player, BlockPos pos) {
		// check if the timer is valid
		long currentTime = world.getTotalWorldTime();

		// if someone set the time backwards, fix the spawn timer
		if (currentTime < this.lastSpawnedHintMonsterTime) {
			this.lastSpawnedHintMonsterTime = 0;
		}

		if (currentTime - this.lastSpawnedHintMonsterTime > 1200) {
			// okay, time is good, try several times to spawn one
			for (int i = 0; i < 20; i++) {
				if (didSpawnHintMonster(world, player, pos)) {
					this.lastSpawnedHintMonsterTime = currentTime;
					break;
				}
			}
		}
	}

	/**
	 * Try once to spawn a hint monster near the player.  Return true if we did.
	 * <p>
	 * We could change up the monster depending on what feature this is, but we currently are not doing that
	 */
	private boolean didSpawnHintMonster(World world, EntityPlayer player, BlockPos pos) {
		// find a target point
		int dx = world.rand.nextInt(16) - world.rand.nextInt(16);
		int dy = world.rand.nextInt( 4) - world.rand.nextInt( 4);
		int dz = world.rand.nextInt(16) - world.rand.nextInt(16);

		// make our hint monster
		EntityTFKobold hinty = new EntityTFKobold(world);
		hinty.moveToBlockPosAndAngles(pos.add(dx, dy, dz), 0f, 0f);

		// check if the bounding box is clear
		if (hinty.isNotColliding() && hinty.getEntitySenses().canSee(player)) {

			// add items and hint book
			ItemStack book = this.createHintBook();

			hinty.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, book);
			hinty.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);
			//hinty.setDropItemsWhenDead(true);

			world.spawnEntity(hinty);
			return true;
		}

		return false;
	}

	/**
	 * Create a hint book for the specified feature.  Only features with block protection will need this.
	 */
	public ItemStack createHintBook() {
		ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
		this.addBookInformation(book, new NBTTagList());
		return book;
	}

	protected void addBookInformation(ItemStack book, NBTTagList bookPages) {

		addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.unknown", 2);

		book.setTagInfo("pages", bookPages);
		book.setTagInfo("author", new NBTTagString(BOOK_AUTHOR));
		book.setTagInfo("title", new NBTTagString("Notes on the Unexplained"));
	}

	public StructureStartTFAbstract provideStructureStart(World world, Random rand, int chunkX, int chunkZ) {
		return new StructureStartNothing(world, rand, chunkX, chunkZ);
	}

	private static void addTranslatedPages(NBTTagList bookPages, String translationKey, int pageCount) {
		for (int i = 1; i <= pageCount; i++) {
			bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentTranslation(translationKey + "." + i))));
		}
	}
}
