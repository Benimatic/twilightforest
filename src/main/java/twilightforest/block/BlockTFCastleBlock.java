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
 * Castle block makes a castle
 * 
 * @author Ben
 *
 */
public class BlockTFCastleBlock extends Block {
	
	private static IIcon brickIcon;
	private static IIcon crackIcon;
	private static IIcon fadedIcon;
	private static IIcon roofIcon;

	/**
	 * Note that the texture called for here will only be used when the meta value is not a good block to mimic
	 * 
	 * @param id
	 * @param texture
	 */
    public BlockTFCastleBlock()
    {
        super(Material.rock);
        this.setHardness(100F);
        this.setResistance(15F);
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
			return brickIcon;
		case 1:
			return fadedIcon;
		case 2:
			return crackIcon;
		case 3:
			return roofIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		BlockTFCastleBlock.brickIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":castleblock_brick");
		BlockTFCastleBlock.fadedIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":castleblock_faded");
		BlockTFCastleBlock.crackIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":castleblock_cracked");
		BlockTFCastleBlock.roofIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":castleblock_roof");
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
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return meta;
	}

}
