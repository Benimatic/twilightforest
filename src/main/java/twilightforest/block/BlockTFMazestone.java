package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.MazestoneVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.ItemTFMazebreakerPick;
import twilightforest.item.TFItems;

/**
 * Mazestone mimics other types of stone in appearance, but is much harder to mine
 *
 * @author Ben
 */
public class BlockTFMazestone extends Block implements ModelRegisterCallback {

	public static final IProperty<MazestoneVariant> VARIANT = PropertyEnum.create("variant", MazestoneVariant.class);

	public BlockTFMazestone() {
		super(Material.ROCK);
		this.setHardness(100F);
		this.setResistance(5F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, MazestoneVariant.PLAIN));
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
		return getDefaultState().withProperty(VARIANT, MazestoneVariant.values()[meta]);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(world, pos, state, player);
		ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

		// damage the player's pickaxe
		if (!world.isRemote && !stack.isEmpty() && stack.getItem().isDamageable() && !(stack.getItem() instanceof ItemTFMazebreakerPick)) {
			stack.damageItem(16, player);
		}
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		for (MazestoneVariant variant : MazestoneVariant.values()) {
			list.add(new ItemStack(this, 1, variant.ordinal()));
		}
	}

	@Override
	public int damageDropped(BlockState state) {
		return getMetaFromState(state);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelUtils.registerToStateSingleVariant(this, VARIANT);
	}

}
