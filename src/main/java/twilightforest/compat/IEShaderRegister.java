package twilightforest.compat;

import blusunrize.immersiveengineering.api.shader.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.util.TriConsumer;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.TFClientEvents;
import twilightforest.client.shader.ShaderHelper;
import twilightforest.client.shader.ShaderUniform;

import java.util.List;
import java.util.function.IntConsumer;

public class IEShaderRegister {
    static final List<ShaderRegistry.ShaderRegistryEntry> SHADERS;

    private static final ResourceLocation TEXTURE_STARS = new ResourceLocation("textures/entity/end_portal.png");
    private static int lastTime;

    private static final TriConsumer<IntConsumer, Boolean, Float> preShader = (shaderCallback, pre, partialTick) -> {
        if (pre) GlStateManager.scale(1.005f, 1.005f, 1.005f);
    };

    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    private static final TriConsumer<IntConsumer, Boolean, Float> renderShader = (shaderCallback, pre, partialTick) -> {
        if (pre) {
            // Uncomment to enable reloading
            if ((TFClientEvents.time % 64) == 0 && lastTime != TFClientEvents.time) {


                TwilightForestMod.LOGGER.info("Reloaded shader! " + TFClientEvents.time);
                ShaderHelper.auroraShader = ShaderHelper.createProgram("starfield.vert", "aurora.frag");

                lastTime = TFClientEvents.time;
            }

            ShaderHelper.BLOOM_FB.bindFramebuffer(false);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDepthFunc(GL11.GL_EQUAL);

            ShaderHelper.useShader(ShaderHelper.auroraShader, shaderCallback);
            MINECRAFT.getTextureManager().bindTexture(TEXTURE_STARS);
        } else {
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glEnable(GL11.GL_ALPHA_TEST);

            ShaderHelper.releaseShader();

            MINECRAFT.getFramebuffer().bindFramebuffer(false);

            GlStateManager.scale(1/1.005f, 1/1.005f, 1/1.005f);

            MINECRAFT.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        }
    };

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
        ShaderRegistry.registerShaderCase("Twilight", new ShaderCaseMinecart(
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_0.png"), 0xFF_00_AA_00),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_0.png"), 0xFF_4C_64_5B),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_5.png"), 0xFF_4C_64_5B),
                new ShaderShaderLayer     (new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_4.png"), 0xFF_FF_FF_FF, preShader),
                new ShaderShaderLayer     (new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_4.png"), 0xFF_FF_FF_FF, renderShader, ShaderHelper.COMMON_UNIFORMS),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering:textures/models/shaders/minecart_uncoloured.png"), 0xffffffff)
        ), rarity);

        // ShaderCase Overrides
        ShaderRegistry.registerShaderCase("Twilight", new ShaderCaseChemthrower(
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_0"), 0xFF_00_AA_00),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_0"), 0xFF_4C_64_5B),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_5"), 0xFF_4C_64_5B),
                new ShaderShaderLayer     (new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_4"), 0xFF_FF_FF_FF, preShader),
                new ShaderShaderLayer     (new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_1_4"), 0xFF_FF_FF_FF, renderShader, ShaderHelper.COMMON_UNIFORMS),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_uncoloured"), 0xffffffff)
        ), rarity);

        // ShaderCase Overrides
        ShaderRegistry.registerShaderCase("Twilight", new ShaderCaseRevolver(
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_grip"), 0xFF_00_AA_00),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_0"), 0xFF_28_25_3F),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_0"), 0xFF_FF_FF_FF),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_1_4"), 0xFF_4C_64_5B),
                new ShaderShaderLayer     (new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_1_4"), 0xFF_FF_FF_FF, preShader),
                new ShaderShaderLayer     (new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_1_4"), 0xFF_FF_FF_FF, renderShader, ShaderHelper.COMMON_UNIFORMS),
                new ShaderCase.ShaderLayer(new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_uncoloured"), 0xFF_FF_FF_FF)
        ), rarity);
    }

    // Shaderizing!
    private static class ShaderShaderLayer extends ShaderCase.DynamicShaderLayer {
        private final TriConsumer<IntConsumer, Boolean, Float> render;
        private final IntConsumer shaderCallback;

        ShaderShaderLayer(ResourceLocation texture, int colour, TriConsumer<IntConsumer, Boolean, Float> render, ShaderUniform... shaderParams) {
            super(texture, colour);
            this.render = render;

            shaderCallback = shader -> { for(ShaderUniform param: shaderParams) { param.assignUniform(shader); } };
        }

        @Override
        public void modifyRender(boolean pre, float partialTick) {
            this.render.accept(shaderCallback, pre, partialTick);
        }
    }
}
