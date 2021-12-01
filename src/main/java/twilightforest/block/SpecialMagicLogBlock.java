package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public abstract class SpecialMagicLogBlock extends RotatedPillarBlock {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected SpecialMagicLogBlock(BlockBehaviour.Properties props) {
		super(props.strength(2.0F).sound(SoundType.WOOD).lightLevel((state) -> 15));

		registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> container) {
		super.createBlockStateDefinition(container);
		container.add(ACTIVE);
	}

	//No longer an override, but keep here for sanity
	public int tickRate() {
		return 20;
	}

	@Override
	@Deprecated
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
		world.scheduleTick(pos, this, this.tickRate());
	}

	@Override
	@Deprecated
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {
		if (world.isClientSide || !state.getValue(ACTIVE)) return;

		playSound(world, pos, rand);
		performTreeEffect(world, pos, rand);

		world.scheduleTick(pos, this, this.tickRate());
	}

	@Override
	@Deprecated
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (!state.getValue(ACTIVE)) {
			world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
			world.scheduleTick(pos, this, this.tickRate());
			return InteractionResult.SUCCESS;
		} else if (state.getValue(ACTIVE)) {
			world.setBlockAndUpdate(pos, state.setValue(ACTIVE, false));
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	abstract void performTreeEffect(Level world, BlockPos pos, Random rand);

	protected void playSound(Level level, BlockPos pos, Random rand) { }
}
