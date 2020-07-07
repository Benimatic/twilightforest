package twilightforest.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import twilightforest.client.particle.TFParticleType;
import twilightforest.potions.TFPotions;

public class ItemTFIceSword extends SwordItem {

	public ItemTFIceSword(IItemTier toolMaterial, Properties props) {
		super(toolMaterial, 3, -2.4F, props);
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		boolean result = super.hitEntity(stack, target, attacker);

		if (result) {
			if (!target.world.isRemote) {
				target.addPotionEffect(new EffectInstance(TFPotions.frosty.get(), 20 * 10, 2));
			} else {
				target.world.addParticle(TFParticleType.SNOW.get(), target.getPosX(), target.getPosY() + target.getHeight() * 0.5, target.getPosZ(), target.getWidth() * 0.5, target.getHeight() * 0.5, target.getWidth() * 0.5);
			}
		}

		return result;
	}
}
