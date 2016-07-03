package twilightforest.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;


public class EntityTFBlockGoblin extends EntityMob implements IEntityMultiPart {
	

 
	private static final float CHAIN_SPEED = 16F;
	private static final int DATA_CHAINLENGTH = 17;
	private static final int DATA_CHAINPOS = 18;
	
	int recoilCounter;
	float chainAngle;
	
	public EntityTFSpikeBlock block;
	public EntityTFGoblinChain chain1;
	public EntityTFGoblinChain chain2;
	public EntityTFGoblinChain chain3;
	
	public Entity[] partsArray;


	public EntityTFBlockGoblin(World world)
    {
        super(world);
        //texture = TwilightForestMod.MODEL_DIR + "blockgoblin.png";
        //moveSpeed = 0.28F;
        setSize(0.9F, 1.4F);
        
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 2.0F, 0.8F, 1.5F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0F, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        
        this.recoilCounter = 0;

        this.partsArray = (new Entity[]
        		{
        		block = new EntityTFSpikeBlock(this), chain1 = new EntityTFGoblinChain(this), chain2 = new EntityTFGoblinChain(this), chain3 = new EntityTFGoblinChain(this)
        		});

    }


	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(DATA_CHAINLENGTH, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(DATA_CHAINPOS, Byte.valueOf((byte) 0));
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D); // attack damage
    }
    

    @Override
	protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.redcap.redcap";
    }

    @Override
	protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.redcap.hurt";
    }

    @Override
	protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.redcap.die";
    }

    @Override
    protected Item getDropItem()
    {
        return TFItems.armorShard;
    }
    
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}
    
    /**
     * How high is the chain
     */
	public double getChainYOffset()
    {
        return 1.5 - this.getChainLength() / 4.0;
    }
    
    /**
     * Get the block & chain position
     */
    public Vec3d getChainPosition()
    {
    	return this.getChainPosition(getChainAngle(), getChainLength());
    }
	
    /**
     * Get the block & chain position
     */
    public Vec3d getChainPosition(float angle, float distance)
    {
		double var1 = Math.cos((angle) * Math.PI / 180.0D) * distance;
		double var3 = Math.sin((angle) * Math.PI / 180.0D) * distance;

		return new Vec3d(this.posX + var1, this.posY + this.getChainYOffset(), this.posZ + var3);
    }
    
    public boolean isSwingingChain()
    {
    	return this.isSwingInProgress || (this.getAttackTarget() != null && this.recoilCounter == 0);
    }

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		
        this.swingItem();

		
		return false;

		
		//return super.attackEntityAsMob(par1Entity);
	}
	
    @Override
	public void onUpdate() {
		
		super.onUpdate();
		block.onUpdate();
		chain1.onUpdate();
		chain2.onUpdate();
		chain3.onUpdate();
		
		if (recoilCounter > 0)
		{
			recoilCounter--;
		}

		
    	chainAngle += CHAIN_SPEED;
    	chainAngle %= 360;

        if (!this.worldObj.isRemote)
        {
            this.dataWatcher.updateObject(DATA_CHAINLENGTH, Byte.valueOf((byte) Math.floor(getChainLength() * 127F)));
            this.dataWatcher.updateObject(DATA_CHAINPOS, Byte.valueOf((byte) Math.floor(getChainAngle() / 360F * 255F)));
        }
        else
        {
        	// synch chain pos if it's wrong
        	if (Math.abs(this.chainAngle - this.getChainAngle()) > CHAIN_SPEED * 2)
        	{
        		//FMLLog.info("Fixing chain pos on client");
        		this.chainAngle = getChainAngle();
        	}
        }
        
        // set block position
		Vec3d blockPos = this.getChainPosition();
		this.block.setPosition(blockPos.xCoord, blockPos.yCoord, blockPos.zCoord);
		this.block.rotationYaw = getChainAngle();
		
		// interpolate chain position
		double sx = this.posX;
		double sy = this.posY + this.height - 0.1;
		double sz = this.posZ;
		
		double ox = sx - blockPos.xCoord;
		double oy = sy - blockPos.yCoord - (block.height / 3D);
		double oz = sz - blockPos.zCoord;
		
		this.chain1.setPosition(sx - ox * 0.4, sy - oy * 0.4, sz - oz * 0.4);
		this.chain2.setPosition(sx - ox * 0.5, sy - oy * 0.5, sz - oz * 0.5);
		this.chain3.setPosition(sx - ox * 0.6, sy - oy * 0.6, sz - oz * 0.6);
		
		// collide things with the block
        if (!worldObj.isRemote && this.isSwingingChain())
        {
        	this.applyBlockCollisions(this.block);
        }
		
    }

    /**
     * Check if the block is colliding with any nearby entities
     */
	protected void applyBlockCollisions(Entity collider)
    {
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(collider, collider.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.canBePushed())
                {
                    applyBlockCollision(collider, entity);
                }
            }
        }
    }

    /**
     * Do the effect where the block hits something
     */
    protected void applyBlockCollision(Entity collider, Entity collided)
    {
		if (collided != this)
		{
	    	collided.applyEntityCollision(collider);
			if (collided instanceof EntityLivingBase)
			{
				//FMLLog.info("Spike ball collided with entity %s", collided);
				
				// do hit and throw
		        boolean attackSuccess = super.attackEntityAsMob(collided);

		        if (attackSuccess)
		        {
		        	collided.motionY += 0.4000000059604645D;
			        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		            
		            //System.out.println("Spike ball attack success");
		            
		            this.recoilCounter = 40;
		        }

			}
		}
    }
    
    /**
     * Angle between 0 and 360 to place the chain at
     */
    public float getChainAngle()
    {
        if (!this.worldObj.isRemote)
        {
    		return this.chainAngle;
        }
        else
        {
        	return (this.dataWatcher.getWatchableObjectByte(DATA_CHAINPOS) & 0xFF) / 255F * 360F;
        }
    }
    
	/**
	 * Between 0.0F and 2.0F, how long is the chain right now?
	 */
	public float getChainLength()
	{
        if (!this.worldObj.isRemote)
        {
			if (isSwingingChain())
			{
				return 0.9F;
			}
			else
			{
				return 0.3F;
			}
        }
        else
        {
        	return (this.dataWatcher.getWatchableObjectByte(DATA_CHAINLENGTH) & 0xFF) / 127F;
        }
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}

	@Override
	public boolean attackEntityFromPart(EntityDragonPart entitydragonpart, DamageSource damagesource, float i) {
		return false;
	}
	
    
    /**
     * We need to do this for the bounding boxes on the parts to become active
     */
    @Override
    public Entity[] getParts()
    {
        return partsArray;
    }
    
    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
    public int getTotalArmorValue()
    {
        int i = super.getTotalArmorValue() + 11;

        if (i > 20)
        {
            i = 20; // todo 1.9 attributes
        }

        return i;
    }

    
    
    

}
