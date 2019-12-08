package twilightforest.enums;

import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import twilightforest.item.TFItems;

import java.util.function.Supplier;

public enum TwilightItemTier implements IItemTier {
	// harvestLevel, maxUses, efficiency, damage, enchantability
	TOOL_IRONWOOD(2, 512, 6.5F, 2, 25, () -> Ingredient.fromItems(TFItems.ironwood_ingot)),
	TOOL_FIERY(4, 1024, 9F, 4, 10, () -> Ingredient.fromItems(TFItems.fiery_ingot)),
	TOOL_STEELEAF(3, 131, 8.0F, 3, 9, () -> Ingredient.fromItems(TFItems.steeleaf_ingot)),
	TOOL_KNIGHTLY(3, 512, 8.0F, 3, 8, () -> Ingredient.fromItems(TFItems.knightmetal_ingot)),
	TOOL_GIANT(1, 1024, 4.0F, 1.0F, 5, () -> Ingredient.fromItems(TFItems.knightmetal_ingot)),
	TOOL_ICE(0, 32, 1.0F, 3.5F, 5, () -> Ingredient.fromItems(Blocks.PACKED_ICE)),
	TOOL_GLASS(0, 1, 1.0F, 36.0F, 30, () -> Ingredient.EMPTY);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairMaterial;

	TwilightItemTier(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Supplier<Ingredient> repairMaterial) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = damage;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public int getMaxUses() {
		return maxUses;
	}

	@Override
	public float getEfficiency() {
		return efficiency;
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairMaterial.get();
	}
}
