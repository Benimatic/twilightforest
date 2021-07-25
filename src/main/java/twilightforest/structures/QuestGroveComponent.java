package twilightforest.structures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.entity.TFEntities;
import twilightforest.entity.passive.QuestRamEntity;
import twilightforest.util.ColorUtil;

import java.util.Random;

public class QuestGroveComponent extends TFStructureComponentOld {

	private static final int RADIUS = 13;
	private static final BlockState MOSSY_STONEBRICK = Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
	private static final BlockState CHISELED_STONEBRICK = Blocks.CHISELED_STONE_BRICKS.defaultBlockState();

	protected boolean beastPlaced = false;
	protected boolean dispenserPlaced = false;

	public QuestGroveComponent(StructureManager manager, CompoundTag nbt) {
		super(TFFeature.TFQuest1, nbt);
	}

	public QuestGroveComponent(TFFeature feature, int i, int x, int y, int z) {
		super(TFFeature.TFQuest1, feature, i);
		this.setOrientation(Direction.SOUTH);

		// the maze is 25 x 25 for now
		this.boundingBox = feature.getComponentToAddBoundingBox(x, y, z, -RADIUS, 0, -RADIUS, RADIUS * 2, 10, RADIUS * 2, Direction.SOUTH);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		for (Direction e : Direction.Plane.HORIZONTAL) {
			// make the rings
			makeWallSide(world, e, sbb);
		}

		// a small platform
		for (int x = 10; x < 17; x++) {
			for (int z = 10; z < 17; z++) {
				if (x == 10 || x == 16 || z == 10 || z == 16) {
					if (rand.nextInt(2) > 0) {
						placeBlock(world, MOSSY_STONEBRICK, x, -1, z, sbb);
					}
				} else if (x == 11 || x == 15 || z == 11 || z == 15) {
					if (rand.nextInt(3) > 0) {
						placeBlock(world, MOSSY_STONEBRICK, x, -1, z, sbb);
					}
				} else {
					placeBlock(world, MOSSY_STONEBRICK, x, -1, z, sbb);
				}
			}
		}

		// dispenser frame and button
		placeBlock(world, Blocks.STONE_BUTTON.defaultBlockState().setValue(FaceAttachedHorizontalDirectionalBlock.FACE, AttachFace.WALL).setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 13, 5, 19, sbb);

		placeBlock(world, MOSSY_STONEBRICK, 12, 7, 20, sbb);
		placeBlock(world, MOSSY_STONEBRICK, 13, 7, 20, sbb);
		placeBlock(world, MOSSY_STONEBRICK, 14, 7, 20, sbb);
		placeBlock(world, MOSSY_STONEBRICK, 12, 7, 21, sbb);
		placeBlock(world, MOSSY_STONEBRICK, 13, 7, 21, sbb);
		placeBlock(world, MOSSY_STONEBRICK, 14, 7, 21, sbb);

		// the dispenser
		if (!dispenserPlaced) {
			int bx = this.getWorldX(13, 20);
			int by = this.getWorldY(6);
			int bz = this.getWorldZ(13, 20);
			BlockPos pos = new BlockPos(bx, by, bz);

			if (sbb.isInside(pos)) {
				dispenserPlaced = true;

				world.setBlock(pos, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.NORTH), 4);
				DispenserBlockEntity ted = (DispenserBlockEntity) world.getBlockEntity(pos);

				// add 4 random wool blocks
				for (int i = 0; i < 4; i++) {
					ted.setItem(i, new ItemStack(ColorUtil.WOOL.getRandomColor(rand), 1));
				}
			}
		}

		// the quest beast!
		if (!beastPlaced) {
			int bx = this.getWorldX(13, 13);
			int by = this.getWorldY(0);
			int bz = this.getWorldZ(13, 13);
			BlockPos pos = new BlockPos(bx, by, bz);

			if (sbb.isInside(pos)) {
				beastPlaced = true;

				QuestRamEntity ram = new QuestRamEntity(TFEntities.quest_ram, world.getLevel());
				ram.setPos(bx, by, bz);
				ram.restrictTo(pos, 13);
				ram.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);

				world.addFreshEntity(ram);
			}
		}

		return true;
	}

	//TODO: Parameter "rand" is unused. Remove?
	private void makeWallSide(WorldGenLevel world, Direction direction, BoundingBox sbb) {
		Direction temp = this.getOrientation();
		this.setOrientation(direction);

		// arches
		placeOuterArch(world, 3, -1, sbb);
		placeOuterArch(world, 11, -1, sbb);
		placeOuterArch(world, 19, -1, sbb);

		// connecting thingers
		placeBlock(world, CHISELED_STONEBRICK, 0, 0, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 0, 1, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 0, 2, 0, sbb);

		placeBlock(world, CHISELED_STONEBRICK, 0, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 1, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 2, 3, 0, sbb);

		placeBlock(world, CHISELED_STONEBRICK, 8, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 9, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 10, 3, 0, sbb);

		placeBlock(world, CHISELED_STONEBRICK, 16, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 17, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 18, 3, 0, sbb);

		placeBlock(world, CHISELED_STONEBRICK, 24, 3, 0, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 25, 3, 0, sbb);

		// inner arch
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				for (int z = 0; z < 2; z++) {
					if (x == 0 || x == 1 || x == 7 || x == 8 || y == 0 || y == 1 || y == 7 || y == 8) {
						placeBlock(world, MOSSY_STONEBRICK, x + 9, y - 2, z + 5, sbb);
					}
				}
			}
		}

		// connecting thingers
		placeBlock(world, CHISELED_STONEBRICK, 6, 0, 6, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 6, 1, 6, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 6, 2, 6, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 6, 3, 6, sbb);

		placeBlock(world, CHISELED_STONEBRICK, 6, 4, 6, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 7, 4, 6, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 8, 4, 6, sbb);

		placeBlock(world, CHISELED_STONEBRICK, 18, 4, 6, sbb);
		placeBlock(world, CHISELED_STONEBRICK, 19, 4, 6, sbb);

		this.setOrientation(temp);
	}

	private void placeOuterArch(WorldGenLevel world, int ox, int oy, BoundingBox sbb) {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 6; y++) {
				if (x == 0 || x == 4 || y == 0 || y == 5) {
					placeBlock(world, MOSSY_STONEBRICK, x + ox, y + oy, 0, sbb);
				}
			}
		}
	}
}
