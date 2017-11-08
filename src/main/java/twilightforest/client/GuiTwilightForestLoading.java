package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;

import java.io.IOException;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class GuiTwilightForestLoading extends GuiScreen {

    private Minecraft client = FMLClientHandler.instance().getClient();
    private final NetHandlerPlayClient connection;
    private int progress;
    private boolean isEntering;
    private boolean contentNeedsAssignment = false;
    private long lastWorldUpdateTick = 0L;
    private long seed;
    private static Random random = new Random();
    private Backgrounds background;
    private ItemStack item;

    GuiTwilightForestLoading(NetHandlerPlayClient clientPlayHandler) {
        this.connection = clientPlayHandler;
    }

    void setEntering(boolean isEntering) {
        this.isEntering = isEntering;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.assignContent();
    }

    @Override
    public void updateScreen() {
        if (++this.progress % 10 == 0) this.connection.sendPacket(new CPacketKeepAlive());
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException { }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.contentNeedsAssignment) {
            this.assignContent();
            this.contentNeedsAssignment = false;
        }

        if (mc.world != null && TFConfig.loadingScreen.cycleLoadingScreenFrequency != 0) {
            if (lastWorldUpdateTick != mc.world.getTotalWorldTime() % 240000) {

                lastWorldUpdateTick = mc.world.getTotalWorldTime() % 240000;

                if (lastWorldUpdateTick % TFConfig.loadingScreen.cycleLoadingScreenFrequency == 0) {
                    assignContent();
                }
            }
        }

        FontRenderer fontRenderer = mc.fontRenderer;
        ScaledResolution resolution = new ScaledResolution(client);

        drawBackground(resolution.getScaledWidth(), resolution.getScaledHeight());

        drawBouncingWobblyItem(partialTicks, resolution.getScaledWidth(), resolution.getScaledHeight());

        String loadTitle = I18n.translateToLocal(TwilightForestMod.ID + ".loading.title." + (isEntering ? "enter" : "leave"));
        GlStateManager.pushMatrix();
        GlStateManager.translate(
                ((resolution.getScaledWidth()) / 2) - (fontRenderer.getStringWidth(loadTitle) / 4),
                (resolution.getScaledHeight() / 3),
                0
        );
        GlStateManager.translate(-(fontRenderer.getStringWidth(loadTitle) / 4), 0, 0);
        fontRenderer.drawStringWithShadow(loadTitle, 0, 0, 0xEEEEEE); //eeeeeeeeeeeeeeeeee
        GlStateManager.popMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    private void assignContent() {
        background = Backgrounds.values()[random.nextInt(Backgrounds.values().length)];
        item = TFConfig.loadingScreen.getLoadingScreenIcons().get(random.nextInt(TFConfig.loadingScreen.getLoadingScreenIcons().size()));
        seed = random.nextLong();
    }

    private void drawBackground(float width, float height) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.color(0.9F, 0.9F, 0.9F, 1.0F);
        float f = 32.0F;
        random.setSeed(seed);

        background = Backgrounds.DARKTOWER;

        for (float x = f; x < width + f; x+=f ) {
            for (float y = f; y < height + f; y+=f ) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(background.getBackgroundMaterials()[random.nextInt(background.getBackgroundMaterials().length)]);
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                buffer.pos    (x-f, y, 0)
                        .tex  (0, 1)
                        .color(0.5f, 0.5f, 0.5f, 1f)
                        .endVertex();
                buffer.pos    ( x, y, 0)
                        .tex  ( 1, 1)
                        .color(0.5f, 0.5f, 0.5f, 1f)
                        .endVertex();
                buffer.pos    ( x, y-f, 0)
                        .tex  ( 1, 0)
                        .color(0.5f, 0.5f, 0.5f, 1f)
                        .endVertex();
                buffer.pos    (x-f, y-f, 0)
                        .tex  (0, 0)
                        .color(0.5f, 0.5f, 0.5f, 1f)
                        .endVertex();
                tessellator.draw();
            }
        }

        background.postRenderBackground(width, height, f);
    }

    private void drawBouncingWobblyItem(float partialTicks, float width, float height) {
        float sineTicker = (TFClientEvents.sineTicker + partialTicks) * TFConfig.loadingScreen.frequency;
        GlStateManager.pushMatrix();

        // Shove it!
        GlStateManager.translate(width - ((width/30) * TFConfig.loadingScreen.scale), height - (height / 10), 0); // Bottom right Corner

        if (TFConfig.loadingScreen.enable) {
            // Wobble it!
            GlStateManager.rotate((float) Math.sin(sineTicker / TFConfig.loadingScreen.tiltRange) * TFConfig.loadingScreen.tiltConstant, 0, 0, 1);

            // Bounce it!
            GlStateManager.scale(TFConfig.loadingScreen.scale, ((Math.sin(((sineTicker + 180F) / TFConfig.loadingScreen.tiltRange) * 2F) / TFConfig.loadingScreen.scaleDeviation) + 2F) * (TFConfig.loadingScreen.scale / 2), 1F);
        }

        // Shift it!
        GlStateManager.translate(-8, -16.5, 0);

        RenderHelper.enableGUIStandardItemLighting();
        // Draw it!
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, 0, 0);
        RenderHelper.disableStandardItemLighting();

        // Pop it!
        GlStateManager.popMatrix();
        // Bop it!
    }

    public enum Backgrounds {
        LABYRINTH(new ResourceLocation[]{
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/mazestone_brick.png"     ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/mazestone_brick.png"     ),
                //new ResourceLocation(TwilightForestMod.ID, "textures/blocks/mazestone_mossy.png"     ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/mazestone_cracked.png"   )
        }){
            private final ResourceLocation mazestoneDecor = new ResourceLocation(TwilightForestMod.ID, "textures/blocks/mazestone_decorative.png");

            @Override
            void postRenderBackground(float width, float height, float scale) {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                Minecraft.getMinecraft().getTextureManager().bindTexture(mazestoneDecor);

                for (float x = scale; x < width + scale; x+=scale ) {
                    buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    buffer.pos    (x-scale, 24F, 0F)
                            .tex  (0F, 0.75F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    buffer.pos    ( x, 24F, 0F)
                            .tex  ( 1F, 0.75F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    buffer.pos    ( x, 8F, 0F)
                            .tex  ( 1F, 0.25F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    buffer.pos    (x-scale, 8F, 0)
                            .tex  (0F, 0.25F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    tessellator.draw();
                }

                float halfScale = scale/2F;
                float bottomGrid = height - (height % halfScale);

                for (float x = scale; x < width + scale; x+=scale ) {
                    buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    buffer.pos    (x-scale, bottomGrid, 0F)
                            .tex  (0F, 0.75F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    buffer.pos    ( x, bottomGrid, 0F)
                            .tex  ( 1F, 0.75F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    buffer.pos    ( x, bottomGrid-halfScale, 0F)
                            .tex  ( 1F, 0.25F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    buffer.pos    (x-scale, bottomGrid-halfScale, 0F)
                            .tex  (0F, 0.25F)
                            .color(0.5F, 0.5F, 0.5F, 1F)
                            .endVertex();
                    tessellator.draw();
                }
            }
        },
        STRONGHOLD(new ResourceLocation[]{
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/knightbrick.png"         ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/knightbrick_mossy.png"   ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/knightbrick_cracked.png" )
        }),
        DARKTOWER(new ResourceLocation[]{
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_planks.png"    ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_planks.png"    ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_mossy.png"     ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_cracked.png"   ),
                //new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_infested.png"  ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_alt.png"       )
        }){
            private final ResourceLocation towerwoodEncased = new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_encased.png");

            @Override
            void postRenderBackground(float width, float height, float scale) {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                Minecraft.getMinecraft().getTextureManager().bindTexture(towerwoodEncased);

                buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                buffer.pos    ( 0F, scale, 0F )
                        .tex  ( 0F, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( width, scale, 0F )
                        .tex  ( width / scale, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( width, 0F, 0F )
                        .tex  ( width / scale, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( 0F, 0F, 0F )
                        .tex  ( 0F, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                tessellator.draw();

                buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                float bottomTrapezoid = scale*2;
                float topTrapezoid = scale;

                // BOTTOM HALF
                buffer.pos    ( 0F, bottomTrapezoid, 0F )
                        .tex  ( -0F, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( width, bottomTrapezoid, 0F )
                        .tex  ( (width / scale), 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                // TOP HALF
                buffer.pos    ( width, topTrapezoid, 0F )
                        .tex  ( width / scale, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( 0F, topTrapezoid, 0F )
                        .tex  ( 0F, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                tessellator.draw();

                /*
                // BOTTOM HALF
                buffer.pos    ( 0F, bottomTrapezoid, 0F )
                        .tex  ( 0F, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( width, bottomTrapezoid, 0F )
                        .tex  ( 1F, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                // TOP HALF
                buffer.pos    ( width+3, topTrapezoid, 0F )
                        .tex  ( 0.9F, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( 0F, topTrapezoid, 0F )
                        .tex  ( 0.1F, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                tessellator.draw();
                */

                float bottomUpper = height - scale;

                buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                buffer.pos    ( 0F, height, 0F )
                        .tex  ( 0F, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( width, height, 0F )
                        .tex  ( width / scale, 1F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( width, bottomUpper, 0F )
                        .tex  ( width / scale, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                buffer.pos    ( 0F, bottomUpper, 0F )
                        .tex  ( 0F, 0F )
                        .color( 0.5F, 0.5F, 0.5F, 1F )
                        .endVertex();
                tessellator.draw();
            }
        },
        FINALCASTLE(new ResourceLocation[]{
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/castleblock_brick.png"   ),
                //new ResourceLocation(TwilightForestMod.ID, "textures/blocks/castleblock_mossy.png"   ), // Jeez this one does not fit at ALL. Out!
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/castleblock_cracked.png" ),
                new ResourceLocation(TwilightForestMod.ID, "textures/blocks/castleblock_faded.png"   )
        });

        private final ResourceLocation[] backgroundMaterials;

        Backgrounds(ResourceLocation[] backgroundMaterials) {
            this.backgroundMaterials = backgroundMaterials;
        }

        ResourceLocation[] getBackgroundMaterials() {
            return backgroundMaterials;
        }

        void postRenderBackground(float width, float height, float scale) {
        }
    }
}
