package twilightforest;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import twilightforest.world.TFWorldChunkManager;


public class TFMagicMapData extends MapData
{

    private static final int FEATURE_DATA_BYTE = 18;
	public List<Vec4b> featuresVisibleOnMap = new ArrayList<>();
	
    public TFMagicMapData(String par1Str)
    {
        super(par1Str);
    }
    
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		
		byte[] featureStorage = par1NBTTagCompound.getByteArray("features");
		if (featureStorage.length > 0) {
			this.updateMPMapData(featureStorage);
		}
//		else {
//			System.out.println("Can't find feature storage for " + this.mapName);
//		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound = super.writeToNBT(par1NBTTagCompound);
		
		if (this.featuresVisibleOnMap.size() > 0) {
			byte[] featureStorage = makeFeatureStorageArray();
			par1NBTTagCompound.setByteArray("features", featureStorage);
		}

        return par1NBTTagCompound;
	}


	/**
     * Adds a twilight forest feature to the map.
     */
    public void addFeatureToMap(TFFeature feature, int x, int z) {
        byte relativeX = (byte) ((x - this.xCenter) >> this.scale);
        byte relativeZ = (byte) ((z - this.zCenter) >> this.scale);
        byte rangeX = 64;
        byte rangeY = 64;

        if (relativeX >= (-rangeX) && relativeZ >= (-rangeY) && relativeX <= rangeX && relativeZ <= rangeY)
        {
            byte markerIcon = (byte) feature.featureID;
            byte mapX = (byte)(relativeX << 1);
            byte mapZ = (byte)(relativeZ << 1);
            byte mapRotation = 8;

            boolean featureFound = false;
            
            // look for a feature already at those coordinates
            for (Vec4b existingCoord : featuresVisibleOnMap) {
            	if (existingCoord.getX() == mapX && existingCoord.getY() == mapZ) {
            		featureFound = true;
            	}
            }
            
            // if we didn't find it, add it
            if (!featureFound)
            {
                this.featuresVisibleOnMap.add(new Vec4b(markerIcon, mapX, mapZ, mapRotation));
            }
        }
    }
    
    /**
     * Checks existing features against the feature cache and removes/changes wrong ones.  Does not add features.
     */
    public void checkExistingFeatures(World world)
    {
    	ArrayList<Vec4b> toRemove = null;

            for (Vec4b coord : featuresVisibleOnMap)
            {

            	//System.out.printf("Existing feature at %d, %d.\n", coord.centerX,  coord.centerZ);
            	
            	int worldX = (coord.centerX << this.scale - 1) + this.xCenter;
            	int worldZ = (coord.centerZ << this.scale - 1) + this.zCenter;

         		if (world != null && world.getBiomeProvider() instanceof TFWorldChunkManager)
        		{
        			TFWorldChunkManager tfManager  = (TFWorldChunkManager) world.getBiomeProvider();
        			coord.iconSize = (byte) tfManager.getFeatureID(worldX, worldZ, world);
        			
        			if (coord.getType() == 0)
        			{
        				if (toRemove == null)
        				{
        					toRemove = new ArrayList<>();
        				}
        				toRemove.add(coord);
        				
        				//System.out.println("Removing bad mapcoord " + coord + " from " + worldX + ", " + worldZ);
        			}
        		}
            }
            
            if (toRemove != null)
            {
            	featuresVisibleOnMap.removeAll(toRemove);
            }
    }

    /**
     * Updates the client's map with information from other players in MP
     */
    @Override
    public void updateMPMapData(byte[] par1ArrayOfByte)
    {
        if (par1ArrayOfByte[0] == FEATURE_DATA_BYTE)
        {
    		// this is feature data, we can handle that directly
            this.featuresVisibleOnMap.clear();

            for (int i = 0; i < (par1ArrayOfByte.length - 1) / 3; ++i)
            {
                byte markerIcon = par1ArrayOfByte[i * 3 + 1];
                byte mapX = par1ArrayOfByte[i * 3 + 2];
                byte mapZ = par1ArrayOfByte[i * 3 + 3];
                byte mapRotation = 8;
                this.featuresVisibleOnMap.add(new Vec4b(markerIcon, mapX, mapZ, mapRotation));
            }
        }
        else
        {
            super.updateMPMapData(par1ArrayOfByte);
        }
    }
    
    /**
     * This makes a byte array that we store our feature data in to send with map update packets
     */
    public byte[] makeFeatureStorageArray()
    {
        byte[] storage = new byte[this.featuresVisibleOnMap.size() * 3 + 1];
        storage[0] = FEATURE_DATA_BYTE;

        for (int i = 0; i < featuresVisibleOnMap.size(); ++i)
        {
            Vec4b featureCoord = this.featuresVisibleOnMap.get(i);
            storage[i * 3 + 1] = (featureCoord.getType());
            storage[i * 3 + 2] = featureCoord.getX();
            storage[i * 3 + 3] = featureCoord.getY();
        }
        
        return storage;
    }

}
