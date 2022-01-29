package twilightforest.compat;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

import javax.annotation.Nonnull;

public class CuriosCompat extends TFCompat {

	public CuriosCompat() {
		super(CuriosApi.MODID);
	}

	@Override
	protected boolean preInit() {
		return true;
	}

	@Override
	protected void init() {

	}

	@Override
	protected void postInit() {

	}

	@Override
	protected void handleIMCs() {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
	}

	@Override
	protected void initItems(RegistryEvent.Register<Item> evt) {

	}

	public static ICapabilityProvider setupCuriosCapability(ItemStack stack) {
		return CurioItemCapability.createProvider(new ICurio() {
					@Override
					public ItemStack getStack() {
						return stack;
					}

					@Nonnull
					@Override
					public SoundInfo getEquipSound(top.theillusivec4.curios.api.SlotContext slotContext) {
						return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
					}

					@Override
					public boolean canEquipFromUse(top.theillusivec4.curios.api.SlotContext slotContext) {
						return true;
					}
				});
	}
}
