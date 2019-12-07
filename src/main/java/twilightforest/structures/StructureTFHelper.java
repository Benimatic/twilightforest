package twilightforest.structures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/**
 * Created by Joseph on 7/16/2017.
 */
public class StructureTFHelper {

    public static final BlockState stoneSlab = getSlab(Blocks.STONE_SLAB);
    public static final BlockState stoneSlabTop = getSlabTop(Blocks.STONE_SLAB);
    public static final BlockState stoneSlabDouble = Blocks.DOUBLE_STONE_SLAB.getDefaultState();

    public static final BlockState birchSlab = getSlab(Blocks.WOODEN_SLAB).withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
    public static final BlockState birchSlabTop = getSlabTop(Blocks.WOODEN_SLAB).withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
    public static final BlockState birchPlanks = Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);


    private static BlockState getSlabType(Block type, BlockSlab.EnumBlockHalf side) {
        return type.getDefaultState().withProperty(BlockSlab.HALF, side);
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

    public static BlockState randomSapling(int i) {
        return Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.values()[i]);
    }

    public static BlockState randomMushroom(int i) {
        if(i == 0) return Blocks.RED_MUSHROOM.getDefaultState();
        else return Blocks.BROWN_MUSHROOM.getDefaultState();
    }


}
