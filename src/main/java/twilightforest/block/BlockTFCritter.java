package twilightforest.block;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public abstract class BlockTFCritter extends Block {
	

	
    protected BlockTFCritter()
    {
        super(Material.circuits);
		this.setHardness(0.0F);
		this.setCreativeTab(TFItems.creativeTab);
		
		this.stepSound = new StepSoundTFInsect("squish", 0.25F, 0.6F);
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) 
    {
    	int facing = world.getBlockMetadata(x, y, z) & 7;
    	float wide = 0.15F;
    	if (facing == 1)
    	{
    		setBlockBounds(0.0F, 0.2F, 0.5F - wide, wide * 2.0F, 0.8F, 0.5F + wide);
    	} 
    	else if(facing == 2)
    	{
    		setBlockBounds(1.0F - wide * 2.0F, 0.2F, 0.5F - wide, 1.0F, 0.8F, 0.5F + wide);
    	} 
    	else if(facing == 3)
    	{
    		setBlockBounds(0.5F - wide, 0.2F, 0.0F, 0.5F + wide, 0.8F, wide * 2.0F);
    	} 
    	else if(facing == 4)
    	{
    		setBlockBounds(0.5F - wide, 0.2F, 1.0F - wide * 2.0F, 0.5F + wide, 0.8F, 1.0F);
    	} 
    	else if(facing == 5)
    	{
    		setBlockBounds(0.5F - wide, 0.0F, 0.2F, 0.5F + wide, wide * 2.0F, 0.8F);
    	} 
    	else if(facing == 6)
    	{
    		setBlockBounds(0.5F - wide, 1.0F - wide * 2.0F, 0.2F, 0.5F + wide, 1.0F, 0.8F);
    	} 
    	else
    	{
    		float f1 = 0.1F;
    		setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
    	}   
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
    /**
     * The type of render function that is called for this block
     */
	@Override
	public int getRenderType()
	{
		return TwilightForestMod.proxy.getCritterBlockRenderID();
	}
	

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (canPlaceAt(world, x - 1, y, z)) {
			return true;
		} else if (canPlaceAt(world, x + 1, y, z)) {
			return true;
		} else if (canPlaceAt(world, x, y, z - 1)) {
			return true;
		} else if (canPlaceAt(world, x, y, z + 1)) {
			return true;
		} else if (canPlaceAt(world, x, y - 1, z)) {
			return true;
		} else if (canPlaceAt(world, x, y + 1, z)) {
			return true;
		} else {
			return false;
		}
	}

	
	@Override
    public int onBlockPlaced(World par1World, int x, int y, int z, int placementFacing, float par6, float par7, float par8, int meta)
    {
        if (placementFacing == 1 && this.canPlaceAt(par1World, x, y - 1, z))
        {
            meta = 5;
        }

        if (placementFacing == 0 && this.canPlaceAt(par1World, x, y + 1, z))
        {
            meta = 6;
        }

        if (placementFacing == 2 && par1World.isSideSolid(x, y, z + 1, NORTH, true))
        {
            meta = 4;
        }

        if (placementFacing == 3 && par1World.isSideSolid(x, y, z - 1, SOUTH, true))
        {
            meta = 3;
        }

        if (placementFacing == 4 && par1World.isSideSolid(x + 1, y, z, WEST, true))
        {
            meta = 2;
        }

        if (placementFacing == 5 && par1World.isSideSolid(x - 1, y, z, EAST, true))
        {
            meta = 1;
        }
        
        return meta;
    }

    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	if (world.getBlockMetadata(x, y, z) == 0)
    	{
    		if (canPlaceAt(world, x - 1, y, z)) {
    			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
    		} 
    		else if (canPlaceAt(world, x + 1, y, z)) {
    			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
    		} 
    		else if (canPlaceAt(world, x, y, z - 1)) {
    			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
    		} 
    		else if (canPlaceAt(world, x, y, z + 1)) {
    			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
    		} 
    		else if (canPlaceAt(world, x, y - 1, z)) {
    			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
    		}
    		else if (canPlaceAt(world, x, y + 1, z)) {
    			world.setBlockMetadataWithNotify(x, y, z, 6, 2);
    		}
    	}    	

        dropCritterIfCantStay(world, x, y, z);
        
        // for fireflies, schedule a lighting update 
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0) {
        	world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
        }
    }

    public boolean dropCritterIfCantStay(World world, int x, int y, int z)
    {
        if(!canPlaceBlockAt(world, x, y, z))
        {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block blockID)
    {
        if(dropCritterIfCantStay(world, x, y, z))
        {
            int facing = world.getBlockMetadata(x, y, z) & 7;
            
            boolean flag = false;
            if (!canPlaceAt(world, x - 1, y, z) && facing == 1) {
                flag = true;
            }
            if (!canPlaceAt(world, x + 1, y, z) && facing == 2) {
                flag = true;
            }
            if (!canPlaceAt(world, x, y, z - 1) && facing == 3) {
                flag = true;
            }
            if (!canPlaceAt(world, x, y, z + 1) && facing == 4) {
                flag = true;
            }
            if (!canPlaceAt(world, x, y - 1, z) && facing == 5) {
                flag = true;
            }
            if (!canPlaceAt(world, x, y + 1, z) && facing == 6) {
                flag = true;
            }
            if (flag) {
                dropBlockAsItem(world, x, y, z, 0, 0);
                world.setBlockToAir(x, y, z);
            }
        }
    }


    /**
     * We can place fireflies on any normal block or on leaves.
     */
    public boolean canPlaceAt(World world, int x, int y, int z)
    {
    	return world.isBlockNormalCubeDefault(x, y, z, true) || world.getBlock(x, y, z).getMaterial() == Material.leaves || world.getBlock(x, y, z).getMaterial() == Material.cactus;
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
    public boolean hasTileEntity(int metadata) {
    	return true;
    }
    
    /**
     * Called throughout the code as a replacement for BlockContainer.getBlockEntity
     * Return the same thing you would from that function.
     * This will fall back to BlockContainer.getBlockEntity if this block is a BlockContainer.
     * 
     * @param metadata The Metadata of the current block
     * @return A instance of a class extending TileEntity
     */
    @Override
    public abstract TileEntity createTileEntity(World world, int metadata);

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }

	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
	
	
