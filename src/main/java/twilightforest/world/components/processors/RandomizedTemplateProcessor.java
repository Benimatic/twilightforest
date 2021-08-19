package twilightforest.world.components.processors;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

import java.util.Random;

public abstract class RandomizedTemplateProcessor extends StructureProcessor {

    protected final float integrity;

    public RandomizedTemplateProcessor(float integrity) {
        this.integrity = integrity;
    }

	protected boolean shouldPlaceBlock(Random random) {
        return integrity >= 1.0F || random.nextFloat() > integrity;
    }

    protected Block randomBlock(Random random, Block... blocks) {
        return blocks[random.nextInt(blocks.length)];
    }

    protected static <T extends Comparable<T>> BlockState translateState(BlockState stateIn, Block blockOut, Property<T> property) {
        return blockOut.defaultBlockState().setValue(property, stateIn.getValue(property));
    }

    protected static BlockState translateState(BlockState stateIn, Block blockOut, Property<?>... properties) {
        BlockState stateOut = blockOut.defaultBlockState();
        for (Property<?> property : properties) stateOut = copyValue(stateIn, stateOut, property);
        return stateOut;
    }

    private static <T extends Comparable<T>> BlockState copyValue(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }
}
