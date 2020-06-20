package twilightforest.structures;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFBossSpawner;
import twilightforest.block.TFBlocks;
import twilightforest.enums.BossVariant;

import java.util.List;
import java.util.Random;

public class ComponentTFHydraLair extends ComponentTFHollowHill {

	public ComponentTFHydraLair(TemplateManager manager, CompoundNBT nbt) {
		super(TFFeature.TFHydra, nbt);
	}

	public ComponentTFHydraLair(TFFeature feature, Random rand, int i, int x, int y, int z) {
		super(TFFeature.TFHydra, feature, rand, i, 2, x, y + 2, z);
	}

	@Override
	public void buildComponent(StructurePiece structurecomponent, List<StructurePiece> list, Random random) {
		;
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		int stalacts = 64;
		int stalags = 8;

		// fill in features
		// ore or glowing stalactites! (smaller, less plentiful)
		for (int i = 0; i < stalacts; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateOreStalactite(world.getWorld(), dest[0], 1, dest[1], sbb);
		}
		// stone stalactites!
		for (int i = 0; i < stalacts; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world.getWorld(), Blocks.STONE, 1.0F, true, dest[0], 1, dest[1], sbb);
		}
		// stone stalagmites!
		for (int i = 0; i < stalags; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world.getWorld(), Blocks.STONE, 0.9F, false, dest[0], 1, dest[1], sbb);
		}

		// boss spawner seems important
		setBlockState(world, TFBlocks.boss_spawner.get().getDefaultState().with(BlockTFBossSpawner.VARIANT, BossVariant.HYDRA), 27, 3, 27, sbb);

		return true;
	}
}
