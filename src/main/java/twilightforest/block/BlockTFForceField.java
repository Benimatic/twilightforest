package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.IProperty;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockTFForceField extends BlockTFConnectableRotatedPillar {

	//public static final List<DyeColor> VALID_COLORS = ImmutableList.of(DyeColor.PURPLE, DyeColor.PINK, DyeColor.ORANGE, DyeColor.GREEN, DyeColor.BLUE);

	BlockTFForceField() {
		super(Properties.create(Material.BARRIER).hardnessAndResistance(-1.0F).lightValue(2 / 15), 2);
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
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

//	@Override
//	protected boolean canConnectTo(BlockState state, BlockState otherState, IBlockAccess world, BlockPos pos, Direction connectTo) {
//		BlockFaceShape blockFaceShape = otherState.getBlockFaceShape(world, pos, connectTo);
//
//		return blockFaceShape == BlockFaceShape.SOLID
//				|| blockFaceShape == BlockFaceShape.MIDDLE_POLE_THIN
//				|| super.canConnectTo(state, otherState, world, pos, connectTo);
//	}

//	@Override
//	public Item getItemDropped(BlockState state, Random rand, int fortune) {
//		return Items.AIR;
//	}

	@Override
	public boolean isSolid(BlockState state) {
		return false;
	}

//	@Override
//	public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
//		return face.getAxis() != state.getValue(AXIS) ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.CENTER_SMALL;
//	}

//	@Override
//	public int damageDropped(BlockState state) {
//		return VALID_COLORS.indexOf(state.getValue(COLOR));
//	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
		return false; // TODO: ???
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@Deprecated
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
		return world.getBlockState(pos.offset(face)).getBlock() != this && Block.shouldSideBeRendered(state, world, pos, face);
	}

	@Override
	protected IProperty[] getAdditionalProperties() {
		return new IProperty[0];
	}
}
