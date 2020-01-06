package twilightforest.structures.finalcastle;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleEntranceSideTower extends ComponentTFFinalCastleMazeTower13 {
	public ComponentTFFinalCastleEntranceSideTower() {
	}

	public ComponentTFFinalCastleEntranceSideTower(TFFeature feature, Random rand, int i, int x, int y, int z, int floors, int entranceFloor, Direction direction) {
		super(feature, rand, i, x, y, z, floors, entranceFloor, TFBlocks.castle_rune_brick_pink.get().getDefaultState(), direction);

		addOpening(0, 1, size / 2, Rotation.CLOCKWISE_180);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}

		// add foundation
		ComponentTFFinalCastleFoundation13 foundation = new ComponentTFFinalCastleFoundation13(getFeatureType(), rand, 4, this);
		list.add(foundation);
		foundation.buildComponent(this, list, rand);

		// add roof
		StructureTFComponentOld roof = new ComponentTFFinalCastleRoof13Peaked(getFeatureType(), rand, 4, this);
		list.add(roof);
		roof.buildComponent(this, list, rand);
	}

}
