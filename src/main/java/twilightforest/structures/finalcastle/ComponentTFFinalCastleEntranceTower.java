package twilightforest.structures.finalcastle;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleEntranceTower extends ComponentTFFinalCastleMazeTower13 {

	public ComponentTFFinalCastleEntranceTower(TemplateManager manager, CompoundNBT nbt) {
		super(TFFinalCastlePieces.TFFCEnTo, nbt);
	}

	public ComponentTFFinalCastleEntranceTower(TFFeature feature, Random rand, int i, int x, int y, int z, Direction direction) {
		super(TFFinalCastlePieces.TFFCEnTo, feature, rand, i, x, y, z, 3, 2, TFBlocks.castle_rune_brick_pink.get().getDefaultState(), direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}

		// add foundation
		ComponentTFFinalCastleFoundation13 foundation = new ComponentTFFinalCastleFoundation13(TFFinalCastlePieces.TFFCToF13, getFeatureType(), rand, 4, this);
		list.add(foundation);
		foundation.buildComponent(this, list, rand);

		// add roof
		StructureTFComponentOld roof = new ComponentTFFinalCastleRoof13Peaked(getFeatureType(), rand, 4, this);
		list.add(roof);
		roof.buildComponent(this, list, rand);

		// how many floors until the bottom?
		int missingFloors = (this.boundingBox.minY - 127) / 8;

		// place half on the bottom
		int bottomFloors = missingFloors / 2;
		// how many are left for the middle?
		int middleFloors = missingFloors - bottomFloors;

		// what direction can we put the side tower in, if any?
		Direction facing = Rotation.CLOCKWISE_90.rotate(this.getCoordBaseMode());
		int howFar = 20;
		if (!this.buildSideTower(list, rand, middleFloors + 1, facing, howFar)) {
			facing = Rotation.COUNTERCLOCKWISE_90.rotate(this.getCoordBaseMode());
			if (!this.buildSideTower(list, rand, middleFloors + 1, facing, howFar)) {
				facing = Rotation.NONE.rotate(this.getCoordBaseMode());
				if (!this.buildSideTower(list, rand, middleFloors + 1, facing, howFar)) {
					// side tower no worky
				}
			}
		}

		// add bottom tower
		ComponentTFFinalCastleEntranceBottomTower eTower = new ComponentTFFinalCastleEntranceBottomTower(getFeatureType(), rand, this.getComponentType() + 1, this.boundingBox.minX + 6, this.boundingBox.minY - (middleFloors) * 8, this.boundingBox.minZ + 6, bottomFloors + 1, bottomFloors, facing.getOpposite());
		list.add(eTower);
		eTower.buildComponent(this, list, rand);

		// add bridge to bottom
		BlockPos opening = this.getValidOpeningCC(rand, facing);
		opening = opening.down(middleFloors * 8);

		BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, facing);
		ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(getFeatureType(), this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, facing);
		list.add(bridge);
		bridge.buildComponent(this, list, rand);
	}

	private boolean buildSideTower(List<StructurePiece> list, Random rand, int middleFloors, Direction facing, int howFar) {
		BlockPos opening = this.getValidOpeningCC(rand, facing);

		// build towards
		BlockPos tc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), howFar, facing);

		ComponentTFFinalCastleEntranceSideTower eTower = new ComponentTFFinalCastleEntranceSideTower(getFeatureType(), rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), middleFloors, middleFloors - 1, facing);

		MutableBoundingBox largerBB = new MutableBoundingBox(eTower.getBoundingBox());

		largerBB.minX -= 6;
		largerBB.minZ -= 6;
		largerBB.maxX += 6;
		largerBB.maxZ += 6;

		StructurePiece intersect = StructurePiece.findIntersecting(list, largerBB);

		if (intersect == null) {
			list.add(eTower);
			eTower.buildComponent(this, list, rand);
			// add bridge
			BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, facing);
			ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(getFeatureType(), this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, facing);
			list.add(bridge);
			bridge.buildComponent(this, list, rand);

			// opening
			addOpening(opening.getX(), opening.getY() + 1, opening.getZ(), facing);

			return true;
		} else {
			TwilightForestMod.LOGGER.info("side entrance tower blocked");
			return false;
		}

	}

	/**
	 * Gets a random position in the specified direction that connects to a floor currently in the tower.
	 */
	@Override
	public BlockPos getValidOpeningCC(Random rand, Direction facing) {
		BlockPos opening = super.getValidOpeningCC(rand, facing);
		return new BlockPos(opening.getX(), 0, opening.getZ());
	}


}
