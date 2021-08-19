package twilightforest.world.components.structures;

import twilightforest.world.registration.TFFeature;

public interface TwilightFeature {
    @Deprecated // For Legacy Class usage
    default void setFeature(TFFeature type) {

    }

    TFFeature getFeatureType();
}
