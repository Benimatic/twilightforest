package twilightforest.structures.start;

import net.minecraft.world.World;
import twilightforest.TwilightForestMod;

import java.util.Random;

public class StructureStartNothing extends StructureStartTFAbstract {
    public StructureStartNothing() {
        super();
    }

    public StructureStartNothing(World world, Random rand, int chunkX, int chunkZ) {
        super(world, rand, chunkX, chunkZ);

        TwilightForestMod.LOGGER.warn("Generated nothing at chunk " + chunkX + ", " + chunkZ + "!");
    }
}
