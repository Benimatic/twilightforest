package twilightforest.capabilities.thrown;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.network.TFPacketHandler;
import twilightforest.network.UpdateThrownPacket;

public class YetiThrowCapabilityHandler implements YetiThrowCapability {

	private boolean thrown;
	private LivingEntity host;
	@Nullable
	private LivingEntity thrower;
	private int throwCooldown;

	@Override
	public void setEntity(LivingEntity entity) {
		this.host = entity;
	}

	@Override
	public void update() {
		if (this.host instanceof Player player && this.getThrown()) {
			if (player.isOnGround() || player.isSwimming() || player.isInWater()) {
				this.setThrown(false, null);
			}
		} else {
			if (this.throwCooldown > 0) {
				this.throwCooldown--;
			}
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
		this.sendUpdatePacket();
	}

	@Override
	public @Nullable LivingEntity getThrower() {
		return this.thrower;
	}

	@Override
	public int getThrowCooldown() {
		return this.throwCooldown;
	}

	@Override
	public void setThrowCooldown(int cooldown) {
		this.throwCooldown = cooldown;
		this.sendUpdatePacket();
	}

	private void sendUpdatePacket() {
		if (!this.host.getLevel().isClientSide()) {
			TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.host), new UpdateThrownPacket(this.host, this));
		}
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("yetiThrown", this.getThrown());
		tag.putInt("throwCooldown", this.getThrowCooldown());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.setThrown(nbt.getBoolean("yetiThrown"), null);
		this.setThrowCooldown(nbt.getInt("throwCooldown"));
	}
}
