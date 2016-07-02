package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.stats.Achievement;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import twilightforest.TFAchievementPage;
import twilightforest.block.TFBlocks;


public class TFBiomeFinalPlateau extends TFBiomeBase {

	public TFBiomeFinalPlateau(BiomeProperties props) {
		super(props);
		
        this.topBlock = TFBlocks.deadrock;
        this.field_150604_aj = 0;
        this.fillerBlock = TFBlocks.deadrock;
        this.field_76754_C = 1;
        
        getTFBiomeDecorator().canopyPerChunk = -999;
		getTFBiomeDecorator().setTreesPerChunk(-999);
		
		this.theBiomeDecorator.generateLakes = false;
		
        // custom creature list.
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFRaven.class, 10, 4, 4));
	}

	@Override
	public Block getStoneReplacementBlock() {
		return TFBlocks.deadrock;
	}
	
    @Override
	public byte getStoneReplacementMeta() {
		return 2;
	}

	@Override
	protected Achievement getRequiredAchievement() {
		return TFAchievementPage.twilightProgressGlacier;
	}
}
