package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import twilightforest.data.BlockTagGenerator;
import twilightforest.data.FluidTagGenerator;
import twilightforest.enums.FireJetVariant;
import twilightforest.tileentity.FireJetTileEntity;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FireJetBlock extends Block {

	public static final EnumProperty<FireJetVariant> STATE = EnumProperty.create("state", FireJetVariant.class);

	protected FireJetBlock(Properties props) {
		super(props);
		registerDefaultState(defaultBlockState().setValue(STATE, FireJetVariant.IDLE));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(STATE);
	}

	@Nullable
	@Override
	public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		return state.getValue(STATE) == FireJetVariant.IDLE ? null : BlockPathTypes.DAMAGE_FIRE;
	}

	@Override
	@Deprecated
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		if (!world.isClientSide && state.getValue(STATE) == FireJetVariant.IDLE) {
			BlockPos lavaPos = findLavaAround(world, pos.below());

			if (isLava(world, lavaPos)) {
				world.setBlockAndUpdate(lavaPos, Blocks.AIR.defaultBlockState());
				world.setBlockAndUpdate(pos, state.setValue(STATE, FireJetVariant.POPPING));
			}
		}
	}

	/**
	 * Find a full block of lava near the designated block.  This is intentionally not really that reliable.
	 */
	private BlockPos findLavaAround(Level world, BlockPos pos) {
		if (isLava(world, pos)) {
			return pos;
		}

		for (int i = 0; i < 3; i++) {
			BlockPos randPos = pos.offset(world.random.nextInt(3) - 1, 0, world.random.nextInt(3) - 1);
			if (isLava(world, randPos)) {
				return randPos;
			}
		}

		return pos;
	}

	private boolean isLava(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		return b.is(BlockTagGenerator.FIRE_JET_FUEL) || b.getFluidState(state).is(FluidTagGenerator.FIRE_JET_FUEL);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.getValue(STATE) == FireJetVariant.POPPING || state.getValue(STATE) == FireJetVariant.FLAME;
	}

	@Nullable
	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
		return hasTileEntity(state) ? new FireJetTileEntity() : null;
	}
}
