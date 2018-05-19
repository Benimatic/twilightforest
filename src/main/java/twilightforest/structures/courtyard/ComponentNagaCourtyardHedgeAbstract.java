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
import twilightforest.TwilightForestMod;
import twilightforest.structures.StructureTFComponentTemplate;
import twilightforest.util.StructureBoundingBoxUtils;

import java.util.Random;

public abstract class ComponentNagaCourtyardHedgeAbstract extends StructureTFComponentTemplate {
    private final ResourceLocation HEDGE;
    private final ResourceLocation HEDGE_BIG;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeAbstract(ResourceLocation hedge, ResourceLocation hedgeBig) {
        super();
        this.HEDGE = hedge;
        this.HEDGE_BIG = hedgeBig;
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeAbstract(TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation hedge, ResourceLocation hedgeBig) {
        super(feature, i, x, y, z, rotation);
        this.HEDGE = hedge;
        this.HEDGE_BIG = hedgeBig;
    }

    @Override
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox structureBoundingBoxIn) {
        MinecraftServer server = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

        TEMPLATE = templateManager.getTemplate(server, HEDGE);

        BlockPos posForSetting = this.getModifiedTemplatePositionFromRotation();
        this.setBoundingBoxFromTemplate(posForSetting);

        StructureBoundingBox sbb = StructureBoundingBoxUtils.getUnionOfSBBs(this.boundingBox, structureBoundingBoxIn);

        PlacementSettings placementSettings = new PlacementSettings()
                .setRotation(this.rotation)
                .setReplacedBlock(Blocks.STRUCTURE_VOID)
                .setBoundingBox(this.boundingBox);
                //.setBoundingBox(sbb != null ? sbb : this.boundingBox);

        TEMPLATE.addBlocksToWorld(worldIn, posForSetting, new CourtyardStairsTemplateProcessor(posForSetting, placementSettings), placementSettings, 2);

        Template templateBig = templateManager.getTemplate(server, HEDGE_BIG);
        templateBig.addBlocksToWorld(worldIn, posForSetting, placementSettings.setIntegrity(ComponentNagaCourtyardMain.HEDGE_FLOOF));

        return true;
    }
}