package twilightforest.tileentity.spawner;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.text.StringTextComponent;
import twilightforest.entity.TFEntities;
import twilightforest.tileentity.TFTileEntities;

public class TileEntityTFFinalBossSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFFinalBossSpawner() {
		super(TFTileEntities.FINAL_BOSS_SPAWNER.get(), EntityType.getKey(TFEntities.kobold.get()));
	}

	@Override
	protected void initializeCreature(LivingEntity myCreature) {
		super.initializeCreature(myCreature);
		myCreature.setCustomName(new StringTextComponent("Final Boss"));
		myCreature.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024);
		myCreature.setHealth(myCreature.getMaxHealth());
	}
}
