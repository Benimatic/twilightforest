package twilightforest.world.components.structures.mushroomtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.registration.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.structures.TFStructureComponentOld;

import java.util.Random;

public class MushroomTowerBridgeComponent extends MushroomTowerWingComponent {

	int dSize;
	int dHeight;

	public MushroomTowerBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		this(MushroomTowerPieces.TFMTBri, nbt);
	}

	public MushroomTowerBridgeComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
		this.dSize = nbt.getInt("destSize");
		this.dHeight = nbt.getInt("destHeight");
	}

	protected MushroomTowerBridgeComponent(StructurePieceType piece, TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(piece, feature, i, x, y, z, pSize, pHeight, direction);

		this.boundingBox = feature.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, 3, direction);

		this.dSize = pSize;
		this.dHeight = pHeight;
	}

	@Override
	protected void addAdditionalSaveData(ServerLevel level, CompoundTag tagCompound) {
		super.addAdditionalSaveData(level, tagCompound);
		tagCompound.putInt("destSize", this.dSize);
		tagCompound.putInt("destHeight", this.dHeight);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}

		int[] dest = new int[]{dSize - 1, 1, 1};
		boolean madeWing = makeTowerWing(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], dSize, dHeight, Rotation.NONE);

		if (!madeWing) {
			int[] dx = offsetTowerCoords(dest[0], dest[1], dest[2], dSize, Direction.SOUTH);
			TwilightForestMod.LOGGER.info("Making tower wing failed when bridge was already made.  Size = {}, x = {}, z = {}", dSize, dx[0], dx[2]);
		}
	}

	public BoundingBox getWingBB() {
		int[] dest = offsetTowerCoords(dSize - 1, 1, 1, dSize, this.getOrientation());
		return getFeatureType().getComponentToAddBoundingBox(dest[0], dest[1], dest[2], 0, 0, 0, dSize - 1, dHeight - 1, dSize - 1, this.getOrientation());
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {

		// make walls
		for (int x = 0; x < dSize; x++) {
			placeBlock(world, deco.fenceState, x, 1, 0, sbb);
			placeBlock(world, deco.fenceState, x, 1, 2, sbb);

			placeBlock(world, this.isAscender ? Blocks.JUNGLE_PLANKS.defaultBlockState() : deco.floorState, x, 0, 1, sbb);
		}

		// clear bridge walkway
		this.generateAirBox(world, sbb, 0, 1, 1, 2, 2, 1);

		return true;
	}
}
