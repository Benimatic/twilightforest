package twilightforest.structures.courtyard;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.MossyCobbleTemplateProcessor;
import twilightforest.structures.StructureTFComponentTemplate;

import java.util.Random;

public class ComponentNagaCourtyardWallAbstract extends StructureTFComponentTemplate {

    private final ResourceLocation WALL;
    private final ResourceLocation WALL_DECAYED;

    private Template decayTemplate;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardWallAbstract(ResourceLocation wall, ResourceLocation wall_decayed) {
        super();
        this.WALL = wall;
        this.WALL_DECAYED = wall_decayed;
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardWallAbstract(TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation wall, ResourceLocation wall_decayed) {
        super(feature, i, x, y, z, rotation);
        this.WALL = wall;
        this.WALL_DECAYED = wall_decayed;
    }

    @Override
    protected void loadTemplates(TemplateManager templateManager, MinecraftServer server) {
        TEMPLATE = templateManager.getTemplate(WALL);
        decayTemplate = templateManager.getTemplate(WALL_DECAYED);
    }

	@Override
	public boolean addComponentParts(IWorld world, Random random, MutableBoundingBox structureBoundingBox, ChunkPos chunkPosIn) {
		placeSettings.setBoundingBox(structureBoundingBox);
		TEMPLATE.addBlocksToWorld(world, rotatedPosition, placeSettings.addProcessor(new CourtyardWallTemplateProcessor(rotatedPosition, placeSettings)).addProcessor(new IntegrityProcessor(ComponentNagaCourtyardMain.WALL_INTEGRITY)), 18);
		decayTemplate.addBlocksToWorld(world, rotatedPosition, placeSettings.addProcessor(new MossyCobbleTemplateProcessor(rotatedPosition, placeSettings)).addProcessor(new IntegrityProcessor(ComponentNagaCourtyardMain.WALL_DECAY)), 18);
		return true;
	}
}
