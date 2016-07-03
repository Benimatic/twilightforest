package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraft.util.math.BlockPos;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFSlideBlock;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTFSlider extends BlockRotatedPillar {
	
	private static final int TICK_TIME = 80;
	private static final int OFFSET_TIME = 20;
	private static final int PLAYER_RANGE = 32;
	private static final float BLOCK_DAMAGE = 5;
	private IIcon horiIcon;
	private IIcon vertIcon;
	private IIcon topIcon;

	protected BlockTFSlider() {
		super(Material.IRON);
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
	}

    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	int rotation = meta & 12;
		float pixel = 0.0625F;
    	float inset = 5F;
    	
		switch (rotation) {
    	case 0:
    	default:
        	return new AxisAlignedBB(x + pixel * inset, y, z + pixel * inset, x + 1F - pixel * inset, y + 1F, z + 1F - pixel * inset);
    	case 4:
        	return new AxisAlignedBB(x, y + pixel * inset, z + pixel * inset, x + 1F, y + 1F - pixel * inset, z + 1F - pixel * inset);
    	case 8:
        	return new AxisAlignedBB(x + pixel * inset, y + pixel * inset, z, x + 1F - pixel * inset, y + 1F - pixel * inset, z + 1F);
    	}

    }
    

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        return this.getCollisionBoundingBox(state, world, pos);
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	setBlockBoundsBasedOnMeta(meta);
    }

	public void setBlockBoundsBasedOnMeta(int meta) {
		int rotation = meta & 12;
		float pixel = 0.0625F;
    	float inset = 5F;
    	
		switch (rotation) {
    	case 0:
    	default:
        	this.setBlockBounds(pixel * inset, 0, pixel * inset, 1F - pixel * inset, 1F, 1F - pixel * inset);
        	break;
    	case 4:
    		this.setBlockBounds(0, pixel * inset, pixel * inset, 1F, 1F - pixel * inset, 1F - pixel * inset);
    		break;
    	case 8:
    		this.setBlockBounds(pixel * inset, pixel * inset, 0, 1F - pixel * inset, 1F - pixel * inset, 1F);
    		break;
    	}
	}
    
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 0;
    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        int rotation = meta & 12;
        
        if (rotation == 0) {
        	switch (side) {
        	case 0:
        	case 1:
        		return this.topIcon;
        	default:
        		return this.vertIcon;
        	}
        } else if (rotation == 4) {
        	switch (side) {
        	case 4:
        	case 5:
        		return this.topIcon;
        	default:
        		return this.horiIcon;
        	}
        } else { // rotation == 8
        	switch (side) {
        	case 2:
        	case 3:
        		return this.topIcon;
        	case 0:
        	case 1:     
        		return this.vertIcon;
        	default:
        		return this.horiIcon;
        	}
        }
        
        
//        int type = 0;
//        return rotation == 0 && (side == 1 || side == 0) ? this.getTopIcon(type) : (rotation == 4 && (side == 5 || side == 4) ? this.getTopIcon(type) : (rotation == 8 && (side == 2 || side == 3) ? this.getTopIcon(type) : this.getSideIcon(type)));
    }

    @SideOnly(Side.CLIENT)
	@Override
    protected IIcon getSideIcon(int meta)
    {
        if ((meta & 12) == 0) {
            return this.horiIcon;
        } else if ((meta & 12) == 8) {
            return this.horiIcon;
        }
        return this.vertIcon;
    }

    @SideOnly(Side.CLIENT)
	@Override
    protected IIcon getTopIcon(int p_150161_1_)
    {
        return this.topIcon;
    }
    
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.horiIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":slider_h");
		this.vertIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":slider_v");
		this.topIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":slider_top");
	}
	
    @Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random par5Random)
    {
    	if (!world.isRemote && this.isConnectedInRange(world, x, y, z))
    	{
    		//world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, TwilightForestMod.ID + ":random.creakstart", 0.75F, 1.5F);
    		
    		EntityTFSlideBlock slideBlock = new EntityTFSlideBlock(world, x + 0.5, y, z + 0.5, this, world.getBlockMetadata(x, y, z));
            world.spawnEntityInWorld(slideBlock);
    		
    	}
    	
		scheduleBlockUpdate(world, x, y, z);

    }
    
    /**
     * Check if there is any players in range, and also recursively check connected blocks
     */
    public boolean isConnectedInRange(World world, int x, int y, int z) {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if ((meta & 12) == 0) {
    		return this.anyPlayerInRange(world, x, y, z) || this.isConnectedInRangeRecursive(world, x, y, z, ForgeDirection.UP) || this.isConnectedInRangeRecursive(world, x, y, z, ForgeDirection.DOWN);
    	} else if ((meta & 12) == 4) {
    		return this.anyPlayerInRange(world, x, y, z) || this.isConnectedInRangeRecursive(world, x, y, z, ForgeDirection.WEST) || this.isConnectedInRangeRecursive(world, x, y, z, ForgeDirection.EAST);
    	} else if ((meta & 12) == 8) {
    		return this.anyPlayerInRange(world, x, y, z) || this.isConnectedInRangeRecursive(world, x, y, z, ForgeDirection.NORTH) || this.isConnectedInRangeRecursive(world, x, y, z, ForgeDirection.SOUTH);
    	} else {
    		// why are we here?
    		return this.anyPlayerInRange(world, x, y, z);
    	}
    }

	private boolean isConnectedInRangeRecursive(World world, int x, int y, int z, ForgeDirection dir) {
		// where is the coords we're talking about
		int dx = x + dir.offsetX;
		int dy = y + dir.offsetY;
		int dz = z + dir.offsetZ;
		
		// are the blocks connected?  (block and meta are the same
		if (world.getBlock(x, y, z) == world.getBlock(dx, dy, dz) && world.getBlockMetadata(x, y, z) == world.getBlockMetadata(dx, dy, dz)) {
			return this.anyPlayerInRange(world, dx, dy, dz) || this.isConnectedInRangeRecursive(world, dx, dy, dz, dir);
		} else {
			return false;
		}
	}

	/**
     * Returns true if there is a player in range (using World.getClosestPlayer)
	 * @param world 
	 * @param z 
	 * @param y 
	 * @param x 
     */
    public boolean anyPlayerInRange(World world, int x, int y, int z) {
        return world.getClosestPlayer((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, PLAYER_RANGE) != null;
    }

	public void scheduleBlockUpdate(World world, int x, int y, int z) {
		int offset = world.getBlockMetadata(x, y, z) & 3;
		int update = TICK_TIME - ((int)(world.getWorldTime() - (offset * OFFSET_TIME)) % TICK_TIME);
		world.scheduleBlockUpdate(x, y, z, this, update);
		
		//System.out.println("The current world time is " + world.getWorldTime() + " so update scheduled for " + update + " ticks.");
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		scheduleBlockUpdate(world, x, y, z);
	}
	
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }

	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
    	entity.attackEntityFrom(DamageSource.generic, BLOCK_DAMAGE);
    	if (entity instanceof EntityLivingBase) {
			double kx = (x + 0.5 - entity.posX) * 2.0;
			double kz = (z + 0.5 - entity.posZ) * 2.0;
			
			((EntityLivingBase) entity).knockBack(null, 5, kx, kz);
    	}
    }
}
