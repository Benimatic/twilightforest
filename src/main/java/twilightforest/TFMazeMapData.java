package twilightforest;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import twilightforest.world.registration.TFGenerationSettings;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class TFMazeMapData extends MapItemSavedData {
	private static final Map<Level, Map<String, TFMazeMapData>> CLIENT_DATA = new WeakHashMap<>();

	public int yCenter;

	public TFMazeMapData(int x, int z, byte scale, boolean trackpos, boolean unlimited, boolean locked, ResourceKey<Level> dim) {
		super(x, z, scale, trackpos, unlimited, locked, dim);
	}

	//TODO: Evaluate this
	public static TFMazeMapData load(CompoundTag nbt) {
		MapItemSavedData data = MapItemSavedData.load(nbt);
		TFMazeMapData tfdata = (TFMazeMapData) data;
		tfdata.yCenter = nbt.getInt("yCenter");
		return tfdata;
	}

	@Override
	public CompoundTag save(CompoundTag nbt) {
		CompoundTag ret = super.save(nbt);
		ret.putInt("yCenter", this.yCenter);
		return ret;
	}

	public void calculateMapCenter(Level world, int x, int y, int z, int mapScale) {
		// FIXME super.setOrigin(x, z, mapScale);
		this.yCenter = y;

		// when we are in a labyrinth, snap to the LABYRINTH
		if (world instanceof ServerLevel && TFGenerationSettings.isTwilightChunk((ServerLevel) world)) {
			if (TFFeature.getFeatureForRegion(x >> 4, z >> 4, (ServerLevel) world) == TFFeature.LABYRINTH) {
				BlockPos mc = TFFeature.getNearestCenterXYZ(x >> 4, z >> 4);
				this.x = mc.getX();
				this.z = mc.getZ();
			}
		}
	}

	// [VanillaCopy] Adapted from World.getMapData
	@Nullable
	public static TFMazeMapData getMazeMapData(Level world, String name) {
		if (world.isClientSide) {
			return CLIENT_DATA.getOrDefault(world, Collections.emptyMap()).get(name);
		} else {
			return world.getServer().overworld().getDataStorage().get(TFMazeMapData::load, name);
		}
	}

	// [VanillaCopy] Adapted from World.registerMapData
	public static void registerMazeMapData(Level world, TFMazeMapData data) {
		if (world.isClientSide) {
			// FIXME CLIENT_DATA.computeIfAbsent(world, k -> new HashMap<>()).put(data.getId(), data);
		} else {
			world.getServer().overworld().getDataStorage().set(MapItem.makeKey(world.getFreeMapId()), data);
		}
	}
}
