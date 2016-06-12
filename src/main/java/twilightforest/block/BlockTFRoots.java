package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFRoots extends Block {
	
	public static IIcon spRootSide;
    public static IIcon spOreRootSide;

    
    public static final int ROOT_META = 0;
    public static final int OREROOT_META = 1;

	public BlockTFRoots() {
		super(Material.wood);
		this.setCreativeTab(TFItems.creativeTab);
        this.setHardness(2.0F);
        this.setStepSound(Block.soundTypeWood);
	}

	
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	if (meta == 1) {
    		return spOreRootSide;
    	}
    	else {
    		return spRootSide;
    	}
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		BlockTFRoots.spRootSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":rootblock");
		BlockTFRoots.spOreRootSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":oreroots");
	}
	
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int meta, Random random, int j)
    {
    	switch (meta) {
    	case ROOT_META :
    		// roots drop sticks
    		return Items.STICK;
    	case OREROOT_META :
    		// oreroots drop liveroot
    		return TFItems.liveRoot;
    	default:
    		return Item.getItemFromBlock(this);
    	}
    }
    

 	/**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta)
    {
    	switch (meta) {
    	case ROOT_META :
    		// roots drop sticks, no meta
    		return 0;
    	case OREROOT_META :
    		// oreroots drop liveroot, no meta
    		return 0;
    	default:
    		// set log flag on wood blocks
    		return meta | 8;
    	}
    }
    
    /**
     * Metadata and fortune sensitive version, this replaces the old (int meta, Random rand)
     * version in 1.1. 
     * 
     * @param meta Blocks Metadata
     * @param fortune Current item fortune level
     * @param random Random number generator
     * @return The number of items to drop
     */
    @Override
	public int quantityDropped(int meta, int fortune, Random random) {
    	switch (meta) {
    	case ROOT_META :
    		// roots drop several sticks
    		return 3 + random.nextInt(2);
    	default:
    		return super.quantityDropped(meta, fortune, random);
    	}
	}
    

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }

}
