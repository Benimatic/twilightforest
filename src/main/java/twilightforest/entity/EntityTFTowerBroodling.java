package twilightforest.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityTFTowerBroodling extends EntityTFSwarmSpider 
{

	public EntityTFTowerBroodling(World world) {
		this(world, true);
	}

	public EntityTFTowerBroodling(World world, boolean spawnMore) {
		super(world, spawnMore);
		experienceValue = 3; // XP value
		//texture = TwilightForestMod.MODEL_DIR + "towerbroodling.png";
	}
	
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(7.0D*1.5+twilightforest.TwilightForestMod.Scatter.nextInt(3)-twilightforest.TwilightForestMod.Scatter.nextInt(3)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699999988079071D*1.5D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D*1.5); // attack damage
    }

	/**
	 * Spawn another spider!
	 * 
	 * @return
	 */
	protected boolean spawnAnother() 
	{
		EntityTFSwarmSpider another = new EntityTFTowerBroodling(worldObj, false);

		double sx = posX + (rand.nextBoolean() ? 0.9 : -0.9);
		double sy = posY;
		double sz = posZ + (rand.nextBoolean() ? 0.9 : -0.9);
		another.setLocationAndAngles(sx, sy, sz, rand.nextFloat() * 360F, 0.0F);
		if(!another.getCanSpawnHere())
		{
			another.setDead();
			return false;
		}
		worldObj.spawnEntityInWorld(another);
		
		return true;
	}
}
