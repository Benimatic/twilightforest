package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTFKnightmetalBlock extends Block {

	private static final VoxelShape AABB = VoxelShapes.create(new AxisAlignedBB(1 / 16F, 1 / 16F, 1 / 16F, 15 / 16F, 15 / 16F, 15 / 16F));
	private static final float BLOCK_DAMAGE = 4;

	public BlockTFKnightmetalBlock() {
		super(Properties.create(Material.IRON).hardnessAndResistance(5.0F, 41.0F).sound(SoundType.METAL));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AABB;
	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		return PathNodeType.DAMAGE_CACTUS;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		entity.attackEntityFrom(DamageSource.CACTUS, BLOCK_DAMAGE);
	}

	//TODO: Check this
//	@Override
//	public boolean isSolid(BlockState state) {
//		return false;
//	}

	//TODO: Removed. Check this
//	@Override
//	@Deprecated
//	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
//		return !world.getBlockState(pos.offset(face)).doesSideBlockRendering(world, pos.offset(face), face.getOpposite());
//	}
}
