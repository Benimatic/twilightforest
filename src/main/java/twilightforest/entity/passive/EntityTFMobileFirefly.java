package twilightforest.entity.passive;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTFMobileFirefly extends EntityAmbientCreature
{
    /**
     * randomly selected BlockPos in a 7x6x7 box around the bat (y offset -2 to 4) towards which it will fly.
     * upon getting close a new target will be selected
     */
    private BlockPos currentFlightTarget;

    public EntityTFMobileFirefly(World par1World)
    {
        super(par1World);
        this.setSize(0.5F, 0.5F);
    }

    @Override
	protected float getSoundVolume()
    {
        return 0.1F;
    }

    @Override
	protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.95F;
    }

    @Override
	protected String getHurtSound()
    {
        return "mob.bat.hurt";
    }

    @Override
	protected String getDeathSound()
    {
        return "mob.bat.death";
    }

    @Override
	public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
	public void onUpdate()
    {
        super.onUpdate();

        this.motionY *= 0.6000000238418579D;
    }

    @Override
	protected void updateAITasks()
    {
    	super.updateAITasks();


    	if (this.currentFlightTarget != null && (!this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || this.currentFlightTarget.posY < 1))
    	{
    		this.currentFlightTarget = null;
    	}

    	if (this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0F)
    	{
    		this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
    	}

    	double var1 = this.currentFlightTarget.getX() + 0.5D - this.posX;
    	double var3 = this.currentFlightTarget.getY() + 0.1D - this.posY;
    	double var5 = this.currentFlightTarget.getZ() + 0.5D - this.posZ;
    	double speed = 0.05000000149011612D;
		this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * speed;
    	this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * speed * 2;
    	this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * speed;
    	float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
    	float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
    	this.moveForward = 0.5F;
    	this.rotationYaw += var8;
   }

    @Override
	protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
	public void fall(float dist, float mult) {}

    @Override
	protected void updateFallState(double par1, boolean par3, IBlockState state, BlockPos pos) {}

    @Override
	public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }

    @Override
	public boolean getCanSpawnHere()
    {
        int var1 = MathHelper.floor_double(this.getEntityBoundingBox().minY);

        if (var1 >= 63)
        {
            return false;
        }
        else
        {
            int var2 = MathHelper.floor_double(this.posX);
            int var3 = MathHelper.floor_double(this.posZ);
            int var4 = this.worldObj.getBlockLightValue(var2, var1, var3);
            byte var5 = 4;
  
            return var4 > this.rand.nextInt(var5) ? false : super.getCanSpawnHere();
        }
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }
    
    public float getGlowBrightness() 
    {
    	return (float)Math.sin(this.ticksExisted / 7.0) + 1F;
    }
}
