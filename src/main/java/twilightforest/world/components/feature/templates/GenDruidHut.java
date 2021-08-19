package twilightforest.world.components.feature.templates;

import com.google.common.math.StatsAccumulator;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;

import java.util.Random;

public class GenDruidHut extends Feature<NoneFeatureConfiguration> {
	public GenDruidHut(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override // Loosely based on WorldGenFossils
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

		ChunkPos chunkpos = new ChunkPos(pos);
		BoundingBox structureMask = new BoundingBox(chunkpos.getMinBlockX(), world.getMinBuildHeight(), chunkpos.getMinBlockZ(), chunkpos.getMaxBlockX(), world.getMaxBuildHeight(), chunkpos.getMaxBlockZ());
		StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setMirror(mirror).setRotation(rotation).setBoundingBox(structureMask).setRandom(random);

		BlockPos posSnap = chunkpos.getWorldPosition().offset(0, pos.getY() - 1, 0); // Verify this is correct. Originally chunkpos.getBlock(8, pos.getY() - 1, 8);

		Vec3i transformedSize = template.getSize(rotation);
		int dx = random.nextInt(16 - transformedSize.getX());
		int dz = random.nextInt(16 - transformedSize.getZ());
		posSnap.offset(dx, 0, dz);

		BlockPos.MutableBlockPos startPos = new BlockPos.MutableBlockPos(posSnap.getX(), posSnap.getY(), posSnap.getZ());

		if (!offsetToAverageGroundLevel(world, startPos, transformedSize)) {
			return false;
		}

		BlockPos placementPos = template.getZeroPositionWithTransform(startPos, mirror, rotation);
		template.placeInWorld(world, placementPos, placementPos, placementsettings.clearProcessors().addProcessor(new HutTemplateProcessor(0.0F, random.nextInt(), random.nextInt(), random.nextInt())), random, 20);

		for (StructureTemplate.StructureBlockInfo info : template.filterBlocks(placementPos, placementsettings, Blocks.STRUCTURE_BLOCK)) {
            processData(info, world, rotation, mirror);
        }

		if (random.nextBoolean()) {
			template = templatemanager.getOrCreate(BasementType.values()[random.nextInt(BasementType.size)].getBasement(random.nextBoolean()));
			if(template == null)
				return false;
			placementPos = placementPos.below(12).relative(rotation.rotate(mirror.mirror(Direction.NORTH)), 1).relative(rotation.rotate(mirror.mirror(Direction.EAST)), 1);

			template.placeInWorld(world, placementPos, placementPos, placementsettings.clearProcessors().addProcessor(new HutTemplateProcessor(0.0F, random.nextInt(14), random.nextInt(14), random.nextInt(14))), random, 20);

            for (StructureTemplate.StructureBlockInfo info : template.filterBlocks(placementPos, placementsettings, Blocks.STRUCTURE_BLOCK)) {
                processData(info, world, rotation, mirror);
            }
		}

		return true;
	}

	private static void processData(StructureTemplate.StructureBlockInfo info, WorldGenLevel world, Rotation rotation, Mirror mirror) {
        if (info.nbt != null && StructureMode.valueOf(info.nbt.getString("mode")) == StructureMode.DATA) {
            String s = info.nbt.getString("metadata");
            BlockPos blockPos = info.pos;
            /**
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
                    BlockEntity tile = world.getBlockEntity(blockPos);

                    if (tile instanceof SpawnerBlockEntity ms) {
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
    }

    private static boolean offsetToAverageGroundLevel(WorldGenLevel world, BlockPos.MutableBlockPos startPos, Vec3i size) {
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
}
