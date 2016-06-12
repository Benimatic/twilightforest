package twilightforest.structures.darktower;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class StructureTFTowerWoods extends StructureComponent.BlockSelector {

	@Override
	public void selectBlocks(Random par1Random, int x, int y, int z, boolean isWall) {
        if (!isWall)
        {
            this.field_151562_a = Blocks.AIR;
            this.selectedBlockMetaData = 0;
        }
        else
        {
            this.field_151562_a = TFBlocks.towerWood;
            float randFloat = par1Random.nextFloat();

            if (randFloat < 0.1F)
            {
                this.selectedBlockMetaData = 2;
            }
            else if (randFloat < 0.2F)
            {
                this.selectedBlockMetaData = 3;
            }
            else if (randFloat < 0.225F)
            {
                this.selectedBlockMetaData = 4;
            }
            else
            {
                this.selectedBlockMetaData = 0;
            }
        }
	}

}
