package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFTowerTermite;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



/**
 * 
 * Tower wood is a type of plank block that forms the walls of Dark Towers
 * 
 * @author Ben
 *
 */
public class BlockTFTowerWood extends Block {
	
	private static IIcon TEX_PLAIN;
	private static IIcon TEX_ENCASED;
	private static IIcon TEX_CRACKED;
	private static IIcon TEX_MOSSY;
	private static IIcon TEX_INFESTED;
	
	public static final int META_INFESTED = 4;

	
    public BlockTFTowerWood()
    {
        super(Material.wood);
        this.setHardness(40F);
        this.setResistance(10F);
        this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(TFItems.creativeTab);

    }
    
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
    	int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

    	if (meta == 0 || meta == 2 || meta == 3 || meta == META_INFESTED) 
    	{
       		// stripes!
        	int value = x * 31 + y * 15 + z * 33;
        	if ((value & 256) != 0)
        	{
        		value = 255 - (value & 255);
        	}
        	value &= 255;
        	value = value >> 1;
        	value |= 128;
        	
        	return value << 16 | value << 8 | value;
    	}
    	else
    	{ 
    		return 16777215;
    	}
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta)
		{
		case 0:
		default:
			return TEX_PLAIN;
		case 1:
			return TEX_ENCASED;
		case 2:
			return TEX_CRACKED;
		case 3:
			return TEX_MOSSY;
		case META_INFESTED:
			return TEX_INFESTED;
		}
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFTowerWood.TEX_PLAIN = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerwood_planks");
        BlockTFTowerWood.TEX_ENCASED = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerwood_encased");
        BlockTFTowerWood.TEX_CRACKED = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerwood_cracked");
        BlockTFTowerWood.TEX_MOSSY = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerwood_mossy");
        BlockTFTowerWood.TEX_INFESTED = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerwood_infested");
    }
	
 	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, META_INFESTED));
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return meta;
	}
    
    /**
     * Metadata and fortune sensitive version, this replaces the old (int meta, Random rand)
     * version in 1.1.
     *
     * @param meta Blocks Metadata
     * @param fortune Current item fortune level
     * @param random Random number generator
     * @return The number of items to drop
     */
    @Override
	public int quantityDropped(int meta, int fortune, Random random)
    {
    	if (meta == META_INFESTED)
    	{
    		return 0;
    	}
    	else
    	{
    		return super.quantityDropped(meta, fortune, random);
    	}
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
	public float getBlockHardness(World world, int x, int y, int z)
    {
    	// infested block is not very hard
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if (meta == META_INFESTED)
    	{
    		return 0.75F;
    	}
    	else
    	{
    		return super.getBlockHardness(world, x, y, z);
    	}
    }
    
    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    @Override
	public void dropBlockAsItemWithChance(World par1World, int x, int y, int z, int meta, float chance, int something)
    {
        if (!par1World.isRemote && meta == META_INFESTED)
        {
            EntityTFTowerTermite termite = new EntityTFTowerTermite(par1World);
            termite.setLocationAndAngles(x + 0.5D, y, z + 0.5D, 0.0F, 0.0F);
            par1World.spawnEntityInWorld(termite);
            termite.spawnExplosionParticle();
        }

        super.dropBlockAsItemWithChance(par1World, x, y, z, meta, chance, something);
    }

    /**
     * Chance that fire will spread and consume this block.
     * 300 being a 100% chance, 0, being a 0% chance.
     * 
     * @param world The current world
     * @param x The blocks X position
     * @param y The blocks Y position
     * @param z The blocks Z position
     * @param metadata The blocks current metadata
     * @param face The face that the fire is coming from
     * @return A number ranging from 0 to 300 relating used to determine if the block will be consumed by fire
     */
    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    	return 1;
    }

    /**
     * Called when fire is updating on a neighbor block.
     * The higher the number returned, the faster fire will spread around this block.
     * 
     * @param world The current world
     * @param x The blocks X position
     * @param y The blocks Y position
     * @param z The blocks Z position
     * @param metadata The blocks current metadata
     * @param face The face that the fire is coming from
     * @return A number that is used to determine the speed of fire growth around the block
     */
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 0;
	}

}
