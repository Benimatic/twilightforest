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

	@SuppressWarnings("UnusedAssignment")
	public static void init() {
		int id = 0;
		CHANNEL.registerMessage(id++, AreaProtectionPacket.class, AreaProtectionPacket::encode, AreaProtectionPacket::new, AreaProtectionPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, CreateMovingCicadaSoundPacket.class, CreateMovingCicadaSoundPacket::encode, CreateMovingCicadaSoundPacket::new, CreateMovingCicadaSoundPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, EnforceProgressionStatusPacket.class, EnforceProgressionStatusPacket::encode, EnforceProgressionStatusPacket::new, EnforceProgressionStatusPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, MagicMapPacket.class, MagicMapPacket::encode, MagicMapPacket::new, MagicMapPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, MazeMapPacket.class, MazeMapPacket::encode, MazeMapPacket::new, MazeMapPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, MissingAdvancementToastPacket.class, MissingAdvancementToastPacket::encode, MissingAdvancementToastPacket::new, MissingAdvancementToastPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, ParticlePacket.class, ParticlePacket::encode, ParticlePacket::new, ParticlePacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, SpawnFallenLeafFromPacket.class, SpawnFallenLeafFromPacket::encode, SpawnFallenLeafFromPacket::new, SpawnFallenLeafFromPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, StructureProtectionClearPacket.class, StructureProtectionClearPacket::encode, StructureProtectionClearPacket::new, StructureProtectionClearPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, StructureProtectionPacket.class, StructureProtectionPacket::encode, StructureProtectionPacket::new, StructureProtectionPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, SyncUncraftingTableConfigPacket.class, SyncUncraftingTableConfigPacket::encode, SyncUncraftingTableConfigPacket::new, SyncUncraftingTableConfigPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, ThrowPlayerPacket.class, ThrowPlayerPacket::encode, ThrowPlayerPacket::new, ThrowPlayerPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, UncraftingGuiPacket.class, UncraftingGuiPacket::encode, UncraftingGuiPacket::new, UncraftingGuiPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, UpdateFeatherFanFallPacket.class, UpdateFeatherFanFallPacket::encode, UpdateFeatherFanFallPacket::new, UpdateFeatherFanFallPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, UpdateShieldPacket.class, UpdateShieldPacket::encode, UpdateShieldPacket::new, UpdateShieldPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, UpdateTFMultipartPacket.class, UpdateTFMultipartPacket::encode, UpdateTFMultipartPacket::new, UpdateTFMultipartPacket.Handler::onMessage);
		CHANNEL.registerMessage(id++, UpdateThrownPacket.class, UpdateThrownPacket::encode, UpdateThrownPacket::new, UpdateThrownPacket.Handler::onMessage);
	}
}
