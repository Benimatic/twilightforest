package twilightforest.capabilities.thrown;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.network.TFPacketHandler;
import twilightforest.network.ThrowPlayerPacket;
import twilightforest.network.UpdateThrownPacket;

public class YetiThrowCapabilityHandler implements YetiThrowCapability {

	public static final int THROW_COOLDOWN = 200;

	private boolean thrown;
	private LivingEntity host;
	@Nullable
	private LivingEntity thrower;
	private int throwCooldown;
	private Vec3 throwVector = Vec3.ZERO;

	@Override
	public void setEntity(LivingEntity entity) {
		this.host = entity;
	}

	@Override
	public void update() {
		if (this.host instanceof Player player && this.getThrown()) {
			if (player.onGround() || player.isSwimming() || player.isInWater()) {
				this.setThrown(false, null);
			}
		}
		if (this.throwCooldown > 0) {
			if (!this.host.level().isClientSide() && this.throwCooldown == THROW_COOLDOWN - 1) { // Actually throw the victim
				this.host.push(this.throwVector.x(), this.throwVector.y(), this.throwVector.z());

				if (this.host instanceof ServerPlayer player) {
					TFPacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ThrowPlayerPacket(this.throwVector.x(), this.throwVector.y(), this.throwVector.z()));
				}
				this.throwVector = Vec3.ZERO;
			}
			this.throwCooldown--;
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

	@Override
	public void setThrowVector(Vec3 vector) {
		this.throwVector = vector;
	}

	private void sendUpdatePacket() {
		if (!this.host.level().isClientSide()) {
			TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.host), new UpdateThrownPacket(this.host, this));
		}
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("yetiThrown", this.getThrown());
		tag.putInt("throwCooldown", this.getThrowCooldown());
		tag.putDouble("throwX", this.throwVector.x());
		tag.putDouble("throwY", this.throwVector.y());
		tag.putDouble("throwZ", this.throwVector.z());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.setThrown(nbt.getBoolean("yetiThrown"), null);
		this.setThrowCooldown(nbt.getInt("throwCooldown"));
		this.throwVector = new Vec3(nbt.getDouble("throwX"), nbt.getDouble("throwY"), nbt.getDouble("throwZ"));
	}
}
