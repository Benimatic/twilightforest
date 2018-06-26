package twilightforest.structures.start;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
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
import java.util.List;
import java.util.Random;

// Big ball of dough, let's roll it out into smaller pieces
@SuppressWarnings("ALL")
@Deprecated
public class StructureTFMajorFeatureCluster extends StructureStartTFAbstract {
	public TFFeature feature;

	static {
		MapGenStructureIO.registerStructure(StructureTFMajorFeatureCluster.class, "TFFeature");
		MapGenStructureIO.registerStructure(StructureTFHollowTreeStart.class, "TFHollowTree");

		MapGenStructureIO.registerStructure(StructureStartNothing.class, "TFFeature");

		TFHollowTreePieces.registerPieces();
	}

	public StructureTFMajorFeatureCluster() {
	}

	public StructureTFMajorFeatureCluster(World world, Random rand, int chunkX, int chunkZ) {
		super(world, rand, chunkX, chunkZ);
		StructureStrongholdPieces.prepareStructurePieces();
		//TFStrongholdPieces.prepareStructurePieces();

		int x = (chunkX << 4) + 8;
		int z = (chunkZ << 4) + 8;
		int y = TFWorld.SEALEVEL + 1; //TODO: maybe a biome-specific altitude for some of them?

		this.feature = TFFeature.getFeatureDirectlyAt(chunkX, chunkZ, world);
		this.isConquered = false;

		StructureComponent firstComponent = makeFirstComponent(world, rand, feature, x, y, z);
		if (firstComponent != null) {
			components.add(firstComponent);
			firstComponent.buildComponent(firstComponent, components, rand);
		}

		updateBoundingBox();

		if (firstComponent instanceof StructureStrongholdPieces.Stairs2) {
			List<StructureComponent> var6 = ((StructureStrongholdPieces.Stairs2) firstComponent).pendingChildren;

			while (!var6.isEmpty()) {
				int var7 = rand.nextInt(var6.size());
				StructureComponent var8 = var6.remove(var7);
				var8.buildComponent(firstComponent, this.components, rand);
			}

			updateBoundingBox();

			int offY = -33;

			boundingBox.offset(0, offY, 0);

			for (StructureComponent com : getComponents()) {
				com.getBoundingBox().offset(0, offY, 0);
			}
		}

		if (firstComponent instanceof ComponentTFTowerMain || firstComponent instanceof ComponentTFDarkTowerMain) {
			moveToAvgGroundLevel(world, x, z);
		}

		setupComponents(world);
	}

	/**
	 * @return The first component we should add to our structure
	 */
	@Nullable
	public StructureComponent makeFirstComponent(World world, Random rand, @Nullable TFFeature feature, int x, int y, int z) {
		/*if (feature != null) {
			//FIXME: Debug, force only one kind of feature to spawn.
			TwilightForestMod.LOGGER.info("Selected Debug Feature @ {} {} {}", x, y, z);
			return new ComponentNagaCourtyardMain(feature, world, rand, 0, x, y, z);
		}//*/

		if (feature == TFFeature.NAGA_COURTYARD) {
			TwilightForestMod.LOGGER.info("Naga Courtyard @ {} {} {}", x, y, z);
			return new ComponentNagaCourtyardMain(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.HEDGE_MAZE) {
			TwilightForestMod.LOGGER.info("Hedge Maze @ {} {} {}", x, y, z);
			return new ComponentTFHedgeMaze(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.SMALL_HILL) {
			TwilightForestMod.LOGGER.info("Hill 1 @ {} {} {}", x, y, z);
			return new ComponentTFHollowHill(feature, world, rand, 0, 1, x, y, z);
		}
		if (feature == TFFeature.MEDIUM_HILL) {
			TwilightForestMod.LOGGER.info("Hill 2 @ {} {} {}", x, y, z);
			return new ComponentTFHollowHill(feature, world, rand, 0, 2, x, y, z);
		}
		if (feature == TFFeature.LARGE_HILL) {
			TwilightForestMod.LOGGER.info("Hill 3 @ {} {} {}", x, y, z);
			return new ComponentTFHollowHill(feature, world, rand, 0, 3, x, y, z);
		}
		if (feature == TFFeature.QUEST_GROVE) {
			TwilightForestMod.LOGGER.info("Quest Grove @ {} {} {}", x, y, z);
			return new ComponentTFQuestGrove(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.HYDRA_LAIR) {
			TwilightForestMod.LOGGER.info("Hydra Lair @ {} {} {}", x, y, z);
			return new ComponentTFHydraLair(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.YETI_CAVE) {
			TwilightForestMod.LOGGER.info("Yeti Cave @ {} {} {}", x, y, z);
			return new ComponentTFYetiCave(feature, world, rand, 0, x, y, z);
		}

		if (feature == TFFeature.LICH_TOWER) {
			TwilightForestMod.LOGGER.info("Lich Tower @ {} {} {}", x, y, z);
			return new ComponentTFTowerMain(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.TROLL_CAVE) {
			TwilightForestMod.LOGGER.info("Troll Cave @ {} {} {}", x, y, z);
			return new ComponentTFTrollCaveMain(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.ICE_TOWER) {
			TwilightForestMod.LOGGER.info("Ice Tower @ {} {} {}", x, y, z);
			return new ComponentTFIceTowerMain(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.DARK_TOWER) {
			return new ComponentTFDarkTowerMain(feature, world, rand, 0, x, y - 1, z);
		}
		if (feature == TFFeature.LABYRINTH) {
			return new ComponentTFMazeRuins(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.MUSHROOM_TOWER) {
			return new ComponentTFMushroomTowerMain(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.KNIGHT_STRONGHOLD) {
			return new ComponentTFStrongholdEntrance(feature, world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.FINAL_CASTLE) {
			return new ComponentTFFinalCastleMain(feature, world, rand, 0, x, y, z);
		}

		return null;
	}

	@Override
	public boolean isSizeableStructure() {
		return feature.isStructureEnabled;
	}

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
	private boolean isIntersectingLarger(StructureBoundingBox chunkBB, StructureComponent component) {
		StructureBoundingBox compBB = component.getBoundingBox();

		// don't bother checking Y
		return (compBB.maxX + 1) >= chunkBB.minX && (compBB.minX - 1) <= chunkBB.maxX && (compBB.maxZ + 1) >= chunkBB.minZ && (compBB.minZ - 1) <= chunkBB.maxZ;

	}

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

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("FeatureID", this.feature.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.feature = TFFeature.values()[nbttagcompound.getInteger("FeatureID")];
	}

	public boolean isLocked(int lockIndex) {
		if (lockIndex < this.lockBytes.length) {

			TwilightForestMod.LOGGER.info("Checking locks for lockIndex " + lockIndex);

			for (int i = 0; i < this.lockBytes.length; i++) {
				TwilightForestMod.LOGGER.info("Lock " + i + " = " + this.lockBytes[i]);
			}

			return this.lockBytes[lockIndex] != 0;
		} else {

			TwilightForestMod.LOGGER.info("Current lock index, " + lockIndex + " is beyond array bounds " + this.lockBytes.length);


			return false;
		}
	}

}
