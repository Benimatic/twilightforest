package twilightforest.client.model.item;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.LightUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FullbrightBakedModel implements BakedModel {

	private final BakedModel delegate;
	private Map<Direction, List<BakedQuad>> cachedQuads = Maps.newHashMap();

	public FullbrightBakedModel(BakedModel delegate) {
		this.delegate = delegate;
	}

	@Override
	public BakedModel getBakedModel() {
		return delegate;
	}

	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
		return getQuads(state, side, rand);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return cachedQuads.computeIfAbsent(side, (face) -> {
			List<BakedQuad> quads = delegate.getQuads(state, side, rand);
			for (BakedQuad quad : quads)
				LightUtil.setLightData(quad, 0xF000F0);
			return quads;
		});
	}

	@Override
	public boolean useAmbientOcclusion() {
		return delegate.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return delegate.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return delegate.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return delegate.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return delegate.getParticleIcon();
	}

	@Override
	public ItemTransforms getTransforms() {
		return delegate.getTransforms();
	}

	@Override
	public ItemOverrides getOverrides() {
		return delegate.getOverrides();
	}

	@Override
	public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack mat) {
		return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, cameraTransformType, mat);
	}
}
