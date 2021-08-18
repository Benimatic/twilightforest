package twilightforest.structures;

import twilightforest.TFFeature;

public interface TwilightFeature {
    @Deprecated // For Legacy Class usage
    default void setFeature(TFFeature type) {

    }

    TFFeature getFeatureType();
}
