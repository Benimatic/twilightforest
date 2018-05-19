package twilightforest.client.texture.type;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import team.chisel.ctm.api.texture.ICTMTexture;
import team.chisel.ctm.api.texture.TextureType;
import team.chisel.ctm.api.util.TextureInfo;
import team.chisel.ctm.client.model.AbstractCTMBakedModel;
import team.chisel.ctm.client.texture.ctx.TextureContextCTM;
import team.chisel.ctm.client.texture.render.TextureCTM;
import team.chisel.ctm.client.texture.type.TextureTypeCTM;
import team.chisel.ctm.client.util.CTMLogic;
import twilightforest.client.texture.render.CTMLogicNorth;

import javax.annotation.Nonnull;

@TextureType("ctm_tf_north")
public class TextureTypeCTMNorth extends TextureTypeCTM {

	@Override
	public ICTMTexture<? extends TextureTypeCTM> makeTexture(TextureInfo info) {
		return new TextureCTM<TextureTypeCTM>(this, info);
	}

	@Override
	public TextureContextCTM getBlockRenderContext(IBlockState state, IBlockAccess world, BlockPos pos, ICTMTexture<?> tex) {
		return new TextureContextCTM(state, world, pos, (TextureCTM<?>) tex) {
			@Override
			protected CTMLogic createCTM(@Nonnull IBlockState state) {
				IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state);
				if (model instanceof AbstractCTMBakedModel) {
					return CTMLogicNorth.getInstance().ignoreStates(((AbstractCTMBakedModel) model).getModel().ignoreStates());
				}
				return CTMLogicNorth.getInstance();
			}
		};
	}
}