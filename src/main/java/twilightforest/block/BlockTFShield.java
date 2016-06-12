package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFShield extends Block 
{

	public static IIcon sprSide;
	private IIcon sprInside;
	private IIcon sprOutside;
	
    public BlockTFShield()
    {
        super(Material.ROCK);
        this.setBlockUnbreakable();
        //this.setResistance(2000.0F);
        this.setResistance(6000000.0F);
        this.setStepSound(Block.soundTypeMetal);
		this.setCreativeTab(TFItems.creativeTab);
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	if (side == meta)
    	{
    		return sprInside;
    	}
    	else
    	{
    		return sprOutside;
    	}
    }
    
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	this.sprInside = par1IconRegister.registerIcon(TwilightForestMod.ID + ":shield_inside");
    	this.sprOutside = par1IconRegister.registerIcon(TwilightForestMod.ID + ":shield_outside");
    }
    
 	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
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
        return true;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return true;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return 0;
	}
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    /**
     * Called when the block is placed in the world.
     */
    @Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        int l = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, par5EntityLiving);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
    }
    
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    @Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
    {
        // why can't we just pass the side to this method?  This is annoying and failure-prone
        RayTraceResult mop = getPlayerPointVec(world, player, 6.0);
    	
        int facing = mop != null ? mop.sideHit : -1;
        int meta = world.getBlockMetadata(x, y, z);
        
        //System.out.printf("Determining relative hardness; facing = %d, meta = %d\n", facing, meta);
        
        if (facing == meta)
        {
        	return player.getBreakSpeed(Blocks.STONE, false, 0, x, y, z) / 1.5F / 100F;
        }
        else
        {
        	return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
        }
    }
    
	/**
	 * What block is the player pointing at?
	 * 
	 * This very similar to player.rayTrace, but that method is not available on the server.
	 * 
	 * @return
	 */
	private RayTraceResult getPlayerPointVec(World worldObj, EntityPlayer player, double range) {
        Vec3d position = Vec3d.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d look = player.getLook(1.0F);
        Vec3d dest = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        return worldObj.rayTraceBlocks(position, dest);
	}
}
