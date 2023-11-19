package twilightforest;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import twilightforest.init.TFLandmark;
import twilightforest.network.MagicMapPacket;
import twilightforest.network.TFPacketHandler;
import twilightforest.util.LandmarkUtil;
import twilightforest.util.LegacyLandmarkPlacements;

import java.util.*;

public class TFMagicMapData extends MapItemSavedData {
	private static final Map<String, TFMagicMapData> CLIENT_DATA = new HashMap<>();

	public final Int2ObjectMap<TFMapDecoration> tfDecorations = new Int2ObjectLinkedOpenHashMap<>();

	public TFMagicMapData(int x, int z, byte scale, boolean trackpos, boolean unlimited, boolean locked, ResourceKey<Level> dim) {
		super(x, z, scale, trackpos, unlimited, locked, dim);
	}

	public static TFMagicMapData load(CompoundTag nbt) {
		MapItemSavedData data = MapItemSavedData.load(nbt);
		final boolean trackingPosition = !nbt.contains("trackingPosition", 1) || nbt.getBoolean("trackingPosition");
		final boolean unlimitedTracking = nbt.getBoolean("unlimitedTracking");
		final boolean locked = nbt.getBoolean("locked");
		TFMagicMapData tfdata = new TFMagicMapData(data.centerX, data.centerZ, data.scale, trackingPosition, unlimitedTracking, locked, data.dimension);

		tfdata.colors = data.colors;
		tfdata.bannerMarkers.putAll(data.bannerMarkers);
		tfdata.decorations.putAll(data.decorations);
		tfdata.frameMarkers.putAll(data.frameMarkers);
		tfdata.trackedDecorationCount = data.trackedDecorationCount;

		byte[] featureStorage = nbt.getByteArray("features");
		if (featureStorage.length > 0) {
			tfdata.deserializeFeatures(featureStorage);
		}

		return tfdata;
	}

	@Override
	public CompoundTag save(CompoundTag cmp) {
		cmp = super.save(cmp);

		if (!this.tfDecorations.isEmpty()) {
			cmp.putByteArray("features", serializeFeatures());
		}

		return cmp;
	}

	/**
	 * Checks existing features against the feature cache changes wrong ones
	 */
	public void checkExistingFeatures(Level world) {
        IntArrayList toRemove = new IntArrayList();
        Int2ObjectLinkedOpenHashMap<TFMapDecoration> toAdd = new Int2ObjectLinkedOpenHashMap<>();

		for (var entry : tfDecorations.int2ObjectEntrySet()) {
			TFMapDecoration coord = entry.getValue();
			int worldX = (coord.x << this.scale - 1) + this.centerX;
			int worldZ = (coord.y << this.scale - 1) + this.centerZ;

			int trueId = TFMapDecoration.ICONS_FLIPPED.getInt(LegacyLandmarkPlacements.pickLandmarkAtBlock(worldX, worldZ, (ServerLevel) world));
			if (coord.featureId != trueId) {
				toRemove.add(entry.getIntKey());
				toAdd.put(entry.getIntKey(), new TFMapDecoration(trueId, coord.x, coord.y, coord.rot, LandmarkUtil.isConquered(world, worldX, worldZ)));
			}
		}

		for (int packedCoords : toRemove)
			tfDecorations.remove(packedCoords);
		tfDecorations.putAll(toAdd);
	}

	public void deserializeFeatures(byte[] arr) {
		this.tfDecorations.clear();

		for (int i = 0; i < arr.length / 3; ++i) {
			byte featureInfo = arr[i * 3];
			byte mapX = arr[i * 3 + 1];
			byte mapZ = arr[i * 3 + 2];
			byte mapRotation = 8;
			this.tfDecorations.put(packCoordBytes(mapX, mapZ), new TFMapDecoration(featureInfo & 0b111_1111, mapX, mapZ, mapRotation, (featureInfo & 0b1000_0000) != 0));
		}
	}

	public byte[] serializeFeatures() {
		byte[] storage = new byte[this.tfDecorations.size() * 3];

		int i = 0;
		for (TFMapDecoration featureCoord : tfDecorations.values()) {
			byte featureInfo = (featureCoord.conquered ? (byte) (featureCoord.featureId | 0b1000_0000) : (byte) featureCoord.featureId);
			storage[i * 3] = featureInfo;
			storage[i * 3 + 1] = featureCoord.x;
			storage[i * 3 + 2] = featureCoord.y;
			i++;
		}

		return storage;
	}

	// [VanillaCopy] Adapted from World.getMapData
	@Nullable
	public static TFMagicMapData getMagicMapData(Level world, String name) {
		if (world.isClientSide) return CLIENT_DATA.get(name);
		else return ((ServerLevel)world).getServer().overworld().getDataStorage().get(TFMagicMapData.factory(), name);
	}

	// [VanillaCopy] Adapted from World.registerMapData
	public static void registerMagicMapData(Level world, TFMagicMapData data, String id) {
		if (world.isClientSide) CLIENT_DATA.put(id, data);
		else ((ServerLevel)world).getServer().overworld().getDataStorage().set(id, data);
	}

	public static SavedData.Factory<MapItemSavedData> factory() {
		return new SavedData.Factory<>(() -> {
			throw new IllegalStateException("Should never create an empty map saved data");
		}, TFMagicMapData::load, DataFixTypes.SAVED_DATA_MAP_DATA);
	}

	@Nullable
	@Override
	public Packet<?> getUpdatePacket(int mapId, Player player) {
		Packet<?> packet = super.getUpdatePacket(mapId, player);
		return packet instanceof ClientboundMapItemDataPacket mapItemDataPacket ? TFPacketHandler.CHANNEL.toVanillaPacket(new MagicMapPacket(this, mapItemDataPacket), PlayNetworkDirection.PLAY_TO_CLIENT) : packet;
	}

