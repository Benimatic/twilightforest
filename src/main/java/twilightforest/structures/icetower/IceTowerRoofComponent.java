package twilightforest.structures.icetower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.lichtower.TowerRoofComponent;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.Random;

public class IceTowerRoofComponent extends TowerRoofComponent {

	public IceTowerRoofComponent(ServerLevel level, CompoundTag nbt) {
		super(IceTowerPieces.TFITRoof, nbt);
	}

	public IceTowerRoofComponent(TFFeature feature, int i, TowerWingComponent wing) {
		super(IceTowerPieces.TFITRoof, feature, i);

		// same alignment
		this.setOrientation(wing.getOrientation());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = 12;

		this.deco = wing.deco;

		// just hang out at the very top of the tower
		makeCapBB(wing);
	}

	/**
	 * Swoopy ice roof
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				//int rHeight = this.size - (int) MathHelper.sqrt_float(x * z); // interesting office building pattern
				int rHeight = Math.round(Mth.sqrt(x * x + z * z));
				//int rHeight = MathHelper.ceiling_float_int(Math.min(x * x / 9F, z * z / 9F));

				for (int y = 0; y < rHeight; y++) {
					this.placeBlock(world, deco.blockState, x, y, z, sbb);

				}
			}
		}

		return true;
	}
}
