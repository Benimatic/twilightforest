package twilightforest.tileentity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import twilightforest.TFSounds;
import twilightforest.block.TFBlocks;
import twilightforest.data.BlockTagGenerator;
import twilightforest.entity.TFEntities;
import twilightforest.entity.CarminiteGhastlingEntity;
import twilightforest.util.TFDamageSources;

import java.util.Random;

public class ActiveCarminiteReactorTileEntity extends BlockEntity implements TickableBlockEntity {

	private int counter = 0;

	private int secX, secY, secZ;
	private int terX, terY, terZ;


	public ActiveCarminiteReactorTileEntity() {
		super(TFTileEntities.CARMINITE_REACTOR.get());
		Random rand = new Random();

		// determine the two smaller bursts
		this.secX = 3 * (rand.nextBoolean() ? 1 : -1);
		this.secY = 3 * (rand.nextBoolean() ? 1 : -1);
		this.secZ = 3 * (rand.nextBoolean() ? 1 : -1);

		this.terX = 3 * (rand.nextBoolean() ? 1 : -1);
		this.terY = 3 * (rand.nextBoolean() ? 1 : -1);
		this.terZ = 3 * (rand.nextBoolean() ? 1 : -1);

		if (secX == terX && secY == terY && secZ == terZ) {
			terX = -terX;
			terY = -terY;
			terZ = -terZ;
		}
	}

	@Override
	public void tick() {
		counter++;

		if (!level.isClientSide) {

			// every 2 seconds for 10 seconds, destroy a new radius
			int offset = 10;

			if (counter % 5 == 0) {
				if (counter == 5) {
					BlockState fakeGold = TFBlocks.fake_gold.get().defaultBlockState();
					BlockState fakeDiamond = TFBlocks.fake_diamond.get().defaultBlockState();

					// transformation!
					createFakeBlock(worldPosition.offset(1, 1, 1), fakeDiamond);
					createFakeBlock(worldPosition.offset(1, 1, -1), fakeDiamond);
					createFakeBlock(worldPosition.offset(-1, 1, 1), fakeDiamond);
					createFakeBlock(worldPosition.offset(-1, 1, -1), fakeDiamond);
					createFakeBlock(worldPosition.offset(0, 1, 0), fakeDiamond);

					createFakeBlock(worldPosition.offset(0, 1, 1), fakeGold);
					createFakeBlock(worldPosition.offset(0, 1, -1), fakeGold);
					createFakeBlock(worldPosition.offset(1, 1, 0), fakeGold);
					createFakeBlock(worldPosition.offset(-1, 1, 0), fakeGold);

					createFakeBlock(worldPosition.offset(1, 0, 1), fakeGold);
					createFakeBlock(worldPosition.offset(1, 0, -1), fakeGold);
					createFakeBlock(worldPosition.offset(-1, 0, 1), fakeGold);
					createFakeBlock(worldPosition.offset(-1, 0, -1), fakeGold);

					createFakeBlock(worldPosition.offset(0, 0, 1), fakeDiamond);
					createFakeBlock(worldPosition.offset(0, 0, -1), fakeDiamond);
					createFakeBlock(worldPosition.offset(1, 0, 0), fakeDiamond);
					createFakeBlock(worldPosition.offset(-1, 0, 0), fakeDiamond);
					createFakeBlock(worldPosition.offset(0, -1, 0), fakeDiamond);

					createFakeBlock(worldPosition.offset(1, -1, 1), fakeDiamond);
					createFakeBlock(worldPosition.offset(1, -1, -1), fakeDiamond);
					createFakeBlock(worldPosition.offset(-1, -1, 1), fakeDiamond);
					createFakeBlock(worldPosition.offset(-1, -1, -1), fakeDiamond);

					createFakeBlock(worldPosition.offset(0, -1, 1), fakeGold);
					createFakeBlock(worldPosition.offset(0, -1, -1), fakeGold);
					createFakeBlock(worldPosition.offset(1, -1, 0), fakeGold);
					createFakeBlock(worldPosition.offset(-1, -1, 0), fakeGold);

				}


				// primary burst
				int primary = counter - 80;

				if (primary >= offset && primary <= 249) {
					drawBlob(this.worldPosition, (primary - offset) / 40, Blocks.AIR.defaultBlockState(), primary - offset, false);
				}
				if (primary <= 200) {
					drawBlob(this.worldPosition, primary / 40, TFBlocks.reactor_debris.get().defaultBlockState(), counter, false);
				}

				// secondary burst
				int secondary = counter - 120;

				if (secondary >= offset && secondary <= 129) {
					drawBlob(this.worldPosition.offset(secX, secY, secZ), (secondary - offset) / 40, Blocks.AIR.defaultBlockState(), secondary - offset, false);
				}
				if (secondary >= 0 && secondary <= 160) {
					drawBlob(this.worldPosition.offset(secX, secY, secZ), secondary / 40, Blocks.AIR.defaultBlockState(), secondary, true);
				}

				// tertiary burst
				int tertiary = counter - 160;

				if (tertiary >= offset && tertiary <= 129) {
					drawBlob(this.worldPosition.offset(terX, terY, terZ), (tertiary - offset) / 40, Blocks.AIR.defaultBlockState(), tertiary - offset, false);
				}
				if (tertiary >= 0 && tertiary <= 160) {
					drawBlob(this.worldPosition.offset(terX, terY, terZ), tertiary / 40, Blocks.AIR.defaultBlockState(), tertiary, true);
				}

			}


			if (counter >= 350) {
				// spawn mini ghasts near the secondary & tertiary points
				for (int i = 0; i < 3; i++) {
					spawnGhastNear(this.worldPosition.getX() + secX, this.worldPosition.getY() + secY, this.worldPosition.getZ() + secZ);
					spawnGhastNear(this.worldPosition.getX() + terX, this.worldPosition.getY() + terY, this.worldPosition.getZ() + terZ);
				}

				// deactivate & explode
				level.explode(null, TFDamageSources.REACTOR, (ExplosionDamageCalculator)null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 2.0F, true, Explosion.BlockInteraction.BREAK);
				level.removeBlock(worldPosition, false);
			}

		} else {
			if (counter % 5 == 0 && counter <= 250) {
				level.playLocalSound(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D, TFSounds.REACTOR_AMBIENT, SoundSource.BLOCKS, counter / 100F, counter / 100F, false);
			}
		}
	}


