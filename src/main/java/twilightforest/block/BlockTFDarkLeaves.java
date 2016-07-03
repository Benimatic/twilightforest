package twilightforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFDarkLeaves extends Block {

	protected BlockTFDarkLeaves() {
		super(Material.LEAVES);
		this.setHardness(2F);
		this.setResistance(10F);
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(TFItems.creativeTab);
		
		this.textureName = TwilightForestMod.ID + ":darkwood_leaves2";
	}
	

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        int red = 0;
        int grn = 0;
        int blu = 0;

        for (int dz = -1; dz <= 1; ++dz)
        {
            for (int dx = -1; dx <= 1; ++dx)
            {
                int i2 = world.getBiomeGenForCoords(x + dx, z + dz).getBiomeFoliageColor(x + dx, y, z + dz);
                red += (i2 & 16711680) >> 16;
                grn += (i2 & 65280) >> 8;
                blu += i2 & 255;
            }
        }

        return (red / 9 & 255) << 16 | (grn / 9 & 255) << 8 | blu / 9 & 255;
    }


    @Override
	public int damageDropped(IBlockState state) {
    	return 3;
	}

    @Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return 1;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return 0;
	}

    @Override
	public int quantityDropped(Random par1Random)
    {
    	return par1Random.nextInt(40) == 0 ? 1 : 0;
    }

    @Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
    {
    	return Item.getItemFromBlock(TFBlocks.sapling);
    }

    @Override
	public void dropBlockAsItemWithChance(World par1World, BlockPos pos, IBlockState state, float par6, int fortune)
    {
    	if (!par1World.isRemote)
    	{
    		if (par1World.rand.nextInt(40) == 0)
    		{
    			Item var9 = this.getItemDropped(meta, par1World.rand, fortune);
    			this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(var9, 1, this.damageDropped(meta)));
    		}
    	}
    }

}

