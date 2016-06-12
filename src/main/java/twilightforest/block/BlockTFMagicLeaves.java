package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;




public class BlockTFMagicLeaves extends BlockLeaves {
	
	int oakColor = 0x48B518;
	int canopyColor = 0x609860;
	int mangroveColor = 0x80A755;
	
	public static final int META_TIME = 0;
	public static final int META_TRANS = 1;
	public static final int META_MINE = 2;
	public static final int META_SORT = 3;
	
	public static IIcon SPR_TIMELEAVES;
	public static IIcon SPR_TIMEFX;
	public static IIcon SPR_TRANSLEAVES;
	public static IIcon SPR_TRANSFX;
	public static IIcon SPR_SORTLEAVES;
	public static IIcon SPR_SORTFX;


	protected BlockTFMagicLeaves() {
		super();
		this.setHardness(0.2F);
		this.setLightOpacity(2);
		this.setStepSound(Block.soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
	}
	

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
	public int getRenderColor(int par1)
    {
    	switch (par1 & 3)
    	{
    	case META_TIME :
        	return 106 << 16 | 156 << 8 | 23;
    	case META_TRANS :
        	return 108 << 16 | 204 << 8 | 234;
    	case META_MINE :
        	return 252 << 16 | 241 << 8 | 68;
    	case META_SORT :
        	return 54 << 16 | 76 << 8 | 3;
    	}
    	
    	return 16777215;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
    	int leafType = world.getBlockMetadata(x, y, z) & 0x03;

    	int red = 0;
    	int green = 0;
    	int blue = 0;

    	if (leafType == META_TIME)
    	{
        	int fade = x * 16 + y * 16 + z * 16;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 106 + fall * 251);
	    	green = (int) (spring * 156 + fall * 108);
	    	blue = (int) (spring * 23 + fall * 27);
    	}
    	else if (leafType == META_MINE)
    	{
        	int fade = x * 31 + y * 33 + z * 32;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 252 + fall * 237);
	    	green = (int) (spring * 241 + fall * 172);
	    	blue = (int) (spring * 68 + fall * 9);
    	}
    	else if (leafType == META_TRANS)
    	{
        	int fade = x * 27 + y * 63 + z * 39;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 108 + fall * 96);
	    	green = (int) (spring * 204 + fall * 107);
	    	blue = (int) (spring * 234 + fall * 121);
    	}
    	else if (leafType == META_SORT)
    	{
        	int fade = x * 63 + y * 63 + z * 63;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 54 + fall * 168);
	    	green = (int) (spring * 76 + fall * 199);
	    	blue = (int) (spring * 3 + fall * 43);
    	}
    	
    	return red << 16 | green << 8 | blue;

    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
	public int getRenderType() {
    	return TwilightForestMod.proxy.getMagicLeavesBlockRenderID();
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
	public int getRenderBlockPass()
    {
        return 0;
    }

    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	switch (meta & 0x03)
    	{
    	default:
            return SPR_TIMELEAVES;
    	case 1:
            return SPR_TRANSLEAVES;
    	case 3:
            return SPR_SORTLEAVES;
    		
    	}
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFMagicLeaves.SPR_TIMELEAVES = par1IconRegister.registerIcon(TwilightForestMod.ID + ":time_leaves");
        BlockTFMagicLeaves.SPR_TRANSLEAVES = par1IconRegister.registerIcon(TwilightForestMod.ID + ":trans_leaves");
        BlockTFMagicLeaves.SPR_SORTLEAVES = par1IconRegister.registerIcon(TwilightForestMod.ID + ":sort_leaves");
    }
    

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int side)
    {
    	return Blocks.leaves.shouldSideBeRendered(iblockaccess, i, j, k, side);
    }
    
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	par3List.add(new ItemStack(par1, 1, 0));
    	par3List.add(new ItemStack(par1, 1, 1));
    	par3List.add(new ItemStack(par1, 1, 2));
    	par3List.add(new ItemStack(par1, 1, 3));

    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
	public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
    	int meta = par1World.getBlockMetadata(x, y, z);

    	if ((meta & 3) == META_TRANS)
    	{
    		for (int i = 0; i < 1; ++i) {
    			this.sparkleRunes(par1World, x, y, z, par5Random);
    		}
    	}
    }

    /**
     * The leaf sparkles.
     */
    private void sparkleRunes(World world, int x, int y, int z, Random rand)
    {
    	double offset = 0.0625D;

    	int side = rand.nextInt(5) + 1;
    	double rx = x + rand.nextFloat();
    	double ry = y + rand.nextFloat();
    	double rz = z + rand.nextFloat();

    	if (side == 0 && world.isAirBlock(x, y + 1, z))
    	{
    		ry = y + 1 + offset;
    	}

    	if (side == 1 && world.isAirBlock(x, y - 1, z))
    	{
    		ry = y + 0 - offset;
    	}

    	if (side == 2 && world.isAirBlock(x, y, z + 1))
    	{
    		rz = z + 1 + offset;
    	}

    	if (side == 3 && world.isAirBlock(x, y, z - 1))
    	{
    		rz = z + 0 - offset;
    	}

    	if (side == 4 && world.isAirBlock(x + 1, y, z))
    	{
    		rx = x + 1 + offset;
    	}

    	if (side == 5 && world.isAirBlock(x - 1, y, z))
    	{
    		rx = x + 0 - offset;
    	}

    	if (rx < x || rx > x + 1 || ry < y|| ry > y + 1 || rz < z || rz > z + 1)
    	{
    		TwilightForestMod.proxy.spawnParticle(world, "leafrune", rx, ry, rz, 0.0D, 0.0D, 0.0D);
    	}
    }


	@Override
	public String[] func_150125_e() {
		// TODO Auto-generated method stub
		return null;
	}

}
