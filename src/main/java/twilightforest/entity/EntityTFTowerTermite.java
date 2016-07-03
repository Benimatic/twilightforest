package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.block.BlockTFTowerWood;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

public class EntityTFTowerTermite extends EntityMob 
{

	private int allySummonCooldown;

	public EntityTFTowerTermite(World par1World) {
		super(par1World);
		
        //this.texture = TwilightForestMod.MODEL_DIR + "towertermite.png";
        this.setSize(0.3F, 0.7F);
        //this.moveSpeed = 0.27F;
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D); // attack damage
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        double var1 = 8.0D;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, var1);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.silverfish.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return "mob.silverfish.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return "mob.silverfish.kill";
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable(par1DamageSource))
        {
            return false;
        }
        else
        {
            if (this.allySummonCooldown <= 0 && (par1DamageSource instanceof EntityDamageSource || par1DamageSource == DamageSource.magic))
            {
                this.allySummonCooldown = 20;
            }

            return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
        
        // if we are summoning allies, do that
        if (this.allySummonCooldown > 0)
        {
            --this.allySummonCooldown;

            if (this.allySummonCooldown == 0)
            {
                tryToSummonAllies();
            }
        }

        
        // if we have no target, burrow
        if (this.getAttackTarget() == null && this.getNavigator().noPath())
        {
            tryToBurrow();
        }

    }

    /**
     * Check nearby blocks.  If they are infested blocks, pop them!
     */
	protected void tryToSummonAllies() {
		int sx = MathHelper.floor_double(this.posX);
		int sy = MathHelper.floor_double(this.posY);
		int sz = MathHelper.floor_double(this.posZ);
		boolean stopSummoning = false;

		for (int dy = 0; !stopSummoning && dy <= 5 && dy >= -5; dy = dy <= 0 ? 1 - dy : 0 - dy)
		{
		    for (int dx = 0; !stopSummoning && dx <= 10 && dx >= -10; dx = dx <= 0 ? 1 - dx : 0 - dx)
		    {
		        for (int dz = 0; !stopSummoning && dz <= 10 && dz >= -10; dz = dz <= 0 ? 1 - dz : 0 - dz)
		        {
		            Block blockID = this.worldObj.getBlock(sx + dx, sy + dy, sz + dz);
		            int blockMeta = this.worldObj.getBlockMetadata(sx + dx, sy + dy, sz + dz);
		            
					if (blockID == TFBlocks.towerWood && blockMeta == BlockTFTowerWood.META_INFESTED)
		            {
		                this.worldObj.playAuxSFX(2001, sx + dx, sy + dy, sz + dz, Block.getIdFromBlock(blockID) + (blockMeta << 12));
		                this.worldObj.setBlock(sx + dx, sy + dy, sz + dz, Blocks.AIR, 0, 3);
		                TFBlocks.towerWood.onBlockDestroyedByPlayer(this.worldObj, sx + dx, sy + dy, sz + dz, BlockTFTowerWood.META_INFESTED);

		                if (this.rand.nextBoolean())
		                {
		                    stopSummoning = true;
		                    break;
		                }
		            }
		        }
		    }
		}
	}

    /**
     * Look at a random nearby block.  Is it the proper tower wood?  If so, burrow.
     */
	protected void tryToBurrow() {
        BlockPos pos = new BlockPos(posX, posY + 0.5, posZ);
        EnumFacing randomFacing = EnumFacing.random(rand);
        pos = pos.offset(randomFacing);

		Block blockIDNearby = this.worldObj.getBlock(x, y, z);
		int blockMetaNearby = this.worldObj.getBlockMetadata(x, y, z);

		if (canBurrowIn(blockIDNearby, blockMetaNearby))
		{
		    this.worldObj.setBlock(x, y, z, TFBlocks.towerWood, BlockTFTowerWood.META_INFESTED, 3);
		    this.spawnExplosionParticle();
		    this.setDead();
		}
	}

	/**
	 * @return true if we can burrow in the specified block
	 */
    protected boolean canBurrowIn(Block blockIDNearby, int blockMetaNearby) 
    {
		return blockIDNearby == TFBlocks.towerWood && blockMetaNearby == 0;
	}

	/**
	 * @return true if the nearby block is infested
	 */
    protected boolean isInfestedBlock(Block blockIDNearby, int blockMetaNearby) 
    {
		return blockIDNearby == TFBlocks.towerWood && blockMetaNearby == BlockTFTowerWood.META_INFESTED;
	}

	/**
     * Plays step sound at given x, y, z for the entity
     */
    protected void func_145780_a(int par1, int par2, int par3, Block par4)
    {
        this.playSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    @Override
    protected Item getDropItem()
    {
        return TFItems.borerEssence;
    }

    @Override
    public void onUpdate()
    {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

}
