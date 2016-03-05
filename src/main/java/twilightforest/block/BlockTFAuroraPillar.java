package twilightforest.block;

import java.awt.Color;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTFAuroraPillar extends BlockRotatedPillar {

	private IIcon sideIcon;
	private IIcon topIcon;

	protected BlockTFAuroraPillar() {
		super(Material.packedIce);
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
	}
	

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
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
	
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return this.colorMultiplier(null, 16, 0, 16);
	}

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.getBlockColor();
    }


    @SideOnly(Side.CLIENT)
	@Override
    protected IIcon getSideIcon(int meta)
    {
        return this.sideIcon;
    }

    @SideOnly(Side.CLIENT)
	@Override
    protected IIcon getTopIcon(int p_150161_1_)
    {
        return this.topIcon;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.sideIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurora_pillar_side");
		this.topIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurora_pillar_top");
	}

}
