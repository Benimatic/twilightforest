package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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



public class BlockTFBossSpawner extends Block {

	protected BlockTFBossSpawner()
	{
		super(Material.ROCK);
		this.setHardness(20F);
		//this.setResistance(10F);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
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
	public Item getItemDropped(IBlockState state, Random par2Random, int fortune)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
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
