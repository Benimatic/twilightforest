package twilightforest.structures.finalcastle;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecoratorCastle;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleDungeonEntrance extends ComponentTFFinalCastleDungeonRoom31
{
	public boolean hasExit = false;

	public ComponentTFFinalCastleDungeonEntrance() {
	}

	public ComponentTFFinalCastleDungeonEntrance(Random rand, int i, int x, int y, int z, EnumFacing direction, int level) {
		super(rand, i, x, y, z, direction, level);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void buildComponent(StructureComponent parent, List list, Random rand) {
	    this.deco = new StructureTFDecoratorCastle();
	    this.deco.blockID = TFBlocks.castleMagic;
	    this.deco.blockMeta = 2;

	    this.deco.fenceID = TFBlocks.forceField;
	    this.deco.fenceMeta = 1;

	    // this is going to be the parent for all rooms on this level
	    super.buildComponent(this, list, rand);
    }


	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);

		// stairs
		for (int y = 0; y <= this.height; y++) {
			int x = (this.size / 2) - 2;
			int z = (this.size / 2) - y + 2;
			this.fillWithBlocks(world, sbb, x, y, z, x + 4, y, z, deco.stairID, getStairMeta(3), deco.stairID, getStairMeta(3), false);
			this.fillWithBlocks(world, sbb, x, 0, z, x + 4, y - 1, z, TFBlocks.deadrock.getDefaultState(), TFBlocks.deadrock.getDefaultState(), false);
		}

		// door
	    this.fillWithBlocks(world, sbb, 23, 0, 12, 23, 3, 14, TFBlocks.castleDoor, 2, AIR, false);
	    this.fillWithBlocks(world, sbb, 23, 4, 12, 23, 4, 14, deco.blockState, deco.blockState, false);


		return true;
	}

	@Override
	protected int getForceFieldMeta(Random decoRNG) {
		return 1;
	}

	@Override
	protected int getRuneMeta(int fieldMeta) {
		return 0;
	}
}
