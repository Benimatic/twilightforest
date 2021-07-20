package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class MazeCorridorIronFenceComponent extends MazeCorridorComponent {

	public MazeCorridorIronFenceComponent(TemplateManager manager, CompoundNBT nbt) {
		super(MinotaurMazePieces.TFMMCIF, nbt);
	}

	public MazeCorridorIronFenceComponent(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(MinotaurMazePieces.TFMMCIF, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		this.fillWithBlocks(world, sbb, 1, 4, 2, 4, 4, 3, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 1, 1, 2, 4, 3, 3, TFBlocks.maze_stone_chiseled.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 2, 1, 2, 3, 3, 3, Blocks.IRON_BARS.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		return true;
	}
}
