package twilightforest.structures;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.world.gen.feature.template.StructureProcessor;

import java.util.Random;

public abstract class RandomizedTemplateProcessor extends StructureProcessor {

    private final float integrity;

    public RandomizedTemplateProcessor(float integrity) {
        this.integrity = integrity;
    }

	@Override
	protected <T> Dynamic<T> serialize0(DynamicOps<T> dynOps) {
		return new Dynamic<>(dynOps, dynOps.createMap(ImmutableMap.of(
				dynOps.createString("integrity"), dynOps.createFloat(this.integrity))));
	}

	protected boolean shouldPlaceBlock(Random random) {
        return integrity >= 1.0F || random.nextFloat() > integrity;
    }

    protected Block randomBlock(Random random, Block... blocks) {
        return blocks[random.nextInt(blocks.length)];
    }

    protected static <T extends Comparable<T>> BlockState translateState(BlockState stateIn, Block blockOut, IProperty<T> property) {
        return blockOut.getDefaultState().with(property, stateIn.get(property));
    }

    protected static BlockState translateState(BlockState stateIn, Block blockOut, IProperty<?>... properties) {
        BlockState stateOut = blockOut.getDefaultState();
        for (IProperty<?> property : properties) stateOut = copyValue(stateIn, stateOut, property);
        return stateOut;
    }

    private static <T extends Comparable<T>> BlockState copyValue(BlockState from, BlockState to, IProperty<T> property) {
        return to.with(property, from.get(property));
    }
}
