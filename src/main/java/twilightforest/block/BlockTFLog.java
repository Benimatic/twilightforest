package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFLog extends BlockLog {

    
    public static IIcon sprOakSide;
    public static IIcon sprOakTop;
    public static IIcon sprCanopySide;
    public static IIcon sprCanopyTop;
    public static IIcon sprMangroveSide;
    public static IIcon sprMangroveTop;
    public static IIcon sprDarkwoodSide;
    public static IIcon sprDarkwoodTop;

    //public static int sprRottenSide = 15;
	
	protected BlockTFLog() {
		super();
		this.setHardness(2.0F);
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
        int woodType = meta & 3;
        
        switch (woodType)
        {
        default:
        case 0 :
        	return orient == 0 && (side == 1 || side == 0) ? sprOakTop : (orient == 4 && (side == 5 || side == 4) ? sprOakTop : (orient == 8 && (side == 2 || side == 3) ? sprOakTop : sprOakSide)); 
        case 1 :
        	return orient == 0 && (side == 1 || side == 0) ? sprCanopyTop : (orient == 4 && (side == 5 || side == 4) ? sprCanopyTop : (orient == 8 && (side == 2 || side == 3) ? sprCanopyTop : sprCanopySide)); 
        case 2 :
        	return orient == 0 && (side == 1 || side == 0) ? sprMangroveTop : (orient == 4 && (side == 5 || side == 4) ? sprMangroveTop : (orient == 8 && (side == 2 || side == 3) ? sprMangroveTop : sprMangroveSide)); 
        case 3 :
        	return orient == 0 && (side == 1 || side == 0) ? sprDarkwoodTop : (orient == 4 && (side == 5 || side == 4) ? sprDarkwoodTop : (orient == 8 && (side == 2 || side == 3) ? sprDarkwoodTop : sprDarkwoodSide)); 
        }
     }
    

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFLog.sprOakSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":oak_side");
        BlockTFLog.sprOakTop = par1IconRegister.registerIcon(TwilightForestMod.ID + ":oak_top");
        BlockTFLog.sprCanopySide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":canopy_side");
        BlockTFLog.sprCanopyTop = par1IconRegister.registerIcon(TwilightForestMod.ID + ":canopy_top");
        BlockTFLog.sprMangroveSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mangrove_side");
        BlockTFLog.sprMangroveTop = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mangrove_top");
        BlockTFLog.sprDarkwoodSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":darkwood_side");
        BlockTFLog.sprDarkwoodTop = par1IconRegister.registerIcon(TwilightForestMod.ID + ":darkwood_top");
    }


    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(TFBlocks.log); // hey that's my block ID!
    }
    
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }

}
