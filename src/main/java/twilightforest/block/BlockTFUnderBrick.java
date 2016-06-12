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
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFUnderBrick extends Block {
	
	private static IIcon[] iconSide = new IIcon[4];
//	private static IIcon[] iconFloor = new IIcon[4];


	public BlockTFUnderBrick() {
		super(Material.rock);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeStone);
		
		this.setCreativeTab(TFItems.creativeTab);

	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
//		for (int i = 0; i < this.iconSide.length; i++)
//		{
//			this.iconFloor[i] = Blocks.STONEBRICK.getIcon(0, i);
//		}
		
		BlockTFUnderBrick.iconSide[0] =  par1IconRegister.registerIcon(TwilightForestMod.ID + ":knightbrick");
		BlockTFUnderBrick.iconSide[1] =  par1IconRegister.registerIcon(TwilightForestMod.ID + ":knightbrick_mossy");
		BlockTFUnderBrick.iconSide[2] =  par1IconRegister.registerIcon(TwilightForestMod.ID + ":knightbrick_cracked");
		
//		this.iconFloor[0] =  par1IconRegister.registerIcon(TwilightForestMod.ID + ":knightbrick_floor");
//		this.iconFloor[1] =  par1IconRegister.registerIcon(TwilightForestMod.ID + ":knightbrick_floor");
//		this.iconFloor[2] =  par1IconRegister.registerIcon(TwilightForestMod.ID + ":knightbrick_floor");
	}


    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < BlockTFUnderBrick.iconSide.length)
		{
			if (side == 0 || side == 1)
			{
				return BlockTFUnderBrick.iconSide[meta];
			}
			else
			{
				return BlockTFUnderBrick.iconSide[meta];
			}
		}
		else
		{
			if (side == 0 || side == 1)
			{
				return BlockTFUnderBrick.iconSide[0];
			}
			else
			{
				return BlockTFUnderBrick.iconSide[0];
			}		
		}
	}
	
	   /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
    	int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

    	return 0xFFFFFF;
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
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return meta;
	}
}
