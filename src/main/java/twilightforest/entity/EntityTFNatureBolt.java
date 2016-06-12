package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;


public class EntityTFNatureBolt extends EntityThrowable {



	private EntityPlayer playerTarget;

	public EntityTFNatureBolt(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityTFNatureBolt(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntityTFNatureBolt(World par1World) {
		super(par1World);
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
			double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			worldObj.spawnParticle("happyVillager", dx, dy, dz, 0.0D, 0.0D, 0.0D);
		}
	}

	/**
	 * What happens when we hit something?
	 */
	@Override
	protected void onImpact(RayTraceResult par1MovingObjectPosition) {
		// only damage living things
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
		{
			if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 2))
			{
				// similar to EntityCaveSpider
				byte poisonStrength = (byte) (worldObj.difficultySetting == EnumDifficulty.PEACEFUL ? 0 : worldObj.difficultySetting == EnumDifficulty.NORMAL ? 3 : 7);
				if(poisonStrength > 0)
				{
					((EntityLivingBase)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON.id, poisonStrength * 20, 0));
					
//					System.out.println("Poisoning entityHit " + par1MovingObjectPosition.entityHit);
				}

			}
		}

		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.LEAVES) + "_0", this.posX, this.posY, this.posZ, rand.nextGaussian() * 0.05D, rand.nextDouble() * 0.2D, rand.nextGaussian() * 0.05D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
			
			if (par1MovingObjectPosition != null) {
				// if we hit a solid block, maybe do our nature burst effect.
				int dx = MathHelper.floor_double(par1MovingObjectPosition.blockX);
				int dy = MathHelper.floor_double(par1MovingObjectPosition.blockY);
				int dz = MathHelper.floor_double(par1MovingObjectPosition.blockZ);

				Material materialHit = worldObj.getBlock(dx, dy, dz).getMaterial();
			
				if (materialHit == Material.GRASS && this.playerTarget != null)
				{
					Items.DYE.onItemUse(new ItemStack(Items.DYE, 1, 15), playerTarget, worldObj, dx, dy, dz, 0, 0, 0, 0);
				}			
				else if (materialHit.isSolid() && canReplaceBlock(worldObj, dx, dy, dz)) 
				{
					worldObj.setBlock(dx, dy, dz, Blocks.LEAVES, 2, 3);
				}			
			}
		}

	}

	/**
	 * This is surprisingly difficult to determine
	 */
	private boolean canReplaceBlock(World worldObj, int dx, int dy, int dz) 
	{
		Block blockID = worldObj.getBlock(dx, dy, dz);
		//int meta = worldObj.getBlockMetadata(dx, dy, dz);
		Block blockObj = blockID;
		float hardness = blockObj == null ? -1 : blockObj.getBlockHardness(worldObj, dx, dy, dz);
		
		return hardness >= 0 && hardness < 50F;
	}

	public void setTarget(EntityLivingBase attackTarget) 
	{
		if (attackTarget instanceof EntityPlayer)
		{
			this.playerTarget = (EntityPlayer)attackTarget;
		}
	}

}
