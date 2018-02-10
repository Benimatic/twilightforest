package twilightforest.compat.tcon.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;

@SuppressWarnings("WeakerAccess")
public class GradientMapInfo extends MaterialRenderInfo.AbstractMaterialRenderInfo {

    private GradientMapInfoDeserializer.GradientNode[] gradientMap;
    private boolean shouldStretchMinimumMaximum;

    public GradientMapInfo(boolean shouldStretchMinimumMaximum, GradientMapInfoDeserializer.GradientNode... gradientMap) {
        this.gradientMap = gradientMap;
        this.shouldStretchMinimumMaximum = shouldStretchMinimumMaximum;
    }

    @Override
    public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        return new GradientMappedTexture(baseTexture, location, shouldStretchMinimumMaximum, gradientMap);
    }
}
