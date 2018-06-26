package twilightforest.structures.start;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.*;
import twilightforest.structures.courtyard.ComponentNagaCourtyardMain;
import twilightforest.structures.darktower.ComponentTFDarkTowerMain;
import twilightforest.structures.finalcastle.ComponentTFFinalCastleMain;
import twilightforest.structures.hollowtree.StructureTFHollowTreeStart;
import twilightforest.structures.hollowtree.TFHollowTreePieces;
import twilightforest.structures.icetower.ComponentTFIceTowerMain;
import twilightforest.structures.lichtower.ComponentTFTowerMain;
import twilightforest.structures.minotaurmaze.ComponentTFMazeRuins;
import twilightforest.structures.mushroomtower.ComponentTFMushroomTowerMain;
import twilightforest.structures.stronghold.ComponentTFStrongholdEntrance;
import twilightforest.structures.trollcave.ComponentTFTrollCaveMain;
import twilightforest.world.TFWorld;

import javax.annotation.Nullable;
import java.util.Random;

// Big ball of dough, let's roll it out into smaller pieces
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class StructureTFMajorFeatureCluster extends StructureStartTFAbstract {
	// TODO figure out what to do with these
	static {
		MapGenStructureIO.registerStructure(StructureTFMajorFeatureCluster.class, "TFFeature");
		MapGenStructureIO.registerStructure(StructureTFHollowTreeStart.class, "TFHollowTree");

		MapGenStructureIO.registerStructure(StructureStartNothing.class, "TFFeature");

		TFHollowTreePieces.registerPieces();
	}

	public StructureTFMajorFeatureCluster() {
	}

	public StructureTFMajorFeatureCluster(World world, TFFeature feature, Random rand, int chunkX, int chunkZ) {
		super(world, feature, rand, chunkX, chunkZ);
		StructureStrongholdPieces.prepareStructurePieces();
		//TFStrongholdPieces.prepareStructurePieces();

		int x = (chunkX << 4) + 8;
		int z = (chunkZ << 4) + 8;
		int y = TFWorld.SEALEVEL + 1; //TODO: maybe a biome-specific altitude for some of them?

		//this.feature = TFFeature.getFeatureDirectlyAt(chunkX, chunkZ, world);
		this.isConquered = false;

		StructureComponent firstComponent = makeFirstComponent(world, rand, x, y, z);
		if (firstComponent != null) {
			components.add(firstComponent);
			firstComponent.buildComponent(firstComponent, components, rand);
		}

		updateBoundingBox();

		//if (firstComponent instanceof StructureStrongholdPieces.Stairs2) {
		//	List<StructureComponent> var6 = ((StructureStrongholdPieces.Stairs2) firstComponent).pendingChildren;

		//	while (!var6.isEmpty()) {
		//		int var7 = rand.nextInt(var6.size());
		//		StructureComponent var8 = var6.remove(var7);
		//		var8.buildComponent(firstComponent, this.components, rand);
		//	}

		//	updateBoundingBox();

		//	int offY = -33;

		//	boundingBox.offset(0, offY, 0);

		//	for (StructureComponent com : getComponents()) {
		//		com.getBoundingBox().offset(0, offY, 0);
		//	}
		//}

		if (firstComponent instanceof ComponentTFTowerMain || firstComponent instanceof ComponentTFDarkTowerMain) {
			moveToAvgGroundLevel(world, x, z);
		}

		setupComponents(world);
	}

	/**
	 * @return The first component we should add to our structure
	 */
	public StructureComponent makeFirstComponent(World world, Random rand, int x, int y, int z) {
		return null;
	}
		/*if (feature != null) {
			//FIXME: Debug, force only one kind of feature to spawn.
			TwilightForestMod.LOGGER.info("Selected Debug Feature @ {} {} {}", x, y, z);
			return new ComponentNagaCourtyardMain(feature, world, rand, 0, x, y, z);
		}//*/

		/*if (feature == TFFeature.NAGA_COURTYARD) {
			TwilightForestMod.LOGGER.info("Naga Courtyard @ {} {} {}", x, y, z);
			return
		}
		if (feature == TFFeature.HEDGE_MAZE) {
			TwilightForestMod.LOGGER.info("Hedge Maze @ {} {} {}", x, y, z);

		}
		if (feature == TFFeature.QUEST_GROVE) {
			TwilightForestMod.LOGGER.info("Quest Grove @ {} {} {}", x, y, z);
			return
		}
		if (feature == TFFeature.HYDRA_LAIR) {
			TwilightForestMod.LOGGER.info("Hydra Lair @ {} {} {}", x, y, z);
			return
		}
		if (feature == TFFeature.YETI_CAVE) {
			TwilightForestMod.LOGGER.info("Yeti Cave @ {} {} {}", x, y, z);
			return
		}

		if (feature == TFFeature.LICH_TOWER) {
			TwilightForestMod.LOGGER.info("Lich Tower @ {} {} {}", x, y, z);
			return
		}
		if (feature == TFFeature.TROLL_CAVE) {
			TwilightForestMod.LOGGER.info("Troll Cave @ {} {} {}", x, y, z);
			return
		}
		if (feature == TFFeature.ICE_TOWER) {
			TwilightForestMod.LOGGER.info("Ice Tower @ {} {} {}", x, y, z);
			return
		}
		if (feature == TFFeature.DARK_TOWER) {
			return
		}
		if (feature == TFFeature.LABYRINTH) {
			return
		}
		if (feature == TFFeature.MUSHROOM_TOWER) {
			return
		}
		if (feature == TFFeature.KNIGHT_STRONGHOLD) {
			return
		}
		if (feature == TFFeature.FINAL_CASTLE) {
			return
		}

		return null;
	} */

//    /**
//     * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
//     */
//    public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
//    {
//        if (this.getComponents().getFirst() instanceof ComponentTFStrongholdEntrance)
//        {
//
//			for (StructureComponent component : (LinkedList<StructureComponent>) getComponents())
//			{
//
//				// TODO: we need to test the shield bounding box here, otherwise we lose shield facings across chunk boundires
//
//	            if (isShieldable(component) ? isIntersectingLarger(par3StructureBoundingBox, component) : isIntersectingLarger(par3StructureBoundingBox, component))
//	            {
//	            	if (isShieldable(component))
//	            	{
//	            		addShieldFor(par1World, component, (LinkedList<StructureComponent>) getComponents(), par3StructureBoundingBox);
//	            	}
//	            	component.addComponentParts(par1World, par2Random, par3StructureBoundingBox);
//	            }
//			}
//        }
//        else
//        {
//        	super.generateStructure(par1World, par2Random, par3StructureBoundingBox);
//        }
//    }

	/**
	 * Check if the component is within the chunk bounding box, but check as if it was one larger
	 */
	//private boolean isIntersectingLarger(StructureBoundingBox chunkBB, StructureComponent component) {
	//	StructureBoundingBox compBB = component.getBoundingBox();

	//	// don't bother checking Y
	//	return (compBB.maxX + 1) >= chunkBB.minX && (compBB.minX - 1) <= chunkBB.maxX && (compBB.maxZ + 1) >= chunkBB.minZ && (compBB.minZ - 1) <= chunkBB.maxZ;

	//}

	//private boolean isShieldable(StructureComponent component) {
	//	return component.getBoundingBox().maxY <= 32;
	//}

	/**
	 * Make the stronghold shield around a component's bounding box
	 */
	/*private void addShieldFor(World world, StructureComponent component, List<StructureComponent> otherComponents, StructureBoundingBox chunkBox) {
		StructureBoundingBox shieldBox = new StructureBoundingBox(component.getBoundingBox());

		shieldBox.minX--;
		shieldBox.minY--;
		shieldBox.minZ--;

		shieldBox.maxX++;
		shieldBox.maxY++;
		shieldBox.maxZ++;

		ArrayList<StructureComponent> intersecting = new ArrayList<StructureComponent>();

		for (StructureComponent other : otherComponents) {
			if (other != component && shieldBox.intersectsWith(other.getBoundingBox())) {
				intersecting.add(other);
			}
		}

		// trace outline
		for (int x = shieldBox.minX; x <= shieldBox.maxX; x++) {
			for (int y = shieldBox.minY; y <= shieldBox.maxY; y++) {
				for (int z = shieldBox.minZ; z <= shieldBox.maxZ; z++) {
					if (x == shieldBox.minX || x == shieldBox.maxX || y == shieldBox.minY || y == shieldBox.maxY || z == shieldBox.minZ || z == shieldBox.maxZ) {
						BlockPos pos = new BlockPos(x, y, z);
						if (chunkBox.isVecInside(pos)) {
							// test other boxes
							boolean notIntersecting = true;

							for (StructureComponent other : intersecting) {
								if (other.getBoundingBox().isVecInside(pos)) {
									notIntersecting = false;
								}
							}


							if (notIntersecting) {
								world.setBlockState(pos, TFBlocks.stronghold_shield.getDefaultState().withProperty(BlockDirectional.FACING, calculateShieldFacing(shieldBox, x, y, z)), 2);
							}

						}
					}
				}
			}
		}
	}*/

	/*private EnumFacing calculateShieldFacing(StructureBoundingBox shieldBox, int x, int y, int z) {
		EnumFacing facing = EnumFacing.DOWN;
		if (x == shieldBox.minX) {
			facing = EnumFacing.EAST;
		}
		if (x == shieldBox.maxX) {
			facing = EnumFacing.WEST;
		}
		if (z == shieldBox.minZ) {
			facing = EnumFacing.SOUTH;
		}
		if (z == shieldBox.maxZ) {
			facing = EnumFacing.NORTH;
		}
		if (y == shieldBox.minY) {
			facing = EnumFacing.UP;
		}
		if (y == shieldBox.maxY) {
			facing = EnumFacing.DOWN;
		}
		return facing;
	}*/
}
