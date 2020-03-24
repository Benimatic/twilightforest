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
import twilightforest.structures.StructureTFComponentOld;

import java.util.Random;

public class ComponentTFMazeCorridor extends StructureTFComponentOld {

	public ComponentTFMazeCorridor(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMC, nbt);
	}

	public ComponentTFMazeCorridor(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	public ComponentTFMazeCorridor(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i);
		this.setCoordBaseMode(rotation);
		this.boundingBox = new MutableBoundingBox(x, y, z, x + 5, y + 5, z + 5);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		//arch
		this.fillWithBlocks(world, sbb, 1, 1, 2, 4, 4, 3, Blocks.OAK_FENCE.getDefaultState(), AIR, false);
		this.fillWithAir(world, sbb, 2, 1, 2, 3, 3, 3);

		return true;
	}
}
