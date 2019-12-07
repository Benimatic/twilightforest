package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.BossVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTFBossSpawner extends Block implements ModelRegisterCallback {

	public static final IProperty<BossVariant> VARIANT = PropertyEnum.create("boss", BossVariant.class);

	protected BlockTFBossSpawner() {
		super(Material.ROCK);
		this.setHardness(20F);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, BossVariant.NAGA));
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
		return getDefaultState().withProperty(VARIANT, BossVariant.getVariant(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		for (BossVariant variant : BossVariant.values()) {
			if (variant.hasSpawner()) {
				list.add(new ItemStack(this, 1, variant.ordinal()));
			}
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.getValue(VARIANT).hasSpawner();
	}

	@Override
	@Nullable
	public TileEntity createTileEntity(World world, BlockState state) {
		return state.getValue(VARIANT).getSpawner();
	}

	@Override
	public Item getItemDropped(BlockState state, Random random, int fortune) {
		return Items.AIR;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return blockHardness >= 0f;
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		IStateMapper stateMapper = new StateMap.Builder().ignore(VARIANT).build();
		ModelLoader.setCustomStateMapper(this, stateMapper);
		ModelUtils.registerToStateSingleVariant(this, VARIANT, stateMapper);
	}
}
