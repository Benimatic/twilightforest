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
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFMagicLog extends BlockLog {

	public static final int META_TIME = 0;
	public static final int META_TRANS = 1;
	public static final int META_MINE = 2;
	public static final int META_SORT = 3;
    
    public static IIcon SPR_TIMESIDE;
    public static IIcon SPR_TIMETOP;
    public static IIcon SPR_TIMECLOCK;
    public static IIcon SPR_TIMECLOCKOFF;
    public static IIcon SPR_TRANSSIDE;
    public static IIcon SPR_TRANSTOP;
    public static IIcon SPR_TRANSHEART;
    public static IIcon SPR_TRANSHEARTOFF;
    public static IIcon SPR_MINESIDE;
    public static IIcon SPR_MINETOP;
    public static IIcon SPR_MINEGEM;
    public static IIcon SPR_MINEGEMOFF;
    public static IIcon SPR_SORTSIDE;
    public static IIcon SPR_SORTTOP;
    public static IIcon SPR_SORTEYE;
    public static IIcon SPR_SORTEYEOFF;

    
	protected BlockTFMagicLog() {
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
        case META_TIME :
        	return orient == 0 && (side == 1 || side == 0) ? SPR_TIMETOP : (orient == 4 && (side == 5 || side == 4) ? SPR_TIMETOP : (orient == 8 && (side == 2 || side == 3) ? SPR_TIMETOP : SPR_TIMESIDE)); 
        case META_TRANS :
        	return orient == 0 && (side == 1 || side == 0) ? SPR_TRANSTOP : (orient == 4 && (side == 5 || side == 4) ? SPR_TRANSTOP : (orient == 8 && (side == 2 || side == 3) ? SPR_TRANSTOP : SPR_TRANSSIDE)); 
        case META_MINE :
        	return orient == 0 && (side == 1 || side == 0) ? SPR_MINETOP : (orient == 4 && (side == 5 || side == 4) ? SPR_MINETOP : (orient == 8 && (side == 2 || side == 3) ? SPR_MINETOP : SPR_MINESIDE)); 
        case META_SORT :
        	return orient == 0 && (side == 1 || side == 0) ? SPR_SORTTOP : (orient == 4 && (side == 5 || side == 4) ? SPR_SORTTOP : (orient == 8 && (side == 2 || side == 3) ? SPR_SORTTOP : SPR_SORTSIDE)); 
        }
        
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFMagicLog.SPR_TIMESIDE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":time_side");
        BlockTFMagicLog.SPR_TIMETOP = par1IconRegister.registerIcon(TwilightForestMod.ID + ":time_section");
        BlockTFMagicLog.SPR_TIMECLOCK = par1IconRegister.registerIcon(TwilightForestMod.ID + ":time_clock");
        BlockTFMagicLog.SPR_TIMECLOCKOFF = par1IconRegister.registerIcon(TwilightForestMod.ID + ":time_clock_off");
        BlockTFMagicLog.SPR_TRANSSIDE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":trans_side");
        BlockTFMagicLog.SPR_TRANSTOP = par1IconRegister.registerIcon(TwilightForestMod.ID + ":trans_section");
        BlockTFMagicLog.SPR_TRANSHEART = par1IconRegister.registerIcon(TwilightForestMod.ID + ":trans_heart");
        BlockTFMagicLog.SPR_TRANSHEARTOFF = par1IconRegister.registerIcon(TwilightForestMod.ID + ":trans_heart_off");
        BlockTFMagicLog.SPR_MINESIDE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mine_side");
        BlockTFMagicLog.SPR_MINETOP = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mine_section");
        BlockTFMagicLog.SPR_MINEGEM = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mine_gem");
        BlockTFMagicLog.SPR_MINEGEMOFF = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mine_gem_off");
        BlockTFMagicLog.SPR_SORTSIDE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":sort_side");
        BlockTFMagicLog.SPR_SORTTOP = par1IconRegister.registerIcon(TwilightForestMod.ID + ":sort_section");
        BlockTFMagicLog.SPR_SORTEYE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":sort_eye");
        BlockTFMagicLog.SPR_SORTEYEOFF = par1IconRegister.registerIcon(TwilightForestMod.ID + ":sort_eye_off");
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(this); // hey that's my block ID!
    }

    @Override
	@SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) 
    {
    	
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	par3List.add(new ItemStack(par1, 1, 0));
    	par3List.add(new ItemStack(par1, 1, 1));
    	par3List.add(new ItemStack(par1, 1, 2));
    	par3List.add(new ItemStack(par1, 1, 3));

    }
    

}
