package twilightforest.structures.stronghold;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import twilightforest.structures.StructureTFComponent;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;

public class TFStrongholdPieces {

	private static final TFStrongholdPieceWeight[] pieceWeightArray = new TFStrongholdPieceWeight[] {
		new TFStrongholdPieceWeight(ComponentTFStrongholdSmallHallway.class, 40, 0), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdLeftTurn.class, 20, 0), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdCrossing.class, 10, 4), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdRightTurn.class, 20, 0), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdDeadEnd.class, 5, 0), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdBalconyRoom.class, 10, 3, 2), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdTrainingRoom.class, 10, 2), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdSmallStairs.class, 10, 0), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdTreasureCorridor.class, 5, 0), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdAtrium.class, 5, 2, 3), 
		new TFStrongholdPieceWeight(ComponentTFStrongholdFoundry.class, 5, 1, 4),
		new TFStrongholdPieceWeight(ComponentTFStrongholdTreasureRoom.class, 5, 1, 4),
		new TFStrongholdPieceWeight(ComponentTFStrongholdBossRoom.class, 10, 1, 4)};

	private List<TFStrongholdPieceWeight> pieceList;
	static int totalWeight = 0;

    private static Class<? extends StructureTFComponent> lastPieceMade;
    
    public static void registerPieces()
    {
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdSmallHallway.class, "TFSSH");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdLeftTurn.class, "TFSLT");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdCrossing.class, "TFSCr");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdRightTurn.class, "TFSRT");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdDeadEnd.class, "TFSDE");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdBalconyRoom.class, "TFSBR");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdTrainingRoom.class, "TFSTR");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdSmallStairs.class, "TFSSS");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdTreasureCorridor.class, "TFSTC");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdAtrium.class, "TFSAt");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdFoundry.class, "TFSFo");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdTreasureRoom.class, "TFTreaR");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdBossRoom.class, "TFSBR");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdAccessChamber.class, "TFSAC");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdEntrance.class, "TFSEnter");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdUpperAscender.class, "TFSUA");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdUpperLeftTurn.class, "TFSULT");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdUpperRightTurn.class, "TFSURT");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdUpperCorridor.class, "TFSUCo");
        MapGenStructureIO.func_143031_a(ComponentTFStrongholdUpperTIntersection.class, "TFSUTI");
        MapGenStructureIO.func_143031_a(StructureTFStrongholdShield.class, "TFSShield");
    }

    /**
     * sets up Arrays with the Structure pieces and their weights
     */
    public void prepareStructurePieces()
    {
        pieceList = new ArrayList<TFStrongholdPieceWeight>();

        for (TFStrongholdPieceWeight piece : pieceWeightArray)
        {
        	piece.instancesSpawned = 0;
            pieceList.add(piece);
        }
    }
    
    public void markBossRoomUsed()
    {
    	// let's assume the boss room is the last one on the list
    	//System.out.println("Removing " + pieceList.get(pieceList.size() - 1).pieceClass);
    	
    	pieceList.remove(pieceList.size() - 1);
    }

    
    private boolean hasMoreLimitedPieces()
    {
        boolean flag = false;
        totalWeight = 0;

        for (TFStrongholdPieceWeight piece : pieceList)
        {
        	totalWeight += piece.pieceWeight;
        	
        	if (piece.instancesLimit > 0 && piece.instancesSpawned < piece.instancesLimit)
        	{
        		flag = true;
        	}
        }

        return flag;
    }
    
    @SuppressWarnings("rawtypes")
	public StructureTFStrongholdComponent getNextComponent(StructureComponent parent, List list, Random random, int index, int facing, int x, int y, int z)
    {
        if (!hasMoreLimitedPieces())
        {
            return null;
        }
        else
        {
        	// repeat up to 5 times if we're not getting the right thing
        	for (int i = 0; i < 5; i++)
        	{
        		int counter = random.nextInt(totalWeight);

        		for (TFStrongholdPieceWeight piece : pieceList)
        		{
        			counter -= piece.pieceWeight;

        			if (counter < 0)
        			{
        				
                        if (!piece.isDeepEnough(index) || piece.pieceClass == lastPieceMade)
                        {
                            break;
                        }
        				
        				// we're here!
                        StructureTFStrongholdComponent component = instantiateComponent(piece.pieceClass, index, facing, x, y, z);

                        if (StructureComponent.findIntersecting(list, component.getBoundingBox()) == null)
                        {
                        	++piece.instancesSpawned;

                        	if (!piece.canSpawnMoreStructures())
                        	{
                        		pieceList.remove(piece);
                        	}
                        	
                        	lastPieceMade = piece.pieceClass;

                        	return component;
                        }
        			}
        		}
        	}
        }
        
        // dead end?
        StructureTFStrongholdComponent deadEnd = new ComponentTFStrongholdDeadEnd(index, facing, x, y, z);
        
        if (StructureComponent.findIntersecting(list, deadEnd.getBoundingBox()) == null)
        {
        	return deadEnd;
        }
        else
        {
        	return null;
        }
    }

	private static StructureTFStrongholdComponent instantiateComponent(Class<? extends StructureTFComponent> pieceClass, int index, int facing, int x, int y, int z) {
		try {
			return (StructureTFStrongholdComponent) pieceClass.getConstructor(int.class, int.class, int.class, int.class, int.class).newInstance(index, facing, x, y, z);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		////			attempted = new ComponentTFStrongholdRoom(index, nFacing, nx, ny, nz);

		return null;
	}

}
