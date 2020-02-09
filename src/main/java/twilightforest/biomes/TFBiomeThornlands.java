package twilightforest.biomes;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;

import java.util.Random;
import java.util.function.Predicate;

public class TFBiomeThornlands extends TFBiomeBase {

	private final WorldGenerator tfGenThorns = new TFGenThorns();

	public TFBiomeThornlands(Builder props) {
		super(props);

		getTFBiomeDecorator().hasCanopy = false;
		getTFBiomeDecorator().setTreesPerChunk(-999);
		this.decorator.deadBushPerChunk = 2;
		this.decorator.cactiPerChunk = -9999; // gotta be sure
		//TODO: Find out how to clear lists
		this.spawnableCreatureList.clear();

		this.decorator.generateFalls = false;
	}

    //TODO: Move to feature decorator
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

	private final Predicate<Block> otherGround = block -> block == Blocks.SANDSTONE || block == Blocks.SAND || block == Blocks.CLAY || block == TFBlocks.deadrock.get() || block == TFBlocks.deadrock_cracked.get() || block == TFBlocks.deadrock_weathered.get();

	@Override
	protected ResourceLocation[] getRequiredAdvancements() {
		return new ResourceLocation[]{ TwilightForestMod.prefix("progress_troll") };
	}

	@Override
	public void enforceProgression(PlayerEntity player, World world) {
		if (!world.isRemote && player.ticksExisted % 5 == 0) {
			player.attackEntityFrom(DamageSource.MAGIC, 1.0F);
			world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1.0F, 1.0F);

			// hint monster?
			if (world.rand.nextInt(4) == 0) TFFeature.TROLL_CAVE.trySpawnHintMonster(world, player);
		}
	}
}
