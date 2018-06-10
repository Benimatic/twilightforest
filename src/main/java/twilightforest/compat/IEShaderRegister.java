package twilightforest.compat;

import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.api.shader.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.util.TriConsumer;
import twilightforest.TwilightForestMod;
import twilightforest.client.shader.ShaderHelper;
import twilightforest.client.shader.ShaderUniform;
import twilightforest.item.TFItems;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;

public class IEShaderRegister {
    // Layer Constants
    private static final ShaderCase.ShaderLayer NULL_LAYER                = new ShaderCase.ShaderLayer( null                                                                                            , 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_REVOLVER_LAYER  = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "revolvers/shaders/revolver_uncoloured"           ), 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_CHEMTHROW_LAYER = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/chemthrower_uncoloured"            ), 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_DRILL_LAYER     = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/drill_diesel_uncoloured"           ), 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_RAILGUN_LAYER   = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/railgun_uncoloured"                ), 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_SHIELD_LAYER    = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "items/shaders/shield_uncoloured"                 ), 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_MINECART_LAYER  = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_uncoloured.png" ), 0xFFFFFFFF);
    private static final ShaderCase.ShaderLayer UNCOLORED_BALLOON_LAYER   = new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "blocks/shaders/balloon_uncoloured"               ), 0xFFFFFFFF);

    public static final ResourceLocation PROCESSED_REVOLVER_GRIP_LAYER = new ResourceLocation("twilightforest", "items/immersiveengineering/revolver_grip_processed");
    public static final ResourceLocation PROCESSED_REVOLVER_LAYER      = new ResourceLocation("twilightforest", "items/immersiveengineering/revolver_processed");
    public static final ResourceLocation PROCESSED_CHEMTHROW_LAYER     = new ResourceLocation("twilightforest", "items/immersiveengineering/chemthrower_processed");
    public static final ResourceLocation PROCESSED_DRILL_LAYER         = new ResourceLocation("twilightforest", "items/immersiveengineering/drill_processed");
    public static final ResourceLocation PROCESSED_RAILGUN_LAYER       = new ResourceLocation("twilightforest", "items/immersiveengineering/railgun_processed");
    public static final ResourceLocation PROCESSED_SHIELD_LAYER        = new ResourceLocation("twilightforest", "items/immersiveengineering/shield_processed");
    //public static final ResourceLocation PROCESSED_MINECART_LAYER      = new ResourceLocation("twilightforest", "textures/items/immersiveengineering/minecart_processed.png");
    public static final ResourceLocation PROCESSED_BALLOON_LAYER       = new ResourceLocation("twilightforest", "items/immersiveengineering/balloon_processed");

    // Shader Constants
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    private static final ResourceLocation TEXTURE_STARS = new ResourceLocation("textures/entity/end_portal.png");

    private static final TriConsumer<IntConsumer, Boolean, Float> TWILIGHT_TRICONSUMER = (shaderCallback, pre, partialTick) -> {
        if (pre) {
            ShaderHelper.useShader(ShaderHelper.twilightSkyShader, shaderCallback);

            OpenGlHelper.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            MINECRAFT.getTextureManager().bindTexture(TEXTURE_STARS);

            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            MINECRAFT.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        } else {
            ShaderHelper.releaseShader();

            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            MINECRAFT.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        }
    };

    private static final TriConsumer<IntConsumer, Boolean, Float> AURORA_TRICONSUMER = (shaderCallback, pre, partialTick) -> {
        if (pre) ShaderHelper.useShader(ShaderHelper.auroraShader, shaderCallback);
        else     ShaderHelper.releaseShader();
    };

    private final static ShaderLayerProvider LAYER_PROVIDER = ShaderCase.ShaderLayer::new;

    // Registering
    private static List<ShaderRegistry.ShaderRegistryEntry> SHADERS;

    private static IResourceManagerReloadListener shaderReloadListener;

    private static final EnumRarity RARITY = TwilightForestMod.getRarity();

    static {
        if (RARITY != EnumRarity.EPIC)
            ShaderRegistry.rarityWeightMap.put(RARITY, 1);

        IResourceManager iManager;

        // Call this a bad idea, but it works for reloadability for now
        if ((iManager = MINECRAFT.getResourceManager()) instanceof SimpleReloadableResourceManager) ((SimpleReloadableResourceManager) iManager).registerReloadListener(shaderReloadListener = (manager -> SHADERS = ImmutableList.of(
                // MAIN COLOR, MINOR COLOR (EDGES), SECONDARY COLOR (GRIP, etc)
                registerShaderCasesEngineer( "Twilight"      , "4"     , RARITY, 0xFF_4C_64_5B, 0xFF_28_25_3F, 0xFF_00_AA_00, 0xFFFFFFFF, (rl, c) -> new ShaderConsumerShaderLayer(rl, c, TWILIGHT_TRICONSUMER, ShaderHelper.STAR_UNIFORMS )).setInfo("Twilight Forest", "Twilight Forest"          , "twilightforest" ),
                registerShaderCasesEngineer( "Firefly"       , "6"     , RARITY, 0xFF_FF_FF_FF, 0xFF_FF_FF_FF, 0xFF_FF_FF_FF, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Firefly"                  , "firefly"        ),

                registerShaderCasesEngineer( "Snakestone"    , "0"     , RARITY, 0xFF_80_80_80, 0xFF_80_80_80, 0xFF_80_80_80, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Nagastone"                , "courtyard"      ),
                registerShaderCasesTwilight( "Naga"          , "scales", RARITY, 0xFF_32_5D_25, 0xFF_17_29_11, 0xFF_A5_D4_16, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Naga Boss"                , "naga"           ),
                registerShaderCasesEngineer( "Lich"          , "0"     , RARITY, 0xFF_DF_D9_CC, 0xFF_C3_9C_00, 0xFF_3A_04_75, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Twilight Lich Boss"       , "lich"           ),

                registerShaderCasesEngineer( "Mazestone"     , "0"     , RARITY, 0xFF_70_7B_70, 0xFF_8E_99_8E, 0xFF_65_6E_65, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Mazestone"                , "mazestone"      ),
                registerShaderCasesEngineer( "Minoshroom"    , "0"     , RARITY, 0xFF_A8_10_12, 0xFF_B3_B3_B3, 0xFF_33_EB_CB, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Minoshroom Miniboss"      , "minoshroom"     ),
                registerShaderCasesTwilight( "Hydra"         , "scales", RARITY, 0xFF_14_29_40, 0xFF_29_80_6B, 0xFF_F1_0A_92, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Hydra Boss"               , "hydra"          ),

                registerShaderCasesTwilight( "Underbrick"    , "scales", RARITY, 0xFF_85_68_45, 0xFF_76_7F_76, 0xFF_61_4D_33, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Underbrick"               , "underbrick"     ),
                registerShaderCasesEngineer( "Knight Phantom", "0"     , RARITY, 0xCC_40_6D_05, 0xFF_36_35_34, 0xFF_7A_5C_49, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Knight Phantom Minibosses", "knight_phantom" ),
                registerShaderCasesEngineer( "Towerwood"     , "0"     , RARITY, 0xFF_83_5A_35, 0xFF_F5_DA_93, 0xFF_A6_65_3A, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Towerwood Planks"         , "towerwood"      ),
                registerShaderCasesEngineer( "Ur-Ghast"      , "2"     , RARITY, 0xFF_F9_F9_F9, 0xFF_9A_37_37, 0xFF_56_56_56, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Ur-Ghast"                 , "ur-ghast"       ),

                registerShaderCasesEngineer( "Alpha Yeti"    , "0"     , RARITY, 0xFF_FC_FC_FC, 0xFF_4A_80_CE, 0xFF_25_3F_66, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Alpha Yeti"               , "alpha_yeti"     ),
                registerShaderCasesEngineer( "Auroralized"   , "5"     , RARITY, 0xFF_00_FF_FF, 0xFF_00_FF_00, 0xFF_00_00_FF, 0xFFFFFFFF, (rl, c) -> new ShaderConsumerShaderLayer(rl, c, AURORA_TRICONSUMER  , ShaderHelper.TIME_UNIFORM  )).setInfo("Twilight Forest", "Aurora Palace"            , "aurora"         ),
                registerShaderCasesEngineer( "Snow Queen"    , "0"     , RARITY, 0xFF_DC_FB_FF, 0xFF_C3_9C_00, 0xFF_03_05_89, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Snow Queen"               , "snow_queen"     ),

                registerShaderCasesEngineer( "Knightly"      , "0"     , RARITY, 0xFF_E7_FC_CD, 0xFF_4D_4C_4B, 0xFF_80_8C_72, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Knightly"                 , "knightly"       ),
                registerShaderCasesEngineer( "Fiery"         , "0"     , RARITY, 0xFF_19_13_13, 0xFF_FD_D4_5D, 0xFF_77_35_11, 0xFFFFFFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Fiery"                    , "fiery"          ),

                registerShaderCasesTwilight( "Final Castle"  , "scales", RARITY, 0xFF_EC_EA_E6, 0xFF_C8_BB_BC, 0xFF_00_FF_FF, 0xFF00FFFF, LAYER_PROVIDER                                                                                    ).setInfo("Twilight Forest", "Final Castle"             , "finalcastle"    )
        )));
    }

    public static List<ShaderRegistry.ShaderRegistryEntry> getAllTwilightShaders() {
        return SHADERS;
    }

    public static IResourceManagerReloadListener getShaderReloadListener() {
        return shaderReloadListener;
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

    // Register with Immersive Engineering Textures
    @SafeVarargs
    private static ShaderRegistry.ShaderRegistryEntry registerShaderCasesEngineer(String name, String overlayType, EnumRarity rarity, int colourPrimary, int colourSecondary, int colourBackground, int colourBlade, ShaderLayerProvider<? extends ShaderCase.ShaderLayer>... providers) {
        ShaderRegistry.registerShader_Item(name, rarity, colourBackground, colourPrimary, colourSecondary);

        registerShaderCaseRevolver   ( name, colourBackground, colourPrimary, colourBlade, rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "revolvers/shaders/revolver_1_"       + overlayType          ), colourSecondary, providers ));
        registerShaderCaseChemthrower( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "items/shaders/chemthrower_1_"        + overlayType          ), colourSecondary, providers ));
        registerShaderCaseDrill      ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "items/shaders/drill_diesel_1_"       + overlayType          ), colourSecondary, providers ));
        registerShaderCaseRailgun    ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "items/shaders/railgun_1_"            + overlayType          ), colourSecondary, providers ));
        registerShaderCaseShield     ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "items/shaders/shield_1_"             + overlayType          ), colourSecondary, providers ));
        registerShaderCaseMinecart   ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "textures/models/shaders/minecart_1_" + overlayType + ".png" ), colourSecondary, providers ));
        registerShaderCaseBalloon    ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "immersiveengineering", "blocks/shaders/balloon_1_"           + overlayType          ), colourSecondary, providers ));

        // Since shaders won't occur in a way we'd like them to, we should register any additional variants ourselves if we know of any
        for(ShaderRegistry.IShaderRegistryMethod method : ShaderRegistry.shaderRegistrationMethods)
            method.apply(name, overlayType, rarity, colourBackground, colourPrimary, colourSecondary, colourBlade, null, 0);

        return ShaderRegistry.shaderRegistry.get(name).setCrateLoot(false).setBagLoot(true).setInLowerBags(false).setReplicationCost(new IngredientStack(new ItemStack(TFItems.ore_meter)));
    }

    // Register with Twilight Forest Textures
    @SafeVarargs
    private static ShaderRegistry.ShaderRegistryEntry registerShaderCasesTwilight(String name, String overlayType, EnumRarity rarity, int colourPrimary, int colourSecondary, int colourBackground, int colourBlade, ShaderLayerProvider<? extends ShaderCase.ShaderLayer>... providers) {
        ShaderRegistry.registerShader_Item(name, rarity, colourBackground, colourPrimary, colourSecondary);

        registerShaderCaseRevolver   ( name, colourBackground, colourPrimary, colourBlade, rarity, provideFromProviders( new ResourceLocation( "twilightforest", "items/immersiveengineering/revolver_"          + overlayType          ), colourSecondary, providers ));
        registerShaderCaseChemthrower( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "twilightforest", "items/immersiveengineering/chemthrower_"       + overlayType          ), colourSecondary, providers ));
        registerShaderCaseDrill      ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "twilightforest", "items/immersiveengineering/drill_"             + overlayType          ), colourSecondary, providers ));
        registerShaderCaseRailgun    ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "twilightforest", "items/immersiveengineering/railgun_"           + overlayType          ), colourSecondary, providers ));
        registerShaderCaseShield     ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "twilightforest", "items/immersiveengineering/shield_"            + overlayType          ), colourSecondary, providers ));
        registerShaderCaseMinecart   ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "twilightforest", "textures/items/immersiveengineering/minecart_" + overlayType + ".png" ), colourSecondary, providers ));
        registerShaderCaseBalloon    ( name, colourBackground, colourPrimary,              rarity, provideFromProviders( new ResourceLocation( "twilightforest", "blocks/shaders/balloon_"                       + overlayType          ), colourSecondary, providers ));

        // Since shaders won't occur in a way we'd like them to, we should register any additional variants ourselves if we know of any
        for(ShaderRegistry.IShaderRegistryMethod method : ShaderRegistry.shaderRegistrationMethods)
            method.apply(name, overlayType, rarity, colourBackground, colourPrimary, colourSecondary, colourBlade, null, 0);

        return ShaderRegistry.shaderRegistry.get(name).setCrateLoot(false).setBagLoot(true).setInLowerBags(false).setReplicationCost(new IngredientStack(new ItemStack(TFItems.ore_meter)));
    }

    // Shader Case Registration helpers
    @FunctionalInterface
    private interface ShaderLayerProvider<T extends ShaderCase.ShaderLayer> {
        T get(ResourceLocation resourceLocation, int color);
    }

    @SafeVarargs
    private static ShaderCase.ShaderLayer[] provideFromProviders(ResourceLocation resourceLocation, int color, ShaderLayerProvider<? extends ShaderCase.ShaderLayer>... layerProviders) {
        ShaderCase.ShaderLayer[] array = new ShaderCase.ShaderLayer[layerProviders.length];

        for (int i = 0; i < layerProviders.length; i++)
            array[i] = layerProviders[i].get(resourceLocation, color);

        return array;
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseRevolver registerShaderCaseRevolver(String name, int gripColor, int mainColor, int bladeColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseRevolver(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer(PROCESSED_REVOLVER_GRIP_LAYER, gripColor),
                        new ShaderCase.ShaderLayer(PROCESSED_REVOLVER_LAYER, mainColor),
                        new ShaderCase.ShaderLayer(PROCESSED_REVOLVER_LAYER, bladeColor)
                ).add(additionalLayers).add(UNCOLORED_REVOLVER_LAYER).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseChemthrower registerShaderCaseChemthrower(String name, int gripColor, int mainColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseChemthrower(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer(PROCESSED_CHEMTHROW_LAYER, gripColor),
                        new ShaderCase.ShaderLayer(PROCESSED_CHEMTHROW_LAYER, mainColor)
                ).add(additionalLayers).add(UNCOLORED_CHEMTHROW_LAYER).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseDrill registerShaderCaseDrill(String name, int gripColor, int mainColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        //.addHeadLayers(new ShaderLayer(new ResourceLocation("immersiveengineering", "items/drill_iron"), 0xFF_FF_FF_FF))

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseDrill(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer(PROCESSED_DRILL_LAYER, gripColor),
                        new ShaderCase.ShaderLayer(PROCESSED_DRILL_LAYER, mainColor)
                ).add(additionalLayers).add(UNCOLORED_DRILL_LAYER).add(NULL_LAYER).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseRailgun registerShaderCaseRailgun(String name, int gripColor, int mainColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseRailgun(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer(PROCESSED_RAILGUN_LAYER, gripColor),
                        new ShaderCase.ShaderLayer(PROCESSED_RAILGUN_LAYER, mainColor)
                ).add(additionalLayers).add(UNCOLORED_RAILGUN_LAYER).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseShield registerShaderCaseShield(String name, int gripColor, int mainColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseShield(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer(PROCESSED_SHIELD_LAYER, gripColor),
                        new ShaderCase.ShaderLayer(PROCESSED_SHIELD_LAYER, mainColor)
                ).add(additionalLayers).add(UNCOLORED_SHIELD_LAYER).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseMinecart registerShaderCaseMinecart(String name, int bodyColor, int secondaryColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseMinecart(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_0.png"   ), bodyColor),
                        new ShaderCase.ShaderLayer( new ResourceLocation("immersiveengineering", "textures/models/shaders/minecart_1_0.png" ), secondaryColor)
                ).add(additionalLayers).add(UNCOLORED_MINECART_LAYER).build()), rarity);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ShaderCaseBalloon registerShaderCaseBalloon(String name, int gripColor, int mainColor, EnumRarity rarity, ShaderCase.ShaderLayer... additionalLayers) {
        ImmutableList.Builder<ShaderCase.ShaderLayer> shaderLayerBuilder = ImmutableList.builder();

        return ShaderRegistry.registerShaderCase(name, new ShaderCaseBalloon(
                shaderLayerBuilder.add(
                        new ShaderCase.ShaderLayer(PROCESSED_BALLOON_LAYER, gripColor),
                        new ShaderCase.ShaderLayer(PROCESSED_BALLOON_LAYER, mainColor)
                ).add(additionalLayers).add(UNCOLORED_BALLOON_LAYER).build()), rarity);
    }
}
