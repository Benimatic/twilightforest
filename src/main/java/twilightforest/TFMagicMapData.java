package twilightforest;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

import net.minecraft.world.level.saveddata.maps.MapDecoration.Type;

public class TFMagicMapData extends MapItemSavedData {
	private static final Map<Level, Map<String, TFMagicMapData>> CLIENT_DATA = new WeakHashMap<>();

	public final Set<TFMapDecoration> tfDecorations = new HashSet<>();

	public TFMagicMapData(String name) {
		super(name);
	}

	@Override
	public void load(CompoundTag cmp) {
		super.load(cmp);

		byte[] featureStorage = cmp.getByteArray("features");
		if (featureStorage.length > 0) {
			this.deserializeFeatures(featureStorage);
		}
	}

	@Override
	public CompoundTag save(CompoundTag cmp) {
		cmp = super.save(cmp);

		if (this.tfDecorations.size() > 0) {
			cmp.putByteArray("features", serializeFeatures());
		}

		return cmp;
	}

	/**
	 * Checks existing features against the feature cache changes wrong ones
	 */
	public void checkExistingFeatures(Level world) {
		List<TFMapDecoration> toRemove = new ArrayList<>();
		List<TFMapDecoration> toAdd = new ArrayList<>();

		for (TFMapDecoration coord : tfDecorations) {
			int worldX = (coord.getX() << this.scale - 1) + this.x;
			int worldZ = (coord.getY() << this.scale - 1) + this.z;

			int trueId = TFFeature.getFeatureID(worldX, worldZ, (ServerLevel) world);
			if (coord.featureId != trueId) {
				toRemove.add(coord);
				toAdd.add(new TFMapDecoration(trueId, coord.getX(), coord.getY(), coord.getRot()));
			}
		}

		toRemove.forEach(tfDecorations::remove);
		tfDecorations.addAll(toAdd);
	}

	public void deserializeFeatures(byte[] arr) {
		this.tfDecorations.clear();

		for (int i = 0; i < arr.length / 3; ++i) {
			byte featureId = arr[i * 3];
			byte mapX = arr[i * 3 + 1];
			byte mapZ = arr[i * 3 + 2];
			byte mapRotation = 8;
			this.tfDecorations.add(new TFMapDecoration(featureId, mapX, mapZ, mapRotation));
		}
	}

	public byte[] serializeFeatures() {
		byte[] storage = new byte[this.tfDecorations.size() * 3];

		int i = 0;
		for (TFMapDecoration featureCoord : tfDecorations) {
			storage[i * 3] = (byte) featureCoord.featureId;
			storage[i * 3 + 1] = featureCoord.getX();
			storage[i * 3 + 2] = featureCoord.getY();
			i++;
		}

		return storage;
	}

	// VanillaCopy of super, but adjust origin
	@Override
	public void setOrigin(double x, double z, int mapScale) {
		// magic maps are offset by 1024 from normal maps so that 0,0 is in the middle of the map containing those coords
		int mapSize = 128 * (1 << mapScale);
		int roundX = (int) Math.round(x / mapSize);
		int roundZ = (int) Math.round(z / mapSize);
		this.x = roundX * mapSize;
		this.z = roundZ * mapSize;
	}

	// [VanillaCopy] Adapted from World.getMapData
	@Nullable
	public static TFMagicMapData getMagicMapData(Level world, String name) {
		if (world.isClientSide) {
			return CLIENT_DATA.getOrDefault(world, Collections.emptyMap()).get(name);
		} else {
			return world.getServer().getLevel(Level.OVERWORLD).getDataStorage().get(() -> new TFMagicMapData(name), name);
		}
	}

	// [VanillaCopy] Adapted from World.registerMapData
	public static void registerMagicMapData(Level world, TFMagicMapData data) {
		if (world.isClientSide) {
			CLIENT_DATA.computeIfAbsent(world, k -> new HashMap<>()).put(data.getId(), data);
		} else {
			world.getServer().getLevel(Level.OVERWORLD).getDataStorage().set(data);
		}
	}

	public static class TFMapDecoration extends MapDecoration {


		@OnlyIn(Dist.CLIENT)
		public static class RenderContext {
			private static final RenderType MAP_ICONS = RenderType.text(TwilightForestMod.prefix("textures/gui/mapicons.png"));
			public static PoseStack stack;
			public static MultiBufferSource buffer;
			public static int light;
		}

		final int featureId;

		public TFMapDecoration(int featureId, byte xIn, byte yIn, byte rotationIn) {
			super(Type.TARGET_X, xIn, yIn, rotationIn, new TranslatableComponent("map.magic.text")); //TODO: Shush for now
			this.featureId = featureId;
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean render(int idx) {
			// TODO: Forge needs to pass in the ms and buffers, but for now this works
			if (TFFeature.getFeatureByID(featureId).isStructureEnabled) {
				RenderContext.stack.pushPose();
				RenderContext.stack.translate(0.0F + getX() / 2.0F + 64.0F, 0.0F + getY() / 2.0F + 64.0F, -0.02F);
				RenderContext.stack.mulPose(Vector3f.ZP.rotationDegrees(getRot() * 360 / 16.0F));
				RenderContext.stack.scale(4.0F, 4.0F, 3.0F);
				RenderContext.stack.translate(-0.125D, 0.125D, 0.0D);
				float f1 = featureId % 8 / 8.0F;
				float f2 = featureId / 8 / 8.0F;
				float f3 = (featureId % 8 + 1) / 8.0F;
				float f4 = (featureId / 8 + 1) / 8.0F;
				Matrix4f matrix4f1 = RenderContext.stack.last().pose();
				VertexConsumer ivertexbuilder1 = RenderContext.buffer.getBuffer(RenderContext.MAP_ICONS);
				ivertexbuilder1.vertex(matrix4f1, -1.0F, 1.0F, idx * -0.001F).color(255, 255, 255, 255).uv(f1, f2).uv2(RenderContext.light).endVertex();
				ivertexbuilder1.vertex(matrix4f1, 1.0F, 1.0F, idx * -0.001F).color(255, 255, 255, 255).uv(f3, f2).uv2(RenderContext.light).endVertex();
				ivertexbuilder1.vertex(matrix4f1, 1.0F, -1.0F, idx * -0.001F).color(255, 255, 255, 255).uv(f3, f4).uv2(RenderContext.light).endVertex();
				ivertexbuilder1.vertex(matrix4f1, -1.0F, -1.0F, idx * -0.001F).color(255, 255, 255, 255).uv(f1, f4).uv2(RenderContext.light).endVertex();
				RenderContext.stack.popPose();
			}
			return true;
		}

		@Override
		public boolean equals(Object o) {
			if (super.equals(o) && o instanceof TFMapDecoration) {
				TFMapDecoration other = (TFMapDecoration) o;
				return this.featureId == other.featureId;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return super.hashCode() * 31 + featureId;
		}
	}
}
