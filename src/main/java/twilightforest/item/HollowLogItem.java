package twilightforest.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.RegistryObject;
import twilightforest.block.HollowLogHorizontal;
import twilightforest.block.HollowLogVertical;

import javax.annotation.Nullable;

public class HollowLogItem extends BlockItem {
    private final HollowLogHorizontal horizontalLog;
    private final HollowLogVertical verticalLog;

    public HollowLogItem(RegistryObject<HollowLogHorizontal> horizontalLog, RegistryObject<HollowLogVertical> verticalLog, Properties properties) {
        super(verticalLog.get(), properties);
        this.horizontalLog = horizontalLog.get();
        this.verticalLog = verticalLog.get();
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        return switch (context.getClickedFace().getAxis()) {
            case Y -> this.verticalLog.getStateForPlacement(context);
            case X, Z -> this.horizontalLog.getStateForPlacement(context);
        };
    }
}
