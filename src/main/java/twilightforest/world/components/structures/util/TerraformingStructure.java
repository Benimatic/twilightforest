package twilightforest.world.components.structures.util;

public interface TerraformingStructure {
    boolean isSurfaceDecorationsAllowed();

    boolean isUndergroundDecoAllowed();

    @Deprecated // Method exists for the logic transition. Ultimately this logic should be handled by vanilla.
    boolean shouldAdjustToTerrain();
}
