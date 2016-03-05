package twilightforest.world;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.structures.hollowtree.StructureTFHollowTreeStart;


public class MapGenTFHollowTree extends MapGenBase {
	
    //public static final int SPAWN_CHANCE = 48;
    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structure generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Map<Long, StructureStart> structureMap = new HashMap<Long, StructureStart>();
    
    /**
     * Generate recursively
     */
    protected void func_151538_a(World world, final int chunkX, final int chunkZ, int centerX, int centerZ, Block[] blockData) {
        if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)))){
            this.rand.nextInt();

            try {
                if (this.canSpawnStructureAtCoords(chunkX, chunkZ)){
                    StructureStart structurestart = this.getStructureStart(chunkX, chunkZ);
                    this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)), structurestart);
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing hollow tree");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
                crashreportcategory.addCrashSectionCallable("Is feature chunk", new Callable() {
                    private static final String __OBFID = "CL_00000506";
                    public String call() {
                        return MapGenTFHollowTree.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
                    }
                });
                crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] {Integer.valueOf(chunkX), Integer.valueOf(chunkZ)}));
                crashreportcategory.addCrashSectionCallable("Chunk pos hash", new Callable() {
                    private static final String __OBFID = "CL_00000507";
                    public String call() {
                        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
                    }
                });
                crashreportcategory.addCrashSectionCallable("Structure type", new Callable() {
                    private static final String __OBFID = "CL_00000508";
                    public String call() {
                        return MapGenTFHollowTree.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }

    	
    	
    }

    
	public boolean generateStructuresInChunk(World world, Random rand, int chunkX, int chunkZ) {
        int mapX = (chunkX << 4) + 8;
        int mapZ = (chunkZ << 4) + 8;
        boolean flag = false;

        Iterator<StructureStart> iterator = this.structureMap.values().iterator();

        while (iterator.hasNext())
        {
            StructureStart structurestart = iterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(mapX, mapZ, mapX + 15, mapZ + 15))
            {
                structurestart.generateStructure(world, rand, new StructureBoundingBox(mapX, mapZ, mapX + 15, mapZ + 15));
                flag = true;
            }
        }

        return flag;
	}

	/** A list of all the biomes twilight oaks can spawn in. */
    public static List<BiomeGenBase> oakSpawnBiomes = Arrays.asList(new BiomeGenBase[] {TFBiomeBase.twilightForest, TFBiomeBase.twilightForest2, 
    		TFBiomeBase.mushrooms, TFBiomeBase.tfSwamp, TFBiomeBase.clearing, TFBiomeBase.oakSavanna, 
    		TFBiomeBase.fireflyForest, TFBiomeBase.deepMushrooms, TFBiomeBase.enchantedForest, TFBiomeBase.fireSwamp});

	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return rand.nextInt(TwilightForestMod.twilightOakChance) == 0 && TFFeature.getNearestFeature(chunkX, chunkZ, worldObj).areChunkDecorationsEnabled 
				&&  this.worldObj.getWorldChunkManager().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 0, oakSpawnBiomes);
	}

	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new StructureTFHollowTreeStart(worldObj, rand, chunkX, chunkZ);
	}
	
}
