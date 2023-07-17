package twilightforest.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.entity.monster.Kobold;
import twilightforest.init.TFLandmark;
import twilightforest.network.AreaProtectionPacket;
import twilightforest.network.EnforceProgressionStatusPacket;
import twilightforest.network.TFPacketHandler;
import twilightforest.util.LandmarkUtil;
import twilightforest.util.LegacyLandmarkPlacements;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.world.components.structures.util.ProgressionStructure;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to store events relating to progression
 */
@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class ProgressionEvents {

	/**
	 * Check if the player is trying to break a block in a structure that's considered unbreakable for progression reasons
	 */
	@SubscribeEvent
	public static void breakBlock(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		BlockPos pos = event.getPos();

		if (!(event.getLevel() instanceof Level level) || level.isClientSide()) return;

		if (isBlockProtectedFromBreaking(level, pos) && isAreaProtected(level, player, pos)) {
			event.setCanceled(true);

		}
	}

	/**
	 * Stop the player from interacting with blocks that could produce treasure or open doors in a protected area
	 */
	@SubscribeEvent
	public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {

		Player player = event.getEntity();
		Level level = player.getLevel();

		if (!level.isClientSide() && isBlockProtectedFromInteraction(level, event.getPos()) && isAreaProtected(level, player, event.getPos())) {
			event.setUseBlock(Event.Result.DENY);
		}
	}

	private static boolean isBlockProtectedFromInteraction(Level level, BlockPos pos) {
		return level.getBlockState(pos).is(BlockTagGenerator.STRUCTURE_BANNED_INTERACTIONS);
	}

	private static boolean isBlockProtectedFromBreaking(Level level, BlockPos pos) {
		return !level.getBlockState(pos).is(BlockTagGenerator.PROGRESSION_ALLOW_BREAKING);
	}

	/**
	 * Return if the area at the coordinates is considered protected for that player.
	 * Currently, if we return true, we also send the area protection packet here.
	 */
	private static boolean isAreaProtected(Level level, Player player, BlockPos pos) {

		if (player.getAbilities().instabuild || player.isSpectator() ||
				!LandmarkUtil.isProgressionEnforced(level) || player instanceof FakePlayer) {
			return false;
		}

		ChunkGeneratorTwilight chunkGenerator = WorldUtil.getChunkGenerator(level);

		if (chunkGenerator != null) {
			Optional<StructureStart> struct = LandmarkUtil.locateNearestLandmarkStart(level, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
			if (struct.isPresent()) {
				StructureStart structureStart = struct.get();
				if (structureStart.getBoundingBox().isInside(pos) && structureStart.getStructure() instanceof ProgressionStructure structureHints) {
					if (!structureHints.doesPlayerHaveRequiredAdvancements(player)/* && chunkGenerator.isBlockProtected(pos)*/) {
						// what feature is nearby?  is it one the player has not unlocked?
						TFLandmark nearbyFeature = LegacyLandmarkPlacements.pickLandmarkAtBlock(pos.getX(), pos.getZ(), (ServerLevel) level);

						// TODO: This is terrible but *works* for now.. proper solution is to figure out why the stronghold bounding box is going so high
						if (nearbyFeature == TFLandmark.KNIGHT_STRONGHOLD && pos.getY() >= TFGenerationSettings.SEALEVEL)
							return false;

						// send protection packet
						List<BoundingBox> boxes = new ArrayList<>();
						structureStart.getPieces().forEach(piece -> {
							if (piece.getBoundingBox().isInside(pos))
								boxes.add(piece.getBoundingBox());
						});

						sendAreaProtectionPacket(level, pos, boxes);

						// send a hint monster?
						structureHints.trySpawnHintMonster(level, player, pos);

						return true;
					}
				}
			}
		}
		return false;
	}

	private static void sendAreaProtectionPacket(Level level, BlockPos pos, List<BoundingBox> sbb) {
		PacketDistributor.TargetPoint targetPoint = new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 64, level.dimension());
		TFPacketHandler.CHANNEL.send(PacketDistributor.NEAR.with(() -> targetPoint), new AreaProtectionPacket(sbb, pos));
	}

	@SubscribeEvent
	public static void livingAttack(LivingAttackEvent event) {
		LivingEntity living = event.getEntity();
		// cancel attacks in protected areas
		if (!living.getLevel().isClientSide() && living instanceof Enemy && event.getSource().getEntity() instanceof Player && !(living instanceof Kobold)
				&& isAreaProtected(living.getLevel(), (Player) event.getSource().getEntity(), new BlockPos(living.blockPosition()))) {

			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void playerPortals(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
			if (TFGenerationSettings.usesTwilightChunkGenerator(player.getLevel())) {
				sendEnforcedProgressionStatus(player, LandmarkUtil.isProgressionEnforced(player.getLevel()));
			}
		}
	}

	@SubscribeEvent
	public static void playerLogsIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
			sendEnforcedProgressionStatus(player, LandmarkUtil.isProgressionEnforced(event.getEntity().getLevel()));
		}
	}

	private static void sendEnforcedProgressionStatus(ServerPlayer player, boolean isEnforced) {
		TFPacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new EnforceProgressionStatusPacket(isEnforced));
	}
}
