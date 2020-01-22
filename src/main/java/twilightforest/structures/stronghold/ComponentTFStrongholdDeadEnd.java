package twilightforest.structures.stronghold;

import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.loot.TFTreasure;

import java.util.List;
import java.util.Random;

public class ComponentTFStrongholdDeadEnd extends StructureTFStrongholdComponent {

	private boolean chestTrapped;

	public ComponentTFStrongholdDeadEnd() {
	}

	public ComponentTFStrongholdDeadEnd(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(feature, i, facing, x, y, z);
	}

	@Override
	protected void writeStructureToNBT(CompoundNBT tagCompound) {
		super.writeStructureToNBT(tagCompound);

		tagCompound.putBoolean("chestTrapped", this.chestTrapped);
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) {
		super.readAdditional(tagCompound);
		this.chestTrapped = tagCompound.getBoolean("chestTrapped");
	}

	@Override
	public MutableBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 6, 9, facing);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random random) {
		super.buildComponent(parent, list, random);

		// entrance
		this.addDoor(4, 1, 0);

		this.chestTrapped = random.nextInt(3) == 0;
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World worldIn = world.getWorld();
		placeStrongholdWalls(worldIn, sbb, 0, 0, 0, 8, 6, 8, rand, deco.randomBlocks);

		// statues
		this.placeWallStatue(worldIn, 1, 1, 4, Rotation.CLOCKWISE_90, sbb);
		this.placeWallStatue(worldIn, 7, 1, 4, Rotation.COUNTERCLOCKWISE_90, sbb);
		this.placeWallStatue(worldIn, 4, 1, 7, Rotation.NONE, sbb);

		// doors
		placeDoors(worldIn, rand, sbb);

		// treasure
		this.placeTreasureAtCurrentPosition(worldIn, rand, 4, 1, 3, TFTreasure.stronghold_cache, this.chestTrapped, sbb);
		if (this.chestTrapped) {
			this.setBlockState(world, Blocks.TNT.getDefaultState(), 4, 0, 3, sbb);
		}

		for (int z = 2; z < 5; z++) {
			this.setBlockState(world, deco.stairState.with(StairsBlock.FACING, Direction.WEST), 3, 1, z, sbb);
			this.setBlockState(world, deco.stairState.with(StairsBlock.FACING, Direction.EAST), 5, 1, z, sbb);
		}

		this.setBlockState(world, deco.stairState.with(StairsBlock.FACING, Direction.NORTH), 4, 1, 2, sbb);
		this.setBlockState(world, deco.stairState.with(StairsBlock.FACING, Direction.SOUTH), 4, 1, 4, sbb);
		this.setBlockState(world, deco.stairState.with(StairsBlock.FACING, Direction.NORTH), 4, 2, 3, sbb);

		return true;
	}


}
