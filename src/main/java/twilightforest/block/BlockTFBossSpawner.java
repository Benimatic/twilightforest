package twilightforest.block;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import twilightforest.block.enums.BossVariant;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFHydraSpawner;
import twilightforest.tileentity.TileEntityTFKnightPhantomsSpawner;
import twilightforest.tileentity.TileEntityTFLichSpawner;
import twilightforest.tileentity.TileEntityTFNagaSpawner;
import twilightforest.tileentity.TileEntityTFSnowQueenSpawner;
import twilightforest.tileentity.TileEntityTFTowerBossSpawner;

public class BlockTFBossSpawner extends Block {
	public static final PropertyEnum<BossVariant> VARIANT = PropertyEnum.create("boss", BossVariant.class, (Predicate<BossVariant>) input -> input != BossVariant.NONE);

	protected BlockTFBossSpawner()
	{
		super(Material.ROCK);
		this.setHardness(20F);
		//this.setResistance(10F);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, BossVariant.NAGA));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, BossVariant.values()[meta]);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch (state.getValue(VARIANT)) {
			case NAGA: return new TileEntityTFNagaSpawner();
			case LICH: return new TileEntityTFLichSpawner();
			case HYDRA: return new TileEntityTFHydraSpawner();
			case URGHAST: return new TileEntityTFTowerBossSpawner();
			case KNIGHT_PHANTOM: return new TileEntityTFKnightPhantomsSpawner();
			case SNOW_QUEEN: return new TileEntityTFSnowQueenSpawner();
			default: return null;
		}
    }

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int fortune)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        //par3List.add(new ItemStack(par1, 1, 0));
        //par3List.add(new ItemStack(par1, 1, 1));
        //par3List.add(new ItemStack(par1, 1, 2));
        //par3List.add(new ItemStack(par1, 1, 3));
        //par3List.add(new ItemStack(par1, 1, 4));
    }
}
