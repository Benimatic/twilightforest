package twilightforest.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

public class BlockTFStairs extends BlockStairs implements ModelRegisterCallback {

    protected BlockTFStairs(BlockState modelState) {
        super(modelState);
        this.setCreativeTab(TFItems.creativeTab);
        this.useNeighborBrightness = true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelUtils.registerToState(this, 0, getDefaultState().withProperty(FACING, Direction.EAST));
    }
}
