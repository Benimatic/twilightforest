package twilightforest.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class TomeBolt extends TFThrowable implements ItemSupplier {

	public TomeBolt(EntityType<? extends TomeBolt> type, Level world, LivingEntity thrower) {
		super(type, world, thrower);
	}

	public TomeBolt(EntityType<? extends TomeBolt> type, Level world) {
		super(type, world);
	}

	@Override
	public void tick() {
		super.tick();
		this.makeTrail(ParticleTypes.CRIT, 5);
	}

	@Override
	protected float getGravity() {
		return 0.003F;
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.PAPER));
			for (int i = 0; i < 8; ++i) {
				this.getLevel().addParticle(particle, false, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, this.random.nextDouble() * 0.2D, this.random.nextGaussian() * 0.05D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (result.getEntity() instanceof LivingEntity living) {
			if (result.getEntity().hurt(TFDamageTypes.getIndirectEntityDamageSource(this.getLevel(), this.random.nextBoolean() ? TFDamageTypes.LOST_WORDS : TFDamageTypes.SCHOOLED, this.getOwner(), this), 3)) {
				// inflict move slowdown
				int duration = this.getLevel().getDifficulty() == Difficulty.EASY ? 2 : this.getLevel().getDifficulty() == Difficulty.NORMAL ? 6 : 8;
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration * 20, 1));
			}
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.getLevel().isClientSide()) {
			this.getLevel().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Items.PAPER);
	}
}
