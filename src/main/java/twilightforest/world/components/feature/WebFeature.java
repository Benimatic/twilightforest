package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.init.TFBlocks;

public class WebFeature extends Feature<NoneFeatureConfiguration> {

	public WebFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	private static boolean isValidMaterial(BlockState state) {
		return state.is(BlockTags.LOGS) || state.is(BlockTags.LEAVES);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
		WorldGenLevel world = config.level();
		BlockPos pos = config.origin().above(config.random().nextInt(world.getMaxBuildHeight() - config.origin().getY()));
		while (pos.getY() > config.origin().getY()) {
			pos = pos.below();
			if (world.isEmptyBlock(pos.below()) && isValidMaterial(world.getBlockState(pos))) {
				world.setBlock(pos.below(), Blocks.COBWEB.defaultBlockState(), 16 | 2);
				return true;
			}
		}

		return false;
	}
}
