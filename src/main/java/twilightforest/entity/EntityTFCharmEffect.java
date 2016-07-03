package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

public class EntityTFCharmEffect extends Entity
{

	private static final int DATA_OWNER = 17;
	private static final int DATA_ITEMID = 16;
	private static final double DISTANCE = 1.75;
	private EntityLivingBase orbiting;
	private double newPosX;
	private double newPosY;
	private double newPosZ;
	private double newRotationYaw;
	private double newRotationPitch;
	private int newPosRotationIncrements;
	
	public float offset;

	// client constructor
	public EntityTFCharmEffect(World par1World) 
	{
		super(par1World);
		this.setSize(0.25F, 0.25F);
		
		this.setItemID(TFItems.charmOfLife1);
	}

	// server constructor
	public EntityTFCharmEffect(World par1World, EntityLivingBase par2EntityLiving, Item item)
	{
		super(par1World);
		this.setSize(0.25F, 0.25F);
		
		this.orbiting = par2EntityLiving;
		//this.setOwner(orbiting.getEntityName());
		this.setItemID(item);
		
		Vec3d look = new Vec3d(DISTANCE, 0, 0);
		
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX += look.xCoord * DISTANCE;
		//this.posY += look.yCoord * DISTANCE;
		this.posZ += look.zCoord * DISTANCE;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;

	}
	
    @Override
    public void onUpdate()
    {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        
        if (this.newPosRotationIncrements > 0)
        {
            double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
            double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
            double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double var7 = MathHelper.wrapDegrees(this.newRotationYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(var1, var3, var5);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        
		float rotation = this.ticksExisted / 5.0F + offset;

        if (this.orbiting == null)
        {
        	this.orbiting = getOwner();
        }
        
        if (this.orbiting != null && !worldObj.isRemote)
        {
        	this.setLocationAndAngles(orbiting.posX, orbiting.posY + orbiting.getEyeHeight(), orbiting.posZ, orbiting.rotationYaw, orbiting.rotationPitch);

        	Vec3d look = new Vec3d(DISTANCE, 0, 0);
        	look.rotateAroundY(rotation);
        	this.posX += look.xCoord;
//        	this.posY += Math.sin(this.ticksExisted / 3.0F + offset);
        	this.posZ += look.zCoord;

    		this.setPosition(this.posX, this.posY, this.posZ);

        }
        
        if (this.getItemID() > 0)
        {
        	for (int i = 0; i < 3; i++) {
        		double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
        		double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
        		double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 

        		worldObj.spawnParticle("iconcrack_" + this.getItemID(), dx, dy, dz, 0, 0.2, 0);
        	}
        }
       
        if (this.ticksExisted > 200 || this.orbiting == null || this.orbiting.isDead)
        {
        	this.setDead();
        }
    }
    
    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.yOffset = 0.0F;
        this.newPosX = par1;
        this.newPosY = par3;
        this.newPosZ = par5;
        this.newRotationYaw = par7;
        this.newRotationPitch = par8;
        this.newPosRotationIncrements = par9;
    }


    @Override
    protected void entityInit()
    {
        this.dataWatcher.addObject(DATA_ITEMID, Integer.valueOf(0));
        this.dataWatcher.addObject(DATA_OWNER, "");
    }

    public String getOwnerName()
    {
        return this.dataWatcher.getWatchableObjectString(DATA_OWNER);
    }

    public void setOwner(String par1Str)
    {
        this.dataWatcher.updateObject(DATA_OWNER, par1Str);
    }

    public EntityLivingBase getOwner()
	{
	    return this.worldObj.getPlayerEntityByName(this.getOwnerName());
	}

	public int getItemID()
    {
        return this.dataWatcher.getWatchableObjectInt(DATA_ITEMID);
    }

    public void setItemID(Item charmOfLife1)
    {
        //this.dataWatcher.updateObject(DATA_ITEMID, Integer.valueOf(charmOfLife1));
    }

    @Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) 
	{
        par1NBTTagCompound.setString("Owner", this.getOwnerName());
        par1NBTTagCompound.setShort("ItemID", (short) this.getItemID());
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) 
	{
        this.setOwner(par1NBTTagCompound.getString("Owner"));
        //this.setItemID(par1NBTTagCompound.getShort("ItemID"));
	}

}
