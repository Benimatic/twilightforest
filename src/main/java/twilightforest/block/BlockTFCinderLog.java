package twilightforest.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;

public class BlockTFCinderLog extends BlockLog {

	private IIcon topIcon;
	private IIcon cornerIcon;


	protected BlockTFCinderLog() {
		super();
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(TFItems.creativeTab);
	}
	

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
        int orient = meta & 12;
        
        return orient == 12 ? this.cornerIcon : (orient == 0 && (side == 1 || side == 0) ? this.topIcon : (orient == 4 && (side == 5 || side == 4) ? this.topIcon : (orient == 8 && (side == 2 || side == 3) ? this.topIcon : blockIcon))); 
     }
    

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	this.blockIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":cinder_side");
    	this.topIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":cinder_top");
    	this.cornerIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":cinder_corner");
    }


    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(TFBlocks.cinderLog); // hey that's my block ID!
    }


}
