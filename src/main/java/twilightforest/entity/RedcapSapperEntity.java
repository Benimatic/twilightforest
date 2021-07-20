package twilightforest.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import twilightforest.entity.ai.RedcapPlantTNTGoal;
import twilightforest.item.TFItems;

public class RedcapSapperEntity extends RedcapEntity {

	public RedcapSapperEntity(EntityType<? extends RedcapSapperEntity> type, World world) {
		super(type, world);
		this.heldPick = new ItemStack(TFItems.ironwood_pickaxe.get());
		this.heldTNT.setCount(3);
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(TFItems.ironwood_boots.get()));
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new RedcapPlantTNTGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return RedcapEntity.registerAttributes()
				.createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
				.createMutableAttribute(Attributes.ARMOR, 2.0D);
	}
}
