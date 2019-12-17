package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.Leaves3Variant;
import twilightforest.enums.ThornVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;
import twilightforest.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTFThorns extends BlockTFConnectableRotatedPillar {

	//TODO: Flatten
	public static final IProperty<ThornVariant> VARIANT = PropertyEnum.create("variant", ThornVariant.class);

	private static final float THORN_DAMAGE = 4.0F;

	BlockTFThorns() {
		super(Material.WOOD, MaterialColor.OBSIDIAN, 10);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(TFItems.creativeTab);

		if (hasVariant()) {
			this.setDefaultState(this.getDefaultState().with(VARIANT, ThornVariant.BROWN));
		}
	}

	@Override
	protected IProperty[] getAdditionalProperties() {
		return new IProperty[]{VARIANT};
	}

	protected boolean hasVariant() {
		return true;
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return hasVariant() ? super.getMetaFromState(state) | state.getValue(VARIANT).ordinal() : super.getMetaFromState(state);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return hasVariant() ? super.getStateFromMeta(meta).with(VARIANT, ThornVariant.values()[meta & 0b11]) : super.getStateFromMeta(meta);
	}

	@Override
	public MaterialColor getMaterialColor(BlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(VARIANT) == ThornVariant.GREEN ? MaterialColor.FOLIAGE : super.getMaterialColor(state, world, pos);
	}

	@Override
	protected boolean canConnectTo(BlockState state, BlockState otherState, IBlockAccess world, BlockPos pos, Direction connectTo) {
		return (otherState.getBlock() instanceof BlockTFThorns
				|| otherState.getBlock() == TFBlocks.thorn_rose
				||(otherState.getBlock() == TFBlocks.twilight_leaves_3 && otherState.getValue(BlockTFLeaves3.VARIANT) == Leaves3Variant.THORN)
				|| otherState.getMaterial() == Material.GRASS
				|| otherState.getMaterial() == Material.GROUND)
				|| super.canConnectTo(state, otherState, world, pos, connectTo);
	}

	@Override
	public int damageDropped(BlockState state) {
		return hasVariant() ? state.getValue(VARIANT).ordinal() : 0;
	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		return PathNodeType.DAMAGE_CACTUS;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		entity.attackEntityFrom(DamageSource.CACTUS, THORN_DAMAGE);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		BlockState state = world.getBlockState(pos);

		if (state.getBlock() instanceof BlockTFThorns && state.get(AXIS) == Direction.Axis.Y)
			onEntityCollision(state, world, pos, entity);

		super.onEntityWalk(world, pos, entity);
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
		if (!player.abilities.isCreativeMode) {
			if (!world.isRemote) {
				// grow back
				world.setBlockState(pos, state, 2);
				// grow more
				this.doThornBurst(world, pos, state);
			}
		} else {
			world.setBlockToAir(pos);
		}

		return true;
	}

	@Override
	@Deprecated
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	/**
	 * Grow thorns out of both the ends, then maybe in another direction too
	 */
	private void doThornBurst(World world, BlockPos pos, BlockState state) {
		switch (state.get(AXIS)) {
			case Y:
				growThorns(world, pos, Direction.UP);
				growThorns(world, pos, Direction.DOWN);
				break;
			case X:
				growThorns(world, pos, Direction.EAST);
				growThorns(world, pos, Direction.WEST);
				break;
			case Z:
				growThorns(world, pos, Direction.NORTH);
				growThorns(world, pos, Direction.SOUTH);
				break;
		}

		// also try three random directions
		growThorns(world, pos, Direction.random(world.rand));
		growThorns(world, pos, Direction.random(world.rand));
		growThorns(world, pos, Direction.random(world.rand));
	}

	/**
	 * grow several green thorns in the specified direction
	 */
	private void growThorns(World world, BlockPos pos, Direction dir) {
		int length = 1 + world.rand.nextInt(3);

		for (int i = 1; i < length; i++) {
			BlockPos dPos = pos.offset(dir, i);

			if (world.isAirBlock(dPos)) {
				world.setBlockState(dPos, getDefaultState().with(AXIS, dir.getAxis()).with(VARIANT, ThornVariant.GREEN), 2);
			} else {
				break;
			}
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state) {
		int range = 4;
		int exRange = range + 1;

		if (world.isAreaLoaded(pos, exRange)) {
			for (BlockPos pos_ : WorldUtil.getAllAround(pos, range)) {
				BlockState state_ = world.getBlockState(pos_);
				if (state_.getBlock().isLeaves(state_, world, pos_)) {
					state.getBlock().beginLeavesDecay(state_, world, pos_);
				}
			}
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public boolean canSustainLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
		return face.getAxis() != state.getValue(AXIS) ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	@Deprecated
	public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		return (blockAccess.getBlockState(pos.offset(side)).getBlock() instanceof BlockTFThorns || super.shouldSideBeRendered(blockState, blockAccess, pos, side));
	}
}
