package twilightforest.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.BlockTFTowerTranslucent;
import twilightforest.block.TFBlocks;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTFTowerBuilder extends TileEntity
{
	private static final int RANGE = 16;

	int ticksRunning = 0;
	int blockedCounter = 0;
	int ticksStopped = 0;
	
	public boolean makingBlocks = false;
	
	int blocksMade = 0;
	
	protected ChunkCoordinates lastBlockCoords;
	protected int nextFacing;
	
	protected EntityPlayer trackedPlayer;
	
	protected Block blockBuiltID = TFBlocks.towerTranslucent;
	protected int blockBuiltMeta = BlockTFTowerTranslucent.META_BUILT_INACTIVE;
    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }

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
		this.lastBlockCoords = new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord);
	}

    /**
     * Determines if this TileEntity requires update calls.
     * @return True if you want updateEntity() to be called, false if not
     */
	@Override
	public boolean canUpdate() {
		return true;
	}

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
	@Override
	public void updateEntity() 
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
			if (this.ticksRunning % 10 == 0 && lastBlockCoords != null && nextFacing != -1)
			{
				int nextX = lastBlockCoords.posX + Facing.offsetsXForSide[nextFacing];
				int nextY = lastBlockCoords.posY + Facing.offsetsYForSide[nextFacing];
				int nextZ = lastBlockCoords.posZ + Facing.offsetsZForSide[nextFacing];
				
				// make a block
				if (blocksMade <= RANGE && worldObj.isAirBlock(nextX, nextY, nextZ))
				{
					worldObj.setBlock(nextX, nextY, nextZ,blockBuiltID, blockBuiltMeta, 3);
					
					worldObj.playAuxSFX(1001, nextX, nextY, nextZ, 0);
					
					this.lastBlockCoords.posX = nextX;
					this.lastBlockCoords.posY = nextY;
					this.lastBlockCoords.posZ = nextZ;
					
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

	/**
	 * Are the specified destination coordinates within our build range?
	 */
	private boolean isInBounds(int nextX, int nextY, int nextZ) 
	{
		return nextX > this.xCoord - RANGE && nextX < this.xCoord + RANGE 
				&& nextY > this.yCoord - RANGE && nextY < this.yCoord + RANGE 
				&& nextZ > this.zCoord - RANGE && nextZ < this.zCoord + RANGE;
	}

	/**
	 * Which direction is the player facing, in terms of directions in java.util.Facing
	 */
	private int findNextFacing() 
	{
		if (this.trackedPlayer != null)
		{
			// check up and down
			int pitch = MathHelper.floor_double((double)(trackedPlayer.rotationPitch * 4.0F / 360.0F) + 1.5D) & 3;
			
			if (pitch == 0)
			{
				return 1;
			}
			else if (pitch == 2)
			{
				return 0;
			}
			else
			{
				int direction = MathHelper.floor_double((double)(trackedPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

				switch (direction)
				{
				case 0: // south
					return 3;
				case 1: // west
					return 4;
				case 2: // north
					return 2;
				case 3: // east
					return 5;
				}
			}
		}

		return -1;
	}

	/**
	 * Who is the closest player?  Used to find which player we should track when building
	 */
	private EntityPlayer findClosestValidPlayer() 
	{
		return worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 16);
	}
	
	
}
