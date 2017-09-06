package twilightforest;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import twilightforest.advancements.AdvancementTrigger;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TFAdvancements {
    public static void init() {
        CriteriaTriggers.register(new AdvancementTrigger());
    }
}
