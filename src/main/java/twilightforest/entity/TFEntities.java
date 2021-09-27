package twilightforest.entity;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.*;
import twilightforest.client.renderer.entity.*;
import twilightforest.entity.boss.*;
import twilightforest.entity.monster.*;
import twilightforest.entity.passive.*;
import twilightforest.entity.projectile.*;
import twilightforest.item.TransformPowderItem;
import twilightforest.item.TFItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TFEntities {

	public static final SpawnPlacements.Type ON_ICE = SpawnPlacements.Type.create("TF_ON_ICE", (world, pos, entityType) -> {
		BlockState state = world.getBlockState(pos.below());
		Block block = state.getBlock();
		Material material = state.getMaterial();
		BlockPos up = pos.above();
		return (material == Material.ICE || material == Material.ICE_SOLID) && block != Blocks.BEDROCK && block != Blocks.BARRIER && NaturalSpawner.isValidEmptySpawnBlock(world, pos, world.getBlockState(pos), world.getFluidState(pos), entityType) && NaturalSpawner.isValidEmptySpawnBlock(world, up, world.getBlockState(up), world.getFluidState(up), entityType);
	});

	public static final SpawnPlacements.Type CLOUDS = SpawnPlacements.Type.create("CLOUD_DWELLERS", (world, pos, entityType) -> {
		BlockState state = world.getBlockState(pos.below());
		Block block = state.getBlock();
		BlockPos up = pos.above();
		return (block == TFBlocks.wispy_cloud.get() || block == TFBlocks.wispy_cloud.get()) && block != Blocks.BEDROCK && block != Blocks.BARRIER && NaturalSpawner.isValidEmptySpawnBlock(world, pos, world.getBlockState(pos), world.getFluidState(pos), entityType) && NaturalSpawner.isValidEmptySpawnBlock(world, up, world.getBlockState(up), world.getFluidState(up), entityType);
	});

	private static final List<EntityType<?>> ALL = new ArrayList<>();
	public static final EntityType<Boar> wild_boar = make(TFEntityNames.WILD_BOAR, Boar::new, MobCategory.CREATURE, 0.9F, 0.9F);
	public static final EntityType<Bighorn> bighorn_sheep = make(TFEntityNames.BIGHORN_SHEEP, Bighorn::new, MobCategory.CREATURE, 0.9F, 1.3F);
	public static final EntityType<Deer> deer = make(TFEntityNames.DEER, Deer::new, MobCategory.CREATURE, 0.7F, 1.8F);
	public static final EntityType<Redcap> redcap = make(TFEntityNames.REDCAP, Redcap::new, MobCategory.MONSTER, 0.9F, 1.4F);
	public static final EntityType<SwarmSpider> swarm_spider = make(TFEntityNames.SWARM_SPIDER, SwarmSpider::new, MobCategory.MONSTER, 0.8F, 0.4F);
	public static final EntityType<Naga> naga = make(TFEntityNames.NAGA, Naga::new, MobCategory.MONSTER, 1.75f, 3.0f);
	public static final EntityType<SkeletonDruid> skeleton_druid = make(TFEntityNames.SKELETON_DRUID, SkeletonDruid::new, MobCategory.MONSTER, 0.6F, 1.99F);
	public static final EntityType<HostileWolf> hostile_wolf = make(TFEntityNames.HOSTILE_WOLF, HostileWolf::new, MobCategory.MONSTER, 0.6F, 0.85F);
	public static final EntityType<Wraith> wraith = make(TFEntityNames.WRAITH, Wraith::new, MobCategory.MONSTER);
	public static final EntityType<HedgeSpider> hedge_spider = make(TFEntityNames.HEDGE_SPIDER, HedgeSpider::new, MobCategory.MONSTER, 1.4F, 0.9F);
	public static final EntityType<Hydra> hydra = make(TFEntityNames.HYDRA, Hydra::new, MobCategory.MONSTER, 16F, 12F);
	public static final EntityType<Lich> lich = make(TFEntityNames.LICH, Lich::new, MobCategory.MONSTER, 1.1F, 2.1F);
	public static final EntityType<Penguin> penguin = make(TFEntityNames.PENGUIN, Penguin::new, MobCategory.CREATURE, 0.5F, 0.9F);
	public static final EntityType<LichMinion> lich_minion = make(TFEntityNames.LICH_MINION, LichMinion::new, MobCategory.MONSTER, 0.6F, 1.95F);
	public static final EntityType<LoyalZombie> loyal_zombie = make(TFEntityNames.LOYAL_ZOMBIE, LoyalZombie::new, MobCategory.MONSTER);
	public static final EntityType<TinyBird> tiny_bird = make(TFEntityNames.TINY_BIRD, TinyBird::new, MobCategory.CREATURE, 0.3F, 0.3F);
	public static final EntityType<Squirrel> squirrel = make(TFEntityNames.SQUIRREL, Squirrel::new, MobCategory.CREATURE, 0.3F, 0.5F);
	public static final EntityType<DwarfRabbit> bunny = make(TFEntityNames.BUNNY, DwarfRabbit::new, MobCategory.CREATURE, 0.4F, 0.4F);
	public static final EntityType<Raven> raven = make(TFEntityNames.RAVEN, Raven::new, MobCategory.CREATURE, 0.3F, 0.5F);
	public static final EntityType<QuestRam> quest_ram = make(TFEntityNames.QUEST_RAM, QuestRam::new, MobCategory.CREATURE, 1.25F, 2.9F);
	public static final EntityType<Kobold> kobold = make(TFEntityNames.KOBOLD, Kobold::new, MobCategory.MONSTER, 0.8F, 1.1F);
	public static final EntityType<MosquitoSwarm> mosquito_swarm = make(TFEntityNames.MOSQUITO_SWARM, MosquitoSwarm::new, MobCategory.MONSTER, 0.7F, 1.9F);
	public static final EntityType<DeathTome> death_tome = make(TFEntityNames.DEATH_TOME, DeathTome::new, MobCategory.MONSTER);
	public static final EntityType<Minotaur> minotaur = make(TFEntityNames.MINOTAUR, Minotaur::new, MobCategory.MONSTER, 0.6F, 2.1F);
	public static final EntityType<Minoshroom> minoshroom = make(TFEntityNames.MINOSHROOM, Minoshroom::new, MobCategory.MONSTER, 1.49F, 2.5F);
	public static final EntityType<FireBeetle> fire_beetle = make(TFEntityNames.FIRE_BEETLE, FireBeetle::new, MobCategory.MONSTER, 1.1F, 0.5F);
	public static final EntityType<SlimeBeetle> slime_beetle = make(TFEntityNames.SLIME_BEETLE, SlimeBeetle::new, MobCategory.MONSTER, 0.9F, 0.5F);
	public static final EntityType<PinchBeetle> pinch_beetle = make(TFEntityNames.PINCH_BEETLE, PinchBeetle::new, MobCategory.MONSTER, 1.2F, 0.5F);
	public static final EntityType<MazeSlime> maze_slime = make(TFEntityNames.MAZE_SLIME, MazeSlime::new, MobCategory.MONSTER, 2.04F, 2.04F);
	public static final EntityType<RedcapSapper> redcap_sapper = make(TFEntityNames.REDCAP_SAPPER, RedcapSapper::new, MobCategory.MONSTER, 0.9F, 1.4F);
	public static final EntityType<MistWolf> mist_wolf = make(TFEntityNames.MIST_WOLF, MistWolf::new, MobCategory.MONSTER, 1.4F, 1.9F);
	public static final EntityType<KingSpider> king_spider = make(TFEntityNames.KING_SPIDER, KingSpider::new, MobCategory.MONSTER, 1.6F, 1.6F);
	public static final EntityType<CarminiteGhastling> mini_ghast = make(TFEntityNames.MINI_GHAST, CarminiteGhastling::new, MobCategory.MONSTER, 1.1F, 1.5F);
	public static final EntityType<CarminiteGhastguard> tower_ghast = make(TFEntityNames.TOWER_GHAST, CarminiteGhastguard::new, MobCategory.MONSTER, 4.0F, 6.0F);
	public static final EntityType<CarminiteGolem> tower_golem = make(TFEntityNames.TOWER_GOLEM, CarminiteGolem::new, MobCategory.MONSTER, 1.4F, 2.9F);
	public static final EntityType<TowerwoodBorer> tower_termite = make(TFEntityNames.TOWER_TERMITE, TowerwoodBorer::new, MobCategory.MONSTER, 0.4F, 0.3F);
	public static final EntityType<TowerBroodling> tower_broodling = make(TFEntityNames.TOWER_BROODLING, TowerBroodling::new, MobCategory.MONSTER, 0.7F, 0.5F);
	public static final EntityType<UrGhast> ur_ghast = make(TFEntityNames.UR_GHAST, UrGhast::new, MobCategory.MONSTER, 14.0F, 18.0F);
	public static final EntityType<BlockChainGoblin> blockchain_goblin = make(TFEntityNames.BLOCKCHAIN_GOBLIN, BlockChainGoblin::new, MobCategory.MONSTER, 0.9F, 1.4F);
	public static final EntityType<UpperGoblinKnight> goblin_knight_upper = make(TFEntityNames.GOBLIN_KNIGHT_UPPER, UpperGoblinKnight::new, MobCategory.MONSTER, 1.1F, 1.3F);
	public static final EntityType<LowerGoblinKnight> goblin_knight_lower = make(TFEntityNames.GOBLIN_KNIGHT_LOWER, LowerGoblinKnight::new, MobCategory.MONSTER, 0.7F, 1.1F);
	public static final EntityType<HelmetCrab> helmet_crab = make(TFEntityNames.HELMET_CRAB, HelmetCrab::new, MobCategory.MONSTER, 0.8F, 1.1F);
	public static final EntityType<KnightPhantom> knight_phantom = make(TFEntityNames.KNIGHT_PHANTOM, KnightPhantom::new, MobCategory.MONSTER, 1.5F, 3.0F);
	public static final EntityType<Yeti> yeti = make(TFEntityNames.YETI, Yeti::new, MobCategory.MONSTER, 1.4F, 2.4F);
	public static final EntityType<AlphaYeti> yeti_alpha = make(TFEntityNames.YETI_ALPHA, AlphaYeti::new, MobCategory.MONSTER, 3.8F, 5.0F);
	public static final EntityType<WinterWolf> winter_wolf = make(TFEntityNames.WINTER_WOLF, WinterWolf::new, MobCategory.MONSTER, 1.4F, 1.9F);
	public static final EntityType<SnowGuardian> snow_guardian = make(TFEntityNames.SNOW_GUARDIAN, SnowGuardian::new, MobCategory.MONSTER, 0.6F, 1.8F);
	public static final EntityType<StableIceCore> stable_ice_core = make(TFEntityNames.STABLE_ICE_CORE, StableIceCore::new, MobCategory.MONSTER, 0.8F, 1.8F);
	public static final EntityType<UnstableIceCore> unstable_ice_core = make(TFEntityNames.UNSTABLE_ICE_CORE, UnstableIceCore::new, MobCategory.MONSTER, 0.8F, 1.8F);
	public static final EntityType<SnowQueen> snow_queen = make(TFEntityNames.SNOW_QUEEN, SnowQueen::new, MobCategory.MONSTER, 0.7F, 2.2F);
	public static final EntityType<Troll> troll = make(TFEntityNames.TROLL, Troll::new, MobCategory.MONSTER, 1.4F, 2.4F);
	public static final EntityType<GiantMiner> giant_miner = make(TFEntityNames.GIANT_MINER, GiantMiner::new, MobCategory.MONSTER, 2.4F, 7.2F);
	public static final EntityType<ArmoredGiant> armored_giant = make(TFEntityNames.ARMORED_GIANT, ArmoredGiant::new, MobCategory.MONSTER, 2.4F, 7.2F);
	public static final EntityType<IceCrystal> ice_crystal = make(TFEntityNames.ICE_CRYSTAL, IceCrystal::new, MobCategory.MONSTER, 0.6F, 1.8F);
	public static final EntityType<HarbingerCube> harbinger_cube = make(TFEntityNames.HARBINGER_CUBE, HarbingerCube::new, MobCategory.MONSTER, 1.9F, 2.4F);
	public static final EntityType<Adherent> adherent = make(TFEntityNames.ADHERENT, Adherent::new, MobCategory.MONSTER, 0.8F, 2.2F);
	public static final EntityType<RovingCube> roving_cube = make(TFEntityNames.ROVING_CUBE, RovingCube::new, MobCategory.MONSTER, 1.2F, 2.1F);
	//public static final EntityType<EntityTFCastleGuardian> castle_guardian = make(TFEntityNames.CASTLE_GUARDIAN, EntityTFCastleGuardian::new, EntityClassification.MONSTER, 1.8F, 2.4F);
	public static final EntityType<PlateauBoss> plateau_boss = make(TFEntityNames.PLATEAU_BOSS, PlateauBoss::new, MobCategory.MONSTER, 1F, 1F);

	public static final EntityType<NatureBolt> nature_bolt = build(TFEntityNames.NATURE_BOLT, makeCastedBuilder(NatureBolt.class, NatureBolt::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(5));
	public static final EntityType<LichBolt> lich_bolt = build(TFEntityNames.LICH_BOLT, makeCastedBuilder(LichBolt.class, LichBolt::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(2));
	public static final EntityType<TwilightWandBolt> wand_bolt = build(TFEntityNames.WAND_BOLT, makeCastedBuilder(TwilightWandBolt.class, TwilightWandBolt::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(5));
	public static final EntityType<TomeBolt> tome_bolt = build(TFEntityNames.TOME_BOLT, makeCastedBuilder(TomeBolt.class, TomeBolt::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150).setUpdateInterval(5));
	public static final EntityType<HydraMortarHead> hydra_mortar = build(TFEntityNames.HYDRA_MORTAR, makeCastedBuilder(HydraMortarHead.class, HydraMortarHead::new, MobCategory.MISC).sized(0.75F, 0.75F).setTrackingRange(150));
	public static final EntityType<LichBomb> lich_bomb = build(TFEntityNames.LICH_BOMB, makeCastedBuilder(LichBomb.class, LichBomb::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<MoonwormShot> moonworm_shot = build(TFEntityNames.MOONWORM_SHOT, makeCastedBuilder(MoonwormShot.class, MoonwormShot::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<CicadaShot> cicada_shot = build(TFEntityNames.CICADA_SHOT, makeCastedBuilder(CicadaShot.class, CicadaShot::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<SlimeProjectile> slime_blob = build(TFEntityNames.SLIME_BLOB, makeCastedBuilder(SlimeProjectile.class, SlimeProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<CharmEffect> charm_effect = make(TFEntityNames.CHARM_EFFECT, CharmEffect::new, MobCategory.MISC, 0.25F, 0.25F);
	public static final EntityType<ThrownWep> thrown_wep = make(TFEntityNames.THROWN_WEP, ThrownWep::new, MobCategory.MISC, 0.5F, 0.5F);
	public static final EntityType<FallingIce> falling_ice = make(TFEntityNames.FALLING_ICE, FallingIce::new, MobCategory.MISC, 2.98F, 2.98F);
	public static final EntityType<IceBomb> thrown_ice = build(TFEntityNames.THROWN_ICE, makeCastedBuilder(IceBomb.class, IceBomb::new, MobCategory.MISC).sized(1.0F, 1.0F).setUpdateInterval(2));
	public static final EntityType<SeekerArrow> seeker_arrow = build(TFEntityNames.SEEKER_ARROW, makeCastedBuilder(SeekerArrow.class, SeekerArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(150).setUpdateInterval(1));
	public static final EntityType<IceArrow> ice_arrow = build(TFEntityNames.ICE_ARROW, makeCastedBuilder(IceArrow.class, IceArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(150).setUpdateInterval(1));
	public static final EntityType<IceSnowball> ice_snowball = build(TFEntityNames.ICE_SNOWBALL, makeCastedBuilder(IceSnowball.class, IceSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(150));
	public static final EntityType<ChainBlock> chain_block = build(TFEntityNames.CHAIN_BLOCK, makeCastedBuilder(ChainBlock.class, ChainBlock::new, MobCategory.MISC).sized(0.6F, 0.6F).setUpdateInterval(1));
	public static final EntityType<CubeOfAnnihilation> cube_of_annihilation = build(TFEntityNames.CUBE_OF_ANNIHILATION, makeCastedBuilder(CubeOfAnnihilation.class, CubeOfAnnihilation::new, MobCategory.MISC).sized(1F, 1F).setUpdateInterval(1));
	public static final EntityType<SlideBlock> slider = build(TFEntityNames.SLIDER, makeCastedBuilder(SlideBlock.class, SlideBlock::new, MobCategory.MISC).sized(0.98F, 0.98F).setUpdateInterval(1));
	//public static final EntityType<EntityTFBoggard> boggard = make(TFEntityNames.BOGGARD, EntityTFBoggard::new, EntityClassification.MONSTER, 0.8F, 1.1F);
	public static final EntityType<RisingZombie> rising_zombie = make(TFEntityNames.RISING_ZOMBIE, RisingZombie::new, MobCategory.MONSTER, 0.6F, 1.95F);
	public static final EntityType<ProtectionBox> protection_box = build(TFEntityNames.PROTECTION_BOX, makeCastedBuilder(ProtectionBox.class, ProtectionBox::new, MobCategory.MISC).noSave().noSummon().sized(0, 0));

	private static <E extends Entity> EntityType<E> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height) {
		return build(id, makeBuilder(factory, classification).sized(width, height));
	}

	private static <E extends Entity> EntityType<E> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification) {
		return make(id, factory, classification, 0.6F, 1.8F);
	}

	@SuppressWarnings("unchecked")
	private static <E extends Entity> EntityType<E> build(ResourceLocation id, EntityType.Builder<E> builder) {
		boolean cache = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
		SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;
		EntityType<E> ret = (EntityType<E>) builder.build(id.toString()).setRegistryName(id);
		SharedConstants.CHECK_DATA_FIXER_SCHEMA = cache;
		ALL.add(ret);
		return ret;
	}

	private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.EntityFactory<E> factory, MobCategory classification) {
		return makeBuilder(factory, classification);
	}

	private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification) {
		return EntityType.Builder.of(factory, classification).
				sized(0.6F, 1.8F).
				setTrackingRange(80).
				setUpdateInterval(3).
				setShouldReceiveVelocityUpdates(true);
	}

	private static Item spawnEgg(EntityType<? extends Mob> type, int color, int color2) {
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

		SpawnPlacements.register(wild_boar, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(bighorn_sheep, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(deer, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(redcap, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(skeleton_druid, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkeletonDruid::skeletonDruidSpawnHandler);
		SpawnPlacements.register(wraith, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wraith::getCanSpawnHere);
		SpawnPlacements.register(hydra, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(lich, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(penguin, ON_ICE, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Penguin::canSpawn);
		SpawnPlacements.register(lich_minion, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(loyal_zombie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(tiny_bird, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(squirrel, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(bunny, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(raven, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(quest_ram, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(kobold, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(mosquito_swarm, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MosquitoSwarm::canSpawn);
		SpawnPlacements.register(death_tome, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(minotaur, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(minoshroom, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(fire_beetle, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(slime_beetle, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(pinch_beetle, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(mist_wolf, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HostileWolf::getCanSpawnHere);
		SpawnPlacements.register(mini_ghast, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CarminiteGhastling::canSpawnHere);
		SpawnPlacements.register(tower_golem, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(tower_termite, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(tower_ghast, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CarminiteGhastguard::ghastSpawnHandler);
		SpawnPlacements.register(ur_ghast, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(blockchain_goblin, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(goblin_knight_upper, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(goblin_knight_lower, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(helmet_crab, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(knight_phantom, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(naga, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(swarm_spider, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwarmSpider::getCanSpawnHere);
		SpawnPlacements.register(king_spider, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(tower_broodling, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwarmSpider::getCanSpawnHere);
		SpawnPlacements.register(hedge_spider, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HedgeSpider::canSpawn);
		SpawnPlacements.register(redcap_sapper, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(maze_slime, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MazeSlime::getCanSpawnHere);
		SpawnPlacements.register(yeti, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Yeti::yetiSnowyForestSpawnHandler);
		SpawnPlacements.register(yeti_alpha, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(winter_wolf, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterWolf::canSpawnHere);
		SpawnPlacements.register(snow_guardian, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(stable_ice_core, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(unstable_ice_core, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(snow_queen, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(troll, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Troll::canSpawn);
		SpawnPlacements.register(giant_miner, CLOUDS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GiantMiner::canSpawn);
		SpawnPlacements.register(armored_giant, CLOUDS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GiantMiner::canSpawn);
		SpawnPlacements.register(ice_crystal, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(harbinger_cube, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
		SpawnPlacements.register(adherent, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(roving_cube, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
		SpawnPlacements.register(rising_zombie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);

		//EntitySpawnPlacementRegistry.register(castle_guardian, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
	}

	@SubscribeEvent
	public static void addEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(wild_boar, Pig.createAttributes().build());
		event.put(bighorn_sheep, Sheep.createAttributes().build());
		event.put(deer, Deer.registerAttributes().build());
		event.put(redcap, Redcap.registerAttributes().build());
		event.put(swarm_spider, SwarmSpider.registerAttributes().build());
		event.put(naga, Naga.registerAttributes().build());
		event.put(skeleton_druid, AbstractSkeleton.createAttributes().build());
		event.put(hostile_wolf, HostileWolf.registerAttributes().build());
		event.put(wraith, Wraith.registerAttributes().build());
		event.put(hedge_spider, Spider.createAttributes().build());
		event.put(hydra, Hydra.registerAttributes().build());
		event.put(lich, Lich.registerAttributes().build());
		event.put(penguin, Penguin.registerAttributes().build());
		event.put(lich_minion, Zombie.createAttributes().build());
		event.put(loyal_zombie, LoyalZombie.registerAttributes().build());
		event.put(tiny_bird, TinyBird.registerAttributes().build());
		event.put(squirrel, Squirrel.registerAttributes().build());
		event.put(bunny, DwarfRabbit.registerAttributes().build());
		event.put(raven, Raven.registerAttributes().build());
		event.put(quest_ram, QuestRam.registerAttributes().build());
		event.put(kobold, Kobold.registerAttributes().build());
		event.put(mosquito_swarm, MosquitoSwarm.registerAttributes().build());
		event.put(death_tome, DeathTome.registerAttributes().build());
		event.put(minotaur, Minotaur.registerAttributes().build());
		event.put(minoshroom, Minoshroom.registerAttributes().build());
		event.put(fire_beetle, FireBeetle.registerAttributes().build());
		event.put(slime_beetle, SlimeBeetle.registerAttributes().build());
		event.put(pinch_beetle, PinchBeetle.registerAttributes().build());
		event.put(maze_slime, MazeSlime.registerAttributes().build());
		event.put(redcap_sapper, RedcapSapper.registerAttributes().build());
		event.put(mist_wolf, MistWolf.registerAttributes().build());
		event.put(king_spider, KingSpider.registerAttributes().build());
		event.put(mini_ghast, CarminiteGhastling.registerAttributes().build());
		event.put(tower_ghast, CarminiteGhastguard.registerAttributes().build());
		event.put(tower_golem, CarminiteGolem.registerAttributes().build());
		event.put(tower_termite, TowerwoodBorer.registerAttributes().build());
		event.put(tower_broodling, TowerBroodling.registerAttributes().build());
		event.put(ur_ghast, UrGhast.registerAttributes().build());
		event.put(blockchain_goblin, BlockChainGoblin.registerAttributes().build());
		event.put(goblin_knight_upper, UpperGoblinKnight.registerAttributes().build());
		event.put(goblin_knight_lower, LowerGoblinKnight.registerAttributes().build());
		event.put(helmet_crab, HelmetCrab.registerAttributes().build());
		event.put(knight_phantom, KnightPhantom.registerAttributes().build());
		event.put(yeti, Yeti.registerAttributes().build());
		event.put(yeti_alpha, AlphaYeti.registerAttributes().build());
		event.put(winter_wolf, WinterWolf.registerAttributes().build());
		event.put(snow_guardian, SnowGuardian.registerAttributes().build());
		event.put(stable_ice_core, StableIceCore.registerAttributes().build());
		event.put(unstable_ice_core, UnstableIceCore.registerAttributes().build());
		event.put(snow_queen, SnowQueen.registerAttributes().build());
		event.put(troll, Troll.registerAttributes().build());
		event.put(giant_miner, GiantMiner.registerAttributes().build());
		event.put(armored_giant, GiantMiner.registerAttributes().build());
		event.put(ice_crystal, IceCrystal.registerAttributes().build());
		event.put(harbinger_cube, HarbingerCube.registerAttributes().build());
		event.put(adherent, Adherent.registerAttributes().build());
		event.put(roving_cube, RovingCube.registerAttributes().build());
		//event.put(castle_guardian, MobEntity.createMobAttributes().create());
		event.put(plateau_boss, PlateauBoss.registerAttributes().build());

		//event.put(boggard, EntityTFBoggard.registerAttributes().create());
		event.put(rising_zombie, Zombie.createAttributes().build());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(wild_boar, m -> new BoarRenderer(m, new BoarModel<>(m.bakeLayer(TFModelLayers.BOAR))));
		event.registerEntityRenderer(bighorn_sheep, m -> new BighornRenderer(m, new BighornModel<>(m.bakeLayer(TFModelLayers.BIGHORN_SHEEP)), new BighornFurLayer(m.bakeLayer(TFModelLayers.BIGHORN_SHEEP_FUR)), 0.7F));
		event.registerEntityRenderer(deer, m -> new TFGenericMobRenderer<>(m, new DeerModel(m.bakeLayer(TFModelLayers.DEER)), 0.7F, "wilddeer.png"));
		event.registerEntityRenderer(redcap, m -> new TFBipedRenderer<>(m, new RedcapModel<>(m.bakeLayer(TFModelLayers.REDCAP)), new RedcapModel<>(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_INNER)), new RedcapModel<>(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_OUTER)), 0.4F, "redcap.png"));
		event.registerEntityRenderer(skeleton_druid, m -> new TFBipedRenderer<>(m, new SkeletonDruidModel(m.bakeLayer(TFModelLayers.SKELETON_DRUID)), new SkeletonDruidModel(m.bakeLayer(TFModelLayers.SKELETON_DRUID_INNER_ARMOR)), new SkeletonDruidModel(m.bakeLayer(TFModelLayers.SKELETON_DRUID_OUTER_ARMOR)), 0.5F, "skeletondruid.png"));
		event.registerEntityRenderer(hostile_wolf, WolfRenderer::new);
		event.registerEntityRenderer(wraith, m -> new WraithRenderer(m, new WraithModel(m.bakeLayer(TFModelLayers.WRAITH)), 0.5F));
		event.registerEntityRenderer(hydra, m -> new HydraRenderer(m, new HydraModel(m.bakeLayer(TFModelLayers.HYDRA)), 4.0F));
		event.registerEntityRenderer(lich, m -> new LichRenderer(m, new LichModel(m.bakeLayer(TFModelLayers.LICH)), 0.6F));
		event.registerEntityRenderer(penguin, m -> new BirdRenderer<>(m, new PenguinModel(m.bakeLayer(TFModelLayers.PENGUIN)), 0.375F, "penguin.png"));
		event.registerEntityRenderer(lich_minion, m -> new TFBipedRenderer<>(m, new LichMinionModel(m.bakeLayer(TFModelLayers.LICH_MINION)), new LichMinionModel(m.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new LichMinionModel(m.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)), 0.5F, "textures/entity/zombie/zombie.png"));
		event.registerEntityRenderer(loyal_zombie, m -> new TFBipedRenderer<>(m, new LoyalZombieModel(m.bakeLayer(TFModelLayers.LOYAL_ZOMBIE)), new LoyalZombieModel(m.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new LoyalZombieModel(m.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)), 0.5F, "textures/entity/zombie/zombie.png"));
		event.registerEntityRenderer(tiny_bird, m -> new TinyBirdRenderer(m, new TinyBirdModel(m.bakeLayer(TFModelLayers.TINY_BIRD)), 0.3F));
		event.registerEntityRenderer(squirrel, m -> new TFGenericMobRenderer<>(m, new SquirrelModel(m.bakeLayer(TFModelLayers.SQUIRREL)), 0.3F, "squirrel2.png"));
		event.registerEntityRenderer(bunny, m -> new BunnyRenderer(m, new BunnyModel(m.bakeLayer(TFModelLayers.BUNNY)), 0.3F));
		event.registerEntityRenderer(raven, m -> new BirdRenderer<>(m, new RavenModel(m.bakeLayer(TFModelLayers.RAVEN)), 0.3F, "raven.png"));
		event.registerEntityRenderer(quest_ram, m -> new QuestRamRenderer(m, new QuestRamModel(m.bakeLayer(TFModelLayers.QUEST_RAM))));
		event.registerEntityRenderer(kobold, m -> new KoboldRenderer(m, new KoboldModel(m.bakeLayer(TFModelLayers.KOBOLD)), 0.4F, "kobold.png"));
		//event.registerEntityRenderer(boggard, m -> new RenderTFBiped<>(m, new BipedModel<>(0), 0.625F, "kobold.png"));
		event.registerEntityRenderer(mosquito_swarm, m -> new TFGenericMobRenderer<>(m, new MosquitoSwarmModel(m.bakeLayer(TFModelLayers.MOSQUITO_SWARM)), 0.0F, "mosquitoswarm.png"));
		event.registerEntityRenderer(death_tome, m -> new TFGenericMobRenderer<>(m, new DeathTomeModel(m.bakeLayer(TFModelLayers.DEATH_TOME)), 0.3F, "textures/entity/enchanting_table_book.png"));
		event.registerEntityRenderer(minotaur, m -> new TFBipedRenderer<>(m, new MinotaurModel(m.bakeLayer(TFModelLayers.MINOTAUR)), 0.625F, "minotaur.png"));
		event.registerEntityRenderer(minoshroom, m -> new MinoshroomRenderer(m, new MinoshroomModel(m.bakeLayer(TFModelLayers.MINOSHROOM)), 0.625F));
		event.registerEntityRenderer(fire_beetle, m -> new TFGenericMobRenderer<>(m, new FireBeetleModel(m.bakeLayer(TFModelLayers.FIRE_BEETLE)), 0.8F, "firebeetle.png"));
		event.registerEntityRenderer(slime_beetle, m -> new SlimeBeetleRenderer(m, new SlimeBeetleModel(m.bakeLayer(TFModelLayers.SLIME_BEETLE), false), 0.6F));
		event.registerEntityRenderer(pinch_beetle, m -> new TFGenericMobRenderer<>(m, new PinchBeetleModel(m.bakeLayer(TFModelLayers.PINCH_BEETLE)), 0.6F, "pinchbeetle.png"));
		event.registerEntityRenderer(mist_wolf, MistWolfRenderer::new);
		event.registerEntityRenderer(mini_ghast, m -> new TFGhastRenderer<>(m, new TFGhastModel<>(m.bakeLayer(TFModelLayers.CARMINITE_GHASTLING)), 0.625F));
		event.registerEntityRenderer(tower_golem, m -> new CarminiteGolemRenderer<>(m, new CarminiteGolemModel<>(m.bakeLayer(TFModelLayers.CARMINITE_GOLEM)), 0.75F));
		event.registerEntityRenderer(tower_termite, m -> new TFGenericMobRenderer<>(m, new SilverfishModel<>(m.bakeLayer(ModelLayers.SILVERFISH)), 0.3F, "towertermite.png"));
		event.registerEntityRenderer(tower_ghast, m -> new CarminiteGhastRenderer<>(m, new TFGhastModel<>(m.bakeLayer(TFModelLayers.CARMINITE_GHASTGUARD)), 3.0F));
		event.registerEntityRenderer(ur_ghast, m -> new UrGhastRenderer(m, new UrGhastModel(m.bakeLayer(TFModelLayers.UR_GHAST)), 8.0F, 24F));
		event.registerEntityRenderer(blockchain_goblin, m -> new BlockChainGoblinRenderer<>(m, new BlockChainGoblinModel<>(m.bakeLayer(TFModelLayers.BLOCKCHAIN_GOBLIN)), 0.4F));
		event.registerEntityRenderer(goblin_knight_upper, m -> new UpperGoblinKnightRenderer(m, new UpperGoblinKnightModel(m.bakeLayer(TFModelLayers.UPPER_GOBLIN_KNIGHT)), 0.625F));
		event.registerEntityRenderer(goblin_knight_lower, m -> new TFBipedRenderer<>(m, new LowerGoblinKnightModel(m.bakeLayer(TFModelLayers.LOWER_GOBLIN_KNIGHT)), 0.625F, "doublegoblin.png"));
		event.registerEntityRenderer(helmet_crab, m -> new TFGenericMobRenderer<>(m, new HelmetCrabModel(m.bakeLayer(TFModelLayers.HELMET_CRAB)), 0.625F, "helmetcrab.png"));
		event.registerEntityRenderer(knight_phantom, m -> new KnightPhantomRenderer(m, new KnightPhantomModel(m.bakeLayer(TFModelLayers.KNIGHT_PHANTOM)), 0.625F));
		event.registerEntityRenderer(naga, m -> new NagaRenderer<>(m, new NagaModel<>(m.bakeLayer(TFModelLayers.NAGA)), 1.45F));
		event.registerEntityRenderer(swarm_spider, SwarmSpiderRenderer::new);
		event.registerEntityRenderer(king_spider, KingSpiderRenderer::new);
		event.registerEntityRenderer(tower_broodling, CarminiteBroodlingRenderer::new);
		event.registerEntityRenderer(hedge_spider, HedgeSpiderRenderer::new);
		event.registerEntityRenderer(redcap_sapper, m -> new TFBipedRenderer<>(m, new RedcapModel<>(m.bakeLayer(TFModelLayers.REDCAP)), new RedcapModel<>(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_INNER)), new RedcapModel<>(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_OUTER)), 0.4F, "redcapsapper.png"));
		event.registerEntityRenderer(maze_slime, m -> new MazeSlimeRenderer(m, 0.625F));
		event.registerEntityRenderer(yeti, m -> new TFBipedRenderer<>(m, new YetiModel<>(m.bakeLayer(TFModelLayers.YETI)), 0.625F, "yeti2.png"));
		event.registerEntityRenderer(protection_box, ProtectionBoxRenderer::new);
		event.registerEntityRenderer(yeti_alpha, m -> new TFBipedRenderer<>(m, new AlphaYetiModel(m.bakeLayer(TFModelLayers.ALPHA_YETI)), 1.75F, "yetialpha.png"));
		event.registerEntityRenderer(winter_wolf, WinterWolfRenderer::new);
		event.registerEntityRenderer(snow_guardian, m -> new SnowGuardianRenderer(m, new NoopModel<>(m.bakeLayer(TFModelLayers.NOOP))));
		event.registerEntityRenderer(stable_ice_core, m -> new StableIceCoreRenderer(m, new StableIceCoreModel(m.bakeLayer(TFModelLayers.STABLE_ICE_CORE))));
		event.registerEntityRenderer(unstable_ice_core, m -> new UnstableIceCoreRenderer<>(m, new UnstableIceCoreModel<>(m.bakeLayer(TFModelLayers.UNSTABLE_ICE_CORE))));
		event.registerEntityRenderer(snow_queen, m -> new SnowQueenRenderer(m, new SnowQueenModel(m.bakeLayer(TFModelLayers.SNOW_QUEEN))));
		event.registerEntityRenderer(troll, m -> new TFBipedRenderer<>(m, new TrollModel(m.bakeLayer(TFModelLayers.TROLL)), 0.625F, "troll.png"));
		event.registerEntityRenderer(giant_miner, TFGiantRenderer::new);
		event.registerEntityRenderer(armored_giant, TFGiantRenderer::new);
		event.registerEntityRenderer(ice_crystal, IceCrystalRenderer::new);
		event.registerEntityRenderer(chain_block, BlockChainRenderer::new);
		event.registerEntityRenderer(cube_of_annihilation, CubeOfAnnihilationRenderer::new);
		event.registerEntityRenderer(harbinger_cube, HarbingerCubeRenderer::new);
		event.registerEntityRenderer(adherent, AdherentRenderer::new);
		event.registerEntityRenderer(roving_cube, RovingCubeRenderer::new);
		event.registerEntityRenderer(rising_zombie, m -> new TFBipedRenderer<>(m, new RisingZombieModel(m.bakeLayer(TFModelLayers.RISING_ZOMBIE)), new RisingZombieModel(m.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new RisingZombieModel(m.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)), 0.5F, "textures/entity/zombie/zombie.png"));
		//event.registerEntityRenderer(castle_guardian, m -> new RenderTFCastleGuardian(m, new ModelTFCastleGuardian(), 2.0F, "finalcastle/castle_guardian.png"));
		event.registerEntityRenderer(plateau_boss, NoopRenderer::new);

		// projectiles
		event.registerEntityRenderer(nature_bolt, ThrownItemRenderer::new);
		event.registerEntityRenderer(lich_bolt, ThrownItemRenderer::new);
		event.registerEntityRenderer(wand_bolt, ThrownItemRenderer::new);
		event.registerEntityRenderer(tome_bolt, ThrownItemRenderer::new);
		event.registerEntityRenderer(hydra_mortar, HydraMortarRenderer::new);
		event.registerEntityRenderer(slime_blob, ThrownItemRenderer::new);
		event.registerEntityRenderer(cicada_shot, CicadaShotRenderer::new);
		event.registerEntityRenderer(moonworm_shot, MoonwormShotRenderer::new);
		event.registerEntityRenderer(charm_effect, ThrownItemRenderer::new);
		event.registerEntityRenderer(lich_bomb, ThrownItemRenderer::new);
		event.registerEntityRenderer(thrown_wep, ThrownWepRenderer::new);
		event.registerEntityRenderer(falling_ice, FallingIceRenderer::new);
		event.registerEntityRenderer(thrown_ice, ThrownIceRenderer::new);
		event.registerEntityRenderer(ice_snowball, ThrownItemRenderer::new);
		event.registerEntityRenderer(slider, SlideBlockRenderer::new);
		event.registerEntityRenderer(seeker_arrow, DefaultArrowRenderer::new);
		event.registerEntityRenderer(ice_arrow, DefaultArrowRenderer::new);
	}

	@OnlyIn(Dist.CLIENT)
	public static class BakedMultiPartRenderers {

		private static final Map<ResourceLocation, EntityRenderer<?>> renderers = new HashMap<>();

		public static void bakeMultiPartRenderers(EntityRendererProvider.Context context) {
			renderers.put(TFPart.RENDERER, new NoopRenderer<>(context));
			renderers.put(HydraHead.RENDERER, new HydraHeadRenderer(context));
			renderers.put(HydraNeck.RENDERER, new HydraNeckRenderer(context));
			renderers.put(SnowQueenIceShield.RENDERER, new SnowQueenIceShieldLayer<>(context));
			renderers.put(NagaSegment.RENDERER, new NagaSegmentRenderer<>(context));
		}

		public static EntityRenderer<?> lookup(ResourceLocation location) {
			return renderers.get(location);
		}

	}
}
