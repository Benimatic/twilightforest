package twilightforest.client;


import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.inventory.ContainerTFUncrafting;

public class GuiTFGoblinCrafting extends GuiContainer {
	
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.GUI_DIR + "guigoblintinkering.png");


	public GuiTFGoblinCrafting(InventoryPlayer inventory, World world, int x, int y, int z) {
		super(new ContainerTFUncrafting(inventory, world, x, y, z));
	}

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int var1, int var2)
    {
        this.fontRendererObj.drawString("Uncrafting Table", 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }


	/**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textureLoc);
        int frameX = (this.width - this.xSize) / 2;
        int frameY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(frameX, frameY, 0, 0, this.xSize, this.ySize);
        
        ContainerTFUncrafting tfContainer = (ContainerTFUncrafting)this.inventorySlots;
        
        // show uncrafting ingredients as background
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)this.guiLeft, (float)this.guiTop, 0.0F);
        
        for (int i = 0; i < 9; i++) {
        	Slot uncrafting = tfContainer.getSlot(2 + i);
        	Slot assembly = tfContainer.getSlot(11 + i);
        	
        	if (uncrafting.getStack() != null)
        	{
        		drawSlotAsBackground(uncrafting, assembly);
        	}
        }
        GL11.glPopMatrix();
        
        // show the costs if there are any
        FontRenderer fontRendererObj = this.mc.fontRenderer;
        RenderHelper.disableStandardItemLighting();
        
        int costVal =  tfContainer.getUncraftingCost();
        if (costVal > 0) {
	        if (this.mc.thePlayer.experienceLevel < costVal && !this.mc.thePlayer.capabilities.isCreativeMode) {
		        int color = 0xA00000;
		        String cost = String.valueOf(costVal);
		        fontRendererObj.drawStringWithShadow(cost, frameX + 48 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
	        }
	        else {
		        int color = 0x80FF20;
		        String cost = String.valueOf(costVal);
		        fontRendererObj.drawStringWithShadow(cost, frameX + 48 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
	        }
        }
 
        costVal = tfContainer.getRecraftingCost();
        if (costVal > 0) {
	        if (this.mc.thePlayer.experienceLevel < costVal && !this.mc.thePlayer.capabilities.isCreativeMode) {
		        int color = 0xA00000;
		        String cost = String.valueOf(costVal);
		        fontRendererObj.drawStringWithShadow(cost, frameX + 130 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
	        }
	        else {
		        int color = 0x80FF20;
		        String cost = String.valueOf(costVal);
		        fontRendererObj.drawStringWithShadow(cost, frameX + 130 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
	        }
        }
 
	}


	
    /**
     * Draws an inventory slot
     */
    private void drawSlotAsBackground(Slot backgroundSlot, Slot appearSlot)
    {
        int screenX = appearSlot.xDisplayPosition;
        int screenY = appearSlot.yDisplayPosition;
        ItemStack itemStackToRender = backgroundSlot.getStack();
        this.zLevel = 50.0F;
        itemRender.zLevel = 50.0F;

        itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc.renderEngine, itemStackToRender, screenX, screenY);
        itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, itemStackToRender, screenX, screenY);
        
        boolean itemBroken = false;
        
        if (backgroundSlot.getHasStack() && backgroundSlot.getStack().stackSize == 0) {
        	itemBroken = true;
        }
        
        
        // draw 50% gray rectangle over the item
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        Gui.drawRect(screenX, screenY, screenX + 16, screenY + 16, itemBroken ? 0x80FF8b8b : 0x9f8b8b8b);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);


        itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }
}
