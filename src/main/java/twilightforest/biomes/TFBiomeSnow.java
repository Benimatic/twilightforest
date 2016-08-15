/**
 * 
 */
package twilightforest.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.entity.EntityTFWinterWolf;
import twilightforest.entity.EntityTFYeti;
import twilightforest.world.TFGenLargeWinter;


/**
 * @author Ben
 *
 */
public class TFBiomeSnow extends TFBiomeBase {

	
	private static final int MONSTER_SPAWN_RATE = 10;
	Random monsterRNG = new org.bogdang.modifications.random.XSTR(53439L);
	ArrayList<SpawnListEntry> emptyList = new ArrayList<SpawnListEntry>();
	
	/**
	 * @param i
	 */
	@SuppressWarnings("unchecked")
	public TFBiomeSnow(int i) {
		super(i);
		
		getTFBiomeDecorator().setTreesPerChunk(7);
		getTFBiomeDecorator().setGrassPerChunk(1);
        
        this.temperature = 0.125F;
        this.rainfall = 0.9F;
        
        getTFBiomeDecorator().canopyPerChunk = -999;
        getTFBiomeDecorator().generateLakes = false;
        
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTFYeti.class, 20, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTFWinterWolf.class, 5, 1, 4));

	}

    public WorldGenAbstractTree func_150567_a(Random random)
    {
    	if (random.nextInt(3) == 0) {
    		return new WorldGenTaiga1();
    	} 
    	else if (random.nextInt(8) == 0) {
    		return new TFGenLargeWinter();
    	} 
    	else {
    		return new WorldGenTaiga2(true);
    	}
    }

    /**
     * Let it snow!
     */
    @Override
    public boolean getEnableSnow()
    {
        return true;
    }

    /**
     * Required for actual snow?
     */
    @Override
    public boolean canSpawnLightningBolt()
    {
    	return false;
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
	 * If there is a required achievement to be here, return it, otherwise return null
	 */
	protected Achievement getRequiredAchievement() {
		return TFAchievementPage.twilightProgressUrghast;
	}

	/**
	 * Do something bad to a player in the wrong biome.
	 */
	public void enforceProgession(EntityPlayer player, World world) {
		if (!world.isRemote && world.getWorldTime() % 60 == 0) {
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));
			
			// hint monster?
			if (world.rand.nextInt(4) == 0) {
				TFFeature.yetiCave.trySpawnHintMonster(world, player);
			}
		}
	}
	
	
}
