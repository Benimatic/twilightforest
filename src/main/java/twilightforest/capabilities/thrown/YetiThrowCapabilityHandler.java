package twilightforest.capabilities.thrown;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.network.TFPacketHandler;
import twilightforest.network.UpdateThrownPacket;

public class YetiThrowCapabilityHandler implements YetiThrowCapability {

	private boolean thrown;
	private LivingEntity host;
	@Nullable
	private LivingEntity thrower;

	@Override
	public void setEntity(LivingEntity entity) {
		this.host = entity;
	}

	@Override
	public void update() {
		if (this.getThrown() && (this.host.isOnGround() || this.host.isSwimming())) {
			this.setThrown(false, null);
		}
	}

	@Override
	public boolean getThrown() {
		return this.thrown;
	}

	@Override
	public void setThrown(boolean thrown, @Nullable LivingEntity thrower) {
		this.thrown = thrown;
		this.thrower = thrower;
		if (!this.host.getLevel().isClientSide()) {
			TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.host), new UpdateThrownPacket(this.host, this));
		}
	}

	@Override
	public @Nullable LivingEntity getThrower() {
		return this.thrower;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("yetiThrown", this.getThrown());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.setThrown(nbt.getBoolean("yetiThrown"), null);
	}
}
