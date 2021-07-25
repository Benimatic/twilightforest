package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;
import twilightforest.advancements.TFAdvancements;
import twilightforest.tileentity.*;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.sounds.SoundSource;

public class GhastTrapBlock extends Block {
	public static final int ACTIVATE_EVENT = 0;
	public static final int DEACTIVATE_EVENT = 1;
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public GhastTrapBlock(Properties props) {
		super(props);
		this.registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (world.isClientSide) {
			return;
		}

		if (!state.getValue(ACTIVE) && isInactiveTrapCharged(world, pos) && world.hasNeighborSignal(pos)) {
			for (ServerPlayer player : world.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(6.0D))) {
				TFAdvancements.ACTIVATED_GHAST_TRAP.trigger(player);
			}

			world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
			world.playSound(null, pos, TFSounds.JET_START, SoundSource.BLOCKS, 0.3F, 0.6F);
			world.blockEvent(pos, this, ACTIVATE_EVENT, 0);
		}
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int event, int payload) {
		BlockEntity te = world.getBlockEntity(pos);
		return te != null && te.triggerEvent(event, payload);
	}

	/**
	 * Check if the inactive trap block is fully charged
	 */
	private boolean isInactiveTrapCharged(Level world, BlockPos pos) {
		BlockEntity tileEntity = world.getBlockEntity(pos);
		return tileEntity instanceof ActiveGhastTrapTileEntity && ((ActiveGhastTrapTileEntity) tileEntity).isCharged();
	}

	// [VanillaCopy] BlockRedstoneOre.spawnParticles. Unchanged.
	public void sparkle(Level worldIn, BlockPos pos) {
		Random random = worldIn.random;
		double d0 = 0.0625D;

		for (int i = 0; i < 6; ++i) {
			double d1 = pos.getX() + random.nextFloat();
			double d2 = pos.getY() + random.nextFloat();
			double d3 = pos.getZ() + random.nextFloat();

			if (i == 0 && !worldIn.getBlockState(pos.above()).isSolidRender(worldIn, pos)) {
				d2 = pos.getY() + d0 + 1.0D;
			}

			if (i == 1 && !worldIn.getBlockState(pos.below()).isSolidRender(worldIn, pos)) {
				d2 = pos.getY() - d0;
			}

			if (i == 2 && !worldIn.getBlockState(pos.south()).isSolidRender(worldIn, pos)) {
				d3 = pos.getZ() + d0 + 1.0D;
			}

			if (i == 3 && !worldIn.getBlockState(pos.north()).isSolidRender(worldIn, pos)) {
				d3 = pos.getZ() - d0;
			}

			if (i == 4 && !worldIn.getBlockState(pos.east()).isSolidRender(worldIn, pos)) {
				d1 = pos.getX() + d0 + 1.0D;
			}

			if (i == 5 && !worldIn.getBlockState(pos.west()).isSolidRender(worldIn, pos)) {
				d1 = pos.getX() - d0;
			}

			if (d1 < pos.getX() || d1 > pos.getX() + 1 || d2 < 0.0D || d2 > pos.getY() + 1 || d3 < pos.getZ() || d3 > pos.getZ() + 1) {
				worldIn.addParticle(DustParticleOptions.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
		return new ActiveGhastTrapTileEntity();
	}
}
