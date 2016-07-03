package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.BlockPos;
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
        super(Material.WOOD);
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
	
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, META_INFESTED));
    }
    
    @Override
	public int damageDropped(IBlockState state) {
    	return meta;
	}

    @Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
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
    
    @Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
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
    
    @Override
	public void dropBlockAsItemWithChance(World par1World, BlockPos pos, IBlockState state, float chance, int something)
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

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing side) {
    	return 1;
    }

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return 0;
	}

}
