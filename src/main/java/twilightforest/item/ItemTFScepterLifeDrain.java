package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemTFScepterLifeDrain extends ItemTF {

	protected ItemTFScepterLifeDrain() {
		super();
        this.maxStackSize = 1;
        this.setMaxDamage(99);
		this.setCreativeTab(TFItems.creativeTab);

	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer player) {
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) {
			player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		else {
			player.stopUsingItem();
		}
		
		return par1ItemStack;
	}

	/**
	 * Animates the target falling apart into a rain of shatter particles
	 */
	public static void animateTargetShatter(World worldObj, EntityLivingBase target) {
		for (int var1 = 0; var1 < 50; ++var1)
		{
		    double gaussX = itemRand.nextGaussian() * 0.02D;
		    double gaussY = itemRand.nextGaussian() * 0.02D;
		    double gaussZ = itemRand.nextGaussian() * 0.02D;
		    double gaussFactor = 10.0D;
		    
		    Item popItem = getTargetDropItemId(target) != null ? getTargetDropItemId(target) : Items.ROTTEN_FLESH;
		    
		    worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(popItem), target.posX + itemRand.nextFloat() * target.width * 2.0F - target.width - gaussX * gaussFactor, target.posY + itemRand.nextFloat() * target.height - gaussY * gaussFactor, target.posZ + itemRand.nextFloat() * target.width * 2.0F - target.width - gaussZ * gaussFactor, gaussX, gaussY, gaussZ);
		}
	}
	
	public static Item getTargetDropItemId(EntityLivingBase target) {
		//TODO: make this actually work
		return Items.ROTTEN_FLESH;
	}

	/**
	 * What, if anything, is the player currently looking at?
	 */
	private Entity getPlayerLookTarget(World worldObj, EntityPlayer player) {
		Entity pointedEntity = null;
		double range = 20.0D;
        Vec3d srcVec = Vec3d.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d lookVec = player.getLook(1.0F);
        Vec3d destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
        float var9 = 1.0F;
        List<Entity> possibleList = worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range).expand(var9, var9, var9));
        double hitDist = 0;

        for (Entity possibleEntity : possibleList)
        {
            

            if (possibleEntity.canBeCollidedWith())
            {
                float borderSize = possibleEntity.getCollisionBorderSize();
                AxisAlignedBB collisionBB = possibleEntity.boundingBox.expand((double)borderSize, (double)borderSize, (double)borderSize);
                RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);

                if (collisionBB.isVecInside(srcVec))
                {
                    if (0.0D < hitDist || hitDist == 0.0D)
                    {
                        pointedEntity = possibleEntity;
                        hitDist = 0.0D;
                    }
                }
                else if (interceptPos != null)
                {
                    double possibleDist = srcVec.distanceTo(interceptPos.hitVec);

                    if (possibleDist < hitDist || hitDist == 0.0D)
                    {
                        pointedEntity = possibleEntity;
                        hitDist = possibleDist;
                    }
                }
            }
        }
		return pointedEntity;
	}
	
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		World worldObj = player.worldObj;
		
		if (stack.getItemDamage() >= this.getMaxDamage()) {
			// do not use
			player.stopUsingItem();
			return;
		}

		if (count % 5 == 0) {

			// is the player looking at an entity
			Entity pointedEntity = getPlayerLookTarget(worldObj, player);

			if (pointedEntity != null && pointedEntity instanceof EntityLivingBase) {
				EntityLivingBase target =  (EntityLivingBase)pointedEntity;

				if (target.getActivePotionEffect(MobEffects.MOVESLOWDOWN) != null || target.getHealth() < 1) {

					if (target.getHealth() <= 3) {
						// make it explode

						makeRedMagicTrail(worldObj,  player.posX, player.posY + player.getEyeHeight(), player.posZ, target.posX, target.posY + target.getEyeHeight(), target.posZ);
						if (target instanceof EntityLiving)
						{
							((EntityLiving) target).spawnExplosionParticle();
						}
						worldObj.playSoundAtEntity(target, "game.player.hurt.fall.big", 1.0F, ((itemRand.nextFloat() - itemRand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
						animateTargetShatter(worldObj, (EntityLivingBase) target);
						if (!worldObj.isRemote) {
							target.setDead();
							target.onDeath(DamageSource.causeIndirectMagicDamage(player, player));
						}
						player.stopUsingItem();
					}
					else {
						// we have hit this creature recently
						if (!worldObj.isRemote) {
							target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, player), 3);

							// only do lifting effect on creatures weaker than the player
							if (getMaxHealth(target) <= getMaxHealth(player)) {
								target.motionX = 0;
								target.motionY = 0.2;
								target.motionZ = 0;
							}

							target.addPotionEffect(new PotionEffect(MobEffects.MOVESLOWDOWN.id, 20, 2));

							if (count % 10 == 0) {
								// heal the player
								player.heal(1);
								// and give foods
								player.getFoodStats().addStats(1, 0.1F);
							}
						}
					}
				}
				else {
					// this is a new creature to start draining
					makeRedMagicTrail(worldObj,  player.posX, player.posY + player.getEyeHeight(), player.posZ, target.posX, target.posY + target.getEyeHeight(), target.posZ);

					worldObj.playSoundAtEntity(player, "fire.ignite", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);

					if (!worldObj.isRemote) {
						target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, player), 1);
						
						// only do lifting effect on creatures weaker than the player
						if (getMaxHealth(target) <= getMaxHealth(player)) {
							target.motionX = 0;
							target.motionY = 0.2;
							target.motionZ = 0;
						}

						target.addPotionEffect(new PotionEffect(MobEffects.MOVESLOWDOWN.id, 20, 2));
					}
				}

				if (!worldObj.isRemote) {
					stack.damageItem(1, player);
				}

			}
		}

	}
	
	private float getMaxHealth(EntityLivingBase target)
	{
		return (float) target.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
	}

	/**
     * Make a trail of particles from one point to another
     */
    protected void makeRedMagicTrail(World worldObj, double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
		// make particle trail
    	int particles = 32;
    	for (int i = 0; i < particles; i++)
    	{
    		double trailFactor = i / (particles - 1.0D);
    		float f = 1.0F;
    		float f1 = 0.5F;
    		float f2 = 0.5F;
    		double tx = srcX + (destX - srcX) * trailFactor + worldObj.rand.nextGaussian() * 0.005;
    		double ty = srcY + (destY - srcY) * trailFactor + worldObj.rand.nextGaussian() * 0.005;
    		double tz = srcZ + (destZ - srcZ) * trailFactor + worldObj.rand.nextGaussian() * 0.005;
    		worldObj.spawnParticle("mobSpell", tx, ty, tz, f, f1, f2);
    	}
	}
	
    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    
    /**
     * Return an item rarity from EnumRarity
     */    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.rare;
	}

    /**
     * Display charges left in tooltip
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add((par1ItemStack.getMaxDamage() -  par1ItemStack.getItemDamage()) + " charges left");
	}

	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
