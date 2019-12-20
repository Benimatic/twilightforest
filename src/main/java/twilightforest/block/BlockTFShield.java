package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;
import twilightforest.util.EntityUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTFShield extends Block {

	public BlockTFShield() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 6000000.0F).sound(SoundType.METAL));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		this.setDefaultState(stateContainer.getBaseState().with(DirectionalBlock.FACING, Direction.DOWN));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockDirectional.FACING);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DirectionalBlock.FACING);
	}

//	@Override
//	public int quantityDropped(Random random) {
//		return 0;
//	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(DirectionalBlock.FACING, Direction.getDirectionFromEntityLiving(pos, placer));
	}

	@Override
	@Deprecated
	public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader world, BlockPos pos) {
		// why can't we just pass the side to this method?  This is annoying and failure-prone
		RayTraceResult ray = EntityUtil.rayTrace(player, range -> range + 1.0);

		Direction hitFace = ray != null ? ray.sideHit : null;
		Direction blockFace = state.get(DirectionalBlock.FACING);

		if (hitFace == blockFace) {
			return player.getDigSpeed(Blocks.STONE.getDefaultState(), pos) / 1.5F / 100F;
		} else {
			return super.getPlayerRelativeBlockHardness(state, player, world, pos);
		}
	}

//	@Override
//	protected boolean canSilkHarvest() {
//		return false;
//	}
//
//	@Override
//	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
//		return false;
//	}
//
//	@Override
//	public int damageDropped(BlockState state) {
//		return 0;
//	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
		return false;
	}
}
