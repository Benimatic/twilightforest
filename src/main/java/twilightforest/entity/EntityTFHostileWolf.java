package twilightforest.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;

import javax.annotation.Nullable;


public class EntityTFHostileWolf extends EntityWolf implements IMob {

	public EntityTFHostileWolf(World world) {
		super(world);
		setAngry(true);
		
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
	}
	
    public EntityTFHostileWolf(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    }

    @Override
	public void onUpdate()
    {
        super.onUpdate();
        if(!worldObj.isRemote && worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
        	setDead();
        }
    }

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

    @Override
	public boolean getCanSpawnHere()
    {
		// are we near a hedge maze?
		int chunkX = MathHelper.floor_double(posX) >> 4;
		int chunkZ = MathHelper.floor_double(posZ) >> 4;
		if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hedgeMaze) {
			// don't check light level
	        return worldObj.checkNoEntityCollision(getEntityBoundingBox()) && worldObj.getCollisionBoxes(this, getEntityBoundingBox()).size() == 0 && !worldObj.containsAnyLiquid(getEntityBoundingBox());
		}
		else {
			return isValidLightLevel() && worldObj.checkNoEntityCollision(getEntityBoundingBox()) && worldObj.getCollisionBoxes(this, getEntityBoundingBox()).size() == 0 && !worldObj.containsAnyLiquid(getEntityBoundingBox());
		}
    }
    
    // [VanillaCopy] Direct copy of EntityMob.isValidLightLevel
    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.worldObj.getLightFromNeighbors(blockpos);

            if (this.worldObj.isThundering())
            {
                int j = this.worldObj.getSkylightSubtracted();
                this.worldObj.setSkylightSubtracted(10);
                i = this.worldObj.getLightFromNeighbors(blockpos);
                this.worldObj.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    @Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
    {
    	return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
    {
    	return false;
    }

}
