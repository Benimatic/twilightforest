package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

public interface WaterloggedBlock extends BucketPickup, LiquidBlockContainer {
    boolean stateIsWaterlogged(BlockState state);
    BlockState setWaterlogging(BlockState state, boolean doWater);

    default boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return !this.stateIsWaterlogged(state) && fluid == Fluids.WATER;
    }

    default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!this.stateIsWaterlogged(state) && fluidState.getType() == Fluids.WATER) {
            if (!level.isClientSide()) {
                level.setBlock(pos, this.setWaterlogging(state, true), 3);
                level.getLiquidTicks().scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else {
            return false;
        }
    }

    default ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        if (this.stateIsWaterlogged(state)) {
            level.setBlock(pos, this.setWaterlogging(state, false), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }

            return new ItemStack(Items.WATER_BUCKET);
        } else {
            return ItemStack.EMPTY;
        }
    }

    default Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }
}
