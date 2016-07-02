package twilightforest.entity.ai;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import twilightforest.entity.EntityTFRovingCube;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;

public class EntityAICubeMoveToRedstoneSymbols extends EntityAIBase {

	private EntityTFRovingCube myCube;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    
	public EntityAICubeMoveToRedstoneSymbols(EntityTFRovingCube entityTFRovingCube, double d) {
		this.myCube = entityTFRovingCube;
		this.speed = d;
	}

	@Override
	public boolean shouldExecute() {
		if (this.myCube.getRNG().nextInt(20) != 0)
        {
            return false;
        }
        else
        {
        	//System.out.println("Cube scanning for symbol");
        	
            Vec3d vec3 = this.searchForRedstoneSymbol(this.myCube, 16, 5);

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.xPosition = vec3.xCoord;
                this.yPosition = vec3.yCoord;
                this.zPosition = vec3.zCoord;
                return true;
            }
        }
	}

	@Override
    public boolean continueExecuting()
    {
        return !this.myCube.getNavigator().noPath();
    }

	@Override
    public void startExecuting()
    {
        this.myCube.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Search the area for a redstone circle (8 redstone dust around a blank square)
     */
	private Vec3d searchForRedstoneSymbol(EntityTFRovingCube myCube2, int xzRange, int yRange) {

		BlockPos curPos = new BlockPos(myCube2);

        boolean foundSymbol = false;
		
		for (int x = -xzRange; x < xzRange; x++) {
			for (int z = -xzRange; z < xzRange; z++) {
				for (int y = -yRange; y < yRange; y++) {
					if (this.isRedstoneSymbol(curPos.add(x, y, z))) {
						
			        	//System.out.println("Cube found symbol at " + x + ", " + y + ", " + z);
						
						this.myCube.hasFoundSymbol = true;
						this.myCube.symbolX = curPos.getX() + x;
						this.myCube.symbolY = curPos.getY() + y;
						this.myCube.symbolZ = curPos.getZ() + z;
						
						return new Vec3d(curPos).addVector(x, y, z);
					}
				}
			}
		}
		
		return null;
	}

	private boolean isRedstoneSymbol(BlockPos pos) {
		
    	//System.out.println("Cube checking area at " + x + ", " + y + ", " + z);
		
		if (!this.myCube.worldObj.isBlockLoaded(pos) || !this.myCube.worldObj.isAirBlock(pos)) {
			return false;
		} else {
			// we found an air block, is it surrounded by redstone?
			for (EnumFacing e : EnumFacing.VALUES) {
				if (this.myCube.worldObj.getBlockState(pos.offset(e)).getBlock() != Blocks.REDSTONE_WIRE) {
					return false;
				}
			}

			return true;
		}
	}

}
