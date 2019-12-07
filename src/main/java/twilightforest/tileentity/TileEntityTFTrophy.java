package twilightforest.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTFTrophy extends TileEntitySkull {

	public int ticksExisted;

	@Override
	public void update() {
		super.update();
		this.ticksExisted++;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
}
