package twilightforest.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFKobold;
import twilightforest.entity.passive.EntityTFMobileFirefly;
import net.minecraftforge.fml.common.FMLLog;


public abstract class TFBiomeBase extends Biome  {
	
	static
	{
		assignBlankBiomeIds();
		areThereBiomeIdConflicts();
	}
	
	public static final Biome tfLake = (new TFBiomeTwilightLake(TwilightForestMod.idBiomeLake)).setColor(0x0000ff).setBiomeName("Twilight Lake").setHeight(height_DeepOceans);
	public static final Biome twilightForest = (new TFBiomeTwilightForest(TwilightForestMod.idBiomeTwilightForest)).setColor(0x005500).setBiomeName("Twilight Forest");
	public static final Biome twilightForest2 = (new TFBiomeTwilightForestVariant(TwilightForestMod.idBiomeTwilightForestVariant)).setColor(0x005522).setBiomeName("Dense Twilight Forest").setHeight(height_MidPlains);
	public static final Biome highlands = (new TFBiomeHighlands(TwilightForestMod.idBiomeHighlands)).setColor(0x556644).setBiomeName("Twilight Highlands").setHeight(new Height(3.5F, 0.05F));
	public static final Biome mushrooms = (new TFBiomeMushrooms(TwilightForestMod.idBiomeMushrooms)).setColor(0x226622).setBiomeName("Mushroom Forest");
	public static final Biome tfSwamp = (new TFBiomeSwamp(TwilightForestMod.idBiomeSwamp)).setColor(0x334422).setBiomeName("Twilight Swamp").setHeight(new Height(-0.125F, 0.125F));
	public static final Biome stream = (new TFBiomeStream(TwilightForestMod.idBiomeStream)).setColor(0x3253b7).setBiomeName("Twilight Stream").setHeight(height_ShallowWaters);
	public static final Biome tfSnow = (new TFBiomeSnow(TwilightForestMod.idBiomeSnowfield)).setColor(0xccffff).setBiomeName("Snowy Forest").setHeight(height_MidPlains);
	public static final Biome glacier = (new TFBiomeGlacier(TwilightForestMod.idBiomeGlacier)).setColor(0x7777bb).setBiomeName("Twilight Glacier");
	public static final Biome clearing = (new TFBiomeClearing(TwilightForestMod.idBiomeClearing)).setColor(0x349b34).setBiomeName("Twilight Clearing").setHeight(height_LowPlains);
	public static final Biome oakSavanna = (new TFBiomeOakSavanna(TwilightForestMod.idBiomeOakSavanna)).setColor(0x446622).setBiomeName("Oak Savanna").setHeight(height_MidPlains);
	public static final Biome fireflyForest = (new TFBiomeFireflyForest(TwilightForestMod.idBiomeFireflyForest)).setColor(0x006633).setBiomeName("Firefly Forest").setHeight(height_LowPlains);
	public static final Biome deepMushrooms = (new TFBiomeDeepMushrooms(TwilightForestMod.idBiomeDeepMushrooms)).setColor(0x663322).setBiomeName("Deep Mushroom Forest").setHeight(height_LowPlains);
	public static final Biome darkForest = (new TFBiomeDarkForest(TwilightForestMod.idBiomeDarkForest)).setColor(0x003311).setBiomeName("Dark Forest").setHeight(height_LowPlains);
	public static final Biome enchantedForest = (new TFBiomeEnchantedForest(TwilightForestMod.idBiomeEnchantedForest)).setColor(0x115566).setBiomeName("Enchanted Forest");
	public static final Biome fireSwamp = (new TFBiomeFireSwamp(TwilightForestMod.idBiomeFireSwamp)).setColor(0x42231a).setBiomeName("Fire Swamp").setHeight(height_Default);
	public static final Biome darkForestCenter = (new TFBiomeDarkForestCenter(TwilightForestMod.idBiomeDarkForestCenter)).setColor(0x002200).setBiomeName("Dark Forest Center").setHeight(height_LowPlains);
	public static final Biome highlandsCenter = (new TFBiomeFinalPlateau(TwilightForestMod.idBiomeHighlandsCenter)).setColor(0x334422).setBiomeName("Highlands Center").setHeight(new Height(10.5F, 0.025F));
	public static final Biome thornlands = (new TFBiomeThornlands(TwilightForestMod.idBiomeThornlands)).setColor(0x223322).setBiomeName("Thornlands").setHeight(new Height(6F, 0.1F));

