package twilightforest.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import twilightforest.TwilightForestMod;

import java.util.List;

public class OptifineWarningScreen extends Screen {

	private final Screen lastScreen;
	private int ticksUntilEnable = 20 * 3;
	private MultiLineLabel message = MultiLineLabel.EMPTY;
	private static final Component text = new TranslatableComponent(TwilightForestMod.ID + ".gui.optifine.message");
	private final List<Button> exitButton = Lists.newArrayList();

	protected OptifineWarningScreen(Screen screen) {
		super(new TranslatableComponent(TwilightForestMod.ID + ".gui.optifine.title"));
		this.lastScreen = screen;
	}

	public Component getNarrationMessage() {
		return CommonComponents.joinForNarration(super.getNarrationMessage(), text);
	}

	protected void init() {
		super.init();
		this.exitButton.clear();
		this.exitButton.add(new Button(this.width / 2 - 75, this.height * 3 / 4, 150, 20, CommonComponents.GUI_PROCEED, (p_213002_1_) -> Minecraft.getInstance().setScreen(lastScreen)));
		this.message = MultiLineLabel.create(this.font, text, this.width - 50);
	}

	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 30, 16777215);
		this.message.renderCentered(matrixStack, this.width / 2, 70);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	public void tick() {
		super.tick();
		if (--this.ticksUntilEnable == 0) {
			for(Button button : this.exitButton) {
				button.active = true;
			}
		}
	}

	public boolean shouldCloseOnEsc() {
		return false;
	}
}
