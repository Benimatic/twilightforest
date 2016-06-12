package twilightforest.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.entity.EntityTFKingSpider;
import twilightforest.entity.EntityTFKobold;
import twilightforest.entity.EntityTFMistWolf;
import twilightforest.entity.EntityTFSkeletonDruid;



public class TFBiomeDarkForest extends TFBiomeBase {
	
	private static final int MONSTER_SPAWN_RATE = 20;
	Random monsterRNG;
	ArrayList<SpawnListEntry> emptyList = new ArrayList<SpawnListEntry>();

	@SuppressWarnings("unchecked")
	public TFBiomeDarkForest(int i) {
		super(i);
		
		this.temperature = 0.7F;
		this.rainfall = 0.8F;

		getTFBiomeDecorator().canopyPerChunk = 5.5F;
		
		getTFBiomeDecorator().setTreesPerChunk(10);
		getTFBiomeDecorator().setGrassPerChunk(-99);
		getTFBiomeDecorator().setFlowersPerChunk(-99);
        getTFBiomeDecorator().setMushroomsPerChunk(2);
        getTFBiomeDecorator().setDeadBushPerChunk(10);

        
        this.rootHeight = 0.05F;
        this.heightVariation = 0.05F;
        
        this.monsterRNG = new Random();
        
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 5, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 5, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTFMistWolf.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTFSkeletonDruid.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTFKingSpider.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTFKobold.class, 10, 4, 8));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 1, 1, 1));
        
        this.theBiomeDecorator.generateLakes = false;
	}
	
	/**
	 * We have our own decorator since we have strange generation
	 */
    public BiomeDecorator createBiomeDecorator()
    {
        return new TFDarkForestBiomeDecorator();
    }
	
    /**
     * Occasional shrub, no big trees
     */
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        if(random.nextInt(5) == 0)
        {
        	return new WorldGenShrub(3, 0);
        }
        if(random.nextInt(8) == 0)
        {
            return this.birchGen;
        }
        else {
            return worldGeneratorTrees;
        }
    }
    
    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
    @Override
    public int getBiomeGrassColor(int x, int y, int z)
    {
        double var1 = (double)MathHelper.clamp_float(this.getFloatTemperature(x, y, z), 0.0F, 1.0F);
        double var3 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
        return ((ColorizerGrass.getGrassColor(var1, var3) & 0xFEFEFE) + 0x1E0E4E) / 2;
        
//        return 0x554114;
    }

    /**
     * Provides the basic foliage color based on the biome temperature and rainfall
     */
    @Override
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        double var1 = (double)MathHelper.clamp_float(this.getFloatTemperature(x, y, z), 0.0F, 1.0F);
        double var3 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
        return ((ColorizerFoliage.getFoliageColor(var1, var3) & 0xFEFEFE) + 0x1E0E4E) / 2;
    }
    
    
    /**
     * Returns the correspondent list of the EnumCreatureType informed.
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List getSpawnableList(EnumCreatureType par1EnumCreatureType)
    {
    	// if is is monster, then only give it the real list 1/MONSTER_SPAWN_RATE of the time
    	if (par1EnumCreatureType == EnumCreatureType.monster) {
			return monsterRNG.nextInt(MONSTER_SPAWN_RATE) == 0 ? this.spawnableMonsterList : emptyList;
    	}
    	else {
    		return par1EnumCreatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (par1EnumCreatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (par1EnumCreatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null));
    	}
    }

    /**
     * This just seems to act as a fire discouragement
     */
	@Override
	public boolean isHighHumidity() {
		return true;
	}
    
    
	/**
	 * If there is a required achievement to be here, return it, otherwise return null
	 */
	protected Achievement getRequiredAchievement() {
		return TFAchievementPage.twilightProgressHydra;
	}

	/**
	 * Do something bad to a player in the wrong biome.
	 */
	public void enforceProgession(EntityPlayer player, World world) {
		if (!world.isRemote && world.getWorldTime() % 60 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
			
			// hint monster?
			if (world.rand.nextInt(4) == 0) {
				TFFeature.tfStronghold.trySpawnHintMonster(world, player);
			}
		}
	}
}
