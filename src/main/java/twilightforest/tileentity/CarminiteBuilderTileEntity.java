package twilightforest.tileentity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import twilightforest.block.BuilderBlock;
import twilightforest.block.TranslucentBuiltBlock;
import twilightforest.block.TFBlocks;
import twilightforest.enums.TowerDeviceVariant;

public class CarminiteBuilderTileEntity extends BlockEntity implements TickableBlockEntity {
	private static final int RANGE = 16;

	private int ticksRunning = 0;
	private int blockedCounter = 0;
	private int ticksStopped = 0;

	public boolean makingBlocks = false;

	private int blocksMade = 0;

	private BlockPos lastBlockCoords;

    private Player trackedPlayer;

	private BlockState blockBuiltState = TFBlocks.built_block.get().defaultBlockState().setValue(TranslucentBuiltBlock.ACTIVE, false);

	public CarminiteBuilderTileEntity() {
		super(TFTileEntities.TOWER_BUILDER.get());
	}

	/**
	 * Start building stuffs
	 */
	public void startBuilding() {
		this.makingBlocks = true;
		this.blocksMade = 0;
		this.lastBlockCoords = getBlockPos();
	}

	@Override
	public void tick() {
		if (!level.isClientSide && this.makingBlocks) {
			// if we are not tracking the nearest player, start tracking them
			if (trackedPlayer == null) {
				this.trackedPlayer = findClosestValidPlayer();
			}

			// find player facing
            Direction nextFacing = findNextFacing();

			++this.ticksRunning;

			// if we are at the half second marker, make a block and advance the block cursor
			if (this.ticksRunning % 10 == 0 && lastBlockCoords != null && nextFacing != null) {
				BlockPos nextPos = lastBlockCoords.relative(nextFacing);

				// make a block
				if (blocksMade <= RANGE && level.isEmptyBlock(nextPos)) {
					level.setBlock(nextPos, blockBuiltState, 3);

					level.levelEvent(1001, nextPos, 0);

					this.lastBlockCoords = nextPos;

					blockedCounter = 0;
					blocksMade++;
				} else {
					blockedCounter++;
				}
			}

			// if we're blocked for more than a second, shut down block making
			if (blockedCounter > 0) {
				this.makingBlocks = false;
				this.trackedPlayer = null;
				ticksStopped = 0;
			}
		} else if (!level.isClientSide && !this.makingBlocks) {
			this.trackedPlayer = null;
			if (++ticksStopped == 60) {
				// force the builder back into an inactive state
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BuilderBlock.STATE, TowerDeviceVariant.BUILDER_TIMEOUT));
				level.getBlockTicks().scheduleTick(getBlockPos(), getBlockState().getBlock(), 4);
			}
		}
	}

	private Direction findNextFacing() {
		if (this.trackedPlayer != null) {
			// check up and down
			int pitch = Mth.floor(trackedPlayer.xRot * 4.0F / 360.0F + 1.5D) & 3;

			if (pitch == 0) {
				return Direction.UP; // todo 1.9 recheck this and down
			} else if (pitch == 2) {
				return Direction.DOWN;
			} else {
				return trackedPlayer.getDirection();
			}
		}

		return null;
	}

	/**
	 * Who is the closest player?  Used to find which player we should track when building
	 */
	private Player findClosestValidPlayer() {
		return level.getNearestPlayer(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 16, false);
	}
}
