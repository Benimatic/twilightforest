package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.inventory.UncraftingMenu;

import org.jetbrains.annotations.Nullable;

public class UncraftingTableBlock extends Block {

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public UncraftingTableBlock() {
		super(Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD));
		this.registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));
	}

	@Override
	@Deprecated
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (!level.isClientSide()) {
			player.openMenu(state.getMenuProvider(level, pos));
			player.awardStat(TFStats.UNCRAFTING_TABLE_INTERACTIONS.get());
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide()) {
			boolean flag = level.hasNeighborSignal(pos);
			if (flag != state.getValue(POWERED)) {
				if (flag && level.getBlockState(pos.below()).is(Blocks.AMETHYST_BLOCK)) {
					level.playSound(null, pos, TFSounds.UNCRAFTING_TABLE_ACTIVATE.get(), SoundSource.BLOCKS, 0.5F, 1.0F);
				}
				level.setBlockAndUpdate(pos, state.setValue(POWERED, flag));
			}
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		if (!newState.is(state.getBlock())) {
			this.neighborChanged(state, level, pos, this, pos, moving);
		}
	}

	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((id, inv, player) -> new UncraftingMenu(id, inv, player.getLevel(), ContainerLevelAccess.create(level, pos)),
				Component.translatable("container.twilightforest.uncrafting_table"));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

}
