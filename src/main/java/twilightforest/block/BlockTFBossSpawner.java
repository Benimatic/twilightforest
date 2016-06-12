package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFHydraSpawner;
import twilightforest.tileentity.TileEntityTFKnightPhantomsSpawner;
import twilightforest.tileentity.TileEntityTFLichSpawner;
import twilightforest.tileentity.TileEntityTFNagaSpawner;
import twilightforest.tileentity.TileEntityTFSnowQueenSpawner;
import twilightforest.tileentity.TileEntityTFTowerBossSpawner;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class BlockTFBossSpawner extends BlockContainer {

	protected BlockTFBossSpawner()
	{
		super(Material.rock);
		this.setHardness(20F);
		//this.setResistance(10F);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
	
    /**
     * Called throughout the code as a replacement for block instanceof BlockContainer
     * Moving this to the Block base class allows for mods that wish to extend vinella
     * blocks, and also want to have a tile entity on that block, may.
     *
     * Return true from this function to specify this block has a tile entity.
     *
     * @param metadata Metadata of the current block
     * @return True if block has a tile entity, false otherwise
     */
	@Override
	public boolean hasTileEntity(int metadata) 
	{
		return true;
	}

	
	/**
	 * This is where we actually give out our tile entity
	 */
	@Override
	public TileEntity createTileEntity(World world, int metadata) 
    {
		if (metadata == 0) 
		{
			return new TileEntityTFNagaSpawner();
		}
		else if (metadata == 1)
		{
			return new TileEntityTFLichSpawner();
		}
		else if (metadata == 2) 
		{
			return new TileEntityTFHydraSpawner();
		}
		else if (metadata == 3) 
		{
			return new TileEntityTFTowerBossSpawner();
		}
		else if (metadata == 4) 
		{
			return new TileEntityTFKnightPhantomsSpawner();
		}
		else if (metadata == 5) 
		{
			return new TileEntityTFSnowQueenSpawner();
		}
		else 
		{
			return null;
		}
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return createTileEntity(var1, var2);
	}


	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;
	}

	/**
	 * quantity dropped
	 */
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	
 	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        //par3List.add(new ItemStack(par1, 1, 0));
        //par3List.add(new ItemStack(par1, 1, 1));
        //par3List.add(new ItemStack(par1, 1, 2));
        //par3List.add(new ItemStack(par1, 1, 3));
        //par3List.add(new ItemStack(par1, 1, 4));
    }


    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int metadata)
    {
        return Blocks.MOB_SPAWNER.getIcon(side, metadata);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        ; // don't load anything
    }
}
