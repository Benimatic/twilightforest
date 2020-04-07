package twilightforest.structures.minotaurmaze;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;

import java.util.Random;

public class ComponentTFMazeRoomSpawnerChests extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomSpawnerChests(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMRSC, nbt);
	}

	public ComponentTFMazeRoomSpawnerChests(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World worldIn = world.getWorld();
		super.generate(world, generator, rand, sbb, chunkPosIn);

		// 4 pillar enclosures
		placePillarEnclosure(worldIn, sbb, 3, 3);
		placePillarEnclosure(worldIn, sbb, 10, 3);
		placePillarEnclosure(worldIn, sbb, 3, 10);
		placePillarEnclosure(worldIn, sbb, 10, 10);

		// spawner
		setSpawner(worldIn, 4, 2, 4, sbb, TFEntities.minotaur.get());

		// treasure
		this.placeTreasureAtCurrentPosition(worldIn, rand, 4, 2, 11, TFTreasure.labyrinth_room, sbb);

		// treasure
		this.placeTreasureAtCurrentPosition(worldIn, rand, 11, 2, 4, TFTreasure.labyrinth_room, sbb);

		// trap
		setBlockState(world, Blocks.OAK_PRESSURE_PLATE.getDefaultState(), 11, 1, 11, sbb);
		setBlockState(world, Blocks.TNT.getDefaultState(), 10, 0, 11, sbb);
		setBlockState(world, Blocks.TNT.getDefaultState(), 11, 0, 10, sbb);
		setBlockState(world, Blocks.TNT.getDefaultState(), 11, 0, 12, sbb);
		setBlockState(world, Blocks.TNT.getDefaultState(), 12, 0, 11, sbb);

		return true;
	}

	private void placePillarEnclosure(World world, MutableBoundingBox sbb,
									  int dx, int dz) {
		for (int y = 1; y < 5; y++) {
			final BlockState chiselledMazeBlock = TFBlocks.maze_stone_chiseled.get().getDefaultState();
			setBlockState(world, chiselledMazeBlock, dx + 0, y, dz + 0, sbb);
			setBlockState(world, chiselledMazeBlock, dx + 2, y, dz + 0, sbb);
			setBlockState(world, chiselledMazeBlock, dx + 0, y, dz + 2, sbb);
			setBlockState(world, chiselledMazeBlock, dx + 2, y, dz + 2, sbb);
		}
		setBlockState(world, Blocks.OAK_PLANKS.getDefaultState(), dx + 1, 1, dz + 1, sbb);
		setBlockState(world, Blocks.OAK_PLANKS.getDefaultState(), dx + 1, 4, dz + 1, sbb);

		final BlockState defaultState = Blocks.OAK_STAIRS.getDefaultState();


		setBlockState(world, getStairState(defaultState, Direction.NORTH, rotation, false), dx + 1, 1, dz + 0, sbb);
		setBlockState(world, getStairState(defaultState, Direction.WEST, rotation, false), dx + 0, 1, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, Direction.EAST, rotation, false), dx + 2, 1, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, Direction.SOUTH, rotation, false), dx + 1, 1, dz + 2, sbb);

		setBlockState(world, getStairState(defaultState, Direction.NORTH, rotation, true), dx + 1, 4, dz + 0, sbb);
		setBlockState(world, getStairState(defaultState, Direction.WEST, rotation, true), dx + 0, 4, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, Direction.EAST, rotation, true), dx + 2, 4, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, Direction.SOUTH, rotation, true), dx + 1, 4, dz + 2, sbb);

		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 1, 2, dz + 0, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 0, 2, dz + 1, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 2, 2, dz + 1, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 1, 2, dz + 2, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 1, 3, dz + 0, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 0, 3, dz + 1, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 2, 3, dz + 1, sbb);
		setBlockState(world, Blocks.IRON_BARS.getDefaultState(), dx + 1, 3, dz + 2, sbb);


	}

}
