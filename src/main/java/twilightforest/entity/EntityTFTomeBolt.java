package twilightforest.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;


public class EntityTFTomeBolt extends EntityThrowable {

	public EntityTFTomeBolt(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	public EntityTFTomeBolt(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
	}

	public EntityTFTomeBolt(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onUpdate() {
		super.onUpdate();

        makeTrail();
	}
	
	/**
	 * How much this entity falls each tick
	 */
	@Override
    protected float getGravityVelocity()
    {
		return 0.003F;
    }
	
	/**
	 * Make sparkly trail
	 */
	public void makeTrail() {
		for (int i = 0; i < 5; i++) {
			double dx = posX + 0.5 * (rand.nextFloat() - rand.nextFloat()); 
			double dy = posY + 0.5 * (rand.nextFloat() - rand.nextFloat()); 
			double dz = posZ + 0.5 * (rand.nextFloat() - rand.nextFloat()); 
			worldObj.spawnParticle("crit", dx, dy, dz, 0.0D, 0.0D, 0.0D);
		}
	}

	/**
	 * What happens when we hit something?
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		// only damage living things
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
		{
			if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 6))
			{
				// inflict move slowdown
				byte potionStrength = (byte) (worldObj.difficultySetting == EnumDifficulty.PEACEFUL ? 3 : worldObj.difficultySetting == EnumDifficulty.NORMAL ? 7 : 9);
				if(potionStrength > 0)
				{
					((EntityLivingBase)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, potionStrength * 20, 1));
				}

			}
		}


		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(Items.fire_charge), this.posX, this.posY, this.posZ, rand.nextGaussian() * 0.05D, rand.nextFloat() * 0.2D, rand.nextGaussian() * 0.05D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}

	}

}
