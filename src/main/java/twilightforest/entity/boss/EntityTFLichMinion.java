package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;



public class EntityTFLichMinion extends EntityZombie {
	
	EntityTFLich master;

	public EntityTFLichMinion(World par1World) {
		super(par1World);
		this.master = null;
	}

	public EntityTFLichMinion(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

	public EntityTFLichMinion(World par1World, EntityTFLich entityTFLich) {
		super(par1World);
		this.master = entityTFLich;
	}

    @Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		EntityLivingBase prevTarget = getAttackTarget();
		
		if (super.attackEntityFrom(par1DamageSource, par2)) {
			if (par1DamageSource.getEntity() instanceof EntityTFLich) {
				// return to previous target
				setAttackTarget(prevTarget);
				setRevengeTarget(prevTarget);
				// but speed up
				addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 4));
				addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 1));
			}
			return true;
		}
		else {
			return false;
		}
	}
    
    @Override
	public void onLivingUpdate() {
    	if (master == null) {
    		findNewMaster();
    	}
    	// if we still don't have a master, or ours is dead, die.
    	if (master == null || master.isDead) {
    		this.setHealth(0);
    	}
    	super.onLivingUpdate();
    }

	private void findNewMaster() {
		List<EntityTFLich> nearbyLiches = world.getEntitiesWithinAABB(EntityTFLich.class, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));
		
		for (EntityTFLich nearbyLich : nearbyLiches) {
			if (!nearbyLich.isShadowClone() && nearbyLich.wantsNewMinion(this)) {
				this.master = nearbyLich;
				
				// animate our new linkage!
				master.makeBlackMagicTrail(posX, posY + this.getEyeHeight(), posZ, master.posX, master.posY + master.getEyeHeight(), master.posZ);
				
				// become angry at our masters target
				setAttackTarget(master.getAttackTarget());
				
				// quit looking
				break;
			}
		}
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}
    
    /*@Override todo 1.9 seems to be close copy of super super class
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        float[] equipChances = new float[] {0.0F, 0.25F, 0.75F, 1F};
    	
        if (this.rand.nextFloat() < equipChances[2]) //this.world.difficultySetting])
        {
            int var1 = this.rand.nextInt(2);
            float var2 = this.world.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;

            if (this.rand.nextFloat() < 0.07F)
            {
                ++var1;
            }

            if (this.rand.nextFloat() < 0.07F)
            {
                ++var1;
            }

            if (this.rand.nextFloat() < 0.07F)
            {
                ++var1;
            }

            for (int var3 = 3; var3 >= 0; --var3)
            {
                ItemStack var4 = this.func_130225_q(var3);

                if (var3 < 3 && this.rand.nextFloat() < var2)
                {
                    break;
                }

                if (var4 == null)
                {
                    Item var5 = getArmorByChance(var3 + 1, var1);

                    if (var5 != null)
                    {
                        this.setCurrentItemOrArmor(var3 + 1, new ItemStack(var5));
                    }
                }
            }
        }
    }*/

}
