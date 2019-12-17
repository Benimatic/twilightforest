package twilightforest.block;

import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockTFLog extends LogBlock {

	protected BlockTFLog(MaterialColor topColor, MaterialColor sideColor) {
		super(topColor, Properties.create(Material.WOOD, sideColor).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
}
