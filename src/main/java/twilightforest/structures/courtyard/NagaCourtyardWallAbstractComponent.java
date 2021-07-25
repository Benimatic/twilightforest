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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.MossyCobbleTemplateProcessor;
import twilightforest.structures.TFStructureComponentTemplate;

import java.util.Random;

public class NagaCourtyardWallAbstractComponent extends TFStructureComponentTemplate {

    private final ResourceLocation WALL;
    private final ResourceLocation WALL_DECAYED;

    private StructureTemplate decayTemplate;

    public NagaCourtyardWallAbstractComponent(StructureManager manager, StructurePieceType piece, CompoundTag nbt, ResourceLocation wall, ResourceLocation wall_decayed) {
        super(manager, piece, nbt);
        this.WALL = wall;
        this.WALL_DECAYED = wall_decayed;
    }

    public NagaCourtyardWallAbstractComponent(StructurePieceType type, TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation wall, ResourceLocation wall_decayed) {
        super(type, feature, i, x, y, z, rotation);
        this.WALL = wall;
        this.WALL_DECAYED = wall_decayed;
    }

    @Override
    protected void loadTemplates(StructureManager templateManager) {
        TEMPLATE = templateManager.get(WALL);
        decayTemplate = templateManager.get(WALL_DECAYED);
    }

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random random, BoundingBox structureBoundingBox, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeSettings.setBoundingBox(structureBoundingBox).clearProcessors();
		TEMPLATE.placeInWorld(world, rotatedPosition, rotatedPosition, placeSettings.addProcessor(new CourtyardWallTemplateProcessor(0.0F)).addProcessor(new BlockRotProcessor(NagaCourtyardMainComponent.WALL_INTEGRITY)).addProcessor(BlockIgnoreProcessor.AIR), random, 18);
		decayTemplate.placeInWorld(world, rotatedPosition, rotatedPosition, placeSettings.clearProcessors().addProcessor(new MossyCobbleTemplateProcessor(0.0F)).addProcessor(new BlockRotProcessor(NagaCourtyardMainComponent.WALL_DECAY)), random, 18);
		return true;
	}
}
