package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.entity.TFBlockEntities;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.Hydra;

public class HydraSpawnerBlockEntity extends BossSpawnerBlockEntity<Hydra> {

	public HydraSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TFBlockEntities.HYDRA_SPAWNER.get(), TFEntities.HYDRA.get(), pos, state);
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ParticleTypes.FLAME;
	}
}
