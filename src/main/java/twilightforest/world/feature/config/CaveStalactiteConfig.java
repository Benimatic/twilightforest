package twilightforest.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CaveStalactiteConfig implements IFeatureConfig {

	public static final Codec<CaveStalactiteConfig> caveStalactiteCodec = RecordCodecBuilder.create((instance) ->
			instance.group(
					BlockState.CODEC.fieldOf("state").forGetter((obj) -> obj.blockState),
					Codec.FLOAT.fieldOf("size_factor").orElse(0.0F).forGetter((obj) -> obj.sizeFactor),
					Codec.INT.fieldOf("max_length").orElse(-1).forGetter((obj) -> obj.maxLength),
					Codec.INT.fieldOf("min_height").orElse(-1).forGetter((obj) -> obj.minHeight),
					Codec.BOOL.fieldOf("hanging").orElse(false).forGetter((obj) -> obj.hang))
					.apply(instance, CaveStalactiteConfig::new));

	public final BlockState blockState;
	public final float sizeFactor;
	public final int maxLength;
	public final int minHeight;
	public final boolean hang;

	public CaveStalactiteConfig(BlockState state, float size, int length, int height, boolean hang) {
		this.blockState = state;
		this.sizeFactor = size;
		this.maxLength = length;
		this.minHeight = height;
		this.hang = hang;
	}
}
