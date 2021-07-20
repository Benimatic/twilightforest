package twilightforest.structures.finalcastle;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;

import java.util.List;
import java.util.Random;

public class FinalCastleEntranceSideTowerComponent extends FinalCastleMazeTower13Component {

	public FinalCastleEntranceSideTowerComponent(TemplateManager manager, CompoundNBT nbt) {
		super(FinalCastlePieces.TFFCEnSiTo, nbt);
	}

	public FinalCastleEntranceSideTowerComponent(TFFeature feature, Random rand, int i, int x, int y, int z, int floors, int entranceFloor, Direction direction) {
		super(FinalCastlePieces.TFFCEnSiTo, feature, rand, i, x, y, z, floors, entranceFloor, TFBlocks.castle_rune_brick_pink.get().getDefaultState(), direction);

		addOpening(0, 1, size / 2, Rotation.CLOCKWISE_180);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}

		// add foundation
		FinalCastleFoundation13Component foundation = new FinalCastleFoundation13Component(FinalCastlePieces.TFFCToF13, getFeatureType(), rand, 4, this);
		list.add(foundation);
		foundation.buildComponent(this, list, rand);

		// add roof
		TFStructureComponentOld roof = new FinalCastleRoof13PeakedComponent(getFeatureType(), rand, 4, this);
		list.add(roof);
		roof.buildComponent(this, list, rand);
	}
}
