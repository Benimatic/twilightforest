package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityTFProtectionBox extends Entity {

	public int lifeTime = 60;

	public final int sizeX;
	public final int sizeY;
	public final int sizeZ;

	private final MutableBoundingBox sbb;

	public EntityTFProtectionBox(World world, MutableBoundingBox sbb) {
		super(world);

		this.sbb = new MutableBoundingBox(sbb);

		this.setLocationAndAngles(sbb.minX, sbb.minY, sbb.minZ, 0.0F, 0.0F);

		sizeX = sbb.getXSize();
		sizeY = sbb.getYSize();
		sizeZ = sbb.getZSize();

		this.setSize(Math.max(sizeX, sizeZ), sizeY);
	}

	@Override
	public void tick() {
		super.tick();

		if (lifeTime <= 1) {
			setDead();
		} else {
			lifeTime--;
		}
	}

	public boolean matches(MutableBoundingBox sbb) {
		return this.sbb.minX == sbb.minX && this.sbb.minY == sbb.minY && this.sbb.minZ == sbb.minZ
				&& this.sbb.maxX == sbb.maxX && this.sbb.maxY == sbb.maxY && this.sbb.maxZ == sbb.maxZ;
	}

	public void resetLifetime() {
		lifeTime = 60;
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getBrightnessForRender() {
		return 15728880;
	}

	@Override
	protected void registerData() {}

	@Override
	protected void readAdditional(CompoundNBT compound) {}

	@Override
	protected void writeAdditional(CompoundNBT compound) {}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean canRenderOnFire() {
		return false;
	}
}
