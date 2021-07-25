package twilightforest.entity.boss;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.renderer.entity.HydraHeadRenderer;

public class HydraHeadEntity extends HydraPartEntity {

	private static final EntityDataAccessor<Float> DATA_MOUTH_POSITION = SynchedEntityData.defineId(HydraHeadEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> DATA_MOUTH_POSITION_LAST = SynchedEntityData.defineId(HydraHeadEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Byte> DATA_STATE = SynchedEntityData.defineId(HydraHeadEntity.class, EntityDataSerializers.BYTE);

	public HydraHeadEntity(HydraEntity hydra) {
		super(hydra, 4F, 4F);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityRenderer<?> renderer(EntityRenderDispatcher manager) {
		return new HydraHeadRenderer(manager);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_MOUTH_POSITION, 0F);
		entityData.define(DATA_MOUTH_POSITION_LAST, 0F);
		entityData.define(DATA_STATE, (byte) 0);
	}

	public float getMouthOpen() {
		return entityData.get(DATA_MOUTH_POSITION);
	}

	public float getMouthOpenLast() {
		return entityData.get(DATA_MOUTH_POSITION_LAST);
	}

	public HydraHeadContainer.State getState() {
		return HydraHeadContainer.State.values()[entityData.get(DATA_STATE)];
	}

	public void setMouthOpen(float openness) {
		entityData.set(DATA_MOUTH_POSITION_LAST, getMouthOpen());
		entityData.set(DATA_MOUTH_POSITION, openness);
	}

	public void setState(HydraHeadContainer.State state) {
		entityData.set(DATA_STATE, (byte) state.ordinal());
	}
}
