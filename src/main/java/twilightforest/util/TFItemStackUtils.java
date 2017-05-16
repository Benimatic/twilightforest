package twilightforest.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TFItemStackUtils
{
	public static boolean consumeInventoryItem(InventoryPlayer inventory, Item item, int count)
	{
		final int slotFor = inventory.getSlotFor(new ItemStack(item));
		if (slotFor == -1) {
			return false;
		}
		inventory.decrStackSize(slotFor, count);
		return true;
	}
}
