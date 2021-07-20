package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.client.model.entity.*;
import twilightforest.client.renderer.entity.*;
import twilightforest.entity.boss.*;
import twilightforest.entity.passive.*;
import twilightforest.entity.projectile.*;
import twilightforest.item.TransformPowderItem;
import twilightforest.item.TFItems;
import twilightforest.util.TFEntityNames;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TFEntities {

	public static final EntitySpawnPlacementRegistry.PlacementType ON_ICE = EntitySpawnPlacementRegistry.PlacementType.create("TF_ON_ICE", (world, pos, entityType) -> {
		BlockState state = world.getBlockState(pos.down());
		Block block = state.getBlock();
		Material material = state.getMaterial();
		BlockPos up = pos.up();
		return (material == Material.ICE || material == Material.PACKED_ICE) && block != Blocks.BEDROCK && block != Blocks.BARRIER && WorldEntitySpawner.func_234968_a_(world, pos, world.getBlockState(pos), world.getFluidState(pos), entityType) && WorldEntitySpawner.func_234968_a_(world, up, world.getBlockState(up), world.getFluidState(up), entityType);
	});

	public static final EntitySpawnPlacementRegistry.PlacementType CLOUDS = EntitySpawnPlacementRegistry.PlacementType.create("CLOUD_DWELLERS", (world, pos, entityType) -> {
		BlockState state = world.getBlockState(pos.down());
		Block block = state.getBlock();
		BlockPos up = pos.up();
		return (block == TFBlocks.wispy_cloud.get() || block == TFBlocks.wispy_cloud.get()) && block != Blocks.BEDROCK && block != Blocks.BARRIER && WorldEntitySpawner.func_234968_a_(world, pos, world.getBlockState(pos), world.getFluidState(pos), entityType) && WorldEntitySpawner.func_234968_a_(world, up, world.getBlockState(up), world.getFluidState(up), entityType);
	});

	private static final List<EntityType<?>> ALL = new ArrayList<>();
	public static final EntityType<BoarEntity> wild_boar = make(TFEntityNames.WILD_BOAR, BoarEntity::new, EntityClassification.CREATURE, 0.9F, 0.9F);
	public static final EntityType<BighornEntity> bighorn_sheep = make(TFEntityNames.BIGHORN_SHEEP, BighornEntity::new, EntityClassification.CREATURE, 0.9F, 1.3F);
	public static final EntityType<DeerEntity> deer = make(TFEntityNames.DEER, DeerEntity::new, EntityClassification.CREATURE, 0.7F, 1.8F);
	public static final EntityType<RedcapEntity> redcap = make(TFEntityNames.REDCAP, RedcapEntity::new, EntityClassification.MONSTER, 0.9F, 1.4F);
	public static final EntityType<SwarmSpiderEntity> swarm_spider = make(TFEntityNames.SWARM_SPIDER, SwarmSpiderEntity::new, EntityClassification.MONSTER, 0.8F, 0.4F);
	public static final EntityType<NagaEntity> naga = make(TFEntityNames.NAGA, NagaEntity::new, EntityClassification.MONSTER, 1.75f, 3.0f);
	public static final EntityType<SkeletonDruidEntity> skeleton_druid = make(TFEntityNames.SKELETON_DRUID, SkeletonDruidEntity::new, EntityClassification.MONSTER, 0.6F, 1.99F);
	public static final EntityType<HostileWolfEntity> hostile_wolf = make(TFEntityNames.HOSTILE_WOLF, HostileWolfEntity::new, EntityClassification.MONSTER, 0.6F, 0.85F);
	public static final EntityType<WraithEntity> wraith = make(TFEntityNames.WRAITH, WraithEntity::new, EntityClassification.MONSTER);
	public static final EntityType<HedgeSpiderEntity> hedge_spider = make(TFEntityNames.HEDGE_SPIDER, HedgeSpiderEntity::new, EntityClassification.MONSTER, 1.4F, 0.9F);
	public static final EntityType<HydraEntity> hydra = make(TFEntityNames.HYDRA, HydraEntity::new, EntityClassification.MONSTER, 16F, 12F);
	public static final EntityType<LichEntity> lich = make(TFEntityNames.LICH, LichEntity::new, EntityClassification.MONSTER, 1.1F, 2.1F);
	public static final EntityType<PenguinEntity> penguin = make(TFEntityNames.PENGUIN, PenguinEntity::new, EntityClassification.CREATURE, 0.5F, 0.9F);
	public static final EntityType<LichMinionEntity> lich_minion = make(TFEntityNames.LICH_MINION, LichMinionEntity::new, EntityClassification.MONSTER, 0.6F, 1.95F);
	public static final EntityType<LoyalZombieEntity> loyal_zombie = make(TFEntityNames.LOYAL_ZOMBIE, LoyalZombieEntity::new, EntityClassification.MONSTER);
	public static final EntityType<TinyBirdEntity> tiny_bird = make(TFEntityNames.TINY_BIRD, TinyBirdEntity::new, EntityClassification.CREATURE, 0.3F, 0.3F);
	public static final EntityType<SquirrelEntity> squirrel = make(TFEntityNames.SQUIRREL, SquirrelEntity::new, EntityClassification.CREATURE, 0.3F, 0.5F);
	public static final EntityType<BunnyEntity> bunny = make(TFEntityNames.BUNNY, BunnyEntity::new, EntityClassification.CREATURE, 0.3F, 0.6F);
	public static final EntityType<RavenEntity> raven = make(TFEntityNames.RAVEN, RavenEntity::new, EntityClassification.CREATURE, 0.3F, 0.5F);
	public static final EntityType<QuestRamEntity> quest_ram = make(TFEntityNames.QUEST_RAM, QuestRamEntity::new, EntityClassification.CREATURE, 1.25F, 2.9F);
	public static final EntityType<KoboldEntity> kobold = make(TFEntityNames.KOBOLD, KoboldEntity::new, EntityClassification.MONSTER, 0.8F, 1.1F);
	public static final EntityType<MosquitoSwarmEntity> mosquito_swarm = make(TFEntityNames.MOSQUITO_SWARM, MosquitoSwarmEntity::new, EntityClassification.MONSTER, 0.7F, 1.9F);
	public static final EntityType<DeathTomeEntity> death_tome = make(TFEntityNames.DEATH_TOME, DeathTomeEntity::new, EntityClassification.MONSTER);
	public static final EntityType<MinotaurEntity> minotaur = make(TFEntityNames.MINOTAUR, MinotaurEntity::new, EntityClassification.MONSTER, 0.6F, 2.1F);
	public static final EntityType<MinoshroomEntity> minoshroom = make(TFEntityNames.MINOSHROOM, MinoshroomEntity::new, EntityClassification.MONSTER, 1.49F, 2.5F);
	public static final EntityType<FireBeetleEntity> fire_beetle = make(TFEntityNames.FIRE_BEETLE, FireBeetleEntity::new, EntityClassification.MONSTER, 1.1F, 0.5F);
	public static final EntityType<SlimeBeetleEntity> slime_beetle = make(TFEntityNames.SLIME_BEETLE, SlimeBeetleEntity::new, EntityClassification.MONSTER, 0.9F, 0.5F);
	public static final EntityType<PinchBeetleEntity> pinch_beetle = make(TFEntityNames.PINCH_BEETLE, PinchBeetleEntity::new, EntityClassification.MONSTER, 1.2F, 0.5F);
	public static final EntityType<MazeSlimeEntity> maze_slime = make(TFEntityNames.MAZE_SLIME, MazeSlimeEntity::new, EntityClassification.MONSTER, 2.04F, 2.04F);
	public static final EntityType<RedcapSapperEntity> redcap_sapper = make(TFEntityNames.REDCAP_SAPPER, RedcapSapperEntity::new, EntityClassification.MONSTER, 0.9F, 1.4F);
	public static final EntityType<MistWolfEntity> mist_wolf = make(TFEntityNames.MIST_WOLF, MistWolfEntity::new, EntityClassification.MONSTER, 1.4F, 1.9F);
	public static final EntityType<KingSpiderEntity> king_spider = make(TFEntityNames.KING_SPIDER, KingSpiderEntity::new, EntityClassification.MONSTER, 1.6F, 1.6F);
	public static final EntityType<CarminiteGhastlingEntity> mini_ghast = make(TFEntityNames.MINI_GHAST, CarminiteGhastlingEntity::new, EntityClassification.MONSTER, 1.1F, 1.5F);
	public static final EntityType<CarminiteGhastguardEntity> tower_ghast = make(TFEntityNames.TOWER_GHAST, CarminiteGhastguardEntity::new, EntityClassification.MONSTER, 4.0F, 6.0F);
	public static final EntityType<CarminiteGolemEntity> tower_golem = make(TFEntityNames.TOWER_GOLEM, CarminiteGolemEntity::new, EntityClassification.MONSTER, 1.4F, 2.9F);
	public static final EntityType<TowerwoodBorerEntity> tower_termite = make(TFEntityNames.TOWER_TERMITE, TowerwoodBorerEntity::new, EntityClassification.MONSTER, 0.4F, 0.3F);
	public static final EntityType<TowerBroodlingEntity> tower_broodling = make(TFEntityNames.TOWER_BROODLING, TowerBroodlingEntity::new, EntityClassification.MONSTER, 0.7F, 0.5F);
	public static final EntityType<UrGhastEntity> ur_ghast = make(TFEntityNames.UR_GHAST, UrGhastEntity::new, EntityClassification.MONSTER, 14.0F, 18.0F);
	public static final EntityType<BlockChainGoblinEntity> blockchain_goblin = make(TFEntityNames.BLOCKCHAIN_GOBLIN, BlockChainGoblinEntity::new, EntityClassification.MONSTER, 0.9F, 1.4F);
	public static final EntityType<UpperGoblinKnightEntity> goblin_knight_upper = make(TFEntityNames.GOBLIN_KNIGHT_UPPER, UpperGoblinKnightEntity::new, EntityClassification.MONSTER, 1.1F, 1.3F);
	public static final EntityType<LowerGoblinKnightEntity> goblin_knight_lower = make(TFEntityNames.GOBLIN_KNIGHT_LOWER, LowerGoblinKnightEntity::new, EntityClassification.MONSTER, 0.7F, 1.1F);
	public static final EntityType<HelmetCrabEntity> helmet_crab = make(TFEntityNames.HELMET_CRAB, HelmetCrabEntity::new, EntityClassification.MONSTER, 0.8F, 1.1F);
	public static final EntityType<KnightPhantomEntity> knight_phantom = make(TFEntityNames.KNIGHT_PHANTOM, KnightPhantomEntity::new, EntityClassification.MONSTER, 1.5F, 3.0F);
	public static final EntityType<YetiEntity> yeti = make(TFEntityNames.YETI, YetiEntity::new, EntityClassification.MONSTER, 1.4F, 2.4F);
	public static final EntityType<AlphaYetiEntity> yeti_alpha = make(TFEntityNames.YETI_ALPHA, AlphaYetiEntity::new, EntityClassification.MONSTER, 3.8F, 5.0F);
	public static final EntityType<WinterWolfEntity> winter_wolf = make(TFEntityNames.WINTER_WOLF, WinterWolfEntity::new, EntityClassification.MONSTER, 1.4F, 1.9F);
	public static final EntityType<SnowGuardianEntity> snow_guardian = make(TFEntityNames.SNOW_GUARDIAN, SnowGuardianEntity::new, EntityClassification.MONSTER, 0.6F, 1.8F);
	public static final EntityType<StableIceCoreEntity> stable_ice_core = make(TFEntityNames.STABLE_ICE_CORE, StableIceCoreEntity::new, EntityClassification.MONSTER, 0.8F, 1.8F);
	public static final EntityType<UnstableIceCoreEntity> unstable_ice_core = make(TFEntityNames.UNSTABLE_ICE_CORE, UnstableIceCoreEntity::new, EntityClassification.MONSTER, 0.8F, 1.8F);
	public static final EntityType<SnowQueenEntity> snow_queen = make(TFEntityNames.SNOW_QUEEN, SnowQueenEntity::new, EntityClassification.MONSTER, 0.7F, 2.2F);
	public static final EntityType<TrollEntity> troll = make(TFEntityNames.TROLL, TrollEntity::new, EntityClassification.MONSTER, 1.4F, 2.4F);
	public static final EntityType<GiantMinerEntity> giant_miner = make(TFEntityNames.GIANT_MINER, GiantMinerEntity::new, EntityClassification.MONSTER, 2.4F, 7.2F);
	public static final EntityType<ArmoredGiantEntity> armored_giant = make(TFEntityNames.ARMORED_GIANT, ArmoredGiantEntity::new, EntityClassification.MONSTER, 2.4F, 7.2F);
	public static final EntityType<IceCrystalEntity> ice_crystal = make(TFEntityNames.ICE_CRYSTAL, IceCrystalEntity::new, EntityClassification.MONSTER, 0.6F, 1.8F);
	public static final EntityType<HarbingerCubeEntity> harbinger_cube = make(TFEntityNames.HARBINGER_CUBE, HarbingerCubeEntity::new, EntityClassification.MONSTER, 1.9F, 2.4F);
	public static final EntityType<AdherentEntity> adherent = make(TFEntityNames.ADHERENT, AdherentEntity::new, EntityClassification.MONSTER, 0.8F, 2.2F);
	public static final EntityType<RovingCubeEntity> roving_cube = make(TFEntityNames.ROVING_CUBE, RovingCubeEntity::new, EntityClassification.MONSTER, 1.2F, 2.1F);
	//public static final EntityType<EntityTFCastleGuardian> castle_guardian = make(TFEntityNames.CASTLE_GUARDIAN, EntityTFCastleGuardian::new, EntityClassification.MONSTER, 1.8F, 2.4F);
	public static final EntityType<PlateauBossEntity> plateau_boss = make(TFEntityNames.PLATEAU_BOSS, PlateauBossEntity::new, EntityClassification.MONSTER, 1F, 1F);

	public static final EntityType<NatureBoltEntity> nature_bolt = build(TFEntityNames.NATURE_BOLT, makeCastedBuilder(NatureBoltEntity.class, NatureBoltEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(5));
	public static final EntityType<LichBoltEntity> lich_bolt = build(TFEntityNames.LICH_BOLT, makeCastedBuilder(LichBoltEntity.class, LichBoltEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(2));
	public static final EntityType<TwilightWandBoltEntity> wand_bolt = build(TFEntityNames.WAND_BOLT, makeCastedBuilder(TwilightWandBoltEntity.class, TwilightWandBoltEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(5));
	public static final EntityType<TomeBoltEntity> tome_bolt = build(TFEntityNames.TOME_BOLT, makeCastedBuilder(TomeBoltEntity.class, TomeBoltEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(5));
	public static final EntityType<HydraMortarHead> hydra_mortar = build(TFEntityNames.HYDRA_MORTAR, makeCastedBuilder(HydraMortarHead.class, HydraMortarHead::new, EntityClassification.MISC).size(0.75F, 0.75F).setTrackingRange(150));
	public static final EntityType<LichBombEntity> lich_bomb = build(TFEntityNames.LICH_BOMB, makeCastedBuilder(LichBombEntity.class, LichBombEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<MoonwormShotEntity> moonworm_shot = build(TFEntityNames.MOONWORM_SHOT, makeCastedBuilder(MoonwormShotEntity.class, MoonwormShotEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<CicadaShotEntity> cicada_shot = build(TFEntityNames.CICADA_SHOT, makeCastedBuilder(CicadaShotEntity.class, CicadaShotEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<SlimeProjectileEntity> slime_blob = build(TFEntityNames.SLIME_BLOB, makeCastedBuilder(SlimeProjectileEntity.class, SlimeProjectileEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<CharmEffectEntity> charm_effect = make(TFEntityNames.CHARM_EFFECT, CharmEffectEntity::new, EntityClassification.MISC, 0.25F, 0.25F);
	public static final EntityType<ThrownWepEntity> thrown_wep = make(TFEntityNames.THROWN_WEP, ThrownWepEntity::new, EntityClassification.MISC, 0.5F, 0.5F);
	public static final EntityType<FallingIceEntity> falling_ice = make(TFEntityNames.FALLING_ICE, FallingIceEntity::new, EntityClassification.MISC, 2.98F, 2.98F);
	public static final EntityType<IceBombEntity> thrown_ice = build(TFEntityNames.THROWN_ICE, makeCastedBuilder(IceBombEntity.class, IceBombEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setUpdateInterval(2));
	public static final EntityType<SeekerArrowEntity> seeker_arrow = build(TFEntityNames.SEEKER_ARROW, makeCastedBuilder(SeekerArrowEntity.class, SeekerArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setTrackingRange(150).setUpdateInterval(1));
	public static final EntityType<IceArrowEntity> ice_arrow = build(TFEntityNames.ICE_ARROW, makeCastedBuilder(IceArrowEntity.class, IceArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setTrackingRange(150).setUpdateInterval(1));
	public static final EntityType<IceSnowballEntity> ice_snowball = build(TFEntityNames.ICE_SNOWBALL, makeCastedBuilder(IceSnowballEntity.class, IceSnowballEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<ChainBlockEntity> chain_block = build(TFEntityNames.CHAIN_BLOCK, makeCastedBuilder(ChainBlockEntity.class, ChainBlockEntity::new, EntityClassification.MISC).size(0.6F, 0.6F).setUpdateInterval(1));
	public static final EntityType<CubeOfAnnihilationEntity> cube_of_annihilation = build(TFEntityNames.CUBE_OF_ANNIHILATION, makeCastedBuilder(CubeOfAnnihilationEntity.class, CubeOfAnnihilationEntity::new, EntityClassification.MISC).size(1F, 1F).setUpdateInterval(1));
	public static final EntityType<SlideBlockEntity> slider = build(TFEntityNames.SLIDER, makeCastedBuilder(SlideBlockEntity.class, SlideBlockEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).setUpdateInterval(1));
	//public static final EntityType<EntityTFBoggard> boggard = make(TFEntityNames.BOGGARD, EntityTFBoggard::new, EntityClassification.MONSTER, 0.8F, 1.1F);
	public static final EntityType<RisingZombieEntity> rising_zombie = make(TFEntityNames.RISING_ZOMBIE, RisingZombieEntity::new, EntityClassification.MONSTER, 0.6F, 1.95F);
	public static final EntityType<ProtectionBoxEntity> protection_box = build(TFEntityNames.PROTECTION_BOX, makeCastedBuilder(ProtectionBoxEntity.class, ProtectionBoxEntity::new, EntityClassification.MISC).disableSerialization().disableSummoning().size(0, 0));

	private static <E extends Entity> EntityType<E> make(ResourceLocation id, EntityType.IFactory<E> factory, EntityClassification classification, float width, float height) {
		return build(id, makeBuilder(factory, classification).size(width, height));
	}

	private static <E extends Entity> EntityType<E> make(ResourceLocation id, EntityType.IFactory<E> factory, EntityClassification classification) {
		return make(id, factory, classification, 0.6F, 1.8F);
	}

	@SuppressWarnings("unchecked")
	private static <E extends Entity> EntityType<E> build(ResourceLocation id, EntityType.Builder<E> builder) {
		boolean cache = SharedConstants.useDatafixers;
		SharedConstants.useDatafixers = false;
		EntityType<E> ret = (EntityType<E>) builder.build(id.toString()).setRegistryName(id);
		SharedConstants.useDatafixers = cache;
		ALL.add(ret);
		return ret;
	}

	private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.IFactory<E> factory, EntityClassification classification) {
		return makeBuilder(factory, classification);
	}

	private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.IFactory<E> factory, EntityClassification classification) {
		return EntityType.Builder.create(factory, classification).
				size(0.6F, 1.8F).
				setTrackingRange(80).
				setUpdateInterval(3).
				setShouldReceiveVelocityUpdates(true);
	}

	private static Item spawnEgg(EntityType<?> type, int color, int color2) {
		ResourceLocation eggId = new ResourceLocation(type.getRegistryName().getNamespace(), type.getRegistryName().getPath() + "_spawn_egg");
		return new SpawnEggItem(type, color, color2, TFItems.defaultBuilder()).setRegistryName(eggId);
	}

	@SubscribeEvent
	public static void registerSpawnEggs(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		r.register(spawnEgg(adherent, 0x0a0000, 0x00008b));
		r.register(spawnEgg(yeti_alpha, 0xcdcdcd, 0x29486e));
		r.register(spawnEgg(armored_giant, 0x239391, 0x9a9a9a));
		r.register(spawnEgg(bighorn_sheep, 0xdbceaf, 0xd7c771));
		r.register(spawnEgg(blockchain_goblin, 0xd3e7bc, 0x1f3fff));
		r.register(spawnEgg(tower_broodling, 0x343c14, 0xbaee02));
		r.register(spawnEgg(tower_ghast, 0xbcbcbc, 0xb77878));
		r.register(spawnEgg(mini_ghast, 0xbcbcbc, 0xa74343));
		r.register(spawnEgg(tower_golem, 0x6b3d20, 0xe2ddda));
		r.register(spawnEgg(troll, 0x9ea98f, 0xb0948e));
		r.register(spawnEgg(death_tome, 0x774e22, 0xdbcdbe));
		r.register(spawnEgg(deer, 0x7b4d2e, 0x4b241d));
		r.register(spawnEgg(bunny, 0xfefeee, 0xccaa99));
		r.register(spawnEgg(fire_beetle, 0x1d0b00, 0xcb6f25));
		r.register(spawnEgg(squirrel, 0x904f12, 0xeeeeee));
		r.register(spawnEgg(giant_miner, 0x211b52, 0x9a9a9a));
		r.register(spawnEgg(goblin_knight_lower, 0x566055, 0xd3e7bc));
		r.register(spawnEgg(harbinger_cube, 0x00000a, 0x8b0000));
		r.register(spawnEgg(hedge_spider, 0x235f13, 0x562653));
		r.register(spawnEgg(helmet_crab, 0xfb904b, 0xd3e7bc));
		r.register(spawnEgg(hostile_wolf, 0xd7d3d3, 0xab1e14));
		r.register(spawnEgg(hydra, 0x142940, 0x29806b));
		r.register(spawnEgg(ice_crystal, 0xdce9fe, 0xadcafb));
		r.register(spawnEgg(king_spider, 0x2c1a0e, 0xffc017));
		r.register(spawnEgg(knight_phantom, 0xa6673b, 0xd3e7bc));
		r.register(spawnEgg(kobold, 0x372096, 0x895d1b));
		r.register(spawnEgg(maze_slime, 0xa3a3a3, 0x2a3b17));
		r.register(spawnEgg(minoshroom, 0xa81012, 0xaa7d66));
		r.register(spawnEgg(minotaur, 0x3f3024, 0xaa7d66));
		r.register(spawnEgg(mist_wolf, 0x3a1411, 0xe2c88a));
		r.register(spawnEgg(mosquito_swarm, 0x080904, 0x2d2f21));
		r.register(spawnEgg(naga, 0xa4d316, 0x1b380b));
		r.register(spawnEgg(penguin, 0x12151b, 0xf9edd2));
		r.register(spawnEgg(pinch_beetle, 0xbc9327, 0x241609));
		r.register(spawnEgg(quest_ram, 0xfefeee, 0x33aadd));
		r.register(spawnEgg(raven, 0x000011, 0x222233));
		r.register(spawnEgg(redcap, 0x3b3a6c, 0xab1e14));
		r.register(spawnEgg(redcap_sapper, 0x575d21, 0xab1e14));
		r.register(spawnEgg(roving_cube, 0x0a0000, 0x00009b));
		r.register(spawnEgg(skeleton_druid, 0xa3a3a3, 0x2a3b17));
		r.register(spawnEgg(slime_beetle, 0x0c1606, 0x60a74c));
		r.register(spawnEgg(snow_guardian, 0xd3e7bc, 0xfefefe));
		r.register(spawnEgg(snow_queen, 0xb1b2d4, 0x87006e));
		r.register(spawnEgg(stable_ice_core, 0xa1bff3, 0x7000f8));
		r.register(spawnEgg(swarm_spider, 0x32022e, 0x17251e));
		r.register(spawnEgg(tiny_bird, 0x33aadd, 0x1188ee));
		r.register(spawnEgg(tower_termite, 0x5d2b21, 0xaca03a));
		r.register(spawnEgg(lich, 0xaca489, 0x360472));
		r.register(spawnEgg(unstable_ice_core, 0x9aacf5, 0x9b0fa5));
		r.register(spawnEgg(ur_ghast, 0xbcbcbc, 0xb77878));
		r.register(spawnEgg(wild_boar, 0x83653b, 0xffefca));
		r.register(spawnEgg(winter_wolf, 0xdfe3e5, 0xb2bcca));
		r.register(spawnEgg(wraith, 0x505050, 0x838383));
		r.register(spawnEgg(yeti, 0xdedede, 0x4675bb));
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
		evt.getRegistry().registerAll(ALL.toArray(new EntityType<?>[0]));
		((TransformPowderItem) TFItems.transformation_powder.get()).initTransformations();

		EntitySpawnPlacementRegistry.register(wild_boar, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(bighorn_sheep, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(deer, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(redcap, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(skeleton_druid, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SkeletonDruidEntity::skeletonDruidSpawnHandler);
		EntitySpawnPlacementRegistry.register(wraith, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WraithEntity::getCanSpawnHere);
		EntitySpawnPlacementRegistry.register(hydra, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(lich, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(penguin, ON_ICE, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn);
		EntitySpawnPlacementRegistry.register(lich_minion, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(loyal_zombie, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(tiny_bird, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(squirrel, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(bunny, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(raven, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(quest_ram, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
		EntitySpawnPlacementRegistry.register(kobold, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(mosquito_swarm, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MosquitoSwarmEntity::canSpawn);
		EntitySpawnPlacementRegistry.register(death_tome, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(minotaur, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(minoshroom, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(fire_beetle, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(slime_beetle, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(pinch_beetle, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(mist_wolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileWolfEntity::getCanSpawnHere);
		EntitySpawnPlacementRegistry.register(mini_ghast, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CarminiteGhastlingEntity::canSpawnHere);
		EntitySpawnPlacementRegistry.register(tower_golem, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(tower_termite, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(tower_ghast, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CarminiteGhastguardEntity::ghastSpawnHandler);
		EntitySpawnPlacementRegistry.register(ur_ghast, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(blockchain_goblin, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(goblin_knight_upper, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(goblin_knight_lower, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(helmet_crab, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(knight_phantom, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(naga, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(swarm_spider, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SwarmSpiderEntity::getCanSpawnHere);
		EntitySpawnPlacementRegistry.register(king_spider, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(tower_broodling, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SwarmSpiderEntity::getCanSpawnHere);
		EntitySpawnPlacementRegistry.register(hedge_spider, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HedgeSpiderEntity::canSpawn);
		EntitySpawnPlacementRegistry.register(redcap_sapper, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(maze_slime, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MazeSlimeEntity::getCanSpawnHere);
		EntitySpawnPlacementRegistry.register(yeti, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, YetiEntity::yetiSnowyForestSpawnHandler);
		EntitySpawnPlacementRegistry.register(yeti_alpha, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(winter_wolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WinterWolfEntity::canSpawnHere);
		EntitySpawnPlacementRegistry.register(snow_guardian, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(stable_ice_core, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(unstable_ice_core, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(snow_queen, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(troll, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TrollEntity::canSpawn);
		EntitySpawnPlacementRegistry.register(giant_miner, CLOUDS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GiantMinerEntity::canSpawn);
		EntitySpawnPlacementRegistry.register(armored_giant, CLOUDS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GiantMinerEntity::canSpawn);
		EntitySpawnPlacementRegistry.register(ice_crystal, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(harbinger_cube, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawn);
		EntitySpawnPlacementRegistry.register(adherent, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(roving_cube, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawn);
		EntitySpawnPlacementRegistry.register(rising_zombie, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);

		//EntitySpawnPlacementRegistry.register(castle_guardian, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
	}

	@SubscribeEvent
	public static void addEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(wild_boar, PigEntity.func_234215_eI_().create());
		event.put(bighorn_sheep, SheepEntity.registerAttributes().create());
		event.put(deer, DeerEntity.registerAttributes().create());
		event.put(redcap, RedcapEntity.registerAttributes().create());
		event.put(swarm_spider, SwarmSpiderEntity.registerAttributes().create());
		event.put(naga, NagaEntity.registerAttributes().create());
		event.put(skeleton_druid, AbstractSkeletonEntity.registerAttributes().create());
		event.put(hostile_wolf, HostileWolfEntity.registerAttributes().create());
		event.put(wraith, WraithEntity.registerAttributes().create());
		event.put(hedge_spider, SpiderEntity.func_234305_eI_().create());
		event.put(hydra, HydraEntity.registerAttributes().create());
		event.put(lich, LichEntity.registerAttributes().create());
		event.put(penguin, PenguinEntity.registerAttributes().create());
		event.put(lich_minion, ZombieEntity.func_234342_eQ_().create());
		event.put(loyal_zombie, LoyalZombieEntity.registerAttributes().create());
		event.put(tiny_bird, TinyBirdEntity.registerAttributes().create());
		event.put(squirrel, SquirrelEntity.registerAttributes().create());
		event.put(bunny, BunnyEntity.registerAttributes().create());
		event.put(raven, RavenEntity.registerAttributes().create());
		event.put(quest_ram, QuestRamEntity.registerAttributes().create());
		event.put(kobold, KoboldEntity.registerAttributes().create());
		event.put(mosquito_swarm, MosquitoSwarmEntity.registerAttributes().create());
		event.put(death_tome, DeathTomeEntity.registerAttributes().create());
		event.put(minotaur, MinotaurEntity.registerAttributes().create());
		event.put(minoshroom, MinoshroomEntity.registerAttributes().create());
		event.put(fire_beetle, FireBeetleEntity.registerAttributes().create());
		event.put(slime_beetle, SlimeBeetleEntity.registerAttributes().create());
		event.put(pinch_beetle, PinchBeetleEntity.registerAttributes().create());
		event.put(maze_slime, MazeSlimeEntity.registerAttributes().create());
		event.put(redcap_sapper, RedcapSapperEntity.registerAttributes().create());
		event.put(mist_wolf, MistWolfEntity.registerAttributes().create());
		event.put(king_spider, KingSpiderEntity.registerAttributes().create());
		event.put(mini_ghast, CarminiteGhastlingEntity.registerAttributes().create());
		event.put(tower_ghast, CarminiteGhastguardEntity.registerAttributes().create());
		event.put(tower_golem, CarminiteGolemEntity.registerAttributes().create());
		event.put(tower_termite, TowerwoodBorerEntity.registerAttributes().create());
		event.put(tower_broodling, TowerBroodlingEntity.registerAttributes().create());
		event.put(ur_ghast, UrGhastEntity.registerAttributes().create());
		event.put(blockchain_goblin, BlockChainGoblinEntity.registerAttributes().create());
		event.put(goblin_knight_upper, UpperGoblinKnightEntity.registerAttributes().create());
		event.put(goblin_knight_lower, LowerGoblinKnightEntity.registerAttributes().create());
		event.put(helmet_crab, HelmetCrabEntity.registerAttributes().create());
		event.put(knight_phantom, KnightPhantomEntity.registerAttributes().create());
		event.put(yeti, YetiEntity.registerAttributes().create());
		event.put(yeti_alpha, AlphaYetiEntity.registerAttributes().create());
		event.put(winter_wolf, WinterWolfEntity.registerAttributes().create());
		event.put(snow_guardian, SnowGuardianEntity.registerAttributes().create());
		event.put(stable_ice_core, StableIceCoreEntity.registerAttributes().create());
		event.put(unstable_ice_core, UnstableIceCoreEntity.registerAttributes().create());
		event.put(snow_queen, SnowQueenEntity.registerAttributes().create());
		event.put(troll, TrollEntity.registerAttributes().create());
		event.put(giant_miner, GiantMinerEntity.registerAttributes().create());
		event.put(armored_giant, GiantMinerEntity.registerAttributes().create());
		event.put(ice_crystal, IceCrystalEntity.registerAttributes().create());
		event.put(harbinger_cube, HarbingerCubeEntity.registerAttributes().create());
		event.put(adherent, AdherentEntity.registerAttributes().create());
		event.put(roving_cube, RovingCubeEntity.registerAttributes().create());
		//event.put(castle_guardian, MobEntity.func_233666_p_().create());
		event.put(plateau_boss, PlateauBossEntity.registerAttributes().create());

		//event.put(boggard, EntityTFBoggard.registerAttributes().create());
		event.put(rising_zombie, ZombieEntity.func_234342_eQ_().create());
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(wild_boar, m -> new BoarRenderer(m, new BoarModel<>()));
		RenderingRegistry.registerEntityRenderingHandler(bighorn_sheep, m -> new BighornRenderer(m, new BighornModel<>(), new BighornFurLayer(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(deer, m -> new TFGenericMobRenderer<>(m, new DeerModel(), 0.7F, "wilddeer.png"));
		RenderingRegistry.registerEntityRenderingHandler(redcap, m -> new TFBipedRenderer<>(m, new RedcapModel<>(0.0F), new BipedModel<>(0.7F), new BipedModel<>(0.7F), 0.4F, "redcap.png"));
		RenderingRegistry.registerEntityRenderingHandler(skeleton_druid, m -> new TFBipedRenderer<>(m, new SkeletonDruidModel(), new SkeletonDruidModel(), new SkeletonDruidModel(), 0.5F, "skeletondruid.png"));
		RenderingRegistry.registerEntityRenderingHandler(hostile_wolf, WolfRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(wraith, m -> new WraithRenderer(m, new WraithModel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(hydra, m -> new HydraRenderer(m, new HydraModel(), 4.0F));
		RenderingRegistry.registerEntityRenderingHandler(lich, m -> new LichRenderer(m, new LichModel(), 0.6F));
		RenderingRegistry.registerEntityRenderingHandler(penguin, m -> new BirdRenderer<>(m, new PenguinModel(), 0.375F, "penguin.png"));
		RenderingRegistry.registerEntityRenderingHandler(lich_minion, m -> new TFBipedRenderer<>(m, new LichMinionModel(false), new LichMinionModel(true), new LichMinionModel(true), 0.5F, "textures/entity/zombie/zombie.png"));
		RenderingRegistry.registerEntityRenderingHandler(loyal_zombie, m -> new TFBipedRenderer<>(m, new LoyalZombieModel(false), new LoyalZombieModel(true), new LoyalZombieModel(true), 0.5F, "textures/entity/zombie/zombie.png"));
		RenderingRegistry.registerEntityRenderingHandler(tiny_bird, m -> new TinyBirdRenderer(m, new TinyBirdModel(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(squirrel, m -> new TFGenericMobRenderer<>(m, new SquirrelModel(), 0.3F, "squirrel2.png"));
		RenderingRegistry.registerEntityRenderingHandler(bunny, m -> new BunnyRenderer(m, new BunnyModel(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(raven, m -> new BirdRenderer<>(m, new RavenModel(), 0.3F, "raven.png"));
		RenderingRegistry.registerEntityRenderingHandler(quest_ram, manager -> new QuestRamRenderer(manager, new QuestRamModel()));
		RenderingRegistry.registerEntityRenderingHandler(kobold, m -> new KoboldRenderer(m, new KoboldModel(), 0.4F, "kobold.png"));
		//RenderingRegistry.registerEntityRenderingHandler(boggard, m -> new RenderTFBiped<>(m, new BipedModel<>(0), 0.625F, "kobold.png"));
		RenderingRegistry.registerEntityRenderingHandler(mosquito_swarm, m -> new TFGenericMobRenderer<>(m, new MosquitoSwarmModel(), 0.0F, "mosquitoswarm.png"));
		RenderingRegistry.registerEntityRenderingHandler(death_tome, m -> new TFGenericMobRenderer<>(m, new DeathTomeModel(), 0.3F, "textures/entity/enchanting_table_book.png"));
		RenderingRegistry.registerEntityRenderingHandler(minotaur, m -> new TFBipedRenderer<>(m, new MinotaurModel(), 0.625F, "minotaur.png"));
		RenderingRegistry.registerEntityRenderingHandler(minoshroom, m -> new MinoshroomRenderer(m, new MinoshroomModel(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(fire_beetle, m -> new TFGenericMobRenderer<>(m, new FireBeetleModel(), 0.8F, "firebeetle.png"));
		RenderingRegistry.registerEntityRenderingHandler(slime_beetle, m -> new SlimeBeetleRenderer(m, new SlimeBeetleModel(), 0.6F));
		RenderingRegistry.registerEntityRenderingHandler(pinch_beetle, m -> new TFGenericMobRenderer<>(m, new PinchBeetleModel(), 0.6F, "pinchbeetle.png"));
		RenderingRegistry.registerEntityRenderingHandler(mist_wolf, MistWolfRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(mini_ghast, m -> new TFGhastRenderer<>(m, new TFGhastModel<>(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(tower_golem, m -> new CarminiteGolemRenderer<>(m, new CarminiteGolemModel<>(), 0.75F));
		RenderingRegistry.registerEntityRenderingHandler(tower_termite, m -> new TFGenericMobRenderer<>(m, new SilverfishModel<>(), 0.3F, "towertermite.png"));
		RenderingRegistry.registerEntityRenderingHandler(tower_ghast, m -> new CarminiteGhastRenderer<>(m, new TFGhastModel<>(), 3.0F));
		RenderingRegistry.registerEntityRenderingHandler(ur_ghast, m -> new UrGhastRenderer(m, new UrGhastModel(), 8.0F, 24F));
		RenderingRegistry.registerEntityRenderingHandler(blockchain_goblin, m -> new BlockChainGoblinRenderer<>(m, new BlockChainGoblinModel<>(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(goblin_knight_upper, m -> new UpperGoblinKnightRenderer(m, new UpperGoblinKnightModel(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(goblin_knight_lower, m -> new TFBipedRenderer<>(m, new LowerGoblinKnightModel(), 0.625F, "doublegoblin.png"));
		RenderingRegistry.registerEntityRenderingHandler(helmet_crab, m -> new TFGenericMobRenderer<>(m, new HelmetCrabModel(), 0.625F, "helmetcrab.png"));
		RenderingRegistry.registerEntityRenderingHandler(knight_phantom, m -> new KnightPhantomRenderer(m, new KnightPhantomModel(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(naga, m -> new NagaRenderer<>(m, new NagaModel<>(), 1.45F));
		RenderingRegistry.registerEntityRenderingHandler(swarm_spider, SwarmSpiderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(king_spider, KingSpiderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(tower_broodling, CarminiteBroodlingRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(hedge_spider, HedgeSpiderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(redcap_sapper, m -> new TFBipedRenderer<>(m, new RedcapModel<>(0.0F), new BipedModel<>(0.75F), new BipedModel<>(0.75F), 0.4F, "redcapsapper.png"));
		RenderingRegistry.registerEntityRenderingHandler(maze_slime, m -> new MazeSlimeRenderer(m, 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(yeti, m -> new TFBipedRenderer<>(m, new YetiModel<>(), 0.625F, "yeti2.png"));
		RenderingRegistry.registerEntityRenderingHandler(protection_box, ProtectionBoxRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(yeti_alpha, m -> new TFBipedRenderer<>(m, new AlphaYetiModel(), 1.75F, "yetialpha.png"));
		RenderingRegistry.registerEntityRenderingHandler(winter_wolf, WinterWolfRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(snow_guardian, m -> new SnowGuardianRenderer(m, new NoopModel<>()));
		RenderingRegistry.registerEntityRenderingHandler(stable_ice_core, m -> new StableIceCoreRenderer(m, new StableIceCoreModel()));
		RenderingRegistry.registerEntityRenderingHandler(unstable_ice_core, m -> new UnstableIceCoreRenderer<>(m, new UnstableIceCoreModel<>()));
		RenderingRegistry.registerEntityRenderingHandler(snow_queen, m -> new SnowQueenRenderer(m, new SnowQueenModel()));
		RenderingRegistry.registerEntityRenderingHandler(troll, m -> new TFBipedRenderer<>(m, new TrollModel(), 0.625F, "troll.png"));
		RenderingRegistry.registerEntityRenderingHandler(giant_miner, TFGiantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(armored_giant, TFGiantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ice_crystal, IceCrystalRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(chain_block, BlockChainRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(cube_of_annihilation, CubeOfAnnihilationRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(harbinger_cube, HarbingerCubeRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(adherent, AdherentRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(roving_cube, RovingCubeRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(rising_zombie, m -> new TFBipedRenderer<>(m, new RisingZombieModel(false), new RisingZombieModel(true), new RisingZombieModel(true), 0.5F, "textures/entity/zombie/zombie.png"));
		//RenderingRegistry.registerEntityRenderingHandler(castle_guardian, m -> new RenderTFCastleGuardian(m, new ModelTFCastleGuardian(), 2.0F, "finalcastle/castle_guardian.png"));
		RenderingRegistry.registerEntityRenderingHandler(plateau_boss, NoopRenderer::new);

		// projectiles
		RenderingRegistry.registerEntityRenderingHandler(nature_bolt, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(lich_bolt, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(wand_bolt, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(tome_bolt, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(hydra_mortar, HydraMortarRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(slime_blob, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(cicada_shot, CicadaShotRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(moonworm_shot, MoonwormShotRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(charm_effect, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(lich_bomb, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(thrown_wep, ThrownWepRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(falling_ice, FallingIceRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(thrown_ice, ThrownIceRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ice_snowball, m -> new SpriteRenderer<>(m, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(slider, SlideBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(seeker_arrow, DefaultArrowRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ice_arrow, DefaultArrowRenderer::new);

	}
}
