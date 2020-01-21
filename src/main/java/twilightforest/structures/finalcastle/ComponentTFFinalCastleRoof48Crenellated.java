package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.util.RotationUtil;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleRoof48Crenellated extends StructureTFComponentOld {
	public ComponentTFFinalCastleRoof48Crenellated() {
	}

	public ComponentTFFinalCastleRoof48Crenellated(TFFeature feature, Random rand, int i, StructureTFComponentOld keep) {
		super(feature, i);

		int height = 5;

		this.setCoordBaseMode(keep.getCoordBaseMode());
		this.boundingBox = new MutableBoundingBox(keep.getBoundingBox().minX - 2, keep.getBoundingBox().maxY - 1, keep.getBoundingBox().minZ - 2, keep.getBoundingBox().maxX + 2, keep.getBoundingBox().maxY + height - 1, keep.getBoundingBox().maxZ + 2);

	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}
	}

	@Override
	public boolean generate(IWorld worldIn, ChunkGenerator<?> generator, Random randomIn, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World world = worldIn.getWorld();
		// add second layer of floor
		final BlockState castleMagic = TFBlocks.castle_rune_brick_purple.get().getDefaultState();
		this.fillWithBlocks(world, sbb, 2, 2, 2, 50, 2, 50, castleMagic, castleMagic, false);

		// crenellations
		for (Rotation rotation : RotationUtil.ROTATIONS) {
			this.fillBlocksRotated(world, sbb, 3, 1, 1, 45, 3, 1, deco.blockState, rotation);

			for (int i = 10; i < 41; i += 5) {
				this.fillBlocksRotated(world, sbb, i, 1, 0, i + 2, 5, 2, deco.blockState, rotation);
				this.setBlockStateRotated(world, deco.blockState, i + 1, 0, 1, rotation, sbb);
			}
		}

		return true;
	}
}
