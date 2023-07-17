package twilightforest.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import twilightforest.loot.TFLootTables;

import java.util.List;
import java.util.Map;

public class LootGenerator extends LootTableProvider {
	public LootGenerator(PackOutput output) {
		super(output, TFLootTables.allBuiltin(), List.of(
				new LootTableProvider.SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK),
				new LootTableProvider.SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
				new LootTableProvider.SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY),
				new LootTableProvider.SubProviderEntry(SpecialLootTables::new, LootContextParamSets.EMPTY)
		));
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {

	}
}
