package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.state.BooleanProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import java.util.Random;

public class BlockTFTrollSteinn extends Block {

	static final BooleanProperty DOWN_LIT  = BooleanProperty.create("down");
	static final BooleanProperty UP_LIT    = BooleanProperty.create("up");
	static final BooleanProperty NORTH_LIT = BooleanProperty.create("north");
	static final BooleanProperty SOUTH_LIT = BooleanProperty.create("south");
	static final BooleanProperty WEST_LIT  = BooleanProperty.create("west");
	static final BooleanProperty EAST_LIT  = BooleanProperty.create("east");

	private static final int LIGHT_THRESHHOLD = 7;

	BlockTFTrollSteinn() {
		super(Material.ROCK);

		this.setHardness(2F);
		this.setResistance(15F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState()
				.with(DOWN_LIT, false).with(UP_LIT, false)
				.with(NORTH_LIT, false).with(SOUTH_LIT, false)
				.with(WEST_LIT, false).with(EAST_LIT, false));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DOWN_LIT, UP_LIT, NORTH_LIT, SOUTH_LIT, WEST_LIT, EAST_LIT);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return 0;
	}

	@Override
	@Deprecated
	public BlockState getActualState(BlockState state, IBlockAccess world, BlockPos pos) {
		if (!(world instanceof World)) return this.getDefaultState();

		for (SideProps side : SideProps.values())
			state = state.with(side.prop, ((World) world).getLight(pos.offset(side.facing)) > LIGHT_THRESHHOLD);

		return state;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextInt(2) == 0) this.sparkle(world, pos);
	}

	// [VanillaCopy] Based on BlockRedstoneOre.spawnParticles
	private void sparkle(World world, BlockPos pos) {
		Random random = world.rand;
		int threshhold = LIGHT_THRESHHOLD;

		for (Direction side : Direction.values()) {
			double rx = (double) ((float) pos.getX() + random.nextFloat());
			double ry = (double) ((float) pos.getY() + random.nextFloat());
			double rz = (double) ((float) pos.getZ() + random.nextFloat());

			if (side == Direction.DOWN && !world.getBlockState(pos.down()).isOpaqueCube() && world.getLight(pos.down()) <= threshhold) {
				ry = (double)pos.getY() - 0.0625D;
			}

			if (side == Direction.UP && !world.getBlockState(pos.up()).isOpaqueCube() && world.getLight(pos.up()) <= threshhold) {
				ry = (double)pos.getY() + 0.0625D + 1.0D;
			}

			if (side == Direction.NORTH && !world.getBlockState(pos.north()).isOpaqueCube() && world.getLight(pos.north()) <= threshhold) {
				rz = (double)pos.getZ() - 0.0625D;
			}

			if (side == Direction.SOUTH && !world.getBlockState(pos.south()).isOpaqueCube() && world.getLight(pos.south()) <= threshhold) {
				rz = (double)pos.getZ() + 0.0625D + 1.0D;
			}

			if (side == Direction.WEST && !world.getBlockState(pos.west()).isOpaqueCube() && world.getLight(pos.west()) <= threshhold) {
				rx = (double)pos.getX() - 0.0625D;
			}

			if (side == Direction.EAST && !world.getBlockState(pos.east()).isOpaqueCube() && world.getLight(pos.east()) <= threshhold) {
				rx = (double)pos.getX() + 0.0625D + 1.0D;
			}

			if (rx < (double) pos.getX() || rx > (double) (pos.getX() + 1) || ry < 0.0D || ry > (double) (pos.getY() + 1) || rz < (double) pos.getZ() || rz > (double) (pos.getZ() + 1)) {
				world.addParticle(ParticleTypes.REDSTONE, rx, ry, rz, 0.25D, -1.0D, 0.5D);
			}
		}
	}

	private enum SideProps {
		UP(UP_LIT, Direction.UP),
		DOWN(DOWN_LIT, Direction.DOWN),
		NORTH(NORTH_LIT, Direction.NORTH),
		SOUTH(SOUTH_LIT, Direction.SOUTH),
		WEST(WEST_LIT, Direction.WEST),
		EAST(EAST_LIT, Direction.EAST);

		private final BooleanProperty prop;
		private final Direction facing;

		SideProps(BooleanProperty prop, Direction faceing) {
			this.prop = prop;
			this.facing = faceing;
		}
	}
}
