package twilightforest.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import twilightforest.TwilightForestMod;
import twilightforest.worldgen.structures.GenDruidHut;
import twilightforest.worldgen.structures.TFGenGraveyard;
import twilightforest.structures.courtyard.CourtyardStairsTemplateProcessor;
import twilightforest.structures.courtyard.CourtyardTerraceTemplateProcessor;
import twilightforest.structures.courtyard.CourtyardWallTemplateProcessor;

/**
 * Class for registering IStructureProcessorTypes. These are just used for StructureProcessor.getType()
 */
public class TFStructureProcessors {

	public static final StructureProcessorType<?> COURTYARD_TERRACE = registerProcessor("courtyard_terrace", CourtyardTerraceTemplateProcessor.codecTerraceProcessor);
	public static final StructureProcessorType<?> COURTYARD_STAIRS = registerProcessor("courtyard_stairs", CourtyardStairsTemplateProcessor.codecStairsProcessor);
	public static final StructureProcessorType<?> COURTYARD_WALL = registerProcessor("courtyard_wall", CourtyardWallTemplateProcessor.codecWallProcessor);
	public static final StructureProcessorType<?> MOSSY_COBBLE = registerProcessor("mossy_cobble", MossyCobbleTemplateProcessor.codecMossyProcessor);
	public static final StructureProcessorType<?> STONE_BRICKS = registerProcessor("stone_bricks", StoneBricksTemplateProcessor.codecBricksProcessor);
	public static final StructureProcessorType<?> HUT = registerProcessor("hut", GenDruidHut.HutTemplateProcessor.codecHutProcessor);
	public static final StructureProcessorType<?> WEB = registerProcessor("web", TFGenGraveyard.WebTemplateProcessor.codecWebProcessor);

	public static <P extends StructureProcessor> StructureProcessorType<P> registerProcessor(String name, Codec<P> processor) {
		return Registry.register(Registry.STRUCTURE_PROCESSOR, TwilightForestMod.prefix(name), () -> processor);
	}
}
