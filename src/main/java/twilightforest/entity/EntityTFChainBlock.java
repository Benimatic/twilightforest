package twilightforest.entity;

import java.util.List;

import twilightforest.item.ItemTFChainBlock;
import twilightforest.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class EntityTFChainBlock extends EntityThrowable implements IEntityMultiPart  {
	
	private static final int MAX_SMASH = 12;
	private static final int MAX_CHAIN = 16;
	private boolean isReturning = false;
	private int blocksSmashed = 0;

	// where are we headed?
	private double velX;
	private double velY;
	private double velZ;


	// for the client to show the chain
	private boolean isAttached;
	private EntityLivingBase attachedTo;
	
	public EntityTFGoblinChain chain1;
	public EntityTFGoblinChain chain2;
	public EntityTFGoblinChain chain3;
	public EntityTFGoblinChain chain4;
	public EntityTFGoblinChain chain5;
	public Entity[] partsArray;

	public EntityTFChainBlock(World par1World) {
		super(par1World);
		
		this.setSize(0.6F, 0.6F);


        this.partsArray = (new Entity[]
        		{
        		chain1 = new EntityTFGoblinChain(this), chain2 = new EntityTFGoblinChain(this), chain3 = new EntityTFGoblinChain(this), chain4 = new EntityTFGoblinChain(this), chain5 = new EntityTFGoblinChain(this)
        		});
	}

	
	public EntityTFChainBlock(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}


	public EntityTFChainBlock(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		
		this.setSize(0.6F, 0.6F);
		
		this.isReturning = false;
	}
	
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double x, double y, double z, float speed, float accuracy)
    {
    	super.setThrowableHeading(x, y, z, speed, accuracy);
    	
    	// save velocity
    	this.velX = this.motionX;
    	this.velY = this.motionY;
    	this.velZ = this.motionZ;
    }

	/**
	 * How much this entity falls each tick
	 */
	@Override
    protected float getGravityVelocity()
    {
        return 0.05F;
    }



	@Override
	protected void onImpact(MovingObjectPosition mop) {
		// only hit living things
        if (mop.entityHit != null && mop.entityHit instanceof EntityLivingBase && mop.entityHit != this.getThrower()) {
            if (mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 10*1.5f)) {
            	// age when we hit a monster so that we go back to the player faster
                this.ticksExisted += 60;
            }
        }
        
        if (!this.worldObj.isAirBlock(mop.blockX, mop.blockY, mop.blockZ)) {

        	// clang!
        	if (!this.isReturning) {
        		this.worldObj.playSoundAtEntity(this, "random.anvil_land", 0.125f, this.rand.nextFloat());
        	}

        	if (!this.worldObj.isRemote && this.blocksSmashed < MAX_SMASH) {
        		if (this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getBlockHardness(worldObj, mop.blockX, mop.blockY, mop.blockZ) > 0.3F) {
        			// riccochet
        			double bounce = 0.6;
        			this.velX *= bounce;
        			this.velY *= bounce;
        			this.velZ *= bounce;
        			
        			
        			switch (mop.sideHit) {
        			case 0:
        				if (this.velY > 0) {
        					this.velY *= -bounce;
        				}
        				break;
        			case 1:
        				if (this.velY < 0) {
        					this.velY *= -bounce;
        				}
        				break;
        			case 2:
        				if (this.velZ > 0) {
        					this.velZ *= -bounce;
        				}
        				break;
        			case 3:
        				if (this.velZ < 0) {
        					this.velZ *= -bounce;
        				}
        				break;
        			case 4:
        				if (this.velX > 0) {
        					this.velX *= -bounce;
        				}
        				break;
        			case 5:
        				if (this.velX < 0) {
        					this.velX *= -bounce;
        				}
        				break;
        			}
        		}
        		
            	// demolish some blocks
        		this.affectBlocksInAABB(this.boundingBox, this.getThrower());
        	}

        	// head back to owner
        	if (!this.worldObj.isRemote) {
        		this.isReturning = true;
        	}
        	
        	// if we have smashed enough, add to ticks so that we go back faster
        	if (this.blocksSmashed > MAX_SMASH && this.ticksExisted < 60) {
                this.ticksExisted += 60;
        	}
        }

	}
	
	
	/**
     * Do our ball and chain effect on blocks we hit.  Harvest/destroy/whatevs
	 * @param entity 
     */
    private boolean affectBlocksInAABB(AxisAlignedBB par1AxisAlignedBB, EntityLivingBase entity) {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean hitBlock = false;
        for (int dx = minX; dx <= maxX; ++dx) {
            for (int dy = minY; dy <= maxY; ++dy) {
                for (int dz = minZ; dz <= maxZ; ++dz) {
                    Block block = this.worldObj.getBlock(dx, dy, dz);
                    int currentMeta = this.worldObj.getBlockMetadata(dx, dy, dz);
                    
                    if (block != Blocks.air && block.getExplosionResistance(this) < 7F && block.getBlockHardness(worldObj, dx, dy, dz) >= 0) {

                    	if (entity != null && entity instanceof EntityPlayer) {
                    		EntityPlayer player = (EntityPlayer)entity;

                    		if (block.canHarvestBlock(player, currentMeta)){
                    			block.harvestBlock(this.worldObj, player, dx, dy, dz, currentMeta);
                    		}
                    	}

                    	this.worldObj.setBlockToAir(dx, dy, dz);

                    	// here, this effect will have to do
            			worldObj.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(block) + (currentMeta << 12));
            			
            			this.blocksSmashed++;
            			
            			hitBlock = true;
                    }
                }
            }
        }

        return hitBlock;
    }

	
	/**
	 * Skip most of the living update things
	 */
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	// chains are probably always null on the server
    	if (this.chain1 != null) {
			chain1.onUpdate();
			chain2.onUpdate();
			chain3.onUpdate();
			chain4.onUpdate();
			chain5.onUpdate();
    	}
    	
    	if (this.getThrower() == null && !this.worldObj.isRemote) {
    		this.setDead();
    	}
    	
    	if (this.getThrower() != null) {
    		float distToPlayer = this.getDistanceToEntity(this.getThrower());
        	// return if far enough away
    		if (!this.isReturning && distToPlayer > MAX_CHAIN) {
    			this.isReturning = true;
    		}

    		// despawn if close enough
    		if (this.isReturning && distToPlayer < 1F) {
    			//System.out.println("we have returned after smashing " + this.blocksSmashed + " blocks");
        		if (this.getThrower() instanceof EntityPlayer) {
        			ItemTFChainBlock.setChainAsReturned((EntityPlayer)this.getThrower());
        		}
    			this.setDead();
    		}
    	}
    	
    	// if we are returning, set course for the player
        if (this.isReturning && !this.worldObj.isRemote && this.getThrower() != null) {
        	
        	EntityLivingBase returnTo = this.getThrower();
        
            Vec3 back = Vec3.createVectorHelper(returnTo.posX - this.posX, returnTo.posY + (double)returnTo.getEyeHeight() - 1.200000023841858D - this.posY, returnTo.posZ - this.posZ).normalize();
            
            float age = Math.min(this.ticksExisted * 0.03F, 1.0F);
            
            // separate the return velocity from the normal bouncy velocity
            this.motionX = this.velX * (1.0 - age) + (back.xCoord * 2F * age);
            this.motionY = this.velY * (1.0 - age) + (back.yCoord * 2F * age) - this.getGravityVelocity();
            this.motionZ = this.velZ * (1.0 - age) + (back.zCoord * 2F * age);

        }
        
        
        // on the client, if we are not attached, assume we have just spawned, and attach to the player
        if (this.worldObj.isRemote && !this.isAttached) {
            List nearbyEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(-this.motionX, -this.motionY, -this.motionZ).expand(2.0D, 2.0D, 2.0D));
            for (int i = 0; i < nearbyEntities.size(); ++i) {
                Entity nearby = (Entity)nearbyEntities.get(i);
                
                // attach?  should we check for closest player?
                if (nearby instanceof EntityPlayer) {
                	this.attachedTo = (EntityPlayer) nearby;
                	
                	//System.out.println("Attached to player nearby");
                }
            }
            
            this.isAttached = true;
        }
        
        // set chain positions, client only
        if (this.attachedTo != null) {
    		// interpolate chain position
        	Vec3 handVec = this.attachedTo.getLookVec();
        	
        	handVec.rotateAroundY(-0.4F);
        	
    		double sx = this.attachedTo.posX + handVec.xCoord;
    		double sy = this.attachedTo.posY + handVec.yCoord - 0.6F;
    		double sz = this.attachedTo.posZ + handVec.zCoord;
    		
    		double ox = sx - this.posX;
    		double oy = sy - this.posY - 0.25F;
    		double oz = sz - this.posZ;
    		
    		this.chain1.setPosition(sx - ox * 0.05, sy - oy * 0.05, sz - oz * 0.05);
    		this.chain2.setPosition(sx - ox * 0.25, sy - oy * 0.25, sz - oz * 0.25);
    		this.chain3.setPosition(sx - ox * 0.45, sy - oy * 0.45, sz - oz * 0.45);
    		this.chain4.setPosition(sx - ox * 0.65, sy - oy * 0.65, sz - oz * 0.65);
    		this.chain5.setPosition(sx - ox * 0.85, sy - oy * 0.85, sz - oz * 0.85);
        }
    }

	

    /**
     * Velocity
     */
    protected float func_70182_d()
    {
        return 1.5F;
    }


	@Override
	public World func_82194_d() {
		return this.worldObj;
	}


	@Override
	public boolean attackEntityFromPart(EntityDragonPart p_70965_1_, DamageSource p_70965_2_, float p_70965_3_) {
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
    
}
