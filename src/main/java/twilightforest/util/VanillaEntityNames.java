package twilightforest.util;

import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

/**
 * Created by codew on 29/05/2017.
 */
public class VanillaEntityNames {
    public static final ResourceLocation CAVE_SPIDER = prefix("cave_spider");
    public static final ResourceLocation SPIDER = prefix("spider");
    public static final ResourceLocation ZOMBIE = prefix("zombie");
    public static final ResourceLocation SKELETON = prefix("skeleton");

    private static ResourceLocation prefix(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
