package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.entity.TFBlockEntities;
import twilightforest.client.particle.TFParticleType;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.SnowQueen;

public class SnowQueenSpawnerBlockEntity extends BossSpawnerBlockEntity<SnowQueen> {

	public SnowQueenSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TFBlockEntities.SNOW_QUEEN_SPAWNER.get(), TFEntities.SNOW_QUEEN.get(), pos, state);
	}

	@Override
	public boolean anyPlayerInRange() {
		Player closestPlayer = level.getNearestPlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > worldPosition.getY() - 4;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return TFParticleType.SNOW.get();
	}
}
