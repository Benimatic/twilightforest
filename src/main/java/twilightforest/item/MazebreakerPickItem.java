package twilightforest.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.MazestoneBlock;

import javax.annotation.Nonnull;

public class MazebreakerPickItem extends PickaxeItem {
	protected MazebreakerPickItem(Tier material, Properties props) {
		super(material, 1, -2.8F, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (this.allowedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.BLOCK_EFFICIENCY, 4);
			istack.enchant(Enchantments.UNBREAKING, 3);
			istack.enchant(Enchantments.BLOCK_FORTUNE, 2);
			list.add(istack);
		}
	}

	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
		float destroySpeed = super.getDestroySpeed(stack, state);
		return state.getBlock() instanceof MazestoneBlock ? destroySpeed * 16F : destroySpeed;
	}
}
