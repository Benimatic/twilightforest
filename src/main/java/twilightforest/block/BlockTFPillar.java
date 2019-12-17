package twilightforest.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFPillar extends RotatedPillarBlock {

	protected BlockTFPillar(Material material) {
		super(Properties.create(material));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y));
	}

}
