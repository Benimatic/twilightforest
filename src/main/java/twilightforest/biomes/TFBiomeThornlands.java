package twilightforest.biomes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFDeadrock;
import twilightforest.block.TFBlocks;
import twilightforest.enums.DeadrockVariant;
import twilightforest.world.feature.TFGenThorns;
import twilightforest.world.TFWorld;

import java.util.Random;
import java.util.function.Predicate;

public class TFBiomeThornlands extends TFBiomeBase {

	private final WorldGenerator tfGenThorns = new TFGenThorns();

	public TFBiomeThornlands(BiomeProperties props) {
		super(props);

		this.topBlock = TFBlocks.deadrock.getDefaultState().withProperty(BlockTFDeadrock.VARIANT, DeadrockVariant.SURFACE);
		this.fillerBlock = TFBlocks.deadrock.getDefaultState().withProperty(BlockTFDeadrock.VARIANT, DeadrockVariant.CRACKED);

		getTFBiomeDecorator().hasCanopy = false;
		getTFBiomeDecorator().setTreesPerChunk(-999);
		this.decorator.deadBushPerChunk = 2;
		this.decorator.cactiPerChunk = -9999; // gotta be sure
		this.spawnableCreatureList.clear();

		this.decorator.generateFalls = false;
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		// add thorns!
		for (int i = 0; i < 128; i++) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			int ry = TFWorld.getGroundLevel(world, rx, rz, otherGround);

			this.tfGenThorns.generate(world, rand, new BlockPos(rx, ry, rz));
		}
	}

	private final Predicate<Block> otherGround = block -> block == Blocks.SANDSTONE || block == Blocks.SAND || block == Blocks.CLAY || block == TFBlocks.deadrock;

	@Override
	public IBlockState getStoneReplacementState() {
		return TFBlocks.deadrock.getDefaultState().withProperty(BlockTFDeadrock.VARIANT, DeadrockVariant.SOLID);
	}

	@Override
	protected ResourceLocation[] getRequiredAdvancements() {
		return new ResourceLocation[]{ TwilightForestMod.prefix("progress_troll") };
	}

	@Override
	public void enforceProgression(EntityPlayer player, World world) {
		if (!world.isRemote && player.ticksExisted % 5 == 0) {
			player.attackEntityFrom(DamageSource.MAGIC, 1.0F);
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1.0F, 1.0F);

			// hint monster?
			if (world.rand.nextInt(4) == 0) TFFeature.TROLL_CAVE.trySpawnHintMonster(world, player);
		}
	}
}
