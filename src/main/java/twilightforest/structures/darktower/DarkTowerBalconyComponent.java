package twilightforest.structures.darktower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.List;
import java.util.Random;

public class DarkTowerBalconyComponent extends TowerWingComponent {

	public DarkTowerBalconyComponent(ServerLevel level, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTBal, nbt);
	}

	protected DarkTowerBalconyComponent(TFFeature feature, int i, int x, int y, int z, Direction direction) {
		super(DarkTowerPieces.TFDTBal, feature, i, x, y, z, 5, 5, direction);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// make floor
		generateBox(world, sbb, 0, 0, 0, 2, 0, 4, deco.accentState, Blocks.AIR.defaultBlockState(), false);
		generateBox(world, sbb, 0, 0, 1, 1, 0, 3, deco.blockState, Blocks.AIR.defaultBlockState(), false);

		generateBox(world, sbb, 0, 1, 0, 2, 1, 4, deco.fenceState, Blocks.AIR.defaultBlockState(), false);

		this.placeBlock(world, deco.accentState, 2, 1, 0, sbb);
		this.placeBlock(world, deco.accentState, 2, 1, 4, sbb);

		// clear inside
		generateAirBox(world, sbb, 0, 1, 1, 1, 1, 3);

		return true;
	}
}
