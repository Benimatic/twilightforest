package twilightforest;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import twilightforest.advancements.ProgressionTrigger;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TFAdvancements {
    public static final ProgressionTrigger ADVANCEMENT_UNLOCKED = CriteriaTriggers.register(new ProgressionTrigger());

    public static void init() {
    }
}
