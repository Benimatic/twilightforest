package twilightforest.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;
import twilightforest.data.custom.CrumbleHornGenerator;
import twilightforest.data.custom.TransformationPowderGenerator;
import twilightforest.data.custom.UncraftingRecipeGenerator;
import twilightforest.data.custom.stalactites.StalactiteGenerator;
import twilightforest.data.tags.*;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent evt) {
		DataGenerator generator = evt.getGenerator();
		PackOutput output = evt.getGenerator().getPackOutput();
		ExistingFileHelper helper = evt.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> provider = evt.getLookupProvider();

		generator.addProvider(true, new AdvancementGenerator(output, provider, helper));
		generator.addProvider(true, new BlockstateGenerator(generator, helper));
		generator.addProvider(true, new ItemModelGenerator(generator, helper));
		generator.addProvider(true, new BiomeTagGenerator(output, provider, helper));
		generator.addProvider(true, new CustomTagGenerator.BannerPatternTagGenerator(output, provider, helper));
		BlockTagGenerator blocktags = new BlockTagGenerator(generator.getPackOutput(), provider, helper);
		generator.addProvider(true, blocktags);
		generator.addProvider(true, new FluidTagGenerator(output, provider, helper));
		generator.addProvider(true, new ItemTagGenerator(output, provider, blocktags, helper));
		generator.addProvider(true, new EntityTagGenerator(output, provider, helper));
		generator.addProvider(true, new CustomTagGenerator.EnchantmentTagGenerator(output, provider, helper));
		generator.addProvider(true, new LootGenerator(output));
		generator.addProvider(true, new StonecuttingGenerator(output));
		generator.addProvider(true, new CraftingGenerator(output));
		generator.addProvider(true, new WorldGenerator(generator, provider));
		generator.addProvider(true, new LootModifierGenerator(generator));

		generator.addProvider(true, new CrumbleHornGenerator(output, helper));
		generator.addProvider(true, new TransformationPowderGenerator(output, helper));
		generator.addProvider(true, new UncraftingRecipeGenerator(output, helper));
		generator.addProvider(true, new StalactiteGenerator(output));
	}
}
