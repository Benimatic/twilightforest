package twilightforest.entity.ai;

import twilightforest.entity.EntityTFRovingCube;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * 
 * This is a task that runs when we are near a symbol and have stopped pathfinding, but are not centered on the symbol.
 * 
 * The goal of this task is to center on a symbol.
 * 
 * @author benma_000
 *
 */
public class EntityAICubeCenterOnSymbol extends EntityAIBase {
	
	private EntityTFRovingCube myCube;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;

	public EntityAICubeCenterOnSymbol(EntityTFRovingCube entityTFRovingCube, double d) {
		this.myCube = entityTFRovingCube;
		this.xPosition = this.myCube.symbolX;
		this.yPosition = this.myCube.symbolY;
		this.zPosition = this.myCube.symbolZ;
		this.speed = d;
	}

	@Override
	public boolean shouldExecute() {
		if (this.myCube.getNavigator().noPath()) {
			return false;
		} else if (this.isCloseToSymbol()){
			return true;
		}
	}

	private boolean isCloseToSymbol() {
		// TODO Auto-generated method stub
		return false;
	}

}
