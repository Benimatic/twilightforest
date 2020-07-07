package twilightforest.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;

public class BlockTFMagicLog extends RotatedPillarBlock {

	protected BlockTFMagicLog(AbstractBlock.Properties props) {
		super(props.hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	}
}
