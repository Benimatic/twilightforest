package twilightforest.structures.darktower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.structures.lichtower.TowerRoofComponent;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.List;
import java.util.Random;

public class DarkTowerRoofComponent extends TowerRoofComponent {

	public DarkTowerRoofComponent(StructureManager manager, CompoundTag nbt) {
		this(DarkTowerPieces.TFDTRooS, nbt);
	}

	public DarkTowerRoofComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
	}

	public DarkTowerRoofComponent(StructurePieceType piece, TFFeature feature, int i, TowerWingComponent wing) {
		super(piece, feature, i);

		// same alignment
		this.setOrientation(wing.getOrientation());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = 12;

		// just hang out at the very top of the tower
		makeCapBB(wing);

		// spawn list!
		this.spawnListIndex = 1;
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	/**
	 * A fence around the roof!
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// fence
		for (int x = 0; x <= size - 1; x++) {
			for (int z = 0; z <= size - 1; z++) {
				if (x == 0 || x == size - 1 || z == 0 || z == size - 1) {
					placeBlock(world, deco.fenceState, x, 1, z, sbb);
				}
			}
		}

		placeBlock(world, deco.accentState, 0, 1, 0, sbb);
		placeBlock(world, deco.accentState, size - 1, 1, 0, sbb);
		placeBlock(world, deco.accentState, 0, 1, size - 1, sbb);
		placeBlock(world, deco.accentState, size - 1, 1, size - 1, sbb);

		return true;
	}
}
