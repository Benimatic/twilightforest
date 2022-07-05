package twilightforest.capabilities.fan;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class FeatherFanCapabilityHandler implements FeatherFanFallCapability {

	private Player host;
	private boolean falling;

	@Override
	public void setEntity(Player entity) {
		this.host = entity;
	}

	@Override
	public void update() {
		if (this.getFalling() && !this.host.isOnGround()) {
			this.host.resetFallDistance();
		}

		if (this.host.isOnGround() || this.host.isSwimming()) {
			this.setFalling(false);
		}
	}

	@Override
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	@Override
	public boolean getFalling() {
		return this.falling;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("featherFanFalling", this.getFalling());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.setFalling(nbt.getBoolean("featherFanFalling"));
	}
}
