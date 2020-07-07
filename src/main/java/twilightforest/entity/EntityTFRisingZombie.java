package twilightforest.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
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
		setMotion(new Vector3d(0, 0, 0));
		super.livingTick();
		if (!world.isRemote && ticksExisted % 130 == 0) {
			remove();
			ZombieEntity zombie = new ZombieEntity(world);
			zombie.setPositionAndUpdate(getPosX(), getPosY(), getPosZ());
			zombie.getAttribute(Attributes.field_233818_a_).setBaseValue(getMaxHealth());
			zombie.setHealth(getHealth());
			zombie.setChild(isChild());
			world.addEntity(zombie);
			if (rand.nextBoolean() && world.getBlockState(func_233580_cy_().down()).getBlock() == Blocks.GRASS)
				world.setBlockState(func_233580_cy_().down(), Blocks.DIRT.getDefaultState());
		}
		if (world.isRemote && !world.isAirBlock(func_233580_cy_().down())) {
			for (int i = 0; i < 5; i++)
				world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, world.getBlockState(func_233580_cy_().down())), getPosX() + rand.nextGaussian() * 0.01F, getPosY() + rand.nextGaussian() * 0.01F, getPosZ() + rand.nextGaussian() * 0.01F, 0, 0, 0);
		}
	}

	@Override
	public void func_233627_a_(float strength, double xRatio, double zRatio) {
		//NO-OP
	}
}
