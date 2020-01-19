package twilightforest.structures.minotaurmaze;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartLabyrinth;


public class TFMinotaurMazePieces {


	public static void registerPieces() {
		StructureIO.registerStructure(StructureStartLabyrinth.class, "TFLr");

		StructureIO.registerStructureComponent(ComponentTFMazeCorridor.class, "TFMMC");
		StructureIO.registerStructureComponent(ComponentTFMazeCorridorIronFence.class, "TFMMCIF");
		StructureIO.registerStructureComponent(ComponentTFMazeCorridorRoots.class, "TFMMCR");
		StructureIO.registerStructureComponent(ComponentTFMazeCorridorShrooms.class, "TFMMCS");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEnd.class, "TFMMDE");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndChest.class, "TFMMDEC");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndFountain.class, "TFMMDEF");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndFountainLava.class, "TFMMDEFL");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndPainting.class, "TFMMDEP");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndRoots.class, "TFMMDER");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndShrooms.class, "TFMMDES");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndTorches.class, "TFMMDET");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndTrappedChest.class, "TFMMDETrC");
		StructureIO.registerStructureComponent(ComponentTFMazeDeadEndTripwireChest.class, "TFMMDETC");
		StructureIO.registerStructureComponent(ComponentTFMazeEntranceShaft.class, "TFMMES");
		StructureIO.registerStructureComponent(ComponentTFMazeMound.class, "TFMMMound");
		StructureIO.registerStructureComponent(ComponentTFMazeMushRoom.class, "TFMMMR");
		StructureIO.registerStructureComponent(ComponentTFMazeRoom.class, "TFMMR");
		StructureIO.registerStructureComponent(ComponentTFMazeRoomBoss.class, "TFMMRB");
		StructureIO.registerStructureComponent(ComponentTFMazeRoomCollapse.class, "TFMMRC");
		StructureIO.registerStructureComponent(ComponentTFMazeRoomExit.class, "TFMMRE");
		StructureIO.registerStructureComponent(ComponentTFMazeRoomFountain.class, "TFMMRF");
		StructureIO.registerStructureComponent(ComponentTFMazeRoomSpawnerChests.class, "TFMMRSC");
		StructureIO.registerStructureComponent(ComponentTFMazeRoomVault.class, "TFMMRV");
		StructureIO.registerStructureComponent(ComponentTFMazeRuins.class, "TFMMRuins");
		StructureIO.registerStructureComponent(ComponentTFMazeUpperEntrance.class, "TFMMUE");
		StructureIO.registerStructureComponent(ComponentTFMinotaurMaze.class, "TFMMaze");
	}

}