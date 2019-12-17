package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import java.util.Random;

public class BlockTFUberousSoil extends Block implements IGrowable {
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);

	protected BlockTFUberousSoil() {
		super(Material.GROUND);
		this.setLightOpacity(255);
		this.setHardness(0.6F);
		this.setSoundType(SoundType.GROUND);
		this.setTickRandomly(true);
		this.useNeighborBrightness = true;

		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
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
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return face == Direction.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

//	@Override
//	public Item getItemDropped(BlockState state, Random rand, int fortune) {
//		return Item.getItemFromBlock(Blocks.DIRT);
//	}

	@Override
	public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
		Material aboveMaterial = world.getBlockState(pos.up()).getMaterial();
		if (aboveMaterial.isSolid()) {
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockAccess world, BlockPos pos, Direction direction, IPlantable plantable) {
		if (direction != Direction.UP)
			return false;
		EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));
		return plantType == EnumPlantType.Crop || plantType == EnumPlantType.Plains || plantType == EnumPlantType.Cave;
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block neighbor, BlockPos fromPos) {
		BlockState above = world.getBlockState(pos.up());
		Material aboveMaterial = above.getMaterial();

		if (aboveMaterial.isSolid()) {
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
		}

		// todo should probably use IGrowable and loop until it can't grow anymore
		if (above.getBlock() instanceof IPlantable) {
			IPlantable plant = (IPlantable) above.getBlock();
			// revert to farmland or grass
			if (plant.getPlantType(world, pos.up()) == EnumPlantType.Crop) {
				world.setBlockState(pos, Blocks.FARMLAND.getDefaultState().with(BlockFarmland.MOISTURE, 2));
			} else if (plant.getPlantType(world, pos.up()) == EnumPlantType.Plains) {
				world.setBlockState(pos, Blocks.GRASS.getDefaultState());
			} else {
				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
			}
			// apply bonemeal
			ItemDye.applyBonemeal(new ItemStack(Items.DYE), world, pos.up());
			ItemDye.applyBonemeal(new ItemStack(Items.DYE), world, pos.up());
			ItemDye.applyBonemeal(new ItemStack(Items.DYE), world, pos.up());
			ItemDye.applyBonemeal(new ItemStack(Items.DYE), world, pos.up());
			// green sparkles
			world.playEvent(2005, pos.up(), 0);
		}
	}

	@Override
	public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, BlockState state) {
		pos = pos.offset(Direction.HORIZONTALS[rand.nextInt(Direction.HORIZONTALS.length)]);

		Block blockAt = world.getBlockState(pos).getBlock();
		if (world.isAirBlock(pos.up()) && (blockAt == Blocks.DIRT || blockAt == Blocks.GRASS || blockAt == Blocks.FARMLAND)) {
			world.setBlockState(pos, this.getDefaultState());
		}
	}
}
