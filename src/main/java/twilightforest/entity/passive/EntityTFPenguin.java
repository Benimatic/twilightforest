package twilightforest.entity.passive;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import twilightforest.entity.TFEntities;

public class EntityTFPenguin extends EntityTFBird {
	public EntityTFPenguin(EntityType<? extends EntityTFPenguin> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new SwimGoal(this));
		goalSelector.addGoal(1, new PanicGoal(this, 1.75F));
		goalSelector.addGoal(2, new BreedGoal(this, 1.0F));
		goalSelector.addGoal(3, new TemptGoal(this, 0.75F, Ingredient.fromItems(Items.COD), false));
		goalSelector.addGoal(4, new FollowParentGoal(this, 1.15F));
		goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0F));
		goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6F));
		goalSelector.addGoal(7, new LookAtGoal(this, EntityTFPenguin.class, 5F, 0.02F));
		goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	@Override
	public AnimalEntity func_241840_a(ServerWorld world, AgeableEntity entityanimal) {
		return TFEntities.penguin.create(world);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.COD;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
	}
}
