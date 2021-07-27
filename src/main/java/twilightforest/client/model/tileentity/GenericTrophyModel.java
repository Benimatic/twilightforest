package twilightforest.client.model.tileentity;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;

//[VanillaCopy] of SkullModelBase, this is just so our trophies use it
public abstract class GenericTrophyModel extends Model {

	public GenericTrophyModel() {
		super(RenderType::entityTranslucent);
	}

	public abstract void setRotations(float x, float y, float z);
}
