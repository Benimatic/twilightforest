package twilightforest.block;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;

import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTFForceField extends BlockPane {
	
	public static String[] names = new String[] {"purple", "pink", "orange", "green", "blue"};
	
	public static IIcon[] sides;
	public static IIcon top;

    protected BlockTFForceField() {
        super("glass", "glass_pane_top", Material.grass, false);
		this.setLightLevel(2F / 15F);
		this.setCreativeTab(TFItems.creativeTab);
	}

	/**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return this.sides[meta % names.length];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon func_150097_e()
    {
        return this.top;
    }

	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);
        
        this.sides = new IIcon[names.length];
        
        for (int i = 0; i < names.length; i++) {
            this.sides[i] = register.registerIcon(TwilightForestMod.ID + ":forcefield_" + names[i]);
        }
        
        this.top = register.registerIcon(TwilightForestMod.ID + ":forcefield_top");

    }
    
    /**
     * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public int getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z)
    {
    	return 15 << 20 | 15 << 4;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta % names.length;
    }
    
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < names.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
    
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
    	super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    	
    	// just fill in the whole bounding box when we connect on all sides
        boolean north = this.canPaneConnectTo(world, x, y, z - 1, NORTH);
        boolean south = this.canPaneConnectTo(world, x, y, z + 1, SOUTH);
        boolean east = this.canPaneConnectTo(world, x - 1, y, z, WEST );
        boolean west = this.canPaneConnectTo(world, x + 1, y, z, EAST );
        
        if (north && south && east && west) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        
        // manually add to the list, since super.method is overwritten
        AxisAlignedBB myAABB = this.getCollisionBoundingBoxFromPool(world, x, y, z);

        if (myAABB != null && aabb.intersectsWith(myAABB))
        {
        	list.add(myAABB);
        }

    }

}
