package twilightforest.tileentity;

import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TFSignTileEntity extends SignBlockEntity {

    @Override
    public BlockEntityType<?> getType() {
        return TFTileEntities.TF_SIGN.get();
    }
}
