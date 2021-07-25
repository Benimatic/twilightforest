package twilightforest;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;

public class TFCommonProxy {

	@Nullable
	public static Iterable<Entity> getEntityListForASM() {
		return ASMHooks.world instanceof ServerLevel ? ((ServerLevel) ASMHooks.world).getAllEntities() : null;
	}

	public void init() {}
}
