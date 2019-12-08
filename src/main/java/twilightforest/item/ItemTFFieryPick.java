package twilightforest.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.util.ParticleHelper;
import twilightforest.util.TFItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class ItemTFFieryPick extends PickaxeItem {

	protected ItemTFFieryPick(IItemTier toolMaterial, Properties props) {
		super(toolMaterial, 1, -2.8F, props.group(TFItems.creativeTab));
	}

	@SubscribeEvent
	public static void onDrops(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() != null && event.getHarvester().getHeldItemMainhand().getItem() == TFItems.fiery_pickaxe
				&& event.getState().getBlock().canHarvestBlock(event.getWorld(), event.getPos(), event.getHarvester())) {

			List<ItemStack> removeThese = new ArrayList<>();
			List<ItemStack> addThese = new ArrayList<>();

			for (ItemStack input : event.getDrops()) {
				ItemStack result = FurnaceRecipe.instance().getSmeltingResult(input);
				if (!result.isEmpty()) {

					int combinedCount = input.getCount() * result.getCount();

					addThese.addAll(TFItemStackUtils.splitToSize(new ItemStack(result.getItem(), combinedCount, result.getItemDamage())));
					removeThese.add(input);

					// [VanillaCopy] SlotFurnaceOutput.onCrafting
					int i = combinedCount;
					float f = FurnaceRecipes.instance().getSmeltingExperience(result);

					if (f == 0.0F) {
						i = 0;
					} else if (f < 1.0F) {
						int j = MathHelper.floor((float) i * f);

						if (j < MathHelper.ceil((float) i * f) && Math.random() < (double) ((float) i * f - (float) j)) {
							++j;
						}

						i = j;
					}

					while (i > 0) {
						int k = ExperienceOrbEntity.getXPSplit(i);
						i -= k;
						event.getHarvester().world.addEntity(new ExperienceOrbEntity(event.getWorld(), event.getHarvester().posX, event.getHarvester().posY + 0.5D, event.getHarvester().posZ, k));
					}

					ParticleHelper.spawnParticles(event.getWorld(), event.getPos(), ParticleTypes.FLAME, 5, 0.02);
				}
			}

			event.getDrops().removeAll(removeThese);
			event.getDrops().addAll(addThese);
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		boolean result = super.hitEntity(stack, target, attacker);

		if (result && !target.world.isRemote && !target.isImmuneToFire()) {
			ParticleHelper.spawnParticles(target, ParticleTypes.FLAME, 20, 0.02);
			target.setFire(15);
		}

		return result;
	}

	private static final Rarity RARITY = Rarity.UNCOMMON;

	@Nonnull
	@Override
	public Rarity getRarity(ItemStack stack) {
		return stack.isEnchanted() ? Rarity.RARE.compareTo(RARITY) > 0 ? Rarity.RARE : RARITY : RARITY;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
		super.addInformation(stack, world, tooltip, flags);
		tooltip.add(new TranslationTextComponent(getTranslationKey() + ".tooltip"));
	}
}
