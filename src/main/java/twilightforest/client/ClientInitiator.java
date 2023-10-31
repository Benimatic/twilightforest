package twilightforest.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;

import java.util.function.Supplier;

import twilightforest.TwilightForestMod;

/**
 * Avoid adding to this class, this is strictly for {@link TFClientSetup} initiliazation from {@link TwilightForestMod#TwilightForestMod()} via {@link DistExecutor#safeRunWhenOn(Dist, Supplier)}
 */
public class ClientInitiator {

	public static void call() {
		TFClientSetup.init();
	}

}
