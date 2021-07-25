package twilightforest.structures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import twilightforest.structures.darktower.StructureDecoratorDarkTower;
import twilightforest.structures.finalcastle.StructureTFDecoratorCastle;
import twilightforest.structures.icetower.IceTowerDecorator;
import twilightforest.structures.mushroomtower.MushroomTowerDecorator;
import twilightforest.structures.stronghold.StrongholdDecorator;

/**
 * Stores information about what blocks to use in constructing this structure
 *
 * @author Ben
 */
public class TFStructureDecorator {
	public BlockState blockState = Blocks.STONE.defaultBlockState();
	public BlockState accentState = Blocks.COBBLESTONE.defaultBlockState();
	public BlockState stairState = Blocks.STONE_STAIRS.defaultBlockState();
	public BlockState fenceState = Blocks.OAK_FENCE.defaultBlockState();
	public BlockState pillarState = Blocks.STONE_BRICKS.defaultBlockState();
	public BlockState platformState = Blocks.STONE_SLAB.defaultBlockState();
	public BlockState floorState = Blocks.STONE_BRICKS.defaultBlockState();
	public BlockState roofState = Blocks.STONE_BRICKS.defaultBlockState();

	public StructurePiece.BlockSelector randomBlocks = new StrongholdStones();

	public static String getDecoString(TFStructureDecorator deco) {
//TODO: Structure Disabled
		if (deco instanceof StructureDecoratorDarkTower) {
			return "DecoDarkTower";
		}
		if (deco instanceof IceTowerDecorator) {
			return "DecoIceTower";
		}
		if (deco instanceof MushroomTowerDecorator) {
			return "DecoMushroomTower";
		}
		if (deco instanceof StrongholdDecorator) {
			return "DecoStronghold";
		}
		if (deco instanceof StructureTFDecoratorCastle) {
			return "DecoCastle";
		}

		return "";
	}

	public static TFStructureDecorator getDecoFor(String decoString) {
//TODO: Structure Disabled
		if (decoString.equals("DecoDarkTower")) {
			return new StructureDecoratorDarkTower();
		}
		if (decoString.equals("DecoIceTower")) {
			return new IceTowerDecorator();
		}
		if (decoString.equals("DecoMushroomTower")) {
			return new MushroomTowerDecorator();
		}
		if (decoString.equals("DecoStronghold")) {
			return new StrongholdDecorator();
		}
		if (decoString.equals("DecoCastle")) {
			return new StructureTFDecoratorCastle();
		}

		return new TFStructureDecorator();
	}
}
