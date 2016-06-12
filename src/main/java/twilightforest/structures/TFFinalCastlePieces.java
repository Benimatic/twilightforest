package twilightforest.structures;

import java.awt.Rectangle;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import twilightforest.TFFeature;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFFinalCastlePieces.EntranceTower;
import twilightforest.structures.TFFinalCastlePieces.MazeTower13;
import twilightforest.structures.icetower.ComponentTFIceTowerStairs;
import twilightforest.structures.icetower.StructureDecoratorIceTower;
import twilightforest.structures.lichtower.ComponentTFTowerBridge;
import twilightforest.structures.lichtower.ComponentTFTowerWing;
import twilightforest.structures.minotaurmaze.ComponentTFMazeUpperEntrance;
import twilightforest.structures.stronghold.StructureTFDecoratorStronghold;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;

public class TFFinalCastlePieces {




	public static void registerFinalCastlePieces()
    {
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Main.class, "TFFCMain");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.StairTower.class, "TFFCStTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.LargeTower.class, "TFFCLaTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Mural.class, "TFFCMur");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Foundation48.class, "TFFCToF48");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Roof48Crenellated.class, "TFFCRo48Cr");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.BossGazebo.class, "TFFCBoGaz");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.MazeTower13.class, "TFFCSiTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.DungeonSteps.class, "TFFCDunSt");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.DungeonEntrance.class, "TFFCDunEn");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.DungeonRoom31.class, "TFFCDunR31");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.DungeonExit.class, "TFFCDunEx");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.DungeonForgeRoom.class, "TFFCDunBoR");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Roof9Crenellated.class, "TFFCRo9Cr");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Roof13Crenellated.class, "TFFCRo13Cr");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Roof13Conical.class, "TFFCRo13Con");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Roof13Peaked.class, "TFFCRo13Pk");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.EntranceTower.class, "TFFCEnTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.EntranceSideTower.class, "TFFCEnSiTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.EntranceBottomTower.class, "TFFCEnBoTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.EntranceStairs.class, "TFFCEnSt");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.BellTower21.class, "TFFCBelTo");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Bridge.class, "TFFCBri");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Foundation13.class, "TFFCToF13");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.BellFoundation21.class, "TFFCBeF21");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.Foundation13Thorns.class, "TFFCFTh21");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.DamagedTower.class, "TFFCDamT");
        MapGenStructureIO.func_143031_a(TFFinalCastlePieces.WreckedTower.class, "TFFCWrT");
    }
    
	public static class Main extends StructureTFComponent {

    	public Main() {
    	}
    	
    	public Main(World world, Random rand, int i, int x, int y, int z) {
    		this.setCoordBaseMode(0);
			this.spawnListIndex = 1; // main monsters
    		
			x = ((x + 127) >> 8) << 8;
			z = ((z + 127) >> 8) << 8;

    		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -24, 120, -24, 48, 40, 48, 0);
    		
			ChunkCoordinates cc = TFFeature.getNearestCenterXYZ(x >> 4, z >> 4, world);
			
			int cx = (x >> 8) << 8;
			int cz = (z >> 8) << 8;
			
			System.out.println("Making castle at " + x + ", " + z + ". center is " + cc.posX + ", " + cc.posZ);
			System.out.println("Natural center at " + cx + ", " + cz);

			// decorator
			if (this.deco == null)
			{
				this.deco = new StructureTFDecoratorCastle();
			}
    	}

    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		// add foundation
    		Foundation48 foundation = new Foundation48(rand, 4, this);
    		list.add(foundation);
    		foundation.buildComponent(this, list, rand);
    		
    		// add roof
    		StructureTFComponent roof = new Roof48Crenellated(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    		
    		// boss gazebo on roof
    		StructureTFComponent gazebo = new BossGazebo(rand, 5, this);
    		list.add(gazebo);
    		gazebo.buildComponent(this, list, rand);
    		

    		// build 4 towers on sides
    		StairTower tower0 = new StairTower(rand, 3, boundingBox.minX, boundingBox.minY + 3, boundingBox.minZ, 2);
    		list.add(tower0);
    		tower0.buildComponent(this, list, rand);	
    		
    		LargeTower tower1 = new LargeTower(rand, 3, boundingBox.maxX, boundingBox.minY + 3, boundingBox.minZ, 3);
    		list.add(tower1);
    		tower1.buildComponent(this, list, rand);	
    		
    		StairTower tower2 = new StairTower(rand, 3, boundingBox.minX, boundingBox.minY + 3, boundingBox.maxZ, 1);
    		list.add(tower2);
    		tower2.buildComponent(this, list, rand);	
    		
    		StairTower tower3 = new StairTower(rand, 3, boundingBox.maxX, boundingBox.minY + 3, boundingBox.maxZ, 0);
    		list.add(tower3);
    		tower3.buildComponent(this, list, rand);
    		
    		// tower maze towards entrance
    		ChunkCoordinates dest = new ChunkCoordinates(boundingBox.minX - 4, boundingBox.maxY, boundingBox.minZ - 24);
			buildTowerMaze(list, rand, 48, 0, 24, 60, 0, 0, dest);	

			
			// another tower/bridge maze towards the clock tower
			dest = new ChunkCoordinates(boundingBox.maxX + 4, boundingBox.minY, boundingBox.maxZ + 24);
			buildTowerMaze(list, rand, 0, 30, 24, 60, 2, 1, dest);	

			
			// initial stairs down towards dungeon
    		DungeonSteps steps0 = new DungeonSteps(rand, 5, boundingBox.minX + 18, boundingBox.minY + 1, boundingBox.minZ + 18, 0);
    		list.add(steps0);
    		steps0.buildComponent(this, list, rand);
    		
    		// continued steps
    		DungeonSteps steps1 = steps0.buildMoreStepsTowards(parent, list, rand, 3);
    		DungeonSteps steps2 = steps1.buildMoreStepsTowards(parent, list, rand, 3);
     		DungeonSteps steps3 = steps2.buildMoreStepsTowards(parent, list, rand, 3);

    		// start dungeon
    		DungeonEntrance dRoom = steps3.buildLevelUnder(parent, list, rand, 1);
    		
    		// mural on front
			ChunkCoordinates mc = this.offsetTowerCCoords(48, 23, 25, 1, 0);
    		Mural mural0 = new Mural(rand, 7, mc.posX, mc.posY, mc.posZ, 35, 30, 0);
    		list.add(mural0);
    		mural0.buildComponent(this, list, rand);
    		
    		// mural inside
			ChunkCoordinates mc1 = this.offsetTowerCCoords(48, 33, 24, -1, 0);
    		Mural mural1 = new Mural(rand, 7, mc1.posX, mc1.posY, mc.posZ, 19, 12, 2);
    		list.add(mural1);
    		mural1.buildComponent(this, list, rand);

    	}

    	/**
    	 * Build a side tower, then tell it to start building towards the destination
    	 */
    	private void buildTowerMaze(List list, Random rand, int x, int y, int z, int howFar, int direction, int type, ChunkCoordinates dest) {
    		// duplicate list
    		LinkedList before = new LinkedList(list);
    		
    		// build
			ChunkCoordinates tc = this.offsetTowerCCoords(x, y, z, howFar, direction);
    		MazeTower13 sTower = new MazeTower13(rand, 3, tc.posX, tc.posY, tc.posZ, type, direction);
    		// add bridge
			ChunkCoordinates bc = this.offsetTowerCCoords(x, y, z, 1, direction);
			Bridge bridge = new Bridge(this.getComponentType() + 1, bc.posX, bc.posY, bc.posZ, howFar - 7, direction);
			list.add(bridge);
			bridge.buildComponent(this, list, rand);
			
    		// don't check if the bounding box is clear, there's either nothing there or we've made a terrible mistake 
    		list.add(sTower);
    		sTower.buildTowards(this, list, rand, dest);
    		
    		// check if we've successfully built the end tower
    		if (this.isMazeComplete(list, type)) {
    			System.out.println("Tower maze type " + type + " complete!");
    		} else {
    			// TODO: add limit on retrying, in case of infinite loop?
    			System.out.println("Tower maze type " + type + " INCOMPLETE, retrying!");
    			list.clear();
    			list.addAll(before);
    			this.buildTowerMaze(list, rand, x, y, z, howFar, direction, type, dest);
    		}
    		
    	}
    	
		private boolean isMazeComplete(List list, int type) {
	        Iterator iterator = list.iterator();
	        StructureComponent structurecomponent;

	        do
	        {
	            if (!iterator.hasNext())
	            {
	                return false;
	            }

	            structurecomponent = (StructureComponent)iterator.next();
	        }
	        while (!((structurecomponent instanceof EntranceTower && type == 0) || (structurecomponent instanceof BellTower21 && type == 1)));
	        
			return true;
		}

		/**
		 * Provides coordinates to make a tower such that it will open into the parent tower at the provided coordinates.
		 */
		protected ChunkCoordinates offsetTowerCCoords(int x, int y, int z, int howFar, int direction) {
			
			int dx = getXWithOffset(x, z);
			int dy = getYWithOffset(y);
			int dz = getZWithOffset(x, z);
			
			switch (direction) {
			case 0:
				dx += howFar;
				break;
			case 1:
				dz += howFar;
				break;
			case 2:
				dx -= howFar;
				break;
			case 3:
				dz -= howFar;
				break;
			}
			
			// ugh?
			return new ChunkCoordinates(dx, dy, dz);
		}

    	@Override
    	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
    		// walls
    		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, 48, 40, 48, false, rand, deco.randomBlocks);
    		
    		// 2M
    		fillWithRandomizedBlocks(world, sbb, 13, 30, 1, 47, 30, 12, false, rand, deco.randomBlocks);
    		this.fillWithBlocks(world, sbb, 13, 31, 12, 36, 31, 12, deco.fenceID, deco.fenceID, false);
    		fillWithRandomizedBlocks(world, sbb, 13, 30, 36, 47, 30, 47, false, rand, deco.randomBlocks);
    		this.fillWithBlocks(world, sbb, 13, 31, 36, 36, 31, 36, deco.fenceID, deco.fenceID, false);
    		fillWithRandomizedBlocks(world, sbb, 1, 30, 1, 12, 30, 47, false, rand, deco.randomBlocks);
    		this.fillWithBlocks(world, sbb, 12, 31, 12, 12, 31, 36, deco.fenceID, deco.fenceID, false);
    		
    		// second floor stairs to mezzanine
    		fillWithRandomizedBlocks(world, sbb, 38, 25, 13, 47, 25, 35, false, rand, deco.randomBlocks);
    		
    		for (int i = 0; i < 5; i++) {
    			int y = 30 - i;

        		makeMezzTopStairs(world, sbb, y, 10 + i, 3);
        		makeMezzTopStairs(world, sbb, y, 38 - i, 1);
        		
        		y = 25 - i;
        		int x = 37 - i;
    			this.fillWithMetadataBlocks(world, sbb, x, y, 14, x, y, 22, deco.stairID, getStairMeta(0), deco.stairID, getStairMeta(0), false);
    			this.fillWithMetadataBlocks(world, sbb, x, y - 1, 14, x, y - 1, 22, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
    			this.fillWithMetadataBlocks(world, sbb, x, y, 26, x, y, 34, deco.stairID, getStairMeta(0), deco.stairID, getStairMeta(0), false);
    			this.fillWithMetadataBlocks(world, sbb, x, y - 1, 26, x, y - 1, 34, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
    		}

    		
    		// pillars
    		for (int x = 11; x < 47; x += 12) {
        		for (int z = 11; z < 47; z += 12) {
            		this.fillWithMetadataBlocks(world, sbb, x, 1, z, x + 2, 40, z + 2, deco.pillarID, deco.pillarMeta, deco.blockID, deco.blockMeta, false);

            		makePillarBase(world, sbb, x, z, 1, 0);
            		makePillarBase(world, sbb, x, z, 19, 4);
            		makePillarBase(world, sbb, x, z, 21, 0);
            		makePillarBase(world, sbb, x, z, 39, 4);

        		
        		}
    		}
    		
    		// side pillars
    		for (int rotation = 0; rotation < 4; rotation++) {
    			for (int z = 11; z < 47; z += 12) {
    				
    				// no middle pillars on walls with entrances
    				if (z == 23 && (rotation == 0 || rotation == 2)) {
    					continue;
    				}
    				
            		this.fillBlocksRotated(world, sbb, 1, 1, z, 1, 40, z + 2, deco.pillarID, deco.pillarMeta, rotation);
					makeHalfPillarBase(world, sbb, rotation, 1, z, 0);
					makeHalfPillarBase(world, sbb, rotation, 19, z, 4);
					makeHalfPillarBase(world, sbb, rotation, 21, z, 0);
					makeHalfPillarBase(world, sbb, rotation, 39, z, 4);
    			}
    		}
    		
    		// second floor
    		fillWithRandomizedBlocks(world, sbb, 1, 20, 1, 47, 20, 47, false, rand, deco.randomBlocks);
    		
    		// force field around dungeon stairs
    		Block fieldBlock = TFBlocks.forceField;
			int fieldMeta = 6;
			this.fillWithMetadataBlocks(world, sbb, 12, 1, 12, 24, 10, 12, fieldBlock, fieldMeta, fieldBlock, fieldMeta, false);
    		this.fillWithMetadataBlocks(world, sbb, 12, 1, 12, 12, 10, 24, fieldBlock, fieldMeta, fieldBlock, fieldMeta, false);
    		this.fillWithMetadataBlocks(world, sbb, 24, 1, 12, 24, 10, 24, fieldBlock, fieldMeta, fieldBlock, fieldMeta, false);
    		this.fillWithMetadataBlocks(world, sbb, 12, 1, 24, 24, 10, 24, fieldBlock, fieldMeta, fieldBlock, fieldMeta, false);

    		this.fillWithMetadataBlocks(world, sbb, 12, 10, 12, 24, 10, 24, fieldBlock, fieldMeta, fieldBlock, fieldMeta, false);
    		
    		// doors in dungeon force field
    		this.fillWithMetadataBlocks(world, sbb, 17, 1, 12, 19, 4, 12, TFBlocks.castleDoor, 2, Blocks.AIR, 0, false);
    		this.fillWithMetadataBlocks(world, sbb, 17, 1, 24, 19, 4, 24, TFBlocks.castleDoor, 2, Blocks.AIR, 0, false);
    		
    		// stairs to stair towers
    		makeSmallTowerStairs(world, sbb, 0);
    		makeSmallTowerStairs(world, sbb, 1);
    		makeSmallTowerStairs(world, sbb, 3);
    		makeLargeTowerStairs(world, sbb, 2);
    		
    		// door, first floor
    		this.fillWithMetadataBlocks(world, sbb, 48, 1, 23, 48, 4, 25, TFBlocks.castleDoor, 0, Blocks.AIR, 0, false);
    		
    		// door, second floor
    		this.fillWithMetadataBlocks(world, sbb, 0, 31, 23, 0, 34, 25, TFBlocks.castleDoor, 1, Blocks.AIR, 0, false);
            
    		return true;
    	}

		private void makeSmallTowerStairs(World world, StructureBoundingBox sbb, int rotation) {
			for (int y = 1; y < 4; y++) {
    			int z = 40 + y;
				this.fillBlocksRotated(world, sbb, 1, 1, z, 4, y, z, deco.blockID, deco.blockMeta, rotation);
    			this.fillBlocksRotated(world, sbb, 2, y, z, 3, y, z, deco.stairID, getStairMeta(1 + rotation), rotation);
    		}
		}

		private void makeLargeTowerStairs(World world, StructureBoundingBox sbb, int rotation) {
			for (int y = 1; y < 4; y++) {
    			int z = 38 + y;
				this.fillBlocksRotated(world, sbb, 2, 1, z, 6, y, z, deco.blockID, deco.blockMeta, rotation);
    			this.fillBlocksRotated(world, sbb, 3, y, z, 5, y, z, deco.stairID, getStairMeta(1 + rotation), rotation);
    		}
		}

		private void makeMezzTopStairs(World world, StructureBoundingBox sbb, int y, int z, int stairMeta) {
			this.fillWithMetadataBlocks(world, sbb, 38, y, z, 46, y, z, deco.stairID, getStairMeta(stairMeta), deco.stairID, getStairMeta(stairMeta), false);
			this.fillWithMetadataBlocks(world, sbb, 38, y - 1, z, 46, y - 1, z, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
			this.fillWithAir(world, sbb, 38, y + 1, z, 46, y + 3, z);
		}

		private void makeHalfPillarBase(World world, StructureBoundingBox sbb, int rotation, int y, int z, int metaBit) {
			this.fillBlocksRotated(world, sbb, 2, y, z - 1, 2, y, z + 3, deco.stairID, getStairMeta(2 + rotation) | metaBit, rotation);
			this.placeBlockRotated(world, deco.stairID, getStairMeta(1 + rotation) | metaBit, 1, y, z - 1, rotation, sbb);
			this.placeBlockRotated(world, deco.stairID, getStairMeta(3 + rotation) | metaBit, 1, y, z + 3, rotation, sbb);
		}

		private void makePillarBase(World world, StructureBoundingBox sbb, int x, int z, int y, int metaBit) {
			this.fillWithMetadataBlocks(world, sbb, x + 0, y, z + 3, x + 3, y, z + 3, deco.stairID, getStairMeta(3) | metaBit, Blocks.AIR, 0, false);
			this.fillWithMetadataBlocks(world, sbb, x - 1, y, z - 1, x + 2, y, z - 1, deco.stairID, getStairMeta(1) | metaBit, Blocks.AIR, 0, false);

			this.fillWithMetadataBlocks(world, sbb, x + 3, y, z - 1, x + 3, y, z + 2, deco.stairID, getStairMeta(2) | metaBit, Blocks.AIR, 0, false);
			this.fillWithMetadataBlocks(world, sbb, x - 1, y, z + 0, x - 1, y, z + 3, deco.stairID, getStairMeta(0) | metaBit, Blocks.AIR, 0, false);
		}

    }
    
    public static class Roof48Crenellated extends StructureTFComponent {
		public Roof48Crenellated() {}
		
		public Roof48Crenellated(Random rand, int i, StructureTFComponent keep) {
			super(i);
			
			int height = 5;
			
			this.setCoordBaseMode(keep.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(keep.getBoundingBox().minX - 2, keep.getBoundingBox().maxY - 1, keep.getBoundingBox().minZ - 2, keep.getBoundingBox().maxX + 2, keep.getBoundingBox().maxY + height - 1, keep.getBoundingBox().maxZ + 2);

		}

    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}
    	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			// add second layer of floor
			this.fillWithMetadataBlocks(world, sbb, 2, 2, 2, 50, 2, 50, TFBlocks.castleMagic, 3, TFBlocks.castleMagic, 3, false);
			
    		// crenellations
            for (int rotation = 0; rotation < 4; rotation++) {
	    		this.fillBlocksRotated(world, sbb, 3, 1, 1, 45, 3, 1, deco.blockID, deco.blockMeta, rotation);
	    		
	    		for (int i = 10; i < 41; i += 5) {
		    		this.fillBlocksRotated(world, sbb, i, 1, 0, i + 2, 5, 2, deco.blockID, deco.blockMeta, rotation);
		    		this.placeBlockRotated(world, deco.blockID, deco.blockMeta, i + 1, 0, 1, rotation, sbb);
	    		}
            }
			
			return true;
		}
	
	}

	public static class BossGazebo extends StructureTFComponent {
		
		public BossGazebo() {
		}
		
		public BossGazebo(Random rand, int i, StructureTFComponent keep) {
			super(i);
			this.spawnListIndex = -1; // no monsters
			
			this.setCoordBaseMode(keep.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(keep.getBoundingBox().minX + 14, keep.getBoundingBox().maxY + 2, keep.getBoundingBox().minZ + 14, keep.getBoundingBox().maxX - 14, keep.getBoundingBox().maxY + 13, keep.getBoundingBox().maxZ - 14);
	
		}
		
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		this.deco = new StructureTFDecoratorCastle();
    		this.deco.blockID = TFBlocks.castleMagic;
    		this.deco.blockMeta = 1;
    		
    		this.deco.fenceID = TFBlocks.forceField;
    		this.deco.fenceMeta = 10;
    	}

	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			// walls
			for (int rotation = 0; rotation < 4; rotation++) {
				this.fillBlocksRotated(world, sbb, 0, 0, 0, 0, 10, 20, deco.fenceID, deco.fenceMeta, rotation);
			}
			
			// roof
			this.fillWithMetadataBlocks(world, sbb, 0, 11, 0, 20, 11, 20, deco.fenceID, deco.fenceMeta, deco.fenceID, deco.fenceMeta, false);
			
	        this.placeSignAtCurrentPosition(world, 10, 0, 10, "Final Boss Here", "You win!", sbb);

			
			return true;
		}

	
	}

	public static class Foundation48 extends StructureTFComponent {
		private int groundLevel = -1;
		
		public Foundation48() {
		}
		
		public Foundation48(Random rand, int i, StructureTFComponent sideTower) {
			super(i);
			
			this.setCoordBaseMode(sideTower.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX, sideTower.getBoundingBox().minY, sideTower.getBoundingBox().minZ, sideTower.getBoundingBox().maxX, sideTower.getBoundingBox().minY - 1, sideTower.getBoundingBox().maxZ);
	
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void buildComponent(StructureComponent parent, List list, Random rand) {
			if (parent != null && parent instanceof StructureTFComponent) {
				this.deco = ((StructureTFComponent)parent).deco;
			}
		}
	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
    		// foundation
			for (int x = 4; x < 45; x++)
			{
				for (int z = 4; z < 45; z++)
				{
					this.func_151554_b(world, deco.blockID, deco.blockMeta, x, -1, z, sbb);
				}

			}
    		
			int mid = 16;
            for (int rotation = 0; rotation < 4; rotation++) {
	            // do corner
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 3, -2, 3, rotation, sbb);

				// directly under castle
	    		this.fillBlocksRotated(world, sbb, 2, -2, 1, 46, -1, 1, deco.blockID, deco.blockMeta, rotation);
	    		this.fillBlocksRotated(world, sbb, 2, -4, 2, 45, -1, 2, deco.blockID, deco.blockMeta, rotation);
	    		this.fillBlocksRotated(world, sbb, 4, -6, 3, 44, -1, 3, deco.blockID, deco.blockMeta, rotation);
	    		
	    		// pilings
	    		for (int i = 9; i < 45; i += 6) {
					makePiling(world, sbb, mid, rotation, i);
	    		}
	    		
				makePiling(world, sbb, mid, rotation, 4);
				makePiling(world, sbb, mid, rotation, 44);

            }
            
            // add supports for entrance bridge
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 21, -2, 0, 1, sbb);
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 21, -4, 1, 1, sbb);
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 21, -6, 2, 1, sbb);
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 27, -2, 0, 1, sbb);
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 27, -4, 1, 1, sbb);
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 27, -6, 2, 1, sbb);

	        return true;
		}

		private void makePiling(World world, StructureBoundingBox sbb, int mid, int rotation, int i) {
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, i, -7, 3, rotation, sbb);
			this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, i, -mid, 2, rotation, sbb);
			
			this.placeBlockRotated(world, deco.blockID, deco.blockMeta, i, -1, 0, rotation, sbb);
			this.placeBlockRotated(world, deco.blockID, deco.blockMeta, i, -3, 1, rotation, sbb);
			this.placeBlockRotated(world, deco.blockID, deco.blockMeta, i, -5, 2, rotation, sbb);
		}	

	}

	public static class DungeonSteps extends StructureTFComponent {
    	public DungeonSteps() { }
		
    	public DungeonSteps(Random rand, int i, int x, int y, int z, int rotation) {
			this.spawnListIndex = 2; // dungeon monsters
    		
    		this.setCoordBaseMode(rotation);
    		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, -2, -15, -3, 5, 15, 20, rotation);
    	}
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}
    	
    	/**
    	 * build more steps towards the specified direction
    	 */
    	public DungeonSteps buildMoreStepsTowards(StructureComponent parent, List list, Random rand, int rotation) {
    		
    		int direction = (rotation + this.coordBaseMode) % 4;
    		
    		int sx = 2;
    		int sy = 0;
    		int sz = 17;
    		
			switch (rotation) {
			case 0:
				sz -= 5;
				break;
			case 1:
				sx -= 5;
				break;
			case 2:
				sz += 5;
				break;
			case 3:
				sx += 6;
				break;
			}    		
    		
    		// find center of landing
    		int dx = this.getXWithOffset(sx, sz);
    		int dy = this.getYWithOffset(sy);
    		int dz = this.getZWithOffset(sx, sz);

			
			// build a new stairway there
    		DungeonSteps steps = new DungeonSteps(rand, this.componentType + 1, dx, dy, dz, direction);
    		list.add(steps);
    		steps.buildComponent(this, list, rand);
    		
			return steps;
    	}
    	
    	/**
    	 * build a new level under the exit
    	 */
    	public DungeonEntrance buildLevelUnder(StructureComponent parent, List list, Random rand, int level) {
    		// find center of landing
    		int dx = this.getXWithOffset(2, 19);
    		int dy = this.getYWithOffset(-7);
    		int dz = this.getZWithOffset(2, 19);
    		
			// build a new dungeon level under there
    		DungeonEntrance room = new DungeonEntrance(rand, 8, dx, dy, dz, this.coordBaseMode, level);
    		list.add(room);
    		room.buildComponent(this, list, rand);
    		
			return room;
    	}
    	
    	/**
    	 * build the boss room
    	 */
    	public DungeonForgeRoom buildBossRoomUnder(StructureComponent parent, List list, Random rand) {
    		// find center of landing
    		int dx = this.getXWithOffset(2, 19);
    		int dy = this.getYWithOffset(-31);
    		int dz = this.getZWithOffset(2, 19);
    		
			// build a new dungeon level under there
    		DungeonForgeRoom room = new DungeonForgeRoom(rand, 8, dx, dy, dz, this.coordBaseMode);
    		list.add(room);
    		room.buildComponent(this, list, rand);
    		
    		//System.out.println("Made dungeon boss room at " + dx + ", " + dy + ", " + dz + ".");
    		
			return room;
    	}
    		

	
    	@Override
    	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
    		for (int z = 0; z < 15; z++) {
    			int y = 14 - z;
    			this.fillWithMetadataBlocks(world, sbb, 0, y, z, 4, y, z, deco.stairID, getStairMeta(3), deco.stairID, getStairMeta(3), false);
    			this.fillWithAir(world, sbb, 0, y + 1, z, 4, y + 6, z);
    		}
    		this.fillWithAir(world, sbb, 0, 0, 15, 4, 5, 19);

			return true;
		}
	
	}

	public static class DungeonEntrance extends DungeonRoom31  {
		public boolean hasExit = false;
		
		public DungeonEntrance() {
		}

		public DungeonEntrance(Random rand, int i, int x, int y, int z, int direction, int level) {
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
				this.fillWithMetadataBlocks(world, sbb, x, y, z, x + 4, y, z, deco.stairID, getStairMeta(3), deco.stairID, getStairMeta(3), false);
				this.fillWithMetadataBlocks(world, sbb, x, 0, z, x + 4, y - 1, z, TFBlocks.deadrock, 0, TFBlocks.deadrock, 0, false);
			}
			
			// door
    		this.fillWithMetadataBlocks(world, sbb, 23, 0, 12, 23, 3, 14, TFBlocks.castleDoor, 2, Blocks.AIR, 0, false);
    		this.fillWithMetadataBlocks(world, sbb, 23, 4, 12, 23, 4, 14, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);

			
			return true;
		}

		protected int getForceFieldMeta(Random decoRNG) {
			return 1; 
		}

		protected int getRuneMeta(int fieldMeta) {
			return 0;
		}
	}

	public static class DungeonExit extends DungeonRoom31  {
		public DungeonExit() {
		}

		public DungeonExit(Random rand, int i, int x, int y, int z, int direction, int level) {
			super(rand, i, x, y, z, direction, level);
		}
		
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		
    		// no need for additional rooms, we're along the outside anyways
    		
    		// add stairway down
    		int bestDir = this.findStairDirectionTowards(parent.getBoundingBox().minX, parent.getBoundingBox().minZ);
    		
    		DungeonSteps steps0 = new DungeonSteps(rand, 5, boundingBox.minX + 15, boundingBox.minY + 0, boundingBox.minZ + 15, bestDir);
    		list.add(steps0);
    		steps0.buildComponent(this, list, rand);

    		// another level!?
    		if (this.level == 1) {
    			steps0.buildLevelUnder(parent, list, rand, this.level + 1);
    		} else {
    			steps0.buildBossRoomUnder(parent, list, rand);
    		}
    	}
		

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			super.addComponentParts(world, rand, sbb);
			
			// door
    		this.fillWithMetadataBlocks(world, sbb, 7, 0, 16, 7, 3, 18, TFBlocks.castleDoor, 2, Blocks.AIR, 0, false);
    		this.fillWithMetadataBlocks(world, sbb, 7, 4, 16, 7, 4, 18, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);

			
			return true;
		}
		

		public int findStairDirectionTowards(int x, int z) {
			// center of component
			int cx = this.boundingBox.getCenterX();
			int cz = this.boundingBox.getCenterZ();
			
			// difference
			int dx = cx - x;
			int dz = cz - z;
			
			int absoluteDir;
			if (Math.abs(dz) >= Math.abs(dx)) {
				absoluteDir = (dz >= 0) ? 2 : 0;
			} else {
				absoluteDir = (dx >= 0) ? 3 : 1;
			}

			return absoluteDir;
		}
		
		protected int getForceFieldMeta(Random decoRNG) {
			return 1; 
		}

		protected int getRuneMeta(int fieldMeta) {
			return 0;
		}
	}

	public static class DungeonRoom31 extends ComponentTFTowerWing  {
		public int level; // this is not serialized, since it's only used during build, which should be all one step

		public DungeonRoom31() {
		}

		public DungeonRoom31(Random rand, int i, int x, int y, int z, int direction, int level) {
			super(i);
			this.setCoordBaseMode(direction);
			this.spawnListIndex = 2; // dungeon monsters
			this.size = 31;
			this.height = 7;
			this.level = level;
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -15, 0, -15, this.size - 1, this.height - 1, this.size - 1, 0);
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
			if (parent instanceof DungeonEntrance) {
				return ((DungeonEntrance)parent).hasExit;
			} else {
				return false;
			}
		}

		private void setExitBuiltForLevel(StructureComponent parent, boolean exit) {
			if (parent instanceof DungeonEntrance) {
				((DungeonEntrance)parent).hasExit = exit;
			} else {}
		}

		protected boolean addDungeonRoom(StructureComponent parent, List list, Random rand, int rotation, int level) {
			rotation = (this.coordBaseMode + rotation) % 4;
			
			ChunkCoordinates rc = this.getNewRoomCoords(rand, rotation);
			
			DungeonRoom31 dRoom = new DungeonRoom31(rand, this.componentType + 1, rc.posX, rc.posY, rc.posZ, rotation, level);
			
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
			ChunkCoordinates rc = this.getNewRoomCoords(rand, rotation);
			DungeonExit dRoom = new DungeonExit(rand, this.componentType + 1, rc.posX, rc.posY, rc.posZ, rotation, this.level);
			StructureComponent intersect = StructureTFComponent.findIntersectingExcluding(list, dRoom.getBoundingBox(), this);
			if (intersect == null) {
				list.add(dRoom);
				dRoom.buildComponent(this, list, rand);
				return true;
			} else {
				return false;
			}
		}

		private ChunkCoordinates getNewRoomCoords(Random rand, int rotation) {
			// make the rooms connect around the corners, not the centers
			int offset = rand.nextInt(15) - 9;
			if (rand.nextBoolean()) {
				offset += this.size;
			}
			
			switch (rotation) {
			default:
			case 0:
				return new ChunkCoordinates(this.boundingBox.maxX + 9, this.boundingBox.minY, this.boundingBox.minZ + offset);
			case 1:
				return new ChunkCoordinates(this.boundingBox.minX + offset, this.boundingBox.minY, this.boundingBox.maxZ + 9);
			case 2:
				return new ChunkCoordinates(this.boundingBox.minX - 9, this.boundingBox.minY, this.boundingBox.minZ + offset);
			case 3:
				return new ChunkCoordinates(this.boundingBox.minX + offset, this.boundingBox.minY, this.boundingBox.minZ - 9);
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
	        		if (world.getBiomeGenForCoords(x, z) != TFBiomeBase.highlandsCenter && world.getBiomeGenForCoords(x, z) != TFBiomeBase.thornlands) {
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
	
	public static class DungeonForgeRoom extends StructureTFComponent  {
		public DungeonForgeRoom() {
		}

		public DungeonForgeRoom(Random rand, int i, int x, int y, int z, int direction) {
			super(i);
			this.spawnListIndex = 3; // forge monsters
			this.setCoordBaseMode(direction);
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, -15, 0, -15, 50, 30, 50, direction);
		}


		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			this.fillWithAir(world, sbb, 0, 0, 0, 50, 30, 50);

	        // sign
	        this.placeSignAtCurrentPosition(world, 25, 0, 25, "Mini-boss 2", "Gives talisman", sbb);
			
			return true;
		}

	}


	public static class StairTower extends ComponentTFTowerWing {

    	public StairTower() { }
    	
    	public StairTower(Random rand, int i, int x, int y, int z, int rotation) {
    		this.setCoordBaseMode(rotation);
    		this.size = 9;
    		this.height = 51;
    		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -4, 0, -4, 8, 50, 8, 0);

    	}
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		// add crown
    		Roof9Crenellated roof = new Roof9Crenellated(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    	}

    	@Override
    	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

    		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, 8, 49, 8, false, rand, deco.randomBlocks);
	        
	        // add branching runes
			int numBranches = 6 + decoRNG.nextInt(4);
			for (int i = 0; i < numBranches; i++) {
				makeGlyphBranches(world, decoRNG, this.getGlyphMeta(), sbb);
			}
    		
    		// beard
    		for (int i = 1; i < 4; i++) {
        		fillWithRandomizedBlocks(world, sbb, i, 0 - (i * 2), i, 8 - i, 1 - (i * 2), 8 - i, false, rand, deco.randomBlocks);
    		}
    		this.placeBlockAtCurrentPosition(world, deco.blockID, deco.blockMeta, 4, -7, 4, sbb);
    		
    		
    		// door, first floor
    		this.fillWithMetadataBlocks(world, sbb, 0, 1, 1, 0, 3, 2, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR, 0, false);
    		
    		// stairs
    		for (int f = 0; f < 5; f++) {
    			int rotation = (f + 2) % 4;
    			int y = f * 3 + 1;
    			for (int i = 0; i < 3; i++) {
    				int sx = 3 + i;
    				int sy = y + i;
    				int sz = 1;
    				
    				this.placeBlockRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz, rotation, sbb);
    				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz, rotation, sbb);
    				this.placeBlockRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz + 1, rotation, sbb);
    				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz + 1, rotation, sbb);
    			}
    			// landing
    			this.fillBlocksRotated(world, sbb, 6, y + 2, 1, 7, y + 2, 2, deco.blockID, deco.blockMeta, rotation);
    		}
    		
    		// door, second floor
    		this.fillWithMetadataBlocks(world, sbb, 1, 18, 0, 2, 20, 0, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR, 0, false);
    		
    		// second floor landing
    		this.fillWithMetadataBlocks(world, sbb, 1, 17, 1, 3, 17, 3, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
    		this.fillWithMetadataBlocks(world, sbb, 1, 17, 4, 2, 17, 4, deco.stairID, getStairMeta(3), deco.stairID, getStairMeta(3), false);
    		this.fillWithMetadataBlocks(world, sbb, 1, 16, 4, 2, 16, 4, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
    		this.fillWithMetadataBlocks(world, sbb, 1, 16, 5, 2, 16, 5, deco.stairID, getStairMeta(3), deco.stairID, getStairMeta(3), false);
    		this.fillWithMetadataBlocks(world, sbb, 1, 15, 5, 2, 15, 5, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);

    		// door, roof
    		this.fillWithMetadataBlocks(world, sbb, 1, 39, 0, 2, 41, 0, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR, 0, false);

    		// stairs
    		for (int f = 0; f < 7; f++) {
    			int rotation = (f + 0) % 4;
    			int y = f * 3 + 18;
    			for (int i = 0; i < 3; i++) {
    				int sx = 3 + i;
    				int sy = y + i;
    				int sz = 1;
    				
    				this.placeBlockRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz, rotation, sbb);
    				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz, rotation, sbb);
    				this.placeBlockRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz + 1, rotation, sbb);
    				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz + 1, rotation, sbb);
    			}
    			// landing
    			this.fillBlocksRotated(world, sbb, 6, y + 2, 1, 7, y + 2, 2, deco.blockID, deco.blockMeta, rotation);
    		}

    		// roof access landing
    		this.fillWithMetadataBlocks(world, sbb, 1, 38, 1, 3, 38, 5, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
    		this.fillWithMetadataBlocks(world, sbb, 3, 39, 1, 3, 39, 5, deco.fenceID, deco.fenceMeta, deco.fenceID, deco.fenceMeta, false);

    		
    		return true;
    	}
    	

		public int getGlyphMeta() {
			return 1;
		}


    }
    
    public static class LargeTower extends ComponentTFTowerWing {

    	public LargeTower() {
    	}
    	
    	public LargeTower(Random rand, int i, int x, int y, int z, int rotation) {
    		this.setCoordBaseMode(rotation);
    		this.size = 13;
    		this.height = 61;
    		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -6, 0, -6, 12, 60, 12, 0);

    	}
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		// add crown
    		Roof9Crenellated roof = new Roof9Crenellated(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    	}

    	@Override
    	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

			fillWithRandomizedBlocks(world, sbb, 0, 0, 0, 12, 59, 12, false, rand, deco.randomBlocks);
	        
	        // add branching runes
			int numBranches = 6 + decoRNG.nextInt(4);
			for (int i = 0; i < numBranches; i++) {
				makeGlyphBranches(world, decoRNG, this.getGlyphMeta(), sbb);
			}			
    		
    		// beard
    		for (int i = 1; i < 4; i++) {
        		fillWithRandomizedBlocks(world, sbb, i, 0 - (i * 2), i, 8 - i, 1 - (i * 2), 8 - i, false, rand, deco.randomBlocks);
    		}
    		this.placeBlockAtCurrentPosition(world, deco.blockID, deco.blockMeta, 4, -7, 4, sbb);
    		
    		// door, first floor
    		this.fillWithMetadataBlocks(world, sbb, 0, 1, 1, 0, 4, 3, TFBlocks.castleDoor, 0, Blocks.AIR, this.getGlyphMeta(), false);

	        this.placeSignAtCurrentPosition(world, 6, 1, 6, "Parkour area 1", "Unique monster?", sbb);
    		
    		return true;
    	}
    	

		public int getGlyphMeta() {
			return 0;
		}


	}

	public static class Mural extends StructureTFComponent {
		
		private int height;
		private int width;
		
		// we will model the mural in this byte array
		private byte[][] mural;
		
		public Mural() {}
	
		public Mural(Random rand, int i, int x, int y, int z, int width, int height, int direction) {
			super(i);
			this.setCoordBaseMode(direction);
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, 0, -height / 2, -width / 2, 1, height - 1, width - 1, direction);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			this.height = this.boundingBox.getYSize();
			this.width = (this.coordBaseMode == 0 || this.coordBaseMode == 2) ? this.boundingBox.getZSize() :  this.boundingBox.getXSize();
			
			Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
			
			if (mural == null) {
				// only make it once
				mural = new byte[width][height];

				int startX = width / 2 - 1;
				int startY = 2;

				// make mural, fill in dot by start
				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						mural[startX + x][startY + y] = 1;
					}
				}

				// side branches
				makeHorizontalTree(decoRNG, mural, startX + 1, startY, decoRNG.nextInt(width / 6) + width / 6, true);
				makeHorizontalTree(decoRNG, mural, startX - 1, startY, decoRNG.nextInt(width / 6) + width / 6, false);

				// main tree
				makeVerticalTree(decoRNG, mural, startX, startY + 1, decoRNG.nextInt(height / 6) + height / 6, true);
				
				// stripes
				makeStripes(decoRNG, mural);
			}

			// copy mural to world
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (mural[x][y] > 0) {
						this.placeBlockAtCurrentPosition(world, TFBlocks.castleMagic, 1, 0, y, x, sbb);
					} else {
						//this.placeBlockAtCurrentPosition(world, TFBlocks.forceField, 0, 0, y, x, sbb);
					}
				}
			}
			
			return true;
		}

		private void makeHorizontalTree(Random decoRNG, byte[][] mural, int centerX, int centerY, int branchLength, boolean positive) {
			this.fillHorizontalLine(mural, centerX, centerY, branchLength, positive);
			
			this.makeHorizontalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, true);
			this.makeHorizontalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, false);
		}

		private void makeVerticalTree(Random decoRNG, byte[][] mural, int centerX, int centerY, int branchLength, boolean positive) {
			this.fillVerticalLine(mural, centerX, centerY, branchLength, positive);
			
			this.makeVerticalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, true);
			this.makeVerticalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, false);
		}

		private boolean makeHorizontalBranch(byte[][] mural, Random rand, int sx, int sy, int length, boolean plusX, boolean plusY) {
			int downLine = (length / 2) + 1 + rand.nextInt(Math.max(length / 2, 2));
			int branchLength = rand.nextInt(width / 8) + width / 8;

			// check if the area is clear
			boolean clear = true;
			for (int i = 0; i <= branchLength; i++) {
				int cx = sx + (plusX ? downLine - 1 + i : -(downLine - 1 + i));
				int cy = sy + (plusY ? 2 : -2);
				if (cx < 0 || cx >= width || cy < 0 || cy >= height || mural[cx][cy] > 0) {
					clear = false;
					break;
				}
			}
			if (clear) {
				int bx = sx + (plusX ? downLine : -downLine);
				int by = sy;

				// jag
				this.fillVerticalLine(mural, bx, by, 1, plusY);

				by += (plusY ? 2 : -2);

				this.fillHorizontalLine(mural, bx, by, branchLength, plusX);

				// recurse?
				if (bx > 0 && bx < width && by > 0 && by < height) {
					if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true)) {
						if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true)) {
							if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true)) {
								this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true);
							}
						}
					}
					if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false)) {
						if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false)) {
							if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false)) {
								this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false);
							}
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		private boolean makeVerticalBranch(byte[][] mural, Random rand, int sx, int sy, int length, boolean plusY, boolean plusX) {
			int downLine = (length / 2) + 1 + rand.nextInt(Math.max(length / 2, 2));
			int branchLength = rand.nextInt(height / 8) + height / 8;

			// check if the area is clear
			boolean clear = true;
			for (int i = 0; i <= branchLength; i++) {
				int cx = sx + (plusX ? 2 : -2);
				int cy = sy + (plusY ? downLine - 1 + i : -(downLine - 1 + i));
				if (cx < 0 || cx >= width || cy < 0 || cy >= height || mural[cx][cy] > 0) {
					clear = false;
					break;
				}
			}
			if (clear) {
				int bx = sx;
				int by = sy + (plusY ? downLine : -downLine);;

				// jag
				this.fillHorizontalLine(mural, bx, by, 1, plusX);

				bx += (plusX ? 2 : -2);

				this.fillVerticalLine(mural, bx, by, branchLength, plusY);

				// recurse?
				if (bx > 0 && bx < width && by > 0 && by < height) {
					if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true)) {
						if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true)) {
							if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true)) {
								this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true);
							}
						}
					}
					if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false)) {
						if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false)) {
							if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false)) {
								this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false);
							}
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		private void fillHorizontalLine(byte[][] mural, int sx, int sy, int length, boolean positive) {
			int x = sx;
			int y = sy;

			for (int i = 0; i <= length; i++) {
				if (x >= 0 && x < width && y >= 0 && y < height) {
					mural[x][y] = (byte) (positive ? 1 : 4);

					x += positive ? 1 : -1;
				}
			}
		}

		private void fillVerticalLine(byte[][] mural, int sx, int sy, int length, boolean positive) {
			int x = sx;
			int y = sy;

			for (int i = 0; i <= length; i++) {
				if (x >= 0 && x < width && y >= 0 && y < height) {
					mural[x][y] = (byte) (positive ? 2 : 3);

					y += positive ? 1 : -1;
				}
			}
		}

		private void makeStripes(Random decoRNG, byte[][] mural2) {
			// stagger slightly on our way down
			for (int y = this.height - 2; y > this.height / 3; y -= (2 + decoRNG.nextInt(2))) {
				makeSingleStripe(mural2, y);
			}
		}

		private void makeSingleStripe(byte[][] mural2, int y) {
			for (int x = 0; x < this.width - 2; x++) {
				if (mural[x + 1][y] == 0 && mural[x + 1][y + 1] == 0) {
					mural[x][y] = 1;
				} else {
					break;
				}
			}
			for (int x = this.width - 1; x > 2; x--) {
				if (mural[x - 1][y] == 0 && mural[x - 1][y + 1] == 0) {
					mural[x][y] = 1;
				} else {
					break;
				}
			}
		}

	}

	public static class MazeTower13 extends ComponentTFTowerWing {
    	
    	public static final int LOWEST_DOOR = 144;
    	public static final int HIGHEST_DOOR = 222;
    	
    	public int type;
    	
		public MazeTower13() {
		}

		public MazeTower13(Random rand, int i, int x, int y, int z, int type, int direction) {
			super(i);
			this.setCoordBaseMode(direction);
			this.type = type;
			this.size = 13;
			
			// decide a number of floors, 2-5
			int floors = rand.nextInt(3) + 2;
			
			this.height = floors * 8 + 1;
			
			// entrance should be on a random floor
			int entranceFloor = rand.nextInt(floors);
			
			// rationalize entrance floor if the tower is going to be too low, put the entrance floor at bottom.  Too high, put it at top
			if ((y - (entranceFloor * 8)) < LOWEST_DOOR) {
				//System.out.println("Tower at " + x + ", " + z + " is getting too low, setting entrance to bottom floor.");
				entranceFloor = 0;
			}
			if ((y + ((floors - entranceFloor) * 8)) > HIGHEST_DOOR) {
				//System.out.println("Tower at " + x + ", " + z + " is getting too high, setting entrance to floor " +  (floors - 1) + ".");
				entranceFloor = floors - 1;
			}
			
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -6, 0 - (entranceFloor * 8), -6, this.size - 1, this.height, this.size - 1, 0);
    		
    		// we should have a door where we started
    		addOpening(0, entranceFloor * 8 + 1, size / 2, 2);
    		
		}
		
		public MazeTower13(Random rand, int i, int x, int y, int z, int floors, int entranceFloor, int type, int direction) {
			super(i);
			this.setCoordBaseMode(direction);
			this.type = type;
			this.size = 13;
			this.height = floors * 8 + 1;
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -6, 0 - (entranceFloor * 8), -6, this.size - 1, this.height, this.size - 1, 0);
    		addOpening(0, entranceFloor * 8 + 1, size / 2, 2);		
    	}

		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}

    		// add foundation
    		Foundation13 foundation = new Foundation13(rand, 4, this);
    		list.add(foundation);
    		foundation.buildComponent(this, list, rand);
    		
    		// add roof
    		StructureTFComponent roof = rand.nextBoolean() ? new Roof13Conical(rand, 4, this) :  new Roof13Crenellated(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    	}
	
    	/**
    	 * Build more components towards the destination
    	 */
    	public void buildTowards(StructureComponent parent, List list, Random rand, ChunkCoordinates dest) {
    		// regular building first, adds roof/foundation
    		this.buildComponent(parent, list, rand);

    		if (this.getComponentType() < 15) {

    			// are we there?
    			if (this.isWithinRange(dest.posX, dest.posZ, this.boundingBox.minX + 6, this.boundingBox.minZ + 6, 30)) {
    				//System.out.println("We are within range of our destination, building final tower");
    				int howFar = 20;
    				if (!buildEndTowerTowards(list, rand, dest, this.findBestDirectionTowards(dest), howFar)) {
        				if (!buildEndTowerTowards(list, rand, dest, this.findSecondDirectionTowards(dest), howFar)) {
            				if (!buildEndTowerTowards(list, rand, dest, this.findThirdDirectionTowards(dest), howFar)) {
                				//System.out.println("Cound not build final tower");
            				}
        				}
    				}
    			} else {

    				int howFar = 14 + rand.nextInt(24);
    				int direction = this.findBestDirectionTowards(dest);

    				// build left or right, not straight if we can help it
    				if (direction == 0 || !buildContinueTowerTowards(list, rand, dest, direction, howFar)) {
    					direction = this.findSecondDirectionTowards(dest);
    					if (direction == 0 || !buildContinueTowerTowards(list, rand, dest, direction, howFar)) {
    						direction = this.findThirdDirectionTowards(dest);
    						if (direction == 0 || !buildContinueTowerTowards(list, rand, dest, direction, howFar)) {
    							// fine, just go straight
    							if (!buildContinueTowerTowards(list, rand, dest, 0, howFar)) {
    								//System.out.println("Could not build tower randomly");
    							}
    						}
    					}
    				}
    			}
    		}
    		
    		// finally, now that the critical path is built, let's add some other towers for atmosphere and complication
    		this.buildNonCriticalTowers(parent, list, rand);

    	}

		protected void buildNonCriticalTowers(StructureComponent parent, List list, Random rand) {
			// pick a random direction
			int dir = rand.nextInt(4);

			// if there isn't something in that direction, check if we can add a wrecked tower
			if (this.openingTowards[dir] == false) {
				if (!buildDamagedTower(list, rand, dir)) {
					dir = (dir + rand.nextInt(4)) % 4;
					if (!buildDamagedTower(list, rand, dir)) {
					// maybe just a balcony?
					//buildBalconyTowards(list, rand, dir);
					}
				}
			}
			
			
		}

		private int findBestDirectionTowards(ChunkCoordinates dest) {
			
			// center of tower
			int cx = this.boundingBox.minX + 6;
			int cz = this.boundingBox.minZ + 6;
			
			// difference
			int dx = cx - dest.posX;
			int dz = cz - dest.posZ;
			
			int absoluteDir = 0;
			if (Math.abs(dx) > Math.abs(dz)) {
				absoluteDir = (dx >= 0) ? 2 : 0;
			} else {
				absoluteDir = (dz >= 0) ? 3 : 1;
			}
			
			int relativeDir = (absoluteDir + 4 - this.coordBaseMode) % 4;

			//System.out.println("Determining best direction!  center is at " + cx + ", " + cz + " and dest is at " + dest + " offset is " + dx + ", " + dz + " so the best absolute direction is " + absoluteDir + " and relative dir is " + relativeDir);

			// return relative dir
			return relativeDir;
		}
		
		private int findSecondDirectionTowards(ChunkCoordinates dest) {
			
			// center of tower
			int cx = this.boundingBox.minX + 6;
			int cz = this.boundingBox.minZ + 6;
			
			// difference
			int dx = cx - dest.posX;
			int dz = cz - dest.posZ;
			
			int absoluteDir = 0;
			if (Math.abs(dx) < Math.abs(dz)) {  // reversed from findBestDirectionTowards
				absoluteDir = (dx >= 0) ? 2 : 0;
			} else {
				absoluteDir = (dz >= 0) ? 3 : 1;
			}
			int relativeDir = (absoluteDir + 4 - this.coordBaseMode) % 4;

			
			//System.out.println("Determining second direction!  center is at " + cx + ", " + cz + " and dest is at " + dest + " offset is " + dx + ", " + dz + " so the best absolute direction is " + absoluteDir + " and relative dir is " + relativeDir);

			// return offset dir
			return relativeDir;
		}
		
		private int findThirdDirectionTowards(ChunkCoordinates dest) {
			int first = this.findBestDirectionTowards(dest);
			int second = this.findSecondDirectionTowards(dest);
			
			if (first == 0 && second == 1) {
				return 3;
			} else if (first == 1 && second == 3) {
				return 0;
			} else {
				return 1;
			}
		}

		private boolean buildContinueTowerTowards(List list, Random rand, ChunkCoordinates dest, int direction, int howFar) {
			ChunkCoordinates opening = this.getValidOpeningCC(rand, direction);
			
			// adjust opening towards dest.posY if we are getting close to dest
			int adjustmentRange = 60;
			if (this.isWithinRange(dest.posX, dest.posZ, this.boundingBox.minX + 6, this.boundingBox.minZ + 6, adjustmentRange)) {
				opening.posY = this.adjustOpening(opening.posY, dest);
			}
			
			//System.out.println("original direction is " + direction);

			
			direction += this.coordBaseMode;
			direction %= 4;
			
			// build towards
			ChunkCoordinates tc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, howFar, direction);

			//System.out.println("Our coord mode is " + this.getCoordBaseMode() + ", and direction is " + direction + ", so our door is going to be at " + opening + " and the new tower will appear at " + tc);
			
			// find start
			StructureComponent start = (StructureComponent)list.get(0);
			
			//System.out.println("Testing range, uncorrected center is at " + centerX + ", " + centerZ);
			
			int centerX = ((start.getBoundingBox().minX + 128) >> 8) << 8;
			int centerZ = ((start.getBoundingBox().minZ + 128) >> 8) << 8;
			
			if (isWithinRange(centerX, centerZ, tc.posX, tc.posZ, 128)) {
			
				MazeTower13 sTower = new MazeTower13(rand, this.getComponentType() + 1, tc.posX, tc.posY, tc.posZ, this.type, direction);
				
				StructureBoundingBox largerBB = new StructureBoundingBox(sTower.getBoundingBox());
				
				largerBB.minX -= 6;
				largerBB.minZ -= 6;
				largerBB.maxX += 6;
				largerBB.maxZ += 6;
				largerBB.minY = 0;
				largerBB.maxY = 255;
				
				StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);
	
				if (intersect == null) {
					//System.out.println("tower success!");
					list.add(sTower);
					sTower.buildTowards(this, list, rand, dest);
					
					// add bridge
					ChunkCoordinates bc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, 1, direction);
					Bridge bridge = new Bridge(this.getComponentType() + 1, bc.posX, bc.posY, bc.posZ, howFar - 7, direction);
					list.add(bridge);
					bridge.buildComponent(this, list, rand);
					
					// opening
		    		addOpening(opening.posX, opening.posY + 1, opening.posZ, direction);
					
					return true;
				} else {
					//System.out.println("tower blocked");
					return false;
				}
			} else {
				//System.out.println("tower out of range");
				return false;
			}
		}
		
		
		protected boolean buildDamagedTower(List list, Random rand, int direction) {
			ChunkCoordinates opening = this.getValidOpeningCC(rand, direction);
			
			direction += this.coordBaseMode;
			direction %= 4;
			
			int howFar = 14 + rand.nextInt(24);
			// build towards
			ChunkCoordinates tc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, howFar, direction);

			// what type of tower?
			MazeTower13 eTower = makeNewDamagedTower(rand, direction, tc);

			StructureBoundingBox largerBB = new StructureBoundingBox(eTower.getBoundingBox());

			largerBB.minX -= 6;
			largerBB.minZ -= 6;
			largerBB.maxX += 6;
			largerBB.maxZ += 6;

			StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

			if (intersect == null) {
				//System.out.println("wreck tower success!  tower is at " + tc.posX + ", " + tc.posY + ", " + tc.posZ);
				list.add(eTower);
				eTower.buildComponent(this, list, rand);
				// add bridge
				ChunkCoordinates bc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, 1, direction);
				Bridge bridge = new Bridge(this.getComponentType() + 1, bc.posX, bc.posY, bc.posZ, howFar - 7, direction);
				list.add(bridge);
				bridge.buildComponent(this, list, rand);
				
				// opening
	    		addOpening(opening.posX, opening.posY + 1, opening.posZ, direction);
				
				return true;
			} else {
				//System.out.println("damaged tower blocked");
				return false;
			}
		}

		protected MazeTower13 makeNewDamagedTower(Random rand, int direction, ChunkCoordinates tc) {
			return new DamagedTower(rand, this.getComponentType() + 1, tc.posX, tc.posY, tc.posZ, direction);
		}

		private int adjustOpening(int posY, ChunkCoordinates dest) {
			int openY = posY;
			
			int realOpeningY = this.getYWithOffset(openY);
			if (realOpeningY - dest.posY < 12) {
				// if it is too low, move it to the top floor
				openY = this.height - 9;
			} else if (dest.posY - realOpeningY < 12) {
				// if the opening is too high, move it to the bottom floor
				openY = 0;
			}
			
			return openY;
		}

		private boolean buildEndTowerTowards(List list, Random rand, ChunkCoordinates dest, int direction, int howFar) {
			ChunkCoordinates opening = this.getValidOpeningCC(rand, direction);
			
			// adjust opening towards dest.posY
			opening.posY = this.adjustOpening(opening.posY, dest);
			
			direction += this.coordBaseMode;
			direction %= 4;
			
			// build towards
			ChunkCoordinates tc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, howFar, direction);

			//System.out.println("Our coord mode is " + this.getCoordBaseMode() + ", and direction is " + direction + ", so our door is going to be at " + opening + " and the new tower will appear at " + tc);

			// what type of tower?
			MazeTower13 eTower;
			if (this.type == 0) {
				eTower = new EntranceTower(rand, this.getComponentType() + 1, tc.posX, tc.posY, tc.posZ, direction);
			} else {
				eTower = new BellTower21(rand, this.getComponentType() + 1, tc.posX, tc.posY, tc.posZ, direction);
			}

			StructureBoundingBox largerBB = new StructureBoundingBox(eTower.getBoundingBox());

			largerBB.minX -= 6;
			largerBB.minZ -= 6;
			largerBB.maxX += 6;
			largerBB.maxZ += 6;

			StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

			if (intersect == null) {
				//System.out.println("entrance tower success!  tower is at " + tc.posX + ", " + tc.posY + ", " + tc.posZ + " and dest is " + dest.posX + ", " + dest.posY + ", " + dest.posZ);
				list.add(eTower);
				eTower.buildComponent(this, list, rand);
				// add bridge
				ChunkCoordinates bc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, 1, direction);
				Bridge bridge = new Bridge(this.getComponentType() + 1, bc.posX, bc.posY, bc.posZ, howFar - 7, direction);
				list.add(bridge);
				bridge.buildComponent(this, list, rand);
				
				// opening
	    		addOpening(opening.posX, opening.posY + 1, opening.posZ, direction);
				
				return true;
			} else {
				//System.out.println("end tower blocked");
				return false;
			}

		}

		private boolean isWithinRange(int centerX, int centerZ, int posX, int posZ, int range) {
			boolean inRange = Math.abs(centerX - posX) < range && Math.abs(centerZ - posZ) < range;
			
			if (!inRange) {
//				System.out.println("Tested range, center is at " + centerX + ", " + centerZ + " and tower is " + posX + ", " + posZ + " so distance is " + Math.max(Math.abs(centerX - posX),  Math.abs(centerZ - posZ)));
			}
			
			return inRange;
		}

		/**
		 * Gets a random position in the specified direction that connects to a floor currently in the tower.
		 */
		public ChunkCoordinates getValidOpeningCC(Random rand, int direction) {
			int floors = (this.height / 8);
			
			// for directions 0 or 2, the wall lies along the z axis
			if (direction == 0 || direction == 2) {
				int rx = direction == 0 ? 12 : 0;
				int rz = 6;
				int ry = rand.nextInt(floors) * 8;
				
				return new ChunkCoordinates(rx, ry, rz);
			}
			
			// for directions 1 or 3, the wall lies along the x axis
			if (direction == 1 || direction == 3) {
				int rx = 6;
				int rz = direction == 1 ? 12 : 0;
				int ry = rand.nextInt(floors) * 8;
				
				return new ChunkCoordinates(rx, ry, rz);
			}
			
			
			return new ChunkCoordinates(0, 0, 0);
		}
		
		/**
		 * Provides coordinates to make a tower such that it will open into the parent tower at the provided coordinates.
		 */
		protected ChunkCoordinates offsetTowerCCoords(int x, int y, int z, int howFar, int direction) {
			
			int dx = getXWithOffset(x, z);
			int dy = getYWithOffset(y);
			int dz = getZWithOffset(x, z);
			
			switch (direction) {
			case 0:
				dx += howFar;
				break;
			case 1:
				dz += howFar;
				break;
			case 2:
				dx -= howFar;
				break;
			case 3:
				dz -= howFar;
				break;
			}
			
			// ugh?
			return new ChunkCoordinates(dx, dy, dz);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
			
			// walls
			fillWithRandomizedBlocks(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, false, rand, deco.randomBlocks);
	
			// stone to ground
			for (int x = 0; x < this.size; x++)
			{
				for (int z = 0; z < this.size; z++)
				{
					this.func_151554_b(world, deco.blockID, deco.blockMeta, x, -1, z, sbb);
				}

			}
	        
	        // add branching runes
			int numBranches = 2 + decoRNG.nextInt(4) +  + decoRNG.nextInt(3);
			for (int i = 0; i < numBranches; i++) {
				makeGlyphBranches(world, decoRNG, this.getGlyphMeta(), sbb);
			}
			
			// floors
			addFloors(world, decoRNG, sbb);
			
	        // openings
	        makeOpenings(world, sbb);


			return true;
		}

		public int getGlyphMeta() {
			return type;
		}

		private void addFloors(World world, Random rand, StructureBoundingBox sbb) {
			// only add floors up to highest opening
			int floors = (this.highestOpening / 8) + 1;
			
			for (int i = 1; i < floors; i++) {
				this.fillWithMetadataBlocks(world, sbb, 1, i * 8, 1, 11, i * 8, 11, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
				
				// stairs
				addStairsDown(world, sbb, (i + 2) % 4, i * 8);
			}
			
			if (hasAccessibleRoof()) {
				// add stairs to roof
				addStairsDown(world, sbb, (floors + 2) % 4, this.height - 1);
			}

		}

		protected boolean hasAccessibleRoof() {
			return this.height - this.highestOpening < 9;
		}

		private void addStairsDown(World world, StructureBoundingBox sbb, int rotation, int y) {
			// top flight
			for (int i = 0; i < 4; i++) {
				int sx = 8 - i;
				int sy = y - i;
				int sz = 9;
				
				this.placeBlockRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz, rotation, sbb);
				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz, rotation, sbb);
				this.placeBlockRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz - 1, rotation, sbb);
				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz - 1, rotation, sbb);
				this.fillAirRotated(world, sbb, sx, sy + 1, sz - 1, sx, sy + 3, sz, rotation);
			}
			// landing
			this.fillBlocksRotated(world, sbb, 3, y - 4, 8, 4, y - 4, 9, deco.blockID, deco.blockMeta, rotation);

			
			// bottom flight
			for (int i = 0; i < 4; i++) {
				int sx = 4;
				int sy = y - i - 4;
				int sz = 7 - i;
				
				this.placeBlockRotated(world, deco.stairID, getStairMeta(1 + rotation), sx, sy, sz, rotation, sbb);
				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx, sy - 1, sz, rotation, sbb);
				this.placeBlockRotated(world, deco.stairID, getStairMeta(1 + rotation), sx - 1, sy, sz, rotation, sbb);
				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, sx - 1, sy - 1, sz, rotation, sbb);
				this.fillAirRotated(world, sbb, sx, sy + 1, sz, sx - 1, sy + 3, sz, rotation);
			}

		}

		/**
		 * Make an opening in this tower for a door.
		 */
		@Override
		protected void makeDoorOpening(World world, int dx, int dy, int dz, StructureBoundingBox sbb) {
	     	// nullify sky light
	     	//nullifySkyLightAtCurrentPosition(world, dx - 3, dy - 1, dz - 3, dx + 3, dy + 3, dz + 3);
			
	        // clear the door
			if (dx == 0 || dx == size - 1) 
			{
				this.fillWithMetadataBlocks(world, sbb, dx, dy - 1, dz - 2, dx, dy + 4, dz + 2, deco.accentID, deco.accentMeta, Blocks.AIR, 0, false);
				//this.fillWithAir(world, sbb, dx, dy, dz - 1, dx, dy + 3, dz + 1);
				this.fillWithMetadataBlocks(world, sbb, dx, dy, dz - 1, dx, dy + 3, dz + 1, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR, 0, false);
			}
			if (dz == 0 || dz == size - 1) 
			{
				this.fillWithMetadataBlocks(world, sbb, dx - 2, dy - 1, dz, dx + 2, dy + 4, dz, deco.accentID, deco.accentMeta, Blocks.AIR, 0, false);
				//this.fillWithAir(world, sbb, dx - 1, dy, dz, dx + 1, dy + 3, dz);
				this.fillWithMetadataBlocks(world, sbb, dx - 1, dy, dz, dx + 1, dy + 3, dz, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR, 0, false);
			}
		}
	}


    public static class EntranceTower extends MazeTower13 {
    	
		public EntranceTower() { }
	
		public EntranceTower(Random rand, int i, int x, int y, int z, int direction) {
			super(rand, i, x, y, z, 3, 2, 0, direction);
		}
	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		
    		// add foundation
    		Foundation13 foundation = new Foundation13(rand, 4, this);
    		list.add(foundation);
    		foundation.buildComponent(this, list, rand);
    		
    		// add roof
    		StructureTFComponent roof = new Roof13Peaked(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    		
    		// how many floors until the bottom?
    		int missingFloors = (this.boundingBox.minY - 127) / 8;
    		
    		// place half on the bottom
    		int bottomFloors = missingFloors / 2;
    		// how many are left for the middle?
    		int middleFloors = missingFloors - bottomFloors;
    		
    		// what direction can we put the side tower in, if any?
    		int direction = 1;
    		int howFar = 20;
    		if (!this.buildSideTower(list, rand, middleFloors + 1, direction, howFar)) {
    			direction = 3;
        		if (!this.buildSideTower(list, rand, middleFloors + 1, direction, howFar)) {
        			direction = 0;
            		if (!this.buildSideTower(list, rand, middleFloors + 1, direction, howFar)) {
            			// side tower no worky
            		}
        		}
        	}
    		
    		// add bottom tower
			int brDirection = (direction + this.coordBaseMode) % 4;
			EntranceBottomTower eTower = new EntranceBottomTower(rand, this.getComponentType() + 1, this.boundingBox.minX + 6, this.boundingBox.minY - (middleFloors) * 8, this.boundingBox.minZ + 6, bottomFloors + 1, bottomFloors, (brDirection + 2) % 4);
			list.add(eTower);
			eTower.buildComponent(this, list, rand);
    		
    		// add bridge to bottom
			ChunkCoordinates opening = this.getValidOpeningCC(rand, direction);
			opening.posY -= middleFloors * 8;
			
			ChunkCoordinates bc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, 1, brDirection);
			Bridge bridge = new Bridge(this.getComponentType() + 1, bc.posX, bc.posY, bc.posZ, howFar - 7, brDirection);
			list.add(bridge);
			bridge.buildComponent(this, list, rand);
		}

		private boolean buildSideTower(List list, Random rand, int middleFloors, int direction, int howFar) {
			ChunkCoordinates opening = this.getValidOpeningCC(rand, direction);
			
			direction += this.coordBaseMode;
			direction %= 4;
			
			// build towards
			ChunkCoordinates tc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, howFar, direction);

			//System.out.println("Our coord mode is " + this.getCoordBaseMode() + ", and direction is " + direction + ", so our door is going to be at " + opening + " and the new tower will appear at " + tc);

			EntranceSideTower eTower = new EntranceSideTower(rand, this.getComponentType() + 1, tc.posX, tc.posY, tc.posZ, middleFloors, middleFloors - 1, direction);

			StructureBoundingBox largerBB = new StructureBoundingBox(eTower.getBoundingBox());

			largerBB.minX -= 6;
			largerBB.minZ -= 6;
			largerBB.maxX += 6;
			largerBB.maxZ += 6;

			StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

			if (intersect == null) {
				list.add(eTower);
				eTower.buildComponent(this, list, rand);
				// add bridge
				ChunkCoordinates bc = this.offsetTowerCCoords(opening.posX, opening.posY, opening.posZ, 1, direction);
				Bridge bridge = new Bridge(this.getComponentType() + 1, bc.posX, bc.posY, bc.posZ, howFar - 7, direction);
				list.add(bridge);
				bridge.buildComponent(this, list, rand);
				
				// opening
	    		addOpening(opening.posX, opening.posY + 1, opening.posZ, direction);
				
				return true;
			} else {
				System.out.println("side entrance tower blocked");
				return false;
			}

		}
		
		/**
		 * Gets a random position in the specified direction that connects to a floor currently in the tower.
		 */
		public ChunkCoordinates getValidOpeningCC(Random rand, int direction) {
			// for directions 0 or 2, the wall lies along the z axis
			if (direction == 0 || direction == 2) {
				int rx = direction == 0 ? 12 : 0;
				int rz = 6;
				int ry = 0;
				
				return new ChunkCoordinates(rx, ry, rz);
			}
			
			// for directions 1 or 3, the wall lies along the x axis
			if (direction == 1 || direction == 3) {
				int rx = 6;
				int rz = direction == 1 ? 12 : 0;
				int ry = 0;
				
				return new ChunkCoordinates(rx, ry, rz);
			}
			
			
			return new ChunkCoordinates(0, 0, 0);
		}

	
	}

	public static class EntranceSideTower extends MazeTower13 {
		public EntranceSideTower() {}

		public EntranceSideTower(Random rand, int i, int x, int y, int z, int floors, int entranceFloor, int direction) {
			super(rand, i, x, y, z, floors, entranceFloor, 0, direction);
			
    		addOpening(0, 1, size / 2, 2);		
		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		
    		// add foundation
    		Foundation13 foundation = new Foundation13(rand, 4, this);
    		list.add(foundation);
    		foundation.buildComponent(this, list, rand);
    		
    		// add roof
    		StructureTFComponent roof = new Roof13Peaked(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    	}

	}

	public static class EntranceBottomTower extends MazeTower13 {
		public EntranceBottomTower() {}

		public EntranceBottomTower(Random rand, int i, int x, int y, int z, int floors, int entranceFloor, int direction) {
			super(rand, i, x, y, z, floors, entranceFloor, 0, direction);
			
//    		addOpening(12, 1, size / 2, 0);		
//    		addOpening(size / 2, 1, 0, 1);		
//    		addOpening(0, 1, size / 2, 2);		
//    		addOpening(size / 2, 1, 12, 3);		
		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		
    		// stairs
    		addStairs(list, rand, this.getComponentType() + 1, this.size - 1, 1, size / 2, 0);
    		addStairs(list, rand, this.getComponentType() + 1, 0, 1, size / 2, 2);
    		addStairs(list, rand, this.getComponentType() + 1, this.size / 2, 1, 0, 3);
    		addStairs(list, rand, this.getComponentType() + 1, this.size / 2, 1, this.size - 1, 1);

    	}
    	

    	/**
    	 * Add some stairs leading to this tower
    	 */
    	private boolean addStairs(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int rotation) {
    		// add door
    		this.addOpening(x, y, z, rotation);

    		int direction = (getCoordBaseMode() + rotation) % 4;
    		ChunkCoordinates dx = offsetTowerCCoords(x, y, z, 0, direction);

    		EntranceStairs stairs = new EntranceStairs(index, dx.posX, dx.posY, dx.posZ, direction);

    		list.add(stairs);
    		stairs.buildComponent(list.get(0), list, rand);
    		return true;
    	}
    	
		protected boolean hasAccessibleRoof() {
			return false;
		}

	}

	/**
	 * Stair blocks heading to the entrance tower doors
	 */
	public static class EntranceStairs extends StructureTFComponent {
		
		public EntranceStairs() {}
	
		public EntranceStairs(int index, int x, int y, int z, int direction) {
			this.setCoordBaseMode(direction);
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, 0, -1, -5, 12, 0, 12, direction);
		}
	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public void buildComponent(StructureComponent parent, List list, Random rand) {
			if (parent != null && parent instanceof StructureTFComponent) {
				this.deco = ((StructureTFComponent)parent).deco;
			}
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			
			int size = 13;
			
			for (int x = 1; x < size; x++) {
			
				this.placeStairs(world, sbb, x, 1 - x, 5, 2);

				for (int z = 0; z <= x; z++) {
					
					if (z > 0 && z <= size / 2) {
						this.placeStairs(world, sbb, x, 1 - x, 5 - z, 2);
						this.placeStairs(world, sbb, x, 1 - x, 5 + z, 2);
					}

					if (x <= size / 2) {
						this.placeStairs(world, sbb, z, 1 - x, 5 - x, 1);
						this.placeStairs(world, sbb, z, 1 - x, 5 + x, 3);
					}
				}
			}
			
			this.func_151554_b(world, deco.blockID, deco.blockMeta, 0, 0, 5, sbb);

			
			return true;
		}

		private void placeStairs(World world, StructureBoundingBox sbb, int x, int y, int z, int stairMeta) {
			if (this.getBlockAtCurrentPosition(world, x, y, z, sbb).isReplaceable(world, x, y, z)) {
				//this.placeBlockAtCurrentPosition(world, deco.blockID, deco.blockMeta, x, y, z, sbb);
				this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(stairMeta), x, y, z, sbb);
				this.func_151554_b(world, deco.blockID, deco.blockMeta, x, y - 1, z, sbb);
			}
		}

	
	}

	public static class BellTower21 extends MazeTower13 {
    	
		private static final int FLOORS = 8;

		public BellTower21() { }
	
		public BellTower21(Random rand, int i, int x, int y, int z, int direction) {
			super(rand, i, x, y, z, FLOORS, 1, 1, direction);
			this.size = 21;
			int floors = FLOORS;
			this.height = floors * 8 + 1;
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, -6, -8, -this.size / 2, this.size - 1, this.height, this.size - 1, direction);
			this.openings.clear();
    		addOpening(0, 9, size / 2, 2);		
		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    		
    		// add foundation
    		BellFoundation21 foundation = new BellFoundation21(rand, 4, this);
    		list.add(foundation);
    		foundation.buildComponent(this, list, rand);
    		
    		// add roof
    		StructureTFComponent roof = new Roof13Crenellated(rand, 4, this);
    		list.add(roof);
    		roof.buildComponent(this, list, rand);
    	}
    	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			super.addComponentParts(world, rand, sbb);
			
			// openings!
			Block fieldBlock = TFBlocks.forceField;
			int fieldMeta = 4;
	        for (int rotation = 0; rotation < 4; rotation++) {
	        	int y = 48;
	        	for (int x = 5; x < this.size - 4; x += 2) {
//	        		for (int wy = 0; wy < 15; wy++) {
//		        		fieldMeta = rand.nextInt(4) + 1;
//	        			this.placeBlockRotated(world, fieldBlock, fieldMeta, x, y + wy, 0, rotation, sbb);
//	        		}
//	        		fieldMeta = rand.nextInt(5);
	        		this.fillBlocksRotated(world, sbb, x, y, 0, x, y + 14, 0, fieldBlock, fieldMeta, rotation);
	        	}
	        	y = 24;
	        	for (int x = 1; x < this.size - 1; x += 8) {
//	        		for (int wy = 0; wy < 15; wy++) {
//		        		fieldMeta = rand.nextInt(4) + 1;
//	        			this.placeBlockRotated(world, fieldBlock, fieldMeta, x, y + wy, 0, rotation, sbb);
//		        		fieldMeta = rand.nextInt(4) + 1;
//	        			this.placeBlockRotated(world, fieldBlock, fieldMeta, x + 2, y + wy, 0, rotation, sbb);
//	        		}
//	        		fieldMeta = rand.nextInt(5);
	        		this.fillBlocksRotated(world, sbb, x, y, 0, x, y + 14, 0, fieldBlock, fieldMeta, rotation);
//	        		fieldMeta = rand.nextInt(5);
	        		this.fillBlocksRotated(world, sbb, x + 2, y, 0, x + 2, y + 14, 0, fieldBlock, fieldMeta, rotation);
	        	}
	        }
	        
	        // sign
	        this.placeSignAtCurrentPosition(world, 7, 9, 8, "Parkour area 2", "mini-boss 1", sbb);
			
			return true;
		}
	}
	
	public static class DamagedTower extends MazeTower13 {

		public DamagedTower() { }

		public DamagedTower(Random rand, int i, int x, int y, int z, int direction) {
			super(rand, i, x, y, z, 2, direction);  //TODO: change rune type
		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}

    		// add foundation
    		Foundation13 foundation = new Foundation13(rand, 0, this);
    		list.add(foundation);
    		foundation.buildComponent(this, list, rand);
    		
    		// add thorns
    		Foundation13 thorns = new Foundation13Thorns(rand, 0, this);
    		list.add(thorns);
    		thorns.buildComponent(this, list, rand);
    		
//    		// add roof
//    		StructureTFComponent roof = rand.nextBoolean() ? new Roof13Conical(rand, 4, this) :  new Roof13Crenellated(rand, 4, this);
//    		list.add(roof);
//    		roof.buildComponent(this, list, rand);
    		
    		
    		// keep on building?
    		this.buildNonCriticalTowers(parent, list, rand);
    	}
    	

		protected MazeTower13 makeNewDamagedTower(Random rand, int direction, ChunkCoordinates tc) {
			return new WreckedTower(rand, this.getComponentType() + 1, tc.posX, tc.posY, tc.posZ, direction);
		}
		
		

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			super.addComponentParts(world, rand, sbb);
			Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
			
			this.destroyTower(world, decoRNG, sbb);
			
			return true;
		}

		public void destroyTower(World world, Random rand, StructureBoundingBox sbb) {

			// make list of destroyed areas
			ArrayList<DestroyArea> areas = makeInitialDestroyList(rand);
			
			boolean hitDeadRock = false;
			
			// go down from the top of the tower to the ground, taking out rectangular chunks
			//for (int y = this.boundingBox.maxY; y > this.boundingBox.minY; y--) {
			for (int y = this.boundingBox.maxY; !hitDeadRock && y > 64; y--) {
				for (int x = this.boundingBox.minX - 2; x <= this.boundingBox.maxX + 2; x++) {
					for (int z = this.boundingBox.minZ - 2; z <= this.boundingBox.maxZ + 2; z++) {
						if (sbb.isVecInside(x, y, z)) {
							if (world.getBlock(x, y, z) == TFBlocks.deadrock) {
								hitDeadRock = true;
							}
							determineBlockDestroyed(world, areas, y, x, z);
						}
					}
				}
				
				// check to see if any of our DestroyAreas are entirely above the current y value
				DestroyArea removeArea = null;
				
				for (DestroyArea dArea : areas) {
					if (dArea == null || dArea.isEntirelyAbove(y)) {
						removeArea = dArea;
					}
				}
				// if so, replace them with new ones
				if (removeArea != null) {
					areas.remove(removeArea);
					areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, y, areas));

				}
			}
		}

		protected ArrayList<DestroyArea> makeInitialDestroyList(Random rand) {
			ArrayList<DestroyArea> areas = new ArrayList<DestroyArea>(2);
			
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			return areas;
		}

		protected void determineBlockDestroyed(World world, ArrayList<DestroyArea> areas, int y, int x, int z) {
			for (DestroyArea dArea : areas) {
				if (dArea != null && dArea.isVecInside(x, y, z)) {
					world.setBlockToAir(x, y, z);
				}
			}
		}

	}
	
	/**
	 * An area that we're going to destroy.  Default is just a StructureBoundingBox
	 */
	public static class DestroyArea {
		
		StructureBoundingBox destroyBox;
		
		public DestroyArea(StructureBoundingBox tower, Random rand, int y) {
			// make a 4x4 area that's entirely within the tower bounding box
			
			int bx = tower.minX - 2 + rand.nextInt(tower.getXSize());
			int bz = tower.minZ - 2 + rand.nextInt(tower.getZSize());
			
			this.destroyBox = new StructureBoundingBox(bx, y - 10, bz, bx + 4, y, bz + 4);
		}

		public boolean isEntirelyAbove(int y) {
			return this.destroyBox.minY > y;
		}

		public boolean isVecInside(int x, int y, int z) {
			return destroyBox.isVecInside(x, y, z);
		}
		
		/**
		 * construct a new area that does not intersect any other areas in the list
		 */
		public static DestroyArea createNonIntersecting(StructureBoundingBox tower, Random rand, int y, ArrayList<DestroyArea> otherAreas) {
			int attempts = 100;
			
			DestroyArea area = null;
			for (int i = 0; i < attempts && area == null; i++) {
				DestroyArea testArea = new DestroyArea(tower, rand, y);
				
				if (otherAreas.size() == 0) {
					area = testArea;
				} else {
					for (DestroyArea otherArea : otherAreas) {
						if (otherArea == null || !testArea.intersectsWith(otherArea)) {
							area = testArea;
						}
					}
				}
			}
			return area;
		}

		/**
		 * We check if the box would intersect even if it was one block larger in the x and z directions
		 */
		private boolean intersectsWith(DestroyArea otherArea) {
			return this.destroyBox.intersectsWith(otherArea.destroyBox.minX - 1, otherArea.destroyBox.minZ - 1, otherArea.destroyBox.maxX + 1, otherArea.destroyBox.maxX + 1);
		}
	}

	public static class WreckedTower extends DamagedTower {

		public WreckedTower() { }

		public WreckedTower(Random rand, int i, int x, int y, int z, int direction) {
			super(rand, i, x, y, z, direction);
		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}

//    		// add foundation
//    		Foundation13 foundation = new Foundation13(rand, 4, this);
//    		list.add(foundation);
//    		foundation.buildComponent(this, list, rand);
    		
    		
    		// add thorns
    		Foundation13 thorns = new Foundation13Thorns(rand, 0, this);
    		list.add(thorns);
    		thorns.buildComponent(this, list, rand);

    		
//    		// add roof
//    		StructureTFComponent roof = rand.nextBoolean() ? new Roof13Conical(rand, 4, this) :  new Roof13Crenellated(rand, 4, this);
//    		list.add(roof);
//    		roof.buildComponent(this, list, rand);
    		
    		
    		// keep on building?
//    		this.buildNonCriticalTowers(parent, list, rand);
    	}
		
		public int getGlyphMeta() {
			return 1;
		}
		
		
		protected void determineBlockDestroyed(World world, ArrayList<DestroyArea> areas, int y, int x, int z) {
			boolean isInside = false;
			for (DestroyArea dArea : areas) {
				if (dArea != null && dArea.isVecInside(x, y, z)) {
					isInside = true;
				}
			}
			
			if (!isInside) {
				world.setBlockToAir(x, y, z);
			}

		}
		

		protected ArrayList<DestroyArea> makeInitialDestroyList(Random rand) {
			ArrayList<DestroyArea> areas = new ArrayList<DestroyArea>(2);
			
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
			return areas;
		}


	}


	public static class Bridge extends StructureTFComponent {
		public Bridge() {}
	
		public Bridge(int i, int x, int y, int z, int length, int direction) {
			this.setCoordBaseMode(direction);
			this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, 0, -1, -3, length - 1, 5, 6, direction);

		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			int length = (this.coordBaseMode == 0 || this.coordBaseMode == 2) ? this.boundingBox.maxX - this.boundingBox.minX : this.boundingBox.maxZ - this.boundingBox.minZ;

			// span
			fillWithRandomizedBlocks(world, sbb, 0, 0, 0, length, 1, 6, false, rand, deco.randomBlocks);
			// rails
			fillWithRandomizedBlocks(world, sbb, 0, 1, 0, length, 2, 0, false, rand, deco.randomBlocks);
			fillWithRandomizedBlocks(world, sbb, 0, 1, 6, length, 2, 6, false, rand, deco.randomBlocks);
			
			// supports
			int l3 = length / 3;
			for (int i = 0; i < l3; i++) {
				int sl = l3 - (int) (MathHelper.cos((float)(l3 - i) / (float)l3 * 1.6F) * (float)l3); // this could be better, maybe?
				fillWithRandomizedBlocks(world, sbb, i, -sl, 0, i, 0, 0, false, rand, deco.randomBlocks);
				fillWithRandomizedBlocks(world, sbb, i, -sl, 6, i, 0, 6, false, rand, deco.randomBlocks);
				fillWithRandomizedBlocks(world, sbb, length - i, -sl, 0, length - i, 0, 0, false, rand, deco.randomBlocks);
				fillWithRandomizedBlocks(world, sbb, length - i, -sl, 6, length - i, 0, 6, false, rand, deco.randomBlocks);
			}
			
			// doorframes
			this.fillWithMetadataBlocks(world, sbb, 0, 2, 1, 0, 7, 1, deco.pillarID, deco.pillarMeta, deco.pillarID, deco.pillarMeta, false);
			this.fillWithMetadataBlocks(world, sbb, 0, 2, 5, 0, 7, 5, deco.pillarID, deco.pillarMeta, deco.pillarID, deco.pillarMeta, false);
			this.fillWithMetadataBlocks(world, sbb, 0, 6, 2, 0, 6, 4, deco.accentID, deco.accentMeta, deco.accentID, deco.accentMeta, false);
			this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, 0, 7, 3, sbb);

			this.fillWithMetadataBlocks(world, sbb, length, 2, 1, length, 7, 1, deco.pillarID, deco.pillarMeta, deco.pillarID, deco.pillarMeta, false);
			this.fillWithMetadataBlocks(world, sbb, length, 2, 5, length, 7, 5, deco.pillarID, deco.pillarMeta, deco.pillarID, deco.pillarMeta, false);
			this.fillWithMetadataBlocks(world, sbb, length, 6, 2, length, 6, 4, deco.accentID, deco.accentMeta, deco.accentID, deco.accentMeta, false);
			this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, length, 7, 3, sbb);

			return true;
		}
	
	}

	public static class Roof9Crenellated extends StructureTFComponent  {
		
		public Roof9Crenellated() {}
		
		public Roof9Crenellated(Random rand, int i, StructureTFComponent sideTower) {
			super(i);
			
			int height = 5;
			
			this.setCoordBaseMode(sideTower.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY + height - 1, sideTower.getBoundingBox().maxZ + 2);

		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
            for (int rotation = 0; rotation < 4; rotation++) {
            	this.fillBlocksRotated(world, sbb, 0, -1, 0, 2, 3, 2, deco.blockID, deco.blockMeta, rotation);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 2, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 2, -2, 1, rotation, sbb);

            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 3, 0, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 3, 1, 1, rotation, sbb);
            	
            	this.fillBlocksRotated(world, sbb, 4, 0, 0, 5, 3, 2, deco.blockID, deco.blockMeta, rotation);
            	
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 6, 0, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 6, 1, 1, rotation, sbb);
            	
            	this.fillBlocksRotated(world, sbb, 7, 0, 0, 8, 3, 2, deco.blockID, deco.blockMeta, rotation);
            	
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 9, 0, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 9, 1, 1, rotation, sbb);
            }
			
			
			return true;
		}


	}
	
	/**
	 * Pointy cone roof with variable height
	 */
	public static class Roof13Conical extends StructureTFComponent  {
		
		public int slope;
		
		public Roof13Conical() {}
		
		public Roof13Conical(Random rand, int i, StructureTFComponent sideTower) {
			super(i);
			
			this.slope = 2 + rand.nextInt(3) + rand.nextInt(3);
			
			int height = slope * 4;
			
			this.setCoordBaseMode(sideTower.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY + height - 1, sideTower.getBoundingBox().maxZ + 2);

		}
		
		/**
		 * Save to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
			super.func_143012_a(par1NBTTagCompound);
			
	        par1NBTTagCompound.setInteger("slope", this.slope);
		}

		/**
		 * Load from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
			super.func_143011_b(par1NBTTagCompound);
	        this.slope = par1NBTTagCompound.getInteger("slope");
		}


    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}
    	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
            for (int rotation = 0; rotation < 4; rotation++) {
            	this.fillBlocksRotated(world, sbb, 0, -1, 0, 3, 2, 3, deco.blockID, deco.blockMeta, rotation);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 2, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 2, -2, 1, rotation, sbb);
            	
            	this.fillBlocksRotated(world, sbb, 4, 0, 1, 12, 1, 1, deco.blockID, deco.blockMeta, rotation);
            	
            	// more teeny crenellations
            	for (int i = 3; i < 13; i += 2) {
                	this.fillBlocksRotated(world, sbb, i, -1, 1, i, 2, 1, deco.blockID, deco.blockMeta, rotation);
            	}

            	// cone roof
            	for (int i = 2; i < 9; i++) {
            		int base = 2 - slope;
            		if (i < 7) {
                    	this.fillBlocksRotated(world, sbb, i - 1, ((i - 1) * slope) + base, i - 1, i, (i * slope) + base - 1, i, deco.blockID, deco.blockMeta, rotation);
            		} else {
                    	this.fillBlocksRotated(world, sbb, 16 - i, ((i - 1) * slope) + base, i, 16 - i, (i * slope) + base - 1, i, deco.roofID, deco.roofMeta, rotation);
            		}
                	this.fillBlocksRotated(world, sbb, i + 1, ((i - 1) * slope) + base, i, 15 - i, (i * slope) + base - 1, i, deco.roofID, deco.roofMeta, rotation);
            	}
            	
            	// point!
            	this.fillBlocksRotated(world, sbb, 8, (slope * 6) + 2, 8, 8, (slope * 7) + 2, 8, deco.roofID, deco.roofMeta, rotation);
            	
            }
			
			
			return true;
		}
	}
	
	public static class Roof13Crenellated extends StructureTFComponent  {
		
		public Roof13Crenellated() {}
		
		public Roof13Crenellated(Random rand, int i, StructureTFComponent sideTower) {
			super(i);
			
			int height = 5;
			
			this.setCoordBaseMode(sideTower.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY + height - 1, sideTower.getBoundingBox().maxZ + 2);

		}
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}
    	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
	        // assume square
	        int size = this.boundingBox.maxX - this.boundingBox.minX;
			
            for (int rotation = 0; rotation < 4; rotation++) {
            	// corner
            	this.fillBlocksRotated(world, sbb, 0, -1, 0, 3, 3, 3, deco.blockID, deco.blockMeta, rotation);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 2, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 2, -2, 1, rotation, sbb);
            	
            	// walls
            	this.fillBlocksRotated(world, sbb, 4, 0, 1, size - 4, 1, 1, deco.blockID, deco.blockMeta, rotation);
            	
            	// smaller crenellations
            	for (int x = 5; x < size - 5; x += 4) {
            		this.fillBlocksRotated(world, sbb, x, 0, 0, x + 2, 3, 2, deco.blockID, deco.blockMeta, rotation);
            		this.placeBlockRotated(world, deco.blockID, deco.blockMeta, x + 1, -1, 1, rotation, sbb);
            		this.placeBlockRotated(world, deco.blockID, deco.blockMeta, x + 1, -2, 1, rotation, sbb);
            	}
            }
			
			return true;
		}
	}

	public static class Roof13Peaked extends StructureTFComponent {
		
		public Roof13Peaked() {}
	
		public Roof13Peaked(Random rand, int i, StructureTFComponent sideTower) {
			super(i);
			
			int height = 18;
			
			this.setCoordBaseMode(sideTower.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY + height - 1, sideTower.getBoundingBox().maxZ + 2);

		}
		
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	@Override
    	public void buildComponent(StructureComponent parent, List list, Random rand) {
    		if (parent != null && parent instanceof StructureTFComponent) {
    			this.deco = ((StructureTFComponent)parent).deco;
    		}
    	}
	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			
            // peaky roof, loop unrolled as it was getting dumb
			for (int i = 0; i < 3; i++) {
				this.fillWithMetadataBlocks(world, sbb, 1, i, i, 15, i, i, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, i, 16 - i, 15, i, 16 - i, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
			}

			for (int i = 0; i < 3; i++) {
				int dz = 3 + i;
				this.fillWithMetadataBlocks(world, sbb, 2, 5 + ((i - 1) * 2), dz, 14, 4 + (i * 2), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 1, dz, 1, 5 + ((i - 1) * 2), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 5 + ((i - 1) * 2), dz - 1, 1, 4 + (i * 2), dz, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 1, dz, 15, 5 + ((i - 1) * 2), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 5 + ((i - 1) * 2), dz - 1, 15, 4 + (i * 2), dz, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
 
				dz = 13 - i;
				this.fillWithMetadataBlocks(world, sbb, 2, 5 + ((i - 1) * 2), dz, 14, 4 + (i * 2), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 1, dz, 1, 5 + ((i - 1) * 2), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 5 + ((i - 1) * 2), dz, 1, 4 + (i * 2), dz + 1, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 1, dz, 15, 5 + ((i - 1) * 2), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 5 + ((i - 1) * 2), dz, 15, 4 + (i * 2), dz + 1, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
			}

			for (int i = 0; i < 3; i++) {
				int dz = 6 + i;
				this.fillWithMetadataBlocks(world, sbb, 2, 12 + ((i - 1) * 3), dz, 14, 11 + (i * 3), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 1, dz, 1, 12 + ((i - 1) * 3), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 12 + ((i - 1) * 3), dz - 1, 1, 11 + (i * 3), dz, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 1, dz, 15, 12 + ((i - 1) * 3), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 12 + ((i - 1) * 3), dz - 1, 15, 11 + (i * 3), dz, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);

				dz = 10 - i;
				this.fillWithMetadataBlocks(world, sbb, 2, 12 + ((i - 1) * 3), dz, 14, 11 + (i * 3), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 1, dz, 1, 12 + ((i - 1) * 3), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 1, 12 + ((i - 1) * 3), dz, 1, 11 + (i * 3), dz + 1, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 1, dz, 15, 12 + ((i - 1) * 3), dz, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
				this.fillWithMetadataBlocks(world, sbb, 15, 12 + ((i - 1) * 3), dz, 15, 11 + (i * 3), dz + 1, deco.blockID, deco.blockMeta, deco.blockID, deco.blockMeta, false);
			}
			
			// top roof bobbles
			this.fillWithMetadataBlocks(world, sbb, 1, 18, 8, 5, 18, 8, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
			this.fillWithMetadataBlocks(world, sbb, 11, 18, 8, 14, 18, 8, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
			this.fillWithMetadataBlocks(world, sbb, 0, 17, 8, 1, 19, 8, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);
			this.fillWithMetadataBlocks(world, sbb, 15, 17, 8, 16, 19, 8, deco.roofID, deco.roofMeta, deco.roofID, deco.roofMeta, false);

			
			
			for (int rotation = 1; rotation < 4; rotation += 2) {
				// this might be one of my more confusing instances of code recycling
				this.fillBlocksRotated(world, sbb, 4, 0, 1, 12, 1, 1, deco.blockID, deco.blockMeta, rotation);
				// more teeny crenellations
				for (int i = 3; i < 13; i += 2) {
					this.fillBlocksRotated(world, sbb, i, -1, 1, i, 2, 1, deco.blockID, deco.blockMeta, rotation);
				}
			}
			
			// corners
			for (int rotation = 0; rotation < 4; rotation++) {
            	this.fillBlocksRotated(world, sbb, 0, -1, 0, 3, 2, 3, deco.blockID, deco.blockMeta, rotation);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 2, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 1, -2, 1, rotation, sbb);
            	this.placeBlockRotated(world, deco.blockID, deco.blockMeta, 2, -2, 1, rotation, sbb);
            }

			
			
			return true;
		}

	}

	public static class Foundation13 extends StructureTFComponent {
		protected int groundLevel = -1;
	
		public Foundation13() {
		}
		
		public Foundation13(Random rand, int i, StructureTFComponent sideTower) {
			super(i);
			
			this.setCoordBaseMode(sideTower.getCoordBaseMode());
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().minY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().minY, sideTower.getBoundingBox().maxZ + 2);
	
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void buildComponent(StructureComponent parent, List list, Random rand) {
			if (parent != null && parent instanceof StructureTFComponent) {
				this.deco = ((StructureTFComponent)parent).deco;
			}
		}
	
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			// offset bounding box to average ground level
	        if (this.groundLevel  < 0)
	        {
	            this.groundLevel = this.getDeadrockLevel(world, sbb);
	
	            if (this.groundLevel < 0)
	            {
	                return true;
	            }
	            
	            //System.out.println("Adjusting root bounding box to " + this.boundingBox.minY);
	        }
	
	        // how tall are we
	        int height = this.boundingBox.maxY - this.groundLevel;
	        int mid = height / 2;
	        
	        // assume square
	        int size = this.boundingBox.maxX - this.boundingBox.minX;
	        
	        for (int rotation = 0; rotation < 4; rotation++) {
	            // do corner
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 1, -1, 1, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 2, -1, 1, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 2, -mid, 0, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 1, -1, 2, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 0, -mid, 2, rotation, sbb);

				for (int x = 6; x < (size - 3); x += 4) {
					this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, x, -1, 1, rotation, sbb);
					this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, x, -mid, 0, rotation, sbb);
				}

	        }
	
			return true;
		}
	
		/**
		 * Find what y level the local deadrock is.  Just check the center of the chunk we're given
		 */
		protected int getDeadrockLevel(World world, StructureBoundingBox sbb) {
		    int groundLevel = 256;
		    
		    for (int y = 150; y > 0; y--) // is 150 a good place to start? :)
		    {
		    	int cx = sbb.getCenterX();
		    	int cz = sbb.getCenterZ();
		    	
		    	Block block = world.getBlock(cx, y, cz);
		    	if (block == TFBlocks.deadrock)
		    	{
		    		groundLevel = y;
		    		break;
		    	}
		    }
		    
		    return groundLevel;
		}
	}

	/**
	 * Foundation that makes thorns go all through the tower
	 * 
	 * @author benma_000
	 *
	 */
	public static class Foundation13Thorns extends Foundation13 {
		
		public Foundation13Thorns() {}

		public Foundation13Thorns(Random rand, int i, StructureTFComponent sideTower) {
			super(rand, i, sideTower);
			
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 5, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 5, sideTower.getBoundingBox().maxX + 5, sideTower.getBoundingBox().maxY, sideTower.getBoundingBox().maxZ + 5);
	
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			// thorns
			Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

			for (int i = 0; i < 4; i++) {
				this.makeThornVine(world, decoRNG, i, sbb);
			}
			
			return true;
		}

		private void makeThornVine(World world, Random decoRNG, int rotation, StructureBoundingBox sbb) {
			
			int x = 3 + decoRNG.nextInt(13);
			int z = 3 + decoRNG.nextInt(13);
			
			int y = this.boundingBox.getYSize() + 5;
			
			int twist = decoRNG.nextInt(4);
			int twistMod = 3 + decoRNG.nextInt(3);
			
			while (this.getBlockIDRotated(world, x, y, z, rotation, sbb) != TFBlocks.deadrock && this.getYWithOffset(y) > 60) {
				this.placeBlockRotated(world, TFBlocks.thorns, 0, x, y, z, rotation, sbb);
				// twist vines around the center block
				switch (twist) {
				case 0:
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x + 1, y, z, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x, y, z + 1, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x + 1, y, z + 1, rotation, sbb);
					break;
				case 1:
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x + 1, y, z, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x, y, z - 1, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x + 1, y, z - 1, rotation, sbb);
					break;
				case 2:
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x - 1, y, z, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x, y, z - 1, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x - 1, y, z - 1, rotation, sbb);
					break;
				case 3:
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x - 1, y, z, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x, y, z + 1, rotation, sbb);
					this.placeBlockRotated(world, TFBlocks.thorns, 0, x - 1, y, z + 1, rotation, sbb);
					break;
				}

				if (Math.abs(y % twistMod) == 1) {
					// make branch
					this.makeThornBranch(world, x, y, z, rotation, sbb);
				}

				// twist randomly
				if (y % twistMod == 0) {
					twist++;
					twist = twist % 4;
				}
				
				y--;
			}
		}

		private void makeThornBranch(World world, int x, int y, int z, int rotation, StructureBoundingBox sbb) {
			Random rand = new Random(world.getSeed() + (x * 321534781) ^ (y * 756839) + z);
			
			// pick a direction
			int dir = rand.nextInt(4);
			
			// initialize direction variables
			int dx = 0;
			int dz = 0;
			
			switch (dir) {
			case 0:
				dx = +1;
				break;
			case 1:
				dz = +1;
				break;
			case 2:
				dx = -1;
				break;
			case 3:
				dz = -1;
				break;
			}
			
			// how far do we branch?
			int dist = 2 + rand.nextInt(3);
			
			// check to make sure there's room
			int destX = x + (dist * dx);
			int destZ = z + (dist * dz);
			
			if (destX > 0 && destX < this.boundingBox.getXSize() && destZ > 0 && destZ < this.boundingBox.getZSize()) {
				for (int i = 0; i < dist; i++) {
					// go out that far
					int branchMeta = ((dir + rotation + this.coordBaseMode) % 2 == 0) ? 5 : 9;
					if (i > 0) {
						this.placeBlockRotated(world, TFBlocks.thorns, branchMeta, x + (dx * i), y, z + (dz * i), rotation, sbb);
					}
					// go up that far
					this.placeBlockRotated(world, TFBlocks.thorns, 1, destX, y + i, destZ, rotation, sbb);
					// go back half that far
					if (i > (dist/ 2)) {
						this.placeBlockRotated(world, TFBlocks.thorns, branchMeta, x + (dx * i), y + dist - 1, z + (dz * i), rotation, sbb);
					}

					
				}


			}

		}
	
	
	}
	
	/**
	 * A larger foundation that comes all the way from the top of a tower 
	 * 
	 * @author benma_000
	 *
	 */
	public static class BellFoundation21 extends Foundation13 {
		
		public BellFoundation21() {}

		public BellFoundation21(Random rand, int i, StructureTFComponent sideTower) {
			super(rand, i, sideTower);
			
			this.boundingBox = new StructureBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY, sideTower.getBoundingBox().maxZ + 2);
	
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
			// find ground level
	        if (this.groundLevel  < 0) {
	            this.groundLevel = this.getDeadrockLevel(world, sbb);
	        }
	
	        // how tall are we?
	        int height = this.boundingBox.maxY - this.groundLevel;
	        
	        int mid = 16;
	        int low = 32;
	        
	        // assume square
	        int size = this.boundingBox.maxX - this.boundingBox.minX;
	        
	        for (int rotation = 0; rotation < 4; rotation++) {
	            // do corner
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 2, -1, 1, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 2, -mid, 0, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 1, -1, 2, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 0, -mid, 2, rotation, sbb);

				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 1, -low, 1, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 0, -low, 1, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 1, -low, 0, rotation, sbb);
				this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, 0, -low, 0, rotation, sbb);

				for (int x = 6; x < (size - 3); x += 4) {
					this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, x, -1, 1, rotation, sbb);
					this.fillToGroundRotated(world, deco.blockID, deco.blockMeta, x, -mid, 0, rotation, sbb);
				}

	        }
	
			return true;
		}

	
	}

}
