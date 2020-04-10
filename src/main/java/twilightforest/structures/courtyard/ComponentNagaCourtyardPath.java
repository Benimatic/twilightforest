package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.StructureTFComponentTemplate;

import java.util.Random;

public class ComponentNagaCourtyardPath extends StructureTFComponentTemplate {

    private static final ResourceLocation PATH = new ResourceLocation(TwilightForestMod.ID, "courtyard/pathway");

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardPath(TemplateManager manager, CompoundNBT nbt) {
        super(NagaCourtyardPieces.TFNCPa, nbt);
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardPath(TFFeature feature, int i, int x, int y, int z) {
        super(NagaCourtyardPieces.TFNCPa, feature, i, x, y, z, Rotation.NONE);
    }

    @Override
    protected void loadTemplates(TemplateManager templateManager, MinecraftServer server) {
        TEMPLATE = templateManager.getTemplate(PATH);
    }

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, MutableBoundingBox structureBoundingBox, ChunkPos chunkPosIn) {
		placeSettings.setBoundingBox(structureBoundingBox);
		TEMPLATE.addBlocksToWorld(world, templatePosition, /*new CourtyardWallTemplateProcessor(templatePosition, placeSettings),*/ placeSettings, 18);
		return true;
	}
}
