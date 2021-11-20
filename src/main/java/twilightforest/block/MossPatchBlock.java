package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class MossPatchBlock extends PatchBlock {

	public MossPatchBlock(Properties props) {
		super(props);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return world.getBlockState(pos.below()).isFaceSturdy(world, pos, Direction.UP);
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.CAVE;
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		if (random.nextInt(10) == 0) {
			world.addParticle(ParticleTypes.MYCELIUM, pos.getX() + random.nextFloat(), pos.getY() + 0.1F, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}
}
