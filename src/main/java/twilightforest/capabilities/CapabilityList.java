package twilightforest.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import twilightforest.TwilightForestMod;
import twilightforest.capabilities.shield.IShieldCapability;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class CapabilityList {

	@CapabilityInject(IShieldCapability.class)
	public static final Capability<IShieldCapability> SHIELDS;

	static {
		SHIELDS = null;
	}

	@SubscribeEvent
	public static void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) {
		if (SHIELDS != null && e.getObject() instanceof EntityLivingBase) {
			e.addCapability(IShieldCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IShieldCapability inst = SHIELDS.getDefaultInstance();

				{
					inst.setEntity((EntityLivingBase) e.getObject());
				}

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == SHIELDS;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == SHIELDS ? SHIELDS.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) SHIELDS.getStorage().writeNBT(SHIELDS, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					SHIELDS.getStorage().readNBT(SHIELDS, inst, null, nbt);
				}

			});
		}
	}
}
