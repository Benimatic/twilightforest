package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTFBurntThorns extends BlockTFThorns {

	protected BlockTFBurntThorns(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		return null;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		// dissolve
		if (!world.isRemote && entity instanceof LivingEntity) {
			world.destroyBlock(pos, false);
		}
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
		getBlock().onBlockHarvested(world, pos, state, player);
		return world.setBlockState(pos, fluid.getBlockState(), world.isRemote ? 11 : 3);
	}
}
