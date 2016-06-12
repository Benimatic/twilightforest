package twilightforest.structures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import twilightforest.TFFeature;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import twilightforest.structures.darktower.ComponentTFDarkTowerMain;
import twilightforest.structures.darktower.TFDarkTowerPieces;
import twilightforest.structures.hollowtree.StructureTFHollowTreeStart;
import twilightforest.structures.hollowtree.TFHollowTreePieces;
import twilightforest.structures.icetower.ComponentTFIceTowerMain;
import twilightforest.structures.icetower.TFIceTowerPieces;
import twilightforest.structures.lichtower.ComponentTFTowerMain;
import twilightforest.structures.lichtower.TFLichTowerPieces;
import twilightforest.structures.minotaurmaze.ComponentTFMazeRuins;
import twilightforest.structures.minotaurmaze.TFMinotaurMazePieces;
import twilightforest.structures.mushroomtower.ComponentTFMushroomTowerMain;
import twilightforest.structures.mushroomtower.TFMushroomTowerPieces;
import twilightforest.structures.stronghold.ComponentTFStrongholdEntrance;
import twilightforest.structures.stronghold.TFStrongholdPieces;
import twilightforest.structures.trollcave.ComponentTFTrollCaveMain;
import twilightforest.structures.trollcave.TFTrollCavePieces;
import twilightforest.world.TFWorld;
import twilightforest.world.TFWorldChunkManager;


public class StructureTFMajorFeatureStart extends StructureStart {
	
	public static int NUM_LOCKS = 4;
	
	public TFFeature feature;
	public boolean isConquered;
	public byte[] lockBytes = new byte[NUM_LOCKS];
	
    static
    {
    	MapGenStructureIO.registerStructure(StructureTFMajorFeatureStart.class, "TFFeature");
    	MapGenStructureIO.registerStructure(StructureTFHollowTreeStart.class, "TFHollowTree");
    	
    	TFStrongholdPieces.registerPieces();
    	TFMinotaurMazePieces.registerPieces();
    	TFDarkTowerPieces.registerPieces();
    	TFLichTowerPieces.registerPieces();
    	TFIceTowerPieces.registerPieces();
    	TFMushroomTowerPieces.registerPieces();
    	TFHollowTreePieces.registerPieces();
    	TFTrollCavePieces.registerPieces();
    	TFFinalCastlePieces.registerFinalCastlePieces();
    	
    	// register one-off pieces here
        MapGenStructureIO.func_143031_a(ComponentTFHedgeMaze.class, "TFHedge");
        MapGenStructureIO.func_143031_a(ComponentTFHillMaze.class, "TFHillMaze");
        MapGenStructureIO.func_143031_a(ComponentTFHollowHill.class, "TFHill");
        MapGenStructureIO.func_143031_a(ComponentTFHydraLair.class, "TFHydra");
        MapGenStructureIO.func_143031_a(ComponentTFNagaCourtyard.class, "TFNaga");
        MapGenStructureIO.func_143031_a(ComponentTFQuestGrove.class, "TFQuest1");
        MapGenStructureIO.func_143031_a(ComponentTFYetiCave.class, "TFYeti");
}
    
    public StructureTFMajorFeatureStart() {}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StructureTFMajorFeatureStart(World world, Random rand, int chunkX, int chunkZ) 
	{
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
        
		if (firstComponent instanceof StructureStrongholdPieces.Stairs2)
		{
			List var6 = ((StructureStrongholdPieces.Stairs2) firstComponent).field_75026_c;

			while (!var6.isEmpty())
			{
				int var7 = rand.nextInt(var6.size());
				StructureComponent var8 = (StructureComponent)var6.remove(var7);
				var8.buildComponent(firstComponent, this.components, rand);
			}
			
			updateBoundingBox();

			int offY = -33;
			
			boundingBox.offset(0, offY, 0);

			for (StructureComponent com : (LinkedList<StructureComponent>) getComponents())
			{
				com.getBoundingBox().offset(0, offY, 0);
			}


			//System.out.println("Making stronghold!");

		}

        if (firstComponent instanceof ComponentTFTowerMain || firstComponent instanceof ComponentTFDarkTowerMain)
        {
        	moveToAvgGroundLevel(world, x, z);
        }
	}
	
