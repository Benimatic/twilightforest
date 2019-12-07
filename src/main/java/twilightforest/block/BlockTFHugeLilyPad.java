package twilightforest.block;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
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

public class BlockTFHugeLilyPad extends BlockBush implements ModelRegisterCallback {

	public static final IProperty<Direction> FACING = BlockHorizontal.FACING;
	public static final IProperty<HugeLilypadPiece> PIECE = PropertyEnum.create("piece", HugeLilypadPiece.class);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.015625, 1);

	private boolean isSelfDestructing = false;

	protected BlockTFHugeLilyPad() {
		super(Material.PLANTS);
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, Direction.NORTH).withProperty(PIECE, HugeLilypadPiece.NW));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, PIECE);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return (state.getValue(FACING).getHorizontalIndex() | (state.getValue(PIECE).ordinal() << 2)) & 0b1111;
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		meta = meta & 0b1111;
		return getDefaultState().withProperty(FACING, Direction.byHorizontalIndex(meta & 0b0011)).withProperty(PIECE, HugeLilypadPiece.values()[(meta & 0b1100) >> 2]);
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
					|| dStateBelow.getValue(BlockLiquid.LEVEL) != 0) {
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
			switch (state.getValue(PIECE)) {
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
	public EnumPushReaction getPushReaction(BlockState state) {
		return EnumPushReaction.BLOCK;
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
