package twilightforest.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import java.util.List;
import java.util.Random;

public class BlockTFForceField extends BlockTFConnectableRotatedPillar implements ModelRegisterCallback {

	public static final List<DyeColor> VALID_COLORS = ImmutableList.of(DyeColor.PURPLE, DyeColor.PINK, DyeColor.ORANGE, DyeColor.GREEN, DyeColor.BLUE);
	public static final IProperty<DyeColor> COLOR = PropertyEnum.create("color", DyeColor.class, VALID_COLORS);

	BlockTFForceField() {
		super(Material.BARRIER, 2);
		this.setBlockUnbreakable();
		this.setResistance(Float.MAX_VALUE);
		this.setLightLevel(2F / 15F);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(this.getDefaultState().withProperty(COLOR, DyeColor.PURPLE));
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return VALID_COLORS.indexOf(state.getValue(COLOR)) + (((state.getValue(AXIS).ordinal() + 1) % 3) * 5);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, VALID_COLORS.get(meta % 5)).withProperty(AXIS, Direction.Axis.values()[((meta / 5) + 1) % 3]);
	}

	@Override
	protected IProperty[] getAdditionalProperties() {
		return new IProperty[]{ COLOR };
	}

	@Override
	protected AxisAlignedBB getSidedAABBStraight(Direction facing, Direction.Axis axis) {
		return makeQuickAABB(
				facing == Direction.EAST  || axis == Direction.Axis.X ? 16 : this.boundingBoxWidthLower,
				facing == Direction.UP    || axis == Direction.Axis.Y ? 16 : this.boundingBoxWidthLower,
				facing == Direction.SOUTH || axis == Direction.Axis.Z ? 16 : this.boundingBoxWidthLower,
				facing == Direction.WEST  || axis == Direction.Axis.X ?  0 : this.boundingBoxWidthUpper,
				facing == Direction.DOWN  || axis == Direction.Axis.Y ?  0 : this.boundingBoxWidthUpper,
				facing == Direction.NORTH || axis == Direction.Axis.Z ?  0 : this.boundingBoxWidthUpper);
	}

	@Override
	protected boolean canConnectTo(BlockState state, BlockState otherState, IBlockAccess world, BlockPos pos, Direction connectTo) {
		BlockFaceShape blockFaceShape = otherState.getBlockFaceShape(world, pos, connectTo);

		return blockFaceShape == BlockFaceShape.SOLID
				|| blockFaceShape == BlockFaceShape.MIDDLE_POLE_THIN
				|| super.canConnectTo(state, otherState, world, pos, connectTo);
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		for (int i = 0; i < COLOR.getAllowedValues().size(); i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
		return face.getAxis() != state.getValue(AXIS) ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.CENTER_SMALL;
	}

	@Override
	public int damageDropped(BlockState state) {
		return VALID_COLORS.indexOf(state.getValue(COLOR));
	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return false; // TODO: ???
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName(), "inventory");
		for (int i = 0; i < VALID_COLORS.size(); i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, mrl);
		}

		//ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(blockState.getProperties().toArray(new IProperty[blockState.getProperties().size()])).build());
	}
}
