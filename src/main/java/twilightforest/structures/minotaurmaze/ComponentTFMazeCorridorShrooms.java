package twilightforest.structures.minotaurmaze;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFMazeCorridorShrooms extends ComponentTFMazeCorridor {

	public ComponentTFMazeCorridorShrooms(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMCS, nbt);
	}

	public ComponentTFMazeCorridorShrooms(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {

		// mycelium & mushrooms
		for (int x = 1; x < 5; x++) {
			for (int z = 0; z < 5; z++) {
				if (rand.nextInt(2) > 0) {
					this.setBlockState(world, Blocks.MYCELIUM.getDefaultState(), x, 0, z, sbb);
				}
				if (rand.nextInt(2) > 0) {
					this.setBlockState(world, rand.nextBoolean() ? Blocks.RED_MUSHROOM.getDefaultState() : Blocks.BROWN_MUSHROOM.getDefaultState(), x, 1, z, sbb);
				}
			}
		}

		// brackets?
		//TODO: Flatten, or at least, be more accurate to current implementation
		boolean mushFlag = rand.nextBoolean();
		BlockState mushType = (mushFlag ? Blocks.RED_MUSHROOM_BLOCK : Blocks.BROWN_MUSHROOM_BLOCK).getDefaultState();
		int mushY = rand.nextInt(4) + 1;
		int mushZ = rand.nextInt(4) + 1;
		this.setBlockState(world, mushType.with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.ALL_STEM), 1, mushY - 1, mushZ, sbb);
		this.fillWithBlocks(world, sbb, 1, 1, mushZ, 1, mushY, mushZ, mushType.with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.STEM), AIR, false);
		this.fillWithBlocks(world, sbb, 1, mushY, mushZ - 1, 2, mushY, mushZ + 1, mushType.with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.ALL_OUTSIDE), AIR, false);

		mushType = (mushFlag ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK).getDefaultState();
		mushY = rand.nextInt(4) + 1;
		mushZ = rand.nextInt(4) + 1;
		this.fillWithBlocks(world, sbb, 4, 1, mushZ, 4, mushY, mushZ, mushType.with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.STEM), AIR, false);
		this.fillWithBlocks(world, sbb, 3, mushY, mushZ - 1, 4, mushY, mushZ + 1, mushType.with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.ALL_OUTSIDE), AIR, false);


		return true;
	}
}
