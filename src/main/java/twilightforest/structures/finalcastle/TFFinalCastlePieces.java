package twilightforest.structures.finalcastle;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartFinalCastle;

public class TFFinalCastlePieces {
	public static void registerFinalCastlePieces() {
		StructureIO.registerStructure(StructureStartFinalCastle.class, "TFFC");

		StructureIO.registerStructureComponent(ComponentTFFinalCastleMain.class, "TFFCMain");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleStairTower.class, "TFFCStTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleLargeTower.class, "TFFCLaTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleMural.class, "TFFCMur");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleFoundation48.class, "TFFCToF48");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleRoof48Crenellated.class, "TFFCRo48Cr");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleBossGazebo.class, "TFFCBoGaz");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleMazeTower13.class, "TFFCSiTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleDungeonSteps.class, "TFFCDunSt");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleDungeonEntrance.class, "TFFCDunEn");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleDungeonRoom31.class, "TFFCDunR31");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleDungeonExit.class, "TFFCDunEx");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleDungeonForgeRoom.class, "TFFCDunBoR");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleRoof9Crenellated.class, "TFFCRo9Cr");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleRoof13Crenellated.class, "TFFCRo13Cr");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleRoof13Conical.class, "TFFCRo13Con");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleRoof13Peaked.class, "TFFCRo13Pk");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleEntranceTower.class, "TFFCEnTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleEntranceSideTower.class, "TFFCEnSiTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleEntranceBottomTower.class, "TFFCEnBoTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleEntranceStairs.class, "TFFCEnSt");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleBellTower21.class, "TFFCBelTo");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleBridge.class, "TFFCBri");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleFoundation13.class, "TFFCToF13");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleBellFoundation21.class, "TFFCBeF21");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleFoundation13Thorns.class, "TFFCFTh21");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleDamagedTower.class, "TFFCDamT");
		StructureIO.registerStructureComponent(ComponentTFFinalCastleWreckedTower.class, "TFFCWrT");
	}
}
