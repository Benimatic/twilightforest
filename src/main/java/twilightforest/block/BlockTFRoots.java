package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFRoots extends Block {

	public BlockTFRoots() {
		super(Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	/* TODO 1.14: Move to block loot table
	@Override
	public Item getItemDropped(BlockState state, Random random, int j) {
		switch (state.getValue(VARIANT)) {
			default:
			case ROOT:
				return Items.STICK;
			case LIVEROOT:
				return TFItems.liveroot;
		}
	}

	@Override
	public int quantityDropped(BlockState state, int fortune, Random random) {
		if (state.getValue(VARIANT) == RootVariant.ROOT) {
			return 3 + random.nextInt(2);
		} else {
			return super.quantityDropped(state, fortune, random);
		}
	}
	*/
}
