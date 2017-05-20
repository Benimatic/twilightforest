package twilightforest;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import twilightforest.world.TFBiomeProvider;

import java.util.ArrayList;
import java.util.List;

public class TFMagicMapData extends MapData
{
	public final List<Vec4b> featuresVisibleOnMap = new ArrayList<>();

    public TFMagicMapData(String name)
    {
        super(name);
    }
    
	@Override
	public void readFromNBT(NBTTagCompound cmp) {
		super.readFromNBT(cmp);
		
		byte[] featureStorage = cmp.getByteArray("features");
		if (featureStorage.length > 0) {
			this.deserializeFeatures(featureStorage);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound cmp) {
		cmp = super.writeToNBT(cmp);
		
		if (this.featuresVisibleOnMap.size() > 0) {
			cmp.setByteArray("features", serializeFeatures());
		}

        return cmp;
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

            // look for a feature already at those coordinates
            for (Vec4b existingCoord : featuresVisibleOnMap) {
            	if (existingCoord.getX() == mapX && existingCoord.getY() == mapZ) {
            		return;
            	}
            }
            
            // if we didn't find it, add it
            this.featuresVisibleOnMap.add(new Vec4b(markerIcon, mapX, mapZ, mapRotation));
        }
    }
    
    /**
     * Checks existing features against the feature cache changes wrong ones
     */
    public void checkExistingFeatures(World world)
    {
    	List<Vec4b> toRemove = new ArrayList<>();
        List<Vec4b> toAdd = new ArrayList<>();

        for (Vec4b coord : featuresVisibleOnMap)
        {
            int worldX = (coord.getX() << this.scale - 1) + this.xCenter;
            int worldZ = (coord.getY() << this.scale - 1) + this.zCenter;

            if (world != null && world.getBiomeProvider() instanceof TFBiomeProvider)
            {
                TFBiomeProvider provider  = (TFBiomeProvider) world.getBiomeProvider();

                byte trueId = (byte) provider.getFeatureID(worldX, worldZ, world);
                if (coord.getType() != trueId)
                {
                    toRemove.add(coord);
                    toAdd.add(new Vec4b(trueId, coord.getX(), coord.getY(), coord.getRotation()));
                }
            }
        }

        featuresVisibleOnMap.removeAll(toRemove);
        featuresVisibleOnMap.addAll(toAdd);
    }

    public void deserializeFeatures(byte[] arr)
    {
        this.featuresVisibleOnMap.clear();

        for (int i = 0; i < arr.length / 3; ++i)
        {
            byte markerIcon = arr[i * 3];
            byte mapX = arr[i * 3 + 1];
            byte mapZ = arr[i * 3 + 2];
            byte mapRotation = 8;
            this.featuresVisibleOnMap.add(new Vec4b(markerIcon, mapX, mapZ, mapRotation));
        }
    }

    public byte[] serializeFeatures()
    {
        byte[] storage = new byte[this.featuresVisibleOnMap.size() * 3];

        for (int i = 0; i < featuresVisibleOnMap.size(); ++i)
        {
            Vec4b featureCoord = this.featuresVisibleOnMap.get(i);
            storage[i * 3] = featureCoord.getType();
            storage[i * 3 + 1] = featureCoord.getX();
            storage[i * 3 + 2] = featureCoord.getY();
        }

        return storage;
    }
}
