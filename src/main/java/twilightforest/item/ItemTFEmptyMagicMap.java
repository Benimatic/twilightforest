package twilightforest.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AbstractMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemTFEmptyMagicMap extends AbstractMapItem {
	protected ItemTFEmptyMagicMap(Properties props) {
		super(props.group(TFItems.creativeTab));
	}

	// [VanillaCopy] ItemEmptyMap.onItemRightClick, edits noted
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		// TF - scale at 4
		ItemStack itemstack = ItemTFMagicMap.setupNewMap(worldIn, playerIn.getX(), playerIn.getZ(), (byte) 4, true, false);
		ItemStack itemstack1 = playerIn.getHeldItem(handIn);
		itemstack1.shrink(1);

		if (itemstack1.isEmpty()) {
			return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
		} else {
			if (!playerIn.inventory.addItemStackToInventory(itemstack.copy())) {
				playerIn.dropItem(itemstack, false);
			}

			playerIn.addStat(Stats.ITEM_USED.get(this));
			return new ActionResult<>(ActionResultType.SUCCESS, itemstack1);
		}
	}
}
