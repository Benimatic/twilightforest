package twilightforest.structures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.structures.darktower.StructureDecoratorDarkTower;
import twilightforest.structures.finalcastle.StructureTFDecoratorCastle;
import twilightforest.structures.icetower.StructureDecoratorIceTower;
import twilightforest.structures.mushroomtower.StructureDecoratorMushroomTower;
import twilightforest.structures.stronghold.StructureTFDecoratorStronghold;

/**
 * Stores information about what blocks to use in constructing this structure
 *
 * @author Ben
 */
public class StructureTFDecorator {
	public BlockState blockState = Blocks.STONE.getDefaultState();
	public BlockState accentState = Blocks.COBBLESTONE.getDefaultState();
	public BlockState stairState = Blocks.STONE_STAIRS.getDefaultState();
	public BlockState fenceState = Blocks.OAK_FENCE.getDefaultState();
	public BlockState pillarState = Blocks.STONE_BRICKS.getDefaultState();
	public BlockState platformState = Blocks.STONE_SLAB.getDefaultState();
	public BlockState floorState = Blocks.STONE_BRICKS.getDefaultState();
	public BlockState roofState = Blocks.STONE_BRICKS.getDefaultState();

	public StructurePiece.BlockSelector randomBlocks = new StructureTFStrongholdStones();

	public static String getDecoString(StructureTFDecorator deco) {
//TODO: Structure Disabled
		if (deco instanceof StructureDecoratorDarkTower) {
			return "DecoDarkTower";
		}
		if (deco instanceof StructureDecoratorIceTower) {
			return "DecoIceTower";
		}
		if (deco instanceof StructureDecoratorMushroomTower) {
			return "DecoMushroomTower";
		}
		if (deco instanceof StructureTFDecoratorStronghold) {
			return "DecoStronghold";
		}
		if (deco instanceof StructureTFDecoratorCastle) {
			return "DecoCastle";
		}

		return "";
	}

	public static StructureTFDecorator getDecoFor(String decoString) {
//TODO: Structure Disabled
		if (decoString.equals("DecoDarkTower")) {
			return new StructureDecoratorDarkTower();
		}
		if (decoString.equals("DecoIceTower")) {
			return new StructureDecoratorIceTower();
		}
		if (decoString.equals("DecoMushroomTower")) {
			return new StructureDecoratorMushroomTower();
		}
		if (decoString.equals("DecoStronghold")) {
			return new StructureTFDecoratorStronghold();
		}
		if (decoString.equals("DecoCastle")) {
			return new StructureTFDecoratorCastle();
		}

		return new StructureTFDecorator();
	}
}
