package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFThorns extends BlockRotatedPillar {

    private static final float THORN_DAMAGE = 4.0F;
    
    private String[] names;
	private IIcon sideIcons[];
	private IIcon topIcons[];

	protected BlockTFThorns() {
		super(Material.WOOD);
		
		this.setNames(new String[] {"brown", "green"});
		
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    @Override
	public int getRenderType(IBlockState state)
    {
    	return TwilightForestMod.proxy.getThornsBlockRenderID();
    }
    
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	int rotation = meta & 12;
		float pixel = 0.0625F;

    	switch (rotation) {
    	case 0:
    	default:
        	return new AxisAlignedBB(x + pixel * 3F, y, z + pixel * 3F, x + 1F - pixel * 3F, y + 1F, z + 1F - pixel * 3F);
    	case 4:
        	return new AxisAlignedBB(x, y + pixel * 3F, z + pixel * 3F, x + 1F, y + 1F - pixel * 3F, z + 1F - pixel * 3F);
    	case 8:
        	return new AxisAlignedBB(x + pixel * 3F, y + pixel * 3F, z, x + 1F - pixel * 3F, y + 1F - pixel * 3F, z + 1F);
    	}

    }

	@Override
    @SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        return this.getCollisionBoundingBox(state, world, pos);
    }

	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
    	entity.attackEntityFrom(DamageSource.cactus, THORN_DAMAGE);
    }

	@Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean harvest) {
    	int meta = world.getBlockMetadata(x, y, z);
    	if (!player.capabilities.isCreativeMode) {
    		if (!world.isRemote) {
    			// grow back
    			world.setBlock(x, y, z, this, (meta & 12) | 1, 2);
    			// grow more
    			this.doThornBurst(world, x, y, z, meta);
    		}
    	} else {
            world.setBlockToAir(x, y, z);
    	}
    	
    	return true;
    }

	@Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }

    /**
     * Grow thorns out of both the ends, then maybe in another direction too
     */
    private void doThornBurst(World world, int x, int y, int z, int meta) {
		int rotation = meta & 12;
		
		switch (rotation) {
		case 0:
			growThorns(world, x, y, z, ForgeDirection.UP);
			growThorns(world, x, y, z, ForgeDirection.DOWN);
			break;
		case 4:
			growThorns(world, x, y, z, ForgeDirection.EAST);
			growThorns(world, x, y, z, ForgeDirection.WEST);
			break;
		case 8:
			growThorns(world, x, y, z, ForgeDirection.NORTH);
			growThorns(world, x, y, z, ForgeDirection.SOUTH);
			break;
		}
		
		// also try three random directions
		growThorns(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[world.rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)]);
		growThorns(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[world.rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)]);
		growThorns(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[world.rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)]);

	}

    /**
     * grow several green thorns in the specified direction
     */
	private void growThorns(World world, int x, int y, int z, ForgeDirection dir) {
		int length = 1 + world.rand.nextInt(3);
		
		for (int i = 1; i < length; i++) {
			int dx = x + (dir.offsetX * i);
			int dy = y + (dir.offsetY * i);
			int dz = z + (dir.offsetZ * i);
 
			if (world.isAirBlock(dx, dy, dz)) {
				world.setBlock(dx, dy, dz, this, getMetaFor(dir) | 1, 2);
			} else {
				break;
			}
		}
	}

	/**
	 * Get the meta we need to place a new block in the specified direction
	 * @param dir
	 * @return
	 */
	public static int getMetaFor(ForgeDirection dir) {
		switch (dir) {
		case UNKNOWN:
		default:
		case UP:
		case DOWN:
			return 0;
		case EAST:
		case WEST:
			return 4;
		case NORTH:
		case SOUTH:
			return 8;
		}
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        byte range = 4;
        int exRange = range + 1;

        if (world.checkChunksExist(x - exRange, y - exRange, z - exRange, x + exRange, y + exRange, z + exRange))
        {
            for (int dx = -range; dx <= range; ++dx)
            {
                for (int dy = -range; dy <= range; ++dy)
                {
                    for (int dz = -range; dz <= range; ++dz)
                    {
                        Block block = world.getBlock(x + dx, y + dy, z + dz);
                        if (block.isLeaves(world, x + dx, y + dy, z + dz))
                        {
                            block.beginLeavesDecay(world, x + dx, y + dy, z + dz);
                        }
                    }
                }
            }
        }
    }

	@Override
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
	@Override
	protected IIcon getSideIcon(int meta) {
		return this.sideIcons[meta & 3];
	}

    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int meta)
    {
        return this.topIcons[meta & 3];
    }
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
    	this.sideIcons = new IIcon[getNames().length]; 
    	this.topIcons = new IIcon[getNames().length];
    	
    	for (int i = 0; i < getNames().length; i++) {
    		this.sideIcons[i] = iconRegister.registerIcon(TwilightForestMod.ID + ":" + getNames()[i] + "_thorns_side");
    		this.topIcons[i] = iconRegister.registerIcon(TwilightForestMod.ID + ":" + getNames()[i] + "_thorns_top");
    	}
    }
    
    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	for (int i = 0; i < getNames().length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
    	}
    }

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
}
