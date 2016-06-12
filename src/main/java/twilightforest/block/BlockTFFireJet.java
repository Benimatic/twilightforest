package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFFlameJet;
import twilightforest.tileentity.TileEntityTFPoppingJet;
import twilightforest.tileentity.TileEntityTFSmoker;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFFireJet extends Block {

	public static final int META_SMOKER = 0;
	public static final int META_ENCASED_SMOKER_OFF = 1;
	public static final int META_ENCASED_SMOKER_ON = 2;
	public static final int META_JET_IDLE = 8;
	public static final int META_JET_POPPING = 9;
	public static final int META_JET_FLAME = 10;
	public static final int META_ENCASED_JET_IDLE = 11;
	public static final int META_ENCASED_JET_POPPING = 12;
	public static final int META_ENCASED_JET_FLAME = 13;
	
	private IIcon iconJet;
	private IIcon iconSide;
	private IIcon iconSmokerInactive;
	private IIcon iconSmokerActive;
	private IIcon iconJetInactive;
	private IIcon iconJetActive;
	
	protected BlockTFFireJet() {
		super(Material.rock);
		this.setHardness(1.5F);
		//this.setResistance(10.0F);
		this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(TFItems.creativeTab);
		this.setTickRandomly(true);
	}



    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	switch (meta)
    	{
    	default :
    		return meta;
    	case META_ENCASED_SMOKER_ON :
    		return META_ENCASED_SMOKER_OFF;
    	case META_ENCASED_JET_POPPING :
    	case META_ENCASED_JET_FLAME :
    		return META_ENCASED_JET_IDLE;
    	case META_JET_POPPING :
    	case META_JET_FLAME :
    		return META_JET_IDLE;
    	}
		
	}


    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	if (meta == META_ENCASED_SMOKER_OFF)
    	{
			if (side >= 2)
			{	
				return iconSmokerInactive;
			}
			else if (side == 1)
			{
				return TFBlocks.towerDevice.getIcon(side, BlockTFTowerDevice.META_GHASTTRAP_INACTIVE);
			}
			else
			{
				return TFBlocks.towerWood.getIcon(side, 1);
			}    	
		}
    	else if (meta == META_ENCASED_SMOKER_ON)
    	{
			if (side >= 2)
			{	
				return iconSmokerActive;
			}
			else if (side == 1)
			{
				return TFBlocks.towerDevice.getIcon(side, BlockTFTowerDevice.META_GHASTTRAP_ACTIVE);
			}
			else
			{
				return TFBlocks.towerWood.getIcon(side, 1);
			}    	
		}
    	if (meta == META_ENCASED_JET_IDLE)
    	{
			if (side >= 2)
			{	
				return iconJetInactive;
			}
			else if (side == 1)
			{
				return TFBlocks.towerDevice.getIcon(side, BlockTFTowerDevice.META_GHASTTRAP_INACTIVE);
			}
			else
			{
				return TFBlocks.towerWood.getIcon(side, 1);
			}    	
		}
    	else if (meta == META_ENCASED_JET_POPPING || meta == META_ENCASED_JET_FLAME)
    	{
			if (side >= 2)
			{	
				return iconJetActive;
			}
			else if (side == 1)
			{
				return TFBlocks.towerDevice.getIcon(side, BlockTFTowerDevice.META_GHASTTRAP_ACTIVE);
			}
			else
			{
				return TFBlocks.towerWood.getIcon(side, 1);
			}    	
		}
    	else
    	{
    		// normal fire jet
        	if (side == 1)
        	{
        		return iconJet;
        	}
        	else
        	{
        		return iconSide;
        	}
    	}
    }
    
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.iconSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":firejet_side");
        this.iconJet = par1IconRegister.registerIcon(TwilightForestMod.ID + ":firejet_top");
        this.iconSmokerInactive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_smoker_off");
        this.iconSmokerActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_smoker_on");
        this.iconJetInactive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_firejet_off");
        this.iconJetActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_firejet_on");    }
    
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
    	int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
    	
    	if (meta == META_ENCASED_SMOKER_OFF || meta == META_ENCASED_SMOKER_ON 
    			|| meta == META_ENCASED_JET_IDLE || meta == META_ENCASED_JET_POPPING || meta == META_ENCASED_JET_FLAME)
    	{
    		return super.colorMultiplier(par1IBlockAccess, x, y, z);
    	}
    	else
    	{
    		int red = 0;
    		int grn = 0;
    		int blu = 0;

    		for (int var8 = -1; var8 <= 1; ++var8)
    		{
    			for (int var9 = -1; var9 <= 1; ++var9)
    			{
    				int biomeColor = par1IBlockAccess.getBiomeGenForCoords(x + var9, z + var8).getBiomeGrassColor(x + var9, y, z + var8);
    				red += (biomeColor & 16711680) >> 16;
    				grn += (biomeColor & 65280) >> 8;
    				blu += biomeColor & 255;
    			}
    		}

    		return (red / 9 & 255) << 16 | (grn / 9 & 255) << 8 | blu / 9 & 255;
    	}
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
    	if (world.getBlock(x, y, z) == this)
    	{
    		int meta = world.getBlockMetadata(x, y, z);

    		switch (meta) {
    		case META_SMOKER:
    		default :
    			return 0;
    		case META_JET_FLAME :
    		case META_ENCASED_JET_FLAME :
    			return 15;
    		}
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	// idle jets may turn active
    	if (!world.isRemote && world.getBlockMetadata(x, y, z) == META_JET_IDLE) {
    		ChunkCoordinates lavaPos = findLavaAround(world, x, y - 1, z);
    		
    		if (isLava(world, lavaPos.posX, lavaPos.posY, lavaPos.posZ))
    		{
    			// deplete lava reservoir
    			world.setBlock(lavaPos.posX, lavaPos.posY, lavaPos.posZ, Blocks.AIR, 0, 2);
    			// change jet state
    			world.setBlock(x, y, z, this, META_JET_POPPING, 0);
    		}
    		else
    		{
    			//System.out.println("Can't fire jet because reservoir is empty.  Block  meta is " + world.getBlockMetadata(lavaPos.posX, lavaPos.posY, lavaPos.posZ));
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
        	if (meta == META_ENCASED_SMOKER_OFF && par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
    			par1World.setBlock(x, y, z, this, META_ENCASED_SMOKER_ON, 3);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
        	}
        	
        	if (meta == META_ENCASED_SMOKER_ON && !par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
    			par1World.setBlock(x, y, z, this, META_ENCASED_SMOKER_OFF, 3);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
        	}
        	
        	if (meta == META_ENCASED_JET_IDLE && par1World.isBlockIndirectlyGettingPowered(x, y, z))
        	{
    			par1World.setBlock(x, y, z, this, META_ENCASED_JET_POPPING, 3);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
        	}

        }
    }

    /**
     * Find a full block of lava near the designated block.  This is intentionally not really that reliable.
     * 
     * 
     */
    private ChunkCoordinates findLavaAround(World world, int x, int y, int z) {
		if (isLava(world, x, y, z)) {
			return new ChunkCoordinates(x, y, z);
		}
		// try three random spots nearby
		int rx = x + world.rand.nextInt(3) - 1;
		int rz = z + world.rand.nextInt(3) - 1;
		if (isLava(world, rx, y, rz)) {
			return new ChunkCoordinates(rx, y, rz);
		}
		
		rx = x + world.rand.nextInt(3) - 1;
		rz = z + world.rand.nextInt(3) - 1;
		if (isLava(world, rx, y, rz)) {
			return new ChunkCoordinates(rx, y, rz);
		}
		
		rx = x + world.rand.nextInt(3) - 1;
		rz = z + world.rand.nextInt(3) - 1;
		if (isLava(world, rx, y, rz)) {
			return new ChunkCoordinates(rx, y, rz);
		}
		
		
		// finally, give up
		return new ChunkCoordinates(x, y, z);
	}
    
    private boolean isLava(World world, int x, int y, int z)
    {
    	return world.getBlock(x, y, z).getMaterial() == Material.lava && world.getBlockMetadata(x, y, z) == 0;
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
    	if (metadata == META_SMOKER || metadata == META_JET_POPPING || metadata == META_JET_FLAME
    			|| metadata == META_ENCASED_SMOKER_ON || metadata == META_ENCASED_JET_POPPING || metadata == META_ENCASED_JET_FLAME)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
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
    public TileEntity createTileEntity(World world, int metadata) {
    	if (metadata == META_SMOKER || metadata == META_ENCASED_SMOKER_ON)
    	{
    		return new TileEntityTFSmoker();
    	}
    	else if (metadata == META_JET_POPPING)
    	{
    		return new TileEntityTFPoppingJet(META_JET_FLAME);
    	}
    	else if (metadata == META_JET_FLAME)
    	{
    		return new TileEntityTFFlameJet(META_JET_IDLE);
    	}
    	else if (metadata == META_ENCASED_JET_POPPING)
    	{
    		return new TileEntityTFPoppingJet(META_ENCASED_JET_FLAME);
    	}
    	else if (metadata == META_ENCASED_JET_FLAME)
    	{
    		return new TileEntityTFFlameJet(META_ENCASED_JET_IDLE);
    	}
    	else
    	{
    		return null;
    	}
    }


    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, META_SMOKER));
        par3List.add(new ItemStack(par1, 1, META_JET_IDLE));
        par3List.add(new ItemStack(par1, 1, META_ENCASED_SMOKER_OFF));
        par3List.add(new ItemStack(par1, 1, META_ENCASED_JET_IDLE));
    }


}
