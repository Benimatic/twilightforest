package twilightforest;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import twilightforest.advancements.HasAdvancementTrigger;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TFAdvancements {
    public static final HasAdvancementTrigger ADVANCEMENT_UNLOCKED = CriteriaTriggers.register(new HasAdvancementTrigger());

    public static void init() {
    }
}
