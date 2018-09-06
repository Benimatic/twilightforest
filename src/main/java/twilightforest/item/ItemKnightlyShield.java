package twilightforest.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.client.ModelRegisterCallback;

import javax.annotation.Nullable;
import java.util.List;

public class ItemKnightlyShield extends ItemShield implements ModelRegisterCallback {
    public ItemKnightlyShield() {
        this.setMaxDamage(1024);
        this.setCreativeTab(TFItems.creativeTab);
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 144000;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == TFItems.knightmetal_ingot || repair.getItem() != Item.getItemFromBlock(Blocks.PLANKS) && super.getIsRepairable(toRepair, repair);
    }
}