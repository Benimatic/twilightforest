package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdCrossing extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdCrossing() {}

	public ComponentTFStrongholdCrossing(int i, EnumFacing facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}

	@Override
	public StructureBoundingBox generateBoundingBox(EnumFacing facing, int x, int y, int z)
	{
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -13, -1, 0, 18, 7, 18, facing);
	}

	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		this.addDoor(13, 1, 0);
		addNewComponent(parent, list, random, Rotation.NONE, 4, 1, 18);
		addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 13);
		addNewComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 18, 1, 4);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 6, 17, rand, deco.randomBlocks);
		
		// statues
		placeCornerStatue(world, 2, 1, 2, 0, sbb);
		placeCornerStatue(world, 15, 1, 15, 3, sbb);
		
		// center pillar
		this.fillBlocksRotated(world, sbb, 8, 1, 8, 9, 5, 9, deco.pillarState, Rotation.NONE);
		
		// statues
		placeWallStatue(world, 8, 1, 7, 0, sbb);
		placeWallStatue(world, 7, 1, 9, 3, sbb);
		placeWallStatue(world, 9, 1, 10, 2, sbb);
		placeWallStatue(world, 10, 1, 8, 1, sbb);

		
		// tables
		placeTableAndChairs(world, sbb, 0);
		placeTableAndChairs(world, sbb, 1);
		placeTableAndChairs(world, sbb, 2);
		placeTableAndChairs(world, sbb, 3);

		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}

	private void placeTableAndChairs(World world, StructureBoundingBox sbb, Rotation rotation) {
		// table
		this.setBlockStateRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(0 + rotation) + 4, 5, 1, 3, sbb, rotation);
		this.setBlockStateRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(3 + rotation) + 4, 5, 1, 4, sbb, rotation);
		this.setBlockStateRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(1 + rotation) + 4, 6, 1, 3, sbb, rotation);
		this.setBlockStateRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(2 + rotation) + 4, 6, 1, 4, sbb, rotation);
		// chairs
		this.setBlockStateRotated(world, Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, getStructureRelativeRotation(rotation.add(Rotation.COUNTERCLOCKWISE_90))), 5, 1, 2, rotation, sbb);
		this.setBlockStateRotated(world, Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, getStructureRelativeRotation(rotation)), 7, 1, 3, rotation, sbb);
		this.setBlockStateRotated(world, Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, getStructureRelativeRotation(rotation.add(Rotation.CLOCKWISE_90))), 6, 1, 5, rotation, sbb);
		this.setBlockStateRotated(world, Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, getStructureRelativeRotation(rotation.add(Rotation.COUNTERCLOCKWISE_90))), 4, 1, 4, rotation, sbb);
	}



}
