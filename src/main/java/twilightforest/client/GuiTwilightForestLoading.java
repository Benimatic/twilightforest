package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
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
import twilightforest.item.TFItems;

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
    private Random random = new Random();
    private ResourceLocation background;
    private ItemStack item;

    private final static ResourceLocation[] BACKGROUNDS = {
            new ResourceLocation(TwilightForestMod.ID, "textures/blocks/mazestone_brick.png"),
            new ResourceLocation(TwilightForestMod.ID, "textures/blocks/towerwood_planks.png"),
            new ResourceLocation(TwilightForestMod.ID, "textures/blocks/knightbrick.png"),
            new ResourceLocation(TwilightForestMod.ID, "textures/blocks/castleblock_brick.png")
    };

    private final static ItemStack[] ITEM_STACKS = {
            new ItemStack(TFItems.experiment115),
            new ItemStack(TFItems.magicMap),
            new ItemStack(TFItems.charmOfKeeping3),
            new ItemStack(TFItems.charmOfLife2),
            new ItemStack(TFItems.phantomHelm),
            new ItemStack(TFItems.lampOfCinders),
            new ItemStack(TFItems.carminite),
            new ItemStack(TFItems.chainBlock),
            new ItemStack(TFItems.yetiHelm),
            new ItemStack(TFItems.oreMagnet),
            new ItemStack(TFItems.hydraChop),
            new ItemStack(TFItems.fierySword),
            new ItemStack(TFItems.steeleafIngot),
            new ItemStack(TFItems.magicBeans),
            new ItemStack(TFItems.ironwoodRaw),
            new ItemStack(TFItems.nagaScale),
            new ItemStack(TFItems.experiment115, 1, 2)/*,
            new ItemStack(TFItems.miniture_structure),
            new ItemStack(TFItems.miniture_structure, 1, 6),
            new ItemStack(Item.getItemFromBlock(TFBlocks.knightmetalStorage)),
            new ItemStack(Item.getItemFromBlock(TFBlocks.towerDevice), 1, 10)*/
    };

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

        if (mc.world != null && TFConfig.loadingIcon.cycleLoadingScreenFrequency != 0) {
            if (lastWorldUpdateTick != mc.world.getTotalWorldTime() % 240000) {

                lastWorldUpdateTick = mc.world.getTotalWorldTime() % 240000;

                if (lastWorldUpdateTick % TFConfig.loadingIcon.cycleLoadingScreenFrequency == 0) {
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
        background = BACKGROUNDS[random.nextInt(BACKGROUNDS.length)];
        item = ITEM_STACKS[random.nextInt(ITEM_STACKS.length)];
    }

    private void drawBackground(float width, float height) {
        // todo randomize
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(background);
        GlStateManager.color(0.9F, 0.9F, 0.9F, 1.0F);
        float f = 32.0F;
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

        buffer.pos(0, height, 0)
                .tex(0, height / f)
                .color(0.5f, 0.5f, 0.5f, 1f)
                .endVertex();
        buffer.pos(width, height, 0)
                .tex(width / f, height / f)
                .color(0.5f, 0.5f, 0.5f, 1f)
                .endVertex();
        buffer.pos(width, 0, 0)
                .tex(width / f, 0)
                .color(0.5f, 0.5f, 0.5f, 1f)
                .endVertex();
        buffer.pos(0, 0, 0)
                .tex(0, 0)
                .color(0.5f, 0.5f, 0.5f, 1f)
                .endVertex();
        tessellator.draw();
    }

    private void drawBouncingWobblyItem(float partialTicks, float width, float height) {
        float sineTicker = (TFClientEvents.sineTicker + partialTicks) * TFConfig.loadingIcon.frequency;
        GlStateManager.pushMatrix();

        // Shove it!
        GlStateManager.translate(width - ((width/30) * TFConfig.loadingIcon.scale), height - (height / 10), 0); // Bottom right Corner

        if (TFConfig.loadingIcon.enable) {
            // Wobble it!
            GlStateManager.rotate((float) Math.sin(sineTicker / TFConfig.loadingIcon.tiltRange) * TFConfig.loadingIcon.tiltConstant, 0, 0, 1);

            // Bounce it!
            GlStateManager.scale(TFConfig.loadingIcon.scale, ((Math.sin(((sineTicker + 180F) / TFConfig.loadingIcon.tiltRange) * 2F) / TFConfig.loadingIcon.scaleDeviation) + 2F) * (TFConfig.loadingIcon.scale / 2), 1F);
        }

        // Shift it!
        GlStateManager.translate(-8, -16.5, 0);

        // Draw it!
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, 0, 0);

        // Pop it!
        GlStateManager.popMatrix();
        // Bop it!
    }
}
