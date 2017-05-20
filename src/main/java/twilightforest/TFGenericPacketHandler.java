package twilightforest;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import twilightforest.network.*;

public class TFGenericPacketHandler {

	public static void init() {
		TwilightForestMod.genericChannel = NetworkRegistry.INSTANCE.newSimpleChannel(TwilightForestMod.ID);

		int id = 0;
		TwilightForestMod.genericChannel.registerMessage(PacketAnnihilateBlock.Handler.class, PacketAnnihilateBlock.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketAreaProtection.Handler.class, PacketAreaProtection.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketChangeBiome.Handler.class, PacketChangeBiome.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketEnforceProgressionStatus.Handler.class, PacketEnforceProgressionStatus.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketStructureProtection.Handler.class, PacketStructureProtection.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketStructureProtectionClear.Handler.class, PacketStructureProtectionClear.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketThrowPlayer.Handler.class, PacketThrowPlayer.class, id++, Side.CLIENT);
		TwilightForestMod.genericChannel.registerMessage(PacketMagicMapFeatures.Handler.class, PacketMagicMapFeatures.class, id++, Side.CLIENT);
	}

}
