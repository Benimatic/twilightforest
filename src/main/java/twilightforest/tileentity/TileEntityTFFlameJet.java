package twilightforest.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFFireJet;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.FireJetVariant;

public class TileEntityTFFlameJet extends TileEntity implements ITickable {
	
	private int counter = 0;
	private FireJetVariant nextVariant;
	
    public TileEntityTFFlameJet(FireJetVariant variant) {
		this.nextVariant = variant;
	}

    @Override
	public void update()
    {
		if (++counter > 60)
		{
			counter = 0;
	    	// idle again
			if (!world.isRemote && world.getBlockState(pos).getBlock() == TFBlocks.fireJet)
			{
				world.setBlockState(pos, TFBlocks.fireJet.getDefaultState().withProperty(BlockTFFireJet.VARIANT, this.nextVariant), 3);
			}
			this.invalidate();
		}
		else if (counter % 2 == 0)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX() + 0.5, this.pos.getY() + 1.0, this.pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
			TwilightForestMod.proxy.spawnParticle(this.world, "largeflame", this.xCoord + 0.5, this.yCoord + 1.0, this.zCoord + 0.5, 0.0D, 0.5D, 0.0D);
//			TwilightForestMod.proxy.spawnParticle("largeflame", this.xCoord + 0.5, this.yCoord + 1.0, this.zCoord + 0.5, 
//    				Math.cos(counter / 4.0) * 0.2, 0.35D, Math.sin(counter / 4.0) * 0.2);			
//			TwilightForestMod.proxy.spawnParticle("largeflame", this.xCoord + 0.5, this.yCoord + 1.0, this.zCoord + 0.5, 
//    				Math.cos(counter / 4.0 + Math.PI) * 0.2, 0.35D, Math.sin(counter / 4.0 + Math.PI) * 0.2);			
//			TwilightForestMod.proxy.spawnParticle("largeflame", this.xCoord + 0.5 + Math.cos(counter / 4.0), this.yCoord + 1.0, this.zCoord + 0.5 + Math.sin(counter / 4.0), 
//    				Math.sin(counter / 4.0) * 0.05, 0.35D, Math.cos(counter / 4.0) * 0.05);			
//			TwilightForestMod.proxy.spawnParticle("largeflame", this.xCoord +  0.5 + Math.cos(counter / 4.0 + Math.PI), this.yCoord + 1.0, this.zCoord + 0.5 + Math.sin(counter / 4.0 + Math.PI), 
//    				Math.sin(counter / 4.0 + Math.PI) * 0.05, 0.35D, Math.cos(counter / 4.0 + Math.PI) * 0.05);			

			TwilightForestMod.proxy.spawnParticle(this.world, "largeflame", this.xCoord - 0.5, this.yCoord + 1.0, this.zCoord + 0.5, 0.05D, 0.5D, 0.0D);
			TwilightForestMod.proxy.spawnParticle(this.world, "largeflame", this.xCoord + 0.5, this.yCoord + 1.0, this.zCoord - 0.5, 0.0D, 0.5D, 0.05D);
			TwilightForestMod.proxy.spawnParticle(this.world, "largeflame", this.xCoord + 1.5, this.yCoord + 1.0, this.zCoord + 0.5, -0.05D, 0.5D, 0.0D);
			TwilightForestMod.proxy.spawnParticle(this.world, "largeflame", this.xCoord + 0.5, this.yCoord + 1.0, this.zCoord + 1.5, 0.0D, 0.5D, -0.05D);

		}
		
		// sounds
		if (counter % 4 == 0)
		{
			world.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "mob.ghast.fireball", 1.0F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.3F);

		}
		else if (counter == 1)
		{
			world.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "fire.ignite", 1.0F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.3F);
		}
		
		// actual fire effects
		if (!world.isRemote)
		{
			if (counter % 5 == 0)
			{
				// find entities in the area of effect
				List<Entity> entitiesInRange = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(pos.add(-2, 0, -2), pos.add(2, 4, 2)));
				// fire!
				for (Entity entity : entitiesInRange)
				{
					if (!entity.isImmuneToFire())
			        {
						entity.attackEntityFrom(DamageSource.inFire, 2);
						entity.setFire(15);
			        }
				}
			}
		}
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
        this.nextVariant = FireJetVariant.values()[par1NBTTagCompound.getInteger("NextMeta")];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("NextMeta", this.nextVariant.ordinal());
		return par1NBTTagCompound;
    }
}
