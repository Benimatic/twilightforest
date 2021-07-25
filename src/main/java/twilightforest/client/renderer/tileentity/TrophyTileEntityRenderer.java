package twilightforest.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.block.AbstractTrophyBlock;
import twilightforest.block.TrophyBlock;
import twilightforest.block.TrophyWallBlock;
import twilightforest.block.TFBlocks;
import twilightforest.client.model.tileentity.*;
import twilightforest.enums.BossVariant;
import twilightforest.tileentity.TrophyTileEntity;

import javax.annotation.Nullable;

//Legacy lines are commented out and labeled
public class TrophyTileEntityRenderer extends BlockEntityRenderer<TrophyTileEntity> {

	private static final HydraTrophyModel hydraHead = new HydraTrophyModel();
	private static final ResourceLocation textureLocHydra = TwilightForestMod.getModelTexture("hydra4.png");

	private static final NagaTrophyModel nagaHead = new NagaTrophyModel();
	private static final ResourceLocation textureLocNaga = TwilightForestMod.getModelTexture("nagahead.png");

	private static final LichTrophyModel lichHead = new LichTrophyModel();
	private static final ResourceLocation textureLocLich = TwilightForestMod.getModelTexture("twilightlich64.png");

	private static final UrGhastTrophyModel ghastHead = new UrGhastTrophyModel();
	private static final ResourceLocation textureLocUrGhast = TwilightForestMod.getModelTexture("towerboss.png");

	private static final SnowQueenTrophyModel waifuHead = new SnowQueenTrophyModel();
	private static final ResourceLocation textureLocSnowQueen = TwilightForestMod.getModelTexture("snowqueen.png");

	private static final MinoshroomTrophyModel minoshroomHead = new MinoshroomTrophyModel();
	private static final ResourceLocation textureLocMinoshroom = TwilightForestMod.getModelTexture("minoshroomtaur.png");

	private static final KnightPhantomTrophyModel phantomHead = new KnightPhantomTrophyModel();
	private static final ResourceLocation textureLocKnightPhantom = TwilightForestMod.getModelTexture("phantomskeleton.png");
	private static final PhantomArmorTrophyModel phantomArmorModel = new PhantomArmorTrophyModel();
	private static final ResourceLocation textureLocKnightPhantomArmor = new ResourceLocation(TwilightForestMod.ARMOR_DIR + "phantom_1.png");

	private static final ModelTFYetiAlphaTrophy yetiHead = new ModelTFYetiAlphaTrophy();
	private static final ResourceLocation textureLocYeti = TwilightForestMod.getModelTexture("yetialpha.png");

	private static final QuestRamTrophyModel ramHead = new QuestRamTrophyModel();
	private static final ResourceLocation textureLocQuestRam = TwilightForestMod.getModelTexture("questram.png");
	private static final ResourceLocation textureLocQuestRamLines = TwilightForestMod.getModelTexture("questram_lines.png");

	public TrophyTileEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	public static ItemStack stack = new ItemStack(TFBlocks.naga_trophy.get());
	@Override
	public void render(TrophyTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		float f = tileEntityIn.getAnimationProgress(partialTicks);
		BlockState blockstate = tileEntityIn.getBlockState();
		boolean flag = blockstate.getBlock() instanceof TrophyWallBlock;
		Direction direction = flag ? blockstate.getValue(TrophyWallBlock.FACING) : null;
		float f1 = 22.5F * (flag ? (2 + direction.get2DDataValue()) * 4 : blockstate.getValue(TrophyBlock.ROTATION));
		matrixStackIn.pushPose();
		if (((AbstractTrophyBlock) blockstate.getBlock()).getVariant() == BossVariant.HYDRA && flag) {
			//FIXME: both rotation points are legacy
			//hydraHead.mouth.setRotationPoint(0.0F, 15.0F, -19.0F);
			hydraHead.openMouthForTrophy(0.5F);
		} else {
			//hydraHead.mouth.setRotationPoint(0.0F, 10.0F, -20.0F);
			hydraHead.openMouthForTrophy(0.0F);
		}
		if (((AbstractTrophyBlock) blockstate.getBlock()).getVariant() == BossVariant.UR_GHAST) {
			ghastHead.setTranslate(matrixStackIn, 0F, 1.0F, 0F);
		}
		render(direction, f1, ((AbstractTrophyBlock) blockstate.getBlock()).getVariant(), f, matrixStackIn, bufferIn, combinedLightIn, ItemTransforms.TransformType.NONE);
		matrixStackIn.popPose();
	}

