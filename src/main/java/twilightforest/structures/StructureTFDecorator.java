package twilightforest.structures;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.darktower.StructureDecoratorDarkTower;
import twilightforest.structures.icetower.StructureDecoratorIceTower;
import twilightforest.structures.mushroomtower.StructureDecoratorMushroomTower;
import twilightforest.structures.stronghold.StructureTFDecoratorStronghold;

/**
 *	Stores information about what blocks to use in constructing this structure
 * 
 * @author Ben
 *
 */
public class StructureTFDecorator 
{
	public Block blockID = Blocks.stone;
	public int blockMeta;
	
	public Block accentID = Blocks.cobblestone;
	public int accentMeta;
	
	public Block stairID;
	public int stairMeta; // probably not useful
	
	public Block fenceID;
	public int fenceMeta;
	
	public Block pillarID;
	public int pillarMeta;
	
	public Block platformID;
	public int platformMeta;
	
	public Block floorID;
	public int floorMeta;
	
	public Block roofID;
	public int roofMeta;
	
	public StructureComponent.BlockSelector randomBlocks = new StructureTFStrongholdStones();
	
	public static String getDecoString(StructureTFDecorator deco)
	{
		if (deco instanceof StructureDecoratorDarkTower)
		{
			return "DecoDarkTower";
		}
		if (deco instanceof StructureDecoratorIceTower)
		{
			return "DecoIceTower";
		}
		if (deco instanceof StructureDecoratorMushroomTower)
		{
			return "DecoMushroomTower";
		}
		if (deco instanceof StructureTFDecoratorStronghold)
		{
			return "DecoStronghold";
		}
		if (deco instanceof StructureTFDecoratorCastle)
		{
			return "DecoCastle";
		}
		
		return "";
	}
	
	public static StructureTFDecorator getDecoFor(String decoString)
	{
		if (decoString.equals("DecoDarkTower"))
		{
			return new StructureDecoratorDarkTower();
		}
		if (decoString.equals("DecoIceTower"))
		{
			return new StructureDecoratorIceTower();
		}
		if (decoString.equals("DecoMushroomTower"))
		{
			return new StructureDecoratorMushroomTower();
		}
		if (decoString.equals("DecoStronghold"))
		{
			return new StructureTFDecoratorStronghold();
		}
		if (decoString.equals("DecoCastle"))
		{
			return new StructureTFDecoratorCastle();
		}
		
		return new StructureTFDecorator();
	}
}
