package twilightforest.entity;

import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import twilightforest.TFGenericPacketHandler;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.item.ItemTFCubeOfAnnihilation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.network.PacketAnnihilateBlock;


public class EntityTFCubeOfAnnihilation extends EntityThrowable  {
	
	boolean hasHitObstacle = false;

	public EntityTFCubeOfAnnihilation(World par1World) {
		super(par1World);
		
		this.setSize(1.1F, 1F);
		this.isImmuneToFire = true;

	}

	
	public EntityTFCubeOfAnnihilation(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		this.setSize(1F, 1F);
		this.isImmuneToFire = true;
	}


	public EntityTFCubeOfAnnihilation(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		
		this.setSize(1F, 1F);
		this.isImmuneToFire = true;
	}

	@Override
    protected float getGravityVelocity()
    {
        return 0F;
    }

	@Override
	protected void onImpact(RayTraceResult mop) {
		// only hit living things
        if (mop.entityHit != null && mop.entityHit instanceof EntityLivingBase)
        {
            if (mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 10))
            {
                this.ticksExisted += 60;
            }
        }
        
        if (mop.getBlockPos() != null && !this.worldObj.isAirBlock(mop.getBlockPos())) {

        	// demolish some blocks
        	if (!this.worldObj.isRemote) {
        		this.affectBlocksInAABB(this.getEntityBoundingBox().expand(0.2F, 0.2F, 0.2F), this.getThrower());
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

                	if (block != Blocks.AIR) {
                		if (canAnnihilate(dx, dy, dz, block, currentMeta)) {
                			this.worldObj.setBlockToAir(dx, dy, dz);

                    		this.worldObj.playSoundAtEntity(this, "random.fizz", 0.125f, this.rand.nextFloat() * 0.25F + 0.75F);
                    		
                    		this.sendAnnihilateBlockPacket(worldObj, dx, dy, dz);

                		} else {
                			// return if we hit an obstacle
                			this.hasHitObstacle = true;
                		}
            			hitBlock = true;
                	}
                }
            }
        }

        return hitBlock;
    }


	private boolean canAnnihilate(int dx, int dy, int dz, Block block, int meta) {
		// whitelist many castle blocks
		if (block == TFBlocks.deadrock || block == TFBlocks.castleBlock || (block == TFBlocks.castleMagic && meta != 3) || block == TFBlocks.forceField || block == TFBlocks.thorns) {
			return true;
		}
		
		return block.getExplosionResistance(this) < 8F && block.getBlockHardness(worldObj, dx, dy, dz) >= 0;
	}

    

	private void sendAnnihilateBlockPacket(World world, BlockPos pos) {
		// send packet
		IMessage message = new PacketAnnihilateBlock(pos);

		NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 64);
		
		TwilightForestMod.genericChannel.sendToAllAround(message, targetPoint);
	}
	
    @Override
    public void onUpdate() {
    	super.onUpdate();

    	// all server side
    	if (!this.worldObj.isRemote) {

    		if (this.getThrower() == null) {
    			this.setDead();
    			return;
    		}

    		if (this.isReturning()) {
    			// if we are returning, and are near enough to the player, then we are done
    			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));

    			if (list.contains(this.getThrower()) && !this.worldObj.isRemote) {
    				//System.out.println("we have returned");
            		if (this.getThrower() instanceof EntityPlayer) {
            			ItemTFCubeOfAnnihilation.setCubeAsReturned((EntityPlayer)this.getThrower());
            		}
    				this.setDead();
    			}
    		}



    		// always head towards either the point or towards the player
			Vec3d destPoint = new Vec3d(this.getThrower().posX, this.getThrower().posY + this.getThrower().getEyeHeight(), this.getThrower().posZ);

    		if (!this.isReturning()) {
    			Vec3d look = this.getThrower().getLookVec();
    			
    			
    			
    			float dist = 16F;

				look = look.scale(dist);
				destPoint = destPoint.add(look);
    		}
    		
    		//System.out.println("Dest point = " + destPoint);
    		
    		
    		// set motions
    		Vec3d velocity = new Vec3d(this.posX - destPoint.xCoord, (this.posY + this.height / 2F) - destPoint.yCoord, this.posZ - destPoint.zCoord);
    		
    		this.motionX -= velocity.xCoord;
    		this.motionY -= velocity.yCoord;
    		this.motionZ -= velocity.zCoord;
    		
    		// normalize speed
    		float currentSpeed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    		
    		float maxSpeed = 0.5F;
    		
    		
    		if (currentSpeed > maxSpeed) {
	    		this.motionX /= currentSpeed / maxSpeed;
	    		this.motionY /= currentSpeed / maxSpeed;
	    		this.motionZ /= currentSpeed / maxSpeed;
    		} else {
    			float slow = 0.5F;
	    		this.motionX *= slow;
	    		this.motionY *= slow;
	    		this.motionZ *= slow;    		
	    	}
    		
    		
        	// demolish some blocks
        	this.affectBlocksInAABB(this.getEntityBoundingBox().expand(0.2F, 0.2F, 0.2F), this.getThrower());

    	}


        

    }
    
    public boolean isReturning() {
    	if (this.hasHitObstacle || this.getThrower() == null || !(this.getThrower() instanceof EntityPlayer)) {
    		return true;
    	} else {
    		EntityPlayer player = (EntityPlayer) this.getThrower();
    		
    		return !player.isHandActive();
    	}
    }

	

    /**
     * Velocity
     */
    protected float func_70182_d()
    {
        return 1.5F;
    }

    
}
