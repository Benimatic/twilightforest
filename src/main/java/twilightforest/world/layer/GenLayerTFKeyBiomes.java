package twilightforest.world.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import twilightforest.biomes.TFBiomes;

import java.util.function.Supplier;

/**
 * Puts key biomes in the proper positions
 *
 * @author Ben
 */
public enum GenLayerTFKeyBiomes implements IAreaTransformer1 {
	INSTANCE;

	GenLayerTFKeyBiomes() { }

	@Override
	public int getOffsetX(int x) {
		return x | 3;
	}

	@Override
	public int getOffsetZ(int z) {
		return z | 3;
	}

	//TODO: This logic is butchered to hell and back
	@Override
	public int apply(IExtendedNoiseRandom<?> random, IArea iArea, int x, int z) {
		int dx = getOffsetX(x);
		int dz = getOffsetZ(z);
		// get offsets
		//initChunkSeed(((dx + x) | 3), ((dz + z) | 3));

		int ox = random.random(3) + 1;
		int oz = random.random(3) + 1;

		if (((dx + x) & 3) == ox && ((dz + z) & 3) == oz) {
			// determine which of the 4
			if (((dx + x) & 4) == 0) {
				if (((dz + z) & 4) == 0) {
					return Registry.BIOME.getId(getKeyBiomeFor(random, dx + x, dz + z, 0).get());
				} else {
					return Registry.BIOME.getId(getKeyBiomeFor(random, dx + x, dz + z, 1).get());
				}
			} else {
				if (((dz + z) & 4) == 0) {
					return Registry.BIOME.getId(getKeyBiomeFor(random, dx + x, dz + z, 2).get());
				} else {
					return Registry.BIOME.getId(getKeyBiomeFor(random, dx + x, dz + z, 3).get());
				}
			}

		} else {
			return iArea.getValue(x, z);
		}
	}

	/**
	 * Determine which map "region" the specified points are in.  Assign the 0-3 of the index to the key biomes based on that region.
	 */
	private Supplier<Biome> getKeyBiomeFor(IExtendedNoiseRandom<?> random, int mapX, int mapZ, int index) {
		int regionX = (mapX + 4) >> 3;
		int regionZ = (mapZ + 4) >> 3;

		//this.initChunkSeed(regionX, regionZ);
		int offset = random.random(4);

		// do we need to shuffle this better?
		// the current version just "rotates" the 4 key biomes
		switch ((index + offset) & 0b11) {
			case 0:
			default:
				return TFBiomes.glacier;
			case 1:
				return TFBiomes.fireSwamp;
			case 2:
				return TFBiomes.darkForestCenter;
			case 3:
				return TFBiomes.highlandsCenter;
		}
	}
}
