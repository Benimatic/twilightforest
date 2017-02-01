package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

	@Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

	@Override
    protected void entityInit() {}

    @Override
    public boolean canBeCollidedWith()
    {
    	return !this.isDead;
    }

    @Override
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

    	if (!this.world.isRemote)
    	{
    		//int y = MathHelper.floor(this.posY);

    		if (this.onGround)
    		{
    			this.motionX *= 0.699999988079071D;
    			this.motionZ *= 0.699999988079071D;
    			this.motionY *= -0.5D;

    			this.setDead();
    		}
//    		else if (this.fallTime > 100 && !this.world.isRemote && (y < 1 || y > 256) || this.fallTime > 600)
//    		{
//    			// something's wrong
//    			this.setDead();
//    		}
    	}
    	
    	// kill other nearby blocks if they are not as old as this one
    	if (!this.world.isRemote) {
    		List<Entity> nearby = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());

    		for (Entity entity : nearby) {
    			if (entity instanceof EntityTFFallingIce) {
    				EntityTFFallingIce otherIce = (EntityTFFallingIce)entity;
    				
    				if (otherIce.getFallTime() < this.fallTime) {
    					otherIce.setDead();
    				}
    			}
    		}
    		
    		destroyIceInAABB(this.getEntityBoundingBox().expand(0.5, 0, 0.5));
    	}
    	
    	makeTrail();
    }
    
	public void makeTrail() {
		for (int i = 0; i < 2; i++) {
			double dx = this.posX + 2F * (rand.nextFloat() - rand.nextFloat()); 
			double dy = this.posY - 3F + 3F * (rand.nextFloat() - rand.nextFloat()); 
			double dz = this.posZ + 2F * (rand.nextFloat() - rand.nextFloat()); 

			TwilightForestMod.proxy.spawnParticle(this.world, "snowwarning", dx, dy, dz, 0, -1, 0);
			
			//System.out.println("Trail! " + this.world);
		}
	}


	@Override
    public void fall(float dist, float multiplier)
    {
    	int distance = MathHelper.ceiling_float_int(dist - 1.0F);

    	if (distance > 0)
    	{
    		List<Entity> nearby = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(2, 0, 2));
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
			
			this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, dx, dy, dz, 0, 0, 0, Block.getStateId(Blocks.PACKED_ICE.getDefaultState()));
		}
		
		this.playSound(Blocks.ANVIL.getSoundType().getBreakSound(), 3F, 0.5F); // todo 1.9 this gon crash
		this.playSound(Blocks.PACKED_ICE.getSoundType().getBreakSound(), 3F, 0.5F);
    }
    
	/**
     * Destroys all blocks that aren't associated with 'The End' inside the given bounding box.
     */
    public void destroyIceInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);

    	int minX = MathHelper.floor(par1AxisAlignedBB.minX);
    	int minY = MathHelper.floor(par1AxisAlignedBB.minY);
    	int minZ = MathHelper.floor(par1AxisAlignedBB.minZ);
    	int maxX = MathHelper.floor(par1AxisAlignedBB.maxX);
    	int maxY = MathHelper.floor(par1AxisAlignedBB.maxY);
    	int maxZ = MathHelper.floor(par1AxisAlignedBB.maxZ);

    	for (int dx = minX; dx <= maxX; ++dx) {
    		for (int dy = minY; dy <= maxY; ++dy) {
    			for (int dz = minZ; dz <= maxZ; ++dz) {
					BlockPos pos = new BlockPos(dx, dy, dz);
    				Block block = this.world.getBlockState(pos).getBlock();

    				if (block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.STONE) {
						this.world.destroyBlock(pos, false);
    				}
    			}
    		}
    	}
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {}
	
    @SideOnly(Side.CLIENT)
	@Override
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
