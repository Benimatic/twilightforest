package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockTFCritter extends Block {

	private final float WIDTH = getWidth();

	private final AxisAlignedBB DOWN_BB  = new AxisAlignedBB(0.5F - WIDTH, 1.0F - WIDTH * 2.0F, 0.2F, 0.5F + WIDTH, 1.0F, 0.8F);
	private final AxisAlignedBB UP_BB    = new AxisAlignedBB(0.5F - WIDTH, 0.0F, 0.2F, 0.5F + WIDTH, WIDTH * 2.0F, 0.8F);
	private final AxisAlignedBB NORTH_BB = new AxisAlignedBB(0.5F - WIDTH, 0.2F, 1.0F - WIDTH * 2.0F, 0.5F + WIDTH, 0.8F, 1.0F);
	private final AxisAlignedBB SOUTH_BB = new AxisAlignedBB(0.5F - WIDTH, 0.2F, 0.0F, 0.5F + WIDTH, 0.8F, WIDTH * 2.0F);
	private final AxisAlignedBB WEST_BB  = new AxisAlignedBB(1.0F - WIDTH * 2.0F, 0.2F, 0.5F - WIDTH, 1.0F, 0.8F, 0.5F + WIDTH);
	private final AxisAlignedBB EAST_BB  = new AxisAlignedBB(0.0F, 0.2F, 0.5F - WIDTH, WIDTH * 2.0F, 0.8F, 0.5F + WIDTH);

	protected BlockTFCritter(Properties props) {
		super(props.create(Material.MISCELLANEOUS).sound(SoundType.SLIME).hardnessAndResistance(0.0F));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		this.setDefaultState(stateContainer.getBaseState().with(DirectionalBlock.FACING, Direction.UP));
	}

	public float getWidth() {
		return 0.15F;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockDirectional.FACING);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(BlockDirectional.FACING).getIndex();
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().with(BlockDirectional.FACING, Direction.byIndex(meta));
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		switch (state.get(DirectionalBlock.FACING)) {
			case DOWN:
				return DOWN_BB;
			case UP:
			default:
				return UP_BB;
			case NORTH:
				return NORTH_BB;
			case SOUTH:
				return SOUTH_BB;
			case WEST:
				return WEST_BB;
			case EAST:
				return EAST_BB;
		}
	}

	@Override
	@Deprecated
	public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, Direction side) {
		return canPlaceAt(world, pos.offset(side.getOpposite()), side);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		for (Direction side : Direction.values()) {
			if (canPlaceAt(world, pos.offset(side.getOpposite()), side)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Deprecated
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction sideHit, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		BlockState state = getDefaultState();

		if (canPlaceAt(world, pos.offset(sideHit.getOpposite()), sideHit)) {
			state = state.with(DirectionalBlock.FACING, sideHit);
		}

		return state;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, BlockState state) {
		world.scheduleUpdate(pos, this, 1);
	}

	@Override
	public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
		checkAndDrop(world, pos, state);
	}

	protected boolean checkAndDrop(World world, BlockPos pos, BlockState state) {
		Direction facing = state.get(DirectionalBlock.FACING);
		if (!canPlaceAt(world, pos.offset(facing.getOpposite()), facing)) {
			world.destroyBlock(pos, true);
			return false;
		}
		return true;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		checkAndDrop(world, pos, state);
	}

	protected boolean canPlaceAt(IBlockAccess world, BlockPos pos, Direction facing) {
		BlockState state = world.getBlockState(pos);
		return state.getBlockFaceShape(world, pos, facing) == BlockFaceShape.SOLID
				&& (!isExceptBlockForAttachWithPiston(state.getBlock())
				|| state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.CACTUS);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	public abstract ItemStack getSquishResult(); // oh no!
}
