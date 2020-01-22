package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFCastleMagic;
import twilightforest.block.BlockTFForceField;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.util.StructureBoundingBoxUtils;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleDungeonExit extends ComponentTFFinalCastleDungeonRoom31 {

	public ComponentTFFinalCastleDungeonExit() {}

	public ComponentTFFinalCastleDungeonExit(TFFeature feature, Random rand, int i, int x, int y, int z, Direction direction, int level) {
		super(feature, rand, i, x, y, z, direction, level);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}

		// no need for additional rooms, we're along the outside anyways

		// add stairway down
		Rotation bestDir = this.findStairDirectionTowards(parent.getBoundingBox().minX, parent.getBoundingBox().minZ);

		ComponentTFFinalCastleDungeonSteps steps0 = new ComponentTFFinalCastleDungeonSteps(rand, 5, boundingBox.minX + 15, boundingBox.minY, boundingBox.minZ + 15, bestDir.rotate(Direction.SOUTH));
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
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {

		if (!super.generate(world, generator, rand, sbb, chunkPosIn)) {
			return false;
		}

		// door
		final BlockState castleDoor = TFBlocks.castle_door_pink.get().getDefaultState();

		this.fillWithBlocks(world, sbb, 7, 0, 16, 7, 3, 18, castleDoor, AIR, false);
		this.fillWithBlocks(world, sbb, 7, 4, 16, 7, 4, 18, deco.blockState, deco.blockState, false);

		return true;
	}

	public Rotation findStairDirectionTowards(int x, int z) {
		Vec3i center = StructureBoundingBoxUtils.getCenter(this.boundingBox);
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

	//TODO: Make this BlockState
	@Override
	protected DyeColor getForceFieldColor(Random decoRNG) {
		return BlockTFForceField.VALID_COLORS.get(1);
	}

	//TODO: Make this BlockState
	@Override
	protected DyeColor getRuneColor(DyeColor fieldColor) {
		return BlockTFCastleMagic.VALID_COLORS.get(0);
	}
}
