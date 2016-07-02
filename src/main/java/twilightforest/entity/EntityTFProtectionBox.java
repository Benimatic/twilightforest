package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTFProtectionBox extends Entity {

	public int lifeTime;
	public int sizeX;
	public int sizeY;
	public int sizeZ;

	public EntityTFProtectionBox(World worldObj, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(worldObj);
		
		this.setLocationAndAngles(minX, minY, minZ, 0.0F, 0.0F);
		
		sizeX = Math.abs(maxX - minX) + 1;
		sizeY = Math.abs(maxY - minY) + 1;
		sizeZ = Math.abs(maxZ - minZ) + 1;
		
		this.setSize(Math.max(sizeX, sizeZ), sizeY);
		
		this.lifeTime = 60;
		
		//System.out.println("Made new box");

	}

    @Override
	public void onUpdate()
    {
        super.onUpdate();
        
        if (lifeTime <= 1) {
        	setDead();
        } else {
        	lifeTime--;
        }

    }

	@Override
    public float getBrightness(float par1)
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
	@Override
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }
    
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		
	}

    @SideOnly(Side.CLIENT)
	@Override
    public boolean canRenderOnFire()
    {
        return false;
    }

}
