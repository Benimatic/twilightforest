package twilightforest.structures.start;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.biomes.TFBiomes;
import twilightforest.structures.StructureTFComponentTemplate;
import twilightforest.world.TFBiomeProvider;

import java.util.Random;

public abstract class StructureStartTFAbstract extends StructureStart {
    private static int NUM_LOCKS = 4;
    public boolean isConquered;
    public byte[] lockBytes = new byte[NUM_LOCKS];

    public StructureStartTFAbstract() {
    }

    public StructureStartTFAbstract(World world, Random rand, int chunkX, int chunkZ) {
        setupComponents(world);
    }

    /**
     * Move the whole structure up or down
     */
    protected void moveToAvgGroundLevel(World world, int x, int z) {
        if (world.getBiomeProvider() instanceof TFBiomeProvider) {
            // determine the biome at the origin
            Biome biomeAt = world.getBiome(new BlockPos(x, 0, z));

            int offY = (int) ((biomeAt.getBaseHeight() + biomeAt.getHeightVariation()) * 8);

            // dark forest doesn't seem to get the right value. Why is my calculation so bad?
            if (biomeAt == TFBiomes.darkForest) {
                offY += 4;
            }

            if (offY > 0) {
                boundingBox.offset(0, offY, 0);

                for (StructureComponent com : getComponents()) {
                    com.getBoundingBox().offset(0, offY, 0);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setBoolean("Conquered", this.isConquered);
        par1NBTTagCompound.setByteArray("Locks", this.lockBytes);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.isConquered = nbttagcompound.getBoolean("Conquered");
        this.lockBytes = nbttagcompound.getByteArray("Locks");
    }

    protected void setupComponents(World world) {
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        MinecraftServer server = world.getMinecraftServer();

        for (StructureComponent component : components)
            if (component instanceof StructureTFComponentTemplate)
                ((StructureTFComponentTemplate) component).setup(templateManager, server);
    }
}
