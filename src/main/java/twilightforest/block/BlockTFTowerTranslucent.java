package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.TowerDeviceVariant;
import twilightforest.enums.TowerTranslucentVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

import java.util.Random;

public class BlockTFTowerTranslucent extends Block implements ModelRegisterCallback {

	public static final IProperty<TowerTranslucentVariant> VARIANT = PropertyEnum.create("variant", TowerTranslucentVariant.class);
	private static final AxisAlignedBB REAPPEARING_BB = new AxisAlignedBB(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);

	public BlockTFTowerTranslucent() {
		super(Material.GLASS);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, TowerTranslucentVariant.REAPPEARING_INACTIVE));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, TowerTranslucentVariant.values()[meta]);
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
	public int tickRate(World world) {
		return 15;
	}

	@Override
	public Item getItemDropped(BlockState state, Random random, int fortune) {
		return Items.AIR;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		return false;
	}

	@Override
	@Deprecated
	public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		switch (state.getValue(VARIANT)) {
			case REAPPEARING_INACTIVE:
			case REAPPEARING_ACTIVE:
				return NULL_AABB;
			default:
				return super.getCollisionBoundingBox(state, world, pos);
		}
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		switch (state.getValue(VARIANT)) {
			case REAPPEARING_INACTIVE:
			case REAPPEARING_ACTIVE:
				return REAPPEARING_BB;
			default:
				return FULL_BLOCK_AABB;
		}
	}

	@Override
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		switch (state.getValue(VARIANT)) {
			case REAPPEARING_INACTIVE:
			case REAPPEARING_ACTIVE:
				return BlockFaceShape.UNDEFINED;
			default:
				return super.getBlockFaceShape(worldIn, state, pos, face);
		}
	}

	@Override
	@Deprecated
	public float getBlockHardness(BlockState state, World world, BlockPos pos) {
		// reverter replacement is like glass
		switch (state.getValue(VARIANT)) {
			case REVERTER_REPLACEMENT:
			case REACTOR_DEBRIS:
				return 0.3F;
			default:
				return super.getBlockHardness(state, world, pos);
		}
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos) {
		switch (world.getBlockState(pos).getValue(VARIANT)) {
			case REAPPEARING_INACTIVE:
			case REAPPEARING_ACTIVE:
				return true;
			default:
				return false;
		}
	}

	// todo 1.10 smart model for REACTOR_DEBRIS that randomly chooses sides from portal/netherrack/bedrock/obsidian

	@Override
	public void updateTick(World world, BlockPos pos, BlockState state, Random random) {

		if (world.isRemote) return;

		TowerTranslucentVariant variant = state.getValue(VARIANT);

		if (variant == TowerTranslucentVariant.BUILT_ACTIVE) {
			world.setBlockToAir(pos);
			world.notifyNeighborsRespectDebug(pos, this, false);
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.3F, 0.5F);
			//world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);

			// activate all adjacent inactive vanish blocks
			for (Direction e : Direction.VALUES) {
				BlockTFTowerDevice.checkAndActivateVanishBlock(world, pos.offset(e));
			}

		} else if (variant == TowerTranslucentVariant.REAPPEARING_ACTIVE) {
			world.setBlockState(pos, TFBlocks.tower_device.getDefaultState().withProperty(BlockTFTowerDevice.VARIANT, TowerDeviceVariant.REAPPEARING_INACTIVE));
			world.notifyNeighborsRespectDebug(pos, this, false);
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
			//world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);

		} else if (variant == TowerTranslucentVariant.REAPPEARING_INACTIVE) {
			BlockTFTowerDevice.changeToActiveVanishBlock(world, pos, TowerTranslucentVariant.REAPPEARING_ACTIVE);
		}
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelUtils.registerToStateSingleVariant(this, VARIANT);
	}
}
