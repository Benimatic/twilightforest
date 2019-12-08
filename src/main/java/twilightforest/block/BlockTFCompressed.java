package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.enums.CompressedVariant;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockTFCompressed extends Block implements ModelRegisterCallback {

	public static final IProperty<CompressedVariant> VARIANT = PropertyEnum.create("variant", CompressedVariant.class);

	public BlockTFCompressed() {
		super(Material.IRON, MaterialColor.IRON);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, CompressedVariant.IRONWOOD));
		this.setCreativeTab(TFItems.creativeTab);
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
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, CompressedVariant.values()[meta]);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (CompressedVariant variation : CompressedVariant.values() ) {
			list.add(new ItemStack(this, 1, variation.ordinal()));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getPackedLightmapCoords(BlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(VARIANT) == CompressedVariant.FIERY ? 15728880 : super.getPackedLightmapCoords(state, source, pos);
	}

	@Override
	public boolean getUseNeighborBrightness(BlockState state) {
		return state.getValue(VARIANT) == CompressedVariant.FIERY || super.getUseNeighborBrightness(state);
	}

	@Override
	public SoundType getSoundType(BlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return state.getValue(VARIANT).soundType;
	}

	@Override
	public Material getMaterial(BlockState state) {
		return state.getValue(VARIANT).material;
	}

	@Override
	public MaterialColor getMaterialColor(BlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(VARIANT).mapColor;
	}

	@Override
	public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, World worldIn, BlockPos pos) {
		// ItemShears#getDestroySpeed is really dumb and doesn't check IShearable so we have to do it this way to try to match the wool break speed with shears
		return state.getValue(VARIANT) == CompressedVariant.ARCTIC_FUR && player.getHeldItemMainhand().getItem() instanceof ItemShears ? 0.2F : super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
	}

	@Override
	public float getBlockHardness(BlockState blockState, World worldIn, BlockPos pos) {
		switch (blockState.getValue(VARIANT)) {
			default:
			case FIERY:
			case IRONWOOD:
			case STEELLEAF:
				return super.getBlockHardness(blockState, worldIn, pos);
			case ARCTIC_FUR:
				return 0.8F;
			case CARMINITE:
				return 0.0F;
		}
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return true;
	}

	@Override
	public int damageDropped(BlockState state) {
		return getMetaFromState(state);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		List<CompressedVariant> variants = new ArrayList<>(VARIANT.getAllowedValues());

		for (int i = 0; i < variants.size(); i++)
			if (i != CompressedVariant.FIERY.ordinal())
				ModelUtils.registerToState(this, i, this.getDefaultState().withProperty(VARIANT, variants.get(i)));

		ModelResourceLocation mrl = new ModelResourceLocation(this.getRegistryName(), "inventory_fiery");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), CompressedVariant.FIERY.ordinal(), mrl);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if ((!entityIn.isImmuneToFire())
				&& entityIn instanceof EntityLivingBase
				&& (!EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entityIn))
				&& worldIn.getBlockState(pos).getValue(VARIANT) == CompressedVariant.FIERY) {
			entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
		}

		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		switch(worldIn.getBlockState(pos).getValue(VARIANT)) {
			case STEELLEAF:
				entityIn.fall(fallDistance, 0.75F);
				break;
			case ARCTIC_FUR:
				entityIn.fall(fallDistance, 0.1F);
				break;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, Direction face) {
		return CompressedVariant.FIERY == world.getBlockState(pos).getValue(VARIANT);
	}

	@Override
	public boolean isStickyBlock(BlockState state) {
		return state.getValue(VARIANT) == CompressedVariant.CARMINITE;
	}

	@Override
	@Deprecated
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(VARIANT) == CompressedVariant.FIERY) {
			BlockState state = blockAccess.getBlockState(pos.offset(side));
			return state.getBlock() != this || state.getValue(VARIANT) != CompressedVariant.FIERY;
		} else return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
}
