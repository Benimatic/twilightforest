package twilightforest.compat;

import blusunrize.immersiveengineering.api.shader.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.util.TriConsumer;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.TFClientEvents;
import twilightforest.client.shader.ShaderHelper;
import twilightforest.client.shader.ShaderUniform;

import java.util.List;
import java.util.function.IntConsumer;

public class IEShaderRegister {
    // Layer Constants
    private static final ShaderCase.ShaderLayer nullLayer = new ShaderCase.ShaderLayer( null, 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredRevolverLayer  = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_uncoloured"          ), 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredChemthrowLayer = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_uncoloured"           ), 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredDrillLayer     = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_uncoloured"          ), 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredRailgunLayer   = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/railgun_uncoloured"               ), 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredShieldLayer    = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/shield_uncoloured"                ), 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredMinecartLayer  = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_uncoloured.png"), 0xFF_FF_FF_FF);
    private static final ShaderCase.ShaderLayer uncoloredBalloonLayer   = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "blocks/shaders/balloon_uncoloured"              ), 0xFF_FF_FF_FF);

    // Shader Constants
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    private static final ResourceLocation TEXTURE_STARS = new ResourceLocation("textures/entity/end_portal.png");

    private static final TriConsumer<IntConsumer, Boolean, Float> TWILIGHT_TRICONSUMER = (shaderCallback, pre, partialTick) -> {
        if (pre) {
            ShaderHelper.useShader(ShaderHelper.twilightSkyShader, shaderCallback);

            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            MINECRAFT.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            OpenGlHelper.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            MINECRAFT.getTextureManager().bindTexture(TEXTURE_STARS);
        } else {
            ShaderHelper.releaseShader();

            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            MINECRAFT.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        }
    };

    // Registering
    static final List<ShaderRegistry.ShaderRegistryEntry> SHADERS;

    static {
        EnumRarity rarity = TwilightForestMod.getRarity();

        if (rarity != EnumRarity.EPIC)
            ShaderRegistry.rarityWeightMap.put(rarity, 1);

        SHADERS = ImmutableList.of( // MAIN COLOR, MINOR COLOR (EDGES), SECONDARY COLOR (GRIP, etc)
                ShaderRegistry.registerShader("Twilight"      , "0", rarity, 0xFF_00_AA_00, 0xFF_28_25_3F, 0xFF_4C_64_5B, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest").setCrateLoot(false).setInLowerBags(false),

                ShaderRegistry.registerShader("Snakestone"    , "0", rarity, 0xFF_80_80_80, 0xFF_80_80_80, 0xFF_80_80_80, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Naga"          , "0", rarity, 0xFF_32_5D_25, 0xFF_17_29_11, 0xFF_A5_D4_16, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "naga").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Lich"          , "0", rarity, 0xFF_DF_D9_CC, 0xFF_C3_9C_00, 0xFF_3A_04_75, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "lich").setCrateLoot(false).setInLowerBags(false),

                ShaderRegistry.registerShader("Mazestone"     , "0", rarity, 0xFF_70_7B_70, 0xFF_8E_99_8E, 0xFF_65_6E_65, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "mazestone").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Minoshroom"    , "0", rarity, 0xFF_A8_10_12, 0xFF_B3_B3_B3, 0xFF_33_EB_CB, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "minoshroom").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Hydra"         , "0", rarity, 0xFF_14_29_40, 0xFF_F1_0A_92, 0xFF_29_80_6B, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "hydra").setCrateLoot(false).setInLowerBags(false),

                ShaderRegistry.registerShader("Underbrick"    , "0", rarity, 0xFF_85_68_45, 0xFF_76_7F_76, 0xFF_61_4D_33, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "underbrick").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Knight Phantom", "0", rarity, 0xFF_40_6D_05, 0xFF_36_35_34, 0xFF_7A_5C_49, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "knight_phantom").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Towerwood"     , "0", rarity, 0xFF_83_5A_35, 0xFF_F5_DA_93, 0xFF_A6_65_3A, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "towerwood").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Ur-Ghast"      , "0", rarity, 0xFF_F9_F9_F9, 0xFF_9A_37_37, 0xFF_56_56_56, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "ur-ghast").setCrateLoot(false).setInLowerBags(false),

                ShaderRegistry.registerShader("Alpha Yeti"    , "0", rarity, 0xFF_FC_FC_FC, 0xFF_4A_80_CE, 0xFF_25_3F_66, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "alpha_yeti").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Auroralized"   , "0", rarity, 0xFF_80_80_80, 0xFF_80_80_80, 0xFF_80_80_80, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Snow Queen"    , "0", rarity, 0xFF_DC_FB_FF, 0xFF_C3_9C_00, 0xFF_03_05_89, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "snow_queen").setCrateLoot(false).setInLowerBags(false),

                ShaderRegistry.registerShader("Knightly"      , "0", rarity, 0xFF_E7_FC_CD, 0xFF_4D_4C_4B, 0xFF_80_8C_72, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "knightly").setCrateLoot(false).setInLowerBags(false),
                ShaderRegistry.registerShader("Fiery"         , "0", rarity, 0xFF_19_13_13, 0xFF_FD_D4_5D, 0xFF_77_35_11, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "fiery").setCrateLoot(false).setInLowerBags(false),

                ShaderRegistry.registerShader("Final Castle"  , "0", rarity, 0xFF_EC_EA_E6, 0xFF_00_FF_FF, 0xFF_C8_BB_BC, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Final Castle", "finalcastle").setCrateLoot(false).setInLowerBags(false)
        );

        // ShaderCase Overrides

        registerShaderCaseRevolver("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, 0xFF_FF_FF_FF, rarity,
                new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_1_4" ), 0xFF_28_25_3F),
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_1_4" ), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );

        registerShaderCaseChemthrower("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, rarity,
                new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_4"), 0xFF_28_25_3F),
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_4"), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );

        registerShaderCaseDrill("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, rarity,
                new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_1_4"), 0xFF_28_25_3F),
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_1_4"), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );

        registerShaderCaseRailgun("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, rarity,
                new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/railgun_1_4"), 0xFF_28_25_3F),
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/railgun_1_4"), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );

        registerShaderCaseShield("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/shield_1_4"), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );

        registerShaderCaseMinecart("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, rarity,
                new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_5.png"), 0xFF_28_25_3F),
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_4.png"), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );

        registerShaderCaseBalloon("Twilight", 0xFF_00_AA_00, 0xFF_4C_64_5B, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "blocks/shaders/balloon_1_4"), 0xFF_FF_FF_FF, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS)
        );
    }

    // Shaderizing!
    private static class ShaderConsumerShaderLayer extends ShaderCase.DynamicShaderLayer {
        private final TriConsumer<IntConsumer, Boolean, Float> render;
        private final IntConsumer shaderCallback;

        ShaderConsumerShaderLayer(ResourceLocation texture, int colour, TriConsumer<IntConsumer, Boolean, Float> render, ShaderUniform... shaderParams) {
            super(texture, colour);
            this.render = render;

            shaderCallback = shader -> { for(ShaderUniform param: shaderParams) { param.assignUniform(shader); } };
        }

        @Override
        public void modifyRender(boolean pre, float partialTick) {
            this.render.accept(shaderCallback, pre, partialTick);
        }
    }

    private static void registerWithShaders(String name, String type, int mainColor, int secondaryColor, int gripColor, int bladeColor, EnumRarity rarity, TriConsumer<IntConsumer, Boolean, Float> render, ShaderUniform... shaderParams) {
        registerShaderCaseRevolver( name, mainColor, gripColor, bladeColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_1_" + type ), 0xFF_FF_FF_FF, render, shaderParams)
        );

        registerShaderCaseChemthrower( name, mainColor, gripColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_" + type ), 0xFF_FF_FF_FF, render, shaderParams)
        );

        registerShaderCaseDrill( name, mainColor, gripColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_1_" + type ), 0xFF_FF_FF_FF, render, shaderParams)
        );

        registerShaderCaseRailgun( name, mainColor, gripColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/railgun_1_" + type ), 0xFF_FF_FF_FF, render, shaderParams)
        );

        registerShaderCaseShield( name, mainColor, gripColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/shield_1_" + type ), 0xFF_FF_FF_FF, render, shaderParams)
        );

        registerShaderCaseMinecart( name, mainColor, gripColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_" + type + ".png"), 0xFF_FF_FF_FF, render, shaderParams)
        );

        registerShaderCaseBalloon( name, mainColor, gripColor, rarity,
                new ShaderConsumerShaderLayer( new ResourceLocation("immersiveengineering", "blocks/shaders/balloon_1_" + type ), 0xFF_FF_FF_FF, render, shaderParams)
        );
    }

    // Shader Case Registration helpers

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseRevolver registerShaderCaseRevolver(String name, int mainColor, int gripColor, int bladeColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseRevolver(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_grip"), gripColor),
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_0"   ), mainColor),
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_0"   ), bladeColor)
                ).add(additionalLayers).add(uncoloredRevolverLayer).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseChemthrower registerShaderCaseChemthrower(String name, int mainColor, int gripColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseChemthrower(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_0"  ), gripColor),
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_0"  ), mainColor)
                ).add(additionalLayers).add(uncoloredChemthrowLayer).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseDrill registerShaderCaseDrill(String name, int mainColor, int gripColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseDrill(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_0"  ), gripColor),
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_0"  ), mainColor)
                ).add(additionalLayers).add(uncoloredDrillLayer).add(nullLayer).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseRailgun registerShaderCaseRailgun(String name, int mainColor, int gripColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseRailgun(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/railgun_0"  ), gripColor),
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/railgun_0"  ), mainColor)
                ).add(uncoloredRailgunLayer).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseShield registerShaderCaseShield(String name, int mainColor, int gripColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseShield(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/shield_0"  ), gripColor),
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "items/shaders/shield_0"  ), mainColor)
                ).add(additionalLayers).add(uncoloredShieldLayer).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseMinecart registerShaderCaseMinecart(String name, int bodyColor, int secondaryColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseMinecart(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_0.png"   ), bodyColor),
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_0.png" ), secondaryColor)
                ).add(additionalLayers).add(uncoloredMinecartLayer).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseBalloon registerShaderCaseBalloon(String name, int mainColor, int gripColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseBalloon(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "blocks/shaders/balloon_0" ), gripColor),
                        new ShaderCase.ShaderLayer   ( new ResourceLocation("immersiveengineering", "blocks/shaders/balloon_0" ), mainColor)
                ).add(additionalLayers).add(uncoloredBalloonLayer).build()), rarity);
    }
}