	public void putMapData(TFMapDecoration info) {
		this.tfDecorations.put(packCoordBytes(info.x, info.y), info);
	}

	private static int packCoordBytes(byte x, byte y) {
		return (x & 0xFF) | ((y & 0xFF) << 8);
	}

	public static class TFMapDecoration {
		
		private static final RenderType MAP_ICONS = RenderType.text(TwilightForestMod.prefix("textures/gui/mapicons.png"));
		private static final RenderType VANILLA_ICONS = RenderType.text(new ResourceLocation("textures/map/map_icons.png"));

		private static final Int2ObjectArrayMap<TFLandmark> ICONS = new Int2ObjectArrayMap<>(){{
			defaultReturnValue(TFLandmark.NOTHING);
			put(0, TFLandmark.NOTHING);
			put(1, TFLandmark.SMALL_HILL);
			put(2, TFLandmark.MEDIUM_HILL);
			put(3, TFLandmark.LARGE_HILL);
			put(4, TFLandmark.HEDGE_MAZE);
			put(5, TFLandmark.NAGA_COURTYARD);
			put(6, TFLandmark.LICH_TOWER);
			put(7, TFLandmark.ICE_TOWER);
			put(9, TFLandmark.QUEST_GROVE);
			put(12, TFLandmark.HYDRA_LAIR);
			put(13, TFLandmark.LABYRINTH);
			put(14, TFLandmark.DARK_TOWER);
			put(15, TFLandmark.KNIGHT_STRONGHOLD);
			put(17, TFLandmark.YETI_CAVE);
			put(18, TFLandmark.TROLL_CAVE);
			put(19, TFLandmark.FINAL_CASTLE);
		}};
		private static final Object2IntArrayMap<TFLandmark> ICONS_FLIPPED = new Object2IntArrayMap<>(){{
			ICONS.forEach((k, v) -> put(v, k.intValue()));
		}};

		final int featureId;
		final byte x;
		final byte y;
		final byte rot;
		final boolean conquered;

		public TFMapDecoration(TFLandmark featureId, byte xIn, byte yIn, byte rotationIn, boolean conquered) {
			this(ICONS_FLIPPED.getInt(featureId), xIn, yIn, rotationIn, conquered);
		}

		public TFMapDecoration(int featureId, byte xIn, byte yIn, byte rotationIn, boolean conquered) {
			this.featureId = featureId;
			this.x = xIn;
			this.y = yIn;
			this.rot = rotationIn;
			this.conquered = conquered;
		}

		@OnlyIn(Dist.CLIENT)
		public boolean render(int idx, PoseStack stack, MultiBufferSource buffer, int light) {
			if (ICONS.get(featureId).isStructureEnabled) {
				stack.pushPose();
				stack.translate(0.0F + this.x / 2.0F + 64.0F, 0.0F + this.y / 2.0F + 64.0F, -0.02F);
				stack.mulPose(Axis.ZP.rotationDegrees(this.rot * 360 / 16.0F));
				stack.scale(4.0F, 4.0F, 3.0F);
				stack.translate(-0.125D, 0.125D, 0.0D);
				float uMin = featureId % 8.0F / 8.0F;
				float vMin = featureId / 8 / 8.0F;
				float uMax = (featureId % 8 + 1) / 8.0F;
				float vMax = (featureId / 8 + 1) / 8.0F;
				Matrix4f matrix4f = stack.last().pose();
				float depth = idx * -0.004F;
				VertexConsumer mapIconVertices = buffer.getBuffer(MAP_ICONS);
				mapIconVertices.vertex(matrix4f, -1.0F, 1.0F, depth).color(255, 255, 255, 255).uv(uMin, vMin).uv2(light).endVertex();
				mapIconVertices.vertex(matrix4f, 1.0F, 1.0F, depth).color(255, 255, 255, 255).uv(uMax, vMin).uv2(light).endVertex();
				mapIconVertices.vertex(matrix4f, 1.0F, -1.0F, depth).color(255, 255, 255, 255).uv(uMax, vMax).uv2(light).endVertex();
				mapIconVertices.vertex(matrix4f, -1.0F, -1.0F, depth).color(255, 255, 255, 255).uv(uMin, vMax).uv2(light).endVertex();

				if (this.conquered) {
					uMin = 10f/16f;
					vMin = 1f/16f;
					uMax = 11f/16f;
					vMax = 2f/16f;
					depth -= 0.002f;
					VertexConsumer vanillaIconVertices = buffer.getBuffer(VANILLA_ICONS);
					vanillaIconVertices.vertex(matrix4f, -1, 0, depth).color(255, 255, 255, 255).uv(uMin, vMin).uv2(light).endVertex();
					vanillaIconVertices.vertex(matrix4f, 0, 0, depth).color(255, 255, 255, 255).uv(uMax, vMin).uv2(light).endVertex();
					vanillaIconVertices.vertex(matrix4f, 0, -1, depth).color(255, 255, 255, 255).uv(uMax, vMax).uv2(light).endVertex();
					vanillaIconVertices.vertex(matrix4f, -1, -1, depth).color(255, 255, 255, 255).uv(uMin, vMax).uv2(light).endVertex();
				}
				stack.popPose();
			}
			return true;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			TFMapDecoration that = (TFMapDecoration) o;
			return featureId == that.featureId && x == that.x && y == that.y && rot == that.rot && conquered == that.conquered;
		}

		@Override
		public int hashCode() {
			return Objects.hash(featureId, x, y, rot, conquered);
		}
	}
}
