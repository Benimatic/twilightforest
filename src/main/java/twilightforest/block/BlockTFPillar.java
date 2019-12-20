package twilightforest.block;

import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;

public class BlockTFPillar extends RotatedPillarBlock {

	protected BlockTFPillar(Material material, float hardness, float resistance) {
		super(Properties.create(material).hardnessAndResistance(hardness, resistance));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y));
	}

}
