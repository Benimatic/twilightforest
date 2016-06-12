package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.util.Facing;
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
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled()
    {
        return true;
    }
	

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.27D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D); // attack damage
    }

    /**
     * returns if this entity triggers Blocks.ONENTITYWALKING on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
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
    protected String getHurtSound()
    {
        return "mob.silverfish.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.silverfish.kill";
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (this.isEntityInvulnerable())
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
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY + 0.5D);
		int z = MathHelper.floor_double(this.posZ);
		int randomFacing = this.rand.nextInt(6);
		
		x += Facing.offsetsXForSide[randomFacing];
		y += Facing.offsetsYForSide[randomFacing];
		z += Facing.offsetsZForSide[randomFacing];
		
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

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItem()
    {
        return TFItems.borerEssence;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }


    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

}
