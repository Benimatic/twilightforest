package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTFBurntThorns extends BlockTFThorns {

	protected BlockTFBurntThorns() {
		super(Properties.create(Material.WOOD, MaterialColor.STONE).hardnessAndResistance(0.01F, 0.0F).sound(SoundType.SAND));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		return null;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		// dissolve
		if (!world.isRemote && entity instanceof LivingEntity) {
			world.destroyBlock(pos, false);
		}
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
		world.removeBlock(pos, false);
		return true;
	}

//	@Override
//	public boolean canSustainLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
//		return false;
//	}
//
//	@Override
//	public void breakBlock(World world, BlockPos pos, BlockState state) {}
}
