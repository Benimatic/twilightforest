package twilightforest.structures.courtyard;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import twilightforest.block.BlockTFNagastoneStairs;
import twilightforest.block.TFBlocks;
import twilightforest.structures.RandomizedTemplateProcessor;

import javax.annotation.Nullable;

public class CourtyardTerraceTemplateProcessor extends RandomizedTemplateProcessor {

    public CourtyardTerraceTemplateProcessor(BlockPos pos, PlacementSettings settings) {
        super(pos, settings);
    }

    @Nullable
    @Override
    public Template.BlockInfo process(IWorldReader world, BlockPos pos, Template.BlockInfo p_215194_3_, Template.BlockInfo blockInfo, PlacementSettings placementSettingsIn, @Nullable Template template) {
        if (!shouldPlaceBlock()) return null;

        boolean shouldMakeNewBlockInfo = false;
        BlockState state = blockInfo.state;

        final BlockState SMOOTHBRICK_SLAB_STATE = Blocks.SMOOTH_STONE_SLAB.getDefaultState();
        final BlockState SMOOTHBRICK_STATE = Blocks.STONE_BRICKS.getDefaultState(); //Blocks.DOUBLE_STONE_SLAB.getDefaultState().with(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK).with(BlockDoubleStoneSlab.SEAMLESS, false);

        //final BlockState SANDSTONE_SLAB_STATE = Blocks.STONE_SLAB.getDefaultState().with(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
        //final BlockState SANDSTONE_STATE = Blocks.DOUBLE_STONE_SLAB.getDefaultState().with(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).with(BlockDoubleStoneSlab.SEAMLESS, false);

        if (state == Blocks.SANDSTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE)) {
            BlockState stateCheck = world.getBlockState(pos);
            if (stateCheck == SMOOTHBRICK_SLAB_STATE)
                return new Template.BlockInfo(pos, SMOOTHBRICK_SLAB_STATE, null);
            else if (stateCheck.getMaterial() == Material.AIR)
                return null;
            else
                state = SMOOTHBRICK_STATE;

            shouldMakeNewBlockInfo = true;
        }

        if (state.getBlock() == Blocks.SANDSTONE_SLAB) {
            BlockState stateCheck = world.getBlockState(pos);

            if (stateCheck.getMaterial() == Material.AIR)
                return null;
            else
                return new Template.BlockInfo(pos, SMOOTHBRICK_SLAB_STATE, null);
        }

        Block block = state.getBlock();

        if (state == Blocks.STONE_BRICKS.getDefaultState())
        	//TODO: We can't randomise variant. Flatten
            return random.nextBoolean() ? (shouldMakeNewBlockInfo ? new Template.BlockInfo(blockInfo.pos, state, blockInfo.tileentityData) : blockInfo) : new Template.BlockInfo(pos, state.with(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.values()[random.nextInt(3)]), null);

        if (state == Blocks.SMOOTH_STONE_SLAB.getDefaultState())
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, Blocks.COBBLESTONE_SLAB.getDefaultState(), null);

        if (block == TFBlocks.etched_nagastone.get())
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.etched_nagastone_mossy.get(), TFBlocks.etched_nagastone_weathered.get()), DirectionalBlock.FACING), null);

        if (block == TFBlocks.nagastone_pillar.get())
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_pillar_mossy.get(), TFBlocks.nagastone_pillar_weathered.get()), RotatedPillarBlock.AXIS), null);

        if (block == TFBlocks.nagastone_stairs.get())
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_stairs.get(), TFBlocks.nagastone_stairs.get()), BlockTFNagastoneStairs.DIRECTION, BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);

        return blockInfo;
    }
}