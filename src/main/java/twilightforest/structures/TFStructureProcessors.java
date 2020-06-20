package twilightforest.structures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import twilightforest.TwilightForestMod;
import twilightforest.features.GenDruidHut;
import twilightforest.features.TFGenGraveyard;
import twilightforest.structures.courtyard.CourtyardStairsTemplateProcessor;
import twilightforest.structures.courtyard.CourtyardTerraceTemplateProcessor;
import twilightforest.structures.courtyard.CourtyardWallTemplateProcessor;

/**
 * Class for registering IStructureProcessorTypes. These are just used for StructureProcessor.getType()
 */
public class TFStructureProcessors {

	public static final IStructureProcessorType COURTYARD_TERRACE = registerProcessor("courtyard_terrace", CourtyardTerraceTemplateProcessor::new);
	public static final IStructureProcessorType COURTYARD_STAIRS = registerProcessor("courtyard_stairs", CourtyardStairsTemplateProcessor::new);
	public static final IStructureProcessorType COURTYARD_WALL = registerProcessor("courtyard_wall", CourtyardWallTemplateProcessor::new);
	public static final IStructureProcessorType MOSSY_COBBLE = registerProcessor("mossy_cobble", MossyCobbleTemplateProcessor::new);
	public static final IStructureProcessorType HUT = registerProcessor("hut", GenDruidHut.HutTemplateProcessor::new);
	public static final IStructureProcessorType WEB = registerProcessor("hut", TFGenGraveyard.WebTemplateProcessor::new);

	public static IStructureProcessorType registerProcessor(String name, IStructureProcessorType processor) {
		return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(TwilightForestMod.ID, name), processor);
	}
}
