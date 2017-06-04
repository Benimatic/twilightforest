package twilightforest.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import twilightforest.TFFeature;
import twilightforest.biomes.TFBiomes;
import twilightforest.world.layer.GenLayerTF;

public class TFBiomeProvider extends BiomeProvider
{
    public TFBiomeProvider(World par1World)
    {
        biomesToSpawnIn.clear();
        biomesToSpawnIn.add(TFBiomes.twilightForest);
        biomesToSpawnIn.add(TFBiomes.denseTwilightForest);
        biomesToSpawnIn.add(TFBiomes.clearing);
        biomesToSpawnIn.add(TFBiomes.tfSwamp);
        biomesToSpawnIn.add(TFBiomes.mushrooms);

        GenLayer[] agenlayer = GenLayerTF.makeTheWorld(par1World.getSeed());
        genBiomes = agenlayer[0];
        biomeIndexLayer = agenlayer[1];
    }

    public int getFeatureID(int mapX, int mapZ, World world)
    {
		return getFeatureAt(mapX, mapZ, world).featureID;
	}

    public TFFeature getFeatureAt(int mapX, int mapZ, World world)
    {
		return TFFeature.generateFeatureFor1Point7(mapX >> 4, mapZ >> 4, world);
    }

    public boolean isInFeatureChunk(World world, int mapX, int mapZ) {
		int chunkX = mapX >> 4;
		int chunkZ = mapZ >> 4;
		BlockPos cc = TFFeature.getNearestCenterXYZ(chunkX, chunkZ, world);
		
		return chunkX == (cc.getX() >> 4) && chunkZ == (cc.getZ() >> 4);
    }
}
