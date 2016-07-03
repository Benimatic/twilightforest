package twilightforest.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFTrophy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Head trophy similar to (and based partially on) BlockSkull
 *
 */
public class BlockTFTrophy extends Block
{

	public BlockTFTrophy() 
	{
        super(Material.CIRCUITS);
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
	}

    @Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos)
    {
        int meta = par1IBlockAccess.getBlockMetadata(x, y, z) & 7;
    	TileEntityTFTrophy trophy = (TileEntityTFTrophy)par1IBlockAccess.getTileEntity(x, y, z);

        if (trophy != null && trophy.func_145904_a() == 0)
        {
        	// hydra bounds
        	switch (meta)
        	{
        	case 1:
        	default:
        		this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
        		break;
        	case 2:
        	case 3:
        		this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 1.0F);
        		break;
        	case 4:
        	case 5:
        		this.setBlockBounds(0.0F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
        		break;
        	}
        }
        else if (trophy != null && trophy.func_145904_a() == 3)
        {
        	// urghast bounds
        	this.setBlockBounds(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);
        }
        else
        {
        	// normal skull bounds
        	switch (meta)
        	{
        	case 1:
        	default:
        		this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
        		break;
        	case 2:
        		this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
        		break;
        	case 3:
        		this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
        		break;
        	case 4:
        		this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
        		break;
        	case 5:
        		this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        	}
        }
    }

    @Override
	public IBlockState onBlockPlaced(World par1World, BlockPos pos, EnumFacing sideHit, float hitX, float hitY, float hitZ, int meta, EntityLivingBase par5EntityLiving)
    {
        int rotation = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, rotation, 2);
    }

    @Override
	public TileEntity createTileEntity(World var1, IBlockState state) {
        return new TileEntityTFTrophy();
	}

	@SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public Item idPicked(World par1World, int par2, int par3, int par4)
    {
        return TFItems.trophy;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
	public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        TileEntity var5 = par1World.getTileEntity(par2, par3, par4);
        return var5 != null && var5 instanceof TileEntitySkull ? ((TileEntitySkull)var5).func_145904_a() : super.getDamageValue(par1World, par2, par3, par4);
    }

    @Override
	public int damageDropped(IBlockState state)
    {
        return par1;
    }

    /**
     * Called when the block is attempted to be harvested
     */
    @Override
	public void onBlockHarvested(World par1World, BlockPos pos, IBlockState state, EntityPlayer par6EntityPlayer)
    {
        if (par6EntityPlayer.capabilities.isCreativeMode)
        {
            par5 |= 8;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5, 2);
        }

        dropBlockAsItem(par1World, par2, par3, par4, par5, 0);

        super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
    }

//    /**
//     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
//     */
//    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
//    {
//        super.breakBlock(par1World, par2, par3, par4, par5, par6);
//    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if ((metadata & 8) == 0)
        {
            ItemStack var7 = new ItemStack(TFItems.trophy, 1, this.getDamageValue(world, x, y, z));
            TileEntityTFTrophy var8 = (TileEntityTFTrophy)world.getTileEntity(x, y, z);

            if (var8 == null)
            {
                return drops;
            }
//            if (var8.func_145904_a() == 3 && var8.func_145907_c() != null && var8.func_145907_c().length() > 0)
//            {
//                var7.setTagCompound(new NBTTagCompound());
//                var7.getTagCompound().setString("SkullOwner", var8.func_145907_c());
//            }
            drops.add(var7);
        }
        return drops;
    }

    @Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
    {
        return TFItems.trophy;
    }

}
