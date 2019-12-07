package twilightforest.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.enums.TowerDeviceVariant;

import javax.annotation.Nonnull;

public class ItemTFTowerKey extends ItemTF {
	ItemTFTowerKey(EnumRarity rarity) {
		super(rarity);
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction side, float fx, float fy, float fz) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() == TFBlocks.tower_device && state.getValue(BlockTFTowerDevice.VARIANT) == TowerDeviceVariant.VANISH_LOCKED) {
			if (!world.isRemote) {
				BlockTFTowerDevice.unlockBlock(world, pos);
				player.getHeldItem(hand).shrink(1);
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}
}
