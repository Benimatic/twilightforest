package twilightforest.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.block.enums.ThornVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;
import twilightforest.util.WorldUtil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockTFThorns extends BlockRotatedPillar implements ModelRegisterCallback {

	public static final PropertyEnum<ThornVariant> VARIANT = PropertyEnum.create("variant", ThornVariant.class);

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");

	public static final PropertyBool[] DIRECTIONS = new PropertyBool[]{ NORTH, SOUTH, WEST, EAST };

	private static final float THORN_DAMAGE = 4.0F;

	protected BlockTFThorns() {
		super(Material.WOOD);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(TFItems.creativeTab);

		if (hasVariant())
			this.setDefaultState(blockState.getBaseState()
					.withProperty(AXIS, EnumFacing.Axis.Y)
					.withProperty(VARIANT, ThornVariant.BROWN)
					.withProperty(NORTH, false).withProperty(SOUTH, false)
					.withProperty(WEST, false).withProperty(EAST, false));
	}

	protected boolean hasVariant() {
		return true;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return hasVariant() ? new BlockStateContainer(this, AXIS, VARIANT, NORTH, SOUTH, WEST, EAST) : new BlockStateContainer(this, AXIS, NORTH, SOUTH, WEST, EAST);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return hasVariant() ? super.getMetaFromState(state) | state.getValue(VARIANT).ordinal() : super.getMetaFromState(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return hasVariant() ? super.getStateFromMeta(meta).withProperty(VARIANT, ThornVariant.values()[meta & 0b11]) : super.getStateFromMeta(meta);
	}

	@Override
	@Deprecated
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		// If our axis is rotated (i.e. not upright), then adjust the actual sides tested
		// This allows the entire model to be rotated in the assets in a cleaner way

		EnumFacing.Axis axis = state.getValue(AXIS);

		for (PropertyBool property : DIRECTIONS)
			state = state.withProperty(property, canConnectTo(state, world, pos, getFacingFromPropertyWithAxis(property, axis)));

		return state;
	}

	private boolean canConnectTo(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing connectTo) {
		IBlockState otherState = world.getBlockState(pos.offset(connectTo));
		return (otherState.getBlock() == TFBlocks.thorns || otherState.getBlock() == TFBlocks.burntThorns)
				&& state.getValue(AXIS) != connectTo.getAxis();
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> list, @Nullable Entity entity, boolean useActualState) {
		if (!useActualState) state = this.getActualState(state, world, pos);

		EnumFacing.Axis axis = state.getValue(AXIS);

		addCollisionBoxToList(pos, aabb, list, BlockTFForceField.makeQuickAABB(
				axis == EnumFacing.Axis.X ? 16 :  3,
				axis == EnumFacing.Axis.X ?  0 : 13,
				axis == EnumFacing.Axis.Y ? 16 :  3,
				axis == EnumFacing.Axis.Y ?  0 : 13,
				axis == EnumFacing.Axis.Z ? 16 :  3,
				axis == EnumFacing.Axis.Z ?  0 : 13));

		for (EnumFacing facing : EnumFacing.VALUES) {
			if (facing.getAxis() != axis && state.getValue(getPropertyFromFacingWithAxis(facing, axis))) {
				addCollisionBoxToList(pos, aabb, list, BlockTFForceField.makeQuickAABB(
						facing == EnumFacing.EAST  ? 16 :  3,
						facing == EnumFacing.WEST  ?  0 : 13,
						facing == EnumFacing.UP    ? 16 :  3,
						facing == EnumFacing.DOWN  ?  0 : 13,
						facing == EnumFacing.SOUTH ? 16 :  3,
						facing == EnumFacing.NORTH ?  0 : 13));
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		state = this.getActualState(state, world, pos);

		switch (state.getValue(AXIS)) {
			case X:
				return BlockTFForceField.makeQuickAABB(
						 0, 16,  // WEST & EAST
						state.getValue(NORTH) ?  0 :  3,  // DOWN
						state.getValue(SOUTH) ? 16 : 13,  // UP
						state.getValue(WEST ) ?  0 :  3,  // NORTH
						state.getValue(EAST ) ? 16 : 13); // SOUTH
			case Z:
				return BlockTFForceField.makeQuickAABB(
						state.getValue(EAST ) ?  0 :  3,  // WEST
						state.getValue(WEST ) ? 16 : 13,  // EAST
						state.getValue(SOUTH) ?  0 :  3,  // DOWN
						state.getValue(NORTH) ? 16 : 13,  // UP
						0, 16); // NORTH & SOUTH
			default:
				return BlockTFForceField.makeQuickAABB(
						state.getValue(WEST)  ?  0 :  3,  // WEST
						state.getValue(EAST)  ? 16 : 13,  // EAST
						 0, 16,  // DOWN & UP
						state.getValue(NORTH) ?  0 :  3,  // NORTH
						state.getValue(SOUTH) ? 16 : 13); // SOUTH
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		entity.attackEntityFrom(DamageSource.CACTUS, THORN_DAMAGE);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() instanceof BlockTFThorns && state.getValue(AXIS) == EnumFacing.Axis.Y)
			onEntityCollidedWithBlock(world, pos, state, entity);

		super.onEntityWalk(world, pos, entity);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean harvest) {
		if (!player.capabilities.isCreativeMode) {
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
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	/**
	 * Grow thorns out of both the ends, then maybe in another direction too
	 */
	private void doThornBurst(World world, BlockPos pos, IBlockState state) {
		switch (state.getValue(AXIS)) {
			case Y:
				growThorns(world, pos, EnumFacing.UP);
				growThorns(world, pos, EnumFacing.DOWN);
				break;
			case X:
				growThorns(world, pos, EnumFacing.EAST);
				growThorns(world, pos, EnumFacing.WEST);
				break;
			case Z:
				growThorns(world, pos, EnumFacing.NORTH);
				growThorns(world, pos, EnumFacing.SOUTH);
				break;
		}

		// also try three random directions
		growThorns(world, pos, EnumFacing.random(world.rand));
		growThorns(world, pos, EnumFacing.random(world.rand));
		growThorns(world, pos, EnumFacing.random(world.rand));
	}

	/**
	 * grow several green thorns in the specified direction
	 */
	private void growThorns(World world, BlockPos pos, EnumFacing dir) {
		int length = 1 + world.rand.nextInt(3);

		for (int i = 1; i < length; i++) {
			BlockPos dPos = pos.offset(dir, i);

			if (world.isAirBlock(dPos)) {
				world.setBlockState(dPos, getDefaultState().withProperty(AXIS, dir.getAxis()).withProperty(VARIANT, ThornVariant.GREEN), 2);
			} else {
				break;
			}
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		int range = 4;
		int exRange = range + 1;

		if (world.isAreaLoaded(pos, exRange)) {
			for (BlockPos pos_ : WorldUtil.getAllAround(pos, range)) {
				IBlockState state_ = world.getBlockState(pos_);
				if (state_.getBlock().isLeaves(state_, world, pos_)) {
					state.getBlock().beginLeavesDecay(state_, world, pos_);
				}
			}
		}
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 0;
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		for (int i = 0; i < (hasVariant() ? ThornVariant.values().length : 1); i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}


	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelUtils.registerToStateSingleVariant(this, VARIANT);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@Deprecated
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side.getAxis() == blockState.getValue(AXIS) && (blockAccess.getBlockState(pos.offset(side)).getBlock() == this || super.shouldSideBeRendered(blockState, blockAccess, pos, side));
	}

	private EnumFacing getFacingFromPropertyWithAxis(PropertyBool property, EnumFacing.Axis axis) {
		switch (axis) {
			case X:
				if (property == NORTH) return EnumFacing.DOWN;
				if (property == SOUTH) return EnumFacing.UP;
				if (property == WEST ) return EnumFacing.NORTH;
				if (property == EAST ) return EnumFacing.SOUTH;
			case Z:
				if (property == NORTH) return EnumFacing.UP;
				if (property == SOUTH) return EnumFacing.DOWN;
				if (property == WEST ) return EnumFacing.EAST;
				if (property == EAST ) return EnumFacing.WEST;
			default:
				if (property == NORTH) return EnumFacing.NORTH;
				if (property == SOUTH) return EnumFacing.SOUTH;
				if (property == WEST ) return EnumFacing.WEST;
				if (property == EAST ) return EnumFacing.EAST;
		}

		return EnumFacing.UP;
	}

	private PropertyBool getPropertyFromFacingWithAxis(EnumFacing facing, EnumFacing.Axis axis) {
		switch (axis) {
			case X:
				if (facing == EnumFacing.DOWN ) return NORTH;
				if (facing == EnumFacing.UP   ) return SOUTH;
				if (facing == EnumFacing.NORTH) return WEST ;
				if (facing == EnumFacing.SOUTH) return EAST ;
				break;
			case Z:
				if (facing == EnumFacing.UP   ) return NORTH;
				if (facing == EnumFacing.DOWN ) return SOUTH;
				if (facing == EnumFacing.EAST ) return WEST ;
				if (facing == EnumFacing.WEST ) return EAST ;
				break;
			case Y:
				if (facing == EnumFacing.NORTH) return NORTH;
				if (facing == EnumFacing.SOUTH) return SOUTH;
				if (facing == EnumFacing.WEST ) return WEST ;
				if (facing == EnumFacing.EAST ) return EAST ;
				break;
		}

		TwilightForestMod.LOGGER.info("Thorns had a problem. Might wanna report to Mod authors. " + facing.getName() + " with " + axis.getName());
		return NORTH;
	}
}
