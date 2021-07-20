package twilightforest.structures.darktower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class DarkTowerEntranceComponent extends DarkTowerWingComponent {

	public DarkTowerEntranceComponent(TemplateManager manager, CompoundNBT nbt) {
		super(DarkTowerPieces.TFDTEnt, nbt);
	}

	protected DarkTowerEntranceComponent(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(DarkTowerPieces.TFDTEnt, feature, i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		super.buildComponent(parent, list, rand);

		// a few more openings
		addOpening(size / 2, 1, 0, Rotation.CLOCKWISE_90, EnumDarkTowerDoor.REAPPEARING);
		addOpening(size / 2, 1, size - 1, Rotation.COUNTERCLOCKWISE_90, EnumDarkTowerDoor.REAPPEARING);
	}

	@Override
	public void makeABeard(StructurePiece parent, List<StructurePiece> list, Random rand) {
	}

	@Override
	public void makeARoof(StructurePiece parent, List<StructurePiece> list, Random rand) {
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// make walls
		makeEncasedWalls(world, rand, sbb, 0, 0, 0, size - 1, height - 1, size - 1);

		// deco to ground
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				this.setBlockState(world, deco.accentState, x, -1, z, sbb);
			}
		}

		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);

		// sky light
//		nullifySkyLightForBoundingBox(world);

		// openings
		makeOpenings(world, sbb);

		return true;
	}
}
