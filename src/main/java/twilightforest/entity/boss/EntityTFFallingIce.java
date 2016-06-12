package twilightforest.entity.boss;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTFFallingIce extends Entity {

	private static final int HANG_TIME = 100;
	private int fallTime;
	private float hurtAmount;
	private int hurtMax;
	
	public EntityTFFallingIce(World par1World) {
		super(par1World);
        this.setSize(2.98F, 2.98F);
        
        this.hurtAmount = 10;
        this.hurtMax = 30;
	}

	public EntityTFFallingIce(World par1World, int x, int y, int z) {
		this(par1World);

        this.preventEntitySpawning = true;

        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
	}
	
	  /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit() {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
    	return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @SuppressWarnings("unchecked")
	public void onUpdate()
    {
    	this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
    	
    	++this.fallTime;
    	
    	if (this.fallTime > HANG_TIME) {
    		this.motionY -= 0.03999999910593033D;
    	}
    	
    	this.moveEntity(this.motionX, this.motionY, this.motionZ);
    	this.motionX *= 0.9800000190734863D;
    	this.motionY *= 0.9800000190734863D;
    	this.motionZ *= 0.9800000190734863D;

    	if (!this.worldObj.isRemote)
    	{
    		//int y = MathHelper.floor_double(this.posY);

    		if (this.onGround)
    		{
    			this.motionX *= 0.699999988079071D;
    			this.motionZ *= 0.699999988079071D;
    			this.motionY *= -0.5D;

    			this.setDead();
    		}
//    		else if (this.fallTime > 100 && !this.worldObj.isRemote && (y < 1 || y > 256) || this.fallTime > 600)
//    		{
//    			// something's wrong
//    			this.setDead();
//    		}
    	}
    	
    	// kill other nearby blocks if they are not as old as this one
    	if (!this.worldObj.isRemote) {
    		ArrayList<Entity> nearby = new ArrayList<Entity>(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));

    		for (Entity entity : nearby) {
    			if (entity instanceof EntityTFFallingIce) {
    				EntityTFFallingIce otherIce = (EntityTFFallingIce)entity;
    				
    				if (otherIce.getFallTime() < this.fallTime) {
    					otherIce.setDead();
    				}
    			}
    		}
    		
    		destroyIceInAABB(this.boundingBox.expand(0.5, 0, 0.5));
    	}
    	
    	makeTrail();
    }
    
	public void makeTrail() {
		for (int i = 0; i < 2; i++) {
			double dx = this.posX + 2F * (rand.nextFloat() - rand.nextFloat()); 
			double dy = this.posY - 3F + 3F * (rand.nextFloat() - rand.nextFloat()); 
			double dz = this.posZ + 2F * (rand.nextFloat() - rand.nextFloat()); 

			TwilightForestMod.proxy.spawnParticle(this.worldObj, "snowwarning", dx, dy, dz, 0, -1, 0);
			
			//System.out.println("Trail! " + this.worldObj);
		}
	}

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @SuppressWarnings({ "unchecked" })
    protected void fall(float par1)
    {
    	int distance = MathHelper.ceiling_float_int(par1 - 1.0F);

    	if (distance > 0)
    	{
    		ArrayList<Entity> nearby = new ArrayList<Entity>(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2, 0, 2)));
    		DamageSource damagesource = DamageSource.fallingBlock;
    		for (Entity entity : nearby) {
    			if (!(entity instanceof EntityTFYetiAlpha)) {
    				entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor_float((float)distance * this.hurtAmount), this.hurtMax));
    			}
    		}
    	}
    	
		for (int i = 0; i < 200; i++) {
			double dx = this.posX + 3F * (rand.nextFloat() - rand.nextFloat()); 
			double dy = this.posY + 2 + 3F * (rand.nextFloat() - rand.nextFloat()); 
			double dz = this.posZ + 3F * (rand.nextFloat() - rand.nextFloat());
			
			this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.PACKED_ICE) + "_0", dx, dy, dz, 0, 0, 0);
		}
		
		this.playSound(Blocks.ANVIL.stepSound.getBreakSound(), 3F, 0.5F);
		this.playSound(Blocks.PACKED_ICE.stepSound.getBreakSound(), 3F, 0.5F);
    }
    
	/**
     * Destroys all blocks that aren't associated with 'The End' inside the given bounding box.
     */
    public void destroyIceInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);

    	int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
    	int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
    	int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
    	int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
    	int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
    	int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);

    	for (int dx = minX; dx <= maxX; ++dx) {
    		for (int dy = minY; dy <= maxY; ++dy) {
    			for (int dz = minZ; dz <= maxZ; ++dz) {
    				Block block = this.worldObj.getBlock(dx, dy, dz);

    				if (block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.STONE) {
    					this.worldObj.setBlock(dx, dy, dz, Blocks.AIR, 0, 3);
    				}
    			}
    		}
    	}
    }


	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * Return whether this entity should be rendered as on fire.
     */
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }
    
    public Block getBlock() {
    	return Blocks.PACKED_ICE;
    }
    
    public int getFallTime() {
    	return this.fallTime;
    }
}
