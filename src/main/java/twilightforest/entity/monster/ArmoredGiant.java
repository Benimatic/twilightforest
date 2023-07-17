package twilightforest.entity.monster;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import twilightforest.init.TFItems;

public class ArmoredGiant extends GiantMiner {

	public ArmoredGiant(EntityType<? extends ArmoredGiant> type, Level world) {
		super(type, world);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TFItems.GIANT_SWORD.get()));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
		this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
		this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
	}
}
