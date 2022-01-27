package twilightforest.data.custom;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;

public class TransformationPowderGenerator extends TransformationPowderProvider{

	public TransformationPowderGenerator(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, TwilightForestMod.ID, helper);
	}

	@Override
	public void registerTransforms() {
		addTwoWayTransform(TFEntities.MINOTAUR, EntityType.ZOMBIFIED_PIGLIN);
		addTwoWayTransform(TFEntities.DEER, EntityType.COW);
		addTwoWayTransform(TFEntities.BOAR, EntityType.PIG);
		addTwoWayTransform(TFEntities.BIGHORN_SHEEP, EntityType.SHEEP);
		addTwoWayTransform(TFEntities.DWARF_RABBIT, EntityType.RABBIT);
		addTwoWayTransform(TFEntities.TINY_BIRD, EntityType.PARROT);
		addTwoWayTransform(TFEntities.RAVEN, EntityType.BAT);
		addTwoWayTransform(TFEntities.HOSTILE_WOLF, EntityType.WOLF);
		addTwoWayTransform(TFEntities.PENGUIN, EntityType.CHICKEN);
		addTwoWayTransform(TFEntities.HEDGE_SPIDER, EntityType.SPIDER);
		addTwoWayTransform(TFEntities.SWARM_SPIDER, EntityType.CAVE_SPIDER);
		addTwoWayTransform(TFEntities.WRAITH, EntityType.VEX);
		addTwoWayTransform(TFEntities.SKELETON_DRUID, EntityType.WITCH);
		addTwoWayTransform(TFEntities.CARMINITE_GHASTGUARD, EntityType.GHAST);
		addTwoWayTransform(TFEntities.TOWERWOOD_BORER, EntityType.SILVERFISH);
		addTwoWayTransform(TFEntities.MAZE_SLIME, EntityType.SLIME);
	}
}
