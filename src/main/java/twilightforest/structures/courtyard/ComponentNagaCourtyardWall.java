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

public class ComponentNagaCourtyardWall extends StructureTFComponent {
    private static final ResourceLocation WALL = new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall");
    private static final ResourceLocation WALL_DECAYED = new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_decayed");

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardWall() {
        super();
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardWall(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i);
        this.rotation = rotation;
        this.boundingBox = new StructureBoundingBox(x, y, z, x + 11, y + 8, z + 3);
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

        Template template = templateManager.getTemplate(server, WALL);
        template.addBlocksToWorldChunk(worldIn, pos, placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_INTEGRITY));

        Template templateBig = templateManager.getTemplate(server, WALL_DECAYED);
        templateBig.addBlocksToWorldChunk(worldIn, pos, placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_DECAY));

        return true;
    }
}
