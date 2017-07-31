package twilightforest.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTFAuroraBrick extends Block {
	
	private static IIcon[] icons;


	public BlockTFAuroraBrick() {
		super(Material.packedIce);
		
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);

	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		BlockTFAuroraBrick.icons = new IIcon[8];
		BlockTFAuroraBrick.icons[0] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick0");
		BlockTFAuroraBrick.icons[1] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick1");
		BlockTFAuroraBrick.icons[2] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick2");
		BlockTFAuroraBrick.icons[3] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick3");
		BlockTFAuroraBrick.icons[4] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick4");
		BlockTFAuroraBrick.icons[5] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick5");
		BlockTFAuroraBrick.icons[6] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick6");
		BlockTFAuroraBrick.icons[7] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick7");
		
//		BlockTFAuroraBrick.icons[0] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick0");
//		BlockTFAuroraBrick.icons[1] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick2");
//		BlockTFAuroraBrick.icons[2] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick4");
//		BlockTFAuroraBrick.icons[3] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":aurorabrick6");

	}


    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < 8) {
			return icons[meta]; 
		} else {
			return icons[15 - meta];
		}
		
//		switch (meta % 8) {
//		default:
//			return icons[(meta % 8) * 2];
//		case 4:
//		case 5:
//		case 6:
//		case 7:
//			return icons[(7 - (meta % 8)) * 2];
//		}
		
	}

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z)
	{
		int red = 0;
		int green = 0;
		int blue = 0;


		// aurora fade
		red = 16;

		blue = x * 12 + z * 6;
		if ((blue & 256) != 0){
			blue = 255 - (blue & 255);
		}
		blue ^= 255;
		blue &= 255;


		green = x * 4 + z * 8;
		if ((green & 256) != 0){
			green = 255 - (green & 255);
		}
		green &= 255;

		// don't let things get black
		if (green + blue < 128){
			green = 128 - blue;
		}

		return red << 16 | blue << 8 | green;
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
	
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return 0;
	}
    
    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return Math.abs(x + z) % 16;
    }
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	world.setBlockMetadataWithNotify(x, y, z, Math.abs(x + z) % 16, 2);
    }

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 20;
    }
}
