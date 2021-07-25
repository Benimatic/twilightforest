package twilightforest.tileentity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.AABB;
import twilightforest.TFSounds;
import twilightforest.block.FireJetBlock;
import twilightforest.block.TFBlocks;
import twilightforest.enums.FireJetVariant;
import twilightforest.client.particle.TFParticleType;
import twilightforest.util.TFDamageSources;

import java.util.List;

public class FireJetTileEntity extends BlockEntity implements TickableBlockEntity {

	private int counter = 0;

	public FireJetTileEntity() {
		super(TFTileEntities.FLAME_JET.get());
	}

	@Override
	public void tick() {
		if (getBlockState().getBlock() == TFBlocks.fire_jet.get() || getBlockState().getBlock() == TFBlocks.encased_fire_jet.get()) {
			switch (getBlockState().getValue(FireJetBlock.STATE)) {
			case POPPING: tickPopping(); break;
			case FLAME: tickFlame(); break;
			}
		}
	}

	private void tickPopping() {
		if (++counter >= 80) {
			counter = 0;
			// turn to flame
			if (!level.isClientSide) {
				if (getBlockState().getBlock() == TFBlocks.fire_jet.get() || getBlockState().getBlock() == TFBlocks.encased_fire_jet.get()) {
					level.setBlockAndUpdate(worldPosition, getBlockState().setValue(FireJetBlock.STATE, FireJetVariant.FLAME));
				} else {
					level.removeBlock(worldPosition, false);
				}
			}
		} else {
			if (counter % 20 == 0) {
				for (int i = 0; i < 8; i++)
				{
					level.addParticle(ParticleTypes.LAVA, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.5, this.worldPosition.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
				}
				level.playSound(null, worldPosition, TFSounds.JET_POP, SoundSource.BLOCKS, 0.2F + level.random.nextFloat() * 0.2F, 0.9F + level.random.nextFloat() * 0.15F);
			}
		}
	}

	private void tickFlame() {
		double x = this.worldPosition.getX();
		double y = this.worldPosition.getY();
		double z = this.worldPosition.getZ();

		if (++counter > 60) {
			counter = 0;
			// idle again
			if (!level.isClientSide) {
				if (getBlockState().getBlock() == TFBlocks.fire_jet.get() || getBlockState().getBlock() == TFBlocks.encased_fire_jet.get()) {
					level.setBlockAndUpdate(worldPosition, getBlockState().setValue(FireJetBlock.STATE, FireJetVariant.IDLE));
				} else {
					level.removeBlock(worldPosition, false);
				}
			}
		}

		if (level.isClientSide) {
			if (counter % 2 == 0) {
				level.addParticle(ParticleTypes.LARGE_SMOKE, x + 0.5, y + 1.0, z + 0.5, 0.0D, 0.0D, 0.0D);
				level.addParticle(TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z + 0.5, 0.0D, 0.5D, 0.0D);
				level.addParticle(TFParticleType.LARGE_FLAME.get(), x - 0.5, y + 1.0, z + 0.5, 0.05D, 0.5D, 0.0D);
				level.addParticle(TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z - 0.5, 0.0D, 0.5D, 0.05D);
				level.addParticle(TFParticleType.LARGE_FLAME.get(), x + 1.5, y + 1.0, z + 0.5, -0.05D, 0.5D, 0.0D);
				level.addParticle(TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z + 1.5, 0.0D, 0.5D, -0.05D);
			}

			// sounds
			if (counter % 4 == 0) {
				level.playLocalSound(x + 0.5, y + 0.5, z + 0.5, TFSounds.JET_ACTIVE, SoundSource.BLOCKS, 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F, false);

			} else if (counter == 1) {
				level.playLocalSound(x + 0.5, y + 0.5, z + 0.5, TFSounds.JET_START, SoundSource.BLOCKS, 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F, false);
			}
		}

		// actual fire effects
		if (!level.isClientSide) {
			if (counter % 5 == 0) {
				// find entities in the area of effect
				List<Entity> entitiesInRange = level.getEntitiesOfClass(Entity.class,
						new AABB(worldPosition.offset(-2, 0, -2), worldPosition.offset(2, 4, 2)));
				// fire!
				for (Entity entity : entitiesInRange) {
					if (!entity.fireImmune()) {
						entity.hurt(TFDamageSources.FIRE_JET, 2);
						entity.setSecondsOnFire(15);
					}
				}
			}
		}
	}
}
