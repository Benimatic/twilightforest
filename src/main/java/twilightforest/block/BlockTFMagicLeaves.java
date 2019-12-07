package twilightforest.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.client.particle.TFParticleType;
import twilightforest.item.TFItems;

import java.util.List;
import java.util.Random;

public class BlockTFMagicLeaves extends BlockLeaves implements ModelRegisterCallback {

	protected BlockTFMagicLeaves() {
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true)
				.withProperty(BlockTFMagicLog.VARIANT, MagicWoodVariant.TIME));
	}

	@Override
	public int getLightOpacity(BlockState state) {
		return TFConfig.performance.leavesLightOpacity;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockTFMagicLog.VARIANT, CHECK_DECAY, DECAYABLE);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		int i = 0;
		i |= state.getValue(BlockTFMagicLog.VARIANT).ordinal();

		if (!state.getValue(DECAYABLE)) {
			i |= 4;
		}

		if (state.getValue(CHECK_DECAY)) {
			i |= 8;
		}

		return i;
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		int variant = meta & 3;
		final MagicWoodVariant[] values = MagicWoodVariant.values();

		return getDefaultState()
				.withProperty(BlockTFMagicLog.VARIANT, values[variant % values.length])
				.withProperty(DECAYABLE, (meta & 4) == 0)
				.withProperty(CHECK_DECAY, (meta & 8) > 0);
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.getValue(BlockTFMagicLog.VARIANT) == MagicWoodVariant.TRANS) {
			for (int i = 0; i < 1; ++i) {
				this.sparkleRunes(world, pos, random);
			}
		}
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return BlockPlanks.EnumType.OAK;
	}

	private void sparkleRunes(World world, BlockPos pos, Random rand) {
		double offset = 0.0625D;

		Direction side = Direction.random(rand);
		double rx = pos.getX() + rand.nextFloat();
		double ry = pos.getY() + rand.nextFloat();
		double rz = pos.getZ() + rand.nextFloat();

		if (side == Direction.DOWN && world.isAirBlock(pos.up())) {
			ry = pos.getY() + 1 + offset;
		}

		if (side == Direction.UP && world.isAirBlock(pos.down())) {
			ry = pos.getY() + 0 - offset;
		}

		if (side == Direction.NORTH && world.isAirBlock(pos.south())) {
			rz = pos.getZ() + 1 + offset;
		}

		if (side == Direction.SOUTH && world.isAirBlock(pos.north())) {
			rz = pos.getZ() + 0 - offset;
		}

		if (side == Direction.WEST && world.isAirBlock(pos.east())) {
			rx = pos.getX() + 1 + offset;
		}

		if (side == Direction.EAST && world.isAirBlock(pos.west())) {
			rx = pos.getX() + 0 - offset;
		}

		if (rx < pos.getX() || rx > pos.getX() + 1 || ry < pos.getY() || ry > pos.getY() + 1 || rz < pos.getZ() || rz > pos.getZ() + 1) {
			TwilightForestMod.proxy.spawnParticle(TFParticleType.LEAF_RUNE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(BlockTFMagicLog.VARIANT).ordinal()));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		IStateMapper stateMapper = new StateMap.Builder().ignore(CHECK_DECAY, DECAYABLE).build();
		ModelLoader.setCustomStateMapper(this, stateMapper);
		ModelUtils.registerToStateSingleVariant(this, BlockTFMagicLog.VARIANT, stateMapper);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public int damageDropped(BlockState state) {
		return state.getValue(BlockTFMagicLog.VARIANT).ordinal();
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {}
}
