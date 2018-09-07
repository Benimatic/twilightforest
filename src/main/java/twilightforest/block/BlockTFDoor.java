package twilightforest.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFDoor extends BlockDoor implements ModelRegisterCallback {
    protected BlockTFDoor(Material materialIn) {
        super(materialIn);
    }
}
