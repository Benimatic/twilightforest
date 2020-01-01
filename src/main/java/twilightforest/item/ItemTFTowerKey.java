package twilightforest.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import twilightforest.block.BlockTFLockedVanishing;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.enums.TowerDeviceVariant;

import javax.annotation.Nonnull;

public class ItemTFTowerKey extends ItemTF {
	ItemTFTowerKey(Rarity rarity, Properties props) {
		super(rarity, props);
	}

	@Nonnull
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		BlockState state = context.getWorld().getBlockState(context.getPos());
		if (state.getBlock() == TFBlocks.tower_device && state.get(BlockTFTowerDevice.VARIANT) == TowerDeviceVariant.VANISH_LOCKED) {
			if (!context.getWorld().isRemote) {
				BlockTFLockedVanishing.unlockBlock(context.getWorld(), context.getPos());
				context.getPlayer().getHeldItem(context.getHand()).shrink(1);
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}
}
