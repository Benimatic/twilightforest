package twilightforest.structures.trollcave;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.world.feature.TFGenBigMushgloom;
import twilightforest.world.feature.TFGenMyceliumBlob;

import java.util.List;
import java.util.Random;

public class ComponentTFTrollCaveGarden extends ComponentTFTrollCaveMain {

	private TFGenMyceliumBlob myceliumBlobGen = new TFGenMyceliumBlob(5);
	private TFGenMyceliumBlob dirtGen = new TFGenMyceliumBlob(Blocks.DIRT, 5);
	private WorldGenBigMushroom bigMushroomGen = new WorldGenBigMushroom();
	private TFGenBigMushgloom bigMushgloomGen = new TFGenBigMushgloom();

	public ComponentTFTrollCaveGarden() {}

	public ComponentTFTrollCaveGarden(TFFeature feature, int index, int x, int y, int z, int caveSize, int caveHeight, Direction direction) {
		super(feature, index);
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
	public boolean addComponentParts(IWorld worldIn, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World world = worldIn.getWorld();

		if (this.isBoundingBoxOutsideBiomes(world, sbb, highlands)) {
			return false;
		}

		// clear inside
		hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		// treasure!
		makeTreasureCrate(world, rand, sbb);

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
			generate(world, bigMushroomGen, decoRNG, dest.getX(), 1, dest.getZ(), sbb);
		}

		// stone stalactites!
		for (int i = 0; i < 128; i++) {
			BlockPos dest = getCoordsInCave(decoRNG);
			generateBlockStalactite(world, decoRNG, Blocks.STONE, 0.7F, true, dest.getX(), 3, dest.getZ(), sbb);
		}

		return true;
	}

	protected void generate(World world, WorldGenerator generator, Random rand, int x, int y, int z, MutableBoundingBox sbb) {
		// are the coordinates in our bounding box?
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);

		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isVecInside(pos)) {
			generator.generate(world, rand, pos);
		}
	}
}
