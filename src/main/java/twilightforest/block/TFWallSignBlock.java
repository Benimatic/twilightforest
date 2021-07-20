package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import twilightforest.tileentity.TFSignTileEntity;

public class TFWallSignBlock extends WallSignBlock {
    public TFWallSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TFSignTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
