package twilightforest.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityTFCritter extends TileEntity {

	public TileEntityTFCritter() {
		super();
	}
    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }


    /**
     * Reads a tile entity from NBT.
     */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

    /**
     * Writes a tile entity to NBT.
     */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	}

    /**
     * Determines if this TileEntity requires update calls.
     * @return True if you want updateEntity() to be called, false if not
     */
    @Override
	public boolean canUpdate()
    {
        return true;
    }
	
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
	public void updateEntity()
    {

    }
    
    
//	/**
//	 * Fix imported tileentities from version 1.9
//	 */
//	private void fixFacing() {
//		// we get called here if our facing is -1.  Let's see what we can do.
//		//System.out.println("Trying to fix critter tile entity facing...");
//		
//		// look in all directions for a surface to face
//		if (TFBlocks.firefly.canPlaceBlockAt(worldObj, xCoord - 1, yCoord, zCoord)) {
//			setFacing(1);
//		}
//		else if (TFBlocks.firefly.canPlaceBlockAt(worldObj, xCoord + 1, yCoord, zCoord)) {
//			setFacing(2);
//		}
//		else if (TFBlocks.firefly.canPlaceBlockAt(worldObj, xCoord, yCoord, zCoord - 1)) {
//			setFacing(3);
//		}
//		else if (TFBlocks.firefly.canPlaceBlockAt(worldObj, xCoord, yCoord, zCoord + 1)) {
//			setFacing(4);
//		}
//		else if (TFBlocks.firefly.canPlaceBlockAt(worldObj, xCoord, yCoord - 1, zCoord)) {
//			setFacing(5);
//		}
//		else if (TFBlocks.firefly.canPlaceBlockAt(worldObj, xCoord, yCoord + 1, zCoord)) {
//			setFacing(6);
//		}
//
//	}

//    /**
//     * Called when you receive a TileEntityData packet for the location this
//     * TileEntity is currently in. On the client, the NetworkManager will always
//     * be the remote server. On the server, it will be whomever is responsible for 
//     * sending the packet.
//     * 
//     * @param net The NetworkManager the packet originated from 
//     * @param pkt The data packet
//     */
//	@Override
//	public void onDataPacket(NetworkManager net, Packet132TileEntityData pkt) {
////		setFacing(pkt.customParam1);
//	}
	

	
//    /**
//     * Overriden in a sign to provide the text
//     */
//    public Packet getDescriptionPacket()
//    {
//        int var1 = getFacing();
//        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
//    }
	

}
