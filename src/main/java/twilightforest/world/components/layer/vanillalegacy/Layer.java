package twilightforest.world.components.layer.vanillalegacy;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import twilightforest.world.components.layer.vanillalegacy.area.AreaFactory;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;

public class Layer {
	public final LazyArea area;

	public Layer(AreaFactory<LazyArea> p_76714_) {
		this.area = p_76714_.make();
	}

	public Biome get(Registry<Biome> p_76716_, int p_76717_, int p_76718_) {
		int i = this.area.get(p_76717_, p_76718_);
		Biome biome = p_76716_.byId(i);
		if (biome == null) {
			Util.logAndPauseIfInIde("Unknown biome id: " + i);
			return p_76716_.get(Biomes.PLAINS);
		} else {
			return biome;
		}

	}
}
