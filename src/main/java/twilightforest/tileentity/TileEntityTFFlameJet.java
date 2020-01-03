package twilightforest.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import twilightforest.block.BlockTFFireJet;
import twilightforest.block.TFBlocks;
import twilightforest.enums.FireJetVariant;
import twilightforest.client.particle.TFParticleType;

import java.util.List;

public class TileEntityTFFlameJet extends TileEntity implements ITickableTileEntity {

	private int counter = 0;
	private FireJetVariant nextVariant;

	public TileEntityTFFlameJet(FireJetVariant variant) {
		super(TFTileEntities.FLAME_JET.get());
		this.nextVariant = variant;
	}

	public TileEntityTFFlameJet() {
		super(TFTileEntities.FLAME_JET.get());
	}

	@Override
	public void tick() {

		double x = this.pos.getX();
		double y = this.pos.getY();
		double z = this.pos.getZ();

		if (++counter > 60) {
			counter = 0;
			// idle again
			if (!world.isRemote) {
				if (world.getBlockState(pos).getBlock() == TFBlocks.fire_jet.get()) {
					world.setBlockState(pos, TFBlocks.fire_jet.get().getDefaultState().with(BlockTFFireJet.STATE, this.nextVariant));
				} else if (world.getBlockState(pos).getBlock() == TFBlocks.encased_fire_jet.get()) {
					world.setBlockState(pos, TFBlocks.encased_fire_jet.get().getDefaultState().with(BlockTFFireJet.STATE, this.nextVariant));
				}
			}
		}

		if (world.isRemote) {
			if (counter % 2 == 0) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, x + 0.5, y + 1.0, z + 0.5, 0.0D, 0.0D, 0.0D);
				world.addParticle(TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z + 0.5, 0.0D, 0.5D, 0.0D);
//			    world.addParticle();(TFParticleType.LARGE_FLAME, x + 0.5, y + 1.0, z + 0.5,
//    				Math.cos(counter / 4.0) * 0.2, 0.35D, Math.sin(counter / 4.0) * 0.2);			
//			    world.addParticle();(TFParticleType.LARGE_FLAME, x + 0.5, y + 1.0, this.pos.getZ() + 0.5,
//    				Math.cos(counter / 4.0 + Math.PI) * 0.2, 0.35D, Math.sin(counter / 4.0 + Math.PI) * 0.2);			
//		    	world.addParticle();(TFParticleType.LARGE_FLAME, x + 0.5 + Math.cos(counter / 4.0), y + 1.0, z + 0.5 + Math.sin(counter / 4.0),
//    				Math.sin(counter / 4.0) * 0.05, 0.35D, Math.cos(counter / 4.0) * 0.05);			
//			    world.addParticle();(TFParticleType.LARGE_FLAME, x + 0.5 + Math.cos(counter / 4.0 + Math.PI), y + 1.0, z + 0.5 + Math.sin(counter / 4.0 + Math.PI),
//    				Math.sin(counter / 4.0 + Math.PI) * 0.05, 0.35D, Math.cos(counter / 4.0 + Math.PI) * 0.05);			

				world.addParticle(TFParticleType.LARGE_FLAME.get(), x - 0.5, y + 1.0, z + 0.5, 0.05D, 0.5D, 0.0D);
				world.addParticle(TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z - 0.5, 0.0D, 0.5D, 0.05D);
				world.addParticle(TFParticleType.LARGE_FLAME.get(), x + 1.5, y + 1.0, z + 0.5, -0.05D, 0.5D, 0.0D);
				world.addParticle(TFParticleType.LARGE_FLAME.get(), x + 0.5, y + 1.0, z + 1.5, 0.0D, 0.5D, -0.05D);
			}

			// sounds
			if (counter % 4 == 0) {
				world.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.BLOCKS, 1.0F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.3F, false);

			} else if (counter == 1) {
				world.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.3F, false);
			}
		}

		// actual fire effects
		if (!world.isRemote) {
			if (counter % 5 == 0) {
				// find entities in the area of effect
				List<Entity> entitiesInRange = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(pos.add(-2, 0, -2), pos.add(2, 4, 2)));
				// fire!
				for (Entity entity : entitiesInRange) {
					if (!entity.isImmuneToFire()) {
						entity.attackEntityFrom(DamageSource.IN_FIRE, 2);
						entity.setFire(15);
					}
				}
			}
		}
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.nextVariant = FireJetVariant.values()[compound.getInt("NextMeta")];
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("NextMeta", this.nextVariant.ordinal());
		return compound;
	}
}
