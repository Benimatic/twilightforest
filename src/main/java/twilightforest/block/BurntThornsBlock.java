package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;

public class BurntThornsBlock extends ThornsBlock {

	protected BurntThornsBlock(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		return null;
	}

	@Override
	@Deprecated
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		// dissolve
		if (!world.isClientSide && (entity instanceof LivingEntity || entity instanceof Projectile)) {
			world.destroyBlock(pos, false);
		}
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		state.getBlock().playerWillDestroy(world, pos, state, player);
		return world.setBlock(pos, fluid.createLegacyBlock(), world.isClientSide ? 11 : 3);
	}
}
