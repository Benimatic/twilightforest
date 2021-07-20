package twilightforest.item;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import twilightforest.block.MazestoneBlock;

import javax.annotation.Nonnull;

public class MazebreakerPickItem extends PickaxeItem {
	protected MazebreakerPickItem(IItemTier material, Properties props) {
		super(material, 1, -2.8F, props);
	}

	@Override
	public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> list) {
		if (isInGroup(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.addEnchantment(Enchantments.EFFICIENCY, 4);
			istack.addEnchantment(Enchantments.UNBREAKING, 3);
			istack.addEnchantment(Enchantments.FORTUNE, 2);
			list.add(istack);
		}
	}

	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
		float destroySpeed = super.getDestroySpeed(stack, state);
		return state.getBlock() instanceof MazestoneBlock ? destroySpeed * 16F : destroySpeed;
	}
}
