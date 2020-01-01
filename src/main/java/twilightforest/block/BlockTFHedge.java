package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import twilightforest.util.EntityUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockTFHedge extends Block {

	private static final VoxelShape HEDGE_BB = VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 0.9375, 1));

	private final int damageDone;

	protected BlockTFHedge() {
		super(Properties.create(Material.CACTUS).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.PLANT));
		this.damageDone = 3;
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	@Deprecated
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction side) {
		return world.getBlockState(pos.offset(side)).getBlock() != this && shouldSideBeRendered(state, world, pos, side);
	}

	@Override
	@Deprecated
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return HEDGE_BB;
	}

//	@Override
//	@Deprecated
//	public boolean isOpaqueCube(BlockState state) {
//		return true;
//	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		return entity != null && shouldDamage(entity) ? PathNodeType.DAMAGE_CACTUS : null;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		if (shouldDamage(entity)) {
			entity.attackEntityFrom(DamageSource.CACTUS, damageDone);
		}
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		if (shouldDamage(entity)) {
			entity.attackEntityFrom(DamageSource.CACTUS, damageDone);
		}
	}

//	@Override
//	public void onBlockClicked(World world, BlockPos pos, PlayerEntity player) {
//		if (!world.isRemote && world.getBlockState(pos).getValue(VARIANT) == HedgeVariant.HEDGE) {
//			world.scheduleUpdate(pos, this, 10);
//		}
//	}

	@Override
	public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		player.attackEntityFrom(DamageSource.CACTUS, damageDone);
	}

	@Override
	@Deprecated
	public void tick(BlockState state, World world, BlockPos pos, Random random) {
		// find players within range
		List<PlayerEntity> nearbyPlayers = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).grow(8.0));

		for (PlayerEntity player : nearbyPlayers) {
			// are they swinging?
			if (player.isSwingInProgress) {
				RayTraceResult ray = EntityUtil.rayTrace(player);
				// are they pointing at this block?
				if (ray != null && ray.getType() == RayTraceResult.Type.BLOCK && pos.equals(((BlockRayTraceResult) ray).getPos())) {
					// prick them!  prick them hard!
					player.attackEntityFrom(DamageSource.CACTUS, damageDone);

					// trigger this again!
					//TODO: Do we? Or just leave it for this method to do itself?
					//world.scheduleUpdate(pos, this, 10);
				}
			}
		}
	}

	private boolean shouldDamage(Entity entity) {
		return !(entity instanceof SpiderEntity || entity instanceof ItemEntity || entity.doesEntityNotTriggerPressurePlate());
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 0;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 0;
	}

//	@Override
//	public int quantityDropped(Random random) {
//		return random.nextInt(40) == 0 ? 1 : 0;
//	}
//
//	@Override
//	public Item getItemDropped(BlockState state, Random random, int fortune) {
//		if (state.getValue(VARIANT) == HedgeVariant.DARKWOOD_LEAVES) {
//			return Item.getItemFromBlock(TFBlocks.twilight_sapling);
//		} else {
//			return Items.AIR;
//		}
//	}
//
//	@Override
//	public ItemStack getItem(World world, BlockPos pos, BlockState state) {
//		return new ItemStack(this, 1, getMetaFromState(state));
//	}
//
//	@Override
//	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
//		if (state.getValue(VARIANT) == HedgeVariant.DARKWOOD_LEAVES) {
//			Random rand = world instanceof World ? ((World)world).rand : RANDOM;
//			if (rand.nextInt(40) == 0) {
//				Item item = this.getItemDropped(state, rand, fortune);
//				drops.add(new ItemStack(item, 1, this.damageDropped(state)));
//			}
//		}
//	}
}
