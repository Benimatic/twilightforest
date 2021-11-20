package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public abstract class TFPlantBlock extends BushBlock implements BonemealableBlock {

	protected TFPlantBlock(BlockBehaviour.Properties props) {
		super(props);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState soil = world.getBlockState(pos.below());
		return (world.getMaxLocalRawBrightness(pos) >= 3 || world.canSeeSkyFromBelowWater(pos)) && soil.canSustainPlant(world, pos.below(), Direction.UP, this);
	}

	public static boolean canPlaceRootAt(LevelReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos.above());
		if (state.getMaterial() == Material.DIRT || state.getMaterial() == Material.GRASS) {
			// can always hang below dirt blocks
			return true;
		} else {
			return (state.getBlock() == TFBlocks.ROOT_STRAND.get()
					|| state.is(TFBlocks.ROOT_BLOCK.get())
					|| state.is(TFBlocks.LIVEROOT_BLOCK.get())
					|| state.is(TFBlocks.MANGROVE_ROOT.get()));
		}
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.PLAINS;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
		return false;
	}

	@Override
	public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) { }

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 100;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 60;
	}
}
