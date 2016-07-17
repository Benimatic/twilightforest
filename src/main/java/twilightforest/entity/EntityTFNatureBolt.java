package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
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
			worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, dx, dy, dz, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected void onImpact(RayTraceResult ray) {
		// only damage living things
		if (ray.entityHit != null && ray.entityHit instanceof EntityLivingBase)
		{
			if (ray.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 2))
			{
				// similar to EntityCaveSpider
				byte poisonStrength = (byte) (worldObj.getDifficulty() == EnumDifficulty.PEACEFUL ? 0 : worldObj.getDifficulty() == EnumDifficulty.NORMAL ? 3 : 7);
				if(poisonStrength > 0)
				{
					((EntityLivingBase)ray.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, poisonStrength * 20, 0));
					
//					System.out.println("Poisoning entityHit " + par1MovingObjectPosition.entityHit);
				}

			}
		}

		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, rand.nextGaussian() * 0.05D, rand.nextDouble() * 0.2D, rand.nextGaussian() * 0.05D, Block.getStateId(Blocks.LEAVES.getDefaultState()));
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();

			if (ray.getBlockPos() != null) {
				// if we hit a solid block, maybe do our nature burst effect.
				Material materialHit = worldObj.getBlockState(ray.getBlockPos()).getMaterial();

				if (materialHit == Material.GRASS && this.playerTarget != null)
				{
					Items.DYE.onItemUse(new ItemStack(Items.DYE, 1, 15), playerTarget, worldObj, ray.getBlockPos(), EnumHand.MAIN_HAND, ray.sideHit, 0, 0, 0);
				}
				else if (materialHit.isSolid() && canReplaceBlock(worldObj, ray.getBlockPos()))
				{
					worldObj.setBlockState(ray.getBlockPos(), Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH));
				}
			}
		}
	}

	private boolean canReplaceBlock(World worldObj, BlockPos pos)
	{
		float hardness = worldObj.getBlockState(pos).getBlockHardness(worldObj, pos);
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
