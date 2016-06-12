package twilightforest.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.ItemTFMazebreakerPick;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



/**
 * 
 * Mazestone mimics other types of stone in appearance, but is much harder to mine
 * 
 * @author Ben
 *
 */
public class BlockTFMazestone extends Block {
	
	private static IIcon TEX_PLAIN;
	private static IIcon TEX_BRICK;
	private static IIcon TEX_PILLAR;
	private static IIcon TEX_DECO;
	private static IIcon TEX_CRACKED;
	private static IIcon TEX_MOSSY;
	private static IIcon TEX_MOSAIC;
	private static IIcon TEX_BORDER;

	/**
	 * Note that the texture called for here will only be used when the meta value is not a good block to mimic
	 * 
	 * @param id
	 * @param texture
	 */
    public BlockTFMazestone()
    {
        super(Material.ROCK);
        this.setHardness(100F);
        this.setResistance(5F);
        this.setStepSound(Block.soundTypeStone);
		this.setCreativeTab(TFItems.creativeTab);

    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta)
		{
		case 0:
		default:
			return TEX_PLAIN;
		case 1:
			return TEX_BRICK;
		case 2:
			return side > 1 ? TEX_PILLAR : TEX_PLAIN;
		case 3:
			return side > 1 ? TEX_DECO : TEX_BRICK;
		case 4:
			return TEX_CRACKED;
		case 5:
			return TEX_MOSSY;
		case 6:
			return side > 1 ? TEX_BRICK : TEX_MOSAIC;
		case 7:
			return side > 1 ? TEX_BRICK : TEX_BORDER;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		BlockTFMazestone.TEX_PLAIN = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_plain");
		BlockTFMazestone.TEX_BRICK = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_brick");
		BlockTFMazestone.TEX_PILLAR = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_pillar");
		BlockTFMazestone.TEX_DECO = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_decorative");
		BlockTFMazestone.TEX_CRACKED = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_cracked");
		BlockTFMazestone.TEX_MOSSY = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_mossy");
		BlockTFMazestone.TEX_MOSAIC = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_mosaic");
		BlockTFMazestone.TEX_BORDER = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mazestone_border");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		// damage the player's pickaxe
    	ItemStack cei = entityplayer.getCurrentEquippedItem();
        if(cei != null && cei.getItem() instanceof ItemTool && !(cei.getItem() instanceof ItemTFMazebreakerPick)) 
        {
            cei.damageItem(16, entityplayer);
        }
    	
		super.harvestBlock(world, entityplayer, x, y, z, meta);
    }
    
    
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
        par3List.add(new ItemStack(par1, 1, 6));
        par3List.add(new ItemStack(par1, 1, 7));
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return meta;
	}

}
