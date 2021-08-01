package twilightforest.worldgen.structures;

import com.google.common.math.StatsAccumulator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;
import twilightforest.enums.StructureWoodVariant;
import twilightforest.loot.TFTreasure;
import twilightforest.structures.RandomizedTemplateProcessor;
import twilightforest.structures.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenDruidHut extends Feature<NoneFeatureConfiguration> {

	public GenDruidHut(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override // Loosely based on WorldGenFossils FIXME See if we can move this over to purely-datadriven
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		Random random = world.getRandom();

		StructureManager templatemanager = world.getLevel().getServer().getStructureManager();
		StructureTemplate template = templatemanager.getOrCreate(HutType.values()[random.nextInt(HutType.size)].RL);
		if(template == null)
			return false;

		Rotation[] rotations = Rotation.values();
		Rotation rotation = rotations[random.nextInt(rotations.length)];

		Mirror[] mirrors = Mirror.values();
		Mirror mirror = mirrors[random.nextInt(mirrors.length+1) % mirrors.length];

		ChunkPos chunkpos = new ChunkPos(pos.offset(-8, 0, -8));
		BoundingBox structureboundingbox = new BoundingBox(chunkpos.getMinBlockX() + 8, 0, chunkpos.getMinBlockZ() + 8, chunkpos.getMaxBlockX() + 8, 255, chunkpos.getMaxBlockZ() + 8);
		StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setMirror(mirror).setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);

		BlockPos posSnap = chunkpos.getWorldPosition().offset(8, pos.getY() - 1, 8); // Verify this is correct. Originally chunkpos.getBlock(8, pos.getY() - 1, 8);

		BlockPos transformedSize = (BlockPos) template.getSize(rotation);
		int dx = random.nextInt(17 - transformedSize.getX());
		int dz = random.nextInt(17 - transformedSize.getZ());
		posSnap.offset(dx, 0, dz);

		BlockPos.MutableBlockPos startPos = new BlockPos.MutableBlockPos(posSnap.getX(), posSnap.getY(), posSnap.getZ());

		if (!offsetToAverageGroundLevel(world, startPos, transformedSize)) {
			return false;
		}

		BlockPos placementPos = template.getZeroPositionWithTransform(startPos, mirror, rotation);
		template.placeInWorld(world, placementPos, placementPos, placementsettings.clearProcessors().addProcessor(new HutTemplateProcessor(0.0F, random.nextInt(), random.nextInt(), random.nextInt())), random, 20);
		List<StructureTemplate.StructureBlockInfo> data = new ArrayList<>(template.filterBlocks(placementPos, placementsettings, Blocks.STRUCTURE_BLOCK));

		if (random.nextBoolean()) {
			template = templatemanager.getOrCreate(BasementType.values()[random.nextInt(BasementType.size)].getBasement(random.nextBoolean()));
			if(template == null)
				return false;
			placementPos = placementPos.below(12).relative(rotation.rotate(mirror.mirror(Direction.NORTH)), 1).relative(rotation.rotate(mirror.mirror(Direction.EAST)), 1);

			template.placeInWorld(world, placementPos, placementPos, placementsettings.clearProcessors().addProcessor(new HutTemplateProcessor(0.0F, random.nextInt(14), random.nextInt(14), random.nextInt(14))), random, 20);
			data.addAll(template.filterBlocks(placementPos, placementsettings, Blocks.STRUCTURE_BLOCK));
		}

		data.forEach(info -> {
			if (info.nbt != null && StructureMode.valueOf(info.nbt.getString("mode")) == StructureMode.DATA) {
				String s = info.nbt.getString("metadata");
				BlockPos blockPos = info.pos;
			/*
   		        `spawner` will place a Druid spawner.

   		        `loot` will place a chest facing the was-North.

   		        `lootT` will place a trapped chest facing the was-North.

   		        `lootW` will place a chest facing the was-West.
   		        `lootS` will place a chest facing the was-South.

   		        `lootET` will place a trapped chest facing the was-East.
   		        `lootNT` will place a trapped chest facing the was-North.
   		         */
			// removeBlock calls are required due to WorldGenRegion jank with cached TEs, this ensures the correct TE is used
				if ("spawner".equals(s)) {
					if (world.removeBlock(blockPos, false) && world.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 16 | 2)) {
						SpawnerBlockEntity ms = (SpawnerBlockEntity) world.getBlockEntity(blockPos);

						if (ms != null) {
							ms.getSpawner().setEntityId(TFEntities.skeleton_druid);
						}
					}
				} else if (s.startsWith("loot")) {
					world.removeBlock(blockPos, false);
					BlockState chest = s.endsWith("T") ? Blocks.TRAPPED_CHEST.defaultBlockState() : Blocks.CHEST.defaultBlockState();

					switch (s.substring(5, 6)) {
						case "L":
							chest = chest.setValue(ChestBlock.TYPE, mirror != Mirror.NONE ? ChestType.RIGHT : ChestType.LEFT);
							break;
						case "R":
							chest = chest.setValue(ChestBlock.TYPE, mirror != Mirror.NONE ? ChestType.LEFT : ChestType.RIGHT);
							break;
						default:
							chest = chest.setValue(ChestBlock.TYPE, ChestType.SINGLE);
							break;
					}

					switch (s.substring(4, 5)) {
						case "W":
							chest = chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.WEST)));
							break;
						case "E":
							chest = chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.EAST)));
							break;
						case "S":
							chest = chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.SOUTH)));
							break;
						default:
							chest = chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.NORTH)));
							break;
					}

					if (world.setBlock(blockPos, chest, 16 | 2)) {
						TFTreasure.basement.generateChestContents(world, blockPos);
					}
				}
			}
		});

		return true;
	}

    private static boolean offsetToAverageGroundLevel(WorldGenLevel world, BlockPos.MutableBlockPos startPos, BlockPos size) {
        StatsAccumulator heights = new StatsAccumulator();

        for (int dx = 0; dx < size.getX(); dx++) {
            for (int dz = 0; dz < size.getZ(); dz++) {

                int x = startPos.getX() + dx;
                int z = startPos.getZ() + dz;

                int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                while (y >= 0) {
                    BlockState state = world.getBlockState(new BlockPos(x, y, z));
                    if (isBlockNotOk(state)) return false;
                    if (isBlockOk(state)) break;
                    y--;
                }

                if (y < 0) return false;

                heights.add(y);
            }
        }

        if (heights.populationStandardDeviation() > 2.0) {
            return false;
        }

        int baseY = (int) Math.round(heights.mean());
        int maxY = (int) heights.max();

        startPos.setY(baseY);

        return isAreaClear(world, startPos.above(maxY - baseY + 1), startPos.offset(size));
    }

    private static boolean isAreaClear(LevelAccessor world, BlockPos min, BlockPos max) {
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (!world.getBlockState(pos).getMaterial().isReplaceable()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBlockOk(BlockState state) {
        Material material = state.getMaterial();
        return material == Material.STONE || material == Material.DIRT || material == Material.GRASS || material == Material.SAND;
    }

    private static boolean isBlockNotOk(BlockState state) {
        Material material = state.getMaterial();
        return material == Material.WATER || material == Material.LAVA || state.getBlock() == Blocks.BEDROCK;
    }

    private enum HutType {

        REGULAR    (TwilightForestMod.prefix("landscape/druid_hut/druid_hut"       )),
        SIDEWAYS   (TwilightForestMod.prefix("landscape/druid_hut/druid_sideways"  )),
        DOUBLE_DECK(TwilightForestMod.prefix("landscape/druid_hut/druid_doubledeck"));

        private final ResourceLocation RL;

        HutType(ResourceLocation rl) {
            this.RL = rl;
            increment();
        }

        private static int size;

        private static void increment() {
            ++size;
        }
    }

    private enum BasementType {

        STUDY  (TwilightForestMod.prefix("landscape/druid_hut/basement_study"  ), TwilightForestMod.prefix("landscape/druid_hut/basement_study_trap"  )),
        SHELVES(TwilightForestMod.prefix("landscape/druid_hut/basement_shelves"), TwilightForestMod.prefix("landscape/druid_hut/basement_shelves_trap")),
        GALLERY(TwilightForestMod.prefix("landscape/druid_hut/basement_gallery"), TwilightForestMod.prefix("landscape/druid_hut/basement_gallery_trap"));

        private final ResourceLocation RL;
        private final ResourceLocation RL_TRAP;

        BasementType(ResourceLocation rl, ResourceLocation rlTrap) {
            this.RL = rl;
            this.RL_TRAP = rlTrap;
            increment();
        }

        private static int size;

        private static void increment() {
            ++size;
        }

        private ResourceLocation getBasement(boolean trapped) {
            return trapped ? RL_TRAP : RL;
        }
    }

    public static class HutTemplateProcessor extends RandomizedTemplateProcessor {

		private final StructureWoodVariant OAK_SWIZZLE;
		private final StructureWoodVariant SPRUCE_SWIZZLE;
		private final StructureWoodVariant BIRCH_SWIZZLE;
		public int dummy = 0;
		public static final Codec<HutTemplateProcessor> codecHutProcessor = RecordCodecBuilder.create((instance) ->
				instance.group(
					Codec.FLOAT.fieldOf("integrity").orElse(1.0F).forGetter((obj) -> obj.integrity),
					Codec.INT.fieldOf("integrity").orElse(0).forGetter((obj) -> obj.dummy),
					Codec.INT.fieldOf("integrity").orElse(0).forGetter((obj) -> obj.dummy),
					Codec.INT.fieldOf("integrity").orElse(0).forGetter((obj) -> obj.dummy))
				.apply(instance, HutTemplateProcessor::new));



		public HutTemplateProcessor(float integrity, int oakSwizzle, int spruceSwizzle, int birchSwizzle) {
            super(integrity);
			int limit = StructureWoodVariant.values().length;
			this.OAK_SWIZZLE    = StructureWoodVariant.values()[ Math.floorMod(oakSwizzle   , limit) ];
			this.SPRUCE_SWIZZLE = StructureWoodVariant.values()[ Math.floorMod(spruceSwizzle, limit) ];
			this.BIRCH_SWIZZLE  = StructureWoodVariant.values()[ Math.floorMod(birchSwizzle , limit) ];
        }

		@Override
		protected StructureProcessorType<?> getType() {
			return TFStructureProcessors.HUT;
		}

		@Nullable
		@Override
		public StructureTemplate.StructureBlockInfo process(LevelReader worldIn, BlockPos pos, BlockPos piecepos, StructureTemplate.StructureBlockInfo p_215194_3_, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {

			Random random = settings.getRandom(pos);

			if (!shouldPlaceBlock(random)) return null;

			BlockState state = blockInfo.state;
			Block block = state.getBlock();

			if (block == Blocks.COBBLESTONE)
				return random.nextBoolean() ? blockInfo : new StructureTemplate.StructureBlockInfo(blockInfo.pos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), null);

			if (block == Blocks.COBBLESTONE_WALL)
				return random.nextBoolean() ? blockInfo : new StructureTemplate.StructureBlockInfo(blockInfo.pos, Blocks.MOSSY_COBBLESTONE_WALL.defaultBlockState(), null);

			if (block == Blocks.STONE_BRICKS) { // TODO: By default it's not chiseled stone as that's a different block
				return random.nextBoolean() ? blockInfo : new StructureTemplate.StructureBlockInfo(blockInfo.pos, random.nextInt(2) == 0 ? Blocks.CRACKED_STONE_BRICKS.defaultBlockState() : Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), null);
			}

			StructureWoodVariant type = StructureWoodVariant.getVariantFromBlock(block);
			if (type != null) {
				switch (type) {
					case OAK:
						return new StructureTemplate.StructureBlockInfo(blockInfo.pos, StructureWoodVariant.modifyBlockWithType(state, OAK_SWIZZLE   ), null);
					case SPRUCE:
						return new StructureTemplate.StructureBlockInfo(blockInfo.pos, StructureWoodVariant.modifyBlockWithType(state, SPRUCE_SWIZZLE), null);
					case BIRCH:
						return new StructureTemplate.StructureBlockInfo(blockInfo.pos, StructureWoodVariant.modifyBlockWithType(state, BIRCH_SWIZZLE ), null);
				}
			}

			return blockInfo;
		}
    }
}
