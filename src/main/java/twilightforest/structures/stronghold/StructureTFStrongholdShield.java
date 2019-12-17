package twilightforest.structures.stronghold;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class StructureTFStrongholdShield extends StructureTFStrongholdComponent {

	public StructureTFStrongholdShield() {
	}

	public StructureTFStrongholdShield(TFFeature feature, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(feature, 0, Direction.SOUTH, minX, minY, minZ);
		this.boundingBox = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
		this.spawnListIndex = -1;
	}

	@Override
	public StructureBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		BlockState down  = TFBlocks.stronghold_shield.getDefaultState().with(BlockDirectional.FACING, Direction.DOWN);
		BlockState up    = TFBlocks.stronghold_shield.getDefaultState().with(BlockDirectional.FACING, Direction.UP);
		BlockState north = TFBlocks.stronghold_shield.getDefaultState().with(BlockDirectional.FACING, Direction.NORTH);
		BlockState south = TFBlocks.stronghold_shield.getDefaultState().with(BlockDirectional.FACING, Direction.SOUTH);
		BlockState west  = TFBlocks.stronghold_shield.getDefaultState().with(BlockDirectional.FACING, Direction.WEST);
		BlockState east  = TFBlocks.stronghold_shield.getDefaultState().with(BlockDirectional.FACING, Direction.EAST);

		// +x
		this.fillWithBlocks(world, sbb, this.boundingBox.getXSize(), 0, 0, this.boundingBox.getXSize(), this.boundingBox.getYSize(), this.boundingBox.getZSize(), west, west, false);
		// -x
		this.fillWithBlocks(world, sbb, 0, 0, 0, 0, this.boundingBox.getYSize(), this.boundingBox.getZSize(), east, east, false);
		// +z
		this.fillWithBlocks(world, sbb, 0, 0, this.boundingBox.getZSize(), this.boundingBox.getXSize(), this.boundingBox.getYSize(), this.boundingBox.getZSize(), north, north, false);
		// -z
		this.fillWithBlocks(world, sbb, 0, 0, 0, this.boundingBox.getXSize(), this.boundingBox.getYSize(), 0, south, south, false);
		// top
		this.fillWithBlocks(world, sbb, 0, 0, 0, this.boundingBox.getXSize(), 0, this.boundingBox.getZSize(), up, up, false);
		// bottom
		this.fillWithBlocks(world, sbb, 0, this.boundingBox.getYSize(), 0, this.boundingBox.getXSize(), this.boundingBox.getYSize(), this.boundingBox.getZSize(), down, down, false);

		return true;
	}

}
