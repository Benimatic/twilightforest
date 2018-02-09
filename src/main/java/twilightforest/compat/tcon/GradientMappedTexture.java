package twilightforest.compat.tcon;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;

import static net.minecraft.util.math.MathHelper.sqrt;

public class GradientMappedTexture extends AbstractColoredTexture {
    private GradientMapInfoDeserializer.GradientNode[] gradientMap;
    private boolean shouldStretchMinimumMaximum;
    private int minimumColor;
    private int maximumColor;

    GradientMappedTexture(ResourceLocation baseTextureLocation, String spriteName, boolean shouldStretchMinimumMaximum, GradientMapInfoDeserializer.GradientNode[] gradientMap) {
        super(baseTextureLocation, spriteName);
        this.shouldStretchMinimumMaximum = shouldStretchMinimumMaximum;
        this.gradientMap = gradientMap;
    }

    @Override
    protected void preProcess(int[] data) {
        if (shouldStretchMinimumMaximum) {
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
        } else {
            this.minimumColor = 0x00_00_00_FF;
            this.maximumColor = 0xFF_FF_FF_FF;
        }
    }

    @Override
    protected int colorPixel(int pixel, int pxCoord) {
        int a = RenderUtil.alpha(pixel);
        if(a == 0) {
            return pixel;
        }

        int rFrom = RenderUtil.red  (pixel);
        int gFrom = RenderUtil.green(pixel);
        int bFrom = RenderUtil.blue (pixel);

        int rTo = 0, gTo = 0, bTo = 0;

        // average it
        float gray = (rFrom + gFrom + bFrom) / (3.0f * 256.0f);

        if (gray <= gradientMap[0].node) {
            rTo = RenderUtil.red  (gradientMap[0].color);
            gTo = RenderUtil.green(gradientMap[0].color);
            bTo = RenderUtil.blue (gradientMap[0].color);
        } else if (gray >= gradientMap[gradientMap.length-1].node) {
            int i = gradientMap[gradientMap.length-1].color;

            rTo = RenderUtil.red  (gradientMap[i].color);
            gTo = RenderUtil.green(gradientMap[i].color);
            bTo = RenderUtil.blue (gradientMap[i].color);
        } else {
            for (int i = 0; i < gradientMap.length - 1; i++) {
                if (gray == gradientMap[i].node) {
                    rTo = RenderUtil.red  (gradientMap[i].color);
                    gTo = RenderUtil.green(gradientMap[i].color);
                    bTo = RenderUtil.blue (gradientMap[i].color);
                } else if (gray >= gradientMap[i].node && gray <= gradientMap[i+1].node) {
                    return getColorFromBetweenNodes(gradientMap[i+1].node - gradientMap[i].node, gradientMap[i].color, gradientMap[i+1].color, a);
                }
            }
        }

        return RenderUtil.compose(rTo, gTo, bTo, a);
    }

    private static int getColorFromBetweenNodes(float placement, int color1, int color2, int alpha) {
        int r1 = RenderUtil.red  (color1);
        int g1 = RenderUtil.green(color1);
        int b1 = RenderUtil.blue (color1);
        int r2 = RenderUtil.red  (color2);
        int g2 = RenderUtil.green(color2);
        int b2 = RenderUtil.blue (color2);

        return RenderUtil.compose(
                pickIntInBetween(placement, r1, r2),
                pickIntInBetween(placement, g1, g2),
                pickIntInBetween(placement, b1, b2), alpha);
    }

    private static int pickIntInBetween(float placement, int v1, int v2) {
        return (int) sqrt(((v1 * v1) * (1.0f - placement)) + ((v2 * v2) * placement));
    }
}
