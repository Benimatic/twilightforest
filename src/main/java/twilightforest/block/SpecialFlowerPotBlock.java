package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SpecialFlowerPotBlock extends FlowerPotBlock {

	public SpecialFlowerPotBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> p_53528_, Properties properties) {
		super(emptyPot, p_53528_, properties);
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if(!this.isEmpty()) {
			pLevel.setBlock(pPos, getEmptyPot().defaultBlockState(), 3);
			pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos);
			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		} else {
			return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
		}
	}
}
