package twilightforest.structures.stronghold;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class ComponentTFStrongholdRightTurn extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdRightTurn(TemplateManager manager, CompoundNBT nbt) {
		super(TFStrongholdPieces.TFSRT, nbt);
	}

	public ComponentTFStrongholdRightTurn(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(TFStrongholdPieces.TFSRT, feature, i, facing, x, y, z);
	}

	@Override
	public MutableBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 7, 9, facing);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random random) {
		super.buildComponent(parent, list, random);

		// entrance
		this.addDoor(4, 1, 0);

		// make a random component to the right
		addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 4);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		placeStrongholdWalls(world.getWorld(), sbb, 0, 0, 0, 8, 6, 8, rand, deco.randomBlocks);

		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, 7, 5, 7);

//		// entrance doorway
//		placeDoorwayAt(world, rand, 2, 4, 1, 0, sbb);
//		
//		// right turn doorway
//		placeDoorwayAt(world, rand, 1, 0, 1, 4, sbb);

		// statue
		placeCornerStatue(world.getWorld(), 6, 1, 6, 3, sbb);

		// doors
		placeDoors(world.getWorld(), rand, sbb);

		return true;
	}
}
