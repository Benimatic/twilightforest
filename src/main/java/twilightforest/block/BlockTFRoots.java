package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
		super(Material.WOOD);
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

    @Override
    public Item getItemDropped(IBlockState state, Random random, int j)
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
    
    @Override
	public int damageDropped(IBlockState state)
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
    
    @Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
    	switch (meta) {
    	case ROOT_META :
    		// roots drop several sticks
    		return 3 + random.nextInt(2);
    	default:
    		return super.quantityDropped(meta, fortune, random);
    	}
	}
    
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }

}
