package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.*;
import twilightforest.entity.finalcastle.EntityTFCastleGuardian;
import twilightforest.entity.passive.*;
import twilightforest.util.TFEntityNames;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class TFEntities {

	private static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass, int id, int backgroundEggColour, int foregroundEggColour) {
		registerEntity(registryName, entityClass, id, backgroundEggColour, foregroundEggColour, 80, 3, true);
	}

	private static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass, int id, int backgroundEggColour, int foregroundEggColour, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(registryName, entityClass, registryName.getResourceDomain() + "." + registryName.getResourcePath(), id, TwilightForestMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates, backgroundEggColour, foregroundEggColour);
	}

	private static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass, int id) {
		EntityRegistry.registerModEntity(registryName, entityClass, registryName.getResourceDomain() + "." + registryName.getResourcePath(), id, TwilightForestMod.instance, 80, 3, true);
	}

	private static void registerEntity(ResourceLocation registryName, Class<? extends Entity> clazz, int id, int trackingRange, int updateFrequency, boolean sendVelocityUpdates) {
		EntityRegistry.registerModEntity(registryName, clazz, registryName.getResourceDomain() + "." + registryName.getResourcePath(), id, TwilightForestMod.instance, trackingRange, updateFrequency, sendVelocityUpdates);
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {

		int id = 0;

		registerEntity(TFEntityNames.WILD_BOAR, EntityTFBoar.class, id++, 0x83653b, 0xffefca);
		registerEntity(TFEntityNames.BIGHORN_SHEEP, EntityTFBighorn.class, id++, 0xdbceaf, 0xd7c771);
		registerEntity(TFEntityNames.DEER, EntityTFDeer.class, id++, 0x7b4d2e, 0x4b241d);

		registerEntity(TFEntityNames.REDCAP, EntityTFRedcap.class, id++, 0x3b3a6c, 0xab1e14);
		registerEntity(TFEntityNames.SWARM_SPIDER, EntityTFSwarmSpider.class, id++, 0x32022e, 0x17251e);
		registerEntity(TFEntityNames.NAGA, EntityTFNaga.class, id++, 0xa4d316, 0x1b380b, 150, 1, true);
		registerEntity(TFEntityNames.SKELETON_DRUID, EntityTFSkeletonDruid.class, id++, 0xa3a3a3, 0x2a3b17);
		registerEntity(TFEntityNames.HOSTILE_WOLF, EntityTFHostileWolf.class, id++, 0xd7d3d3, 0xab1e14);
		registerEntity(TFEntityNames.WRAITH, EntityTFWraith.class, id++, 0x505050, 0x838383);
		registerEntity(TFEntityNames.HEDGE_SPIDER, EntityTFHedgeSpider.class, id++, 0x235f13, 0x562653);
		registerEntity(TFEntityNames.HYDRA, EntityTFHydra.class, id++, 0x142940, 0x29806b);
		registerEntity(TFEntityNames.LICH, EntityTFLich.class, id++, 0xaca489, 0x360472);
		registerEntity(TFEntityNames.PENGUIN, EntityTFPenguin.class, id++, 0x12151b, 0xf9edd2);
		registerEntity(TFEntityNames.LICH_MINION, EntityTFLichMinion.class, id++);
		registerEntity(TFEntityNames.LOYAL_ZOMBIE, EntityTFLoyalZombie.class, id++);
		registerEntity(TFEntityNames.TINY_BIRD, EntityTFTinyBird.class, id++, 0x33aadd, 0x1188ee);
		registerEntity(TFEntityNames.SQUIRREL, EntityTFSquirrel.class, id++, 0x904f12, 0xeeeeee);
		registerEntity(TFEntityNames.BUNNY, EntityTFBunny.class, id++, 0xfefeee, 0xccaa99);
		registerEntity(TFEntityNames.RAVEN, EntityTFRaven.class, id++, 0x000011, 0x222233);
		registerEntity(TFEntityNames.QUEST_RAM, EntityTFQuestRam.class, id++, 0xfefeee, 0x33aadd);
		registerEntity(TFEntityNames.KOBOLD, EntityTFKobold.class, id++, 0x372096, 0x895d1b);
		registerEntity(TFEntityNames.MOSQUITO_SWARM, EntityTFMosquitoSwarm.class, id++, 0x080904, 0x2d2f21);
		registerEntity(TFEntityNames.DEATH_TOME, EntityTFDeathTome.class, id++, 0x774e22, 0xdbcdbe);
		registerEntity(TFEntityNames.MINOTAUR, EntityTFMinotaur.class, id++, 0x3f3024, 0xaa7d66);
		registerEntity(TFEntityNames.MINOSHROOM, EntityTFMinoshroom.class, id++, 0xa81012, 0xaa7d66);
		registerEntity(TFEntityNames.FIRE_BEETLE, EntityTFFireBeetle.class, id++, 0x1d0b00, 0xcb6f25);
		registerEntity(TFEntityNames.SLIME_BEETLE, EntityTFSlimeBeetle.class, id++, 0x0c1606, 0x60a74c);
		registerEntity(TFEntityNames.PINCH_BEETLE, EntityTFPinchBeetle.class, id++, 0xbc9327, 0x241609);
		registerEntity(TFEntityNames.MAZE_SLIME, EntityTFMazeSlime.class, id++, 0xa3a3a3, 0x2a3b17);
		registerEntity(TFEntityNames.REDCAP_SAPPER, EntityTFRedcapSapper.class, id++, 0x575d21, 0xab1e14);
		registerEntity(TFEntityNames.MIST_WOLF, EntityTFMistWolf.class, id++, 0x3a1411, 0xe2c88a);
		registerEntity(TFEntityNames.KING_SPIDER, EntityTFKingSpider.class, id++, 0x2c1a0e, 0xffc017);
		registerEntity(TFEntityNames.FIREFLY, EntityTFMobileFirefly.class, id++, 0xa4d316, 0xbaee02);
		registerEntity(TFEntityNames.MINI_GHAST, EntityTFMiniGhast.class, id++, 0xbcbcbc, 0xa74343);
		registerEntity(TFEntityNames.TOWER_GHAST, EntityTFTowerGhast.class, id++, 0xbcbcbc, 0xb77878);
		registerEntity(TFEntityNames.TOWER_GOLEM, EntityTFTowerGolem.class, id++, 0x6b3d20, 0xe2ddda);
		registerEntity(TFEntityNames.TOWER_TERMITE, EntityTFTowerTermite.class, id++, 0x5d2b21, 0xaca03a);
		registerEntity(TFEntityNames.TOWER_BROODLING, EntityTFTowerBroodling.class, id++, 0x343c14, 0xbaee02);
		registerEntity(TFEntityNames.UR_GHAST, EntityTFUrGhast.class, id++, 0xbcbcbc, 0xb77878);
		registerEntity(TFEntityNames.BLOCKCHAIN_GOBLIN, EntityTFBlockGoblin.class, id++, 0xd3e7bc, 0x1f3fff);
		registerEntity(TFEntityNames.GOBLIN_KNIGHT_UPPER, EntityTFGoblinKnightUpper.class, id++);
		registerEntity(TFEntityNames.GOBLIN_KNIGHT_LOWER, EntityTFGoblinKnightLower.class, id++, 0x566055, 0xd3e7bc);
		registerEntity(TFEntityNames.HELMET_CRAB, EntityTFHelmetCrab.class, id++, 0xfb904b, 0xd3e7bc);
		registerEntity(TFEntityNames.KNIGHT_PHANTOM, EntityTFKnightPhantom.class, id++, 0xa6673b, 0xd3e7bc);
		registerEntity(TFEntityNames.YETI, EntityTFYeti.class, id++, 0xdedede, 0x4675bb);
		registerEntity(TFEntityNames.YETI_ALPHA, EntityTFYetiAlpha.class, id++, 0xcdcdcd, 0x29486e);
		registerEntity(TFEntityNames.WINTER_WOLF, EntityTFWinterWolf.class, id++, 0xdfe3e5, 0xb2bcca);
		registerEntity(TFEntityNames.SNOW_GUARDIAN, EntityTFSnowGuardian.class, id++, 0xd3e7bc, 0xfefefe);
		registerEntity(TFEntityNames.STABLE_ICE_CORE, EntityTFIceShooter.class, id++, 0xa1bff3, 0x7000f8);
		registerEntity(TFEntityNames.UNSTABLE_ICE_CORE, EntityTFIceExploder.class, id++, 0x9aacf5, 0x9b0fa5);
		registerEntity(TFEntityNames.SNOW_QUEEN, EntityTFSnowQueen.class, id++, 0xb1b2d4, 0x87006e);
		registerEntity(TFEntityNames.TROLL, EntityTFTroll.class, id++, 0x9ea98f, 0xb0948e);
		registerEntity(TFEntityNames.GIANT_MINER, EntityTFGiantMiner.class, id++, 0x211b52, 0x9a9a9a);
		registerEntity(TFEntityNames.ARMORED_GIANT, EntityTFArmoredGiant.class, id++, 0x239391, 0x9a9a9a);
		registerEntity(TFEntityNames.ICE_CRYSTAL, EntityTFIceCrystal.class, id++, 0xdce9fe, 0xadcafb);
		registerEntity(TFEntityNames.HARBINGER_CUBE, EntityTFHarbingerCube.class, id++, 0x00000a, 0x8b0000);
		registerEntity(TFEntityNames.ADHERENT, EntityTFAdherent.class, id++, 0x0a0000, 0x00008b);
		registerEntity(TFEntityNames.ROVING_CUBE, EntityTFRovingCube.class, id++, 0x0a0000, 0x00009b);
		registerEntity(TFEntityNames.CASTLE_GUARDIAN, EntityTFCastleGuardian.class, id++, 80, 3, true);

		registerEntity(TFEntityNames.HYDRA_HEAD, EntityTFHydraHead.class, id++, 150, 3, false);

		registerEntity(TFEntityNames.NATURE_BOLT, EntityTFNatureBolt.class, id++, 150, 5, true);
		registerEntity(TFEntityNames.LICH_BOLT, EntityTFLichBolt.class, id++, 150, 2, true);
		registerEntity(TFEntityNames.WAND_BOLT, EntityTFTwilightWandBolt.class, id++, 150, 5, true);
		registerEntity(TFEntityNames.TOME_BOLT, EntityTFTomeBolt.class, id++, 150, 5, true);
		registerEntity(TFEntityNames.HYDRA_MORTAR, EntityTFHydraMortar.class, id++, 150, 3, true);
		registerEntity(TFEntityNames.LICH_BOMB, EntityTFLichBomb.class, id++, 150, 3, true);
		registerEntity(TFEntityNames.MOONWORM_SHOT, EntityTFMoonwormShot.class, id++, 150, 3, true);
		registerEntity(TFEntityNames.SLIME_BLOB, EntityTFSlimeProjectile.class, id++, 150, 3, true);
		registerEntity(TFEntityNames.CHARM_EFFECT, EntityTFCharmEffect.class, id++, 80, 3, true);
		registerEntity(TFEntityNames.THROWN_WEP, EntityTFThrownWep.class, id++, 80, 3, true);
		registerEntity(TFEntityNames.FALLING_ICE, EntityTFFallingIce.class, id++, 80, 3, true);
		registerEntity(TFEntityNames.THROWN_ICE, EntityTFIceBomb.class, id++, 80, 2, true);
		registerEntity(TFEntityNames.SEEKER_ARROW, EntitySeekerArrow.class, id++, 150, 1, true);
		registerEntity(TFEntityNames.ICE_ARROW, EntityIceArrow.class, id++, 150, 1, true);
		registerEntity(TFEntityNames.ICE_SNOWBALL, EntityTFIceSnowball.class, id++, 150, 3, true);
		registerEntity(TFEntityNames.CHAIN_BLOCK, EntityTFChainBlock.class, id++, 80, 1, true);
		registerEntity(TFEntityNames.CUBE_OF_ANNIHILATION, EntityTFCubeOfAnnihilation.class, id++, 80, 1, true);
		registerEntity(TFEntityNames.SLIDER, EntityTFSlideBlock.class, id++, 80, 1, true);
		registerEntity(TFEntityNames.BOGGARD, EntityTFBoggard.class, id++);
	}
}
