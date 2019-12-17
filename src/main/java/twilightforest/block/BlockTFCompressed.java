package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockTFCompressed extends Block {

	public BlockTFCompressed(Material material, MaterialColor color, float hardness, SoundType sound) {
		super(Properties.create(material, color).hardnessAndResistance(hardness, 10.0F).sound(sound));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	@Deprecated
	@OnlyIn(Dist.CLIENT)
	public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader worldIn, BlockPos pos) {
		if (state.getBlock() == TFBlocks.block_storage_fiery.get()) {
			return 15728880;
		}
		return super.getPackedLightmapCoords(state, worldIn, pos);
	}

//	@Override
//	public boolean getUseNeighborBrightness(BlockState state) {
//		return state.getValue(VARIANT) == CompressedVariant.FIERY || super.getUseNeighborBrightness(state);
//	}

	@Override
	public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
		// ItemShears#getDestroySpeed is really dumb and doesn't check IShearable so we have to do it this way to try to match the wool break speed with shears
		return state.getBlock() == TFBlocks.block_storage_arctic_fur.get() && player.getHeldItemMainhand().getItem() instanceof ShearsItem ? 0.2F : super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
	}

	//TODO 1.14: Move to supports_beacon Tag
	@Override
	public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
		return true;
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if ((!entityIn.isImmuneToFire())
				&& entityIn instanceof LivingEntity
				&& (!EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn))
				&& worldIn.getBlockState(pos) == TFBlocks.block_storage_fiery.get().getDefaultState()) {
			entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
		}

		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (worldIn.getBlockState(pos) == TFBlocks.block_storage_steeleaf.get().getDefaultState()) {
			entityIn.fall(fallDistance, 0.75F);
		} else if (worldIn.getBlockState(pos) == TFBlocks.block_storage_arctic_fur.get().getDefaultState()) {
			entityIn.fall(fallDistance, 0.1F);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFireSource(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
		return state == TFBlocks.block_storage_fiery.get().getDefaultState();
	}

	@Override
	public boolean isStickyBlock(BlockState state) {
		return state == TFBlocks.block_storage_carminite.get().getDefaultState();
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
		if (state.getBlock() == TFBlocks.block_storage_fiery.get()) {
			BlockState blockstate = world.getBlockState(pos.offset(face));
			return blockstate.getBlock() != this || blockstate.getBlock() == TFBlocks.block_storage_fiery.get();
		} else return super.shouldSideBeRendered(state, world, pos, face);
	}
}
