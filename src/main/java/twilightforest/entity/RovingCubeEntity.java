package twilightforest.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import twilightforest.client.particle.TFParticleType;
import twilightforest.entity.ai.CubeCenterOnSymbolGoal;
import twilightforest.entity.ai.CubeMoveToRedstoneSymbolsGoal;

public class RovingCubeEntity extends MonsterEntity {

	// data needed for cube AI

	// last circle visited
	public boolean hasFoundSymbol = false;
	public int symbolX = 0;
	public int symbolY = 0;
	public int symbolZ = 0;

	// direction traveling

	// blocks traveled

	public RovingCubeEntity(EntityType<? extends RovingCubeEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new CubeMoveToRedstoneSymbolsGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new CubeCenterOnSymbolGoal(this, 1.0D));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23000000417232513D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	@Override
	public void livingTick() {
		super.livingTick();

		if (this.world.isRemote) {
			for (int i = 0; i < 3; i++) {
				float px = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75F;
				float py = this.getEyeHeight() - 0.25F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75F;
				float pz = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75F;

				world.addParticle(TFParticleType.ANNIHILATE.get(), this.lastTickPosX + px, this.lastTickPosY + py, this.lastTickPosZ + pz, 0, 0, 0);
			}
		}
	}
}
