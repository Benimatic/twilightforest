package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class StructureTFMazeStones extends StructureComponent.BlockSelector {

	@Override
	public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean wall) {
        if (!wall)
        {
            this.field_151562_a = Blocks.air;
            this.selectedBlockMetaData = 0;
        }
        else
        {
            this.field_151562_a = TFBlocks.mazestone;
            float rf = par1Random.nextFloat();

            if (rf < 0.2F)
            {
                this.selectedBlockMetaData = 5;
            }
            else if (rf < 0.5F)
            {
                this.selectedBlockMetaData = 4;
            }
            else
            {
                this.selectedBlockMetaData = 1;
            }
        }
	}

}
