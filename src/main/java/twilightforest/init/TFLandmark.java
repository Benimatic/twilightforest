package twilightforest.init;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.world.components.structures.*;
import twilightforest.world.components.structures.courtyard.CourtyardMain;
import twilightforest.world.components.structures.darktower.DarkTowerMainComponent;
import twilightforest.world.components.structures.finalcastle.FinalCastleMainComponent;
import twilightforest.world.components.structures.icetower.IceTowerMainComponent;
import twilightforest.world.components.structures.lichtower.TowerMainComponent;
import twilightforest.world.components.structures.minotaurmaze.MazeRuinsComponent;
import twilightforest.world.components.structures.mushroomtower.MushroomTowerMainComponent;
import twilightforest.world.components.structures.stronghold.StrongholdEntranceComponent;
import twilightforest.world.components.structures.trollcave.TrollCaveMainComponent;

public class TFLandmark {
	public static final TFLandmark NOTHING = new TFLandmark( 0, "no_feature", false, false);
	public static final TFLandmark SMALL_HILL = new TFLandmark( 1, "small_hollow_hill", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new HollowHillComponent(TFStructurePieceTypes.TFHill.get(), 0, size, x - 3, y - 2, z - 3);
		}
	};
	public static final TFLandmark MEDIUM_HILL = new TFLandmark( 2, "medium_hollow_hill", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new HollowHillComponent(TFStructurePieceTypes.TFHill.get(), 0, size, x - 7, y - 5, z - 7);
		}
	};
	public static final TFLandmark LARGE_HILL = new TFLandmark( 3, "large_hollow_hill", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new HollowHillComponent(TFStructurePieceTypes.TFHill.get(), 0, size, x - 11, y - 5, z - 11);
		}
	};
	public static final TFLandmark HEDGE_MAZE = new TFLandmark( 2, "hedge_maze", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new HedgeMazeComponent(0, x + 1, chunkGenerator.getSeaLevel() + 8, z + 1);
		}
	};
	public static final TFLandmark QUEST_GROVE = new TFLandmark( 1, "quest_grove", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new QuestGrove(structureManager, new BlockPos(x - 12, chunkGenerator.getSeaLevel() + 5, z - 12));
		}
	};
	public static final TFLandmark NAGA_COURTYARD = new TFLandmark( 3, "naga_courtyard", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new CourtyardMain(rand, 0, x + 1, chunkGenerator.getSeaLevel() + 5, z + 1, structureManager);
		}
	};
	public static final TFLandmark LICH_TOWER = new TFLandmark( 1, "lich_tower" ) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new TowerMainComponent(rand, 0, x, y, z);
		}
	};
	public static final TFLandmark HYDRA_LAIR = new TFLandmark( 2, "hydra_lair", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new HydraLairComponent(0, x - 7, y, z - 7);
		}
	};
	public static final TFLandmark LABYRINTH = new TFLandmark( 3, "labyrinth" ) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new MazeRuinsComponent(0, x, y, z);
		}

	};
	public static final TFLandmark DARK_TOWER = new TFLandmark( 1, "dark_tower" ) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new DarkTowerMainComponent(rand, 0, x, y, z);
		}
	};
	public static final TFLandmark KNIGHT_STRONGHOLD = new TFLandmark( 3, "knight_stronghold" ) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new StrongholdEntranceComponent(0, x, y + 5, z);
		}

	};
	public static final TFLandmark YETI_CAVE = new TFLandmark( 2, "yeti_lairs", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new YetiCaveComponent(0, x, y, z);
		}
	};
	public static final TFLandmark ICE_TOWER = new TFLandmark( 2, "ice_tower" ) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new IceTowerMainComponent(rand, 0, x, y, z);
		}
	};
	// TODO split cloud giants from this
	public static final TFLandmark TROLL_CAVE = new TFLandmark( 4, "troll_lairs", true, true) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new TrollCaveMainComponent(TFStructurePieceTypes.TFTCMai.get(), 0, x, y, z);
		}
	};
	public static final TFLandmark FINAL_CASTLE = new TFLandmark( 4, "final_castle") {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new FinalCastleMainComponent(0, x, y, z);
		}
	};
	public static final TFLandmark MUSHROOM_TOWER = new TFLandmark( 2, "mushroom_tower", false, false) {
		@Override
		public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
			return new MushroomTowerMainComponent(rand, 0, x, y, z);
		}
	};
	public static final TFLandmark QUEST_ISLAND = new TFLandmark( 1, "quest_island", false, false);
	//public static final TFLandmark DRUID_GROVE    = new TFLandmark( 1, "druid_grove"   , false, false);
	//public static final TFLandmark FLOATING_RUINS = new TFLandmark( 3, "floating_ruins", false, false);
	//public static final TFLandmark WORLD_TREE = new TFLandmark( 3, "world_tree", false, false);

	public final int size;
	public final String name;
	// Tells the chunkgenerator if there's an associated structure.
	public final boolean isStructureEnabled;
	// Tells the chunkgenerator the terrain changes around the structure.
	public final boolean requiresTerraforming;

	private static int maxPossibleSize;

	private TFLandmark(int size, String name) {
		this(size, name, true, false);
	}

	private TFLandmark(int size, String name, boolean isStructureEnabled, boolean requiresTerraforming) {
		this.size = size;
		this.name = name;
		this.isStructureEnabled = isStructureEnabled;
		this.requiresTerraforming = requiresTerraforming;

		maxPossibleSize = Math.max(this.size, maxPossibleSize);
	}

	@Deprecated // Not good practice - TODO The root need for this method can be fixed
	public static int getMaxSearchSize() {
		return maxPossibleSize;
	}

	@Deprecated // TODO Deleting this method will break maps - best to wait until new MC version before committing to it.
	@Nullable
	public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
		return null;
	}

	@NotNull
	public static BoundingBox getComponentToAddBoundingBox(int x, int y, int z, int minX, int minY, int minZ, int spanX, int spanY, int spanZ, @Nullable Direction dir, boolean centerBounds) {
		// CenterBounds is true for ONLY Hollow Hills, Hydra Lair, & Yeti Caves
		if(centerBounds) {
			x += (spanX + minX) / 4;
			y += (spanY + minY) / 4;
			z += (spanZ + minZ) / 4;
		}
		return switch (dir) {
			case WEST -> // '\001'
					new BoundingBox(x - spanZ + minZ, y + minY, z + minX, x + minZ, y + spanY + minY, z + spanX + minX);
			case NORTH -> // '\002'
					new BoundingBox(x - spanX - minX, y + minY, z - spanZ - minZ, x - minX, y + spanY + minY, z - minZ);
			case EAST -> // '\003'
					new BoundingBox(x + minZ, y + minY, z - spanX, x + spanZ + minZ, y + spanY + minY, z + minX);
			default -> // '\0'
					new BoundingBox(x + minX, y + minY, z + minZ, x + spanX + minX, y + spanY + minY, z + spanZ + minZ);
		};
	}

	private static final ImmutableMap<String, TFLandmark> NAME_2_TYPE = Util.make(() -> ImmutableMap.<String, TFLandmark>builder()
			.put("mushroom_tower", TFLandmark.MUSHROOM_TOWER)
			.put("small_hollow_hill", TFLandmark.SMALL_HILL)
			.put("medium_hollow_hill", TFLandmark.MEDIUM_HILL)
			.put("large_hollow_hill", TFLandmark.LARGE_HILL)
			.put("hedge_maze", TFLandmark.HEDGE_MAZE)
			.put("quest_grove", TFLandmark.QUEST_GROVE)
			.put("quest_island", TFLandmark.QUEST_ISLAND)
			.put("naga_courtyard", TFLandmark.NAGA_COURTYARD)
			.put("lich_tower", TFLandmark.LICH_TOWER)
			.put("hydra_lair", TFLandmark.HYDRA_LAIR)
			.put("labyrinth", TFLandmark.LABYRINTH)
			.put("dark_tower", TFLandmark.DARK_TOWER)
			.put("knight_stronghold", TFLandmark.KNIGHT_STRONGHOLD)
			.put("yeti_lairs", TFLandmark.YETI_CAVE)
			.put("ice_tower", TFLandmark.ICE_TOWER)
			.put("troll_lairs", TFLandmark.TROLL_CAVE)
			.put("final_castle", TFLandmark.FINAL_CASTLE)
			.build());

	public static final Codec<TFLandmark> CODEC = Codec.STRING.comapFlatMap(
			name -> TFLandmark.NAME_2_TYPE.containsKey(name) ? DataResult.success(TFLandmark.NAME_2_TYPE.get(name)) : DataResult.error(() -> "Landmark " + name + " not recognized!"),
			tfFeature -> tfFeature.name
	);
}
