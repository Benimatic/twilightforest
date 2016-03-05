package twilightforest.structures.stronghold;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.TFBlocks;

public class StructureTFStrongholdShield extends StructureTFStrongholdComponent {



	public StructureTFStrongholdShield() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StructureTFStrongholdShield(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(0, 0, minX, minY, minZ);
		this.boundingBox = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
		this.spawnListIndex = -1;
	}

	@Override
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		Block shieldBlock = TFBlocks.shield;
		
		//Facing.facings
		
        // +x
        this.fillWithMetadataBlocks(world, sbb, this.boundingBox.getXSize(), 0, 0, this.boundingBox.getXSize(), this.boundingBox.getYSize(), this.boundingBox.getZSize(), shieldBlock, 4, shieldBlock, 4, false);
        // -x
        this.fillWithMetadataBlocks(world, sbb, 0, 0, 0, 0, this.boundingBox.getYSize(), this.boundingBox.getZSize(), shieldBlock, 5, shieldBlock, 5, false);
        // +z
        this.fillWithMetadataBlocks(world, sbb, 0, 0, this.boundingBox.getZSize(), this.boundingBox.getXSize(), this.boundingBox.getYSize(), this.boundingBox.getZSize(), shieldBlock, 2, shieldBlock, 2, false);
        // -z
        this.fillWithMetadataBlocks(world, sbb, 0, 0, 0, this.boundingBox.getXSize(), this.boundingBox.getYSize(), 0, shieldBlock, 3, shieldBlock, 3, false);
		// top
        this.fillWithMetadataBlocks(world, sbb, 0, 0, 0, this.boundingBox.getXSize(), 0, this.boundingBox.getZSize(), shieldBlock, 1, shieldBlock, 1, false);
        // bottom
        this.fillWithMetadataBlocks(world, sbb, 0, this.boundingBox.getYSize(), 0, this.boundingBox.getXSize(), this.boundingBox.getYSize(), this.boundingBox.getZSize(), shieldBlock, 0, shieldBlock, 0, false);
        
        return true;
	}

}
