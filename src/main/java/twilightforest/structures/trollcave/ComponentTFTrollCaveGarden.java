package twilightforest.structures.trollcave;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.world.feature.TFBiomeFeatures;

import java.util.List;
import java.util.Random;

public class ComponentTFTrollCaveGarden extends ComponentTFTrollCaveMain {

	private ConfiguredFeature<?,?> myceliumBlobGen = TFBiomeFeatures.MYCELIUM_BLOB.get().configure(new SphereReplaceConfig(Blocks.MYCELIUM.getDefaultState(), 5, 1, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState())));
	private ConfiguredFeature<?,?> dirtGen = TFBiomeFeatures.MYCELIUM_BLOB.get().configure(new SphereReplaceConfig(Blocks.DIRT.getDefaultState(), 5, 1, Lists.newArrayList(Blocks.GRASS_BLOCK.getDefaultState())));
	private ConfiguredFeature<?,?> bigRedMushroomGen = Feature.HUGE_RED_MUSHROOM.configure(DefaultBiomeFeatures.HUGE_RED_MUSHROOM_CONFIG);
	private ConfiguredFeature<?,?> bigBrownMushroomGen = Feature.HUGE_BROWN_MUSHROOM.configure(DefaultBiomeFeatures.HUGE_BROWN_MUSHROOM_CONFIG);
	private ConfiguredFeature<?,?> bigMushgloomGen = TFBiomeFeatures.BIG_MUSHGLOOM.get().configure(IFeatureConfig.NO_FEATURE_CONFIG);

	public ComponentTFTrollCaveGarden(TemplateManager manager, CompoundNBT nbt) {
		super(TFTrollCavePieces.TFTCGard, nbt);
	}

	public ComponentTFTrollCaveGarden(TFFeature feature, int index, int x, int y, int z, int caveSize, int caveHeight, Direction direction) {
		super(TFTrollCavePieces.TFTCGard, feature, index);
		this.size = caveSize;
		this.height = caveHeight;
		this.setCoordBaseMode(direction);
		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, size - 1, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		// add a cloud
//		ComponentTFTrollCloud cloud = new ComponentTFTrollCloud(1, boundingBox.minX + ((boundingBox.maxX - boundingBox.minX) / 2), rand.nextInt(64) + 160, boundingBox.minZ + ((boundingBox.maxZ - boundingBox.minZ) / 2));
//		list.add(cloud);
//		cloud.buildComponent(this, list, rand);
	}

	@Override
	public boolean generate(IWorld worldIn, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World world = worldIn.getWorld();

		if (this.isBoundingBoxOutsideBiomes(world, sbb, highlands)) {
			return false;
		}

		// clear inside
		hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		// treasure!
		makeTreasureCrate(world, sbb);

		// dirt!
		for (int i = 0; i < 24; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generate(world, dirtGen, decoRNG, dest.getX(), 1, dest.getZ(), sbb);
		}

		// mycelium!
		for (int i = 0; i < 16; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generate(world, myceliumBlobGen, decoRNG, dest.getX(), 1, dest.getZ(), sbb);
		}

		// uberous!
		for (int i = 0; i < 16; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generate(world, uberousGen, decoRNG, dest.getX(), 1, dest.getZ(), sbb);

			generateAtSurface(world, uberousGen, decoRNG, dest.getX(), 60, dest.getZ(), sbb);
		}

		// mushglooms first
		for (int i = 0; i < 32; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generate(world, bigMushgloomGen, decoRNG, dest.getX(), 1, dest.getZ(), sbb);
		}

		// mushrooms!
		for (int i = 0; i < 64; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generate(world, rand.nextBoolean() ? bigBrownMushroomGen : bigRedMushroomGen, decoRNG, dest.getX(), 1, dest.getZ(), sbb);
		}

		// stone stalactites!
		for (int i = 0; i < 128; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generateBlockStalactite(world, decoRNG, Blocks.STONE, 0.7F, true, dest.getX(), 3, dest.getZ(), sbb);
		}

		return true;
	}

	protected void generate(World world, ConfiguredFeature<?,?> generator, Random rand, int x, int y, int z, MutableBoundingBox sbb) {
		// are the coordinates in our bounding box?
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);

		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isVecInside(pos)) {
			generator.place(world, ((ServerWorld) world).getChunkProvider().getChunkGenerator(), rand, pos);
		}
	}
}
