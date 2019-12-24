package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTFRisingZombie extends ZombieEntity {

	public EntityTFRisingZombie(EntityType<? extends EntityTFRisingZombie> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		// NO-OP
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
		// NO-OP
		return livingdata;
	}

	@Override
	public void livingTick() {
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		super.livingTick();
		if (!world.isRemote && ticksExisted % 130 == 0) {
			setDead();
			ZombieEntity zombie = new ZombieEntity(world);
			zombie.setPositionAndUpdate(posX, posY, posZ);
			zombie.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMaxHealth());
			zombie.setHealth(getHealth());
			zombie.setChild(isChild());
			world.addEntity(zombie);
			if (rand.nextBoolean() && world.getBlockState(getPosition().down()).getBlock() == Blocks.GRASS)
				world.setBlockState(getPosition().down(), Blocks.DIRT.getDefaultState());
		}
		if (world.isRemote && !world.isAirBlock(getPosition().down())) {
			for (int i = 0; i < 5; i++)
				world.addParticle(ParticleTypes.BLOCK_CRACK, posX + rand.nextGaussian() * 0.01F, posY + rand.nextGaussian() * 0.01F, posZ + rand.nextGaussian() * 0.01F, 0, 0, 0, Block.getStateId(world.getBlockState(getPosition().down())));
		}
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
		//NO-OP
	}
}
