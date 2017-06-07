package twilightforest.structures.finalcastle;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerWing;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleDungeonRoom31 extends ComponentTFTowerWing
{
	public int level; // this is not serialized, since it's only used during build, which should be all one step

	public ComponentTFFinalCastleDungeonRoom31() {
	}

	public ComponentTFFinalCastleDungeonRoom31(Random rand, int i, int x, int y, int z, EnumFacing direction, int level) {
		super(i);
		this.setCoordBaseMode(direction);
		this.spawnListIndex = 2; // dungeon monsters
		this.size = 31;
		this.height = 7;
		this.level = level;
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -15, 0, -15, this.size - 1, this.height - 1, this.size - 1, EnumFacing.SOUTH);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void buildComponent(StructureComponent parent, List list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }

	    int mySpread = this.getComponentType() - parent.getComponentType();
	    int maxSpread = (this.level == 1) ? 2 : 3;

	    // add exit if we're far enough away and don't have one
	    if (mySpread == maxSpread && !isExitBuildForLevel(parent)) {
		    int direction = rand.nextInt(4);
		    for (int i = 0; i < 8 && !isExitBuildForLevel(parent); i++) {
			    direction = (direction + i) % 4;
			    if (this.addDungeonExit(parent, list, rand, direction)) {
				    this.setExitBuiltForLevel(parent, true);
			    }
		    }
	    }

	    // add other rooms
	    if (mySpread < maxSpread) {
		    int direction = rand.nextInt(4);
		    for (int i = 0; i < 12; i++) {
			    direction = (direction + i) % 4;
			    this.addDungeonRoom(parent, list, rand, direction, this.level);
		    }
	    }
    }

	private boolean isExitBuildForLevel(StructureComponent parent) {
		if (parent instanceof ComponentTFFinalCastleDungeonEntrance) {
			return ((ComponentTFFinalCastleDungeonEntrance)parent).hasExit;
		} else {
			return false;
		}
	}

	private void setExitBuiltForLevel(StructureComponent parent, boolean exit) {
		if (parent instanceof ComponentTFFinalCastleDungeonEntrance) {
			((ComponentTFFinalCastleDungeonEntrance)parent).hasExit = exit;
		} else {}
	}

	protected boolean addDungeonRoom(StructureComponent parent, List list, Random rand, int rotation, int level) {
		rotation = (this.coordBaseMode + rotation) % 4;

		BlockPos rc = this.getNewRoomCoords(rand, rotation);

		ComponentTFFinalCastleDungeonRoom31 dRoom = new ComponentTFFinalCastleDungeonRoom31(rand, this.componentType + 1, rc.getX(), rc.getY(), rc.getZ(), rotation, level);

		StructureBoundingBox largerBB = new StructureBoundingBox(dRoom.getBoundingBox());

		int expand = 0;
		largerBB.minX -= expand;
		largerBB.minZ -= expand;
		largerBB.maxX += expand;
		largerBB.maxZ += expand;

		StructureComponent intersect = StructureTFComponent.findIntersectingExcluding(list, largerBB, this);
		if (intersect == null) {
			list.add(dRoom);
			dRoom.buildComponent(parent, list, rand);
			return true;
		} else {
			return false;
		}
	}

	protected boolean addDungeonExit(StructureComponent parent, List list, Random rand, int rotation) {

		//TODO: check if we are sufficiently near the castle center

		rotation = (this.coordBaseMode + rotation) % 4;
		BlockPos rc = this.getNewRoomCoords(rand, rotation);
		ComponentTFFinalCastleDungeonExit dRoom = new ComponentTFFinalCastleDungeonExit(rand, this.componentType + 1, rc.getX(), rc.getY(), rc.getZ(), rotation, this.level);
		StructureComponent intersect = StructureTFComponent.findIntersectingExcluding(list, dRoom.getBoundingBox(), this);
		if (intersect == null) {
			list.add(dRoom);
			dRoom.buildComponent(this, list, rand);
			return true;
		} else {
			return false;
		}
	}

	private BlockPos getNewRoomCoords(Random rand, int rotation) {
		// make the rooms connect around the corners, not the centers
		int offset = rand.nextInt(15) - 9;
		if (rand.nextBoolean()) {
			offset += this.size;
		}

		switch (rotation) {
		default:
		case 0:
			return new BlockPos(this.boundingBox.maxX + 9, this.boundingBox.minY, this.boundingBox.minZ + offset);
		case 1:
			return new BlockPos(this.boundingBox.minX + offset, this.boundingBox.minY, this.boundingBox.maxZ + 9);
		case 2:
			return new BlockPos(this.boundingBox.minX - 9, this.boundingBox.minY, this.boundingBox.minZ + offset);
		case 3:
			return new BlockPos(this.boundingBox.minX + offset, this.boundingBox.minY, this.boundingBox.minZ - 9);
		}
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        if (this.isBoundingBoxOutOfPlateau(world, sbb)) {
            return false;
        }

		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		this.fillWithAir(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
		int forceFieldMeta = this.getForceFieldMeta(decoRNG);
		int runeMeta = getRuneMeta(forceFieldMeta);

		for (int rotation = 0; rotation < 4; rotation++) {
			int cs = 7;
			this.fillBlocksRotated(world, sbb, cs, 0, cs + 1, cs, this.height - 1, this.size - 2 - cs, TFBlocks.forceField, forceFieldMeta, rotation);
			// verticals
			for (int z = cs; z < ((this.size - 1) - cs); z += 4) {
				this.fillBlocksRotated(world, sbb, cs, 0, z, cs, this.height - 1, z, TFBlocks.castleMagic, runeMeta, rotation);
				// horizontals
				int y = ((z - cs) % 8 == 0) ? decoRNG.nextInt(3) + 0 : decoRNG.nextInt(3) + 4;
				this.fillBlocksRotated(world, sbb, cs, y, z + 1, cs, y, z + 3, TFBlocks.castleMagic, runeMeta, rotation);
			}
		}

		return true;
	}

	private boolean isBoundingBoxOutOfPlateau(World world, StructureBoundingBox sbb) {
        int minX = this.boundingBox.minX - 1;
        int minZ = this.boundingBox.minZ - 1;
        int maxX = this.boundingBox.maxX + 1;
        int maxZ = this.boundingBox.maxZ + 1;

        for (int x = minX; x <= maxX; x++) {
	        for (int z = minZ; z <= maxZ; z++) {
		        if (world.getBiome(x, z) != TFBiomeBase.highlandsCenter && world.getBiome(x, z) != TFBiomeBase.thornlands) {
			        return true;
		        }
	        }
        }

        return false;
	}

	protected int getRuneMeta(int forceFieldMeta) {
		return (forceFieldMeta == 4) ? 1 : 2;
	}

	protected int getForceFieldMeta(Random decoRNG) {
		return decoRNG.nextInt(2) + 3;
	}

}
