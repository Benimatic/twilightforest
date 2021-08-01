package twilightforest.structures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;
import twilightforest.world.feature.TFBiomeFeatures;
import twilightforest.world.feature.TFGenCaveStalactite;
import twilightforest.world.feature.config.CaveStalactiteConfig;

import java.util.Random;

public class HollowHillComponent extends TFStructureComponentOld {

	int hillSize;
	int radius;

	public HollowHillComponent(ServerLevel level, CompoundTag nbt) {
		this(TFFeature.TFHill, nbt);
	}

	public HollowHillComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
		hillSize = nbt.getInt("hillSize");
		this.radius = ((hillSize * 2 + 1) * 8) - 6;
	}

	public HollowHillComponent(StructurePieceType piece, TFFeature feature, int i, int size, int x, int y, int z) {
		super(piece, feature, i);

		this.setOrientation(Direction.SOUTH);

		// get the size of this hill?
		this.hillSize = size;
		radius = ((hillSize * 2 + 1) * 8) - 6;

		// can we determine the size here?
		this.boundingBox = feature.getComponentToAddBoundingBox(x, y, z, -radius, -(3 + hillSize), -radius, radius * 2, radius / 2, radius * 2, Direction.SOUTH);
	}

	@Override
	protected void addAdditionalSaveData(ServerLevel level, CompoundTag tagCompound) {
		super.addAdditionalSaveData(level, tagCompound);
		tagCompound.putInt("hillSize", hillSize);
	}

	/**
	 * Add in all the blocks we're adding.
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		int[] sna = {0, 128, 256, 512};

		int sn = sna[hillSize]; // number of stalactites mga = {0, 3, 9, 18}
		int[] mga = {0, 1, 4, 9};
		int mg = mga[hillSize]; // number of monster generators mga = {0, 3, 9, 18} (reduced due to "natural" spawning)
		int[] tca = {0, 2, 6, 12};
		int tc = tca[hillSize];  // number of treasure chests tca = {0, 2, 6, 12};

		// fill in features

		// monster generators!
		for (int i = 0; i < mg; i++) {
			int[] dest = getCoordsInHill2D(rand);
			EntityType<?> mobID = getMobID(rand);

			setSpawner(world, dest[0], rand.nextInt(4), dest[1], sbb, mobID);
//			placeMobSpawner(dest[0], hy + rand.nextInt(4), dest[1]);
		}
		// treasure chests!!
		for (int i = 0; i < tc; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateTreasureChest(world, dest[0], 0, dest[1], sbb);
		}

		// ore or glowing stalactites! (smaller, less plentiful)
		for (int i = 0; i < sn; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateOreStalactite(world, generator, manager, dest[0], 1, dest[1], sbb);
		}
		// stone stalactites!
		for (int i = 0; i < sn; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, generator, manager, Blocks.STONE, 1.0F, true, dest[0], 1, dest[1], sbb);
		}
		// stone stalagmites!
		for (int i = 0; i < sn; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, generator, manager, Blocks.STONE, 0.9F, false, dest[0], 1, dest[1], sbb);
		}

		// level 3 hills get 2 mid-air wraith spawners
		if (hillSize == 3) {
		}

		return true;
	}

	/**
	 * Make an RNG and attempt to use it to place a treasure chest
	 */
	protected void generateTreasureChest(WorldGenLevel world, int x, int y, int z, BoundingBox sbb) {
		// generate an RNG for this chest
		//TODO: MOAR RANDOM!
		Random chestRNG = new Random(world.getSeed() + x * z);

		// try placing it
		placeTreasureAtCurrentPosition(world, x, y, z, this.hillSize == 3 ? TFTreasure.hill3 : (this.hillSize == 2 ? TFTreasure.hill2 : TFTreasure.hill1), sbb);

		// make something for it to stand on, if necessary
		placeBlock(world, Blocks.COBBLESTONE.defaultBlockState(), x, y - 1, z, sbb);
	}

	/**
	 * Generate a random ore stalactite
	 */
	protected void generateOreStalactite(WorldGenLevel world, ChunkGenerator generator, StructureFeatureManager manager, int x, int y, int z, BoundingBox sbb) {
		// are the coordinates in our bounding box?
		int dx = getWorldX(x, z);
		int dy = getWorldY(y);
		int dz = getWorldZ(x, z);
		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != Blocks.SPAWNER) {
			// generate an RNG for this stalactite
			//TODO: MOAR RANDOM!
			Random stalRNG = new Random(world.getSeed() + dx * dz);

			// make the actual stalactite
			CaveStalactiteConfig stalag = TFGenCaveStalactite.makeRandomOreStalactite(stalRNG, hillSize);
			TFBiomeFeatures.CAVE_STALACTITE.get().configured(stalag).place(world, generator, stalRNG, pos);
		}
	}

	/**
	 * Make a random stone stalactite
	 */
	protected void generateBlockStalactite(WorldGenLevel world, ChunkGenerator generator, StructureFeatureManager manager, Block blockToGenerate, float length, boolean up, int x, int y, int z, BoundingBox sbb) {
		// are the coordinates in our bounding box?
		int dx = getWorldX(x, z);
		int dy = getWorldY(y);
		int dz = getWorldZ(x, z);
		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != Blocks.SPAWNER) {
			// generate an RNG for this stalactite
			//TODO: MOAR RANDOM!
			Random stalRNG = new Random(world.getSeed() + dx * dz);

			if (hillSize == 1) {
				length *= 1.9F;
			}

			// make the actual stalactite
			TFBiomeFeatures.CAVE_STALACTITE.get().configured(new CaveStalactiteConfig(blockToGenerate.defaultBlockState(), length, -1, -1, up)).place(world, generator, stalRNG, pos);
		}
	}

	/**
	 * @param cx
	 * @param cz
	 * @return true if the coordinates would be inside the hill on the "floor" of the hill
	 */
	boolean isInHill(int cx, int cz) {
		int dx = radius - cx;
		int dz = radius - cz;
		int dist = (int) Math.sqrt(dx * dx + dz * dz);

		return dist < radius;
	}

	/**
	 * @return true if the coordinates are inside the hill in 3D
	 * TODO: Unused. Remove?
	 */
	boolean isInHill(int mapX, int mapY, int mapZ) {
		int dx = boundingBox.minX() + radius - mapX;
		int dy = (boundingBox.minY() - mapY) * 2; // hill is half as high as it is wide, thus we just double y distance from center.  I *think* that math works!
		int dz = boundingBox.minZ() + radius - mapZ;
		int dist = dx * dx + dy * dy + dz * dz;
		return dist < radius * radius;
	}

	int[] getCoordsInHill2D(Random rand) {
		return getCoordsInHill2D(rand, radius);
	}

	/**
	 * @return a two element array containing some coordinates in the hill
	 */
	int[] getCoordsInHill2D(Random rand, int rad) {
		int rx, rz;
		do {
			rx = rand.nextInt(2 * rad);
			rz = rand.nextInt(2 * rad);
		} while (!isInHill(rx, rz));

		int[] coords = {rx, rz};
		return coords;
	}

	/**
	 * Gets the id of a mob appropriate to the current hill size.
	 */
	protected EntityType<?> getMobID(Random rand) {
		return getMobID(rand, this.hillSize);
	}

	/**
	 * Gets the id of a mob appropriate to the specified hill size.
	 *
	 * @param level
	 * @return
	 */
	protected EntityType<?> getMobID(Random rand, int level) {
		if (level == 1) {
			return getLevel1Mob(rand);
		}
		if (level == 2) {
			return getLevel2Mob(rand);
		}
		if (level == 3) {
			return getLevel3Mob(rand);
		}

		return EntityType.SPIDER;
	}

	/**
	 * Returns a mob string appropriate for a level 1 hill
	 */
	public EntityType<?> getLevel1Mob(Random rand) {
		switch (rand.nextInt(10)) {
			case 0:
			case 1:
			case 2:
			default:
				return TFEntities.swarm_spider;
			case 3:
			case 4:
			case 5:
				return EntityType.SPIDER;
			case 6:
			case 7:
				return EntityType.ZOMBIE;
			case 8:
				return EntityType.SILVERFISH;
			case 9:
				return TFEntities.redcap;
		}
	}

	/**
	 * Returns a mob string appropriate for a level 2 hill
	 */
	public EntityType<?> getLevel2Mob(Random rand) {
		switch (rand.nextInt(10)) {
			case 0:
			case 1:
			case 2:
			default:
				return TFEntities.redcap;
			case 3:
			case 4:
			case 5:
				return EntityType.ZOMBIE;
			case 6:
			case 7:
				return EntityType.SKELETON;
			case 8:
				return TFEntities.swarm_spider;
			case 9:
				return EntityType.CAVE_SPIDER;
		}
	}

	/**
	 * Returns a mob string appropriate for a level 3 hill.  The level 3 also has 2 mid-air wraith spawners.
	 */
	public EntityType<?> getLevel3Mob(Random rand) {
		switch (rand.nextInt(11)) {
			case 0:
				return TFEntities.slime_beetle;
			case 1:
				return TFEntities.fire_beetle;
			case 2:
				return TFEntities.pinch_beetle;
			case 3:
			case 4:
			case 5:
				return EntityType.SKELETON;
			case 6:
			case 7:
			case 8:
				return EntityType.CAVE_SPIDER;
			case 9:
				return EntityType.CREEPER;
			case 10:
			default:
				return TFEntities.wraith;
		}
	}
}
