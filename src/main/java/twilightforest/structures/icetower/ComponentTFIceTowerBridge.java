package twilightforest.structures.icetower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public class ComponentTFIceTowerBridge extends StructureTFComponentOld {

	private int length;

	public ComponentTFIceTowerBridge(TemplateManager manager, CompoundNBT nbt) {
		super(TFIceTowerPieces.TFITBri, nbt);
	}

	public ComponentTFIceTowerBridge(TFFeature feature, int index, int x, int y, int z, int length, Direction direction) {
		super(TFIceTowerPieces.TFITBri, feature, index);
		this.length = length;
		this.setCoordBaseMode(direction);

		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, length, 6, 5, direction);
	}

	//TODO: See super
//	@Override
//	protected void writeStructureToNBT(CompoundNBT tagCompound) {
//		super.writeStructureToNBT(tagCompound);
//
//		tagCompound.putInt("bridgeLength", this.length);
//	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) {
		super.readAdditional(tagCompound);
		this.length = tagCompound.getInt("bridgeLength");
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		fillWithAir(world, sbb, 0, 1, 0, length, 5, 4);

		// make floor/ceiling
		fillWithBlocks(world, sbb, 0, 0, 0, length, 0, 4, deco.blockState, deco.blockState, false);
		fillWithBlocks(world, sbb, 0, 6, 0, length, 6, 4, deco.blockState, deco.blockState, false);

		// pillars
		for (int x = 2; x < length; x += 3) {
			fillWithBlocks(world, sbb, x, 1, 0, x, 5, 0, deco.pillarState, deco.pillarState, false);
			fillWithBlocks(world, sbb, x, 1, 4, x, 5, 4, deco.pillarState, deco.pillarState, false);
		}

		return true;
	}
}
