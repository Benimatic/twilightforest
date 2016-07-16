package twilightforest.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import twilightforest.TFGenericPacketHandler;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import twilightforest.network.PacketThrowPlayer;

public class EntityAITFThrowRider extends EntityAIBase {

	
    private EntityCreature theEntityCreature;
	private int throwTimer;
    
    
	public EntityAITFThrowRider(EntityCreature par1EntityCreature, float par2)
    {
        this.theEntityCreature = par1EntityCreature;
        this.setMutexBits(1);
    }
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
        if (this.theEntityCreature.getRidingEntity() == null || this.theEntityCreature.getRNG().nextInt(5) > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
    	EntityLivingBase rider = (EntityLivingBase) this.theEntityCreature.getRidingEntity();
        rider.dismountEntity(theEntityCreature);
        
        Vec3d throwVec = this.theEntityCreature.getLookVec().scale(2);

        // let's throw the player a fixed value in the air
        throwVec = new Vec3d(throwVec.xCoord, 0.9, throwVec.zCoord);
        
        rider.addVelocity(throwVec.xCoord, throwVec.yCoord, throwVec.zCoord);

        // if we're throwing a player (probably!), send a packet with the velocity
        if (rider instanceof EntityPlayerMP) {
        	EntityPlayerMP player = (EntityPlayerMP)rider;

            IMessage message = new PacketThrowPlayer((float)throwVec.xCoord, (float)throwVec.yCoord, (float)throwVec.zCoord);
    		TwilightForestMod.genericChannel.sendTo(message, player);
        }
        
        System.out.println("throw!");
        this.throwTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
    	if (this.theEntityCreature.getRidingEntity() == null) {
    		this.throwTimer++;
    	}
    	
    	return this.throwTimer <= 40;
    }
       
}