	protected WorldGenBigMushroom bigMushroomGen;
	protected WorldGenBirchTree birchGen;
	
    /**
     * Holds the classes of IMobs (hostile mobs) that can be spawned in the biome.
     */
    protected List<SpawnListEntry> undergroundMonsterList;

	@SuppressWarnings("unchecked")
	public TFBiomeBase(int i) 
	{
		super(i);
		
		bigMushroomGen = new WorldGenBigMushroom();
		birchGen = new WorldGenBirchTree(false, false);
		
		// try to stop world leaks
		this.worldGeneratorBigTree = null;
		
    	// remove normal monster spawns
        spawnableMonsterList.clear();
        // remove squids
        spawnableWaterCreatureList.clear();
        // custom creature list.
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFBighorn.class, 12, 4, 4));
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFBoar.class, 10, 4, 4));
        spawnableCreatureList.add(new SpawnListEntry(net.minecraft.entity.passive.EntityChicken.class, 10, 4, 4));
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFDeer.class, 15, 4, 5));
        spawnableCreatureList.add(new SpawnListEntry(net.minecraft.entity.passive.EntityWolf.class, 5, 4, 4));
        
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFTinyBird.class, 15, 4, 8));
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFSquirrel.class, 10, 2, 4));
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFBunny.class, 10, 4, 5));
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFRaven.class, 10, 1, 2));
        
        undergroundMonsterList = new ArrayList<SpawnListEntry>();
        
        undergroundMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntityCreeper.class, 1, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
		undergroundMonsterList.add(new SpawnListEntry(EntityTFKobold.class, 10, 4, 8));

		this.spawnableCaveCreatureList.clear();
        this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityBat.class, 10, 8, 8));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityTFMobileFirefly.class, 10, 8, 8));
        
        getTFBiomeDecorator().setTreesPerChunk(10);
        getTFBiomeDecorator().setGrassPerChunk(2);
	}
	
	@Override
    public TFBiomeBase setColor(int par1) {
    	return (TFBiomeBase) super.setColor(par1);
    }

	
    /**
     * returns the chance a creature has to spawn.
     */
    @Override
    public float getSpawningChance()
    {
    	// okay, 20% more animals
        return 0.12F;
    }

    /**
     * Allocate a new BiomeDecorator for this Biome
     */
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return new TFBiomeDecorator();
    }
    
    protected TFBiomeDecorator getTFBiomeDecorator() {
    	return (TFBiomeDecorator)this.theBiomeDecorator;
    }

    /**
     * Forest trees all over
     * 
     * used to be getRandomWorldGenForTrees()
     */
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        if(random.nextInt(5) == 0)
        {
        	return birchGen;
        }
        if(random.nextInt(10) == 0)
        {
            return new WorldGenBigTree(false);
        } else
        {
            return worldGeneratorTrees;
        }
    }
	
    /**
     * Ferns!
     */
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        if (par1Random.nextInt(4) == 0)
        {
            return new WorldGenTallGrass(Blocks.TALLGRASS, 2);
        }
        else
        {
            return new WorldGenTallGrass(Blocks.TALLGRASS, 1);
        }
    }
    
    /**
     * If any biome IDs are -1, find an open ID for them.
     */
    public static boolean assignBlankBiomeIds() {
		boolean assigned = false;
		
		boolean[] usedIDs = new boolean[Biome.getBiomeGenArray().length];
		
		checkUsedIDs(usedIDs);
		
		TwilightForestMod.idBiomeLake = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeLake);
    	TwilightForestMod.idBiomeTwilightForest = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeTwilightForest);
    	TwilightForestMod.idBiomeTwilightForestVariant = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeTwilightForestVariant);
    	TwilightForestMod.idBiomeHighlands = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeHighlands);
    	TwilightForestMod.idBiomeMushrooms = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeMushrooms);
    	TwilightForestMod.idBiomeSwamp = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeSwamp);
    	TwilightForestMod.idBiomeStream = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeStream);
    	TwilightForestMod.idBiomeSnowfield = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeSnowfield);
    	TwilightForestMod.idBiomeGlacier = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeGlacier);
    	TwilightForestMod.idBiomeClearing = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeClearing);
    	TwilightForestMod.idBiomeOakSavanna = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeOakSavanna);
    	TwilightForestMod.idBiomeFireflyForest = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeFireflyForest);
    	TwilightForestMod.idBiomeDeepMushrooms = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeDeepMushrooms);
    	TwilightForestMod.idBiomeDarkForestCenter = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeDarkForestCenter);
    	TwilightForestMod.idBiomeHighlandsCenter = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeHighlandsCenter);
    	TwilightForestMod.idBiomeDarkForest = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeDarkForest);
    	TwilightForestMod.idBiomeEnchantedForest = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeEnchantedForest);
    	TwilightForestMod.idBiomeFireSwamp = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeFireSwamp);
    	TwilightForestMod.idBiomeThornlands = assignIdIfNeeded(usedIDs, TwilightForestMod.idBiomeThornlands);
		
		return assigned;
	}

    /**
     * Populate the array with whether there is a biome using that ID
     */
	private static void checkUsedIDs(boolean[] usedIDs) {
		for (int i = 0; i < usedIDs.length; i++) {
			usedIDs[i] = (Biome.getBiomeGenArray()[i] != null);
		}
	}

	/**
	 * Return a new biome ID if the given id is -1
	 */
	private static int assignIdIfNeeded(boolean[] usedIDs, int biomeID) {
		if (biomeID == -1) {
			biomeID = findNextOpenBiomeId(usedIDs);
			TwilightForestMod.hasAssignedBiomeID = true;
		}
		
		return biomeID;
	}

	/**
	 * Find an open biome ID
	 */
	private static int findNextOpenBiomeId(boolean[] usedIDs) {
		for (int i = 0; i < 256; i++) {
			if (!usedIDs[i]) {
				usedIDs[i] = true;
				return i;
			}
		}
		
		// Uh oh...
		FMLLog.warning("[TwilightForest] Could not find open biome ID.  Edit the Twilight Forest config to give all biomes unique IDs.");
		return -1;
	}

	/**
     * Check all of our biome IDs.  If there is a biome in them that is not null and does not inherit from TFBiomeBase, make a warning.
     */
    public static boolean areThereBiomeIdConflicts()
    {
    	boolean conflict = TwilightForestMod.hasBiomeIdConflicts;
    	
		//FMLLog.info("[TwilightForest] Starting biome conflict detection.  Conflict = " + conflict);
    	
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeLake);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeTwilightForest);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeTwilightForestVariant);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeHighlands);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeMushrooms);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeSwamp);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeStream);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeSnowfield);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeGlacier);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeClearing);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeOakSavanna);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeFireflyForest);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeDeepMushrooms);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeDarkForestCenter);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeHighlandsCenter);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeDarkForest);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeEnchantedForest);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeFireSwamp);
    	conflict |= isConflictAtBiomeID(TwilightForestMod.idBiomeThornlands);
    	
       	if (conflict) {
			FMLLog.warning("[TwilightForest] Biome ID conflict detected.  Edit the Twilight Forest config to give all biomes unique IDs.");
    	} else {
			//FMLLog.info("[TwilightForest] No biome ID conflicts detected!  You're safe!");
    	}
       	
       	TwilightForestMod.hasBiomeIdConflicts |= conflict;
       	
		return conflict;
    }

    /**
     * Check the ID for conflicts
     */
	public static boolean isConflictAtBiomeID(int id) {
		Biome biomeAt = Biome.getBiome(id);
		
		if (biomeAt == null || biomeAt instanceof TFBiomeBase) {
			//FMLLog.warning("[TwilightForest] Biome ID %d contains a biome named %s.", id, biomeAt);
			return false;
		} else {
			FMLLog.warning("[TwilightForest] Biome ID conflict.  Biome ID %d contains a biome named %s, but Twilight Forest is set to use that ID.", id, biomeAt.biomeName);
			return true;
		}
	}
	
	/**
	 * Register our biomes with the Forge biome dictionary
	 */
	public static void registerWithBiomeDictionary()
	{
		BiomeDictionary.registerBiomeType(tfLake, Type.OCEAN);
		BiomeDictionary.registerBiomeType(twilightForest, Type.FOREST);
		BiomeDictionary.registerBiomeType(twilightForest2, Type.FOREST, Type.DENSE);
		BiomeDictionary.registerBiomeType(highlands, Type.FOREST, Type.MOUNTAIN, Type.CONIFEROUS);
		BiomeDictionary.registerBiomeType(mushrooms, Type.FOREST, Type.MUSHROOM);
		BiomeDictionary.registerBiomeType(tfSwamp, Type.SWAMP, Type.WET);
		BiomeDictionary.registerBiomeType(stream, Type.RIVER);
		BiomeDictionary.registerBiomeType(tfSnow, Type.FOREST, Type.SNOWY, Type.COLD, Type.CONIFEROUS);
		BiomeDictionary.registerBiomeType(glacier, Type.COLD, Type.SNOWY, Type.WASTELAND);
		BiomeDictionary.registerBiomeType(clearing, Type.PLAINS, Type.SPARSE);
		BiomeDictionary.registerBiomeType(oakSavanna, Type.FOREST, Type.SPARSE);
		BiomeDictionary.registerBiomeType(fireflyForest, Type.FOREST, Type.LUSH);
		BiomeDictionary.registerBiomeType(deepMushrooms, Type.FOREST, Type.MUSHROOM);
		BiomeDictionary.registerBiomeType(darkForest, Type.FOREST, Type.DENSE, Type.SPOOKY);
		BiomeDictionary.registerBiomeType(enchantedForest, Type.FOREST, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(fireSwamp, Type.SWAMP, Type.WASTELAND, Type.HOT);
		BiomeDictionary.registerBiomeType(darkForestCenter, Type.FOREST, Type.DENSE, Type.SPOOKY, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(highlandsCenter, Type.MESA, Type.DEAD, Type.DRY, Type.WASTELAND);
		BiomeDictionary.registerBiomeType(thornlands, Type.HILLS, Type.DEAD, Type.DRY, Type.WASTELAND);
	}
	

//    /**
//     * Provides the basic grass color based on the biome temperature and rainfall
//     */
//    @SideOnly(Side.CLIENT)
//    @Override
//    public int getBiomeGrassColor(int x, int y, int z)
//    {
//    	// I hate to be grumpy, but using events for this is a waste of memory and GC resources
//        double d0 = (double)MathHelper.clamp_float(this.getFloatTemperature(x, y, z), 0.0F, 1.0F);
//        double d1 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
//        return ColorizerGrass.getGrassColor(d0, d1);
//    }    
    
//    /**
//     * Provides the basic foliage color based on the biome temperature and rainfall
//     */
//    @SideOnly(Side.CLIENT)
//    @Override
//    public int getBiomeFoliageColor(int x, int y, int z)
//    {
//    	// I hate to be grumpy, but using events for this is a waste of memory and GC resources
//        double d0 = (double)MathHelper.clamp_float(this.getFloatTemperature(x, y, z), 0.0F, 1.0F);
//        double d1 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
//        return ColorizerFoliage.getFoliageColor(d0, d1);
//    }
    
    
//    @SideOnly(Side.CLIENT)
//    public int getWaterColorMultiplier()
//    {
//    	// I hate to be grumpy, but using events for this is a waste of memory and GC resources
//        return this.waterColorMultiplier;
//    }
    
    public void genTerrainBlocks(World world, Random rand, Block[] blockStorage, byte[] metaStorage, int x, int z, double stoneNoise)
    {
        this.genTwilightBiomeTerrain(world, rand, blockStorage, metaStorage, x, z, stoneNoise);
    }

    /**
     * This is just the biome terrain gen function modified to have sea level at 32 instead of 64
     */
    public void genTwilightBiomeTerrain(World world, Random rand, Block[] blockStorage, byte[] metaStorage, int x, int z, double stoneNoise)
    {
        Block topBlock = this.topBlock;
        byte topMeta = (byte)(this.field_150604_aj & 255);
        Block fillerBlock = this.fillerBlock;
        byte fillerMeta = (byte)(this.field_76754_C & 255);
        int currentFillerDepth = -1;
        int maxFillerDepth = (int)(stoneNoise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int maskX = x & 15;
        int maskZ = z & 15;
        int worldHeight = blockStorage.length / 256;
        
        int seaLevel = 32;

        for (int y = 255; y >= 0; --y)
        {
            int index = (maskZ * 16 + maskX) * worldHeight + y;

            if (y <= 0 + rand.nextInt(5))
            {
                blockStorage[index] = Blocks.BEDROCK;
            }
            else
            {
                Block currentBlock = blockStorage[index];

                if (currentBlock != null && currentBlock.getMaterial() != Material.AIR)
                {
                    if (currentBlock == Blocks.STONE)
                    {
                    	// ADDED STONE REPLACEMENT
                    	if (this.getStoneReplacementBlock() != null) {
                    		blockStorage[index] = this.getStoneReplacementBlock();
                    		metaStorage[index] = this.getStoneReplacementMeta();
                    	}
                        
                        if (currentFillerDepth == -1)
                        {
                            if (maxFillerDepth <= 0)
                            {
                                topBlock = null;
                                topMeta = 0;
                                fillerBlock = Blocks.STONE;
                                fillerMeta = 0;
                            }
                            else if (y >= (seaLevel - 5) && y <= seaLevel)
                            {
                                topBlock = this.topBlock;
                                topMeta = (byte)(this.field_150604_aj & 255);
                                fillerBlock = this.fillerBlock;
                                fillerMeta = (byte)(this.field_76754_C & 255);
                            }

                            if (y < (seaLevel - 1) && (topBlock == null || topBlock.getMaterial() == Material.AIR))
                            {
                                if (this.getFloatTemperature(x, y, z) < 0.15F)
                                {
                                    topBlock = Blocks.ICE;
                                    topMeta = 0;
                                }
                                else
                                {
                                    topBlock = Blocks.WATER;
                                    topMeta = 0;
                                }
                            }

                            currentFillerDepth = maxFillerDepth;

                            if (y >= (seaLevel - 2))
                            {
                                blockStorage[index] = topBlock;
                                metaStorage[index] = topMeta;
                            }
                            else if (y < (seaLevel - 8) - maxFillerDepth)
                            {
                                topBlock = null;
                                fillerBlock = Blocks.STONE;
                                fillerMeta = 0;
                                blockStorage[index] = Blocks.GRAVEL;
                            }
                            else
                            {
                                blockStorage[index] = fillerBlock;
                                metaStorage[index] = fillerMeta;
                            }
                        }
                        else if (currentFillerDepth > 0)
                        {
                            --currentFillerDepth;
                            blockStorage[index] = fillerBlock;
                            metaStorage[index] = fillerMeta;

                            if (currentFillerDepth == 0 && fillerBlock == Blocks.SAND)
                            {
                                currentFillerDepth = rand.nextInt(4) + Math.max(0, y - (seaLevel - 1));
                                fillerBlock = Blocks.SANDSTONE;
                                fillerMeta = 0;
                            }
                        }
                    }
                }
                else
                {
                    //currentFillerDepth = -1;
                }
            }
        }
    }

    /**
     * Return a block if you want it to replace stone in the terrain generation
     */
	public Block getStoneReplacementBlock() {
		return null;
	}
	
    /**
     * Metadata for the stone replacement block
     */
	public byte getStoneReplacementMeta() {
		return 0;
	}

	/**
	 * Does the player have the achievement needed to be in this biome?
	 * Defaults to true for no achievement required biomes.
	 */
	public boolean doesPlayerHaveRequiredAchievement(EntityPlayer player) {
		
		if (getRequiredAchievement() != null && player instanceof EntityPlayerMP && ((EntityPlayerMP)player).func_147099_x() != null) {
			StatisticsFile stats = ((EntityPlayerMP)player).func_147099_x();
			
			return stats.hasAchievementUnlocked(this.getRequiredAchievement());
		} else if (getRequiredAchievement() != null && player instanceof EntityClientPlayerMP && ((EntityClientPlayerMP)player).getStatFileWriter() != null) {
			StatFileWriter stats = ((EntityClientPlayerMP)player).getStatFileWriter();
			
			return stats.hasAchievementUnlocked(this.getRequiredAchievement());
		} else {
			return true;
		}
	}

	/**
	 * If there is a required achievement to be here, return it, otherwise return null
	 */
	protected Achievement getRequiredAchievement() {
		return null;
	}

	/**
	 * Do something bad to a player in the wrong biome.
	 */
	public void enforceProgession(EntityPlayer player, World world) {
		;
	}
	
    /**
     * Returns the list of underground creatures.
     */
	public List<SpawnListEntry> getUndergroundSpawnableList() {
    	return this.undergroundMonsterList;
    }
}

