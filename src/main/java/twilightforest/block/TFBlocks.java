package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import twilightforest.TwilightForestMod;
import twilightforest.item.ItemBlockTFMeta;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TFBlocks {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public static Block log;
    public static Block leaves;
    public static Block firefly;
    public static Block portal;
    public static Block mazestone;
    public static Block hedge;
    public static Block bossSpawner;
    public static Block fireflyJar;
    public static Block plant;
    public static Block cicada;
    public static Block root;
    public static Block uncraftingTable;
    public static Block fireJet;
    public static Block nagastone;
    public static Block sapling;
    public static Block magicLog;
    public static Block magicLogSpecial;
    public static Block magicLeaves;
    public static Block moonworm;
    public static Block towerWood;
    public static Block towerDevice;
    //public static Block towerAntenna;
    public static Block towerTranslucent;
    public static Block trophy;
    public static Block shield;
    public static Block trophyPedestal;
    public static Block auroraBlock;
    public static Block underBrick;
    public static Block thorns;
    public static Block burntThorns;
    public static Block thornRose;
    public static Block leaves3;
    public static Block deadrock;
    public static Block darkleaves;
    public static Block auroraPillar;
    public static BlockSlab auroraSlab;
    public static BlockSlab auroraDoubleSlab;
    public static Block trollSteinn;
    public static Block wispyCloud;
    public static Block fluffyCloud;
    public static Block giantCobble;
    public static Block giantLog;
    public static Block giantLeaves;
    public static Block giantObsidian;
    public static Block uberousSoil;
    public static Block hugeStalk;
    public static Block hugeGloomBlock;
    public static Block trollVidr;
    public static Block unripeTrollBer;
    public static Block trollBer;
    public static Block knightmetalStorage;
    public static Block hugeLilyPad;
    public static Block hugeWaterLily;
    public static Block slider;
    public static Block castleBlock;
    public static Block castleMagic;
    public static Block forceField;
    public static Block cinderFurnace;
    public static Block cinderFurnaceLit;
    public static Block cinderLog;
    public static Block castleDoor;
    public static Block castleDoorVanished;
    public static Block castleUnlock;

	public static void registerBlocks() {
		
        log = (new BlockTFLog()).setUnlocalizedName("TFLog");
        leaves = (new BlockTFLeaves()).setUnlocalizedName("TFLeaves");
        firefly = (new BlockTFFirefly()).setUnlocalizedName("TFFirefly");
        cicada = (new BlockTFCicada()).setUnlocalizedName("TFCicada");
        portal = (new BlockTFPortal()).setUnlocalizedName("TFPortal");
        mazestone = (new BlockTFMazestone()).setUnlocalizedName("TFMazestone");
        hedge = (new BlockTFHedge()).setUnlocalizedName("TFHedge");
        bossSpawner = (new BlockTFBossSpawner()).setUnlocalizedName("TFBossSpawner");
        fireflyJar = (new BlockTFFireflyJar()).setUnlocalizedName("TFFireflyJar");
        plant = (new BlockTFPlant()).setUnlocalizedName("TFPlant");
        root = (new BlockTFRoots()).setUnlocalizedName("TFRoots");
        uncraftingTable = (new BlockTFUncraftingTable()).setUnlocalizedName("TFUncraftingTable");
        fireJet = (new BlockTFFireJet()).setUnlocalizedName("TFFireJet");
        nagastone = (new BlockTFNagastone()).setUnlocalizedName("TFNagastone");
        sapling = (new BlockTFSapling()).setUnlocalizedName("TFSapling");
        magicLog = (new BlockTFMagicLog()).setUnlocalizedName("TFMagicLog");
        magicLogSpecial = (new BlockTFMagicLogSpecial()).setUnlocalizedName("TFMagicLogSpecial");
        magicLeaves = (new BlockTFMagicLeaves()).setUnlocalizedName("TFMagicLeaves");
        moonworm = (new BlockTFMoonworm()).setUnlocalizedName("TFMoonworm");
        towerWood = (new BlockTFTowerWood()).setUnlocalizedName("TFTowerStone");
        towerDevice = (new BlockTFTowerDevice()).setUnlocalizedName("TFTowerDevice");
        towerTranslucent = (new BlockTFTowerTranslucent()).setUnlocalizedName("TFTowerTranslucent");
        trophy = (new BlockTFTrophy()).setUnlocalizedName("TFTrophy");
        shield = (new BlockTFShield()).setUnlocalizedName("TFShield");
        trophyPedestal = (new BlockTFTrophyPedestal()).setUnlocalizedName("TFTrophyPedestal");
        auroraBlock = (new BlockTFAuroraBrick()).setUnlocalizedName("TFAuroraBrick");
        underBrick = (new BlockTFUnderBrick()).setUnlocalizedName("TFUnderBrick");
        thorns = (new BlockTFThorns()).setUnlocalizedName("TFThorns");
        burntThorns = (new BlockTFBurntThorns()).setUnlocalizedName("TFBurntThorns");
        thornRose = (new BlockTFThornRose()).setUnlocalizedName("TFThornRose");
        leaves3 = (new BlockTFLeaves3()).setUnlocalizedName("TFLeaves3");
        deadrock = (new BlockTFDeadrock()).setUnlocalizedName("TFDeadrock"); 
        darkleaves = (new BlockTFDarkLeaves()).setUnlocalizedName("DarkLeaves"); 
        auroraPillar = (new BlockTFAuroraPillar()).setUnlocalizedName("AuroraPillar"); 
        auroraSlab = (BlockSlab) (new BlockTFAuroraSlab(false)).setUnlocalizedName("AuroraSlab");
        auroraDoubleSlab = (BlockSlab) (new BlockTFAuroraSlab(true)).setUnlocalizedName("AuroraDoubleSlab");
        trollSteinn = (new BlockTFTrollSteinn()).setUnlocalizedName("TrollSteinn");
        wispyCloud = (new BlockTFWispyCloud()).setUnlocalizedName("WispyCloud");
        fluffyCloud = (new BlockTFFluffyCloud()).setUnlocalizedName("FluffyCloud");
        giantCobble = (new BlockTFGiantCobble()).setUnlocalizedName("GiantCobble");
        giantLog = (new BlockTFGiantLog()).setUnlocalizedName("GiantLog");
        giantLeaves = (new BlockTFGiantLeaves()).setUnlocalizedName("GiantLeaves");
        giantObsidian = (new BlockTFGiantObsidian()).setUnlocalizedName("GiantObsidian");
        uberousSoil = (new BlockTFUberousSoil()).setUnlocalizedName("UberousSoil");
        hugeStalk = (new BlockTFHugeStalk()).setUnlocalizedName("HugeStalk");
        hugeGloomBlock = (new BlockTFHugeGloomBlock()).setUnlocalizedName("HugeGloomBlock");
        trollVidr = (new BlockTFTrollRoot()).setUnlocalizedName("TrollVidr");
        unripeTrollBer = (new BlockTFUnripeTorchCluster()).setUnlocalizedName("UnripeTrollBer");
        trollBer = (new BlockTFRipeTorchCluster()).setUnlocalizedName("TrollBer");
        knightmetalStorage = (new BlockTFKnightmetalBlock()).setUnlocalizedName("KnightmetalBlock");
        hugeLilyPad = (new BlockTFHugeLilyPad()).setUnlocalizedName("HugeLilyPad");
        hugeWaterLily = (new BlockTFHugeWaterLily()).setUnlocalizedName("HugeWaterLily");
        slider = (new BlockTFSlider()).setUnlocalizedName("Slider");
        castleBlock = (new BlockTFCastleBlock()).setUnlocalizedName("CastleBrick");
        castleMagic = (new BlockTFCastleMagic()).setUnlocalizedName("CastleMagic");
        forceField = (new BlockTFForceField()).setUnlocalizedName("ForceField");
        cinderFurnace = (new BlockTFCinderFurnace(false)).setUnlocalizedName("CinderFurnaceIdle");
        cinderFurnaceLit = (new BlockTFCinderFurnace(true)).setUnlocalizedName("CinderFurnaceLit");
        cinderLog = (new BlockTFCinderLog()).setUnlocalizedName("CinderLog");
        castleDoor = (new BlockTFCastleDoor(false)).setUnlocalizedName("CastleDoor");
        castleDoorVanished = (new BlockTFCastleDoor(true)).setUnlocalizedName("CastleDoorVanished");
        castleUnlock = (new BlockTFCastleUnlock()).setUnlocalizedName("CastleUnlock");

		// register blocks with their pickup values
		registerMyBlock(log);
		registerMyBlock(root);
		registerMyBlock(leaves);
		registerMyBlock(firefly, ItemBlock.class);
		registerMyBlock(cicada, ItemBlock.class);
		registerMyBlock(portal, ItemBlock.class);
		registerMyBlock(mazestone);
		registerMyBlock(hedge);
		registerMyBlock(bossSpawner);
		registerMyBlock(fireflyJar, ItemBlock.class);
		registerMyBlock(plant, twilightforest.item.ItemBlockTFPlant.class);
		registerMyBlock(uncraftingTable, ItemBlock.class);
		registerMyBlock(fireJet);
		registerMyBlock(nagastone);
		registerMyBlock(sapling);
		registerMyBlock(moonworm, ItemBlock.class);
		registerMyBlock(magicLog);
		registerMyBlock(magicLeaves);
		registerMyBlock(magicLogSpecial);
		registerMyBlock(towerWood);
		registerMyBlock(towerDevice);
		registerMyBlock(towerTranslucent);
		registerMyBlock(trophy);
		registerMyBlock(shield);
		registerMyBlock(trophyPedestal);
		registerMyBlock(auroraBlock, ItemBlock.class);
		registerMyBlock(underBrick);
		registerMyBlock(thorns, twilightforest.item.ItemBlockTFThorns.class, thorns, ((BlockTFThorns) thorns).getNames());
		registerMyBlock(burntThorns, ItemBlock.class);
		registerMyBlock(thornRose, ItemBlock.class);
		registerMyBlock(leaves3);
		registerMyBlock(deadrock, twilightforest.item.ItemBlockTFDeadrock.class, deadrock, BlockTFDeadrock.names);
		registerMyBlock(darkleaves, ItemBlock.class);
		registerMyBlock(auroraPillar, ItemBlock.class);
		registerMyBlock(auroraSlab,  twilightforest.item.ItemBlockTFAuroraSlab.class, ((BlockSlab) auroraSlab), ((BlockSlab) auroraDoubleSlab), false);
		registerMyBlock(auroraDoubleSlab, twilightforest.item.ItemBlockTFAuroraSlab.class, ((BlockSlab) auroraSlab), ((BlockSlab) auroraDoubleSlab), true);
		registerMyBlock(trollSteinn, ItemBlock.class);
		registerMyBlock(wispyCloud, ItemBlock.class);
		registerMyBlock(fluffyCloud, ItemBlock.class);
		registerMyBlock(giantCobble, ItemBlock.class);
		registerMyBlock(giantLog, ItemBlock.class);
		registerMyBlock(giantLeaves, ItemBlock.class);
		registerMyBlock(giantObsidian, ItemBlock.class);
		registerMyBlock(uberousSoil, ItemBlock.class);
		registerMyBlock(hugeStalk, ItemBlock.class);
		registerMyBlock(hugeGloomBlock, ItemBlock.class);
		registerMyBlock(trollVidr, ItemBlock.class);
		registerMyBlock(unripeTrollBer, ItemBlock.class);
		registerMyBlock(trollBer, ItemBlock.class);
		registerMyBlock(knightmetalStorage, ItemBlock.class);
		registerMyBlock(hugeLilyPad, twilightforest.item.ItemBlockTFHugeLilyPad.class);
		registerMyBlock(hugeWaterLily, twilightforest.item.ItemBlockTFHugeWaterLily.class);
		registerMyBlock(slider);
		registerMyBlock(castleBlock);
		registerMyBlock(castleMagic);
		registerMyBlock(forceField);
		registerMyBlock(cinderFurnace, ItemBlock.class);
		registerMyBlock(cinderFurnaceLit, ItemBlock.class);
		registerMyBlock(cinderLog);
		registerMyBlock(castleDoor);
		registerMyBlock(castleDoorVanished);

		// fire info
        Blocks.FIRE.setFireInfo(log, 5, 5);
        Blocks.FIRE.setFireInfo(leaves, 30, 60);
        Blocks.FIRE.setFireInfo(leaves3, 30, 60);
	}

	private static void registerMyBlock(Block block, Class<? extends ItemBlock> pickup, BlockSlab singleSlab, BlockSlab doubleSlab, boolean isDouble) {
		GameRegistry.registerBlock(block, pickup, block.getUnlocalizedName(), singleSlab, doubleSlab, isDouble);

	}

	private static void registerMyBlock(Block block, Class<? extends ItemBlock> pickup, Block blockAgain, String[] names) {
		GameRegistry.registerBlock(block, pickup, block.getUnlocalizedName(), blockAgain, names);
	}

	private static void registerMyBlock(Block block, Class<? extends ItemBlock> pickup) 
	{
		GameRegistry.registerBlock(block, pickup, block.getUnlocalizedName());
	}
	
	private static void registerMyBlock(Block block) 
	{
		GameRegistry.registerBlock(block, ItemBlockTFMeta.class, block.getUnlocalizedName());
	}

}
