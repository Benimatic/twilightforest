package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.stats.Achievement;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import twilightforest.TFAchievementPage;
import twilightforest.block.TFBlocks;


public class TFBiomeFinalPlateau extends TFBiomeBase {

	public TFBiomeFinalPlateau(int i) {
		super(i);
		
        this.topBlock = TFBlocks.deadrock;
        this.field_150604_aj = 0;
        this.fillerBlock = TFBlocks.deadrock;
        this.field_76754_C = 1;
		
//		this.rootHeight = 7F;
//		this.heightVariation = 0.4F;
        
        this.temperature = 0.3F;
        this.rainfall = 0.2F;
        
        getTFBiomeDecorator().canopyPerChunk = -999;
		getTFBiomeDecorator().setTreesPerChunk(-999);
		
		this.theBiomeDecorator.generateLakes = false;
		
        // custom creature list.
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFRaven.class, 10, 4, 4));
	}
	
    /**
     * Return a block if you want it to replace stone in the terrain generation
     */
	public Block getStoneReplacementBlock() {
		return TFBlocks.deadrock;
	}
	
    /**
     * Metadata for the stone replacement block
     */
	public byte getStoneReplacementMeta() {
		return 2;
	}

	protected Achievement getRequiredAchievement() {
		return TFAchievementPage.twilightProgressGlacier;
	}
}
