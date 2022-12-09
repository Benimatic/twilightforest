package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFConfiguredFeatures;

public class MushgloomBlock extends MushroomBlock {

	private static final VoxelShape MUSHGLOOM_SHAPE = box(2, 0, 2, 14, 8, 14);

	public MushgloomBlock(Properties properties) {
		//FIXME replace the resource key creation once configured features are converted over to keys
		super(properties, ResourceKey.create(Registries.CONFIGURED_FEATURE, TwilightForestMod.prefix("big_mushgloom")));
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return reader.getBlockState(pos.below()).isFaceSturdy(reader, pos, Direction.UP) || reader.getBlockState(pos.below()).is(TFBlocks.UBEROUS_SOIL.get());
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return MUSHGLOOM_SHAPE;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader getter, BlockPos pos, BlockState state, boolean isClientSide) {
		return false;
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.CAVE;
	}
}
