package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.fml.network.PacketDistributor;
import twilightforest.client.particle.data.LeafParticleData;
import twilightforest.enums.PlantVariant;
import twilightforest.network.SpawnFallenLeafFromPacket;
import twilightforest.network.TFPacketHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class TFPlantBlock extends BushBlock {
	private static final VoxelShape MAYAPPLE_SHAPE = box(4, 0, 4, 13, 6, 13);
	private static final VoxelShape FALLEN_LEAVES_SHAPE = box(0, 0, 0, 16, 1, 16);

	public final PlantVariant plantVariant;

	protected TFPlantBlock(PlantVariant plant, BlockBehaviour.Properties props) {
		super(props);
		plantVariant = plant;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState soil = world.getBlockState(pos.below());

		switch (plantVariant) {
		case TORCHBERRY:
		case ROOT_STRAND:
			return TFPlantBlock.canPlaceRootAt(world, pos);
		case FALLEN_LEAVES:
		case MUSHGLOOM:
		case MOSSPATCH:
			return soil.isFaceSturdy(world, pos, Direction.UP);
		default:
			return (world.getMaxLocalRawBrightness(pos) >= 3 || world.canSeeSkyFromBelowWater(pos)) && soil.canSustainPlant(world, pos.below(), Direction.UP, this);
		}
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter access, BlockPos pos, CollisionContext context) {
		long seed = pos.getX() * 3129871 ^ pos.getY() * 116129781L ^ pos.getZ();
		seed = seed * seed * 42317861L + seed * 11L;

		if (plantVariant == PlantVariant.MOSSPATCH) {
			int xOff0 = (int) (seed >> 12 & 3L);
			int xOff1 = (int) (seed >> 15 & 3L);
			int zOff0 = (int) (seed >> 18 & 3L);
			int zOff1 = (int) (seed >> 21 & 3L);

			boolean xConnect0 = access.getBlockState(pos.east()).getBlock() == this && access.getBlockState(pos.east()).getBlock() == TFBlocks.moss_patch.get();
			boolean xConnect1 = access.getBlockState(pos.west()).getBlock() == this && access.getBlockState(pos.west()).getBlock() == TFBlocks.moss_patch.get();
			boolean zConnect0 = access.getBlockState(pos.south()).getBlock() == this && access.getBlockState(pos.north()).getBlock() == TFBlocks.moss_patch.get();
			boolean zConnect1 = access.getBlockState(pos.north()).getBlock() == this && access.getBlockState(pos.south()).getBlock() == TFBlocks.moss_patch.get();

			return Shapes.create(new AABB(xConnect1 ? 0F : (1F + xOff1) / 16F, 0.0F, zConnect1 ? 0F : (1F + zOff1) / 16F,
					xConnect0 ? 1F : (15F - xOff0) / 16F, 1F / 16F, zConnect0 ? 1F : (15F - zOff0) / 16F));

		} else if (plantVariant == PlantVariant.CLOVERPATCH) {
			int xOff0 = (int) (seed >> 12 & 3L);
			int xOff1 = (int) (seed >> 15 & 3L);
			int zOff0 = (int) (seed >> 18 & 3L);
			int zOff1 = (int) (seed >> 21 & 3L);

			int yOff0 = (int) (seed >> 24 & 1L);
			int yOff1 = (int) (seed >> 27 & 1L);

			boolean xConnect0 = access.getBlockState(pos.east()).getBlock() == this && access.getBlockState(pos.east()).getBlock() == TFBlocks.clover_patch.get();
			boolean xConnect1 = access.getBlockState(pos.west()).getBlock() == this && access.getBlockState(pos.west()).getBlock() == TFBlocks.clover_patch.get();
			boolean zConnect0 = access.getBlockState(pos.south()).getBlock() == this && access.getBlockState(pos.north()).getBlock() == TFBlocks.clover_patch.get();
			boolean zConnect1 = access.getBlockState(pos.north()).getBlock() == this && access.getBlockState(pos.south()).getBlock() == TFBlocks.clover_patch.get();

			return Shapes.create(new AABB(xConnect1 ? 0F : (1F + xOff1) / 16F, 0.0F, zConnect1 ? 0F : (1F + zOff1) / 16F,
					xConnect0 ? 1F : (15F - xOff0) / 16F, (1F + yOff0 + yOff1) / 16F, zConnect0 ? 1F : (15F - zOff0) / 16F));
		} else if (plantVariant == PlantVariant.MAYAPPLE) {
			return MAYAPPLE_SHAPE;
		} else if (plantVariant == PlantVariant.FALLEN_LEAVES) {
			return FALLEN_LEAVES_SHAPE;
		} else {
			return Shapes.block();
		}
	}

	public static boolean canPlaceRootAt(LevelReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos.above());
		if (state.getMaterial() == Material.DIRT || state.getMaterial() == Material.GRASS) {
			// can always hang below dirt blocks
			return true;
		} else {
			return (state.getBlock() == TFBlocks.root_strand.get()
					|| state == TFBlocks.root.get().defaultBlockState());
		}
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.getBlock() == this) {
			switch (plantVariant) {
				case MOSSPATCH:
				case MUSHGLOOM:
					return PlantType.CAVE;
				default:
					return PlantType.PLAINS;
			}
		}
		return PlantType.PLAINS;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		super.animateTick(state, world, pos, random);

		if (state.getBlock() == TFBlocks.moss_patch.get() && random.nextInt(10) == 0) {
			world.addParticle(ParticleTypes.MYCELIUM, pos.getX() + random.nextFloat(), pos.getY() + 0.1F, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
		} else if (state.getBlock() == TFBlocks.fallen_leaves.get() && random.nextInt(50) == 0) {
			float dist = 10F;
			if (!world.canSeeSkyFromBelowWater(pos)) {
				for (int y = 0; y <= dist; y++)
					if (world.getBlockState(pos.above(y)).getMaterial() == Material.LEAVES) {
						dist = y;
						break;
					}
				if (dist > 10F)
					return;
			}

			int color = Minecraft.getInstance().getBlockColors().getColor(Blocks.OAK_LEAVES.defaultBlockState(), world, pos, 0);
			int r = Mth.clamp(((color >> 16) & 0xFF) + RANDOM.nextInt(0x22) - 0x11, 0x00, 0xFF);
			int g = Mth.clamp(((color >> 8) & 0xFF) + RANDOM.nextInt(0x22) - 0x11, 0x00, 0xFF);
			int b = Mth.clamp((color & 0xFF) + RANDOM.nextInt(0x22) - 0x11, 0x00, 0xFF);
			world.addParticle(new LeafParticleData(r, g, b), pos.getX() + random.nextFloat(), pos.getY() + dist - 0.25F, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	@Deprecated
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entityIn) {
		super.entityInside(state, world, pos, entityIn);
		if (state.getBlock() == TFBlocks.fallen_leaves.get() && entityIn instanceof LivingEntity && (entityIn.getDeltaMovement().x() != 0 || entityIn.getDeltaMovement().z() != 0) && RANDOM.nextBoolean()) {
			if(world.isClientSide) {
				int color = Minecraft.getInstance().getBlockColors().getColor(Blocks.OAK_LEAVES.defaultBlockState(), world, pos, 0);
				int r = Mth.clamp(((color >> 16) & 0xFF) + RANDOM.nextInt(0x22) - 0x11, 0x00, 0xFF);
				int g = Mth.clamp(((color >> 8) & 0xFF) + RANDOM.nextInt(0x22) - 0x11, 0x00, 0xFF);
				int b = Mth.clamp((color & 0xFF) + RANDOM.nextInt(0x22) - 0x11, 0x00, 0xFF);
					world.addParticle(new LeafParticleData(r, g, b),
							pos.getX() + world.random.nextFloat(),
							pos.getY(),
							pos.getZ() + world.random.nextFloat(),

							(world.random.nextFloat() * -0.5F) * entityIn.getDeltaMovement().x(),
							world.random.nextFloat() * 0.5F + 0.25F,
							(world.random.nextFloat() * -0.5F) * entityIn.getDeltaMovement().z()
				);
			} else if (world instanceof ServerLevel)
				TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entityIn), new SpawnFallenLeafFromPacket(pos, entityIn.getDeltaMovement()));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if(stack.getItem() == TFBlocks.clover_patch.get().asItem()) {
			tooltip.add(new TranslatableComponent("twilightforest.misc.nyi"));
		} else if (stack.getItem() == TFBlocks.moss_patch.get().asItem()) {
			tooltip.add(new TranslatableComponent("twilightforest.misc.wip0"));
			tooltip.add(new TranslatableComponent("twilightforest.misc.wip1"));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
