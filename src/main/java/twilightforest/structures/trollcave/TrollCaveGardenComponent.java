package twilightforest.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class TrollCaveGardenComponent extends TrollCaveMainComponent {
	// FIXME These should probably reference stuff from the datapack
	//private final ConfiguredFeature<?,?> myceliumBlobGen = TFBiomeFeatures.MYCELIUM_BLOB.get().configured(new DiskConfiguration(BlockConstants.MYCELIUM, UniformInt.of(5, 2), 0, ImmutableList.of(BlockConstants.STONE, BlockConstants.DEADROCK))).decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(15, 10)));
	//private final ConfiguredFeature<?,?> dirtGen = TFBiomeFeatures.MYCELIUM_BLOB.get().configured(new DiskConfiguration(BlockConstants.DIRT, UniformInt.of(5, 2), 0, ImmutableList.of(BlockConstants.STONE, BlockConstants.DEADROCK))).decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(15, 10)));
	//private final ConfiguredFeature<?,?> smallUberousGen = TFBiomeFeatures.MYCELIUM_BLOB.get().configured(new DiskConfiguration(BlockConstants.UBEROUS_SOIL, UniformInt.of(4, 3), 0, ImmutableList.of(BlockConstants.PODZOL, BlockConstants.COARSE_DIRT, BlockConstants.DIRT))).decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(60, 10)));
	//private final ConfiguredFeature<?,?> bigRedMushroomGen = Features.HUGE_RED_MUSHROOM.decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(15, 10)));
	//private final ConfiguredFeature<?,?> bigBrownMushroomGen = Features.HUGE_BROWN_MUSHROOM.decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(15, 10)));
	//private final ConfiguredFeature<?,?> bigMushgloomGen = TFBiomeFeatures.BIG_MUSHGLOOM.get().configured(FeatureConfiguration.NONE).decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(15, 10)));

	public TrollCaveGardenComponent(ServerLevel level, CompoundTag nbt) {
		super(TrollCavePieces.TFTCGard, nbt);
	}

	public TrollCaveGardenComponent(TFFeature feature, int index, int x, int y, int z, int caveSize, int caveHeight, Direction direction) {
		super(TrollCavePieces.TFTCGard, feature, index, x, y, z);
		this.size = caveSize;
		this.height = caveHeight;
		this.setOrientation(direction);
		this.boundingBox = feature.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, size - 1, direction);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		// add a cloud
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
//		if (this.isBoundingBoxOutsideBiomes(world, sbb, highlands)) {
//			return false;
//		}

		// clear inside
		hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX() * 321534781L) ^ (this.boundingBox.minZ() * 756839L));

		// treasure!
		makeTreasureCrate(world, sbb);

		/*// dirt!
		for (int i = 0; i < 24; i++) {
			BlockPos dest = getCenterBiasedCaveCoords(decoRNG);
			generate(world, generator, dirtGen, decoRNG, dest.getX() * 4, 1, dest.getZ() * 4, sbb);
		}

		// mycelium!
		for (int i = 0; i < 16; i++) {
			BlockPos dest = getCenterBiasedCaveCoords(decoRNG);
			generate(world, generator, myceliumBlobGen, decoRNG, dest.getX() * 4, 1, dest.getZ() * 4, sbb);
		}

		// uberous!
		for (int i = 0; i < 16; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generate(world, generator, smallUberousGen, decoRNG, dest.getX() * 4, 1, dest.getZ() * 4, sbb);

			generateAtSurface(world, generator, smallUberousGen, decoRNG, dest.getX(), 60, dest.getZ(), sbb);
		}

		// mushglooms first
		for (int i = 0; i < 32; i++) {
			BlockPos dest = getCenterBiasedCaveCoords(decoRNG);
			generate(world, generator, bigMushgloomGen, decoRNG, dest.getX() * 4, 1, dest.getZ() * 4, sbb);
		}

		// mushrooms!
		for (int i = 0; i < 64; i++) {
			BlockPos dest = getCenterBiasedCaveCoords(decoRNG);
			generate(world, generator, rand.nextBoolean() ? bigBrownMushroomGen : bigRedMushroomGen, decoRNG, dest.getX() * 4, 1, dest.getZ() * 4, sbb);
		}*/

		// stone stalactites!
		for (int i = 0; i < 128; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generateBlockStalactite(world, generator, decoRNG, Blocks.STONE, 0.7F, true, dest.getX(), 3, dest.getZ(), sbb);
		}

		return true;
	}

	protected void generate(WorldGenLevel world, ChunkGenerator generator, ConfiguredFeature<?,?> feature, Random rand, int x, int y, int z, BoundingBox sbb) {
		// are the coordinates in our bounding box?
		int dx = getWorldX(x, z);
		int dy = getWorldY(y);
		int dz = getWorldZ(x, z);

		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isInside(pos)) {
			feature.place(world, generator, rand, pos);
		}
	}
}
