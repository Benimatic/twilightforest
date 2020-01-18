package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;

public class BlockTFHugeWaterLily extends LilyPadBlock {

	private static final VoxelShape AABB = VoxelShapes.create(new AxisAlignedBB(0.1, 0.1, 0.1, 0.9, 0.9, 0.9));

	protected BlockTFHugeWaterLily() {
		super(Properties.create(Material.PLANTS).sound(SoundType.PLANT));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AABB;
	}

	/*@Override
	public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
		BlockState down = world.getBlockState(pos.down());
		Block b = down.getBlock();
		IProperty<Integer> levelProp = b instanceof BlockLiquid || b instanceof BlockFluidBase
				? BlockLiquid.LEVEL
				: null;

		return down.getMaterial() == Material.WATER
				&& (levelProp == null || down.getValue(levelProp) == 0);
	}*/

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		return state.getBlock() == Blocks.WATER;
	}

	//TODO: Move to client
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
