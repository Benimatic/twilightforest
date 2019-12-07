package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;
import twilightforest.util.EntityUtil;

import java.util.Random;

public class BlockTFShield extends Block implements ModelRegisterCallback {

	public BlockTFShield() {
		super(Material.ROCK);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(BlockDirectional.FACING, Direction.DOWN));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockDirectional.FACING);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(BlockDirectional.FACING).getIndex();
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockDirectional.FACING, Direction.byIndex(meta));
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	@Deprecated
	public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(BlockDirectional.FACING, Direction.getDirectionFromEntityLiving(pos, placer));
	}

	@Override
	@Deprecated
	public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, World world, BlockPos pos) {
		// why can't we just pass the side to this method?  This is annoying and failure-prone
		RayTraceResult ray = EntityUtil.rayTrace(player, range -> range + 1.0);

		Direction hitFace = ray != null ? ray.sideHit : null;
		Direction blockFace = state.getValue(BlockDirectional.FACING);

		if (hitFace == blockFace) {
			return player.getDigSpeed(Blocks.STONE.getDefaultState(), pos) / 1.5F / 100F;
		} else {
			return super.getPlayerRelativeBlockHardness(state, player, world, pos);
		}
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		return false;
	}

	@Override
	public int damageDropped(BlockState state) {
		return 0;
	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return false;
	}
}
