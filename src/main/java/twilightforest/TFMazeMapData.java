package twilightforest;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.MapData;


public class TFMazeMapData extends MapData
{
	
    public int yCenter;


    public TFMazeMapData(String par1Str)
    {
        super(par1Str);
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		
		this.yCenter = par1NBTTagCompound.getInteger("yCenter");
	}

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagCompound ret = super.writeToNBT(par1NBTTagCompound);
        ret.setInteger("yCenter", this.yCenter);
		return ret;

	}    

}
