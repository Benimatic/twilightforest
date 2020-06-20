package twilightforest.features;

import com.google.common.collect.ImmutableSet;
import com.google.common.math.StatsAccumulator;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFWraith;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;
import twilightforest.structures.RandomizedTemplateProcessor;
import twilightforest.structures.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class TFGenGraveyard extends Feature<NoFeatureConfig> {

	private static final ResourceLocation GRAVEYARD = TwilightForestMod.prefix("landscape/graveyard/graveyard");
	private static final ResourceLocation TRAP = TwilightForestMod.prefix("landscape/graveyard/grave_trap");
	private static final ImmutableSet<Material> MATERIAL_WHITELIST = ImmutableSet.of(Material.EARTH, Material.ORGANIC, Material.LEAVES, Material.WOOD, Material.PLANTS, Material.ROCK);

	public TFGenGraveyard(Function<Dynamic<?>, NoFeatureConfig> config) {
		super(config);
	}

	private static boolean offsetToAverageGroundLevel(World world, BlockPos.Mutable startPos, BlockPos size) {
		StatsAccumulator heights = new StatsAccumulator();

		for (int dx = 0; dx < size.getX(); dx++) {
			for (int dz = 0; dz < size.getZ(); dz++) {

				int x = startPos.getX() + dx;
				int z = startPos.getZ() + dz;

				int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);

				while (y >= 0) {
					BlockState state = world.getBlockState(new BlockPos(x, y, z));
					if (isBlockNotOk(state))
						return false;
					if (isBlockOk(state))
						break;
					y--;
				}

				if (y < 0)
					return false;

				heights.add(y);
			}
		}

		if (heights.populationStandardDeviation() > 2.0) {
			return false;
		}

		int baseY = (int) Math.round(heights.mean());
		int maxY = (int) heights.max();

		startPos.setY(baseY);

		return isAreaClear(world, startPos.up(maxY - baseY + 1), startPos.add(size));
	}

	private static boolean isAreaClear(IBlockReader world, BlockPos min, BlockPos max) {
		for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
			Material material = world.getBlockState(pos).getMaterial();
			if (!material.isReplaceable() && !MATERIAL_WHITELIST.contains(material) && !material.isLiquid()) {
				return false;
			}
		}
		return true;
	}

	private static boolean isBlockOk(BlockState state) {
		Material material = state.getMaterial();
		return material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC || material == Material.SAND;
	}

	private static boolean isBlockNotOk(BlockState state) {
		Material material = state.getMaterial();
		return material == Material.WATER || material == Material.LAVA || state.getBlock() == Blocks.BEDROCK;
	}

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		int flags = 0b10100;
		World world = worldIn.getWorld();
		//Random random = world.getChunk(pos).getRandomWithSeed(987234911L);
		Random random = world.getRandom();

		TemplateManager templatemanager = ((ServerWorld) world).getSaveHandler().getStructureTemplateManager();
		Template base = templatemanager.getTemplate(GRAVEYARD);
		List<Pair<GraveType, Template>> graves = new ArrayList<>();
		Template trap = templatemanager.getTemplate(TRAP);
		for (GraveType type : GraveType.VALUES)
			graves.add(Pair.of(type, templatemanager.getTemplate(type.RL)));

		Rotation[] rotations = Rotation.values();
		Rotation rotation = rotations[random.nextInt(rotations.length)];

		Mirror[] mirrors = Mirror.values();
		Mirror mirror = mirrors[random.nextInt(mirrors.length + 1) % mirrors.length];

		BlockPos transformedSize = base.transformedSize(rotation);
		BlockPos transformedGraveSize = graves.get(0).getValue().transformedSize(rotation);

		ChunkPos chunkpos = new ChunkPos(pos.add(-8, 0, -8));
		ChunkPos chunkendpos = new ChunkPos(pos.add(-8, 0, -8).add(transformedSize));
		MutableBoundingBox structureboundingbox = new MutableBoundingBox(chunkpos.getXStart() + 8, 0, chunkpos.getZStart() + 8, chunkendpos.getXEnd() + 8, 255, chunkendpos.getZEnd() + 8);
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(mirror).setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);

		BlockPos posSnap = chunkpos.getBlock(8, pos.getY() - 1, 8);
		BlockPos.Mutable startPos = new BlockPos.Mutable(posSnap);

		if (!offsetToAverageGroundLevel(world, startPos, transformedSize)) {
			return false;
		}

		BlockPos placementPos = base.getZeroPositionWithTransform(startPos, mirror, rotation).add(1, -1, 0);
		BlockPos size = transformedSize.add(-1, 0, -1);
		BlockPos graveSize = transformedGraveSize.add(-1, 0, -1);

		base.addBlocksToWorld(worldIn, placementPos, placementsettings.addProcessor(new WebTemplateProcessor(0.2F)), flags);

		BlockPos start = startPos.add(1, 1, 0);
		BlockPos end = start.add(size.getX(), 0, size.getZ());

		for (int x = 1; x <= size.getX() - 1; x++)
			for (int z = 1; z <= size.getZ() - 1; z++)
				if (world.isAirBlock(start.add(x, 0, z)) && rand.nextInt(12) == 0)
					world.setBlockState(start.add(x, 0, z), Blocks.COBWEB.getDefaultState(), flags);

		BlockPos inner = start.add(2, 0, 2);
		BlockPos bound = end.add(-2, 0, -2);
		BlockPos innerSize = new BlockPos(bound.getX() - inner.getX(), bound.getY() - inner.getY(), bound.getZ() - inner.getZ());
		BlockPos fixed = inner.add(

				(rotation == Rotation.CLOCKWISE_180 ? graveSize.getX() : 0) + (mirror == Mirror.FRONT_BACK ? transformedGraveSize.getX() - 1 : 0) * (rotation == Rotation.CLOCKWISE_180 ? -1 : 1),

				0,

				(rotation == Rotation.COUNTERCLOCKWISE_90 ? graveSize.getZ() : 0) + (mirror == Mirror.FRONT_BACK ? transformedGraveSize.getZ() - 1 : 0) * (rotation == Rotation.COUNTERCLOCKWISE_90 ? -1 : 1)

		);
		BlockPos fixedSize = innerSize.add(-graveSize.getX(), 0, -graveSize.getZ());
		BlockPos chestloc = new BlockPos(random.nextInt(2) - (mirror == Mirror.FRONT_BACK ? 1 : 0), 1, 0).rotate(rotation);

		for (int x = 0; x <= fixedSize.getX(); x += (rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90 ? 2 : 5))
			for (int z = 0; z <= fixedSize.getZ(); z += (rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180 ? 2 : 5)) {
				if (x == innerSize.getX() / 2 || z == innerSize.getZ() / 2)
					continue;
				BlockPos placement = fixed.add(x, -2, z);
				Pair<GraveType, Template> grave = graves.get(rand.nextInt(graves.size()));
				grave.getValue().addBlocksToWorld(world, placement, placementsettings, flags);
				if (grave.getKey() == GraveType.Full) {
					if (random.nextBoolean()) {
						if (random.nextInt(3) == 0)
							trap.addBlocksToWorld(world, placement.add(new BlockPos(mirror == Mirror.FRONT_BACK ? 1 : -1, 0, mirror == Mirror.LEFT_RIGHT ? 1 : -1).rotate(rotation)), placementsettings, flags);
						if (world.setBlockState(placement.add(chestloc), Blocks.TRAPPED_CHEST.getDefaultState().with(ChestBlock.FACING, Direction.WEST).rotate(rotation).mirror(mirror), flags))
							TFTreasure.graveyard.generateChestContents(world, placement.add(chestloc));
						EntityTFWraith wraith = new EntityTFWraith(TFEntities.wraith, world);
						wraith.setPositionAndUpdate(placement.getX(), placement.getY(), placement.getZ());
						world.addEntity(wraith);
					}
				}
			}

		return true;
	}

	public static class Piece extends TemplateStructurePiece {

		public Piece(IStructurePieceType p_i51339_1_, CompoundNBT p_i51339_2_) {
			super(p_i51339_1_, p_i51339_2_);
		}

		@Override
		protected void handleDataMarker(String s, BlockPos p, IWorld world, Random random, MutableBoundingBox mbb) {
			if ("spawner".equals(s))
				if (random.nextInt(4) == 0) {
					if (world.setBlockState(p, Blocks.SPAWNER.getDefaultState(), 3)) {
						MobSpawnerTileEntity ms = (MobSpawnerTileEntity) world.getTileEntity(p);
						if (ms != null)
							ms.getSpawnerBaseLogic().setEntityType(TFEntities.rising_zombie);
					}
				} else
					world.removeBlock(p, false);
		}
	}

	private enum GraveType {

		Full(TwilightForestMod.prefix("landscape/graveyard/grave_full")),

		Upper(TwilightForestMod.prefix("landscape/graveyard/grave_upper")),

		Lower(TwilightForestMod.prefix("landscape/graveyard/grave_lower"));

		private static final GraveType[] VALUES = values();
		private final ResourceLocation RL;

		GraveType(ResourceLocation rl) {
			this.RL = rl;
		}
	}

	public static class WebTemplateProcessor extends RandomizedTemplateProcessor {

		public WebTemplateProcessor(float integrity) {
			super(integrity);
		}

		public WebTemplateProcessor(Dynamic<?> dynamic) {
			this(dynamic.get("integrity").asFloat(1.0F));
		}

		@Override
		protected IStructureProcessorType getType() {
			return TFStructureProcessors.WEB;
		}

		@Nullable
		@Override
		public Template.BlockInfo process(IWorldReader worldIn, BlockPos pos, Template.BlockInfo p_process_3_, Template.BlockInfo blockInfo, PlacementSettings settings, @Nullable Template template) {
			return blockInfo.state.getBlock() == Blocks.GRASS ? blockInfo : settings.getRandom(pos).nextInt(5) == 0 ? new Template.BlockInfo(pos, Blocks.COBWEB.getDefaultState(), null) : blockInfo;
		}
	}
}
