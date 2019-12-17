package twilightforest.block;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.HugeLilypadPiece;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;
import java.util.List;

public class BlockTFHugeLilyPad extends BushBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<HugeLilypadPiece> PIECE = EnumProperty.create("piece", HugeLilypadPiece.class);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.015625, 1);

	private boolean isSelfDestructing = false;

	protected BlockTFHugeLilyPad() {
		super(Properties.create(Material.PLANTS).sound(SoundType.PLANT));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH).with(PIECE, HugeLilypadPiece.NW));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, PIECE);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return (state.get(FACING).getHorizontalIndex() | (state.get(PIECE).ordinal() << 2)) & 0b1111;
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		meta = meta & 0b1111;
		return getDefaultState().with(FACING, Direction.byHorizontalIndex(meta & 0b0011)).with(PIECE, HugeLilypadPiece.values()[(meta & 0b1100) >> 2]);
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock() == Blocks.WATER;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state) {
		//TwilightForestMod.LOGGER.info("Destroying giant lilypad at {}, state {}", pos, state);

		if (!this.isSelfDestructing) {
			this.setGiantBlockToAir(world, pos, state);
		}
	}

	private void setGiantBlockToAir(World world, BlockPos pos, BlockState state) {
		// this flag is not threadsafe
		this.isSelfDestructing = true;

		for (BlockPos check : this.getAllMyBlocks(pos, state)) {
			BlockState stateThere = world.getBlockState(check);
			if (stateThere.getBlock() == this) {
				world.destroyBlock(check, false);
			}
		}

		this.isSelfDestructing = false;
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
		for (BlockPos check : this.getAllMyBlocks(pos, state)) {
			BlockState dStateBelow = world.getBlockState(check.down());

			if (!(dStateBelow.getBlock() == Blocks.WATER || dStateBelow.getBlock() == Blocks.FLOWING_WATER)
					|| dStateBelow.get(BlockLiquid.LEVEL) != 0) {
				return false;
			}

			if (world.getBlockState(check).getBlock() != this) {
				//TwilightForestMod.LOGGER.info("giant lilypad cannot stay because we can't find all 4 pieces");
				return false;
			}
		}

		return true;
	}

	/**
	 * Get all 4 coordinates for all parts of this lily pad.
	 */
	public List<BlockPos> getAllMyBlocks(BlockPos pos, BlockState state) {
		List<BlockPos> pieces = Lists.newArrayListWithCapacity(4);
		if (state.getBlock() == this) {
			// find NW corner
			BlockPos nwPos = pos;
			switch (state.get(PIECE)) {
				case NE:
					nwPos = nwPos.west();
					break;
				case SE:
					nwPos = nwPos.north().west();
					break;
				case SW:
					nwPos = nwPos.north();
					break;
				default:
					break;
			}

			pieces.add(nwPos);
			pieces.add(nwPos.south());
			pieces.add(nwPos.east());
			pieces.add(nwPos.south().east());
		}

		return pieces;
	}

	// [VanillaCopy] of super without dropping
	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, BlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			// this.dropBlockAsItem(worldIn, pos, state, 0); TF - nodrop
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		}
	}

	@Override
	@Deprecated
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	@Deprecated
	public void addCollisionBoxToList(BlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (!(entityIn instanceof EntityBoat)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB);
		}
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		super.onEntityCollision(worldIn, pos, state, entityIn);

		if (entityIn instanceof EntityBoat) {
			worldIn.destroyBlock(new BlockPos(pos), true);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
