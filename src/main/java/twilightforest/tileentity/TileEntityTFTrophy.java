package twilightforest.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTFTrophy extends SkullTileEntity {

	public int ticksExisted;

	@Override
	public void tick() {
		super.tick();
		this.ticksExisted++;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
}
