package twilightforest.world;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import twilightforest.biomes.TFBiomes;
import twilightforest.world.layer.GenLayerTF;

public class TFBiomeProvider extends BiomeProvider
{
    public TFBiomeProvider(World par1World)
    {
        getBiomesToSpawnIn().clear();
		getBiomesToSpawnIn().add(TFBiomes.twilightForest);
		getBiomesToSpawnIn().add(TFBiomes.denseTwilightForest);
		getBiomesToSpawnIn().add(TFBiomes.clearing);
		getBiomesToSpawnIn().add(TFBiomes.tfSwamp);
		getBiomesToSpawnIn().add(TFBiomes.mushrooms);

        GenLayer[] agenlayer = GenLayerTF.makeTheWorld(par1World.getSeed());
        genBiomes = agenlayer[0];
        biomeIndexLayer = agenlayer[1];
    }
}
