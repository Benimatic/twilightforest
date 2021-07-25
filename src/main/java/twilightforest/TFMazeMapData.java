package twilightforest;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import twilightforest.world.TFGenerationSettings;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class TFMazeMapData extends MapItemSavedData {
	private static final Map<Level, Map<String, TFMazeMapData>> CLIENT_DATA = new WeakHashMap<>();

	public int yCenter;

	public TFMazeMapData(String name) {
		super(name);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.yCenter = nbt.getInt("yCenter");
	}

	@Override
	public CompoundTag save(CompoundTag nbt) {
		CompoundTag ret = super.save(nbt);
		ret.putInt("yCenter", this.yCenter);
		return ret;
	}

	public void calculateMapCenter(Level world, int x, int y, int z, int mapScale) {
		super.setOrigin(x, z, mapScale);
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
			return world.getServer().getLevel(Level.OVERWORLD).getDataStorage().get(() -> new TFMazeMapData(name), name);
		}
	}

	// [VanillaCopy] Adapted from World.registerMapData
	public static void registerMazeMapData(Level world, TFMazeMapData data) {
		if (world.isClientSide) {
			CLIENT_DATA.computeIfAbsent(world, k -> new HashMap<>()).put(data.getId(), data);
		} else {
			world.getServer().getLevel(Level.OVERWORLD).getDataStorage().set(data);
		}
	}
}
