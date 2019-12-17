package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFHugeStalk extends Block {

	protected BlockTFHugeStalk() {
		super(Properties.create(Material.WOOD, MaterialColor.FOLIAGE).hardnessAndResistance(1.25F, 7.0F).sound(SoundType.PLANT));

		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public boolean canSustainLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	// [VanillaCopy] BlockLog
	@SuppressWarnings("unused")
	@Override
	public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
		int i = 4;
		int j = 5;

		if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
				BlockState iblockstate = worldIn.getBlockState(blockpos);

				if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
					iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
				}
			}
		}
	}

}
