package twilightforest.structures.icetower;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class StructureTFAuroraBricks extends StructureComponent.BlockSelector {

	@Override
	public void selectBlocks(Random par1Random, int x, int y, int z, boolean wall) {
        if (!wall)
        {
            this.field_151562_a = Blocks.air;
            this.selectedBlockMetaData = 0;
        }
        else
        {
            this.field_151562_a = TFBlocks.auroraBlock;
            this.selectedBlockMetaData = Math.abs(x + z) % 16;
        }
	}

}
