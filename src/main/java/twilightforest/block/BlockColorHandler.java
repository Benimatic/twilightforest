package twilightforest.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.block.enums.FireJetVariant;
import twilightforest.block.enums.MagicWoodVariant;
import twilightforest.block.enums.TowerWoodVariant;

import javax.annotation.Nullable;
import java.awt.*;

@SideOnly(Side.CLIENT)
public final class BlockColorHandler {

    public static void init() {
        BlockColors colors = Minecraft.getMinecraft().getBlockColors();
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                int red = 0;
                int green = 0;
                int blue = 0;


                // aurora fade
                red = 16;

                blue = pos.getX() * 12 + pos.getZ() * 6;
                if ((blue & 256) != 0){
                    blue = 255 - (blue & 255);
                }
                blue ^= 255;
                blue &= 255;


                green = pos.getX() * 4 + pos.getZ() * 8;
                if ((green & 256) != 0){
                    green = 255 - (green & 255);
                }
                green &= 255;

                // don't let things get black
                if (green + blue < 128){
                    green = 128 - blue;
                }

                return red << 16 | blue << 8 | green;
            }
        }, TFBlocks.auroraBlock);
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                int red = 0;
                int green = 0;
                int blue = 0;

                int normalColor = Minecraft.getMinecraft().getBlockColors().colorMultiplier(TFBlocks.auroraBlock.getDefaultState(), worldIn, pos, tintIndex);;

                red = (normalColor >> 16) & 255;
                blue = normalColor & 255;
                green = (normalColor >> 8) & 255;

                float[] hsb = Color.RGBtoHSB(red, blue, green, null);

                return Color.HSBtoRGB(hsb[0], hsb[1] * 0.5F, Math.min(hsb[2] + 0.4F, 0.9F));
            }
        }, TFBlocks.auroraPillar, TFBlocks.auroraSlab, TFBlocks.auroraDoubleSlab);
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                if (worldIn == null || pos == null) {
                    return ColorizerFoliage.getFoliageColorBasic();
                }

                int red = 0;
                int grn = 0;
                int blu = 0;

                for (int dz = -1; dz <= 1; ++dz)
                {
                    for (int dx = -1; dx <= 1; ++dx)
                    {
                        int i2 = worldIn.getBiomeGenForCoords(pos.add(dx, 0, dz)).getFoliageColorAtPos(pos.add(dx, 0, dz));
                        red += (i2 & 16711680) >> 16;
                        grn += (i2 & 65280) >> 8;
                        blu += i2 & 255;
                    }
                }

                return (red / 9 & 255) << 16 | (grn / 9 & 255) << 8 | blu / 9 & 255;
            }
        }, TFBlocks.darkleaves, TFBlocks.giantLeaves);
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                FireJetVariant variant = state.getValue(BlockTFFireJet.VARIANT);

                if (worldIn == null || pos == null
                        || variant == FireJetVariant.ENCASED_SMOKER_OFF || variant == FireJetVariant.ENCASED_SMOKER_ON
                        || variant == FireJetVariant.ENCASED_JET_IDLE || variant == FireJetVariant.ENCASED_JET_POPPING || variant == FireJetVariant.ENCASED_JET_FLAME)
                {
                    return -1;
                }
                else
                {
                    int red = 0;
                    int grn = 0;
                    int blu = 0;

                    for (int var8 = -1; var8 <= 1; ++var8)
                    {
                        for (int var9 = -1; var9 <= 1; ++var9)
                        {
                            int biomeColor = worldIn.getBiomeGenForCoords(pos.add(var9, 0, var8)).getGrassColorAtPos(pos.add(var9, 0, var8));
                            red += (biomeColor & 16711680) >> 16;
                            grn += (biomeColor & 65280) >> 8;
                            blu += biomeColor & 255;
                        }
                    }

                    return (red / 9 & 255) << 16 | (grn / 9 & 255) << 8 | blu / 9 & 255;
                }
            }
        }, TFBlocks.fireJet);
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return 0x208030;
            }
        }, TFBlocks.hugeLilyPad);
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                if (worldIn == null || pos == null) {
                    switch (state.getValue(BlockTFMagicLog.VARIANT)) {
                        case TIME :
                            return 106 << 16 | 156 << 8 | 23;
                        case TRANS :
                            return 108 << 16 | 204 << 8 | 234;
                        case MINE :
                            return 252 << 16 | 241 << 8 | 68;
                        case SORT :
                            return 54 << 16 | 76 << 8 | 3;
                        default: return -1;
                    }
                } else {
                    int red = -1, green = -1, blue = -1;
                    MagicWoodVariant leafType = state.getValue(BlockTFMagicLog.VARIANT);

                    if (leafType == MagicWoodVariant.TIME)
                    {
                        int fade = pos.getX() * 16 + pos.getY() * 16 + pos.getZ() * 16;
                        if ((fade & 256) != 0)
                        {
                            fade = 255 - (fade & 255);
                        }
                        fade &= 255;

                        float spring = (255 - fade) / 255F;
                        float fall = fade / 255F;

                        red = (int) (spring * 106 + fall * 251);
                        green = (int) (spring * 156 + fall * 108);
                        blue = (int) (spring * 23 + fall * 27);
                    }
                    else if (leafType == MagicWoodVariant.MINE)
                    {
                        int fade = pos.getX() * 31 + pos.getY() * 33 + pos.getZ() * 32;
                        if ((fade & 256) != 0)
                        {
                            fade = 255 - (fade & 255);
                        }
                        fade &= 255;

                        float spring = (255 - fade) / 255F;
                        float fall = fade / 255F;

                        red = (int) (spring * 252 + fall * 237);
                        green = (int) (spring * 241 + fall * 172);
                        blue = (int) (spring * 68 + fall * 9);
                    }
                    else if (leafType == MagicWoodVariant.TRANS)
                    {
                        int fade = pos.getX() * 27 + pos.getY() * 63 + pos.getZ() * 39;
                        if ((fade & 256) != 0)
                        {
                            fade = 255 - (fade & 255);
                        }
                        fade &= 255;

                        float spring = (255 - fade) / 255F;
                        float fall = fade / 255F;

                        red = (int) (spring * 108 + fall * 96);
                        green = (int) (spring * 204 + fall * 107);
                        blue = (int) (spring * 234 + fall * 121);
                    }
                    else if (leafType == MagicWoodVariant.SORT)
                    {
                        int fade = pos.getX() * 63 + pos.getY() * 63 + pos.getZ() * 63;
                        if ((fade & 256) != 0)
                        {
                            fade = 255 - (fade & 255);
                        }
                        fade &= 255;

                        float spring = (255 - fade) / 255F;
                        float fall = fade / 255F;

                        red = (int) (spring * 54 + fall * 168);
                        green = (int) (spring * 76 + fall * 199);
                        blue = (int) (spring * 3 + fall * 43);
                    }

                    return red << 16 | green << 8 | blue;
                }
            }
        }, TFBlocks.magicLeaves);
        colors.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                if (worldIn == null || pos == null || state.getValue(BlockTFTowerWood.VARIANT) == TowerWoodVariant.ENCASED) {
                    return -1;
                } else {
                    // stripes!
                    int value = pos.getX() * 31 + pos.getY() * 15 + pos.getZ() * 33;
                    if ((value & 256) != 0)
                    {
                        value = 255 - (value & 255);
                    }
                    value &= 255;
                    value = value >> 1;
                    value |= 128;

                    return value << 16 | value << 8 | value;
                }
            }
        });
    }

    private BlockColorHandler() {}
}
