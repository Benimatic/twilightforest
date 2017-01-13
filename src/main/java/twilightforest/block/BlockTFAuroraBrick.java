package twilightforest.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFAuroraBrick extends Block {
	
	public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 15);

	public BlockTFAuroraBrick() {
		super(Material.PACKED_ICE);
		
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);

	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, MathHelper.clamp(meta, 0, 15));
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    @Override
	public int damageDropped(IBlockState state) {
    	return 0;
	}

    @Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		return getDefaultState().withProperty(VARIANT, Math.abs(pos.getX() + pos.getZ()) % 16);
    }
}
