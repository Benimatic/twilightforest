package twilightforest.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationTrigger;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.HasAdvancementTrigger;
import twilightforest.worldgen.biomes.BiomeKeys;

import java.util.function.Consumer;

public class PatchouliAdvancementGenerator implements Consumer<Consumer<Advancement>> {
	@Override
	public void accept(Consumer<Advancement> consumer) {
		Advancement root = Advancement.Builder.advancement()
				.addCriterion("hidden", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, "twilightforest:alt/root");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("in_biome", LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(BiomeKeys.DARK_FOREST)))
				.addCriterion("has_other", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/dark_forest_center")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/biomes/dark_forest");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("in_biome", LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(BiomeKeys.DARK_FOREST_CENTER)))
				.save(consumer, "twilightforest:alt/biomes/dark_forest_center");
	}
}
