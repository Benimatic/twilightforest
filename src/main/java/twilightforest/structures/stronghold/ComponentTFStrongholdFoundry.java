package twilightforest.structures.stronghold;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class ComponentTFStrongholdFoundry extends StructureTFStrongholdComponent {

	int entranceLevel;

	public ComponentTFStrongholdFoundry(TemplateManager manager, CompoundNBT nbt) {
		super(TFStrongholdPieces.TFSFo, nbt);
	}

	public ComponentTFStrongholdFoundry(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(TFStrongholdPieces.TFSFo, feature, i, facing, x, y, z);
	}

	//TODO: See super
//	@Override
//	protected void writeStructureToNBT(CompoundNBT tagCompound) {
//		super.writeStructureToNBT(tagCompound);
//
//		tagCompound.putInt("entranceLevel", this.entranceLevel);
//	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) {
		super.readAdditional(tagCompound);
		this.entranceLevel = tagCompound.getInt("entranceLevel");
	}

	@Override
	public MutableBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		if (y > 17) {
			this.entranceLevel = 3;
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -20, 0, 18, 25, 18, facing);
		} else if (y < 11) {
			this.entranceLevel = 1;
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -6, 0, 18, 25, 18, facing);
		} else {
			this.entranceLevel = 2;
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -13, 0, 18, 25, 18, facing);
		}
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random random) {
		super.buildComponent(parent, list, random);

		switch (this.entranceLevel) {
			case 1:
				this.addDoor(4, 6, 0);
				addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 13, 13);
				addNewComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 18, 13, 4);
				addNewComponent(parent, list, random, Rotation.NONE, 13, 20, 18);
				break;
			case 2:
				this.addDoor(4, 13, 0);
				addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 6, 13);
				addNewComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 18, 20, 4);
				addNewComponent(parent, list, random, Rotation.NONE, 13, 13, 18);
				break;
			case 3:
				this.addDoor(4, 20, 0);
				addNewComponent(parent, list, random, Rotation.NONE, 13, 6, 18);
				addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 13, 13);
				addNewComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 18, 13, 4);
		}
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World worldIn = world.getWorld();
		placeStrongholdWalls(worldIn, sbb, 0, 0, 0, 17, 25, 17, rand, deco.randomBlocks);

		// lava bottom
		this.fillWithBlocks(world, sbb, 1, 0, 1, 16, 4, 16, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);

		// top ledge
		this.fillWithRandomizedBlocks(world, sbb, 1, 19, 1, 16, 19, 16, false, rand, deco.randomBlocks);
		this.fillWithAir(world, sbb, 2, 19, 2, 15, 19, 15);

		// middle ledge
		this.fillWithRandomizedBlocks(world, sbb, 1, 12, 1, 16, 12, 16, false, rand, deco.randomBlocks);
		this.fillWithAir(world, sbb, 2, 12, 2, 15, 12, 15);

		// bottom ledge
		this.fillWithRandomizedBlocks(world, sbb, 1, 5, 1, 16, 5, 16, false, rand, deco.randomBlocks);
		this.fillWithAir(world, sbb, 2, 5, 2, 15, 5, 15);

		// corner pillars
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 1, 1, 24, 2, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 2, 1, 1, 2, 24, 1, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 16, 1, 1, 16, 24, 2, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 15, 1, 1, 15, 24, 1, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 15, 1, 24, 16, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 2, 1, 16, 2, 24, 16, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 16, 1, 15, 16, 24, 16, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 15, 1, 16, 15, 24, 16, false, rand, deco.randomBlocks);

		// suspended mass
		Random massRandom = new Random(rand.nextLong());

		for (int x = 4; x < 14; x++) {
			for (int z = 4; z < 14; z++) {
				for (int y = 8; y < 23; y++) {
					float c = Math.abs(x - 8.5F) + Math.abs(z - 8.5F) + Math.abs(y - 18F);
					float r = 5.5F + ((massRandom.nextFloat() - massRandom.nextFloat()) * 3.5F);

					if (c < r) {
						this.setBlockState(world, Blocks.STONE.getDefaultState(), x, y, z, sbb);
					}
				}
			}
		}

		// drips
		for (int i = 0; i < 400; i++) {
			int dx = massRandom.nextInt(9) + 5;
			int dz = massRandom.nextInt(9) + 5;
			int dy = massRandom.nextInt(13) + 10;

			if (this.getBlockStateFromPos(world, dx, dy, dz, sbb).getBlock() != Blocks.AIR) {
				for (int y = 0; y < 3; y++) {
					this.setBlockState(world, Blocks.STONE.getDefaultState(), dx, dy - y, dz, sbb);
				}
			}
		}

		// add some redstone ore
		for (int i = 0; i < 8; i++) {
			addOreToMass(worldIn, sbb, massRandom, Blocks.REDSTONE_ORE.getDefaultState());
		}

		// add some iron ore
		for (int i = 0; i < 8; i++) {
			addOreToMass(worldIn, sbb, massRandom, Blocks.IRON_ORE.getDefaultState());
		}

		// add some gold ore
		for (int i = 0; i < 6; i++) {
			addOreToMass(worldIn, sbb, massRandom, Blocks.GOLD_ORE.getDefaultState());
		}

		// add some glowstone
		for (int i = 0; i < 2; i++) {
			addOreToMass(worldIn, sbb, massRandom, Blocks.GLOWSTONE.getDefaultState());
		}

		// add some emerald ore
		for (int i = 0; i < 2; i++) {
			addOreToMass(worldIn, sbb, massRandom, Blocks.EMERALD_ORE.getDefaultState());
		}

		// add some diamond ore
		for (int i = 0; i < 4; i++) {
			addOreToMass(worldIn, sbb, massRandom, Blocks.DIAMOND_ORE.getDefaultState());
		}

		// doors
		placeDoors(worldIn, rand, sbb);

		return true;
	}

	/**
	 * Add a block of ore to the mass
	 */
	private void addOreToMass(World world, MutableBoundingBox sbb, Random massRandom, BlockState state) {
		for (int i = 0; i < 10; i++) {
			int dx = massRandom.nextInt(9) + 5;
			int dz = massRandom.nextInt(9) + 5;
			int dy = massRandom.nextInt(13) + 10;

			if (this.getBlockStateFromPos(world, dx, dy, dz, sbb).getBlock() != Blocks.AIR) {
				this.setBlockState(world, state, dx, dy, dz, sbb);
				// we have succeeded, stop looping
				break;
			}
		}
	}
}
