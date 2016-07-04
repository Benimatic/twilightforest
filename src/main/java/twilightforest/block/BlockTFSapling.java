package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.block.enums.SaplingVariant;
import twilightforest.item.TFItems;
import twilightforest.world.TFGenCanopyTree;
import twilightforest.world.TFGenDarkCanopyTree;
import twilightforest.world.TFGenHollowTree;
import twilightforest.world.TFGenLargeRainboak;
import twilightforest.world.TFGenMangroveTree;
import twilightforest.world.TFGenMinersTree;
import twilightforest.world.TFGenSmallRainboak;
import twilightforest.world.TFGenSmallTwilightOak;
import twilightforest.world.TFGenSortingTree;
import twilightforest.world.TFGenTreeOfTime;
import twilightforest.world.TFGenTreeOfTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFSapling extends BlockSapling
{

	public static final PropertyEnum<SaplingVariant> TF_TYPE = PropertyEnum.create("tf_type", SaplingVariant.class);

	protected BlockTFSapling() {
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState()
				.withProperty(STAGE, 0)
				.withProperty(TYPE, BlockPlanks.EnumType.OAK)
				.withProperty(TF_TYPE, SaplingVariant.OAK));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STAGE, TYPE, TF_TYPE);
	}
	
    @Override
	public void grow(World world, BlockPos pos, IBlockState state, Random rand) {
		WorldGenerator treeGenerator;

		switch (state.getValue(TF_TYPE)) {
			case CANOPY: treeGenerator = new TFGenCanopyTree(true); break;
			case MANGROVE: treeGenerator = new TFGenMangroveTree(true); break;
			case DARKWOOD: treeGenerator = new TFGenDarkCanopyTree(true); break;
			case HOLLOW_OAK: treeGenerator = new TFGenHollowTree(true); break;
			case TIME: treeGenerator = new TFGenTreeOfTime(true); break;
			case TRANSFORMATION: treeGenerator = new TFGenTreeOfTransformation(true); break;
			case MINING: treeGenerator = new TFGenMinersTree(true); break;
			case SORTING: treeGenerator = new TFGenSortingTree(true); break;
			case RAINBOW: treeGenerator = rand.nextInt(7) == 0 ? new TFGenLargeRainboak(true) : new TFGenSmallRainboak(true); break;
			case OAK:
			default: treeGenerator = new TFGenSmallTwilightOak(true); break;
		}

		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

    	if (!treeGenerator.generate(world, rand, pos))
    	{
			world.setBlockState(pos, state, 4);
    	}
    }
    
	@Override
	public int damageDropped(IBlockState state)
	{
        return getMetaFromState(state);
    }

	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
        par3List.add(new ItemStack(par1, 1, 6));
        par3List.add(new ItemStack(par1, 1, 7));
        par3List.add(new ItemStack(par1, 1, 8));
        par3List.add(new ItemStack(par1, 1, 9));
    }

}
