package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFCReactorActive;
import twilightforest.tileentity.TileEntityTFGhastTrapActive;
import twilightforest.tileentity.TileEntityTFGhastTrapInactive;
import twilightforest.tileentity.TileEntityTFReverter;
import twilightforest.tileentity.TileEntityTFTowerBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



/**
 * 
 * Tower wood is a type of plank block that forms the walls of Dark Towers
 * 
 * @author Ben
 *
 */
public class BlockTFTowerDevice extends Block {
	
	private static IIcon TEX_REAPPEARING_INACTIVE;
	private static IIcon TEX_REAPPEARING_ACTIVE;
	private static IIcon TEX_VANISH_INACTIVE;
	private static IIcon TEX_VANISH_ACTIVE;
	private static IIcon TEX_VANISH_LOCKED;
	private static IIcon TEX_VANISH_UNLOCKED;
	private static IIcon TEX_BUILDER_INACTIVE;
	private static IIcon TEX_BUILDER_ACTIVE;
	private static IIcon TEX_ANTIBUILDER;
	private static IIcon TEX_BUILDER_TIMEOUT;
	private static IIcon TEX_GHASTTRAP_INACTIVE;
	private static IIcon TEX_GHASTTRAP_ACTIVE;
	private static IIcon TEX_REACTOR_INACTIVE;
	private static IIcon TEX_REACTOR_ACTIVE;
	private static IIcon TEX_GHASTTRAP_LID_INACTIVE;
	private static IIcon TEX_GHASTTRAP_LID_ACTIVE;
	private static IIcon TEX_SMOKER_ACTIVE;
	private static IIcon TEX_SMOKER_INACTIVE;
	private static IIcon TEX_FIREJET_ACTIVE;
	private static IIcon TEX_FIREJET_INACTIVE;
	
	public static final int META_REAPPEARING_INACTIVE = 0;
	public static final int META_REAPPEARING_ACTIVE = 1;
	public static final int META_VANISH_INACTIVE = 2;
	public static final int META_VANISH_ACTIVE = 3;
	public static final int META_VANISH_LOCKED = 4;
	public static final int META_VANISH_UNLOCKED = 5;
	public static final int META_BUILDER_INACTIVE = 6;
	public static final int META_BUILDER_ACTIVE = 7;
	public static final int META_BUILDER_TIMEOUT = 8;
	public static final int META_ANTIBUILDER = 9;
	public static final int META_GHASTTRAP_INACTIVE = 10;
	public static final int META_GHASTTRAP_ACTIVE = 11;
	public static final int META_REACTOR_INACTIVE = 12;
	public static final int META_REACTOR_ACTIVE = 13;

