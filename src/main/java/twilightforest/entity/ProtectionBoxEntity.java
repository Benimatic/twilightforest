package twilightforest.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProtectionBoxEntity extends Entity {

	public int lifeTime;

	public final int sizeX;
	public final int sizeY;
	public final int sizeZ;

	private final BoundingBox sbb;

	public ProtectionBoxEntity(EntityType<?> type, Level world) {
		super(type, world);
		sizeX = sizeY = sizeZ = 0;
		sbb = null;
	}

	public ProtectionBoxEntity(Level world, BoundingBox sbb) {
		super(TFEntities.protection_box, world);

		this.sbb = new BoundingBox(sbb);

		this.moveTo(sbb.x0, sbb.y0, sbb.z0, 0.0F, 0.0F);

		sizeX = sbb.getXSpan();
		sizeY = sbb.getYSpan();
		sizeZ = sbb.getZSpan();

		this.dimensions = EntityDimensions.fixed(Math.max(sizeX, sizeZ), sizeY);

		lifeTime = 60;
	}

	@Override
	public void tick() {
		super.tick();

		if (lifeTime <= 1) {
			remove();
		} else {
			lifeTime--;
		}
	}

	public boolean matches(BoundingBox sbb) {
		return this.sbb.x0 == sbb.x0 && this.sbb.y0 == sbb.y0 && this.sbb.z0 == sbb.z0
				&& this.sbb.x1 == sbb.x1 && this.sbb.y1 == sbb.y1 && this.sbb.z1 == sbb.z1;
	}

	public void resetLifetime() {
		lifeTime = 60;
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		throw new IllegalStateException("should never be spawned on server");
	}
}
