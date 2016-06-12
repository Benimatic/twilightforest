package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFTroll;
import twilightforest.world.TFGenTrollRoots;


public class TFBiomeHighlands extends TFBiomeBase {
	
	
    private static final WorldGenTaiga1 taigaGen1 = new WorldGenTaiga1();
    private static final WorldGenTaiga2 taigaGen2 = new WorldGenTaiga2(false);
    private static final WorldGenMegaPineTree megaPineGen1 = new WorldGenMegaPineTree(false, false);
    private static final WorldGenMegaPineTree megaPineGen2 = new WorldGenMegaPineTree(false, true);
    private static final WorldGenBlockBlob genBoulder = new WorldGenBlockBlob(Blocks.MOSSY_COBBLESTONE, 0);
    private static final TFGenTrollRoots genTrollRoots = new TFGenTrollRoots();
    private static final WorldGenTallGrass worldGenMushgloom = new WorldGenTallGrass(TFBlocks.plant, BlockTFPlant.META_MUSHGLOOM);
    

	public TFBiomeHighlands(int i) {
		super(i);
		
        this.temperature = 0.4F;
        this.rainfall = 0.7F;
        
		((TFBiomeDecorator)theBiomeDecorator).canopyPerChunk = -999;
		
        this.theBiomeDecorator.grassPerChunk = 7;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.generateLakes = false; // actually underground water sources

		
        //this.spawnableMonsterList.add(new SpawnListEntry(EntityTFTroll.class, 10, 4, 4));

		
        undergroundMonsterList.clear();
        undergroundMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntityCreeper.class, 1, 4, 4));
        undergroundMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
        
        undergroundMonsterList.add(new SpawnListEntry(EntityTFTroll.class, 10, 4, 4));
	}
	
    /**
     * Birches and large trees only
     */
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        if(random.nextInt(4) == 0){
        	return taigaGen1;
        } else if(random.nextInt(10) == 0){
            return taigaGen2;
        } else if(random.nextInt(3) == 0){
            return megaPineGen1;
        } else if(random.nextInt(13) == 0){
            return megaPineGen2;
        } else {
            return birchGen;
        }
    }
	
    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        return par1Random.nextInt(5) > 0 ? new WorldGenTallGrass(Blocks.TALLGRASS, 2) : new WorldGenTallGrass(Blocks.TALLGRASS, 1);
    }

    public void genTerrainBlocks(World world, Random rand, Block[] blockStorage, byte[] metaStorage, int x, int z, double noiseVal)
    {
        if (true)
        {
            this.topBlock = Blocks.GRASS;
            this.field_150604_aj = 0;
            this.fillerBlock = Blocks.DIRT;

            if (noiseVal > 1.75D)
            {
                this.topBlock = Blocks.DIRT;
                this.field_150604_aj = 1;
            }
            else if (noiseVal > -0.95D)
            {
                this.topBlock = Blocks.DIRT;
                this.field_150604_aj = 2;
            }
        }

        this.genTwilightBiomeTerrain(world, rand, blockStorage, metaStorage, x, z, noiseVal);
    }

    /**
     * Add extra decorations
     */
    public void decorate(World par1World, Random par2Random, int mapX, int mapZ)
    {
    	int dx, dy, dz;

    	// boulders
    	int maxBoulders = par2Random.nextInt(2);
    	for (int i = 0; i < maxBoulders; ++i)
    	{
    		dx = mapX + par2Random.nextInt(16) + 8;
    		dz = mapZ + par2Random.nextInt(16) + 8;
    		dy = par1World.getHeightValue(dx, dz);
    		genBoulder.generate(par1World, par2Random, dx, dy, dz);
    	}

    	// giant ferns
    	genTallFlowers.func_150548_a(3);
    	for (int i = 0; i < 7; ++i)
    	{
    		dx = mapX + par2Random.nextInt(16) + 8;
    		dz = mapZ + par2Random.nextInt(16) + 8;
    		dy = par2Random.nextInt(par1World.getHeightValue(dx, dz) + 32);
    		genTallFlowers.generate(par1World, par2Random, dx, dy, dz);
    	}
    	
    	// mushglooms
    	for (int i = 0; i < 1; ++i)
    	{
    		int rx = mapX + par2Random.nextInt(16) + 8;
    		int rz = mapZ + par2Random.nextInt(16) + 8;
    		int ry = par2Random.nextInt(64);
    		// mushglooms
    		worldGenMushgloom.generate(par1World, par2Random, rx, ry, rz);
    	}

		// generate roots
		for (int i = 0; i < 24; ++i)
		{
		    int rx = mapX + par2Random.nextInt(16) + 8;
		    byte ry = 64;
		    int rz = mapZ + par2Random.nextInt(16) + 8;
		    genTrollRoots.generate(par1World, par2Random, rx, ry, rz);
		}

    	super.decorate(par1World, par2Random, mapX, mapZ);
    }
    
    /**
     * Flowers
     */
    public String func_150572_a(Random rand, int x, int y, int z)
    {
        return rand.nextBoolean() ? BlockFlower.field_149858_b[0] : BlockFlower.field_149859_a[8];
    }
    
    
    
	/**
	 * If there is a required achievement to be here, return it, otherwise return null
	 */
	protected Achievement getRequiredAchievement() {
		return TFAchievementPage.twilightProgressGlacier;
	}

	/**
	 * Do something bad to a player in the wrong biome.
	 */
	public void enforceProgession(EntityPlayer player, World world) {
		if (!world.isRemote && world.getWorldTime() % 5 == 0) {
			player.attackEntityFrom(DamageSource.magic, 0.5F);
			world.playSoundAtEntity(player, "random.fizz", 1.0F, 1.0F);
			//player.playSound("random.fizz", 1.0F, 1.0F);
			
			// hint monster?
			if (world.rand.nextInt(4) == 0) {
				TFFeature.trollCave.trySpawnHintMonster(world, player);
			}
		}
	}

}
