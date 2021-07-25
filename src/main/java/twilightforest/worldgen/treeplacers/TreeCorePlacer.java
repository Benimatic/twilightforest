package twilightforest.worldgen.treeplacers;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import twilightforest.worldgen.TwilightFeatures;

public class TreeCorePlacer extends TreeDecorator {

	public static final Codec<TreeCorePlacer> CODEC = RecordCodecBuilder.create(
	        instance -> instance.group(
	                Codec.intRange(0, 20).fieldOf("core_position").forGetter(o -> o.corePos),
	                BlockStateProvider.CODEC.fieldOf("deco_provider").forGetter(o -> o.core)
	        ).apply(instance, TreeCorePlacer::new));

	private final int corePos;
	private final BlockStateProvider core;

	public TreeCorePlacer(int corePos, BlockStateProvider core) {
		this.corePos = corePos;
		this.core = core;
	}

	@Override
	protected TreeDecoratorType<TreeCorePlacer> type() {
		return TwilightFeatures.CORE_PLACER;
	}

	@Override
	public void place(WorldGenLevel world, Random random, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks,
			Set<BlockPos> decorations, BoundingBox mutableBoundingBox) {
		BlockPos pos = trunkBlocks.get(0);
		BlockPos position = pos.offset(0, this.corePos, 0);
		placeCore(world, random, position, decorations, 0, corePos, 0, mutableBoundingBox, core);
	}
	
	public void placeCore(WorldGenLevel world, Random random, BlockPos pos, Set<BlockPos> decorations, double offset, int iteration, int length, BoundingBox mutableBoundingBox, BlockStateProvider coreType) {
		BlockPos position = pos.offset(0, this.corePos, 0);
		setBlock(world, pos, coreType.getState(random, position), decorations, mutableBoundingBox);
	}

}
