package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentTemplate;

import java.util.Random;

public abstract class ComponentNagaCourtyardTerraceAbstract extends StructureTFComponentTemplate {

    private final ResourceLocation TERRACE;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardTerraceAbstract(IStructurePieceType piece, CompoundNBT nbt, ResourceLocation terrace) {
        super(piece, nbt);
        TERRACE = terrace;
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardTerraceAbstract(IStructurePieceType type, TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation terrace) {
        super(type, feature, i, x, y, z, rotation);
        TERRACE = terrace;
    }

    @Override
    protected void loadTemplates(TemplateManager templateManager, MinecraftServer server) {
        TEMPLATE = templateManager.getTemplate(TERRACE);
    }

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, MutableBoundingBox structureBoundingBox, ChunkPos chunkPosIn) {
		placeSettings.setBoundingBox(structureBoundingBox).addProcessor(new CourtyardTerraceTemplateProcessor(0.2F));
		TEMPLATE.addBlocksToWorld(world, rotatedPosition, placeSettings, 18);
		return true;
	}
}
