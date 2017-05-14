package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;



public class TFGenRavine extends MapGenBase
{
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    private float field_35627_a[];

    public TFGenRavine()
    {
        field_35627_a = new float[1024];
    }

    protected void addTunnel(long l, int i, int j, ChunkPrimer chunkPrimer, double d,
                             double d1, double d2, float f, float f1, float f2,
                             int k, int i1, double d3)
    {
        Random random = new Random(l);
        double d4 = i * 16 + 8;
        double d5 = j * 16 + 8;
        float f3 = 0.0F;
        float f4 = 0.0F;
        if (i1 <= 0)
        {
            int j1 = range * 16 - 16;
            i1 = j1 - random.nextInt(j1 / 4);
        }
        boolean flag = false;
        if (k == -1)
        {
            k = i1 / 2;
            flag = true;
        }
        float f5 = 1.0F;
        for (int k1 = 0; k1 < TFWorld.CHUNKHEIGHT; k1++)
        {
            if (k1 == 0 || random.nextInt(3) == 0)
            {
                f5 = 1.0F + random.nextFloat() * random.nextFloat();
            }
            field_35627_a[k1] = f5 * f5;
        }

        for (; k < i1; k++)
        {
            double d6 = 1.5D + (double)(MathHelper.sin(((float)k * (float)Math.PI) / (float)i1) * f);
            double d7 = d6 * d3;
            d6 *= (double)random.nextFloat() * 0.25D + 0.75D;
            d7 *= (double)random.nextFloat() * 0.25D + 0.75D;
            float f6 = MathHelper.cos(f2);
            float f7 = MathHelper.sin(f2);
            d += MathHelper.cos(f1) * f6;
            d1 += f7;
            d2 += MathHelper.sin(f1) * f6;
            f2 *= 0.7F;
            f2 += f4 * 0.05F;
            f1 += f3 * 0.05F;
            f4 *= 0.8F;
            f3 *= 0.5F;
            f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4F;
            if (!flag && random.nextInt(4) == 0)
            {
                continue;
            }
            double d8a = d - d4;
            double d9a = d2 - d5;
            double d10a = i1 - k;
            double d11 = f + 2.0F + 16F;
            if ((d8a * d8a + d9a * d9a) - d10a * d10a > d11 * d11)
            {
                return;
            }
            if (d < d4 - 16D - d6 * 2D || d2 < d5 - 16D - d6 * 2D || d > d4 + 16D + d6 * 2D || d2 > d5 + 16D + d6 * 2D)
            {
                continue;
            }
            int d8 = MathHelper.floor(d - d6) - i * 16 - 1;
            int l1 = (MathHelper.floor(d + d6) - i * 16) + 1;
            int d9 = MathHelper.floor(d1 - d7) - 1;
            int i2 = MathHelper.floor(d1 + d7) + 1;
            int d10 = MathHelper.floor(d2 - d6) - j * 16 - 1;
            int j2 = (MathHelper.floor(d2 + d6) - j * 16) + 1;
            if (d8 < 0)
            {
                d8 = 0;
            }
            if (l1 > 16)
            {
                l1 = 16;
            }
            if (d9 < 1)
            {
                d9 = 1;
            }
            if (i2 > TFWorld.CHUNKHEIGHT - 8)
            {
                i2 = TFWorld.CHUNKHEIGHT - 8;
            }
            if (d10 < 0)
            {
                d10 = 0;
            }
            if (j2 > 16)
            {
                j2 = 16;
            }
            boolean flag1 = false;
            for (int k2 = d8; !flag1 && k2 < l1; k2++)
            {
                for (int i3 = d10; !flag1 && i3 < j2; i3++)
                {
                    //TODO: Verify this works correctly, in 1.7.10, references to i3 were originally d9
                    for (int j3 = i2 + 1; !flag1 && j3 >= i3 - 1; j3--)
                    {
                        //TODO: Except for this line
                        if (j3 < 0 || j3 >= TFWorld.CHUNKHEIGHT)
                        {
                            continue;
                        }
                        if (isOceanBlock(chunkPrimer, k2, j3, i3, i, j))
                        {
                            flag1 = true;
                        }
                        if (j3 != i3 - 1 && k2 != d8 && k2 != l1 - 1 && i3 != d10 && i3 != j2 - 1)
                        {
                            j3 = i3;
                        }
                    }
                }
            }

            if (flag1)
            {
                continue;
            }
            for (int l2 = d8; l2 < l1; l2++)
            {
                double d12 = (((double)(l2 + i * 16) + 0.5D) - d) / d6;
                for (int l3 = d10; l3 < j2; l3++)
                {
                    double d13 = (((double)(l3 + j * 16) + 0.5D) - d2) / d6;
                    boolean flag2 = false;
                    if (d12 * d12 + d13 * d13 >= 1.0D)
                    {
                        continue;
                    }
                    for (int j4_ = i2; j4_ > d9; j4_++)
                    {
                        int j4 = j4_ - 1;
                        double d14 = (((double)j4 + 0.5D) - d1) / d7;
                        if ((d12 * d12 + d13 * d13) * (double)field_35627_a[j4] + (d14 * d14) / 6D < 1.0D)
                        {
                            if (isTopBlock(chunkPrimer, l2, j4, l3, i, j))
                            {
                                flag2 = true;
                            }
                            digBlock(chunkPrimer, l2, j4, l3, i, j, flag2);
                        }
                    }
                }
            }

            if (flag)
            {
                break;
            }
        }
    }