	public static void render(@Nullable Direction directionIn, float y, BossVariant variant, float animationProgress, PoseStack matrixStackIn, MultiBufferSource buffer, int combinedLight, ItemTransforms.TransformType camera) {
		matrixStackIn.pushPose();
		if (directionIn == null || variant == BossVariant.UR_GHAST) {
			matrixStackIn.translate(0.5D, 0.0D, 0.5D);
		} else {
			matrixStackIn.translate(0.5F - directionIn.getStepX() * 0.25F, 0.25D, 0.5F - directionIn.getStepZ() * 0.25F);
		}
		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		switch (variant) {
		case HYDRA:
			matrixStackIn.scale(0.25F, 0.25F, 0.25F);
			matrixStackIn.translate(0.0F, -1.0F, 0.0F);
			if (camera == ItemTransforms.TransformType.GUI) hydraHead.openMouthForTrophy(0.35F);
			hydraHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer hydraVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocHydra));
			hydraHead.head.render(matrixStackIn, hydraVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case NAGA:
			matrixStackIn.scale(0.5f, 0.5f, 0.5f);
			matrixStackIn.translate(0F, .25F, 0F);
			nagaHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer nagaVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocNaga));
			nagaHead.head.render(matrixStackIn, nagaVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case LICH:
			matrixStackIn.translate(0.0F, .25F, 0.0F);
			lichHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer lichVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocLich));
			lichHead.head.render(matrixStackIn, lichVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case UR_GHAST:
			matrixStackIn.scale(0.5F, 0.5F, 0.5F);
			ghastHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer ghastVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocUrGhast));
			ghastHead.body.render(matrixStackIn, ghastVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case SNOW_QUEEN:
			//FIXME: Legacy
			//matrixStackIn.translate(0.0F, 0.25F, 0.0F);
			waifuHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer waifuVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocSnowQueen));
			waifuHead.head.render(matrixStackIn, waifuVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case MINOSHROOM:
			//FIXME: Legacy
			//matrixStackIn.translate(0.0F, 0.25F, 0.0F);
			matrixStackIn.translate(0.0F, 0.31F, 0.0F);
			minoshroomHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer minoVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocMinoshroom));
			minoshroomHead.head.render(matrixStackIn, minoVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case KNIGHT_PHANTOM:
			matrixStackIn.translate(0.0F, 0.25F, 0.0F);
			phantomHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer phantomVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocKnightPhantom));
			phantomHead.head.render(matrixStackIn, phantomVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			
			matrixStackIn.scale(1.1F, 1.1F, 1.1F);
			matrixStackIn.translate(0.0F, 0.05F, 0.0F);
			phantomArmorModel.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer phantomArmorVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocKnightPhantomArmor));
			phantomArmorModel.head.render(matrixStackIn, phantomArmorVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.0625F);
			break;
		case ALPHA_YETI:
			matrixStackIn.scale(0.2F, 0.2F, 0.2F);
			matrixStackIn.translate(0.0F, -1.5F, 0.0F);
			yetiHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer yetiVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocYeti));
			yetiHead.main.render(matrixStackIn, yetiVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		case QUEST_RAM:
			matrixStackIn.scale(0.7f, 0.7f, 0.7f);
			ramHead.setRotations(animationProgress * 4.5F, y, 0.0F);
			VertexConsumer ramVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocQuestRam));
			ramHead.renderToBuffer(matrixStackIn, ramVertex, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			break;
		default:
			break;
		}
		matrixStackIn.popPose();
	}
}
