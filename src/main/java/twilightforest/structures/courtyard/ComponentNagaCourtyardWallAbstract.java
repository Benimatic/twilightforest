package twilightforest.structures.courtyard;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.MossyCobbleTemplateProcessor;
import twilightforest.structures.StructureTFComponentTemplate;
import twilightforest.util.StructureBoundingBoxUtils;

import java.util.Random;

public class ComponentNagaCourtyardWallAbstract extends StructureTFComponentTemplate {
    private final ResourceLocation WALL;
    private final ResourceLocation WALL_DECAYED;

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
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox structureBoundingBoxIn) {
        MinecraftServer server = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

        TEMPLATE = templateManager.getTemplate(server, WALL);

        BlockPos posForSetting = this.getModifiedTemplatePositionFromRotation();
        this.setBoundingBoxFromTemplate(posForSetting);

        StructureBoundingBox sbb = StructureBoundingBoxUtils.getUnionOfSBBs(this.boundingBox, structureBoundingBoxIn);

        PlacementSettings placementSettings = new PlacementSettings()
                .setRotation(this.rotation)
                .setReplacedBlock(Blocks.STRUCTURE_VOID)
                .setBoundingBox(this.boundingBox);
                //.setBoundingBox(sbb != null ? sbb : this.boundingBox);

        TEMPLATE.addBlocksToWorld(worldIn, posForSetting, new CourtyardWallTemplateProcessor(posForSetting, placementSettings), placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_INTEGRITY), 2);

        Template temDecay = templateManager.getTemplate(server, WALL_DECAYED);
        temDecay.addBlocksToWorld(worldIn, posForSetting, new MossyCobbleTemplateProcessor(posForSetting, placementSettings), placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_DECAY), 2);

        return true;
    }
}
