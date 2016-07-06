package twilightforest.block;

import java.awt.Color;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFAuroraPillar extends BlockRotatedPillar {

	protected BlockTFAuroraPillar() {
		super(Material.PACKED_ICE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		int red = 0;
		int green = 0;
		int blue = 0;
	
		int normalColor = TFBlocks.auroraBlock.colorMultiplier(world, x, y, z);
		
		red = (normalColor >> 16) & 255; 
		blue = normalColor & 255; 
		green = (normalColor >> 8) & 255; 
		
		float[] hsb = Color.RGBtoHSB(red, blue, green, null);

		return Color.HSBtoRGB(hsb[0], hsb[1] * 0.5F, Math.min(hsb[2] + 0.4F, 0.9F));
	}
	
}
