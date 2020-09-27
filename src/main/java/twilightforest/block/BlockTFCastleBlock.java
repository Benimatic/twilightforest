package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.enums.CastleBrickVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.ItemTFMazebreakerPick;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;

/**
 * Castle block makes a castle
 *
 * @author Ben
 */
public class BlockTFCastleBlock extends Block implements ModelRegisterCallback {

	public static final IProperty<CastleBrickVariant> VARIANT = PropertyEnum.create("variant", CastleBrickVariant.class);

	public BlockTFCastleBlock() {
		super(Material.ROCK, MapColor.QUARTZ);
		this.setHardness(100F);
		this.setResistance(35F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, CastleBrickVariant.NORMAL));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, CastleBrickVariant.values()[meta]);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(VARIANT) == CastleBrickVariant.ROOF ? MapColor.GRAY : super.getMapColor(state, world, pos);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		ItemStack cei = player.getHeldItemMainhand();
		if (cei.getItem() instanceof ItemTool && !(cei.getItem() instanceof ItemTFMazebreakerPick)) {
			cei.damageItem(16, player);
		}

		super.harvestBlock(world, player, pos, state, te, stack);
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> items) {
		for (CastleBrickVariant variant : CastleBrickVariant.values()) {
			items.add(new ItemStack(this, 1, variant.ordinal()));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelUtils.registerToStateSingleVariant(this, VARIANT);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
