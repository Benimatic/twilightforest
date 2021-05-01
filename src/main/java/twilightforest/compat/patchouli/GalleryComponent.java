package twilightforest.compat.patchouli;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GalleryComponent implements ICustomComponent {
    int x, y;
    @SuppressWarnings("WeakerAccess")
    @SerializedName("table_x")
    int xCount = 6;

    @SuppressWarnings("WeakerAccess")
    public String items;

    private transient List<ItemStack> stacks;
    private static final transient Minecraft mc = Minecraft.getInstance();

    @Override
    public void build(int x, int y, int pageNum) {
        stacks = Arrays.stream(items.split("\\|")).map(ItemStackUtil::loadStackFromString).filter(i -> !i.isEmpty()).collect(Collectors.toList());

        if (xCount <= 0 || xCount > 6)
            xCount = MathHelper.clamp(stacks.size(), 1, 6);
    }

    @Override
    public void render(MatrixStack ms, IComponentRenderContext context, float pTicks, int mouseX, int mouseY) {
        int x = this.x + 5, y = this.y;
        int row, column;
        int listSize = stacks.size();
        int extras = Math.floorMod(listSize, xCount);
        int listBlock = listSize - extras;

        for (int c = 0; c < listSize; c++) {
            ItemStack stack = stacks.get(c);
            column = Math.floorMod(c, xCount);
            row = c / xCount;

            // Pages are 108 pixels wide.
            if (c < listBlock) {
                context.renderItemStack(ms, column * 18 + x, row * 18 + y, mouseX, mouseY, stack);
            } else {
                int columnPushed = (column * 18) + 54 - (extras * 9);

                context.renderItemStack(ms, columnPushed + x, row * 18 + y, mouseX, mouseY, stack);
            }
        }

        //mc.fontRenderer.drawString(listSize  + "", x - 40, y     , 0xFFAADD);
        //mc.fontRenderer.drawString(listBlock + "", x - 40, y + 15, 0xFFAADD);
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> unaryOperator) {

    }
}
