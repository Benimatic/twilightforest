package twilightforest.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFRecipes;
import twilightforest.init.TFSounds;

import java.util.UUID;

public class TransformationDispenseBehavior extends DefaultDispenseItemBehavior {

	boolean fired = false;

	@Override
	protected ItemStack execute(BlockSource source, ItemStack stack) {
		Level level = source.level();
		RandomSource random = level.getRandom();
		BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
		if (!level.isClientSide()) {
			for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS)) {
				level.getRecipeManager().getAllRecipesFor(TFRecipes.TRANSFORM_POWDER_RECIPE.get()).forEach(recipeHolder -> {
					if (recipeHolder.value().input() == livingentity.getType() || (recipeHolder.value().isReversible() && recipeHolder.value().result() == livingentity.getType())) {
						EntityType<?> type = recipeHolder.value().isReversible() && recipeHolder.value().result() == livingentity.getType() ? recipeHolder.value().input() : recipeHolder.value().result();
						Entity newEntity = type.create(level);
						if (newEntity != null) {
							newEntity.moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity.getYRot(), livingentity.getXRot());
							if (newEntity instanceof Mob mob && livingentity.level() instanceof ServerLevelAccessor accessor) {
								EventHooks.onFinalizeSpawn(mob, accessor, livingentity.level().getCurrentDifficultyAt(livingentity.blockPosition()), MobSpawnType.CONVERSION, null, null);
							}

							try {
								UUID uuid = newEntity.getUUID();
								newEntity.load(livingentity.saveWithoutId(newEntity.saveWithoutId(new CompoundTag())));
								newEntity.setUUID(uuid);
							} catch (Exception e) {
								TwilightForestMod.LOGGER.warn("Couldn't transform entity NBT data", e);
							}

							livingentity.level().addFreshEntity(newEntity);
							livingentity.discard();

							if (livingentity instanceof Mob && livingentity.level().isClientSide()) {
								((Mob) livingentity).spawnAnim();
								((Mob) livingentity).spawnAnim();
							}
							livingentity.playSound(TFSounds.POWDER_USE.get(), 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
							stack.shrink(1);
							this.fired = true;
						}
					}
				});
			}
		}
		return stack;
	}

	@Override
	protected void playSound(BlockSource source) {
		if (this.fired) {
			super.playSound(source);
		} else {
			source.level().levelEvent(1001, source.pos(), 0);
		}
	}
}
