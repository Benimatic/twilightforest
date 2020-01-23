package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.NonNullList;
import twilightforest.util.ParticleHelper;

import javax.annotation.Nonnull;

public class ItemTFGlassSword extends SwordItem {

	public ItemTFGlassSword(IItemTier toolMaterial, Properties props) {
		super(toolMaterial, 3, -2.4F, props.group(TFItems.creativeTab));
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		attacker.world.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), Blocks.GLASS.getDefaultState().getSoundType().getBreakSound(), attacker.getSoundCategory(), 1F, 0.5F);
		//TODO: Move to regular particle spawner?
		ParticleHelper.spawnParticles(target, ParticleTypes.BLOCK_CRACK, 20, 0.0, Block.getStateId(Blocks.STAINED_GLASS.getDefaultState()));
		stack.damageItem(stack.getMaxDamage() + 1, attacker, (user) -> user.sendBreakAnimation(attacker.getActiveHand()));
		return true;
	}

	@Nonnull
	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	//TODO 1.14: This method doesn't exist
	@Override
	public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> items) {
		super.fillItemGroup(tab, items);

		if (isInGroup(tab)) {
			ItemStack stack = new ItemStack(this);
			CompoundNBT tags = new CompoundNBT();
			tags.putBoolean("Unbreakable", true);
			stack.setTag(tags);
			items.add(stack);
		}
	}
}