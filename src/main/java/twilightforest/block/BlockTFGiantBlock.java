package twilightforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTFGiantBlock extends Block {

	private IIcon[][][] giantIcon;
	private Block baseBlock;
	private boolean isSelfDestructing;

	public BlockTFGiantBlock(Block baseBlock) {
		super(baseBlock.getMaterial());
		this.setStepSound(baseBlock.stepSound);

		this.baseBlock = baseBlock;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.giantIcon = new GiantBlockIcon[4][4][6];

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int side = 0; side < 6; side++) {
					this.giantIcon[x][y][side] = new GiantBlockIcon(this.baseBlock.getBlockTextureFromSide(side), x, y);
				}
			}
		}
	}
	
	

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        // return an icon from the icon matrix!
    	switch (side) {
    	case 0:
    	case 1:
    	default:
        	return this.giantIcon[x & 3][z & 3][side];
    	//case 1:
        	//return this.giantIcon[x & 3][z & 3][side];
    	case 2:
        	return this.giantIcon[3 - (x & 3)][3 - (y & 3)][side];
    	case 3:
        	return this.giantIcon[x & 3][3 - (y & 3)][side];
    	case 4:
        	return this.giantIcon[z & 3][3 - (y & 3)][side];
    	case 5:
        	return this.giantIcon[3 - (z & 3)][3 - (y & 3)][side];
    	}
    	
    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return this.giantIcon[0][0][side];
    }
    
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    	
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;
    	
    	boolean allReplaceable = true;
    	
    	for (int dx = 0; dx < 4; dx++) {
    		for (int dy = 0; dy < 4; dy++) {
    			for (int dz = 0; dz < 4; dz++) {
    				allReplaceable &= world.getBlock(bx + dx, by + dy, bz + dz).isReplaceable(world, bx + dx, by + dy, bz + dz);
    			}
    		}
    	}

        return super.canPlaceBlockAt(world, x, y, z) && allReplaceable;
    }
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;
    	
        return AxisAlignedBB.getBoundingBox((double)bx + this.minX, (double)by + this.minY, (double)bz + this.minZ, (double)bx + this.maxX * 4F, (double)by + this.maxY * 4F, (double)bz + this.maxZ * 4F);
    }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
    	if (!world.isRemote) {
    		int bx = (x >> 2) << 2;
    		int by = (y >> 2) << 2;
    		int bz = (z >> 2) << 2;

    		for (int dx = 0; dx < 4; dx++) {
    			for (int dy = 0; dy < 4; dy++) {
    				for (int dz = 0; dz < 4; dz++) {
    					world.setBlock(bx + dx, by + dy, bz + dz, this, 0, 2);
    				}
    			}
    		}
    	}
    }

    /**
     * Spawn particles for when the block is destroyed. Due to the nature
     * of how this is invoked, the x/y/z locations are not always guaranteed
     * to host your block. So be sure to do proper sanity checks before assuming
     * that the location is this block.
     *
     * @param world The current world
     * @param x X position to spawn the particle
     * @param y Y position to spawn the particle
     * @param z Z position to spawn the particle
     * @param meta The metadata for the block before it was destroyed.
     * @param effectRenderer A reference to the current effect renderer.
     * @return True to prevent vanilla break particles from spawning.
     */
	@Override
    @SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
		
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;
		
		Block blockThere = world.getBlock(x, y, z);
		int metaThere = world.getBlockMetadata(x, y, z);
		
        byte b0 = 16;

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < b0; ++k1)
                {
                    double d0 = (double)bx + ((double)i1 + 0.5D) / 4F;
                    double d1 = (double)by + ((double)j1 + 0.5D) / 4F;
                    double d2 = (double)bz + ((double)k1 + 0.5D) / 4F;
                    effectRenderer.addEffect((new EntityDiggingFX(world, d0, d1, d2, d0 - (double)x - 0.5D, d1 - (double)y - 0.5D, d2 - (double)z - 0.5D, blockThere, metaThere)).applyColourMultiplier(x, y, z));
                }
            }
        }


		return true;
	}


	/**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		this.setGiantBlockToAir(world, x, y, z);
    }
    
    /**
     * Called when the block is destroyed by an explosion.
     * Useful for allowing the block to take into account tile entities,
     * metadata, etc. when exploded, before it is removed.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param Explosion The explosion instance affecting the block
     */
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
        world.setBlockToAir(x, y, z);
		this.setGiantBlockToAir(world, x, y, z);
    }

    
    /**
     * Called on server worlds only when the block is about to be replaced by a different block or the same block with a
     * different metadata value. Args: world, x, y, z, old metadata
     */
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
    	if (!this.isSelfDestructing && !canBlockStay(world, x, y, z)) {
    		this.setGiantBlockToAir(world, x, y, z);
    	}
    }
    
    /**
     * Set the whole giant block area to air
     */
    private void setGiantBlockToAir(World world, int x, int y, int z) {
    	// this flag is maybe not totally perfect
    	this.isSelfDestructing = true;
    	
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;

    	for (int dx = 0; dx < 4; dx++) {
    		for (int dy = 0; dy < 4; dy++) {
    			for (int dz = 0; dz < 4; dz++) {
    				if (!(x == bx + dx && y == by + dy && z == bz + dz)) {
    					if (world.getBlock(bx + dx, by + dy, bz + dz) == this) {
    						world.setBlockToAir(bx + dx, by + dy, bz + dz);
    					}
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
        
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;

    	for (int dx = 0; dx < 4; dx++) {
    		for (int dy = 0; dy < 4; dy++) {
    			for (int dz = 0; dz < 4; dz++) {
    				allThisBlock &= world.getBlock(bx + dx, by + dy, bz + dz) == this;
    			}
    		}
    	}
    	
    	return allThisBlock;
    }
    
    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 2;
    }

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 20;
    }
}
