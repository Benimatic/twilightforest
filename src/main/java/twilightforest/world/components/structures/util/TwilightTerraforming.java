package twilightforest.world.components.structures.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public interface TwilightTerraforming {
    boolean isSurfaceDecorationsAllowed();

    boolean isUndergroundDecoAllowed();

    @Deprecated // Method exists for the logic transition. Ultimately this logic should be handled by vanilla.
    boolean shouldAdjustToTerrain();

    record TerraformConfig(boolean surfaceDecorations, boolean undergroundDecorations, boolean adjustElevation) {
        public static MapCodec<TerraformConfig> FLAT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.BOOL.fieldOf("allow_biome_surface_decorations").forGetter(TerraformConfig::surfaceDecorations),
                Codec.BOOL.fieldOf("allow_biome_underground_decorations").forGetter(TerraformConfig::undergroundDecorations),
                Codec.BOOL.fieldOf("adjust_structure_elevation").forGetter(TerraformConfig::adjustElevation)
        ).apply(instance, TerraformConfig::new));

        public static Codec<TerraformConfig> CODEC = FLAT_CODEC.codec();
    }
}
