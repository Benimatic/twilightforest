package twilightforest.structures.lichtower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;

import java.util.List;
import java.util.Random;

public class TowerRoofComponent extends TFStructureComponentOld {

	protected int size;
	protected int height;

	public TowerRoofComponent(StructureManager manager, CompoundTag nbt) {
		this(LichTowerPieces.TFLTRoo, nbt);
	}

	public TowerRoofComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
		this.size = nbt.getInt("roofSize");
		this.height = nbt.getInt("roofHeight");
	}

	public TowerRoofComponent(StructurePieceType type, TFFeature feature, int i) {
		super(type, feature, i);

		this.spawnListIndex = -1;

		// inheritors need to add a bounding box or die~!
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tagCompound) {
		super.addAdditionalSaveData(tagCompound);
		tagCompound.putInt("roofSize", this.size);
		tagCompound.putInt("roofHeight", this.height);
	}

	/**
	 * Makes a bounding box that hangs forwards off of the tower wing we are on.  This is for attached roofs.
	 *
	 * @param wing
	 */
	protected void makeAttachedOverhangBB(TowerWingComponent wing) {
		// just hang out at the very top of the tower
		switch (getOrientation()) {
			case SOUTH:
				this.boundingBox = new BoundingBox(wing.getBoundingBox().x0, wing.getBoundingBox().y1, wing.getBoundingBox().z0 - 1, wing.getBoundingBox().x1 + 1, wing.getBoundingBox().y1 + this.height - 1, wing.getBoundingBox().z1 + 1);
				break;
			case WEST:
				this.boundingBox = new BoundingBox(wing.getBoundingBox().x0 - 1, wing.getBoundingBox().y1, wing.getBoundingBox().z0, wing.getBoundingBox().x1 + 1, wing.getBoundingBox().y1 + this.height - 1, wing.getBoundingBox().z1 + 1);
				break;
			case EAST:
				this.boundingBox = new BoundingBox(wing.getBoundingBox().x0 - 1, wing.getBoundingBox().y1, wing.getBoundingBox().z0 - 1, wing.getBoundingBox().x1, wing.getBoundingBox().y1 + this.height - 1, wing.getBoundingBox().z1 + 1);
				break;
			case NORTH:
				this.boundingBox = new BoundingBox(wing.getBoundingBox().x0 - 1, wing.getBoundingBox().y1, wing.getBoundingBox().z0 - 1, wing.getBoundingBox().x1 + 1, wing.getBoundingBox().y1 + this.height - 1, wing.getBoundingBox().z1);
				break;
			default:
				break;
		}
	}

	/**
	 * Makes a bounding box that sits at the top of the tower.  Works for attached or freestanding roofs.
	 *
	 * @param wing
	 */
	protected void makeCapBB(TowerWingComponent wing) {
		this.boundingBox = new BoundingBox(wing.getBoundingBox().x0, wing.getBoundingBox().y1, wing.getBoundingBox().z0, wing.getBoundingBox().x1, wing.getBoundingBox().y1 + this.height, wing.getBoundingBox().z1);
	}

	/**
	 * Make a bounding box that hangs over the sides of the tower 1 block.  Freestanding towers only.
	 *
	 * @param wing
	 */
	protected void makeOverhangBB(TowerWingComponent wing) {
		this.boundingBox = new BoundingBox(wing.getBoundingBox().x0 - 1, wing.getBoundingBox().y1, wing.getBoundingBox().z0 - 1, wing.getBoundingBox().x1 + 1, wing.getBoundingBox().y1 + this.height - 1, wing.getBoundingBox().z1 + 1);
	}

	@Override
	public boolean postProcess(WorldGenLevel worldIn, StructureFeatureManager manager, ChunkGenerator generator, Random randomIn, BoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn, BlockPos blockPos) {
		return false;
	}

	/**
	 * Does this roof intersect anything except the parent tower?
	 */
	public boolean fits(TowerWingComponent parent, List<StructurePiece> list) {
		return StructurePiece.findCollisionPiece(list, this.boundingBox) == parent;
	}
}
