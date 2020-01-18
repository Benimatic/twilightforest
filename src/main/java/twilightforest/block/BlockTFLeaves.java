package twilightforest.block;

import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import twilightforest.TFConfig;

public class BlockTFLeaves extends LeavesBlock {

	protected BlockTFLeaves() {
		super(Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly());
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return TFConfig.performance.leavesLightOpacity;
	}

	//TODO: Move to loot table
//	@Override
//	public Item getItemDropped(BlockState state, Random random, int fortune) {
//		return Item.getItemFromBlock(TFBlocks.twilight_sapling);
//	}
//
//	@Override
//	public int damageDropped(BlockState state) {
//		LeavesVariant leafType = state.getValue(VARIANT);
//		return leafType == LeavesVariant.RAINBOAK ? 9 : leafType.ordinal();
//	}
//
//	@Override
//	public ItemStack getSilkTouchDrop(BlockState state) {
//		return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
//	}
//
//	@Override
//	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
//		return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
//	}
//
//	@Override
//	public int getSaplingDropChance(BlockState state) {
//		return state.getValue(VARIANT) == LeavesVariant.MANGROVE ? 20 : 40;
//	}
//
//	@Override
//	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
//		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).ordinal()));
//	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}
}
