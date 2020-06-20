package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class ComponentTFMazeDeadEndFountain extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndFountain(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMDEF, nbt);
	}

	public ComponentTFMazeDeadEndFountain(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	public ComponentTFMazeDeadEndFountain(IStructurePieceType type, TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(type, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		// normal doorway
		super.generate(world, generator, rand, sbb, chunkPosIn);

		// back wall brick
		this.fillWithBlocks(world, sbb, 1, 1, 4, 4, 4, 4, TFBlocks.maze_stone_brick.get().getDefaultState(), AIR, false);

		// water
		this.setBlockState(world, Blocks.WATER.getDefaultState(), 2, 3, 4, sbb);
		this.setBlockState(world, Blocks.WATER.getDefaultState(), 3, 3, 4, sbb);

		// receptacle
		this.setBlockState(world, AIR, 2, 0, 3, sbb);
		this.setBlockState(world, AIR, 3, 0, 3, sbb);

		return true;
	}
}
