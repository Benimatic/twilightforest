package twilightforest.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityTFThrowable extends ThrowableEntity implements ITFProjectile {

	public EntityTFThrowable(EntityType<? extends EntityTFThrowable> type, World worldIn) {
		super(type, worldIn);
	}

	public EntityTFThrowable(EntityType<? extends EntityTFThrowable> type, World worldIn, double x, double y, double z) {
		super(type, x, y, z, worldIn);
	}

	public EntityTFThrowable(EntityType<? extends EntityTFThrowable> type, World worldIn, LivingEntity throwerIn) {
		super(type, throwerIn, worldIn);
	}

	@Override
	protected void registerData() {
		
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
