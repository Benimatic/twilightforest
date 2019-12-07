package twilightforest.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFConfig;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.enums.LeavesVariant;
import twilightforest.item.TFItems;

import java.util.List;
import java.util.Random;

public class BlockTFLeaves extends BlockLeaves implements ModelRegisterCallback {

	public static final IProperty<LeavesVariant> VARIANT = PropertyEnum.create("variant", LeavesVariant.class);

	protected BlockTFLeaves() {
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true).withProperty(VARIANT, LeavesVariant.OAK));
	}

	@Override
	public int getLightOpacity(BlockState state) {
		return TFConfig.performance.leavesLightOpacity;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE, VARIANT);
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		LeavesVariant variant = LeavesVariant.values()[meta & 0b11];
		return this.getDefaultState().withProperty(VARIANT, variant).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		int i = state.getValue(VARIANT).ordinal();

		if (!state.getValue(DECAYABLE)) {
			i |= 4;
		}

		if (state.getValue(CHECK_DECAY)) {
			i |= 8;
		}

		return i;
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return BlockPlanks.EnumType.OAK;
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
	}

	@Override
	public Item getItemDropped(BlockState state, Random random, int fortune) {
		return Item.getItemFromBlock(TFBlocks.twilight_sapling);
	}

	@Override
	public int damageDropped(BlockState state) {
		LeavesVariant leafType = state.getValue(VARIANT);
		return leafType == LeavesVariant.RAINBOAK ? 9 : leafType.ordinal();
	}

	@Override
	public ItemStack getSilkTouchDrop(BlockState state) {
		return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
	}

	@Override
	public int getSaplingDropChance(BlockState state) {
		return state.getValue(VARIANT) == LeavesVariant.MANGROVE ? 20 : 40;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).ordinal()));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		IStateMapper stateMapper = new StateMap.Builder().ignore(CHECK_DECAY, DECAYABLE).build();
		ModelLoader.setCustomStateMapper(this, stateMapper);
		ModelUtils.registerToStateSingleVariant(this, VARIANT, stateMapper);
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, Direction face) {
		return 30;
	}
}
