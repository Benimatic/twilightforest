package twilightforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		super(Material.leaves);
		this.setHardness(2F);
		this.setResistance(10F);
		this.setStepSound(Block.soundTypeGrass);
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

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	return 3;
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

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
	public int quantityDropped(Random par1Random)
    {
    	return par1Random.nextInt(40) == 0 ? 1 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int meta, Random par2Random, int par3)
    {
    	return Item.getItemFromBlock(TFBlocks.sapling);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int meta, float par6, int fortune)
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

