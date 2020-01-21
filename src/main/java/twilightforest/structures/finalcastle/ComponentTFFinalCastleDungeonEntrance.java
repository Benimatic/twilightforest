package twilightforest.structures.finalcastle;

import net.minecraft.block.StairsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFCastleMagic;
import twilightforest.block.BlockTFForceField;
import twilightforest.block.TFBlocks;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleDungeonEntrance extends ComponentTFFinalCastleDungeonRoom31 {

	public boolean hasExit = false;

	public ComponentTFFinalCastleDungeonEntrance() {}

	public ComponentTFFinalCastleDungeonEntrance(TFFeature feature, Random rand, int i, int x, int y, int z, Direction direction, int level) {
		super(feature, rand, i, x, y, z, direction, level);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		this.deco = new StructureTFDecoratorCastle();
		this.deco.blockState = TFBlocks.castle_rune_brick_yellow.get().getDefaultState();
		this.deco.fenceState = TFBlocks.force_field_pink.get().getDefaultState();

		// this is going to be the parent for all rooms on this level
		super.buildComponent(this, list, rand);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {

		if (!super.generate(world, generator, rand, sbb, chunkPosIn)) {
			return false;
		}

		final BlockState stairs = deco.stairState.with(StairsBlock.FACING, Direction.SOUTH);
		final BlockState deadRock = TFBlocks.deadrock.get().getDefaultState();
		// stairs
		for (int y = 0; y <= this.height; y++) {
			int x = (this.size / 2) - 2;
			int z = (this.size / 2) - y + 2;

			this.fillWithBlocks(world, sbb, x, 0, z, x + 4, y - 1, z, deadRock, deadRock, false);
			this.fillWithBlocks(world, sbb, x, y, z, x + 4, y, z, stairs, stairs, false);
			this.fillWithAir(world, sbb, x, y + 1, z, x + 4, y + 6, z);
		}

		// door
		final BlockState castleDoor = TFBlocks.castle_door_pink.get().getDefaultState();
		this.fillWithBlocks(world, sbb, 23, 0, 12, 23, 3, 14, castleDoor, AIR, false);
		this.fillWithBlocks(world, sbb, 23, 4, 12, 23, 4, 14, deco.blockState, deco.blockState, false);

		return true;
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
