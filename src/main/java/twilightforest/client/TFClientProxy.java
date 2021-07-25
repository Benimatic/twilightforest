package twilightforest.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import twilightforest.ASMHooks;
import twilightforest.TFCommonProxy;

import javax.annotation.Nullable;

public class TFClientProxy extends TFCommonProxy {

	private boolean isDangerOverlayShown;

	@Nullable
	public static Iterable<Entity> getEntityListForASM() {
		return ASMHooks.world instanceof ServerLevel ? ((ServerLevel) ASMHooks.world).getAllEntities() : ASMHooks.world instanceof ClientLevel ? ((ClientLevel) ASMHooks.world).entitiesForRendering() : null;
	}

	@Override
	public void init() {}

//	public boolean isDangerOverlayShown() {
//		return isDangerOverlayShown;
//	}
//
//	public void setDangerOverlayShown(boolean isDangerOverlayShown) {
//		this.isDangerOverlayShown = isDangerOverlayShown;
//	}

}
