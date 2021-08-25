package twilightforest.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.capabilities.shield.ShieldCapabilityHandler;

import javax.annotation.Nonnull;

public class CapabilityList {

	@CapabilityInject(IShieldCapability.class)
	public static final Capability<IShieldCapability> SHIELDS;

	static {
		SHIELDS = null;
	}

	@SubscribeEvent
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof LivingEntity) {
			e.addCapability(IShieldCapability.ID, new ICapabilitySerializable<CompoundTag>() {

				IShieldCapability inst = new ShieldCapabilityHandler();

				{
					inst.setEntity((LivingEntity) e.getObject());
				}

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return SHIELDS.orEmpty(capability, LazyOptional.of(() -> inst));
				}

				@Override
				public CompoundTag serializeNBT() {
					return inst.serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt) {
					inst.deserializeNBT(nbt);
				}
			});
		}
	}
}
