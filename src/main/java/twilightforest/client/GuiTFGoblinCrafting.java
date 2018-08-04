package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.inventory.ContainerTFUncrafting;
import twilightforest.network.PacketCycleUncraftingRecipe;
import twilightforest.network.TFPacketHandler;

import java.io.IOException;

public class GuiTFGoblinCrafting extends GuiContainer {
	private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.GUI_DIR + "guigoblintinkering.png");

	public GuiTFGoblinCrafting(InventoryPlayer inventory, World world, int x, int y, int z) {
		super(new ContainerTFUncrafting(inventory, world, x, y, z));
	}

	@Override
	public void initGui() {
		super.initGui();

		this.buttonList.add(new CycleButton(1, guiLeft + 40, guiTop + 22, true));
		this.buttonList.add(new CycleButton(2, guiLeft + 40, guiTop + 55, false));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2) {
		this.fontRenderer.drawString(I18n.format(TFBlocks.uncrafting_table.getUnlocalizedName() + ".name"), 8, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GlStateManager.color(1, 1, 1, 1);
		this.mc.getTextureManager().bindTexture(textureLoc);
		int frameX = (this.width - this.xSize) / 2;
		int frameY = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(frameX, frameY, 0, 0, this.xSize, this.ySize);

		ContainerTFUncrafting tfContainer = (ContainerTFUncrafting) this.inventorySlots;

		// show uncrafting ingredients as background
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft, guiTop, 0);

		for (int i = 0; i < 9; i++) {
			Slot uncrafting = tfContainer.getSlot(2 + i);
			Slot assembly = tfContainer.getSlot(11 + i);

			if (!uncrafting.getStack().isEmpty()) {
				drawSlotAsBackground(uncrafting, assembly);
			}
		}
		GlStateManager.popMatrix();

		// show the costs if there are any
		FontRenderer fontRendererObj = this.mc.fontRenderer;
		RenderHelper.disableStandardItemLighting();

		int costVal = tfContainer.getUncraftingCost();
		if (costVal > 0) {
			if (this.mc.player.experienceLevel < costVal && !this.mc.player.capabilities.isCreativeMode) {
				int color = 0xA00000;
				String cost = "" + costVal;
				fontRendererObj.drawStringWithShadow(cost, frameX + 48 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
			} else {
				int color = 0x80FF20;
				String cost = "" + costVal;
				fontRendererObj.drawStringWithShadow(cost, frameX + 48 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
			}
		}

		costVal = tfContainer.getRecraftingCost();
		if (costVal > 0) {
			if (this.mc.player.experienceLevel < costVal && !this.mc.player.capabilities.isCreativeMode) {
				int color = 0xA00000;
				String cost = "" + costVal;
				fontRendererObj.drawStringWithShadow(cost, frameX + 130 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
			} else {
				int color = 0x80FF20;
				String cost = "" + costVal;
				fontRendererObj.drawStringWithShadow(cost, frameX + 130 - fontRendererObj.getStringWidth(cost), frameY + 38, color);
			}
		}

	}

	private void drawSlotAsBackground(Slot backgroundSlot, Slot appearSlot) {
		int screenX = appearSlot.xPos;
		int screenY = appearSlot.yPos;
		ItemStack itemStackToRender = backgroundSlot.getStack();
		this.zLevel = 50.0F;
		itemRender.zLevel = 50.0F;

		itemRender.renderItemIntoGUI(itemStackToRender, screenX, screenY);
		itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemStackToRender, screenX, screenY, "");

		boolean itemBroken = false;

		// TODO 1.11 this isn't going to work properly
		if (backgroundSlot.getHasStack() && backgroundSlot.getStack().getCount() == 0) {
			itemBroken = true;
		}

		// draw 50% gray rectangle over the item
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		Gui.drawRect(screenX, screenY, screenX + 16, screenY + 16, itemBroken ? 0x80FF8b8b : 0x9f8b8b8b);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();


		itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button instanceof CycleButton) {
			TFPacketHandler.CHANNEL.sendToServer(new PacketCycleUncraftingRecipe(((CycleButton) button).up));
			if (this.inventorySlots instanceof ContainerTFUncrafting) {
				ContainerTFUncrafting uncrafting = (ContainerTFUncrafting) this.inventorySlots;

				if (((CycleButton) button).up) {
					uncrafting.recipeInCycle++;
				} else {
					uncrafting.recipeInCycle--;
				}

				uncrafting.onCraftMatrixChanged(uncrafting.tinkerInput);
			}

			this.buttonList.clear();
			this.initGui();
			this.updateScreen();
		}
	}

	static class CycleButton extends GuiButton {
		private final boolean up;

		CycleButton(int buttonId, int x, int y, boolean up) {
			super(buttonId, x, y, 14, 9, "");
			this.up = up;
		}

		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				mc.getTextureManager().bindTexture(GuiTFGoblinCrafting.textureLoc);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

				int textureX = 176;
				int textureY = 0;

				if (this.hovered) textureX += this.width;

				// what's up
				if (!this.up) textureY += this.height;

				this.drawTexturedModalRect(this.x, this.y, textureX, textureY, this.width, this.height);
			}
		}
	}
}