	private void spawnGhastNear(int x, int y, int z) {
		CarminiteGhastlingEntity ghast = new CarminiteGhastlingEntity(TFEntities.mini_ghast, level);
		ghast.moveTo(x - 1.5 + level.random.nextFloat() * 3.0, y - 1.5 + level.random.nextFloat() * 3.0, z - 1.5 + level.random.nextFloat() * 3.0, level.random.nextFloat() * 360F, 0.0F);
		level.addFreshEntity(ghast);
	}

	private void drawBlob(BlockPos pos, int rad, BlockState state, int fuzz, boolean netherTransform) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			// transform fuzz
			int fuzzX = (fuzz + dx) % 8;

			for (byte dy = 0; dy <= rad; dy++) {
				int fuzzY = (fuzz + dy) % 8;

				for (byte dz = 0; dz <= rad; dz++) {
					// determine how far we are from the center.
					byte dist;
					if (dx >= dy && dx >= dz) {
						dist = (byte) (dx + (byte) ((Math.max(dy, dz) * 0.5) + (Math.min(dy, dz) * 0.25)));
					} else if (dy >= dx && dy >= dz) {
						dist = (byte) (dy + (byte) ((Math.max(dx, dz) * 0.5) + (Math.min(dx, dz) * 0.25)));
					} else {
						dist = (byte) (dz + (byte) ((Math.max(dx, dy) * 0.5) + (Math.min(dx, dy) * 0.25)));
					}

					// if we're inside the blob, fill it
					if (dist == rad && !(dx == 0 && dy == 0 && dz == 0)) {
						// do eight at a time for easiness!
						switch (fuzzX) {
							case 0:
								transformBlock(pos.offset(dx, dy, dz), state, fuzzY, netherTransform);
								break;
							case 1:
								transformBlock(pos.offset(dx, dy, -dz), state, fuzzY, netherTransform);
								break;
							case 2:
								transformBlock(pos.offset(-dx, dy, dz), state, fuzzY, netherTransform);
								break;
							case 3:
								transformBlock(pos.offset(-dx, dy, -dz), state, fuzzY, netherTransform);
								break;
							case 4:
								transformBlock(pos.offset(dx, -dy, dz), state, fuzzY, netherTransform);
								break;
							case 5:
								transformBlock(pos.offset(dx, -dy, -dz), state, fuzzY, netherTransform);
								break;
							case 6:
								transformBlock(pos.offset(-dx, -dy, dz), state, fuzzY, netherTransform);
								break;
							case 7:
								transformBlock(pos.offset(-dx, -dy, -dz), state, fuzzY, netherTransform);
								break;
						}
					}
				}
			}
		}
	}

	private void transformBlock(BlockPos pos, BlockState state, int fuzz, boolean netherTransform) {
		BlockState stateThere = level.getBlockState(pos);

		if (stateThere.getBlock() != Blocks.AIR && (stateThere.getBlock().is(BlockTagGenerator.CARMINITE_REACTOR_IMMUNE) || stateThere.getDestroySpeed(level, pos) == -1)) {
			// don't destroy unbreakable stuff
			return;
		}

		if (fuzz == 0 && stateThere.getBlock() != Blocks.AIR) {
			// make pop thing for original block
			level.levelEvent(2001, pos, Block.getId(stateThere));
		}

		if (netherTransform && stateThere.getBlock() != Blocks.AIR) {
			level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
			// fire on top?
			if (level.isEmptyBlock(pos.above()) && fuzz % 3 == 0) {
				level.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 3);
			}
		} else {
			level.setBlock(pos, state, 3);
		}
	}

	private void createFakeBlock(BlockPos pos, BlockState state) {
		BlockState stateThere = level.getBlockState(pos);

		if (stateThere.getBlock() != Blocks.AIR && (stateThere.getBlock().is(BlockTagGenerator.CARMINITE_REACTOR_IMMUNE) || stateThere.getDestroySpeed(level, pos) == -1)) {
			// don't destroy unbreakable stuff
			return;
		} else {
			level.setBlock(pos, state, 2);
		}
	}
}
