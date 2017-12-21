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
import twilightforest.structures.StructureTFComponent;

import java.util.Random;

public class ComponentNagaCourtyardWallPadder extends StructureTFComponent {
    private static final ResourceLocation WALL_PADDING = new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_padding");
    private static final ResourceLocation WALL_PADDING_DECAYED = new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_padding_decayed");

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardWallPadder() {
        super();
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardWallPadder(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i);
        this.rotation = rotation;
        this.boundingBox = new StructureBoundingBox(x, y, z, x, y + 11, z + 3);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        BlockPos pos = new BlockPos(this.getBoundingBox().minX, this.getBoundingBox().minY, this.getBoundingBox().minZ);

        MinecraftServer server = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

        PlacementSettings placementSettings = new PlacementSettings()
                .setRotation(this.rotation)
                .setReplacedBlock(Blocks.STRUCTURE_VOID)
                .setBoundingBox(this.getBoundingBox());

        Template template = templateManager.getTemplate(server, WALL_PADDING);
        template.addBlocksToWorldChunk(worldIn, pos, placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_INTEGRITY));

        Template templateBig = templateManager.getTemplate(server, WALL_PADDING);
        templateBig.addBlocksToWorldChunk(worldIn, pos, placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_DECAY));

        return true;
    }
}
