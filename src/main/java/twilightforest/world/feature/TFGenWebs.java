package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.material.Material;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TFGenWebs<T extends NoFeatureConfig> extends Feature<T> {

	public TFGenWebs(Function<Dynamic<?>, T> config) {
		super(config);
	}

	private static boolean isValidMaterial(Material material) {
		return material == Material.LEAVES || material == Material.WOOD;
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, T config) {
		while (pos.getY() > generator.getSeaLevel() && world.isAirBlock(pos))
			pos = pos.down();

		if (!isValidMaterial(world.getBlockState(pos).getMaterial()))
			return false;

		do {
			if (world.isAirBlock(pos.down())) {
				world.setBlockState(pos.down(), Blocks.COBWEB.getDefaultState(), 16 | 2);
				return true;
			}
			pos = pos.down();
		} while (pos.getY() > generator.getSeaLevel() && isValidMaterial(world.getBlockState(pos).getMaterial()));

		return false;
	}
}
