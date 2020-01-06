package twilightforest.structures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;

/**
 * Created by Joseph on 7/16/2017.
 */
public class StructureTFHelper {

    public static final BlockState stoneSlab = getSlab(Blocks.SMOOTH_STONE_SLAB);
    public static final BlockState stoneSlabTop = getSlabTop(Blocks.SMOOTH_STONE_SLAB);
    public static final BlockState stoneSlabDouble = Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE);

    public static final BlockState birchSlab = getSlab(Blocks.BIRCH_SLAB);
    public static final BlockState birchSlabTop = getSlabTop(Blocks.BIRCH_SLAB);
    public static final BlockState birchPlanks = Blocks.BIRCH_PLANKS.getDefaultState();


    private static BlockState getSlabType(Block type, SlabType side) {
        return type.getDefaultState().with(SlabBlock.TYPE, side);
    }


    public static BlockState getSlab(Block type) {
        return getSlabType(type, BlockSlab.EnumBlockHalf.BOTTOM);
    }

    public static BlockState getSlabTop(Block type) {
        return getSlabType(type, BlockSlab.EnumBlockHalf.TOP);
    }

    public static BlockState randomPlant(int i) {
        if(i < 4) return randomSapling(i);
        else return randomMushroom(i-4);
    }

    //TODO: Flatten
    public static BlockState randomSapling(int i) {
        return Blocks.SAPLING.getDefaultState().with(BlockSapling.TYPE, BlockPlanks.EnumType.values()[i]);
    }

    public static BlockState randomMushroom(int i) {
        if(i == 0) return Blocks.RED_MUSHROOM.getDefaultState();
        else return Blocks.BROWN_MUSHROOM.getDefaultState();
    }


}
