package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.client.particle.TFParticleType;
import twilightforest.entity.EntityTFYeti;

public class EntityTFIceBomb extends EntityThrowable {
	
	private int zoneTimer = 80;
	private boolean hasHit;

	public EntityTFIceBomb(World par1World) {
		super(par1World);
	}

	public EntityTFIceBomb(World par1World, EntityLivingBase thrower) {
		super(par1World, thrower);
	}

	@Override
	protected void onImpact(RayTraceResult mop) {
		if (this.getThrower() != null && this.getThrower() instanceof EntityTFYetiAlpha) {
			double dist = this.getDistanceSqToEntity(this.getThrower());

			if (dist <= 100) {
				this.setDead();
			}
		}
		
		this.motionY = 0;
		this.hasHit = true;
		
		if (!world.isRemote) {
			this.doTerrainEffects();
		}
	}
	
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    private void doTerrainEffects() {
    	int range = 3;
		int ix = MathHelper.floor(this.lastTickPosX);
		int iy = MathHelper.floor(this.lastTickPosY);
		int iz = MathHelper.floor(this.lastTickPosZ);
		
		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				for (int z = -range; z <= range; z++) {
					this.doTerrainEffect(ix + x, iy + y, iz + z);
				}
			}
		}
	}

    /**
     * Freeze water, put snow on snowable surfaces
     */
	private void doTerrainEffect(BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getMaterial() == Material.WATER) {
			this.world.setBlockState(pos, Blocks.ICE.getDefaultState());
		}
		if (state.getMaterial() == Material.LAVA) {
			this.world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
		}
		if (this.world.isAirBlock(pos) && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, pos)) {
			this.world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
		}
	}

    @Override
	public void onUpdate()
    {
    	super.onUpdate();
    	
        //this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ); // push out of block

    	if (this.hasHit)
    	{
    		if (!world.isRemote)
    		{
	    		// slow down
	    		this.motionX *= 0.1D;
	    		this.motionY *= 0.1D;
	    		this.motionZ *= 0.1D;
    		}
    		
    		this.zoneTimer--;
    		
    		makeIceZone();

    		// eventually explode
    		if (this.zoneTimer <= 0)
    		{
    			detonate();
    		}

    	} else {
    		makeTrail();
    	}
    }
    
	public void makeTrail() {
		for (int i = 0; i < 10; i++) {
			double dx = posX + 0.75F * (rand.nextFloat() - 0.5F); 
			double dy = posY + 0.75F * (rand.nextFloat() - 0.5F); 
			double dz = posZ + 0.75F * (rand.nextFloat() - 0.5F); 
			
			TwilightForestMod.proxy.spawnParticle(this.world, TFParticleType.SNOW, dx, dy, dz, 0, 0, 0);
		}
	}

	private void makeIceZone() {
		// nearby sparkles
		if (this.world.isRemote) {
			// sparkles
			for (int i = 0; i < 20; i++) {
				double dx = this.posX + (rand.nextFloat() - rand.nextFloat()) * 3.0F;
				double dy = this.posY + (rand.nextFloat() - rand.nextFloat()) * 3.0F;
				double dz = this.posZ + (rand.nextFloat() - rand.nextFloat()) * 3.0F;
				
				
				TwilightForestMod.proxy.spawnParticle(this.world, TFParticleType.SNOW, dx, dy, dz, 0.0D, 0.0D, 0.0D);
			}
		} else {
			// damage
			if (this.zoneTimer % 10 == 0) {
	    		hitNearbyEntities();

			}
		}
	}

	private void hitNearbyEntities() {
		List<Entity> nearby = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(3, 2, 3));
		
		for (Entity entity : nearby) {
			if (entity instanceof EntityLivingBase && entity != this.getThrower()) {
				
				if (entity instanceof EntityTFYeti) {
					// TODO: make "frozen yeti" entity?
					entity.setDead();
					BlockPos pos = new BlockPos(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);

					world.setBlockState(pos, Blocks.ICE.getDefaultState());
					world.setBlockState(pos.up(), Blocks.ICE.getDefaultState());
					
				} else {
					entity.attackEntityFrom(DamageSource.magic, 1);
					int chillLevel = 2;
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 5, chillLevel, true, true));
				}
				
			}
		}
	}

	private void detonate() {
        this.setDead();
	}

	public Block getBlock() {
		return Blocks.PACKED_ICE;
	}
	
    @Override
    protected float func_70182_d()
    {
    	// velocity
        return 0.75F;
    }
    
    @Override
	protected float getGravityVelocity()
    {
        return this.hasHit ? 0F : 0.025F;
    }
    
    @Override
	protected float func_70183_g()
    {
        return -20.0F;
    }
}
