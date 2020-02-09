package twilightforest.biomes;

import net.minecraft.util.ResourceLocation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class TFBiomeDarkForestCenter extends TFBiomeDarkForest {

	public TFBiomeDarkForestCenter(Builder props) {
		super(props);
	}

	@Override
	public int getGrassColorAt(double p_225528_1_, double p_225528_3_) {
		double d0 = INFO_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D); //TODO: Check
		return d0 < -0.2D ? 0x667540 : 0x554114;
	}

	@Override
	public int getFoliageColor() {
		double d0 = INFO_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D); //TODO: Check
		return d0 < -0.1D ? 0xf9821e : 0xe94e14;
	}

	@Override
	protected ResourceLocation[] getRequiredAdvancements() {
		return new ResourceLocation[]{ TwilightForestMod.prefix("progress_knights") };
	}


	@Override
	protected TFFeature getContainedFeature() {
		return TFFeature.DARK_TOWER;
	}
}
