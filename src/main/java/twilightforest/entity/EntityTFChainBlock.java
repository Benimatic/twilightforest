package twilightforest.entity;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import twilightforest.item.ItemTFChainBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
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

	@Override
    public void setThrowableHeading(double x, double y, double z, float speed, float accuracy)
    {
    	super.setThrowableHeading(x, y, z, speed, accuracy);
    	
    	// save velocity
    	this.velX = this.motionX;
    	this.velY = this.motionY;
    	this.velZ = this.motionZ;
    }

	@Override
    protected float getGravityVelocity()
    {
        return 0.05F;
    }


	@Override
	protected void onImpact(RayTraceResult mop) {
		// only hit living things
        if (mop.entityHit != null && mop.entityHit instanceof EntityLivingBase && mop.entityHit != this.getThrower()) {
            if (mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 10)) {
            	// age when we hit a monster so that we go back to the player faster
                this.ticksExisted += 60;
            }
        }
        
        if (mop.getBlockPos() != null && !this.worldObj.isAirBlock(mop.getBlockPos())) {

        	// clang!
        	if (!this.isReturning) {
        		this.worldObj.playSoundAtEntity(this, "random.anvil_land", 0.125f, this.rand.nextFloat());
        	}

        	if (!this.worldObj.isRemote && this.blocksSmashed < MAX_SMASH) {
        		if (this.worldObj.getBlockState(mop.getBlockPos()).getBlockHardness(worldObj, mop.getBlockPos()) > 0.3F) {
        			// riccochet
        			double bounce = 0.6;
        			this.velX *= bounce;
        			this.velY *= bounce;
        			this.velZ *= bounce;
        			
        			
        			switch (mop.sideHit) {
        			case DOWN:
        				if (this.velY > 0) {
        					this.velY *= -bounce;
        				}
        				break;
        			case UP:
        				if (this.velY < 0) {
        					this.velY *= -bounce;
        				}
        				break;
        			case NORTH:
        				if (this.velZ > 0) {
        					this.velZ *= -bounce;
        				}
        				break;
        			case SOUTH:
        				if (this.velZ < 0) {
        					this.velZ *= -bounce;
        				}
        				break;
        			case WEST:
        				if (this.velX > 0) {
        					this.velX *= -bounce;
        				}
        				break;
        			case EAST:
        				if (this.velX < 0) {
        					this.velX *= -bounce;
        				}
        				break;
        			}
        		}
        		
            	// demolish some blocks
        		this.affectBlocksInAABB(this.getEntityBoundingBox(), this.getThrower());
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
					BlockPos pos = new BlockPos(dx, dy, dz);
					IBlockState state = worldObj.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block != Blocks.AIR && block.getExplosionResistance(this) < 7F && state.getBlockHardness(worldObj, pos) >= 0) {

                    	if (entity != null && entity instanceof EntityPlayer) {
                    		EntityPlayer player = (EntityPlayer)entity;

                    		if (block.canHarvestBlock(worldObj, pos, player)){
                    			block.harvestBlock(this.worldObj, player, pos, state, worldObj.getTileEntity(pos), player.getHeldItemMainhand());
                    		}
                    	}

                    	worldObj.destroyBlock(pos, false);
            			
            			this.blocksSmashed++;
            			
            			hitBlock = true;
                    }
                }
            }
        }

        return hitBlock;
    }

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
        
            Vec3d back = new Vec3d(returnTo.posX - this.posX, returnTo.posY + (double)returnTo.getEyeHeight() - 1.200000023841858D - this.posY, returnTo.posZ - this.posZ).normalize();
            
            float age = Math.min(this.ticksExisted * 0.03F, 1.0F);
            
            // separate the return velocity from the normal bouncy velocity
            this.motionX = this.velX * (1.0 - age) + (back.xCoord * 2F * age);
            this.motionY = this.velY * (1.0 - age) + (back.yCoord * 2F * age) - this.getGravityVelocity();
            this.motionZ = this.velZ * (1.0 - age) + (back.zCoord * 2F * age);

        }
        
        
        // on the client, if we are not attached, assume we have just spawned, and attach to the player
        if (this.worldObj.isRemote && !this.isAttached) {
            List nearbyEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(-this.motionX, -this.motionY, -this.motionZ).expand(2.0D, 2.0D, 2.0D));
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
        	Vec3d handVec = this.attachedTo.getLookVec().rotateYaw(-0.4F);
        	
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
	public World getWorld() {
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
