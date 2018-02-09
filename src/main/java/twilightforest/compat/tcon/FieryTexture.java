package twilightforest.compat.tcon;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;

import java.util.Arrays;
import java.util.function.Function;

/*
From boni:

well, you have frame data in the texture

you need to plop in more than one.
my default code only adds one

https://github.com/SlimeKnights/TinkersConstruct/blob/1.12/src/main/java/slimeknights/tconstruct/library/client/texture/AbstractColoredTexture.java#L63-L67

there is the call that you get in my stuff. it's then added to the framedata
you need multiple frames for animation.
and the metadata for it
no idea on the metadat, i use already existing blocktextures/ with animations for a reason ;P
 */

/* TODO
1 trace inbound TCon tool tex
 -> translucent pixel constitutes full opacity
1.1 find raw outline
   -> save outside transparent pixels into hashmap of transparent pixels
   -> definition of outside pixel: a pixel that touches the texture borders
   -> Ignore potential inner lines
1.2
 */

public class FieryTexture extends AbstractColoredTexture {
    private final ResourceLocation textureIn;
    private int minimumColor;
    private int maximumColor;
    int[] colors;

    FieryTexture(ResourceLocation textureIn, String spriteName) {
        super(textureIn, spriteName);

        this.textureIn = textureIn;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        this.framesTextureData = Lists.newArrayList();
        this.frameCounter = 0;
        this.tickCounter = 0;

        TextureAtlasSprite baseTexture = textureGetter.apply(this.textureIn);
        if(baseTexture == null || baseTexture.getFrameCount() <= 0) {
            this.width = 1; // needed so we don't crash
            this.height = 1;
            // failure
            return false;
        }

        // copy data from base texture - we have the same properties/sizes as the base

        this.copyFrom(baseTexture);
        // todo: do this for every frame for animated textures and remove the old animation classes
        // get the base texture to work on - aka copy the texture data into this texture
        int[][] data;
        int[][] original = baseTexture.getFrameTextureData(0);
        data = new int[original.length][];
        data[0] = Arrays.copyOf(original[0], original[0].length);

        // do the transformation on the data for mipmap level 0
        // looks like other mipmaps are generated correctly
        processData(data[0]);

        if(this.framesTextureData.isEmpty()) {
            this.framesTextureData.add(data);
        }

        return false;
    }

    @Override
    protected void preProcess(int[] data) {
        this.minimumColor = 255;
        this.maximumColor = 0;

        for (int pixel : data) {
            if (RenderUtil.alpha(pixel) == 0) continue;

            minimumColor = Math.min(minimumColor, getPerceptualBrightness(pixel));
            maximumColor = Math.max(maximumColor, getPerceptualBrightness(pixel));
        }

        if (minimumColor > maximumColor) {
            int accumulator = minimumColor;
            minimumColor = maximumColor;
            maximumColor = accumulator;
        }

        this.minimumColor = RenderUtil.compose(minimumColor, minimumColor, minimumColor, 255);
        this.maximumColor = RenderUtil.compose(maximumColor, maximumColor, maximumColor, 255);
    }

    @Override
    protected int colorPixel(int pixel, int pxCoord) {
        return 0;
    }
}
