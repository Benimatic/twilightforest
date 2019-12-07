package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFConfig;
import twilightforest.item.TFItems;

public class BlockTFGiantLeaves extends BlockTFGiantBlock {

	public BlockTFGiantLeaves() {
		super(Blocks.LEAVES.getDefaultState());
		this.setHardness(0.2F * 64F);
		this.setLightOpacity(1);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public int getLightOpacity(BlockState state) {
		return TFConfig.performance.leavesLightOpacity;
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	@Deprecated
	public boolean shouldSideBeRendered(BlockState state, IBlockAccess world, BlockPos pos, Direction side) {
		switch (side) {
			case DOWN:
				return (pos.getY() & 3) == 0;
			case UP:
				return (pos.getY() & 3) == 3;
			case NORTH:
				return (pos.getZ() & 3) == 0;
			case SOUTH:
				return (pos.getZ() & 3) == 3;
			case WEST:
				return (pos.getX() & 3) == 0;
			case EAST:
				return (pos.getX() & 3) == 3;
		}

		return super.shouldSideBeRendered(state, world, pos, side);
	}
}
