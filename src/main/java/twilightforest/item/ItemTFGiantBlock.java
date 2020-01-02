package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFGiantBlock;
import twilightforest.block.TFBlocks;

public class ItemTFGiantBlock extends BlockItem {

	public ItemTFGiantBlock(Block block, Properties props) {
		super(block, props);
	}

	// [VanillaCopy] super, but check every block in the box for permissions
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		Direction facing = context.getFace();

		if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		ItemStack itemstack = context.getPlayer().getHeldItem(context.getHand());

		if (itemstack.isEmpty()) {
			return ActionResultType.FAIL;
		}

		for (BlockPos iterPos : BlockTFGiantBlock.getVolume(pos)) {
			if (!context.getPlayer().canPlayerEdit(iterPos, facing, itemstack) || !worldIn.mayPlace(this.getBlock(), iterPos, false, facing, null)) {
				return ActionResultType.FAIL;
			}
		}

		pos = BlockTFGiantBlock.roundCoords(pos);

		int i = this.getMetadata(itemstack.getMetadata());
		BlockState iblockstate1 = this.getBlock().getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

		if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
			iblockstate1 = worldIn.getBlockState(pos);
			SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, context.getPlayer());
			worldIn.playSound(context.getPlayer(), pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			itemstack.shrink(1);
		}

		return ActionResultType.SUCCESS;
	}

	@Override
	public int getBurnTime(ItemStack itemStack) {
		if (this.getBlock() == TFBlocks.giant_log.get()) {
			return 300 * 64;
		}
		return super.getBurnTime(itemStack);
	}
}
