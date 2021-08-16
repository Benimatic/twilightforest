package twilightforest.world.surfacebuilders;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

@Deprecated // Normally I'd [VanillaCopy] but that's not even needed anymore. Will keep in working state, cut this out in a future commit
public class TFDefaultSurfaceBuilder extends DefaultSurfaceBuilder {
	public TFDefaultSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> config) {
		super(config);
	}
}
