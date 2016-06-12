package twilightforest.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFDeadrock extends Block {
	
    public static final String[] names = new String[] {"surface", "cracked", "solid"};
	private IIcon[] icons;

	protected BlockTFDeadrock() {
		super(Material.rock);
        this.setHardness(100F);
		this.setResistance(6000000.0F);
		this.setStepSound(soundTypePiston);
		this.disableStats();
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
    	this.icons = new IIcon[names.length];
    	
    	for (int i = 0; i < names.length; i++) {
    		this.icons[i] = iconRegister.registerIcon(TwilightForestMod.ID + ":deadrock_" + names[i]);
    	}
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	if (meta > this.names.length) {
    		meta = 0;
    	}
    	
    	return this.icons[meta];
    }
    
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for (int i = 0; i < names.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
    	}
    }
	
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta;
    }
}
