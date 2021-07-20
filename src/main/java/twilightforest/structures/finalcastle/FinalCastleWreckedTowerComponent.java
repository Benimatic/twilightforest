package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FinalCastleWreckedTowerComponent extends FinalCastleDamagedTowerComponent {

	public FinalCastleWreckedTowerComponent(TemplateManager manager, CompoundNBT nbt) {
		super(FinalCastlePieces.TFFCWrT, nbt);
	}

	public FinalCastleWreckedTowerComponent(TFFeature feature, Random rand, int i, int x, int y, int z, Direction direction) {
		super(FinalCastlePieces.TFFCWrT, feature, rand, i, x, y, z, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}

//    		// add foundation
//    		Foundation13 foundation = new Foundation13(rand, 4, this);
//    		list.add(foundation);
//    		foundation.buildComponent(this, list, rand);

		// add thorns
		FinalCastleFoundation13Component thorns = new FinalCastleFoundation13ComponentThorns(getFeatureType(), rand, 0, this);
		list.add(thorns);
		thorns.buildComponent(this, list, rand);

//    		// add roof
//    		StructureTFComponentOld roof = rand.nextBoolean() ? new Roof13Conical(rand, 4, this) :  new Roof13Crenellated(rand, 4, this);
//    		list.add(roof);
//    		roof.buildComponent(this, list, rand);

		// keep on building?
//    		this.buildNonCriticalTowers(parent, list, rand);
	}

	@Override
	public BlockState getGlyphColour() {
		return TFBlocks.castle_rune_brick_blue.get().getDefaultState();
	}

	@Override
	protected void determineBlockDestroyed(ISeedReader world, ArrayList<DestroyArea> areas, int y, int x, int z) {
		boolean isInside = false;

		BlockPos pos = new BlockPos(x, y, z);

		for (DestroyArea dArea : areas) {
			if (dArea != null && dArea.isVecInside(pos)) {
				isInside = true;
			}
		}

		if (!isInside) {
			world.removeBlock(pos, false);
		}
	}

	@Override
	protected ArrayList<DestroyArea> makeInitialDestroyList(Random rand) {
		ArrayList<DestroyArea> areas = new ArrayList<DestroyArea>(2);

		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		return areas;
	}
}
