package twilightforest.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//TODO 1.14: Delete all this
@Deprecated
public interface ModelRegisterCallback {

	/**
	 * Do whatever is necessary to register your models. (Statemappers, custom meshers, etc.)
	 */
	@OnlyIn(Dist.CLIENT)
	default void registerModel() {
		if (this instanceof Item) {
			ModelLoader.setCustomModelResourceLocation((Item) this, 0, new ModelResourceLocation(((Item) this).getRegistryName(), "inventory"));
		} else if (this instanceof Block) {
			ModelUtils.registerToState((Block) this, 0, ((Block) this).getDefaultState());
		}
	}

	class SingleStateMapper extends StateMapperBase {

		private final ModelResourceLocation location;

		public SingleStateMapper(ModelResourceLocation location) {
			this.location = location;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(BlockState state) {
			return location;
		}
	}
}
