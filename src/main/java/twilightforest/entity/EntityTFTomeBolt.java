package twilightforest.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;


public class EntityTFTomeBolt extends EntityThrowable {

	public EntityTFTomeBolt(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityTFTomeBolt(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntityTFTomeBolt(World par1World) {
		super(par1World);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
        makeTrail();
	}

	@Override
    protected float getGravityVelocity()
    {
		return 0.003F;
    }

	public void makeTrail() {
		for (int i = 0; i < 5; i++) {
			double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			world.spawnParticle(EnumParticleTypes.CRIT, dx, dy, dz, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected void onImpact(RayTraceResult par1MovingObjectPosition) {
		// only damage living things
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
		{
			if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 6))
			{
				// inflict move slowdown
				byte potionStrength = (byte) (world.getDifficulty() == EnumDifficulty.PEACEFUL ? 3 : world.getDifficulty() == EnumDifficulty.NORMAL ? 7 : 9);
				if(potionStrength > 0)
				{
					((EntityLivingBase)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, potionStrength * 20, 1));
				}

			}
		}


		for (int i = 0; i < 8; ++i)
		{
			this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, rand.nextGaussian() * 0.05D, rand.nextDouble() * 0.2D, rand.nextGaussian() * 0.05D, Item.getIdFromItem(Items.FIRE_CHARGE));
		}

		if (!this.world.isRemote)
		{
			this.setDead();
		}

	}

}
