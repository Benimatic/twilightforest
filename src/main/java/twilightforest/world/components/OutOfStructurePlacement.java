package twilightforest.world.components;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import twilightforest.TFFeature;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilightBase;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class OutOfStructurePlacement extends FeatureDecorator<NoneDecoratorConfiguration> {

	public OutOfStructurePlacement(Codec<NoneDecoratorConfiguration> p_i232086_1_) {
		super(p_i232086_1_);
	}

	@Override
	public Stream<BlockPos> getPositions(DecorationContext worldDecoratingHelper, Random random, NoneDecoratorConfiguration noPlacementConfig, BlockPos blockPos) {
		//FIXME used to be worldDecoratingHelper.generator... not sure if this is right but im not getting an error anymore so
		if (worldDecoratingHelper.getLevel() instanceof ChunkGeneratorTwilightBase) {
			Optional<StructureStart<?>> struct = TFGenerationSettings.locateTFStructureInRange(worldDecoratingHelper.level, blockPos, 0);

			return struct.isPresent()
					&& struct.get().getBoundingBox().isInside(blockPos)
					&& TFFeature.getFeatureAt(blockPos.getX(), blockPos.getZ(), worldDecoratingHelper.level).areChunkDecorationsEnabled
					? Stream.of(blockPos) : Stream.empty();
		}
		return Stream.of(blockPos);
	}
}
