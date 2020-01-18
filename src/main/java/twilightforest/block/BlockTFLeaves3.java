package twilightforest.block;

import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import twilightforest.TFConfig;

public class BlockTFLeaves3 extends LeavesBlock {

	protected BlockTFLeaves3() {
		super(Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly());
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return TFConfig.performance.leavesLightOpacity;
	}

	//TODO: Move to loot table
	//	@Override
//	public ItemStack getItem(World world, BlockPos pos, BlockState state) {
//		return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
//	}
//
//	@Override
//	public Item getItemDropped(BlockState state, Random random, int fortune) {
//		return TFItems.magic_beans;
//	}

	@Override
	public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
		return true;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}
}
