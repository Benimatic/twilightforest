package twilightforest.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.block.*;
import twilightforest.block.entity.TwilightChestEntity;
import twilightforest.client.model.tileentity.GenericTrophyModel;
import twilightforest.client.renderer.tileentity.SkullCandleTileEntityRenderer;
import twilightforest.client.renderer.tileentity.TrophyTileEntityRenderer;
import twilightforest.enums.BossVariant;
import twilightforest.block.entity.KeepsakeCasketBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class ISTER extends BlockEntityWithoutLevelRenderer {
	private final ResourceLocation typeId;
	private BlockEntity dummy;
	private final KeepsakeCasketBlockEntity keepsakeCasketBlockEntity = new KeepsakeCasketBlockEntity(BlockPos.ZERO, TFBlocks.KEEPSAKE_CASKET.get().defaultBlockState());
	private final Map<Block, TwilightChestEntity> chestEntities = Util.make(new HashMap<>(), map -> {
		makeInstance(map, TFBlocks.TWILIGHT_OAK_CHEST);
		makeInstance(map, TFBlocks.CANOPY_CHEST);
		makeInstance(map, TFBlocks.MANGROVE_CHEST);
		makeInstance(map, TFBlocks.DARKWOOD_CHEST);
		makeInstance(map, TFBlocks.TIME_CHEST);
		makeInstance(map, TFBlocks.TRANSFORMATION_CHEST);
		makeInstance(map, TFBlocks.MINING_CHEST);
		makeInstance(map, TFBlocks.SORTING_CHEST);
	});

	// When this is called from Item register, TEType register has not run yet so we can't pass the actual object
	public ISTER(ResourceLocation typeId) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		this.typeId = typeId;
	}

	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType camera, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {

		if (dummy == null) {
			dummy = ForgeRegistries.BLOCK_ENTITIES.getValue(typeId).create(BlockPos.ZERO, Blocks.AIR.defaultBlockState());
		}
		Item item = stack.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem) item).getBlock();
			if (block instanceof AbstractTrophyBlock) {

				BossVariant variant = ((AbstractTrophyBlock)block).getVariant();
				GenericTrophyModel trophy = TrophyTileEntityRenderer.createTrophyRenderers(Minecraft.getInstance().getEntityModels()).get(variant);

				if(camera == ItemTransforms.TransformType.GUI) {

					ModelResourceLocation back = new ModelResourceLocation(TwilightForestMod.prefix(((AbstractTrophyBlock) block).getVariant().getTrophyType().getModelName()), "inventory");
					BakedModel modelBack = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(back);

					Lighting.setupForFlatItems();
					MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
					ms.pushPose();
					Lighting.setupForFlatItems();
					ms.translate(0.5F, 0.5F, -1.5F);
					Minecraft.getInstance().getItemRenderer().render(TrophyTileEntityRenderer.stack, ItemTransforms.TransformType.GUI, false, ms, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, ForgeHooksClient.handleCameraTransforms(ms, modelBack, camera, false));
					ms.popPose();
					bufferSource.endBatch();
					Lighting.setupFor3DItems();

					ms.pushPose();
					ms.translate(0.5F, 0.5F, 0.5F);
					if(((AbstractTrophyBlock) block).getVariant() == BossVariant.HYDRA || ((AbstractTrophyBlock) block).getVariant() == BossVariant.QUEST_RAM) ms.scale(0.9F, 0.9F, 0.9F);
					ms.mulPose(Vector3f.XP.rotationDegrees(30));
					ms.mulPose(Vector3f.YN.rotationDegrees(TFConfig.CLIENT_CONFIG.rotateTrophyHeadsGui.get() ? TFClientEvents.rotationTicker : -45));
					ms.translate(-0.5F, -0.5F, -0.5F);
					ms.translate(0.0F, 0.25F, 0.0F);
					if(((AbstractTrophyBlock) block).getVariant() == BossVariant.UR_GHAST) ms.translate(0.0F, 0.5F, 0.0F);
					if(((AbstractTrophyBlock) block).getVariant() == BossVariant.ALPHA_YETI) ms.translate(0.0F, -0.15F, 0.0F);
					TrophyTileEntityRenderer.render(null, 180.0F, trophy, variant, 0.0F, ms, buffers, light, camera);
					ms.popPose();

				} else {
					TrophyTileEntityRenderer.render(null, 180.0F, trophy, variant, 0.0F, ms, buffers, light, camera);
				}
			} else if (block instanceof KeepsakeCasketBlock) {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.keepsakeCasketBlockEntity, ms, buffers, light, overlay);
			} else if (block instanceof TwilightChest) {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.chestEntities.get(block), ms, buffers, light, overlay);
			} else if (block instanceof AbstractSkullCandleBlock){
				SkullBlock.Type type = ((AbstractSkullCandleBlock)block).getType();
				SkullModelBase base = SkullCandleTileEntityRenderer.createSkullRenderers(Minecraft.getInstance().getEntityModels()).get(type);
				SkullCandleTileEntityRenderer.renderSkull(null, 180.0F, 0.0F, ms, buffers, light, base, RenderType.entityCutoutNoCull(SkullCandleTileEntityRenderer.SKIN_BY_TYPE.get(type)));
				//we put the candle
				ms.translate(0.0F, 0.5F, 0.0F);
				CompoundTag tag = stack.getTagElement("BlockStateTag");
				if(tag != null && tag.contains("color") && tag.contains("candles")) {
					Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
							AbstractSkullCandleBlock.candleColorToCandle(tag.getString("color"))
									.defaultBlockState().setValue(CandleBlock.CANDLES, tag.getInt("candles")), ms, buffers, light, overlay);
				}
			} else {
				BlockEntityRenderer<BlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(dummy);
				renderer.render(null, 0, ms, buffers, light, overlay);
			}
		}
	}

	public static void makeInstance(Map<Block, TwilightChestEntity> map, RegistryObject<? extends ChestBlock> registryObject) {
		ChestBlock block = registryObject.get();

		map.put(block, new TwilightChestEntity(BlockPos.ZERO, block.defaultBlockState()));
	}
}
