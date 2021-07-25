package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;
import twilightforest.tileentity.FireflyTileEntity;
import twilightforest.tileentity.TFSignTileEntity;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import twilightforest.tileentity.TFTileEntities;

import javax.annotation.Nullable;

public class TFWallSignBlock extends WallSignBlock {
    public TFWallSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TFSignTileEntity(pos, state);
    }
}
