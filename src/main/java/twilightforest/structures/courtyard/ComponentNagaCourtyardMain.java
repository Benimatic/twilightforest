package twilightforest.structures.courtyard;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;

import java.util.Random;

public class ComponentNagaCourtyardMain extends StructureMazeGenerator {

	// that gives us a 95 x 95 area to work with
	static int RADIUS = 62;
	static int DIAMETER = 2 * RADIUS + 1;

	public ComponentNagaCourtyardMain() {
		super();
	}

	public ComponentNagaCourtyardMain(TFFeature feature, World world, Random rand, int i, int x, int y, int z) {
		super(feature, rand, i, 11, 11);

		this.setCoordBaseMode(EnumFacing.NORTH);

		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -RADIUS, -1, -RADIUS, RADIUS * 2, 10, RADIUS * 2, EnumFacing.NORTH);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		//TwilightForestMod.LOGGER.info("" + widthInCellCount + " " + heightInCellCount);

		/*int width = widthInCellCount-1;
		int height = heightInCellCount-1;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int value = maze[width-x-1][height-y-1] & 0b1111;

				for (WallFacing facing : WallFacing.values()) {
					if (facing.unpackAndTest(value)) {
						for (int i = 1; i < 6; i++) {
							setBlockState(world,
									Blocks.WOOL.getStateFromMeta(value),
									(x * 12)+17 + (facing.getEnumFacing().getFrontOffsetX() * -i),
									6,
									(y * 12)+17 + (facing.getEnumFacing().getFrontOffsetZ() * -i),
									sbb);
						}
					}
				}

				setBlockState(world,
						Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(value),
						(x * 12)+17,
						6,
						(y * 12)+17,
						sbb);
			}
		}//*/

		setBlockState(world, TFBlocks.castleMagic.getDefaultState(), 0, 10, 0, sbb);
		setBlockState(world, TFBlocks.castleMagic.getDefaultState(), 0, 10, DIAMETER, sbb);
		setBlockState(world, TFBlocks.castleMagic.getDefaultState(), DIAMETER, 10, 0, sbb);
		setBlockState(world, TFBlocks.castleMagic.getDefaultState(), DIAMETER, 10, DIAMETER, sbb);

		// naga spawner seems important
		setBlockState(world, TFBlocks.bossSpawner.getDefaultState(), RADIUS + 1, 2, RADIUS + 1, sbb);

		return true;
	}
}
