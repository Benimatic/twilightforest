package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTFNagaSegment extends Entity {
	
	EntityTFNaga naga;
	int segment;
	
	String texture;
	
	private int deathCounter;

	public EntityTFNagaSegment(World par1World) {
		super(par1World);
		//this.texture = TwilightForestMod.MODEL_DIR + "nagasegment.png";
        setSize(1.8F, 1.8F);
		this.stepHeight = 2;

	}

	public EntityTFNagaSegment(EntityTFNaga myNaga, int segNum) {
		this(myNaga.getWorld());
		this.naga = myNaga;
		this.segment = segNum;
	}

    /**
     * When we're attacked transfer damage to the head.  Segments only transfer 2/3 normal damage to the head.
     * Segments do not transfer damage from fire, explosions or lava.
     */
    @Override
	public boolean attackEntityFrom(DamageSource damagesource, float damage) {
    	// do not transfer (or take) fire, explosion, or lava damage.
    	if (damagesource.isExplosion() || damagesource.isFireDamage()) {
    		//hurtTime = 0;
//    		System.out.println("Prevented damage from fire/explosion/lava.  DamageSource == " + damagesource.damageType);
    		return false;
    	}
    	
    	// do not transfer damage from segments that are disconnected and about to explode
    	if (naga != null) {
	        //hurtTime = maxHurtTime = 10;
    		// System.out.println("transferring damage, world is " + this.worldObj);
    		
			return naga.attackEntityFrom(damagesource, Math.round(damage * 2.0F / 3.0F));
    	}
    	else
    	{
    		return false;
    	}
	}


	/**
	 * Skip most of the living update things
	 */
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	// remove if invalid
    	if (this.naga == null || this.naga.isDead)
    	{
    		this.setDead();
    	}

    	this.ticksExisted++;
    	
    	lastTickPosX = posX;
    	lastTickPosY = posY;
    	lastTickPosZ = posZ;

    	
//    	System.out.println("Updating " + this + " with angles " + rotationYaw + ", " + rotationPitch);

    	for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
    	for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
    	for (; rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
    	for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }

       	
    	//this.pushOutOfBlocks(posX, posY, posZ);

    	if (!this.onGround)
    	{
    		this.motionY -= 0.08D;
    	}
    	else
    	{
            this.motionX *= 0.800000011920929D;
//            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
    	}
    	
        this.moveEntity(motionX, motionY, motionZ);
        
        collideWithOthers();
        
//        if (this.segment == 6)
//        {
//        	System.out.println("Updating segment 6");
//        	
////        	int dx = MathHelper.floor_double(this.posX);
////        	int dy = MathHelper.floor_double(this.posY);
////        	int dz = MathHelper.floor_double(this.posZ);
////        	
////        	if (worldObj.isAirBlock(dx, dy, dz))
////        	{
////        		worldObj.setBlock(dx, dy, dz, Blocks.TORCH);
////        	}
//        	
//        }
        
		if (deathCounter > 0) {
			deathCounter--;
			
			if (deathCounter == 0) {
				
                for(int k = 0; k < 20; k++)
                {
                    double d = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    String explosionType = rand.nextBoolean() ?  "largeexplode" : "explode";
                    
                    worldObj.spawnParticle(explosionType, (posX + rand.nextFloat() * width * 2.0F) - width, posY + rand.nextFloat() * height, (posZ + rand.nextFloat() * width * 2.0F) - width, d, d1, d2);
                }
                
                // really explode?
//                worldObj.newExplosion(null, posX, posY, posZ, 3.0F, true);

                setDead();
                worldObj.removeEntity(this);
			}
		}

    }
    
	protected void collideWithOthers()
    {
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        for (Entity entity : list)
        {
        	if (entity.canBePushed())
        	{
        		this.collideWithEntity(entity);
        	}
        }
    }
    
    private void collideWithEntity(Entity entity) {
    	entity.applyEntityCollision(this);
    	
		// attack anything that's not us
		if ((entity instanceof EntityLivingBase) && !(entity instanceof EntityTFNaga) && !(entity instanceof EntityTFNagaSegment))
		{
			naga.attackTime = 10;
    		int attackStrength = 2;
    		
    		// get rid of nearby deer & look impressive
    		if (entity instanceof EntityAnimal)
    		{
    			attackStrength *= 3;
    		}
    		
			entity.attackEntityFrom(DamageSource.causeMobDamage(naga), attackStrength);
		}

		
	}

	@Override
    public void setRotation(float par1, float par2)
    {
        this.rotationYaw = MathHelper.wrapDegrees(par1 % 360.0F);
        this.rotationPitch = par2 % 360.0F;
    }

	@Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }
    
    @Override
    protected boolean canDespawn()
    {
        return false;
    }

	@Override
    public boolean isEntityEqual(Entity entity)
    {
        return this == entity || this.naga == entity;
    }

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) { }

	@Override
    protected void playStepSound(BlockPos pos, Block par4)
    {
        ;
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
        return this.texture;
    }

	/**
	 * Starts the self destruction process
	 */
	public void selfDestruct() {
		this.deathCounter = 30;
	}
    
}
