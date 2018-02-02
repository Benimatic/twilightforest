package twilightforest.compat.tcon;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;

public class GradientMapInfo extends MaterialRenderInfo.AbstractMaterialRenderInfo {

    private GradientMapInfoDeserializer.GradientNode[] gradientMap;

    public GradientMapInfo(GradientMapInfoDeserializer.GradientNode... gradientMap) {
        this.gradientMap = gradientMap;
    }

    @Override
    public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        return new GradientMappedTexture(baseTexture, location, gradientMap);
    }
}
