package twilightforest.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFFenceGate extends BlockFenceGate implements ModelRegisterCallback {
    public BlockTFFenceGate(BlockPlanks.EnumType plankEnum) {
        super(plankEnum);
    }
}
