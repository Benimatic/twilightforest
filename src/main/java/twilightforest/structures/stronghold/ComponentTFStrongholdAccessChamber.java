package twilightforest.structures.stronghold;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFTrophyPedestal;
import twilightforest.block.TFBlocks;

import java.util.List;
import java.util.Random;

public class ComponentTFStrongholdAccessChamber extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdAccessChamber() {
	}

	public ComponentTFStrongholdAccessChamber(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(feature, i, facing, x, y, z);
	}

	@Override
	public MutableBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return MutableBoundingBox.getComponentToAddBoundingBox(x, y, z, -4, 1, 0, 9, 5, 9, facing);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random random) {
		super.buildComponent(parent, list, random);

		// make a random component in each direction
		addNewUpperComponent(parent, list, random, Rotation.NONE, 4, 1, 9);
		addNewUpperComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 4);
		addNewUpperComponent(parent, list, random, Rotation.CLOCKWISE_180, 4, 1, -1);
		addNewUpperComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 9, 1, 4);

	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, 8, 4, 8, true, rand, deco.randomBlocks);

		// doors
		placeSmallDoorwayAt(world.getWorld(), rand, 0, 4, 1, 8, sbb);
		placeSmallDoorwayAt(world.getWorld(), rand, 1, 0, 1, 4, sbb);
		placeSmallDoorwayAt(world.getWorld(), rand, 2, 4, 1, 0, sbb);
		placeSmallDoorwayAt(world.getWorld(), rand, 3, 8, 1, 4, sbb);

		// shaft down
		final BlockState defaultState = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
		this.fillWithBlocks(world, sbb, 2, -2, 2, 6, 0, 6, defaultState, AIR, false);

		this.fillWithAir(world, sbb, 3, -2, 3, 5, 2, 5);

		// stairs surrounding shaft
		this.fillWithBlocks(world, sbb, 2, 0, 3, 2, 0, 6, getStairState(deco.stairState, Rotation.CLOCKWISE_180.rotate(Direction.WEST), rotation, false), AIR, false);
		this.fillWithBlocks(world, sbb, 6, 0, 2, 6, 0, 6, getStairState(deco.stairState, Rotation.NONE.rotate(Direction.WEST), rotation, false), AIR, false);
		this.fillWithBlocks(world, sbb, 3, 0, 2, 5, 0, 2, getStairState(deco.stairState, Rotation.COUNTERCLOCKWISE_90.rotate(Direction.WEST), rotation, false), AIR, false);
		this.fillWithBlocks(world, sbb, 3, 0, 6, 5, 0, 6, getStairState(deco.stairState, Rotation.CLOCKWISE_90.rotate(Direction.WEST), rotation, false), AIR, false);

		// pillar
		this.setBlockState(world, deco.pillarState, 2, 0, 2, sbb);

		// pedestal
		final BlockState blockstateIn = TFBlocks.trophy_pedestal.get()
				.getDefaultState()
				.with(BlockTFTrophyPedestal.LATENT, true)
				//TODO: Atomic: Verify this facing.
				.with(BlockTFTrophyPedestal.FACING, Direction.EAST);
		this.setBlockState(world, blockstateIn, 2, 1, 2, sbb);

		// block point
		this.fillWithBlocks(world, sbb, 2, -1, 2, 6, -1, 6, TFBlocks.stronghold_shield.get().getDefaultState(), AIR, false);

		return true;
	}

	/**
	 * Does this component fall under block protection when progression is turned on, normally true
	 */
	@Override
	public boolean isComponentProtected() {
		return false;
	}
}
