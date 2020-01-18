package twilightforest.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import twilightforest.block.TFBlocks;

public class CreativeTabTwilightForest extends ItemGroup {

	public CreativeTabTwilightForest(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(TFBlocks.twilight_portal_miniature_structure.get());
	}
}
