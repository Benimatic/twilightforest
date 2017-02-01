package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockHardenedClay;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;

public class EntityTFIceExploder extends EntityMob {

	private static final float EXPLOSION_RADIUS = 1;


	public EntityTFIceExploder(World par1World) {
		super(par1World);
        this.setSize(0.8F, 1.8F);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

	@Override
    protected Item getDropItem()
    {
        return Items.SNOWBALL;
    }

    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	// make snow particles
    	for (int i = 0; i < 3; i++) {
	    	float px = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
	    	float py = this.getEyeHeight() + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F;
	    	float pz = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
	    	
			TwilightForestMod.proxy.spawnParticle(this.world, "snowguardian", this.lastTickPosX + px, this.lastTickPosY + py, this.lastTickPosZ + pz, 0, 0, 0);
    	}

    }

    @Override
    protected SoundEvent getAmbientSound()
    {
    	return TFSounds.ICE_AMBIENT;
    }

	@Override
	protected SoundEvent getHurtSound()
    {
    	return TFSounds.ICE_HURT;
    }

	@Override
    protected SoundEvent getDeathSound()
    {
    	return TFSounds.ICE_DEATH;
    }

	@Override
    public float getEyeHeight()
    {
        return this.height * 0.6F;
    }

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}
	
    @Override
	protected void onDeathUpdate() {
        ++this.deathTime;

        if (this.deathTime == 60)
        {
            int i;
            
            
            boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)EntityTFIceExploder.EXPLOSION_RADIUS, flag);

            if (flag) {
            	this.detonate();
            }

            if (!this.world.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
            {
                i = this.getExperiencePoints(this.attackingPlayer);

                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }

            this.setDead();

            for (i = 0; i < 20; ++i)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
            }
        }
	}


	private void detonate() {
		int range = 4;
		
		BlockPos pos = new BlockPos(this);
		
		for (int dx = -range; dx <= range; dx++) {
			for (int dy = -range; dy <= range; dy++) {
				for (int dz = -range; dz <= range; dz++) {
					double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
					
					float randRange = range + (rand.nextFloat() - rand.nextFloat()) * 2F;
					
					if (distance < randRange) {
						this.transformBlock(pos.add(dx, dy, dz));
					}
				}
			}
		}
	}


	private void transformBlock(BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		// check if we should even explode this
		if (block.getExplosionResistance(this) < 8F && state.getBlockHardness(world, pos) >= 0) {
			
			int blockColor = 16777215;

			// todo 1.9 wtf
			//TODO: use a better check than exception handling to determine if we have access to client-side methods or not
			try {
				// figure out color
				blockColor = block.colorMultiplier(world, x, y, z);
			} catch (NoSuchMethodError e) {
				// fine, we're on a server
			}

			if (blockColor == 16777215) {
				blockColor = state.getMapColor().colorValue;
			}

			// do appropriate transformation
			if (this.shouldTransformGlass(state, pos)) {
				this.world.setBlockState(pos, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, getClosestDyeColor(blockColor)));
			} else if (this.shouldTransformClay(state, pos)) {
				this.world.setBlockState(pos, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, getClosestDyeColor(blockColor)));
			}
		}
	}


	private boolean shouldTransformClay(IBlockState state, BlockPos pos) {
		return state.getBlock().isNormalCube(state, this.world, pos);
	}


	private boolean shouldTransformGlass(IBlockState state, BlockPos pos) {
		return state.getBlock() != Blocks.AIR && this.isBlockNormalBounds(state, pos) && (!state.getMaterial().isOpaque() || state.getBlock().isLeaves(state, this.world, pos) || state.getBlock() == Blocks.ICE || state.getBlock() == TFBlocks.auroraBlock);
	}


	private boolean isBlockNormalBounds(IBlockState state, BlockPos pos) {
		return Block.FULL_BLOCK_AABB.equals(state.getBoundingBox(world, pos));
	}


	private EnumDyeColor getClosestDyeColor(int blockColor) {
		int red = (blockColor >> 16) & 255; 
		int green = (blockColor >> 8) & 255; 
		int blue = blockColor & 255; 
		
		
		EnumDyeColor bestColor = EnumDyeColor.WHITE;
		int bestDifference = 1024;
		
		for (EnumDyeColor color : EnumDyeColor.values()) {
			int iColor = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, color)
					.getMapColor().colorValue;
			
			int iRed = (iColor >> 16) & 255; 
			int iGreen = (iColor >> 8) & 255; 
			int iBlue = iColor & 255; 
			
			int difference = Math.abs(red - iRed) + Math.abs(green - iGreen) + Math.abs(blue - iBlue);
			
			if (difference < bestDifference) {
				bestColor = color;
				bestDifference = difference;
			}
		}
		
		return bestColor;
	}
	
    @Override
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }
}
