package twilightforest.structures.courtyard;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentTemplate;
import twilightforest.util.StructureBoundingBoxUtils;

import java.util.Random;

public abstract class ComponentNagaCourtyardTerraceAbstract extends StructureTFComponentTemplate {
    private final ResourceLocation TERRACE;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardTerraceAbstract(ResourceLocation terrace) {
        super();
        TERRACE = terrace;
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardTerraceAbstract(TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation terrace) {
        super(feature, i, x, y, z, rotation);
        TERRACE = terrace;
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        MinecraftServer server = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

        TEMPLATE = templateManager.getTemplate(server, TERRACE);

        BlockPos posForSetting = this.getModifiedTemplatePositionFromRotation();
        this.setBoundingBoxFromTemplate(posForSetting);

        StructureBoundingBox sbb = StructureBoundingBoxUtils.getUnionOfSBBs(this.boundingBox, structureBoundingBoxIn);

        PlacementSettings placementSettings = new PlacementSettings()
                .setRotation(this.rotation)
                .setReplacedBlock(Blocks.STRUCTURE_VOID)
                .setBoundingBox(this.boundingBox);
                //.setBoundingBox(sbb != null ? sbb : this.boundingBox);

        TEMPLATE.addBlocksToWorld(worldIn, posForSetting, new CourtyardTerraceTemplateProcessor(posForSetting, placementSettings), placementSettings, 2);

        return true;
    }
}
