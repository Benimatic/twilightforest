package twilightforest.structures.stronghold;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class StructureTFKnightStones extends StructureComponent.BlockSelector {

	@Override
	public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5) {
        if (!par5)
        {
            this.field_151562_a = Blocks.air;
            this.selectedBlockMetaData = 0;
        }
        else
        {
            this.field_151562_a = TFBlocks.underBrick;
            float var6 = par1Random.nextFloat();

            if (var6 < 0.2F)
            {
                this.selectedBlockMetaData = 2;
            }
            else if (var6 < 0.5F)
            {
                this.selectedBlockMetaData = 1;
            }
            else
            {
                this.selectedBlockMetaData = 0;
            }
        }
	}

}
