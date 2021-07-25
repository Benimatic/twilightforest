package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import java.util.Random;

public class TFGenWebs extends Feature<NoneFeatureConfiguration> {

	public TFGenWebs(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	private static boolean isValidMaterial(Material material) {
		return material == Material.LEAVES || material == Material.WOOD;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random random, BlockPos pos, NoneFeatureConfiguration config) {
		while (pos.getY() > generator.getSpawnHeight() && world.isEmptyBlock(pos))
			pos = pos.below();

		if (!isValidMaterial(world.getBlockState(pos).getMaterial()))
			return false;

		do {
			if (world.isEmptyBlock(pos.below())) {
				world.setBlock(pos.below(), Blocks.COBWEB.defaultBlockState(), 16 | 2);
				return true;
			}
			pos = pos.below();
		} while (pos.getY() > generator.getSpawnHeight() && isValidMaterial(world.getBlockState(pos).getMaterial()));

		return false;
	}
}
