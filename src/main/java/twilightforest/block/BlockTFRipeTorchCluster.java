package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTFRipeTorchCluster extends BlockTFTrollRoot {

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 15;
	}

	/* TODO 1.14: Move to block loot table
	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune) {
		return TFItems.torchberries;
	}

	@Override
	public int quantityDropped(BlockState state, int fortune, Random random) {
		return quantityDroppedWithBonus(fortune, random);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 4 + rand.nextInt(5);
	}

	@Override
	public int quantityDroppedWithBonus(int bonus, Random rand) {
		if (bonus > 0 && Item.getItemFromBlock(this) != this.getItemDropped(getDefaultState(), rand, bonus)) {
			int j = rand.nextInt(bonus + 2) - 1;

			if (j < 0) {
				j = 0;
			}

			return this.quantityDropped(rand) * (j + 1);
		} else {
			return this.quantityDropped(rand);
		}
	}
	*/

	@Override
	public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		// do not call normal harvest if the player is shearing
		if (world.isRemote || stack.getItem() != Items.SHEARS) {
			super.harvestBlock(world, player, pos, state, te, stack);
		}
	}

	//TODO: Move to client
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
