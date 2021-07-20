package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.util.StructureBoundingBoxUtils;

import java.util.List;
import java.util.Random;

public class FinalCastleDungeonExitComponent extends FinalCastleDungeonRoom31Component {

	public FinalCastleDungeonExitComponent(TemplateManager manager, CompoundNBT nbt) {
		super(FinalCastlePieces.TFFCDunEx, nbt);
	}

	public FinalCastleDungeonExitComponent(TFFeature feature, Random rand, int i, int x, int y, int z, Direction direction, int level) {
		super(FinalCastlePieces.TFFCDunEx, feature, rand, i, x, y, z, direction, level);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}

		// no need for additional rooms, we're along the outside anyways

		// add stairway down
		Rotation bestDir = this.findStairDirectionTowards(parent.getBoundingBox().minX, parent.getBoundingBox().minZ);

		FinalCastleDungeonStepsComponent steps0 = new FinalCastleDungeonStepsComponent(getFeatureType(), rand, 5, boundingBox.minX + 15, boundingBox.minY, boundingBox.minZ + 15, bestDir.rotate(Direction.SOUTH));
		list.add(steps0);
		steps0.buildComponent(this, list, rand);

		// another level!?
		if (this.level == 1) {
			steps0.buildLevelUnder(parent, list, rand, this.level + 1);
		} else {
			steps0.buildBossRoomUnder(parent, list, rand);
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {

		if (!super.func_230383_a_(world, manager, generator, rand, sbb, chunkPosIn, blockPos)) {
			return false;
		}

		// door
		final BlockState castleDoor = TFBlocks.castle_door_pink.get().getDefaultState();

		this.fillWithBlocks(world, sbb, 7, 0, 16, 7, 3, 18, castleDoor, AIR, false);
		this.fillWithBlocks(world, sbb, 7, 4, 16, 7, 4, 18, deco.blockState, deco.blockState, false);

		return true;
	}

	public Rotation findStairDirectionTowards(int x, int z) {
		Vector3i center = StructureBoundingBoxUtils.getCenter(this.boundingBox);
		// difference
		int dx = center.getX() - x;
		int dz = center.getZ() - z;

		Rotation absoluteDir;
		if (Math.abs(dz) >= Math.abs(dx)) {
			absoluteDir = (dz >= 0) ? Rotation.CLOCKWISE_180 : Rotation.NONE;
		} else {
			absoluteDir = (dx >= 0) ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90;
		}

		return absoluteDir;
	}

	@Override
	protected BlockState getForceFieldColor(Random decoRNG) {
		return TFBlocks.force_field_pink.get().getDefaultState();
	}

	@Override
	protected BlockState getRuneColor(BlockState fieldColor) {
		return TFBlocks.castle_rune_brick_pink.get().getDefaultState();
	}
}
