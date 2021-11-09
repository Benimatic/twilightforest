package twilightforest.client.model.item;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.pipeline.LightUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TintIndexAwareFullbrightBakedModel extends FullbrightBakedModel {

	public TintIndexAwareFullbrightBakedModel(BakedModel delegate) {
		super(delegate);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return cachedQuads.computeIfAbsent(side, (face) -> {
			List<BakedQuad> quads = delegate.getQuads(state, side, rand);
			for (BakedQuad quad : quads)
				if (quad.isTinted())
					LightUtil.setLightData(quad, 0xF000F0);
			return quads;
		});
	}
}
