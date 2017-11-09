package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemTFCompressed extends ItemBlockTFMeta {
	public ItemTFCompressed(Block block) {
		super(block);
	}

	@Override
	public int getItemBurnTime(ItemStack stack) {
		/* vanilla has dumb logic that sets burn time to 300 for all metas
		 if default state material is wood
		 todo assign proper values here?
		 */
		return 0;
	}
}
