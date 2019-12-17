package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFKnightmetalBlock extends Block {

	private static final AxisAlignedBB AABB = new AxisAlignedBB(1 / 16F, 1 / 16F, 1 / 16F, 15 / 16F, 15 / 16F, 15 / 16F);
	private static final float BLOCK_DAMAGE = 4;

	public BlockTFKnightmetalBlock() {
		super(Properties.create(Material.IRON).hardnessAndResistance(5.0F, 41.0F).sound(SoundType.METAL));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockAccess world, BlockPos pos) {
		return PathNodeType.DAMAGE_CACTUS;
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
		entity.attackEntityFrom(DamageSource.CACTUS, BLOCK_DAMAGE);
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	@Deprecated
	public boolean shouldSideBeRendered(BlockState state, IBlockAccess access, BlockPos pos, Direction side) {
		return !access.getBlockState(pos.offset(side)).doesSideBlockRendering(access, pos.offset(side), side.getOpposite());
	}
}
