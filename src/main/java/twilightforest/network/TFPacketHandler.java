package twilightforest.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import twilightforest.TwilightForestMod;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TFPacketHandler {
	// Bump this number every time theres a breaking change, to ensure people dont mess things up when joining on the wrong version
	private static final String PROTOCOL_VERSION = "2";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			TwilightForestMod.prefix("channel"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	@SuppressWarnings({"UnusedAssignment", "Convert2Lambda", "Anonymous2MethodRef"})
	public static void init() {
		int id = 0;
		//as ugly as this is compared to the rest of the packets do not change it, it crashes the server otherwise
		CHANNEL.messageBuilder(AreaProtectionPacket.class, id++).encoder(AreaProtectionPacket::encode).decoder(AreaProtectionPacket::new).consumerMainThread(new BiConsumer<>() {
			@Override
			public void accept(AreaProtectionPacket message, Supplier<NetworkEvent.Context> ctx) {
				AreaProtectionPacket.Handler.onMessage(message, ctx);
			}
		}).add();
		CHANNEL.messageBuilder(ChangeBiomePacket.class, id++).encoder(ChangeBiomePacket::encode).decoder(ChangeBiomePacket::new).consumerMainThread(ChangeBiomePacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(EnforceProgressionStatusPacket.class, id++).encoder(EnforceProgressionStatusPacket::encode).decoder(EnforceProgressionStatusPacket::new).consumerMainThread(EnforceProgressionStatusPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(StructureProtectionPacket.class, id++).encoder(StructureProtectionPacket::encode).decoder(StructureProtectionPacket::new).consumerMainThread(StructureProtectionPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(StructureProtectionClearPacket.class, id++).encoder(StructureProtectionClearPacket::encode).decoder(StructureProtectionClearPacket::new).consumerMainThread(StructureProtectionClearPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(ThrowPlayerPacket.class, id++).encoder(ThrowPlayerPacket::encode).decoder(ThrowPlayerPacket::new).consumerMainThread(ThrowPlayerPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(MagicMapPacket.class, id++).encoder(MagicMapPacket::encode).decoder(MagicMapPacket::new).consumerMainThread(MagicMapPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(MazeMapPacket.class, id++).encoder(MazeMapPacket::encode).decoder(MazeMapPacket::new).consumerMainThread(MazeMapPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(UpdateShieldPacket.class, id++).encoder(UpdateShieldPacket::encode).decoder(UpdateShieldPacket::new).consumerMainThread(UpdateShieldPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(UncraftingGuiPacket.class, id++).encoder(UncraftingGuiPacket::encode).decoder(UncraftingGuiPacket::new).consumerMainThread(UncraftingGuiPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(UpdateTFMultipartPacket.class, id++).encoder(UpdateTFMultipartPacket::encode).decoder(UpdateTFMultipartPacket::new).consumerMainThread(UpdateTFMultipartPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(SpawnFallenLeafFromPacket.class, id++).encoder(SpawnFallenLeafFromPacket::encode).decoder(SpawnFallenLeafFromPacket::new).consumerMainThread(SpawnFallenLeafFromPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(MissingAdvancementToastPacket.class, id++).encoder(MissingAdvancementToastPacket::encode).decoder(MissingAdvancementToastPacket::new).consumerMainThread(MissingAdvancementToastPacket::handle).add();
		CHANNEL.messageBuilder(ParticlePacket.class, id++).encoder(ParticlePacket::encode).decoder(ParticlePacket::new).consumerMainThread(ParticlePacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(UpdateFeatherFanFallPacket.class, id++).encoder(UpdateFeatherFanFallPacket::encode).decoder(UpdateFeatherFanFallPacket::new).consumerMainThread(UpdateFeatherFanFallPacket.Handler::onMessage).add();
		CHANNEL.messageBuilder(UpdateThrownPacket.class, id++).encoder(UpdateThrownPacket::encode).decoder(UpdateThrownPacket::new).consumerMainThread(UpdateThrownPacket.Handler::onMessage).add();
	}
}
