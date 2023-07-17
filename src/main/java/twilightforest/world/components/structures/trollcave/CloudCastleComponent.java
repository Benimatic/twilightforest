package twilightforest.world.components.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.entity.monster.ArmoredGiant;
import twilightforest.entity.monster.GiantMiner;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.init.TFLandmark;
import twilightforest.init.TFStructurePieceTypes;


public class CloudCastleComponent extends TFStructureComponentOld {

	private boolean minerPlaced = false;
	private boolean warriorPlaced = false;

	public CloudCastleComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(TFStructurePieceTypes.TFClCa.get(), nbt);
		this.minerPlaced = nbt.getBoolean("minerPlaced");
		this.warriorPlaced = nbt.getBoolean("warriorPlaced");
	}

	public CloudCastleComponent(int index, int x, int y, int z) {
		super(TFStructurePieceTypes.TFClCa.get(), index, x, y, z);
		this.setOrientation(Direction.SOUTH);

		// round to nearest mult of 4
		x &= ~0b11;
		y &= ~0b11;
		z &= ~0b11;

		// spawn list!
		this.spawnListIndex = 1;

		this.boundingBox = TFLandmark.getComponentToAddBoundingBox(x, y, z, -8, -4, -8, 64, 16, 64, Direction.SOUTH, false);
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
		super.addAdditionalSaveData(ctx, tagCompound);
		tagCompound.putBoolean("minerPlaced", this.minerPlaced);
		tagCompound.putBoolean("warriorPlaced", this.warriorPlaced);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
		// up to two trees
		// tree in x direction
		boolean plus = rand.nextBoolean();
		int offset = rand.nextInt(5) - rand.nextInt(5);
		CloudTreeComponent treeX = new CloudTreeComponent(this.getGenDepth() + 1, boundingBox.minX() + 8 + (plus ? 32 : -16), 168, boundingBox.minZ() + (offset * 4));
		list.addPiece(treeX);
		treeX.addChildren(this, list, rand);

		// tree in z direction
		plus = rand.nextBoolean();
		offset = rand.nextInt(5) - rand.nextInt(5);
		CloudTreeComponent treeZ = new CloudTreeComponent(this.getGenDepth() + 1, boundingBox.minX() + (offset * 4), 168, boundingBox.minZ() + 8 + (plus ? 32 : -16));
		list.addPiece(treeZ);
		treeZ.addChildren(this, list, rand);

	}

	@Override
	public void postProcess(WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {

		// make haus
		this.generateBox(world, sbb, 8, 0, 8, 23, 3, 23, TFBlocks.FLUFFY_CLOUD.get().defaultBlockState(), TFBlocks.FLUFFY_CLOUD.get().defaultBlockState(), false);
		this.generateBox(world, sbb, 8, 4, 8, 23, 15, 23, TFBlocks.GIANT_COBBLESTONE.get().defaultBlockState(), TFBlocks.GIANT_COBBLESTONE.get().defaultBlockState(), false);
		this.generateBox(world, sbb, 8, 16, 8, 23, 19, 23, TFBlocks.GIANT_LOG.get().defaultBlockState(), TFBlocks.GIANT_LOG.get().defaultBlockState(), false);

		// clear inside
		this.generateAirBox(world, sbb, 12, 4, 12, 19, 15, 19);

		// clear door
		this.generateAirBox(world, sbb, 8, 4, 12, 12, 11, 15);

		// add giants
		if (!this.minerPlaced) {
			int bx = this.getWorldX(14, 14);
			int by = this.getWorldY(4);
			int bz = this.getWorldZ(14, 14);
			BlockPos pos = new BlockPos(bx, by, bz);

			if (sbb.isInside(pos)) {
				this.minerPlaced = true;

				GiantMiner miner = TFEntities.GIANT_MINER.get().create(world.getLevel());
				miner.setPos(bx, by, bz);
				miner.setPersistenceRequired();
				ForgeEventFactory.onFinalizeSpawn(miner, world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);

				world.addFreshEntity(miner);
			}
		}
		if (!this.warriorPlaced) {
			int bx = this.getWorldX(17, 17);
			int by = this.getWorldY(4);
			int bz = this.getWorldZ(17, 17);
			BlockPos pos = new BlockPos(bx, by, bz);

			if (sbb.isInside(pos)) {
				this.warriorPlaced = true;

				ArmoredGiant warrior = TFEntities.ARMORED_GIANT.get().create(world.getLevel());
				warrior.setPos(bx, by, bz);
				warrior.setPersistenceRequired();
				ForgeEventFactory.onFinalizeSpawn(warrior, world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);

				world.addFreshEntity(warrior);
			}
		}
	}
}
