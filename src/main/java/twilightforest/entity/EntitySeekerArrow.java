package twilightforest.entity;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySeekerArrow extends EntityArrow {

	private EntityLivingBase homingTarget;
	double seekDistance = 5.0;


	public EntitySeekerArrow(World par1World) {
		super(par1World);
	}

	public EntitySeekerArrow(World world, EntityPlayer player, float velocity) {
		super(world, player, velocity);
	}

    /**
     * Called to update the entity's position/logic.
     */
    @SuppressWarnings("rawtypes")
	public void onUpdate()
    {
        // seek!
        if (isThisArrowFlying()) {

        	if (this.homingTarget == null) {
        		// find new target

        		// target BB
        		double minX = this.lastTickPosX;
        		double minY = this.lastTickPosY;
        		double minZ = this.lastTickPosZ;
        		double maxX = this.lastTickPosX;
        		double maxY = this.lastTickPosY;
        		double maxZ = this.lastTickPosZ;


        		AxisAlignedBB targetBB = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

        		// add two possible courses to our selection box
				Vec3d courseVec = new Vec3d(this.motionX * seekDistance, this.motionY * seekDistance, this.motionZ * seekDistance);
        		courseVec.rotateAroundY((float) (Math.PI / 6F));
        		targetBB = targetBB.addCoord(courseVec.xCoord, courseVec.yCoord, courseVec.zCoord);

        		courseVec = new Vec3d(this.motionX * seekDistance, this.motionY * seekDistance, this.motionZ * seekDistance);
        		courseVec.rotateAroundY(-(float) (Math.PI / 6F));
        		targetBB = targetBB.addCoord(courseVec.xCoord, courseVec.yCoord, courseVec.zCoord);
        		
        		targetBB.minY -= 3;
        		targetBB.maxY += 3;

        		// find targets in box
        		List targets = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, targetBB, IEntitySelector.selectAnything);

        		double closestDot = 1;

        		for (Object thing : targets) {
        			if (thing instanceof EntityLivingBase && !(thing instanceof EntityPlayer)) {
        				EntityLivingBase living = (EntityLivingBase)thing;

        				System.out.println("Possible target : " + living);
        				//System.out.println("Selection box =  " + targetBB);

        				courseVec = new Vec3d(this.motionX, this.motionY, this.motionZ);
        				courseVec = courseVec.normalize();
        				Vec3d targetVec = new Vec3d(this.posX - living.posX, this.posY - (living.posY + (double)living.getEyeHeight()), this.posZ - living.posZ);

        				//double d0 = targetVec.lengthVector(); // do we need this?
        				targetVec = targetVec.normalize();
        				double dot = courseVec.dotProduct(targetVec);

        				//System.out.println("dot product : " + dot);

        				if (dot < closestDot) {
        					this.homingTarget = living;
        					closestDot = dot;
        				}
        			}
        		}
        		if (targets.size() > 0) {
        			//System.out.println("--- End of list");
        			//System.out.println("We have chosen " + this.homingTarget + " as the target");
        		}
        	} else {
        		// find ideal heading
				Vec3d targetVec = new Vec3d(this.posX - this.homingTarget.posX, this.posY - (this.homingTarget.posY + this.homingTarget.getEyeHeight()), this.posZ - this.homingTarget.posZ);
				targetVec = targetVec.normalize();
				
				Vec3d courseVec = new Vec3d(this.motionX * seekDistance, this.motionY * seekDistance, this.motionZ * seekDistance);
				courseVec = courseVec.normalize();
				
				double dotProduct = courseVec.dotProduct(targetVec);
				//System.out.println("target vec compared to course vec= " + dotProduct);
				
				if (dotProduct < 0) {
	
	        		// match current speed
	        		float currentSpeed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
	        		
	        		currentSpeed *= 1.0;
	        		
	        		targetVec.xCoord *= currentSpeed;
	        		targetVec.yCoord *= currentSpeed;
	        		targetVec.zCoord *= currentSpeed;
	
	        		// adjust current heading
	        		double dx = MathHelper.clamp_double(targetVec.xCoord, -2.0, 2.0);
	        		double dy = MathHelper.clamp_double(targetVec.yCoord, -1.0, 1.0);
	        		double dz = MathHelper.clamp_double(targetVec.zCoord, -2.0, 2.0);
	
	//        		System.out.println("Current heading is " + this.motionX + ", " + this.motionY + ", " + this.motionZ);
	//        		System.out.println("Ideal heading is " + targetVec.xCoord + ", " + targetVec.yCoord + ", " + targetVec.zCoord);
	//        		System.out.println("Adjustment is " + dx + ", " + dy + ", " + dz);
	        		
	        		this.motionX -= dx;
	        		this.motionY -= dy;
	        		this.motionZ -= dz;
				} else {
					// abandon target, they're behind us!
					//System.out.println("abandoning target!");
					
					this.homingTarget = null;
				}
        	}

            this.motionY += 0.045F;

        }
        
        // this is a slower arrow, adjust for gravity slightly
        
        super.onUpdate();
        

    }

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(Items.ARROW);
	}

	private boolean isThisArrowFlying() {
		return MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) > 1.0;
	}

}
