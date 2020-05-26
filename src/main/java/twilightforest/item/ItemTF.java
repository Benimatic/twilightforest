package twilightforest.item;

import net.minecraft.item.Rarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemTF extends Item {
	private final Rarity RARITY;

	protected ItemTF(Properties props) {
		this(Rarity.COMMON, props);
	}

	protected ItemTF(Rarity rarity, Properties props) {
		super(props);
		this.RARITY = rarity;
	}

	@Nonnull
	@Override
	public Rarity getRarity(ItemStack stack) {
		return stack.isEnchanted() ? Rarity.RARE.compareTo(RARITY) > 0 ? Rarity.RARE : RARITY : RARITY;
	}
}
