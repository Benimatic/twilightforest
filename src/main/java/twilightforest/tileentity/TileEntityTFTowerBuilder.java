package twilightforest.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.BlockTFTowerTranslucent;
import twilightforest.block.TFBlocks;

public class TileEntityTFTowerBuilder extends TileEntity implements ITickable
{
	private static final int RANGE = 16;

	private int ticksRunning = 0;
	private int blockedCounter = 0;
	private int ticksStopped = 0;
	
	public boolean makingBlocks = false;
	
	private int blocksMade = 0;
	
	private BlockPos lastBlockCoords;
	private EnumFacing nextFacing;
	
	private EntityPlayer trackedPlayer;
	
	private Block blockBuiltID = TFBlocks.towerTranslucent;
	private int blockBuiltMeta = BlockTFTowerTranslucent.META_BUILT_INACTIVE;

	public TileEntityTFTowerBuilder()
	{
		//System.out.println("I made a builder!");
	}

	/**
	 * Start building stuffs
	 */
	public void startBuilding() {
		this.makingBlocks = true;
		this.blocksMade = 0;
		this.lastBlockCoords = getPos();
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote && this.makingBlocks)
		{
			// if we are not tracking the nearest player, start tracking them
			if (trackedPlayer == null)
			{
				this.trackedPlayer = findClosestValidPlayer();
			}
			
			// find player facing
			this.nextFacing = findNextFacing();
			
			++this.ticksRunning;
			
			// if we are at the half second marker, make a block and advance the block cursor
			if (this.ticksRunning % 10 == 0 && lastBlockCoords != null && nextFacing != null)
			{
				BlockPos nextPos = pos.offset(nextFacing);
				
				// make a block
				if (blocksMade <= RANGE && worldObj.isAirBlock(nextPos))
				{
					worldObj.setBlock(nextX, nextY, nextZ,blockBuiltID, blockBuiltMeta, 3);
					
					worldObj.playEvent(1001, nextPos, 0);

					this.lastBlockCoords = nextPos;

					blockedCounter = 0;
					blocksMade++;
				}
				else
				{
					blockedCounter++;
				}
			}
			
			// if we're blocked for more than a second, shut down block making
			if (blockedCounter > 0)
			{
				this.makingBlocks = false;
				this.trackedPlayer = null;
				ticksStopped = 0;
			}
		}
		else if (!worldObj.isRemote && !this.makingBlocks)
		{
			this.trackedPlayer = null;
			if (++ticksStopped == 60)
			{
				// force the builder back into an inactive state
				worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, TFBlocks.towerDevice, BlockTFTowerDevice.META_BUILDER_TIMEOUT, 3);
				worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, TFBlocks.towerDevice, 4);
			}
		}
	}

	private EnumFacing findNextFacing()
	{
		if (this.trackedPlayer != null)
		{
			// check up and down
			int pitch = MathHelper.floor_double((double)(trackedPlayer.rotationPitch * 4.0F / 360.0F) + 1.5D) & 3;
			
			if (pitch == 0)
			{
				return EnumFacing.UP; // todo 1.9 recheck this and down
			}
			else if (pitch == 2)
			{
				return EnumFacing.DOWN;
			}
			else
			{
				return trackedPlayer.getHorizontalFacing();
			}
		}

		return null;
	}

	/**
	 * Who is the closest player?  Used to find which player we should track when building
	 */
	private EntityPlayer findClosestValidPlayer() 
	{
		return worldObj.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, false);
	}
	
	
}
