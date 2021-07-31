package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.TFStructureComponentTemplate;

import java.util.Random;

public class NagaCourtyardPathComponent extends TFStructureComponentTemplate {

    private static final ResourceLocation PATH = new ResourceLocation(TwilightForestMod.ID, "courtyard/pathway");

    public NagaCourtyardPathComponent(StructureManager manager, CompoundTag nbt) {
        super(manager, NagaCourtyardPieces.TFNCPa, nbt);
    }

    public NagaCourtyardPathComponent(TFFeature feature, int i, int x, int y, int z) {
        super(NagaCourtyardPieces.TFNCPa, feature, i, x, y, z, Rotation.NONE);
    }

    @Override
    protected void loadTemplates(StructureManager templateManager) {
        TEMPLATE = templateManager.getOrCreate(PATH);
    }

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random random, BoundingBox structureBoundingBox, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeSettings.setBoundingBox(structureBoundingBox).addProcessor(new CourtyardWallTemplateProcessor(0.0F));
		TEMPLATE.placeInWorld(world, templatePosition, templatePosition, placeSettings, random, 18);
		return true;
	}
}
