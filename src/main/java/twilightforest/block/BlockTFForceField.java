package twilightforest.block;

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockTFForceField extends Block implements ModelRegisterCallback {
	public static final PropertyBool UP    = PropertyBool.create("up");
	public static final PropertyBool DOWN  = PropertyBool.create("down");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST  = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST  = PropertyBool.create("west");
	public static final List<EnumDyeColor> VALID_COLORS = ImmutableList.of(EnumDyeColor.PURPLE, EnumDyeColor.PINK, EnumDyeColor.ORANGE, EnumDyeColor.GREEN, EnumDyeColor.BLUE);
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class, VALID_COLORS);

	protected BlockTFForceField() {
		super(Material.BARRIER);
		this.setLightLevel(2F / 15F);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState()
				.withProperty(UP, false).withProperty(DOWN, false)
				.withProperty(NORTH, false).withProperty(SOUTH, false)
				.withProperty(WEST, false).withProperty(EAST, false)
				.withProperty(COLOR, EnumDyeColor.PURPLE));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		for (PairHelper pair : PairHelper.values()) {
			if(world.getBlockState(pos.offset(pair.facing)).getBlock() == this) state = state.withProperty(pair.property, true);
		}

		return state;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> list, @Nullable Entity entity, boolean useActualState) {
		if (!useActualState) state = this.getActualState(state, world, pos);

		addCollisionBoxToList(pos, aabb, list, makeQuickAABB(7, 9, 7, 9, 7, 9));

		for (PairHelper pair1 : PairHelper.values()) {
			for (PairHelper pair2 : PairHelper.values()) {
				if (pair1.facing.getOpposite() != pair2.facing &&
						((pair1 == pair2 && state.getValue(pair1.property)) || (state.getValue(pair1.property) && state.getValue(pair2.property))))
					addCollisionBoxToList(pos, aabb, list, getSidedAABBEdged(pair1.facing, pair2.facing));
			}
		}
	}

	private static AxisAlignedBB getSidedAABBStraight(EnumFacing facing) {
		return makeQuickAABB(
				facing == EnumFacing.EAST  ? 16 : 7,
				facing == EnumFacing.WEST  ?  0 : 9,
				facing == EnumFacing.UP    ? 16 : 7,
				facing == EnumFacing.DOWN  ?  0 : 9,
				facing == EnumFacing.SOUTH ? 16 : 7,
				facing == EnumFacing.NORTH ?  0 : 9);
	}

	private static AxisAlignedBB getSidedAABBEdged(EnumFacing facing1, EnumFacing facing2) {
		return facing1 == facing2 ? getSidedAABBStraight(facing1) : makeQuickAABB(
				facing1 == EnumFacing.EAST  || facing2 == EnumFacing.EAST  ? 16 : 7,
				facing1 == EnumFacing.WEST  || facing2 == EnumFacing.WEST  ?  0 : 9,
				facing1 == EnumFacing.UP    || facing2 == EnumFacing.UP    ? 16 : 7,
				facing1 == EnumFacing.DOWN  || facing2 == EnumFacing.DOWN  ?  0 : 9,
				facing1 == EnumFacing.SOUTH || facing2 == EnumFacing.SOUTH ? 16 : 7,
				facing1 == EnumFacing.NORTH || facing2 == EnumFacing.NORTH ?  0 : 9);
	}

	static AxisAlignedBB makeQuickAABB(int x1, int x2, int y1, int y2, int z1, int z2) {
		return new AxisAlignedBB(
				x1/16.0d, y1/16.0d,
				z1/16.0d, x2/16.0d,
				y2/16.0d, z2/16.0d);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		state = this.getActualState(state, world, pos);

		return makeQuickAABB(
				state.getValue(WEST ) ?  0 : 7,
				state.getValue(EAST ) ? 16 : 9,
				state.getValue(DOWN ) ?  0 : 7,
				state.getValue(UP   ) ? 16 : 9,
				state.getValue(NORTH) ?  0 : 7,
				state.getValue(SOUTH) ? 16 : 9);
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, UP, DOWN, NORTH, SOUTH, WEST, EAST, COLOR);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return VALID_COLORS.indexOf(state.getValue(COLOR));
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, VALID_COLORS.get(meta));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		for (int i = 0; i < COLOR.getAllowedValues().size(); i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	// TODO add special CTM stuff, Should leverage EDGES format for its obscurity check on edges of corner panes. Fudge around with shouldSideBeRendered when ctm is installed too perhaps


	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName(), "inventory");
		for (int i = 0; i < VALID_COLORS.size(); i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, mrl);
		}

		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(blockState.getProperties().toArray(new IProperty[blockState.getProperties().size()])).build());
	}

	private enum PairHelper {
		UP   (EnumFacing.UP,    BlockTFForceField.UP),
		DOWN (EnumFacing.DOWN,  BlockTFForceField.DOWN),
		NORTH(EnumFacing.NORTH, BlockTFForceField.NORTH),
		EAST (EnumFacing.EAST,  BlockTFForceField.EAST),
		SOUTH(EnumFacing.SOUTH, BlockTFForceField.SOUTH),
		WEST (EnumFacing.WEST,  BlockTFForceField.WEST);

		final EnumFacing facing;
		final PropertyBool property;

		PairHelper(EnumFacing facing, PropertyBool property) {
			this.facing = facing;
			this.property = property;
		}
	}
}
