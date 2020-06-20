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
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentTemplate;

import java.util.Random;

public abstract class ComponentNagaCourtyardHedgeAbstract extends StructureTFComponentTemplate {

    private final ResourceLocation HEDGE;
    private final ResourceLocation HEDGE_BIG;

    private Template templateBig;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeAbstract(IStructurePieceType piece, CompoundNBT nbt, ResourceLocation hedge, ResourceLocation hedgeBig) {
        super(piece, nbt);
        this.HEDGE = hedge;
        this.HEDGE_BIG = hedgeBig;
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeAbstract(IStructurePieceType type, TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation hedge, ResourceLocation hedgeBig) {
        super(type, feature, i, x, y, z, rotation);
        this.HEDGE = hedge;
        this.HEDGE_BIG = hedgeBig;
    }

    @Override
    protected void loadTemplates(TemplateManager templateManager, MinecraftServer server) {
        TEMPLATE = templateManager.getTemplate(HEDGE);
        templateBig = templateManager.getTemplate(HEDGE_BIG);
    }

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random randomIn, MutableBoundingBox structureBoundingBox, ChunkPos chunkPosIn) {
		placeSettings.setBoundingBox(structureBoundingBox).addProcessor(new CourtyardStairsTemplateProcessor(0.2F));
		TEMPLATE.addBlocksToWorld(world, rotatedPosition, placeSettings, 18);
		templateBig.addBlocksToWorld(world, rotatedPosition, placeSettings.copy().addProcessor(new IntegrityProcessor(ComponentNagaCourtyardMain.HEDGE_FLOOF)), 18);
		return true;
	}
}
