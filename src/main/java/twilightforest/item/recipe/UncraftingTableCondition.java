package twilightforest.item.recipe;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.animal.Cod;
import net.neoforged.neoforge.common.conditions.ICondition;
import twilightforest.TFConfig;

public class UncraftingTableCondition implements ICondition {

	public static final UncraftingTableCondition INSTANCE = new UncraftingTableCondition();
	public static final Codec<UncraftingTableCondition> CODEC = Codec.unit(INSTANCE);

	@Override
	public Codec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public boolean test(IContext context) {
		return !TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.disableEntireTable.get();
	}
}