	/**
	 * 
	 * @return The first component we should add to our structure
	 */
	public StructureComponent makeFirstComponent(World world, Random rand, TFFeature feature, int x, int y, int z) {
		
		if (feature == TFFeature.nagaCourtyard) {
			return new ComponentTFNagaCourtyard(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.hedgeMaze) {
			return new ComponentTFHedgeMaze(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.hill1) {
			return new ComponentTFHollowHill(world, rand, 0, 1, x, y, z);
		}
		if (feature == TFFeature.hill2) {
			return new ComponentTFHollowHill(world, rand, 0, 2, x, y, z);
		}
		if (feature == TFFeature.hill3) {
			return new ComponentTFHollowHill(world, rand, 0, 3, x, y, z);
		}
		if (feature == TFFeature.lichTower) {
			return new ComponentTFTowerMain(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.questGrove) {
			return new ComponentTFQuestGrove(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.hydraLair) {
			return new ComponentTFHydraLair(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.labyrinth) {
			return new ComponentTFMazeRuins(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.darkTower) {
			return new ComponentTFDarkTowerMain(world, rand, 0, x, y - 1, z);
		}
		if (feature == TFFeature.tfStronghold) {
			return new ComponentTFStrongholdEntrance(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.iceTower) {
			return new ComponentTFIceTowerMain(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.mushroomTower) {
			return new ComponentTFMushroomTowerMain(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.yetiCave) {
			return new ComponentTFYetiCave(world, rand, 0, x, y, z);
		}
		if (feature == TFFeature.trollCave) {
			return new ComponentTFTrollCaveMain(world, rand, 0, x, y, z);
		}		
		if (feature == TFFeature.finalCastle) {
			return new TFFinalCastlePieces.Main(world, rand, 0, x, y, z);
		}
		
		return null;
	}

	
    /**
     * currently only defined for Villages, returns true if Village has more than 2 non-road components
     */
    public boolean isSizeableStructure()
    {
        return feature.isStructureEnabled;
    }
    

	/**
	 * Move the whole structure up or down
	 */
    @SuppressWarnings("unchecked")
	protected void moveToAvgGroundLevel(World world, int x, int z)
    {
    	if (world.getBiomeProvider() instanceof TFWorldChunkManager)
    	{
    		// determine the biome at the origin
    		Biome biomeAt = world.getBiomeGenForCoords(x, z);

    		int offY = (int) ((biomeAt.rootHeight + biomeAt.heightVariation) * 8);
    		
    		// dark forest doesn't seem to get the right value.  Why is my calculation so bad?
    		if (biomeAt == TFBiomeBase.darkForest)
    		{
    			offY += 4;
    		}
    		
    		if (offY > 0)
    		{
    			//System.out.println("Moving tower, offset is " + offY + " for biome " + biomeAt.biomeName);
    			
    			boundingBox.offset(0, offY, 0);

    			for (StructureComponent com : (LinkedList<StructureComponent>) getComponents())
    			{
    				com.getBoundingBox().offset(0, offY, 0);
    			}
    		}
    	}
    }
    
//    /**
//     * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
//     */
//    public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
//    {
//        if (this.getComponents().getFirst() instanceof ComponentTFStrongholdEntrance)
//        {
//        	//System.out.println("We're generating a stronghold!");
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
	@SuppressWarnings("unused")
	private boolean isIntersectingLarger(StructureBoundingBox chunkBB, StructureComponent component) {
		StructureBoundingBox compBB = component.getBoundingBox();
		
		// don't bother checking Y
        return (compBB.maxX + 1) >= chunkBB.minX && (compBB.minX - 1) <= chunkBB.maxX && (compBB.maxZ + 1) >= chunkBB.minZ && (compBB.minZ - 1) <= chunkBB.maxZ;

	}

	@SuppressWarnings("unused")
	private boolean isShieldable(StructureComponent component) {
		return component.getBoundingBox().maxY <= 32;
	}

    /**
     * Make the stronghold shield around a component's bounding box
     */
	@SuppressWarnings("unused")
	private void addShieldFor(World world, StructureComponent component, List<StructureComponent> otherComponents, StructureBoundingBox chunkBox) {
		StructureBoundingBox shieldBox = new StructureBoundingBox(component.getBoundingBox());
		
		shieldBox.minX--;
		shieldBox.minY--;
		shieldBox.minZ--;
		
		shieldBox.maxX++;
		shieldBox.maxY++;
		shieldBox.maxZ++;
		
		ArrayList<StructureComponent> intersecting = new ArrayList<StructureComponent>();
		
		for (StructureComponent other : otherComponents)
		{
			if (other != component && shieldBox.intersectsWith(other.getBoundingBox()))
			{
				intersecting.add(other);
			}
		}
		
		// trace outline
		for (int x = shieldBox.minX; x <= shieldBox.maxX; x++)
		{
			for (int y = shieldBox.minY; y <= shieldBox.maxY; y++)
			{
				for (int z = shieldBox.minZ; z <= shieldBox.maxZ; z++)
				{
					if (x == shieldBox.minX || x == shieldBox.maxX || y == shieldBox.minY || y == shieldBox.maxY || z == shieldBox.minZ || z == shieldBox.maxZ)
					{
						if (chunkBox.isVecInside(x, y, z))
						{
							// test other boxes
							boolean notIntersecting = true;
							
							for (StructureComponent other : intersecting)
							{
								if (other.getBoundingBox().isVecInside(x, y, z))
								{
									notIntersecting = false;
								}
							}


							if (notIntersecting)
							{
								world.setBlock(x, y, z, TFBlocks.shield, calculateShieldMeta(shieldBox, x, y, z), 2);
							}

						}
					}
				}
			}
		}
	}

	private int calculateShieldMeta(StructureBoundingBox shieldBox, int x, int y, int z) {
		int shieldMeta = 0;
		if (x == shieldBox.minX)
		{
			shieldMeta = 5;
		}
		if (x == shieldBox.maxX)
		{
			shieldMeta = 4;
		}
		if (z == shieldBox.minZ)
		{
			shieldMeta = 3;
		}
		if (z == shieldBox.maxZ)
		{
			shieldMeta = 2;
		}
		if (y == shieldBox.minY)
		{
			shieldMeta = 1;
		}
		if (y == shieldBox.maxY)
		{
			shieldMeta = 0;
		}
		return shieldMeta;
	}
	
    public void func_143022_a(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143022_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Conquered", this.isConquered);
        par1NBTTagCompound.setInteger("FeatureID", this.feature.featureID);
        par1NBTTagCompound.setByteArray("Locks", this.lockBytes);
        
        //System.out.println("Saved structure for feature " + feature.name);
    }

    public void func_143017_b(NBTTagCompound nbttagcompound)
    {
        super.func_143017_b(nbttagcompound);
        this.isConquered = nbttagcompound.getBoolean("Conquered");
        this.feature = TFFeature.featureList[nbttagcompound.getInteger("FeatureID")];
        this.lockBytes = nbttagcompound.getByteArray("Locks");
    
        //System.out.println("Loaded structure");
    }

	public boolean isLocked(int lockIndex) {
		if (lockIndex < this.lockBytes.length) {
			
			System.out.println("Checking locks for lockIndex " + lockIndex);
			
			for (int i = 0; i < this.lockBytes.length; i++) {
				System.out.println("Lock " + i + " = " + this.lockBytes[i]);
			}
			
			return this.lockBytes[lockIndex] != 0;
		} else {
			
			System.out.println("Current lock index, " + lockIndex + " is beyond array bounds " + this.lockBytes.length);

			
			return false;
		}
	}
 
}
