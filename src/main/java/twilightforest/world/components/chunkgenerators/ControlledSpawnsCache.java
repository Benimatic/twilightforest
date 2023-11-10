package twilightforest.world.components.chunkgenerators;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.event.AddReloadListenerEvent;

import java.util.List;
import java.util.WeakHashMap;

public class ControlledSpawnsCache extends SimplePreparableReloadListener<Object> {

	public static WeakHashMap<ChunkGeneratorTwilight, List<Structure>> CONTROLLED_SPAWNS = new WeakHashMap<>();

	public static void reload(AddReloadListenerEvent event) {
		event.addListener(new ControlledSpawnsCache());
	}

	@Override
	protected Object prepare(ResourceManager manager, ProfilerFiller filler) {
		return 0;
	}

	@Override
	protected void apply(Object obj, ResourceManager manager, ProfilerFiller filler) {
		CONTROLLED_SPAWNS.clear();
	}
}