    public BlockTFTowerDevice()
    {
        super(Material.WOOD);
        this.setHardness(10F);
        this.setResistance(35F);
        this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(TFItems.creativeTab);
    }
    
    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 15;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta)
		{
		case META_REAPPEARING_INACTIVE:
		default:
			return TEX_REAPPEARING_INACTIVE;
		case META_REAPPEARING_ACTIVE:
			return TEX_REAPPEARING_ACTIVE;
		case META_VANISH_INACTIVE:
			return TEX_VANISH_INACTIVE;
		case META_VANISH_ACTIVE:
			return TEX_VANISH_ACTIVE;
		case META_VANISH_LOCKED:
			return TEX_VANISH_LOCKED;
		case META_VANISH_UNLOCKED:
			return TEX_VANISH_UNLOCKED;
		case META_BUILDER_INACTIVE:
			return TEX_BUILDER_INACTIVE;
		case META_BUILDER_TIMEOUT:
			return TEX_BUILDER_TIMEOUT;
		case META_BUILDER_ACTIVE:
			return TEX_BUILDER_ACTIVE;
		case META_ANTIBUILDER:
			return TEX_ANTIBUILDER;
		case META_GHASTTRAP_INACTIVE:
			if (side >= 2)
			{	
				return TEX_GHASTTRAP_INACTIVE;
			}
			else if (side == 1)
			{
				return TEX_GHASTTRAP_LID_INACTIVE;
			}
			else
			{
				return TFBlocks.towerWood.getIcon(side, 1);
			}
		case META_GHASTTRAP_ACTIVE:
			if (side >= 2)
			{	
				return TEX_GHASTTRAP_ACTIVE;
			}
			else if (side == 1)
			{
				return TEX_GHASTTRAP_LID_ACTIVE;
			}
			else
			{
				return TFBlocks.towerWood.getIcon(side, 1);
			}	
		case META_REACTOR_INACTIVE:
			return TEX_REACTOR_INACTIVE;
		case META_REACTOR_ACTIVE:
			return TEX_REACTOR_ACTIVE;
		}
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFTowerDevice.TEX_REAPPEARING_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_reappearing_off");
        BlockTFTowerDevice.TEX_REAPPEARING_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_reappearing_on");
        BlockTFTowerDevice.TEX_VANISH_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_vanish_off");
        BlockTFTowerDevice.TEX_VANISH_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_vanish_on");
        BlockTFTowerDevice.TEX_VANISH_LOCKED = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_lock_on");
        BlockTFTowerDevice.TEX_VANISH_UNLOCKED = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_lock_off");
        BlockTFTowerDevice.TEX_BUILDER_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_builder_off");
        BlockTFTowerDevice.TEX_BUILDER_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_builder_on");
        BlockTFTowerDevice.TEX_ANTIBUILDER = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_antibuilder");
        BlockTFTowerDevice.TEX_BUILDER_TIMEOUT = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_builder_timeout");
        BlockTFTowerDevice.TEX_GHASTTRAP_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_ghasttrap_off");
        BlockTFTowerDevice.TEX_GHASTTRAP_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_ghasttrap_on");
        BlockTFTowerDevice.TEX_REACTOR_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_reactor_off");
        BlockTFTowerDevice.TEX_REACTOR_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_reactor_on");
        BlockTFTowerDevice.TEX_GHASTTRAP_LID_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_ghasttraplid_off");
        BlockTFTowerDevice.TEX_GHASTTRAP_LID_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_ghasttraplid_on");
        BlockTFTowerDevice.TEX_SMOKER_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_smoker_off");
        BlockTFTowerDevice.TEX_SMOKER_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_smoker_on");
        BlockTFTowerDevice.TEX_FIREJET_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_firejet_off");
        BlockTFTowerDevice.TEX_FIREJET_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_firejet_on");
    }

 	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, META_REAPPEARING_INACTIVE));
        par3List.add(new ItemStack(par1, 1, META_VANISH_INACTIVE));
        par3List.add(new ItemStack(par1, 1, META_VANISH_LOCKED));
        par3List.add(new ItemStack(par1, 1, META_VANISH_UNLOCKED));
        par3List.add(new ItemStack(par1, 1, META_BUILDER_INACTIVE));
        par3List.add(new ItemStack(par1, 1, META_ANTIBUILDER));
        par3List.add(new ItemStack(par1, 1, META_GHASTTRAP_INACTIVE));
        par3List.add(new ItemStack(par1, 1, META_REACTOR_INACTIVE));
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        int meta = par1World.getBlockMetadata(x, y, z);

        if (meta == META_VANISH_INACTIVE)
        {
        	if (areNearbyLockBlocks(par1World, x, y, z))
        	{
        		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 1.0F, 0.3F);
        	}
        	else
        	{
        		changeToActiveVanishBlock(par1World, x, y, z, META_VANISH_ACTIVE);
        	}
            return true;
        }
        if (meta == META_REAPPEARING_INACTIVE)
        {
        	if (areNearbyLockBlocks(par1World, x, y, z))
        	{
        		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 1.0F, 0.3F);
        	}
        	else
        	{
        		changeToActiveVanishBlock(par1World, x, y, z, META_REAPPEARING_ACTIVE);
        	}
            return true;
        }
        else 
        {
        	return false;
        }
    }
    
    /**
     * Location sensitive version of getExplosionRestance
     *
     * @param par1Entity The entity that caused the explosion
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param explosionX Explosion source X Position
     * @param explosionY Explosion source X Position
     * @param explosionZ Explosion source X Position
     * @return The amount of the explosion absorbed.
     */
    @Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == META_VANISH_INACTIVE)
        {
        	return 6000F;
        }
        else if (meta == META_VANISH_LOCKED)
        {
        	return 6000000.0F;
        }
        else
        {
        	return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
        }
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
	public float getBlockHardness(World world, int x, int y, int z)
    {
    	// most vanish blocks can't be broken
    	int meta = world.getBlockMetadata(x, y, z);

    	switch (meta)
    	{
    	case META_REAPPEARING_ACTIVE :
    	case META_VANISH_INACTIVE :
    	case META_VANISH_ACTIVE :
    	case META_VANISH_LOCKED :
    	case META_VANISH_UNLOCKED :
    		return -1;
    	default :
    		return super.getBlockHardness(world, x, y, z);
    	}
    }

    /**
     * Are any of the 26 adjacent blocks a locked vanishing block?
     */
    public static boolean areNearbyLockBlocks(World world, int x, int y, int z) 
    {
    	boolean locked = false;
    	
    	//TODO: this is hacky.  We really need to determine the exact blocks of the door and check those for locks.
		for (int dx = x - 2; dx <= x + 2; dx++)
		{
			for (int dy = y - 2; dy <= y + 2; dy++)
			{
				for (int dz = z - 2; dz <= z + 2; dz++)
				{
					if (world.getBlock(dx, dy, dz) == TFBlocks.towerDevice && world.getBlockMetadata(dx, dy, dz) == META_VANISH_LOCKED)
					{
						locked = true;
					}
				}
			}
		}
		
		return locked;
	}

	/**
     * Change this block into an different device block
     */
	public static void unlockBlock(World par1World, int x, int y, int z) 
	{
		Block thereBlockID = par1World.getBlock(x, y, z);
		int thereBlockMeta = par1World.getBlockMetadata(x, y, z);

		if (thereBlockID == TFBlocks.towerDevice || thereBlockMeta == META_VANISH_LOCKED)
		{
			changeToBlockMeta(par1World, x, y, z, META_VANISH_UNLOCKED);
			par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
		}
	}
    

	/**
     * Change this block into an different device block
     */
	private static void changeToBlockMeta(World par1World, int x, int y, int z, int meta) 
	{
		Block thereBlockID = par1World.getBlock(x, y, z);
		
		if (thereBlockID == TFBlocks.towerDevice || thereBlockID == TFBlocks.towerTranslucent)
		{
			par1World.setBlock(x, y, z, thereBlockID, meta, 3);
			par1World.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
			par1World.notifyBlocksOfNeighborChange(x, y, z, thereBlockID);
		}
	}
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
	public void onBlockAdded(World par1World, int x, int y, int z)
    {
        int meta = par1World.getBlockMetadata(x, y, z);

        if (!par1World.isRemote)
        {
        	if (meta == META_BUILDER_INACTIVE && par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
        		changeToBlockMeta(par1World, x, y, z, META_BUILDER_ACTIVE);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
        	}

        }

    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
	public void onNeighborBlockChange(World par1World, int x, int y, int z, Block myBlockID)
    {
        int meta = par1World.getBlockMetadata(x, y, z);

        if (!par1World.isRemote)
        {
        	if (meta == META_VANISH_INACTIVE && par1World.isBlockIndirectlyGettingPowered(x, y, z) && !areNearbyLockBlocks(par1World, x, y, z))
        	{
        		changeToActiveVanishBlock(par1World, x, y, z, META_VANISH_ACTIVE);
        	}
        	
        	if (meta == META_REAPPEARING_INACTIVE && par1World.isBlockIndirectlyGettingPowered(x, y, z) && !areNearbyLockBlocks(par1World, x, y, z))
        	{
        		changeToActiveVanishBlock(par1World, x, y, z, META_REAPPEARING_ACTIVE);
        	}
        	
        	if (meta == META_BUILDER_INACTIVE && par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
        		changeToBlockMeta(par1World, x, y, z, META_BUILDER_ACTIVE);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
                
        		par1World.scheduleBlockUpdate(x, y, z, this, 4);
        	}
        	
        	if (meta == META_BUILDER_ACTIVE && !par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
        		changeToBlockMeta(par1World, x, y, z, META_BUILDER_INACTIVE);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
                
        		par1World.scheduleBlockUpdate(x, y, z, this, 4);
        	}
        	
        	if (meta == META_BUILDER_TIMEOUT && !par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
        		changeToBlockMeta(par1World, x, y, z, META_BUILDER_INACTIVE);
        	}
        	
        	if (meta == META_GHASTTRAP_INACTIVE && isInactiveTrapCharged(par1World, x, y, z) && par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
        		changeToBlockMeta(par1World, x, y, z, META_GHASTTRAP_ACTIVE);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
                
        		par1World.scheduleBlockUpdate(x, y, z, this, 4);
        	}

        	if (meta == META_REACTOR_INACTIVE && isReactorReady(par1World, x, y, z))
        	{
        		// check if we should fire up the reactor
        		changeToBlockMeta(par1World, x, y, z, META_REACTOR_ACTIVE);
        	}
        }
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            int meta = par1World.getBlockMetadata(x, y, z);

            if (meta == META_VANISH_ACTIVE || meta == META_REAPPEARING_ACTIVE)
            {
            	if (meta == META_VANISH_ACTIVE)
            	{
                    par1World.setBlock(x, y, z, Blocks.AIR, 0, 3);
            	}
            	else
            	{
                    par1World.setBlock(x, y, z, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_REAPPEARING_INACTIVE, 3);
            		par1World.scheduleBlockUpdate(x, y, z, TFBlocks.towerTranslucent, 80);
            	}
                par1World.notifyBlocksOfNeighborChange(x, y, z, this);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.3F, 0.5F);
                //par1World.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                
                // activate all adjacent inactive vanish blocks
                checkAndActivateVanishBlock(par1World, x - 1, y, z);
                checkAndActivateVanishBlock(par1World, x + 1, y, z);
                checkAndActivateVanishBlock(par1World, x, y + 1, z);
                checkAndActivateVanishBlock(par1World, x, y - 1, z);
                checkAndActivateVanishBlock(par1World, x, y, z + 1);
                checkAndActivateVanishBlock(par1World, x, y, z - 1);

            }
            
            if (meta == META_BUILDER_ACTIVE && par1World.isBlockIndirectlyGettingPowered(x, y, z))
    		{
    			this.letsBuild(par1World, x, y, z);
    		}
            
            if (meta == META_BUILDER_INACTIVE || meta == META_BUILDER_TIMEOUT)
    		{
                // activate all adjacent inactive vanish blocks
                checkAndActivateVanishBlock(par1World, x - 1, y, z);
                checkAndActivateVanishBlock(par1World, x + 1, y, z);
                checkAndActivateVanishBlock(par1World, x, y + 1, z);
                checkAndActivateVanishBlock(par1World, x, y - 1, z);
                checkAndActivateVanishBlock(par1World, x, y, z + 1);
                checkAndActivateVanishBlock(par1World, x, y, z - 1);
    		}
        }
    }

	/**
     * Start the builder block tileentity building!
     */
	private void letsBuild(World par1World, int x, int y, int z) {
    	BlockSourceImpl blockSource = new BlockSourceImpl(par1World, x, y, z);
    	TileEntityTFTowerBuilder tileEntity = (TileEntityTFTowerBuilder)blockSource.getBlockTileEntity();

    	if (tileEntity != null && !tileEntity.makingBlocks)
    	{
    		tileEntity.startBuilding();
    	}

    }
    
    /**
	 * Check if the inactive trap block is fully charged
	 */
	private boolean isInactiveTrapCharged(World par1World, int x, int y, int z) 
	{
    	BlockSourceImpl blockSource = new BlockSourceImpl(par1World, x, y, z);
    	TileEntityTFGhastTrapInactive tileEntity = (TileEntityTFGhastTrapInactive)blockSource.getBlockTileEntity();

    	if (tileEntity != null && tileEntity.isCharged())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }


	/**
	 * Check if the reactor has all the specified things around it
	 */
	private boolean isReactorReady(World world, int x, int y, int z) 
	{
		if (world.getBlock(x, y + 1, z) != Blocks.REDSTONE_BLOCK
				|| world.getBlock(x, y - 1, z) != Blocks.REDSTONE_BLOCK
				|| world.getBlock(x + 1, y, z) != Blocks.REDSTONE_BLOCK
				|| world.getBlock(x - 1, y, z) != Blocks.REDSTONE_BLOCK
				|| world.getBlock(x, y, z + 1) != Blocks.REDSTONE_BLOCK
				|| world.getBlock(x, y, z - 1) != Blocks.REDSTONE_BLOCK)
		{
			return false;
		}
		
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
    	int meta = par1World.getBlockMetadata(x, y, z);

    	if (meta == META_VANISH_ACTIVE || meta == META_REAPPEARING_ACTIVE || meta == BlockTFTowerDevice.META_BUILDER_ACTIVE)
    	{
    		for (int i = 0; i < 1; ++i) {
    			this.sparkle(par1World, x, y, z, par5Random);
    		}
    	}
    }

    /**
     * Shine bright like a DIAMOND! (or actually, sparkle like redstone ore)
     */
    public void sparkle(World world, int x, int y, int z, Random rand)
    {
        double offset = 0.0625D;

        for (int side = 0; side < 6; ++side)
        {
            double rx = x + rand.nextFloat();
            double ry = y + rand.nextFloat();
            double rz = z + rand.nextFloat();

            if (side == 0 && !world.getBlock(x, y + 1, z).isOpaqueCube())
            {
                ry = y + 1 + offset;
            }

            if (side == 1 && !world.getBlock(x, y - 1, z).isOpaqueCube())
            {
                ry = y + 0 - offset;
            }

            if (side == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube())
            {
                rz = z + 1 + offset;
            }

            if (side == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube())
            {
                rz = z + 0 - offset;
            }

            if (side == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube())
            {
                rx = x + 1 + offset;
            }

            if (side == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube())
            {
                rx = x + 0 - offset;
            }

            if (rx < x || rx > x + 1 || ry < 0.0D || ry > y + 1 || rz < z || rz > z + 1)
            {
                world.spawnParticle("reddust", rx, ry, rz, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    /**
     * If the targeted block is a vanishing block, activate it
     */
    public static void checkAndActivateVanishBlock(World world, int x, int y, int z) {
    	Block thereID = world.getBlock(x, y, z);
    	int thereMeta = world.getBlockMetadata(x, y, z);
    	
    	if (thereID == TFBlocks.towerDevice && (thereMeta == META_VANISH_INACTIVE || thereMeta == META_VANISH_UNLOCKED) && !areNearbyLockBlocks(world, x, y, z))
    	{
    		changeToActiveVanishBlock(world, x, y, z, META_VANISH_ACTIVE);
    	}
    	else if (thereID == TFBlocks.towerDevice && thereMeta == META_REAPPEARING_INACTIVE && !areNearbyLockBlocks(world, x, y, z))
    	{
    		changeToActiveVanishBlock(world, x, y, z, META_REAPPEARING_ACTIVE);
    	}
    	else if (thereID == TFBlocks.towerTranslucent && thereMeta == BlockTFTowerTranslucent.META_BUILT_INACTIVE)
    	{
    		changeToActiveVanishBlock(world, x, y, z, BlockTFTowerTranslucent.META_BUILT_ACTIVE);
    	}
	}

	/**
	 * Change this block into an active vanishing block
	 */
    public static void changeToActiveVanishBlock(World par1World, int x, int y, int z, int meta) 
	{
		changeToBlockMeta(par1World, x, y, z, meta);
		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.3F, 0.6F);
		
		Block thereBlockID = par1World.getBlock(x, y, z);
		par1World.scheduleBlockUpdate(x, y, z, thereBlockID, getTickRateFor(thereBlockID, meta, par1World.rand));
	}

    /**
     * We need variable, metadata-based tick rates
     */
	private static int getTickRateFor(Block thereBlockID, int meta, Random rand) 
	{
		if (thereBlockID == TFBlocks.towerDevice && (meta == META_VANISH_ACTIVE || meta == META_REAPPEARING_ACTIVE))
		{
			return 2 + rand.nextInt(5);
		}
		else if (thereBlockID == TFBlocks.towerTranslucent && meta == BlockTFTowerTranslucent.META_BUILT_ACTIVE)
		{
			return 10;
		}
		
		return 15;
	}

	/**
     * Get a light value for this block, normal ranges are between 0 and 15
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return The light value
     */
    @Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
        Block blockID = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        
        if (blockID != this)
        {
        	// why are you asking me?
        	return 0;
        }
        
        switch (meta)
        {
        case META_BUILDER_ACTIVE :
        case META_VANISH_ACTIVE :
        case META_REAPPEARING_ACTIVE :
        	return 4;
        case META_ANTIBUILDER :
        	return 10;
        case META_GHASTTRAP_ACTIVE :
        case META_REACTOR_ACTIVE :
        	return 15;
    	default :
    		return 0;
        }
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
		return metadata == META_BUILDER_ACTIVE || metadata == META_ANTIBUILDER || metadata == META_REACTOR_ACTIVE
				|| metadata == META_GHASTTRAP_INACTIVE || metadata == META_GHASTTRAP_ACTIVE;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) 
	{
		if (metadata == META_BUILDER_ACTIVE)
		{
	        return new TileEntityTFTowerBuilder();
		}
		else if (metadata == META_ANTIBUILDER)
		{
	        return new TileEntityTFReverter();
		}
		else if (metadata == META_GHASTTRAP_INACTIVE)
		{
	        return new TileEntityTFGhastTrapInactive();
		}
		else if (metadata == META_GHASTTRAP_ACTIVE)
		{
	        return new TileEntityTFGhastTrapActive();
		}
		else if (metadata == META_REACTOR_ACTIVE)
		{
	        return new TileEntityTFCReactorActive();
		}
		else
		{
			return null;
		}
	}
	

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int meta, Random par2Random, int par3)
    {
        switch (meta)
        {
        case META_ANTIBUILDER :
        	return null;
    	default :
        	return Item.getItemFromBlock(this);
        }
    }

	/**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) 
    {
        switch (meta)
        {
        case META_REAPPEARING_ACTIVE :
        	return META_REAPPEARING_INACTIVE;
        case META_BUILDER_ACTIVE :
        case META_BUILDER_TIMEOUT :
        	return META_BUILDER_INACTIVE;
        case META_VANISH_ACTIVE :
        	return META_VANISH_INACTIVE;
        case META_GHASTTRAP_ACTIVE :
        	return META_GHASTTRAP_INACTIVE;
        case META_REACTOR_ACTIVE :
        	return META_REACTOR_INACTIVE;
    	default :
        	return meta;
        }
	}

//	@Override
//	public void breakBlock(World par1World, int x, int y, int z, int par5, int meta) {
//		super.breakBlock(par1World, x, y, z, par5, meta);
//		
//		//System.out.println("broke block, meta = " + meta);
//		
//        if (meta == META_BUILDER_ACTIVE)
//		{
//            // activate all adjacent inactive vanish blocks
//            checkAndActivateVanishBlock(par1World, x - 1, y, z);
//            checkAndActivateVanishBlock(par1World, x + 1, y, z);
//            checkAndActivateVanishBlock(par1World, x, y + 1, z);
//            checkAndActivateVanishBlock(par1World, x, y - 1, z);
//            checkAndActivateVanishBlock(par1World, x, y, z + 1);
//            checkAndActivateVanishBlock(par1World, x, y, z - 1);
//		}
//	}
    
    

}
