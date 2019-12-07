package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.block.Blocks;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableInt;
import twilightforest.TFConfig;
import twilightforest.TFTeleporter;
import twilightforest.TwilightForestMod;
import twilightforest.world.TFWorld;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockTFPortal extends BlockBreakable {

	public static final IProperty<Boolean> DISALLOW_RETURN = PropertyBool.create("is_one_way");

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	private static final AxisAlignedBB AABB_ITEM = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);

	private static final int MIN_PORTAL_SIZE =  4;
	private static final int MAX_PORTAL_SIZE = 64;

	public BlockTFPortal() {
		super(Material.PORTAL, false);
		this.setHardness(-1F);
		this.setSoundType(SoundType.GLASS);
		this.setLightLevel(0.75F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DISALLOW_RETURN, false));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DISALLOW_RETURN);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(DISALLOW_RETURN) ? 1 : 0;
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(DISALLOW_RETURN, meta == 1);
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

	@Override
	@Deprecated
	public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(DISALLOW_RETURN) ? AABB : NULL_AABB;
	}

	@Override
	@Deprecated
	public void addCollisionBoxToList(BlockState state, World world, BlockPos pos, AxisAlignedBB entityBB, List<AxisAlignedBB> blockBBs, @Nullable Entity entity, boolean isActualState) {
		addCollisionBoxToList(pos, entityBB, blockBBs, entity instanceof EntityItem ? AABB_ITEM : state.getCollisionBoundingBox(world, pos));
	}

	@Override
	@Deprecated
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.UNDEFINED;
	}

	public boolean tryToCreatePortal(World world, BlockPos pos, ItemEntity catalyst, @Nullable PlayerEntity player) {

		BlockState state = world.getBlockState(pos);

		if (canFormPortal(state) && world.getBlockState(pos.down()).isFullCube()) {
			Map<BlockPos, Boolean> blocksChecked = new HashMap<>();
			blocksChecked.put(pos, true);

			MutableInt size = new MutableInt(0);

			if (recursivelyValidatePortal(world, pos, blocksChecked, size, state) && size.intValue() >= MIN_PORTAL_SIZE) {

				if (TFConfig.checkPortalDestination) {
					TFTeleporter teleporter = TFTeleporter.getTeleporterForDim(catalyst.getServer(), getDestination(catalyst));
					boolean checkProgression = TFWorld.isProgressionEnforced(catalyst.world);
					if (!teleporter.isSafeAround(pos, catalyst, checkProgression)) {
						// TODO: "failure" effect - particles?
						if (player != null) {
							player.sendStatusMessage(new TextComponentTranslation(TwilightForestMod.ID + ".twilight_portal.unsafe"), true);
						}
						return false;
					}
				}

				catalyst.getItem().shrink(1);
				causeLightning(world, pos, TFConfig.portalLightning);

				for (Map.Entry<BlockPos, Boolean> checkedPos : blocksChecked.entrySet()) {
					if (checkedPos.getValue()) {
						world.setBlockState(checkedPos.getKey(), TFBlocks.twilight_portal.getDefaultState(), 2);
					}
				}

				return true;
			}
		}

		return false;
	}

	public boolean canFormPortal(BlockState state) {
		return state == Blocks.WATER.getDefaultState() || state.getBlock() == this && state.getValue(DISALLOW_RETURN);
	}

	private static void causeLightning(World world, BlockPos pos, boolean fake) {
		EntityLightningBolt bolt = new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, fake);
		world.addWeatherEffect(bolt);

		if (fake) {
			double range = 3.0D;
			List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos).grow(range));

			for (Entity victim : list) {
				if (!ForgeEventFactory.onEntityStruckByLightning(victim, bolt)) {
					victim.onStruckByLightning(bolt);
				}
			}
		}
	}

	private static boolean recursivelyValidatePortal(World world, BlockPos pos, Map<BlockPos, Boolean> blocksChecked, MutableInt portalSize, BlockState requiredState) {

		if (portalSize.incrementAndGet() > MAX_PORTAL_SIZE) return false;

		boolean isPoolProbablyEnclosed = true;

		for (int i = 0; i < Direction.HORIZONTALS.length && portalSize.intValue() <= MAX_PORTAL_SIZE; i++) {
			BlockPos positionCheck = pos.offset(Direction.HORIZONTALS[i]);

			if (!blocksChecked.containsKey(positionCheck)) {
				BlockState state = world.getBlockState(positionCheck);

				if (state == requiredState && world.getBlockState(positionCheck.down()).isFullCube()) {
					blocksChecked.put(positionCheck, true);
					if (isPoolProbablyEnclosed) {
						isPoolProbablyEnclosed = recursivelyValidatePortal(world, positionCheck, blocksChecked, portalSize, requiredState);
					}

				} else if (isGrassOrDirt(state) && isNatureBlock(world.getBlockState(positionCheck.up())) || state.getBlock() == TFBlocks.uberous_soil) {
					blocksChecked.put(positionCheck, false);

				} else return false;
			}
		}

		return isPoolProbablyEnclosed;
	}

	private static boolean isNatureBlock(BlockState state) {
		Material mat = state.getMaterial();
		return mat == Material.PLANTS || mat == Material.VINE || mat == Material.LEAVES;
	}

	private static boolean isGrassOrDirt(BlockState state) {
		Material mat = state.getMaterial();
		return state.isFullCube() && (mat == Material.GRASS || mat == Material.GROUND);
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block notUsed, BlockPos fromPos) {
		boolean good = world.getBlockState(pos.down()).isFullCube();

		for (Direction facing : Direction.HORIZONTALS) {
			if (!good) break;

			BlockState neighboringState = world.getBlockState(pos.offset(facing));

			good = isGrassOrDirt(neighboringState) || neighboringState == state;
		}

		if (!good) {
			world.playEvent(2001, pos, Block.getStateId(state));
			world.setBlockState(pos, Blocks.WATER.getDefaultState(), 0b11);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
		if (state == this.getDefaultState()) {
			attemptSendPlayer(entity, false);
		}
	}

	private static int getDestination(Entity entity) {
		return entity.dimension != TFConfig.dimension.dimensionID
				? TFConfig.dimension.dimensionID : TFConfig.originDimension;
	}

	public static void attemptSendPlayer(Entity entity, boolean forcedEntry) {

		if (entity.isDead || entity.world.isRemote) {
			return;
		}

		if (entity.isPassenger() || entity.isBeingRidden() || !entity.isNonBoss()) {
			return;
		}

		if (!forcedEntry && entity.timeUntilPortal > 0) {
			return;
		}

		// set a cooldown before this can run again
		entity.timeUntilPortal = 10;

		int destination = getDestination(entity);

		entity.changeDimension(destination, TFTeleporter.getTeleporterForDim(entity.getServer(), destination));

		if (destination == TFConfig.dimension.dimensionID && entity instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) entity;
			// set respawn point for TF dimension to near the arrival portal
			playerMP.setSpawnChunk(new BlockPos(playerMP), true, TFConfig.dimension.dimensionID);
		}
	}

	// Full [VanillaCopy] of BlockPortal.randomDisplayTick
	// TODO Eeeh... Let's look at changing this too alongside a new model.
	@Override
	@OnlyIn(Dist.CLIENT)
	public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		int random = rand.nextInt(100);
		if (stateIn.getValue(DISALLOW_RETURN) && random < 80) return;

		if (random == 0) {
			worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double xPos = (double) ((float) pos.getX() + rand.nextFloat());
			double yPos = pos.getY()+1D;
			double zPos = (double) ((float) pos.getZ() + rand.nextFloat());
			double xSpeed = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			double ySpeed = rand.nextFloat();
			double zSpeed = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			//int j = rand.nextInt(2) * 2 - 1;

			//if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
			//	xPos = (double) pos.getX() + 0.5D + 0.25D * (double) j;
			//	xSpeed = (double) (rand.nextFloat() * 2.0F * (float) j);
			//} else {
			//	zPos = (double) pos.getZ() + 0.5D + 0.25D * (double) j;
			//	zSpeed = (double) (rand.nextFloat() * 2.0F * (float) j);
			//}

			worldIn.spawnParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
		}
	}
}
