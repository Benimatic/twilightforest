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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.advancements.TFAdvancements;
import twilightforest.enums.TowerDeviceVariant;
import twilightforest.enums.TowerTranslucentVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;
import twilightforest.tileentity.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockTFTowerDevice extends Block implements ModelRegisterCallback {

	public static final IProperty<TowerDeviceVariant> VARIANT = PropertyEnum.create("variant", TowerDeviceVariant.class);

	public BlockTFTowerDevice() {
		super(Material.WOOD, MapColor.SAND);
		this.setHardness(10F);
		this.setResistance(35F);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, TowerDeviceVariant.REAPPEARING_INACTIVE));
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
		return getDefaultState().withProperty(VARIANT, TowerDeviceVariant.values()[meta]);
	}

	@Override
	public int tickRate(World world) {
		return 15;
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, TowerDeviceVariant.REAPPEARING_INACTIVE.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.VANISH_INACTIVE.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.VANISH_LOCKED.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.VANISH_UNLOCKED.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.BUILDER_INACTIVE.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.ANTIBUILDER.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.GHASTTRAP_INACTIVE.ordinal()));
		list.add(new ItemStack(this, 1, TowerDeviceVariant.REACTOR_INACTIVE.ordinal()));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		TowerDeviceVariant variant = state.getValue(VARIANT);

		if (variant == TowerDeviceVariant.VANISH_INACTIVE) {
			if (areBlocksLocked(world, pos)) {
				world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 1.0F, 0.3F);
			} else {
				changeToActiveVanishBlock(world, pos, TowerDeviceVariant.VANISH_ACTIVE);
			}
			return true;

		} else if (variant == TowerDeviceVariant.REAPPEARING_INACTIVE) {
			if (areBlocksLocked(world, pos)) {
				world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 1.0F, 0.3F);
			} else {
				changeToActiveVanishBlock(world, pos, TowerDeviceVariant.REAPPEARING_ACTIVE);
			}
			return true;
		}
		return false;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
		switch (world.getBlockState(pos).getValue(VARIANT)) {
			case VANISH_INACTIVE:
				return 6000F;
			case VANISH_LOCKED:
				return 6000000.0F;
			default:
				return super.getExplosionResistance(world, pos, exploder, explosion);
		}
	}

	@Override
	@Deprecated
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
		// most vanish blocks can't be broken
		switch (state.getValue(VARIANT)) {
			case REAPPEARING_ACTIVE:
			case VANISH_INACTIVE:
			case VANISH_ACTIVE:
			case VANISH_LOCKED:
			case VANISH_UNLOCKED:
				return -1;
			default:
				return super.getBlockHardness(state, world, pos);
		}
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		switch (state.getValue(VARIANT)) {
			case VANISH_INACTIVE:
				return !areBlocksLocked(world, pos);
			case VANISH_LOCKED:
				return false;
			default:
				return super.canEntityDestroy(state, world, pos, entity);
		}
	}

	/**
	 * Are any of the connected tower device blocks a locked vanishing block?
	 */
	private static boolean areBlocksLocked(IBlockAccess world, BlockPos pos) {
		Set<BlockPos> checked = new HashSet<>();
		checked.add(pos);
		return areBlocksLocked(world, pos, checked);
	}

	private static boolean areBlocksLocked(IBlockAccess world, BlockPos pos, Set<BlockPos> checked) {
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos offset = pos.offset(facing);
			if (!checked.add(offset)) continue;
			IBlockState state = world.getBlockState(offset);
			if (state.getBlock() == TFBlocks.tower_device) {
				if (state.getValue(VARIANT) == TowerDeviceVariant.VANISH_LOCKED) {
					return true;
				}
				if (areBlocksLocked(world, offset, checked)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Change this block into an different device block
	 */
	public static void unlockBlock(World world, BlockPos pos) {
		IBlockState thereState = world.getBlockState(pos);

		if (thereState.getBlock() == TFBlocks.tower_device || thereState.getValue(BlockTFTowerDevice.VARIANT) == TowerDeviceVariant.VANISH_LOCKED) {
			changeToBlockState(world, pos, thereState.withProperty(VARIANT, TowerDeviceVariant.VANISH_UNLOCKED));
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		}
	}

	/**
	 * Change this block into an different device block
	 */
	private static void changeToBlockState(World world, BlockPos pos, IBlockState state) {
		Block thereBlock = world.getBlockState(pos).getBlock();

		if (thereBlock == TFBlocks.tower_device || thereBlock == TFBlocks.tower_translucent) {
			world.setBlockState(pos, state, 3);
			world.markBlockRangeForRenderUpdate(pos, pos);
			world.notifyNeighborsRespectDebug(pos, thereBlock, false);
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {

		if (world.isRemote) return;

		if (state.getValue(VARIANT) == TowerDeviceVariant.BUILDER_INACTIVE && world.isBlockPowered(pos)) {
			changeToBlockState(world, pos, state.withProperty(VARIANT, TowerDeviceVariant.BUILDER_ACTIVE));
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		}
	}

	@Override
	@Deprecated
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {

		if (world.isRemote) return;

		TowerDeviceVariant variant = state.getValue(VARIANT);

		if (variant == TowerDeviceVariant.VANISH_INACTIVE && world.isBlockPowered(pos) && !areBlocksLocked(world, pos)) {
			changeToActiveVanishBlock(world, pos, TowerDeviceVariant.VANISH_ACTIVE);
		}

		if (variant == TowerDeviceVariant.REAPPEARING_INACTIVE && world.isBlockPowered(pos) && !areBlocksLocked(world, pos)) {
			changeToActiveVanishBlock(world, pos, TowerDeviceVariant.REAPPEARING_ACTIVE);
		}

		if (variant == TowerDeviceVariant.BUILDER_INACTIVE && world.isBlockPowered(pos)) {
			changeToBlockState(world, pos, state.withProperty(VARIANT, TowerDeviceVariant.BUILDER_ACTIVE));
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
			world.scheduleUpdate(pos, this, 4);
		}

		if (variant == TowerDeviceVariant.BUILDER_ACTIVE && !world.isBlockPowered(pos)) {
			changeToBlockState(world, pos, state.withProperty(VARIANT, TowerDeviceVariant.BUILDER_INACTIVE));
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
			world.scheduleUpdate(pos, this, 4);
		}

		if (variant == TowerDeviceVariant.BUILDER_TIMEOUT && !world.isBlockPowered(pos)) {
			changeToBlockState(world, pos, state.withProperty(VARIANT, TowerDeviceVariant.BUILDER_INACTIVE));
		}

		if (variant == TowerDeviceVariant.GHASTTRAP_INACTIVE && isInactiveTrapCharged(world, pos) && world.isBlockPowered(pos)) {
			for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(pos).grow(6.0D))) {
				TFAdvancements.ACTIVATED_GHAST_TRAP.trigger(player);
			}

			changeToBlockState(world, pos, state.withProperty(VARIANT, TowerDeviceVariant.GHASTTRAP_ACTIVE));
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
			world.scheduleUpdate(pos, this, 4);
		}

		if (variant == TowerDeviceVariant.REACTOR_INACTIVE && isReactorReady(world, pos)) {
			// check if we should fire up the reactor
			changeToBlockState(world, pos, state.withProperty(VARIANT, TowerDeviceVariant.REACTOR_ACTIVE));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {

		if (world.isRemote) return;

		TowerDeviceVariant variant = state.getValue(VARIANT);

		if (variant == TowerDeviceVariant.VANISH_ACTIVE || variant == TowerDeviceVariant.REAPPEARING_ACTIVE) {
			if (variant == TowerDeviceVariant.VANISH_ACTIVE) {
				world.setBlockToAir(pos);
			} else {
				world.setBlockState(pos, TFBlocks.tower_translucent.getDefaultState().withProperty(BlockTFTowerTranslucent.VARIANT, TowerTranslucentVariant.REAPPEARING_INACTIVE));
				world.scheduleUpdate(pos, TFBlocks.tower_translucent, 80);
			}
			world.notifyNeighborsRespectDebug(pos, this, false);
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.3F, 0.5F);
			//world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);

			// activate all adjacent inactive vanish blocks
			for (EnumFacing e : EnumFacing.VALUES) {
				checkAndActivateVanishBlock(world, pos.offset(e));
			}
		}

		if (variant == TowerDeviceVariant.BUILDER_ACTIVE && world.isBlockPowered(pos)) {
			this.letsBuild(world, pos);
		}

		if (variant == TowerDeviceVariant.BUILDER_INACTIVE || variant == TowerDeviceVariant.BUILDER_TIMEOUT) {
			// activate all adjacent inactive vanish blocks
			for (EnumFacing e : EnumFacing.VALUES) {
				checkAndActivateVanishBlock(world, pos.offset(e));
			}
		}
	}

	/**
	 * Start the builder block tileentity building!
	 */
	private void letsBuild(World world, BlockPos pos) {
		TileEntityTFTowerBuilder tileEntity = (TileEntityTFTowerBuilder) world.getTileEntity(pos);

		if (tileEntity != null && !tileEntity.makingBlocks) {
			tileEntity.startBuilding();
		}
	}

	/**
	 * Check if the inactive trap block is fully charged
	 */
	private boolean isInactiveTrapCharged(World world, BlockPos pos) {
		TileEntityTFGhastTrapInactive tileEntity = (TileEntityTFGhastTrapInactive) world.getTileEntity(pos);
		return tileEntity != null && tileEntity.isCharged();
	}

	/**
	 * Check if the reactor has all the specified things around it
	 */
	private boolean isReactorReady(World world, BlockPos pos) {
		return Arrays.stream(EnumFacing.VALUES)
				.allMatch(e -> world.getBlockState(pos.offset(e)).getBlock() == Blocks.REDSTONE_BLOCK);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		TowerDeviceVariant variant = state.getValue(VARIANT);

		if (variant == TowerDeviceVariant.VANISH_ACTIVE || variant == TowerDeviceVariant.REAPPEARING_ACTIVE || variant == TowerDeviceVariant.BUILDER_ACTIVE) {
			this.sparkle(world, pos);
		}
	}

	// [VanillaCopy] BlockRedstoneOre.spawnParticles. Unchanged.
	public void sparkle(World worldIn, BlockPos pos) {
		Random random = worldIn.rand;
		double d0 = 0.0625D;

		for (int i = 0; i < 6; ++i) {
			double d1 = (double) ((float) pos.getX() + random.nextFloat());
			double d2 = (double) ((float) pos.getY() + random.nextFloat());
			double d3 = (double) ((float) pos.getZ() + random.nextFloat());

			if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
				d2 = (double) pos.getY() + d0 + 1.0D;
			}

			if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube()) {
				d2 = (double) pos.getY() - d0;
			}

			if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube()) {
				d3 = (double) pos.getZ() + d0 + 1.0D;
			}

			if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube()) {
				d3 = (double) pos.getZ() - d0;
			}

			if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube()) {
				d1 = (double) pos.getX() + d0 + 1.0D;
			}

			if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube()) {
				d1 = (double) pos.getX() - d0;
			}

			if (d1 < (double) pos.getX() || d1 > (double) (pos.getX() + 1) || d2 < 0.0D || d2 > (double) (pos.getY() + 1) || d3 < (double) pos.getZ() || d3 > (double) (pos.getZ() + 1)) {
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	/**
	 * If the targeted block is a vanishing block, activate it
	 */
	public static void checkAndActivateVanishBlock(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block == TFBlocks.tower_device && (state.getValue(VARIANT) == TowerDeviceVariant.VANISH_INACTIVE || state.getValue(VARIANT) == TowerDeviceVariant.VANISH_UNLOCKED) && !areBlocksLocked(world, pos)) {
			changeToActiveVanishBlock(world, pos, TowerDeviceVariant.VANISH_ACTIVE);
		} else if (block == TFBlocks.tower_device && state.getValue(VARIANT) == TowerDeviceVariant.REAPPEARING_INACTIVE && !areBlocksLocked(world, pos)) {
			changeToActiveVanishBlock(world, pos, TowerDeviceVariant.REAPPEARING_ACTIVE);
		} else if (block == TFBlocks.tower_translucent && state.getValue(BlockTFTowerTranslucent.VARIANT) == TowerTranslucentVariant.BUILT_INACTIVE) {
			changeToActiveVanishBlock(world, pos, TowerTranslucentVariant.BUILT_ACTIVE);
		}
	}

	public static void changeToActiveVanishBlock(World world, BlockPos pos, TowerDeviceVariant variant) {
		changeToActiveVanishBlock(world, pos, TFBlocks.tower_device.getDefaultState().withProperty(VARIANT, variant));
	}

	public static void changeToActiveVanishBlock(World world, BlockPos pos, TowerTranslucentVariant variant) {
		changeToActiveVanishBlock(world, pos, TFBlocks.tower_translucent.getDefaultState().withProperty(BlockTFTowerTranslucent.VARIANT, variant));
	}

	/**
	 * Change this block into an active vanishing block
	 */
	private static void changeToActiveVanishBlock(World world, BlockPos pos, IBlockState state) {
		changeToBlockState(world, pos, state);
		world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.3F, 0.6F);
		world.scheduleUpdate(pos, state.getBlock(), getTickRateFor(state, world.rand));
	}

	/**
	 * We need variable, metadata-based tick rates
	 */
	private static int getTickRateFor(IBlockState state, Random rand) {
		if (state.getBlock() == TFBlocks.tower_device && (state.getValue(VARIANT) == TowerDeviceVariant.VANISH_ACTIVE || state.getValue(VARIANT) == TowerDeviceVariant.REAPPEARING_ACTIVE)) {
			return 2 + rand.nextInt(5);
		} else if (state.getBlock() == TFBlocks.tower_translucent && state.getValue(BlockTFTowerTranslucent.VARIANT) == TowerTranslucentVariant.BUILT_ACTIVE) {
			return 10;
		}

		return 15;
	}

	@Override
	public int getLightValue(IBlockState state) {
		switch (state.getValue(VARIANT)) {
			case BUILDER_ACTIVE:
			case VANISH_ACTIVE:
			case REAPPEARING_ACTIVE:
				return 4;
			case ANTIBUILDER:
				return 10;
			case GHASTTRAP_ACTIVE:
			case REACTOR_ACTIVE:
				return 15;
			default:
				return 0;
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		switch (state.getValue(VARIANT)) {
			case BUILDER_ACTIVE:
			case ANTIBUILDER:
			case GHASTTRAP_INACTIVE:
			case GHASTTRAP_ACTIVE:
			case REACTOR_ACTIVE:
				return true;
			default:
				return false;
		}
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch (state.getValue(VARIANT)) {
			case BUILDER_ACTIVE:
				return new TileEntityTFTowerBuilder();
			case ANTIBUILDER:
				return new TileEntityTFAntibuilder();
			case GHASTTRAP_INACTIVE:
				return new TileEntityTFGhastTrapInactive();
			case GHASTTRAP_ACTIVE:
				return new TileEntityTFGhastTrapActive();
			case REACTOR_ACTIVE:
				return new TileEntityTFCReactorActive();
			default:
				return null;
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		switch (state.getValue(VARIANT)) {
			case ANTIBUILDER:
				return Items.AIR;
			default:
				return Item.getItemFromBlock(this);
		}
	}

	@Override
	@Deprecated
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		switch (state.getValue(VARIANT)) {
			case REAPPEARING_ACTIVE:
				state = state.withProperty(VARIANT, TowerDeviceVariant.REAPPEARING_INACTIVE);
				break;
			case BUILDER_ACTIVE:
			case BUILDER_TIMEOUT:
				state = state.withProperty(VARIANT, TowerDeviceVariant.BUILDER_INACTIVE);
				break;
			case VANISH_ACTIVE:
				state = state.withProperty(VARIANT, TowerDeviceVariant.VANISH_INACTIVE);
				break;
			case GHASTTRAP_ACTIVE:
				state = state.withProperty(VARIANT, TowerDeviceVariant.GHASTTRAP_INACTIVE);
				break;
			case REACTOR_ACTIVE:
				state = state.withProperty(VARIANT, TowerDeviceVariant.REACTOR_INACTIVE);
				break;
			default:
				break;
		}

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
