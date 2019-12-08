package twilightforest.item;

import net.minecraft.item.BowItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;

public abstract class ItemTFBowBase extends BowItem {

	private static final Rarity RARITY = Rarity.UNCOMMON;

	protected ItemTFBowBase(Properties props) {
		super(props.group(TFItems.creativeTab));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return stack.isEnchanted() ? Rarity.RARE.compareTo(RARITY) > 0 ? Rarity.RARE : RARITY : RARITY;
	}
}