    protected void recursiveGenerate(World worldIn, int centerChunkX, int centerChunkZ, int currentChunkX, int currentChunkZ, ChunkPrimer chunkPrimerIn)
    {
        if (rand.nextInt(127) != 0)
        {
            return;
        }
        if (!TFFeature.getNearestFeature(currentChunkX, currentChunkZ, world).areChunkDecorationsEnabled) {
            return;
        }
        double d = centerChunkX * 16 + rand.nextInt(16);
        double d1 = rand.nextInt(rand.nextInt(40) + 8) + 20;
        double d2 = centerChunkZ * 16 + rand.nextInt(16);
        int i1 = 1;
        for (int j1 = 0; j1 < i1; j1++)
        {
            float f = rand.nextFloat() * ((float)Math.PI * 2.0F);
            float f1 = ((rand.nextFloat() - 0.5F) * 2.0F) / 8F;
            float f2 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
            addTunnel(rand.nextLong(), currentChunkX, currentChunkZ, chunkPrimerIn, d, d1, d2, f2, f, f1, 0, 0, 3.0D);
        }
    }

    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        Block block = data.getBlockState(x, y, z).getBlock();
        return block== Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    //Determine if the block at the specified location is the top block for the biome, we take into account
    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
    private boolean isTopBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        IBlockState state = data.getBlockState(x, y, z);
        return state.getBlock() == Blocks.GRASS;
    }

    /**
     * Digs out the current block, default implementation removes stone, filler, and top block
     * Sets the block to lava if y is less then 10, and air other wise.
     * If setting to air, it also checks to see if we've broken the surface and if so
     * tries to make the floor the biome's top block
     *
     * @param data Block data array
     * @param x local X position
     * @param y local Y position
     * @param z local Z position
     * @param chunkX Chunk X position
     * @param chunkZ Chunk Y position
     * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the surface.
     */
    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
    {
        Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        Block block = state.getBlock();
        IBlockState top = biome.topBlock;
        IBlockState filler = Blocks.DIRT.getDefaultState();

        if (block == Blocks.STONE || block == TFBlocks.trollSteinn || block == Blocks.DIRT || block == Blocks.GRASS)
        {
            data.setBlockState(x, y, z, AIR);

            if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
            {
                data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
            }
        }
    }
}