package twilightforest.structures.darktower;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import twilightforest.TFFeature;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

import java.util.Random;

public class ComponentTFDarkTowerRoofRings extends ComponentTFDarkTowerRoof {

	public ComponentTFDarkTowerRoofRings() {
	}

	public ComponentTFDarkTowerRoofRings(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		super.generate(world, generator, rand, sbb, chunkPosIn);

		// antenna
		for (int y = 1; y < 10; y++) {
			setBlockState(world, deco.blockState, size / 2, y, size / 2, sbb);
		}

		setBlockState(world, deco.accentState, size / 2, 10, size / 2, sbb);


		setBlockState(world, deco.accentState, size / 2 - 1, 1, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, 1, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 1, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2, 1, size / 2 + 1, sbb);

		makeARing(world.getWorld(), 6, sbb);
		makeARing(world.getWorld(), 8, sbb);

		return true;
	}


	protected void makeARing(World world, int y, MutableBoundingBox sbb) {
		setBlockState(world, deco.accentState, size / 2 - 2, y, size / 2 + 1, sbb);
		setBlockState(world, deco.accentState, size / 2 - 2, y, size / 2 + 0, sbb);
		setBlockState(world, deco.accentState, size / 2 - 2, y, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2 + 2, y, size / 2 + 1, sbb);
		setBlockState(world, deco.accentState, size / 2 + 2, y, size / 2 + 0, sbb);
		setBlockState(world, deco.accentState, size / 2 + 2, y, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, y, size / 2 - 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 0, y, size / 2 - 2, sbb);
		setBlockState(world, deco.accentState, size / 2 - 1, y, size / 2 - 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, y, size / 2 + 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 0, y, size / 2 + 2, sbb);
		setBlockState(world, deco.accentState, size / 2 - 1, y, size / 2 + 2, sbb);
	}
}
