package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import twilightforest.entity.TFEntities;
import twilightforest.entity.passive.EntityTFPenguin;

import java.util.Random;
import java.util.function.Function;

public class TFGenPenguins extends Feature<NoFeatureConfig> {

	public TFGenPenguins(Function<Dynamic<?>, NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {

		for (int i = 0; i < 10; i++) {
			BlockPos dPos = pos.add(
					rand.nextInt(8) - rand.nextInt(8),
					0,
					rand.nextInt(8) - rand.nextInt(8)
			);
			dPos = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, dPos); //TODO: Verify

			if (dPos.getY() > 0) {
				EntityTFPenguin penguin = new EntityTFPenguin(TFEntities.penguin.get(), world.getWorld());
				penguin.moveToBlockPosAndAngles(dPos, rand.nextFloat() * 360.0F, 0.0F);

				world.addEntity(penguin);
			}
		}

		return true;
	}
}
