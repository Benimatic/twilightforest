package twilightforest.block;

import java.util.List;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTFHugeLilyPad extends BlockBush {
	
	private IIcon pad1;
	private IIcon pad2;
	private IIcon pad3;
	private IIcon blank;
	private boolean isSelfDestructing = false;


	protected BlockTFHugeLilyPad() {
		super(Material.PLANTS);
		
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setStepSound(soundTypeGrass);

		this.setCreativeTab(TFItems.creativeTab);
	}
	
    public int getRenderType()
    {
    	return TwilightForestMod.proxy.getHugeLilyPadBlockRenderID();
    }
    
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
    

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		// sides blank
		if (side > 1) {
			return this.blank;
		}
		
		int orient = meta >> 2;
		int piece = meta & 3;
		
        // why can't this just be simple?
		if (orient == 1) {
			orient = 3;
		} else if (orient == 3) {
			orient = 1;
		}
		
		
		int display = (piece + orient) % 4;
		

		switch (display) {
		case 0:
		default:
			return this.blockIcon;
		case 1:
			return this.pad1;
		case 2:
			return this.pad2;
		case 3:
			return this.pad3;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_0");
		this.pad1 = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_1");
		this.pad2 = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_2");
		this.pad3 = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_3");
		this.blank = par1IconRegister.registerIcon(TwilightForestMod.ID + ":blank");
	}
	
    /**
     * is the block grass, dirt or farmland
     */
    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.WATER;
    }

	@Override
    public void onBlockHarvested(World world, BlockPos ois, IBlockState state, EntityPlayer player) {
		this.setGiantBlockToAir(world, x, y, z);
    }

    @Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        world.setBlockToAir(x, y, z);
		this.setGiantBlockToAir(world, x, y, z);
    }

    
    /**
     * Called on server worlds only when the block is about to be replaced by a different block or the same block with a
     * different metadata value. Args: world, x, y, z, old metadata
     */
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
    	
		int orient = meta >> 2;
		int piece = meta & 3;
		
		int display = (piece + orient) % 4;
    	
    	if (!this.isSelfDestructing  && !canBlockStay(world, x, y, z)) {
    		this.setGiantBlockToAir(world, x, y, z);
    	}
    }
    
    /**
     * Set the whole giant block area to air
     */
    private void setGiantBlockToAir(World world, int x, int y, int z) {
    	// this flag is maybe not totally perfect
    	this.isSelfDestructing = true;
    	
    	int bx = (x >> 1) << 1;
    	int bz = (z >> 1) << 1;

    	// this is the best loop over 3 items that I've ever programmed!
    	for (int dx = 0; dx < 2; dx++) {
    		for (int dz = 0; dz < 2; dz++) {
    			if (!(x == bx + dx && z == bz + dz)) {
    				if (world.getBlock(bx + dx, y, bz + dz) == this) {
    					world.setBlock(bx + dx, y, bz + dz, Blocks.AIR, 0, 2);
    				}
    			}
    		}
    	}

    	this.isSelfDestructing = false;
	}


	/**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World world, int x, int y, int z)  {
        boolean allThisBlock = true;
        boolean allWater = true;
        
    	int bx = (x >> 1) << 1;
    	int bz = (z >> 1) << 1;

    	for (int dx = 0; dx < 2; dx++) {
    		for (int dz = 0; dz < 2; dz++) {
    			allThisBlock &= world.getBlock(bx + dx, y, bz + dz) == this;
    			allWater &= (world.getBlock(bx + dx, y - 1, bz + dz).getMaterial() == Material.WATER && world.getBlockMetadata(bx + dx, y - 1, bz + dz) == 0);
    		}
    	}

    	return allThisBlock && allWater;
    }
    
    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
    {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
        {
            //this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
        }
    }

	@Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }
}
