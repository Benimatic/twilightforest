package twilightforest.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BuiltInItemModel implements BakedModel {

	private class Overrides extends ItemOverrides {

		Overrides() {
			super(/*Collections.emptyList()*/);
		}

		@Nullable
		@Override
		public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) {
			setItemStack(stack);
			return BuiltInItemModel.this;
		}
	}

	private final TextureAtlasSprite particleTexture;
	private final ItemOverrides overrides = new Overrides();

	protected BuiltInItemModel(String particleTextureName) {
		this.particleTexture = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(particleTextureName));
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return Collections.emptyList();
	}

	@Override
	public boolean useAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isCustomRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return particleTexture;
	}

	@Override
	public boolean usesBlockLight() {
		return true; //FIXME
	}

	@Override
	public ItemOverrides getOverrides() {
		return overrides;
	}

	@Override
	public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack mat) {
		setTransform(cameraTransformType);
		return this;
	}

	protected abstract void setItemStack(ItemStack stack);

	protected abstract void setTransform(ItemTransforms.TransformType transform);
}
