package twilightforest.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import twilightforest.TFFeature;
import twilightforest.world.ChunkGeneratorTwilightBase;
import twilightforest.world.TFGenerationSettings;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class OutOfStructurePlacement extends FeatureDecorator<NoneDecoratorConfiguration> {

	public OutOfStructurePlacement(Codec<NoneDecoratorConfiguration> p_i232086_1_) {
		super(p_i232086_1_);
	}

	@Override
	public Stream<BlockPos> getPositions(DecorationContext worldDecoratingHelper, Random random, NoneDecoratorConfiguration noPlacementConfig, BlockPos blockPos) {
		if (worldDecoratingHelper.generator instanceof ChunkGeneratorTwilightBase) {
			Optional<StructureStart<?>> struct = TFGenerationSettings.locateTFStructureInRange(worldDecoratingHelper.level, blockPos, 0);
			if(struct.isPresent()) {
				StructureStart<?> structure = struct.get();
				if(structure.getBoundingBox().isInside(blockPos)) {
					TFFeature nearbyFeature = TFFeature.getFeatureAt(blockPos.getX(), blockPos.getZ(), worldDecoratingHelper.level);

						// TODO: This is terrible but *works* for now.. proper solution is to figure out why the stronghold bounding box is going so high
						if (nearbyFeature == TFFeature.KNIGHT_STRONGHOLD && blockPos.getY() >= 33)
							return Stream.of(blockPos);

						return Stream.empty();
				}
			}
		}
		return Stream.of(blockPos);
	}
}
