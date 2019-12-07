package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFGiantBlock;
import twilightforest.block.TFBlocks;

public class ItemTFGiantBlock extends ItemBlock {

	public ItemTFGiantBlock(Block block) {
		super(block);
	}

	// [VanillaCopy] super, but check every block in the box for permissions
	@Override
	public EnumActionResult onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {

		BlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		ItemStack itemstack = player.getHeldItem(hand);

		if (itemstack.isEmpty()) {
			return EnumActionResult.FAIL;
		}

		for (BlockPos iterPos : BlockTFGiantBlock.getVolume(pos)) {
			if (!player.canPlayerEdit(iterPos, facing, itemstack) || !worldIn.mayPlace(this.block, iterPos, false, facing, null)) {
				return EnumActionResult.FAIL;
			}
		}

		pos = BlockTFGiantBlock.roundCoords(pos);

		int i = this.getMetadata(itemstack.getMetadata());
		BlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

		if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
			iblockstate1 = worldIn.getBlockState(pos);
			SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
			worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			itemstack.shrink(1);
		}

		return EnumActionResult.SUCCESS;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		if (this.block == TFBlocks.giant_log) {
			return 300 * 64;
		}
		return super.getItemBurnTime(itemStack);
	}
}
