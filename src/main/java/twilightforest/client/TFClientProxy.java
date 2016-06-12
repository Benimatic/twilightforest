package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import twilightforest.TFCommonProxy;
import twilightforest.TFGenericPacketHandler;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.client.model.ModelTFAdherent;
import twilightforest.client.model.ModelTFArcticArmor;
import twilightforest.client.model.ModelTFBighorn;
import twilightforest.client.model.ModelTFBighornFur;
import twilightforest.client.model.ModelTFBlockGoblin;
import twilightforest.client.model.ModelTFBoar;
import twilightforest.client.model.ModelTFBunny;
import twilightforest.client.model.ModelTFDeathTome;
import twilightforest.client.model.ModelTFDeer;
import twilightforest.client.model.ModelTFFieryArmor;
import twilightforest.client.model.ModelTFFireBeetle;
import twilightforest.client.model.ModelTFGhast;
import twilightforest.client.model.ModelTFGoblinChain;
import twilightforest.client.model.ModelTFGoblinKnightLower;
import twilightforest.client.model.ModelTFGoblinKnightUpper;
import twilightforest.client.model.ModelTFHelmetCrab;
import twilightforest.client.model.ModelTFHydra;
import twilightforest.client.model.ModelTFHydraHead;
import twilightforest.client.model.ModelTFHydraNeck;
import twilightforest.client.model.ModelTFKnightPhantom2;
import twilightforest.client.model.ModelTFKnightlyArmor;
import twilightforest.client.model.ModelTFKobold;
import twilightforest.client.model.ModelTFLich;
import twilightforest.client.model.ModelTFLichMinion;
import twilightforest.client.model.ModelTFLoyalZombie;
import twilightforest.client.model.ModelTFMinoshroom;
import twilightforest.client.model.ModelTFMinotaur;
import twilightforest.client.model.ModelTFMosquitoSwarm;
import twilightforest.client.model.ModelTFNaga;
import twilightforest.client.model.ModelTFPenguin;
import twilightforest.client.model.ModelTFPhantomArmor;
import twilightforest.client.model.ModelTFPinchBeetle;
import twilightforest.client.model.ModelTFRaven;
import twilightforest.client.model.ModelTFRedcap;
import twilightforest.client.model.ModelTFSkeletonDruid;
import twilightforest.client.model.ModelTFSlimeBeetle;
import twilightforest.client.model.ModelTFSpikeBlock;
import twilightforest.client.model.ModelTFSquirrel;
import twilightforest.client.model.ModelTFTinyBird;
import twilightforest.client.model.ModelTFTowerBoss;
import twilightforest.client.model.ModelTFTowerGolem;
import twilightforest.client.model.ModelTFTroll;
import twilightforest.client.model.ModelTFWraith;
import twilightforest.client.model.ModelTFYeti;
import twilightforest.client.model.ModelTFYetiAlpha;
import twilightforest.client.model.ModelTFYetiArmor;
import twilightforest.client.particle.EntityTFAnnihilateFX;
import twilightforest.client.particle.EntityTFBossTearFX;
import twilightforest.client.particle.EntityTFGhastTrapFX;
import twilightforest.client.particle.EntityTFIceBeamFX;
import twilightforest.client.particle.EntityTFLargeFlameFX;
import twilightforest.client.particle.EntityTFLeafRuneFX;
import twilightforest.client.particle.EntityTFProtectionFX;
import twilightforest.client.particle.EntityTFSnowFX;
import twilightforest.client.particle.EntityTFSnowGuardianFX;
import twilightforest.client.particle.EntityTFSnowWarningFX;
import twilightforest.client.renderer.TFFieryItemRenderer;
import twilightforest.client.renderer.TFGiantBlockRenderer;
import twilightforest.client.renderer.TFGiantItemRenderer;
import twilightforest.client.renderer.TFIceItemRenderer;
import twilightforest.client.renderer.TFMagicMapRenderer;
import twilightforest.client.renderer.TFMazeMapRenderer;
import twilightforest.client.renderer.TileEntityTFCicadaRenderer;
import twilightforest.client.renderer.TileEntityTFFireflyRenderer;
import twilightforest.client.renderer.TileEntityTFMoonwormRenderer;
import twilightforest.client.renderer.TileEntityTFTrophyRenderer;
import twilightforest.client.renderer.blocks.RenderBlockTFCastleMagic;
import twilightforest.client.renderer.blocks.RenderBlockTFCritters;
import twilightforest.client.renderer.blocks.RenderBlockTFFireflyJar;
import twilightforest.client.renderer.blocks.RenderBlockTFHugeLilyPad;
import twilightforest.client.renderer.blocks.RenderBlockTFKnightMetal;
import twilightforest.client.renderer.blocks.RenderBlockTFMagicLeaves;
import twilightforest.client.renderer.blocks.RenderBlockTFNagastone;
import twilightforest.client.renderer.blocks.RenderBlockTFPedestal;
import twilightforest.client.renderer.blocks.RenderBlockTFPlants;
import twilightforest.client.renderer.blocks.RenderBlockTFThorns;
import twilightforest.client.renderer.entity.RenderTFAdherent;
import twilightforest.client.renderer.entity.RenderTFHarbingerCube;
import twilightforest.client.renderer.entity.RenderTFBighorn;
import twilightforest.client.renderer.entity.RenderTFBiped;
import twilightforest.client.renderer.entity.RenderTFBird;
import twilightforest.client.renderer.entity.RenderTFBlockGoblin;
import twilightforest.client.renderer.entity.RenderTFBoar;
import twilightforest.client.renderer.entity.RenderTFBunny;
import twilightforest.client.renderer.entity.RenderTFChainBlock;
import twilightforest.client.renderer.entity.RenderTFCharm;
import twilightforest.client.renderer.entity.RenderTFCubeOfAnnihilation;
import twilightforest.client.renderer.entity.RenderTFDeer;
import twilightforest.client.renderer.entity.RenderTFFallingIce;
import twilightforest.client.renderer.entity.RenderTFGenericLiving;
import twilightforest.client.renderer.entity.RenderTFGiant;
import twilightforest.client.renderer.entity.RenderTFGoblinKnightUpper;
import twilightforest.client.renderer.entity.RenderTFHedgeSpider;
import twilightforest.client.renderer.entity.RenderTFHydra;
import twilightforest.client.renderer.entity.RenderTFHydraHead;
import twilightforest.client.renderer.entity.RenderTFHydraMortar;
import twilightforest.client.renderer.entity.RenderTFIceExploder;
import twilightforest.client.renderer.entity.RenderTFIceShooter;
import twilightforest.client.renderer.entity.RenderTFIceCrystal;
import twilightforest.client.renderer.entity.RenderTFKingSpider;
import twilightforest.client.renderer.entity.RenderTFKnightPhantom;
import twilightforest.client.renderer.entity.RenderTFKobold;
import twilightforest.client.renderer.entity.RenderTFLich;
import twilightforest.client.renderer.entity.RenderTFMazeSlime;
import twilightforest.client.renderer.entity.RenderTFMiniGhast;
import twilightforest.client.renderer.entity.RenderTFMinoshroom;
import twilightforest.client.renderer.entity.RenderTFMistWolf;
import twilightforest.client.renderer.entity.RenderTFMoonwormShot;
import twilightforest.client.renderer.entity.RenderTFNaga;
import twilightforest.client.renderer.entity.RenderTFNagaSegment;
import twilightforest.client.renderer.entity.RenderTFProtectionBox;
import twilightforest.client.renderer.entity.RenderTFQuestRam;
import twilightforest.client.renderer.entity.RenderTFRovingCube;
import twilightforest.client.renderer.entity.RenderTFSlideBlock;
import twilightforest.client.renderer.entity.RenderTFSlimeBeetle;
import twilightforest.client.renderer.entity.RenderTFSnowGuardian;
import twilightforest.client.renderer.entity.RenderTFSnowQueen;
import twilightforest.client.renderer.entity.RenderTFSnowQueenIceShield;
import twilightforest.client.renderer.entity.RenderTFSpikeBlock;
import twilightforest.client.renderer.entity.RenderTFSwarmSpider;
import twilightforest.client.renderer.entity.RenderTFThrownAxe;
import twilightforest.client.renderer.entity.RenderTFThrownIce;
import twilightforest.client.renderer.entity.RenderTFTinyBird;
import twilightforest.client.renderer.entity.RenderTFTinyFirefly;
import twilightforest.client.renderer.entity.RenderTFTowerBroodling;
import twilightforest.client.renderer.entity.RenderTFTowerGhast;
import twilightforest.client.renderer.entity.RenderTFTowerGolem;
import twilightforest.client.renderer.entity.RenderTFUrGhast;
import twilightforest.client.renderer.entity.RenderTFWinterWolf;
import twilightforest.client.renderer.entity.RenderTFWraith;
import twilightforest.client.renderer.entity.RenderTFYeti;
import twilightforest.entity.EntityTFCubeOfAnnihilation;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFCicada;
import twilightforest.tileentity.TileEntityTFFirefly;
import twilightforest.tileentity.TileEntityTFMoonworm;
import twilightforest.tileentity.TileEntityTFTrophy;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TFClientProxy extends TFCommonProxy {

	int critterRenderID;
	int plantRenderID;
	int blockComplexRenderID;
	int nagastoneRenderID;
	int magicLeavesRenderID;
	int pedestalRenderID;
	int thornsRenderID;
	int knightmetalBlockRenderID;
	int hugeLilyPadBlockRenderID;
	int castleMagicBlockRenderID;

	ModelBiped[] knightlyArmorModel;
	ModelBiped[] phantomArmorModel;
	ModelBiped[] yetiArmorModel;
	ModelBiped[] arcticArmorModel;
	ModelBiped[] fieryArmorModel;
	
	TFClientTicker clientTicker;
	TFClientEvents clientEvents;
	
	boolean isDangerOverlayShown;
	/**
	 * Called during mod pre-load.  We need to register our sound thing here so that it can catch the SoundLoadEvent during loading.
	 */
	@Override
	public void doPreLoadRegistration() {
		// sounds
		//MinecraftForge.EVENT_BUS.register(new TFSounds());

	}

	/**
	 * Called during mod loading.  Registers renderers and stuff
	 */
	@Override
	public void doOnLoadRegistration() {
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		// client tick listener
		clientTicker = new TFClientTicker();
		FMLCommonHandler.instance().bus().register(clientTicker);
		
		// client events
		clientEvents = new TFClientEvents();
		MinecraftForge.EVENT_BUS.register(clientEvents);
		
		// packet listener
		TFGenericPacketHandler genericPacketHandler = new TFGenericPacketHandler();
		TwilightForestMod.genericChannel.register(genericPacketHandler);
		
		// entity renderers
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFBoar.class, new RenderTFBoar(new ModelTFBoar(), new ModelPig(0.5F), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFBighorn.class, new RenderTFBighorn(new ModelTFBighorn(), new ModelTFBighornFur(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFDeer.class, new RenderTFDeer(new ModelTFDeer(), 0.7F));

		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFRedcap.class, new RenderTFBiped(new ModelTFRedcap(), 0.625F, "redcap.png"));
//		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFNagaOld.class, new RenderTFNaga(new ModelTFNaga(), 0.625F));
//		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFNagaSegmentOld.class, new RenderTFNaga(new ModelTFNaga(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFTinyFirefly.class, new RenderTFTinyFirefly());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSkeletonDruid.class, new RenderTFBiped(new ModelTFSkeletonDruid(), 0.5F, "skeletondruid.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFWraith.class, new RenderTFWraith(new ModelTFWraith(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFHydra.class, new RenderTFHydra(new ModelTFHydra(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFLich.class, new RenderTFLich(new ModelTFLich(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFPenguin.class, new RenderTFBird(new ModelTFPenguin(), 1.0F, "penguin.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFLichMinion.class, new RenderTFBiped(new ModelTFLichMinion(), 1.0F, "textures/entity/zombie/zombie.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFLoyalZombie.class, new RenderTFBiped(new ModelTFLoyalZombie(), 1.0F, "textures/entity/zombie/zombie.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFTinyBird.class, new RenderTFTinyBird(new ModelTFTinyBird(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFSquirrel.class, new RenderTFGenericLiving(new ModelTFSquirrel(), 1.0F, "squirrel2.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFBunny.class, new RenderTFBunny(new ModelTFBunny(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFRaven.class, new RenderTFBird(new ModelTFRaven(), 1.0F, "raven.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFQuestRam.class, new RenderTFQuestRam());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFKobold.class, new RenderTFKobold(new ModelTFKobold(), 0.625F, "kobold.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFBoggard.class, new RenderTFBiped(new ModelTFLoyalZombie(), 0.625F, "kobold.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFMosquitoSwarm.class, new RenderTFGenericLiving(new ModelTFMosquitoSwarm(), 0.625F, "mosquitoswarm.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFDeathTome.class, new RenderTFGenericLiving(new ModelTFDeathTome(), 0.625F, "textures/entity/enchanting_table_book.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFMinotaur.class, new RenderTFBiped(new ModelTFMinotaur(), 0.625F, "minotaur.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFMinoshroom.class, new RenderTFMinoshroom(new ModelTFMinoshroom(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFFireBeetle.class, new RenderTFGenericLiving(new ModelTFFireBeetle(), 0.625F, "firebeetle.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSlimeBeetle.class, new RenderTFSlimeBeetle(new ModelTFSlimeBeetle(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFPinchBeetle.class, new RenderTFGenericLiving(new ModelTFPinchBeetle(), 0.625F, "pinchbeetle.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFMistWolf.class, new RenderTFMistWolf(new ModelWolf(), new ModelWolf(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.passive.EntityTFMobileFirefly.class, new RenderTFTinyFirefly());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFMiniGhast.class, new RenderTFMiniGhast(new ModelTFGhast(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTowerGolem.class, new RenderTFTowerGolem(new ModelTFTowerGolem(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTowerTermite.class, new RenderTFGenericLiving(new ModelSilverfish(), 0.3F, "towertermite.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTowerGhast.class, new RenderTFTowerGhast(new ModelTFGhast(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFUrGhast.class, new RenderTFUrGhast(new ModelTFTowerBoss(), 0.625F, 24F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFBlockGoblin.class, new RenderTFBlockGoblin(new ModelTFBlockGoblin(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFGoblinChain.class, new RenderTFSpikeBlock(new ModelTFGoblinChain(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSpikeBlock.class, new RenderTFSpikeBlock(new ModelTFSpikeBlock(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFGoblinKnightUpper.class, new RenderTFGoblinKnightUpper(new ModelTFGoblinKnightUpper(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFGoblinKnightLower.class, new RenderTFBiped(new ModelTFGoblinKnightLower(), 0.625F, "doublegoblin.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFHelmetCrab.class, new RenderTFGenericLiving(new ModelTFHelmetCrab(), 0.625F, "helmetcrab.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFKnightPhantom.class, new RenderTFKnightPhantom(new ModelTFKnightPhantom2(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFNaga.class, new RenderTFNaga(new ModelTFNaga(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFNagaSegment.class, new RenderTFNagaSegment(new ModelTFNaga(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSwarmSpider.class, new RenderTFSwarmSpider());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFKingSpider.class, new RenderTFKingSpider());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTowerBroodling.class, new RenderTFTowerBroodling());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFHedgeSpider.class, new RenderTFHedgeSpider());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFRedcapSapper.class, new RenderTFBiped(new ModelTFRedcap(), 0.625F, "redcapsapper.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFMazeSlime.class, new RenderTFMazeSlime(new ModelSlime(16), new ModelSlime(0), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFYeti.class, new RenderTFYeti(new ModelTFYeti(), 0.625F, "yeti2.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFProtectionBox.class, new RenderTFProtectionBox());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFYetiAlpha.class, new RenderTFYeti(new ModelTFYetiAlpha(), 0.625F, "yetialpha.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFWinterWolf.class, new RenderTFWinterWolf(new ModelWolf(), new ModelWolf(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSnowGuardian.class, new RenderTFSnowGuardian());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFIceShooter.class, new RenderTFIceShooter());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFIceExploder.class, new RenderTFIceExploder());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFSnowQueen.class, new RenderTFSnowQueen());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFSnowQueenIceShield.class, new RenderTFSnowQueenIceShield());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTroll.class, new RenderTFBiped(new ModelTFTroll(), 0.625F, "troll.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFGiantMiner.class, new RenderTFGiant());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFIceCrystal.class, new RenderTFIceCrystal());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFChainBlock.class, new RenderTFChainBlock(new ModelTFSpikeBlock(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFCubeOfAnnihilation.class, new RenderTFCubeOfAnnihilation());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFHarbingerCube.class, new RenderTFHarbingerCube());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFAdherent.class, new RenderTFAdherent(new ModelTFAdherent(), 0.625F, "adherent.png"));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFRovingCube.class, new RenderTFRovingCube());

		// projectiles
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFNatureBolt.class, new RenderSnowball(Items.WHEAT_SEEDS));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFLichBolt.class, new RenderSnowball(Items.ENDER_PEARL));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTwilightWandBolt.class, new RenderSnowball(Items.ENDER_PEARL));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFTomeBolt.class, new RenderSnowball(Items.PAPER));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFHydraMortar.class, new RenderTFHydraMortar());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSlimeProjectile.class, new RenderSnowball(Items.SLIME_BALL));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFMoonwormShot.class, new RenderTFMoonwormShot());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFCharmEffect.class, new RenderTFCharm(TFItems.charmOfLife1.getIconFromDamage(0)));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFLichBomb.class, new RenderSnowball(Items.MAGMA_CREAM));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFThrownAxe.class, new RenderTFThrownAxe(TFItems.knightlyAxe));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFThrownPick.class, new RenderTFThrownAxe(TFItems.knightlyPick));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFFallingIce.class, new RenderTFFallingIce());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFIceBomb.class, new RenderTFThrownIce());
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFIceSnowball.class, new RenderSnowball(Items.SNOWBALL));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.EntityTFSlideBlock.class, new RenderTFSlideBlock());
		
		// I guess the hydra gets its own section
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFHydraHead.class, new RenderTFHydraHead(new ModelTFHydraHead(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(twilightforest.entity.boss.EntityTFHydraNeck.class, new RenderTFGenericLiving(new ModelTFHydraNeck(), 1.0F, "hydra4.png"));
		
		// animated textures
//		TextureFXManager.instance().addAnimation(new TextureTFMagicLeavesFX(mc, BlockTFMagicLeaves.SPR_TIMELEAVES, BlockTFMagicLeaves.SPR_TIMEFX));
//		TextureFXManager.instance().addAnimation(new TextureTFMagicLeavesFX(mc, BlockTFMagicLeaves.SPR_TRANSLEAVES, BlockTFMagicLeaves.SPR_TRANSFX));
//		TextureFXManager.instance().addAnimation(new TextureTFMagicLeavesFX(mc, BlockTFMagicLeaves.SPR_SORTLEAVES, BlockTFMagicLeaves.SPR_SORTFX));
		
		// tile entities
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFFirefly.class, new TileEntityTFFireflyRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFCicada.class, new TileEntityTFCicadaRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFNagaSpawner.class, new TileEntityMobSpawnerRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFLichSpawner.class, new TileEntityMobSpawnerRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFHydraSpawner.class, new TileEntityMobSpawnerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFMoonworm.class, new TileEntityTFMoonwormRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFTrophy.class, new TileEntityTFTrophyRenderer());
		
		// map item renderer
		MinecraftForgeClient.registerItemRenderer(TFItems.magicMap, new TFMagicMapRenderer(mc.gameSettings, mc.getTextureManager()));
		TFMazeMapRenderer mazeRenderer = new TFMazeMapRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(TFItems.mazeMap, mazeRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.oreMap, mazeRenderer);
		
		// giant item renderers
		TFGiantItemRenderer giantRenderer = new TFGiantItemRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(TFItems.giantPick, giantRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.giantSword, giantRenderer);
		
		TFGiantBlockRenderer giantBlockRenderer = new TFGiantBlockRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantLeaves), giantBlockRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantCobble), giantBlockRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantLog), giantBlockRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantObsidian), giantBlockRenderer);

		// fiery item render
		TFFieryItemRenderer fieryRenderer = new TFFieryItemRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryPick, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fierySword, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryIngot, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryHelm, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryPlate, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryLegs, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryBoots, fieryRenderer);

		// ice item renderers
		TFIceItemRenderer iceRenderer = new TFIceItemRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(TFItems.iceSword, iceRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.glassSword, iceRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.iceBow, iceRenderer);

		
		// block render ids
		blockComplexRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFFireflyJar(blockComplexRenderID));
				
		plantRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFPlants(plantRenderID));
				
		critterRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFCritters(critterRenderID));
		
		nagastoneRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFNagastone(nagastoneRenderID));

		magicLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFMagicLeaves(magicLeavesRenderID));
		
		pedestalRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFPedestal(pedestalRenderID));
		
		thornsRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFThorns(thornsRenderID));
		
		knightmetalBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFKnightMetal(knightmetalBlockRenderID));
		
		hugeLilyPadBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFHugeLilyPad(hugeLilyPadBlockRenderID));
		
		castleMagicBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockTFCastleMagic(castleMagicBlockRenderID));
		
		// armor model
		knightlyArmorModel = new ModelBiped[4];
		knightlyArmorModel[0] = new ModelTFKnightlyArmor(0, 0.5F);
		knightlyArmorModel[1] = new ModelTFKnightlyArmor(1, 1.0F);
		knightlyArmorModel[2] = new ModelTFKnightlyArmor(2, 0.5F);
		knightlyArmorModel[3] = new ModelTFKnightlyArmor(3, 0.5F);

		phantomArmorModel = new ModelBiped[2];
		phantomArmorModel[0] = new ModelTFPhantomArmor(0, 0.5F);
		phantomArmorModel[1] = new ModelTFPhantomArmor(1, 1.0F);
		
		yetiArmorModel = new ModelBiped[4];
		yetiArmorModel[0] = new ModelTFYetiArmor(0, 0.6F);
		yetiArmorModel[1] = new ModelTFYetiArmor(1, 1.0F);
		yetiArmorModel[2] = new ModelTFYetiArmor(2, 0.4F);
		yetiArmorModel[3] = new ModelTFYetiArmor(3, 0.55F);

		arcticArmorModel = new ModelBiped[4];
		arcticArmorModel[0] = new ModelTFArcticArmor(0, 0.6F);
		arcticArmorModel[1] = new ModelTFArcticArmor(1, 1.0F);
		arcticArmorModel[2] = new ModelTFArcticArmor(2, 0.4F);
		arcticArmorModel[3] = new ModelTFArcticArmor(3, 0.55F);

		fieryArmorModel = new ModelBiped[4];
		fieryArmorModel[0] = new ModelTFFieryArmor(0, 0.5F);
		fieryArmorModel[1] = new ModelTFFieryArmor(1, 1.0F);
		fieryArmorModel[2] = new ModelTFFieryArmor(2, 0.5F);
		fieryArmorModel[3] = new ModelTFFieryArmor(3, 0.5F);



	}
	
	public int getCritterBlockRenderID() {
		return critterRenderID;
	}

	public int getPlantBlockRenderID() {
		return plantRenderID;
	}

	public int getComplexBlockRenderID() {
		return blockComplexRenderID;
	}
	
	public int getNagastoneBlockRenderID() {
		return nagastoneRenderID;
	}
	
	public int getMagicLeavesBlockRenderID() {
		return magicLeavesRenderID;
	}
	
	public int getPedestalBlockRenderID() {
		return pedestalRenderID;
	}
	
	public int getThornsBlockRenderID() {
		return thornsRenderID;
	}
	
	public int getKnightmetalBlockRenderID() {
		return knightmetalBlockRenderID;
	}
	
	public int getHugeLilyPadBlockRenderID() {
		return hugeLilyPadBlockRenderID;
	}
	
	public int getCastleMagicBlockRenderID() {
		return castleMagicBlockRenderID;
	}
	
	/**
	 * The prefix is not actually used, but we do need a render ID
	 */
	public int registerArmorRenderID(String prefix) {
		return RenderingRegistry.addNewArmourRendererPrefix(prefix);
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	

	/**
	 * Spawns a particle.  This is my copy of RenderGlobal.spawnParticle where I implement custom particles.
	 */
	public void spawnParticle(World world, String particleType, double x, double y, double z, double velX, double velY, double velZ)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null && mc.theWorld == world)
		{
			// TODO: check render settings?
			double distX = mc.renderViewEntity.posX - x;
			double distY = mc.renderViewEntity.posY - y;
			double distZ = mc.renderViewEntity.posZ - z;

			EntityFX particle = null;

			double maxDist = 64.0D; // normally 16.0D

			// check for particle max distance
			if (distX * distX + distY * distY + distZ * distZ < maxDist * maxDist)
			{

				if (particleType.equals("largeflame"))
				{
					particle = new EntityTFLargeFlameFX(world, x, y, z, velX, velY, velZ);
				}
				else if (particleType.equals("hugesmoke"))
				{
					particle = new EntitySmokeFX(world, x, y, z, velX, velY, velZ, 8.0f);
				}
				else if (particleType.equals("leafrune"))
				{
					particle = new EntityTFLeafRuneFX(world, x, y, z, velX, velY, velZ);
				}
				else if (particleType.equals("bosstear"))
				{
					particle = new EntityTFBossTearFX(world, x, y, z, velX, velY, velZ, Items.GHAST_TEAR);
				}
				else if (particleType.equals("ghasttrap"))
				{
					particle = new EntityTFGhastTrapFX(world, x, y, z, velX, velY, velZ);
				}
				else if (particleType.equals("protection"))
				{
					particle = new EntityTFProtectionFX(world, x, y, z, velX, velY, velZ);
				}
				else if (particleType.equals("snowstuff"))
				{
					particle = new EntityTFSnowFX(world, x, y, z, velX, velY, velZ);
				}
				else if (particleType.equals("snowwarning"))
				{
					particle = new EntityTFSnowWarningFX(world, x, y, z, velX, velY, velZ, 1F);
				}
				else if (particleType.equals("snowguardian"))
				{
					particle = new EntityTFSnowGuardianFX(world, x, y, z, velX, velY, velZ, 0.75F);
				}
				else if (particleType.equals("icebeam"))
				{
					particle = new EntityTFIceBeamFX(world, x, y, z, velX, velY, velZ, 0.75F);
				}
				else if (particleType.equals("annihilate"))
				{
					particle = new EntityTFAnnihilateFX(world, x, y, z, velX, velY, velZ, 0.75F);
				}

				// if we made a partcle, go ahead and add it
				if (particle != null)
				{
					particle.prevPosX = particle.posX;
					particle.prevPosY = particle.posY;
					particle.prevPosZ = particle.posZ;
					
					// we keep having a non-threadsafe crash adding particles directly here, so let's pass them to a buffer
					//clientTicker.addParticle(particle); 
					mc.effectRenderer.addEffect(particle); // maybe it's fixed?
				}
			}
		}
	}
	

	public ModelBiped getKnightlyArmorModel(int armorSlot) {
		return knightlyArmorModel[armorSlot];
	}
	
	public ModelBiped getPhantomArmorModel(int armorSlot) {
		return phantomArmorModel[armorSlot];
	}
	
	public ModelBiped getYetiArmorModel(int armorSlot) {
		return yetiArmorModel[armorSlot];
	}

	public ModelBiped getArcticArmorModel(int armorSlot) {
		return arcticArmorModel[armorSlot];
	}
	
	
	public ModelBiped getFieryArmorModel(int armorSlot) {
		return this.fieryArmorModel[armorSlot];
	}

	public boolean isDangerOverlayShown() {
		return isDangerOverlayShown;
	}

	public void setDangerOverlayShown(boolean isDangerOverlayShown) {
		this.isDangerOverlayShown = isDangerOverlayShown;
		
	}
	
	public void doBlockAnnihilateEffect(World worldObj, int blockX, int blockY, int blockZ) {
		// particles from the block?
//		for (int i = 0; i < 10; i++) {
//	        
//	        double d0 = worldObj.rand.nextGaussian() * 0.02D;
//	        double d1 = worldObj.rand.nextGaussian() * 0.02D;
//	        double d2 = worldObj.rand.nextGaussian() * 0.02D;
//
//	        float dx = blockX + 0.5F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.4F;
//	        float dy = blockY + 0.5F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.4F;
//	        float dz = blockZ + 0.5F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.4F;
//
//			TwilightForestMod.proxy.spawnParticle(worldObj, "annihilate", dx, dy, dz, d0, d1, d2);
//
//		}
		
		
        byte four = 4;

        for (int dx = 0; dx < four; ++dx)
        {
            for (int dy = 0; dy < four; ++dy)
            {
                for (int dz = 0; dz < four; ++dz)
                {
                    double d0 = (double)blockX + ((double)dx + 0.5D) / (double)four;
                    double d1 = (double)blockY + ((double)dy + 0.5D) / (double)four;
                    double d2 = (double)blockZ + ((double)dz + 0.5D) / (double)four;
                    
        	        double gx = worldObj.rand.nextGaussian() * 0.2D;
        	        double gy = worldObj.rand.nextGaussian() * 0.2D;
        	        double gz = worldObj.rand.nextGaussian() * 0.2D;
                    
        			TwilightForestMod.proxy.spawnParticle(worldObj, "annihilate", d0, d1, d2, gx, gy, gz);

                    
                    //this.addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, d0 - (double)p_147215_1_ - 0.5D, d1 - (double)p_147215_2_ - 0.5D, d2 - (double)p_147215_3_ - 0.5D, p_147215_4_, p_147215_5_)).applyColourMultiplier(p_147215_1_, p_147215_2_, p_147215_3_));
                }
            }
        }
	}

}
