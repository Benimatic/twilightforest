package twilightforest.entity.boss;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFSounds;
import twilightforest.client.renderer.entity.SnowQueenIceShieldLayer;
import twilightforest.entity.TFPartEntity;

public class SnowQueenIceShieldEntity extends TFPartEntity<SnowQueenEntity> {

    public SnowQueenIceShieldEntity(SnowQueenEntity parent) {
        super(parent);
        dimensions = EntityDimensions.scalable(0.75F, 0.75F);
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityRenderer<?> renderer(EntityRendererProvider.Context manager) {
		return new SnowQueenIceShieldLayer<>(manager);
	}

	@Override
    public boolean hurt(DamageSource source, float amount) {
        playSound(TFSounds.SNOW_QUEEN_BREAK, 1.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void defineSynchedData() {

    }
}
