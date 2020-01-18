package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFPlant;
import twilightforest.enums.PlantVariant;

//TODO 1.14: It doesn't appear redundant, but it extends the redundant class
public class ItemBlockTFPlant extends ItemBlockTFMeta {

	public ItemBlockTFPlant(Block block) {
		super(block);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, Direction side, PlayerEntity player, ItemStack stack) {
		int meta = stack.getItemDamage();
		if (meta == PlantVariant.ROOT_STRAND.ordinal() || meta == PlantVariant.TORCHBERRY.ordinal()) {
			// [VanillaCopy] super's side offsetting logic
			Block block = world.getBlockState(pos).getBlock();

			if (block == Blocks.SNOW_LAYER && block.isReplaceable(world, pos)) {
				side = Direction.UP;
			} else if (!block.isReplaceable(world, pos)) {
				pos = pos.offset(side);
			}

			return BlockTFPlant.canPlaceRootAt(world, pos);
		} else {
			return super.canPlaceBlockOnSide(world, pos, side, player, stack);
		}
	}
}
