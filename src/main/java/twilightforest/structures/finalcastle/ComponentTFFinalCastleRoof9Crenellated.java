package twilightforest.structures.finalcastle;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.util.RotationUtil;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleRoof9Crenellated extends StructureTFComponentOld {

	public ComponentTFFinalCastleRoof9Crenellated(TemplateManager manager, CompoundNBT nbt) {
		super(TFFinalCastlePieces.TFFCRo9Cr, nbt);
	}

	public ComponentTFFinalCastleRoof9Crenellated(TFFeature feature, Random rand, int i, StructureTFComponentOld sideTower) {
		super(feature, i);

		int height = 5;

		this.setCoordBaseMode(sideTower.getCoordBaseMode());
		this.boundingBox = new MutableBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY + height - 1, sideTower.getBoundingBox().maxZ + 2);

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
		for (Rotation rotation : RotationUtil.ROTATIONS) {
			this.fillBlocksRotated(world, sbb, 0, -1, 0, 2, 3, 2, deco.blockState, rotation);
			this.setBlockStateRotated(world, deco.blockState, 1, -2, 2, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 1, -2, 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 2, -2, 1, rotation, sbb);

			this.setBlockStateRotated(world, deco.blockState, 3, 0, 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 3, 1, 1, rotation, sbb);

			this.fillBlocksRotated(world, sbb, 4, 0, 0, 5, 3, 2, deco.blockState, rotation);

			this.setBlockStateRotated(world, deco.blockState, 6, 0, 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 6, 1, 1, rotation, sbb);

			this.fillBlocksRotated(world, sbb, 7, 0, 0, 8, 3, 2, deco.blockState, rotation);

			this.setBlockStateRotated(world, deco.blockState, 9, 0, 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 9, 1, 1, rotation, sbb);
		}

		return true;
	}
}
