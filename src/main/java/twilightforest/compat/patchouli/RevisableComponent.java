package twilightforest.compat.patchouli;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.matrix.MatrixStack;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.base.ClientAdvancements;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.base.PatchouliConfig;

import java.util.function.UnaryOperator;

public class RevisableComponent implements ICustomComponent {

    int x, y;

    @SerializedName("advancement_key")
    public String advancementKey;

    @SerializedName("locked_text")
    public String oldText;

    @SerializedName("unlocked_text")
    public String newText;

    @SerializedName("max_width")
    int maxWidth = 116;

    @SerializedName("line_height")
    int lineHeight = 9;

    transient BookTextRenderer textRenderer;

    @Override
    public void build(int x, int y, int pageNum) {

    }

    @Override
    public void onDisplayed(IComponentRenderContext context) {
        this.textRenderer = new BookTextRenderer((GuiBook) context.getGui(), PatchouliConfig.disableAdvancementLocking.get() || advancementKey == null || advancementKey.isEmpty() || ClientAdvancements.hasDone(this.advancementKey) ? this.newText : this.oldText, this.x, this.y, this.maxWidth, this.lineHeight, 0xFF_000000);

        //TwilightForestMod.LOGGER.info(x + " " + y + " " + maxWidth + " " + lineHeight);
    }

    @Override
    public void render(MatrixStack ms, IComponentRenderContext context, float pTicks, int mouseX, int mouseY) {
        this.textRenderer.render(ms, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(IComponentRenderContext context, double mouseX, double mouseY, int mouseButton) {
        this.textRenderer.click(mouseX, mouseY, mouseButton);
        return true;
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> unaryOperator) {

    }
}
