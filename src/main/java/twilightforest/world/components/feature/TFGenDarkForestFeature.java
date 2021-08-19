package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.Random;

//Places single block features in patches around the dark forest
public class TFGenDarkForestFeature extends Feature<RandomPatchConfiguration> {
    public TFGenDarkForestFeature(Codec<RandomPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> ctx) {
        WorldGenLevel reader = ctx.level();
        BlockPos pos = ctx.origin();
        Random rand = ctx.random();
        RandomPatchConfiguration config = ctx.config();

        boolean foundDirt = false;
        Material materialUnder;

        if(pos.getY() <= 40) {
            for (int dy = pos.getY(); dy >= TFGenerationSettings.SEALEVEL; dy--) {
                materialUnder = reader.getBlockState(new BlockPos(pos.getX(), dy - 1, pos.getZ())).getMaterial();
                if ((materialUnder == Material.GRASS || materialUnder == Material.DIRT) && reader.getBlockState(pos) == Blocks.AIR.defaultBlockState()) {
                    foundDirt = true;
                    pos = new BlockPos(pos.getX(), dy, pos.getZ());
                    break;
                } else if (materialUnder == Material.STONE || materialUnder == Material.SAND) {
                    break;
                }
            }
        }

        if (!foundDirt) {
            return false;
        }

        //RandomPatchFeature placement logic
        BlockState blockstate = config.stateProvider.getState(rand, pos);
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        for(int j = 0; j < config.tries; ++j) {
            blockpos$mutable.setWithOffset(pos, rand.nextInt(config.xspread + 1) - rand.nextInt(config.xspread + 1), rand.nextInt(config.yspread + 1) - rand.nextInt(config.yspread + 1), rand.nextInt(config.zspread + 1) - rand.nextInt(config.zspread + 1));
            BlockPos blockpos1 = blockpos$mutable.below();
            BlockState blockstate1 = reader.getBlockState(blockpos1);
            if ((reader.isEmptyBlock(blockpos$mutable) || config.canReplace && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && blockstate.canSurvive(reader, blockpos$mutable) && (config.whitelist.isEmpty() || config.whitelist.contains(blockstate1.getBlock())) && !config.blacklist.contains(blockstate1) && (!config.needWater || reader.getFluidState(blockpos1.west()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.east()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.north()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.south()).is(FluidTags.WATER))) {
                config.blockPlacer.place(reader, blockpos$mutable, blockstate, rand);
                ++i;
            }
        }
        return i > 0;
    }
}
