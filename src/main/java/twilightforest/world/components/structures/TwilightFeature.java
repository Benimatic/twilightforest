package twilightforest.world.components.structures;

import twilightforest.world.registration.TFFeature;

@Deprecated
public interface TwilightFeature {
    @Deprecated // For Legacy usage
    default void setFeature(TFFeature type) {
        // Legacy hook, expect no-op
    }

    @Deprecated // Remove this whole class - These TFFeature labels inside StructComps serve no purpose
    TFFeature getFeatureType();
}
