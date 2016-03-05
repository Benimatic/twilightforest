package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;

public class BlockTFFluffyCloud extends Block {

	protected BlockTFFluffyCloud() {
		super(Material.packedIce);
		this.setStepSound(soundTypeCloth);
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(0.8F);
		
        this.setBlockTextureName(TwilightForestMod.ID + ":fluffy_cloud");
	}


}
