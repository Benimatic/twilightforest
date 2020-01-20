package twilightforest.util;

import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO: Fix methods
public class ParticleHelper {

	public static void spawnParticles(World world, BlockPos pos, ParticleTypes type, int count, double speed, int... particleArgs) {
		world.addParticle(type, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, count, 0.5, 0.5, 0.5, speed, particleArgs);
	}

	public static void spawnParticles(Entity entity, ParticleTypes type, int count, double speed, int... particleArgs) {
		entity.world.addParticle(type, entity.getX(), entity.getY() + entity.getHeight() * 0.5, entity.getZ(), count, entity.getWidth() * 0.5, entity.getHeight() * 0.5, entity.getWidth() * 0.5, speed, particleArgs);
	}
}
