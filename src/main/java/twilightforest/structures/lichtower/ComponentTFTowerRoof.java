package twilightforest.structures.lichtower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public class ComponentTFTowerRoof extends StructureTFComponentOld {

	protected int size;
	protected int height;

	public ComponentTFTowerRoof(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	//TODO: Parameter "wing" is unused. Remove?
	public ComponentTFTowerRoof(IStructurePieceType type, TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(type, feature, i);

		this.spawnListIndex = -1;

		// inheritors need to add a bounding box or die~!
	}

	public ComponentTFTowerRoof(TemplateManager manager, CompoundNBT nbt) {
		super(TFLichTowerPieces.TFLTRoo, nbt);
	}

	/**
	 * Save to NBT
	 * TODO: See super
	 */
//	@Override
//	protected void writeStructureToNBT(CompoundNBT tagCompound) {
//		super.writeStructureToNBT(tagCompound);
//
//		tagCompound.putInt("roofSize", this.size);
//		tagCompound.putInt("roofHeight", this.height);
//	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void readAdditional(CompoundNBT tagCompound) {
		super.readAdditional(tagCompound);
		this.size = tagCompound.getInt("roofSize");
		this.height = tagCompound.getInt("roofHeight");
	}

	/**
	 * Makes a bounding box that hangs forwards off of the tower wing we are on.  This is for attached roofs.
	 *
	 * @param wing
	 */
	protected void makeAttachedOverhangBB(ComponentTFTowerWing wing) {
		// just hang out at the very top of the tower
		switch (getCoordBaseMode()) {
			case SOUTH:
				this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ - 1, wing.getBoundingBox().maxX + 1, wing.getBoundingBox().maxY + this.height - 1, wing.getBoundingBox().maxZ + 1);
				break;
			case WEST:
				this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX - 1, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ, wing.getBoundingBox().maxX + 1, wing.getBoundingBox().maxY + this.height - 1, wing.getBoundingBox().maxZ + 1);
				break;
			case EAST:
				this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX - 1, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ - 1, wing.getBoundingBox().maxX, wing.getBoundingBox().maxY + this.height - 1, wing.getBoundingBox().maxZ + 1);
				break;
			case NORTH:
				this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX - 1, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ - 1, wing.getBoundingBox().maxX + 1, wing.getBoundingBox().maxY + this.height - 1, wing.getBoundingBox().maxZ);
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
	protected void makeCapBB(ComponentTFTowerWing wing) {
		this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ, wing.getBoundingBox().maxX, wing.getBoundingBox().maxY + this.height, wing.getBoundingBox().maxZ);
	}

	/**
	 * Make a bounding box that hangs over the sides of the tower 1 block.  Freestanding towers only.
	 *
	 * @param wing
	 */
	protected void makeOverhangBB(ComponentTFTowerWing wing) {
		this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX - 1, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ - 1, wing.getBoundingBox().maxX + 1, wing.getBoundingBox().maxY + this.height - 1, wing.getBoundingBox().maxZ + 1);
	}

	@Override
	public boolean generate(IWorld worldIn, ChunkGenerator<?> generator, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn) {
		return false;
	}

	/**
	 * Does this roof intersect anything except the parent tower?
	 */
	public boolean fits(ComponentTFTowerWing parent, List<StructurePiece> list, Random rand) {
		return StructurePiece.findIntersecting(list, this.boundingBox) == parent;
	}
}
