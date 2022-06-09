package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFEntities;
import twilightforest.entity.boss.PlateauBoss;

public class FinalBossSpawnerBlockEntity extends BossSpawnerBlockEntity<PlateauBoss> {

	public FinalBossSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TFBlockEntities.FINAL_BOSS_SPAWNER.get(), TFEntities.PLATEAU_BOSS.get(), pos, state);
	}

	//no spawning for you
	@Override
	protected boolean spawnMyBoss(ServerLevelAccessor world) {
		return false;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return TFParticleType.ANNIHILATE.get();
	}
}
