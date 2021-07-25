package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentTemplate;

import java.util.Random;

public abstract class NagaCourtyardTerraceAbstractComponent extends TFStructureComponentTemplate {

    private final ResourceLocation TERRACE;

    public NagaCourtyardTerraceAbstractComponent(StructureManager manager, StructurePieceType piece, CompoundTag nbt, ResourceLocation terrace) {
        super(manager, piece, nbt);
        TERRACE = terrace;
    }

    public NagaCourtyardTerraceAbstractComponent(StructurePieceType type, TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation terrace) {
        super(type, feature, i, x, y, z, rotation);
        TERRACE = terrace;
    }

    @Override
    protected void loadTemplates(StructureManager templateManager) {
        TEMPLATE = templateManager.get(TERRACE);
    }

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random random, BoundingBox structureBoundingBox, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeSettings.setBoundingBox(structureBoundingBox).clearProcessors().addProcessor(new CourtyardTerraceTemplateProcessor(0.0F));
		TEMPLATE.placeInWorld(world, rotatedPosition, rotatedPosition, placeSettings, random, 18);
		return true;
	}
}
