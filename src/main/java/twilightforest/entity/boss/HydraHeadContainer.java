package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import twilightforest.TwilightForestMod;

/**
 * This class holds the state data for a single hydra head
 * 
 * @author Ben
 *
 */
public class HydraHeadContainer {
	
	// balancing factors
	private static int FLAME_BURN_FACTOR = 3;
	private static int FLAME_DAMAGE = 19;
	private static int BITE_DAMAGE = 48;
	private static double FLAME_BREATH_TRACKING_SPEED = 0.04D;
	
	public static final int NEXT_AUTOMATIC = -1;
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_BITE_BEGINNING = 1;
	public static final int STATE_BITE_READY = 2;
	public static final int STATE_BITE_BITING = 3;
	public static final int STATE_BITE_ENDING = 4;
	public static final int STATE_FLAME_BEGINNING = 5;
	public static final int STATE_FLAME_BREATHING = 6;
	public static final int STATE_FLAME_ENDING = 7;
	public static final int STATE_MORTAR_BEGINNING = 8;
	public static final int STATE_MORTAR_LAUNCH = 9;
	public static final int STATE_MORTAR_ENDING = 10;
	public static final int STATE_DYING = 11;
	public static final int STATE_DEAD = 12;
	public static final int STATE_ATTACK_COOLDOWN = 13;
	public static final int STATE_BORN = 14;
	public static final int STATE_ROAR_START = 15;
	public static final int STATE_ROAR_RAWR = 16;


	public static final int NUM_STATES = 17;

	public EntityTFHydraHead headEntity;
	public EntityTFHydraNeck necka;
	public EntityTFHydraNeck neckb;
	public EntityTFHydraNeck neckc;
	public EntityTFHydraNeck neckd;
	public EntityTFHydraNeck necke;
	
	public Entity targetEntity;

	public double targetX;
	public double targetY;
	public double targetZ;

	public int prevState;
	public int currentState;
	public int nextState = NEXT_AUTOMATIC;
	
	public boolean didRoar;
	
	public boolean isSecondaryAttacking;
	
	public int ticksNeeded;
	public int ticksProgress;
	
	public final int headNum;
	
	public int damageTaken;
	public int respawnCounter;
	
	public final EntityTFHydra hydraObj;
	
	public int[] nextStates;
	public int[] stateDurations;
	public float[][] stateNeckLength;
	public float[][] stateXRotations;
	public float[][] stateYRotations;
	public float[][] stateMouthOpen;

	
	public HydraHeadContainer(EntityTFHydra hydra, int number, boolean startActive)
	{
		this.headNum = number;
		this.hydraObj = hydra;
		
		this.damageTaken = 0;
		this.respawnCounter = -1;
		this.didRoar = false;
		
		// is this a good place to initialize the necks?
		necka = new EntityTFHydraNeck(hydraObj, "neck" + headNum + "a", 2F, 2F);
		neckb = new EntityTFHydraNeck(hydraObj, "neck" + headNum + "b", 2F, 2F);
		neckc = new EntityTFHydraNeck(hydraObj, "neck" + headNum + "c", 2F, 2F);
		neckd = new EntityTFHydraNeck(hydraObj, "neck" + headNum + "d", 2F, 2F);
		necke = new EntityTFHydraNeck(hydraObj, "neck" + headNum + "e", 2F, 2F);
		
		// state variables, what state is next in the chain
		nextStates = new int[NUM_STATES];
		
		nextStates[STATE_IDLE] = STATE_IDLE;
		
		nextStates[STATE_BITE_BEGINNING] = STATE_BITE_READY;
		nextStates[STATE_BITE_READY] = STATE_BITE_BITING;
		nextStates[STATE_BITE_BITING] = STATE_BITE_ENDING;
		nextStates[STATE_BITE_ENDING] = STATE_ATTACK_COOLDOWN;
		
		nextStates[STATE_FLAME_BEGINNING] = STATE_FLAME_BREATHING;
		nextStates[STATE_FLAME_BREATHING] = STATE_FLAME_ENDING;
		nextStates[STATE_FLAME_ENDING] = STATE_ATTACK_COOLDOWN;
		
		nextStates[STATE_MORTAR_BEGINNING] = STATE_MORTAR_LAUNCH;
		nextStates[STATE_MORTAR_LAUNCH] = STATE_MORTAR_ENDING;
		nextStates[STATE_MORTAR_ENDING] = STATE_ATTACK_COOLDOWN;
		
		nextStates[STATE_ATTACK_COOLDOWN] = STATE_IDLE;
		
		nextStates[STATE_DYING] = STATE_DEAD;
		
		nextStates[STATE_DEAD] = STATE_DEAD;
		
		nextStates[STATE_BORN] = STATE_ROAR_START;
		nextStates[STATE_ROAR_START] = STATE_ROAR_RAWR;
		nextStates[STATE_ROAR_RAWR] = STATE_IDLE;
		
		// state durations, how long in each state
		stateDurations = new int[NUM_STATES];
		
		setupStateDurations();
		
		// state positions, where is each state positioned?
		stateNeckLength = new float[hydraObj.numHeads][NUM_STATES];
		stateXRotations = new float[hydraObj.numHeads][NUM_STATES];
		stateYRotations = new float[hydraObj.numHeads][NUM_STATES];
		stateMouthOpen = new float[hydraObj.numHeads][NUM_STATES];
		
		setupStateRotations();
		
		if (startActive)
		{
			this.prevState = STATE_IDLE;
			this.currentState = STATE_IDLE;
			this.nextState = NEXT_AUTOMATIC;
			this.ticksNeeded = 60;
			this.ticksProgress = 60;
		}
		else
		{
			this.prevState = STATE_DEAD;
			this.currentState = STATE_DEAD;
			this.nextState = NEXT_AUTOMATIC;
			this.ticksNeeded = 20;
			this.ticksProgress = 20;
		}
	}

	protected void setupStateDurations() {
		stateDurations[STATE_IDLE] = 10;
		
		stateDurations[STATE_BITE_BEGINNING] = 40;
		stateDurations[STATE_BITE_READY] = 80;
		stateDurations[STATE_BITE_BITING] = 7;
		stateDurations[STATE_BITE_ENDING] = 40;
		
		stateDurations[STATE_FLAME_BEGINNING] = 40;
		stateDurations[STATE_FLAME_BREATHING] = 100;
		stateDurations[STATE_FLAME_ENDING] = 30;
		
		stateDurations[STATE_MORTAR_BEGINNING] = 40;
		stateDurations[STATE_MORTAR_LAUNCH] = 25;
		stateDurations[STATE_MORTAR_ENDING] = 30;
		
		stateDurations[STATE_ATTACK_COOLDOWN] = 80;
		
		stateDurations[STATE_DYING] = 70;
		stateDurations[STATE_DEAD] = 20;

		stateDurations[STATE_BORN] = 20;
		
		stateDurations[STATE_ROAR_START] = 10;
		stateDurations[STATE_ROAR_RAWR] = 50;

	}

	protected void setupStateRotations() {
		setAnimation(0, STATE_IDLE, 60, 0, 7, 0);
		setAnimation(1, STATE_IDLE, 10, 60, 9, 0);
		setAnimation(2, STATE_IDLE, 10, -60, 9, 0);
		setAnimation(3, STATE_IDLE, 50, 90, 8, 0);
		setAnimation(4, STATE_IDLE, 50, -90, 8, 0);
		setAnimation(5, STATE_IDLE, -10, 90, 9, 0);
		setAnimation(6, STATE_IDLE, -10, -90, 9, 0);
		
		setAnimation(0, STATE_ATTACK_COOLDOWN, 60, 0, 7, 0);
		setAnimation(1, STATE_ATTACK_COOLDOWN, 10, 60, 9, 0);
		setAnimation(2, STATE_ATTACK_COOLDOWN, 10, -60, 9, 0);
		setAnimation(3, STATE_ATTACK_COOLDOWN, 50, 90, 8, 0);
		setAnimation(4, STATE_ATTACK_COOLDOWN, 50, -90, 8, 0);
		setAnimation(5, STATE_ATTACK_COOLDOWN, -10, 90, 9, 0);
		setAnimation(6, STATE_ATTACK_COOLDOWN, -10, -90, 9, 0);
		
		setAnimation(0, STATE_FLAME_BEGINNING, 50, 0, 8, 0.75F);
		setAnimation(1, STATE_FLAME_BEGINNING, 30, 45, 9, 0.75F);
		setAnimation(2, STATE_FLAME_BEGINNING, 30, -45, 9, 0.75F);
		setAnimation(3, STATE_FLAME_BEGINNING, 50, 90, 8, 0.75F);
		setAnimation(4, STATE_FLAME_BEGINNING, 50, -90, 8, 0.75F);
		setAnimation(5, STATE_FLAME_BEGINNING, -10, 90, 9, 0.75F);
		setAnimation(6, STATE_FLAME_BEGINNING, -10, -90, 9, 0.75F);
		
		setAnimation(0, STATE_FLAME_BREATHING, 45, 0, 8, 1);
		setAnimation(1, STATE_FLAME_BREATHING, 30, 60, 9, 1);
		setAnimation(2, STATE_FLAME_BREATHING, 30, -60, 9, 1);
		setAnimation(3, STATE_FLAME_BREATHING, 50, 90, 8, 1);
		setAnimation(4, STATE_FLAME_BREATHING, 50, -90, 8, 1);
		setAnimation(5, STATE_FLAME_BREATHING, -10, 90, 9, 1);
		setAnimation(6, STATE_FLAME_BREATHING, -10, -90, 9, 1);
		
		setAnimation(0, STATE_FLAME_ENDING, 60, 0, 7, 0);
		setAnimation(1, STATE_FLAME_ENDING, 10, 45, 9, 0);
		setAnimation(2, STATE_FLAME_ENDING, 10, -45, 9, 0);
		setAnimation(3, STATE_FLAME_ENDING, 50, 90, 8, 0);
		setAnimation(4, STATE_FLAME_ENDING, 50, -90, 8, 0);
		setAnimation(5, STATE_FLAME_ENDING, -10, 90, 9, 0);
		setAnimation(6, STATE_FLAME_ENDING, -10, -90, 9, 0);
		
		setAnimation(0, STATE_BITE_BEGINNING, -5, 60, 5, 0.25f);
		setAnimation(1, STATE_BITE_BEGINNING, -10, 60, 9, 0.25f);
		setAnimation(2, STATE_BITE_BEGINNING, -10, -60, 9, 0.25f);
		
		setAnimation(0, STATE_BITE_READY, -5, 60, 5, 1);
		setAnimation(1, STATE_BITE_READY, -10, 60, 9, 1);
		setAnimation(2, STATE_BITE_READY, -10, -60, 9, 1);
		
		setAnimation(0, STATE_BITE_BITING, -5, -30, 5, 0.2F);
		setAnimation(1, STATE_BITE_BITING, -10, -30, 5, 0.2F);
		setAnimation(2, STATE_BITE_BITING, -10, 30, 5, 0.2F);
		
		setAnimation(0, STATE_BITE_ENDING, 60, 0, 7, 0);
		setAnimation(1, STATE_BITE_ENDING, -10, 60, 9, 0);
		setAnimation(2, STATE_BITE_ENDING, -10, -60, 9, 0);
		
		setAnimation(0, STATE_MORTAR_BEGINNING, 50, 0, 8, 0.75F);
		setAnimation(1, STATE_MORTAR_BEGINNING, 30, 45, 9, 0.75F);
		setAnimation(2, STATE_MORTAR_BEGINNING, 30, -45, 9, 0.75F);
		setAnimation(3, STATE_MORTAR_BEGINNING, 50, 90, 8, 0.75F);
		setAnimation(4, STATE_MORTAR_BEGINNING, 50, -90, 8, 0.75F);
		setAnimation(5, STATE_MORTAR_BEGINNING, -10, 90, 9, 0.75F);
		setAnimation(6, STATE_MORTAR_BEGINNING, -10, -90, 9, 0.75F);
		
		setAnimation(0, STATE_MORTAR_LAUNCH, 45, 0, 8, 1);
		setAnimation(1, STATE_MORTAR_LAUNCH, 30, 60, 9, 1);
		setAnimation(2, STATE_MORTAR_LAUNCH, 30, -60, 9, 1);
		setAnimation(3, STATE_MORTAR_LAUNCH, 50, 90, 8, 1);
		setAnimation(4, STATE_MORTAR_LAUNCH, 50, -90, 8, 1);
		setAnimation(5, STATE_MORTAR_LAUNCH, -10, 90, 9, 1);
		setAnimation(6, STATE_MORTAR_LAUNCH, -10, -90, 9, 1);
		
		setAnimation(0, STATE_MORTAR_ENDING, 60, 0, 7, 0);
		setAnimation(1, STATE_MORTAR_ENDING, 10, 45, 9, 0);
		setAnimation(2, STATE_MORTAR_ENDING, 10, -45, 9, 0);
		setAnimation(3, STATE_MORTAR_ENDING, 50, 90, 8, 0);
		setAnimation(4, STATE_MORTAR_ENDING, 50, -90, 8, 0);
		setAnimation(5, STATE_MORTAR_ENDING, -10, 90, 9, 0);
		setAnimation(6, STATE_MORTAR_ENDING, -10, -90, 9, 0);

		setAnimation(0, STATE_DYING, -20, 0, 7, 0);
		setAnimation(1, STATE_DYING, -20, 60, 9, 0);
		setAnimation(2, STATE_DYING, -20, -60, 9, 0);
		setAnimation(3, STATE_DYING, -20, 90, 8, 0);
		setAnimation(4, STATE_DYING, -20, -90, 8, 0);
		setAnimation(5, STATE_DYING, -10, 90, 9, 0);
		setAnimation(6, STATE_DYING, -10, -90, 9, 0);
		
		setAnimation(0, STATE_DEAD, 0, 179, 4, 0);
		setAnimation(1, STATE_DEAD, 0, 179, 4, 0);
		setAnimation(2, STATE_DEAD, 0, -180, 4, 0);
		setAnimation(3, STATE_DEAD, 0, 179, 4, 0);
		setAnimation(4, STATE_DEAD, 0, -180, 4, 0);
		setAnimation(5, STATE_DEAD, 0, 179, 4, 0);
		setAnimation(6, STATE_DEAD, 0, -180, 4, 0);
		
		setAnimation(0, STATE_BORN, 60, 0, 7, 0);
		setAnimation(1, STATE_BORN, 10, 60, 9, 0);
		setAnimation(2, STATE_BORN, 10, -60, 9, 0);
		setAnimation(3, STATE_BORN, 50, 90, 8, 0);
		setAnimation(4, STATE_BORN, 50, -90, 8, 0);
		setAnimation(5, STATE_BORN, -10, 90, 9, 0);
		setAnimation(6, STATE_BORN, -10, -90, 9, 0);
		
		setAnimation(0, STATE_ROAR_START, 60, 0, 7, 0.25F);
		setAnimation(1, STATE_ROAR_START, 10, 60, 9, 0.25F);
		setAnimation(2, STATE_ROAR_START, 10, -60, 9, 0.25F);
		setAnimation(3, STATE_ROAR_START, 50, 90, 8, 0.25F);
		setAnimation(4, STATE_ROAR_START, 50, -90, 8, 0.25F);
		setAnimation(5, STATE_ROAR_START, -10, 90, 9, 0.25F);
		setAnimation(6, STATE_ROAR_START, -10, -90, 9, 0.25F);

		setAnimation(0, STATE_ROAR_RAWR, 60, 0, 9, 1);
		setAnimation(1, STATE_ROAR_RAWR, 10, 60, 11, 1);
		setAnimation(2, STATE_ROAR_RAWR, 10, -60, 11, 1);
		setAnimation(3, STATE_ROAR_RAWR, 50, 90, 10, 1);
		setAnimation(4, STATE_ROAR_RAWR, 50, -90, 10, 1);
		setAnimation(5, STATE_ROAR_RAWR, -10, 90, 11, 1);
		setAnimation(6, STATE_ROAR_RAWR, -10, -90, 11, 1);


	}

	protected void setAnimation(int head, int state, float xRotation, float yRotation, float neckLength, float mouthOpen)
	{
		this.stateXRotations[head][state] = xRotation;
		this.stateYRotations[head][state] = yRotation;
		this.stateNeckLength[head][state] = neckLength;
		this.stateMouthOpen[head][state] = mouthOpen; // this doesn't really need to be set per-head, more per-state
	}
	
	public EntityTFHydraNeck[] getNeckArray()
	{
		return new EntityTFHydraNeck[] { necka, neckb, neckc, neckd, necke };
	}

	/**
	 * Called once per tick as part of the hydra entity update loop.
	 */
	public void onUpdate() {
		
		// neck updates
		necka.onUpdate();
		neckb.onUpdate();
		neckc.onUpdate();
		neckd.onUpdate();
		necke.onUpdate();
		
		// check if the head is here
		if (headEntity == null)
		{
			headEntity = findNearbyHead("head" + headNum);
		}
		
		// adjust for difficulty
		setDifficultyVariables();

		if (headEntity != null)
		{
			// make sure this is set up
			headEntity.width = headEntity.height = this.isActive() ? 4.0F : 1.0F;
	    	
	    	// only actually do these things on the server
	    	if (!hydraObj.worldObj.isRemote)
	    	{
	    		advanceRespawnCounter();
	    		
	    		advanceHeadState();
	    		
	        	setHeadPosition();
	        	
	        	setHeadFacing();
	        	
	        	executeAttacks();
	    	}
	    	else
	    	{
	    		clientAnimateHeadDeath();
	    	}
	    	
	    	setNeckPosition();
	    	
	    	addMouthParticles();

	    	playSounds();

		}
	}

	/**
	 * If we are dead, decrement the respawn counter and respawn when it is empty.
	 */
	protected void advanceRespawnCounter() {
		if (this.currentState == STATE_DEAD && respawnCounter > -1)
		{
			if (--this.respawnCounter <= 0)
			{
				this.setNextState(STATE_BORN);
				this.damageTaken = 0;
				this.endCurrentAction();
				this.respawnCounter = -1;
			}
		}
	}

	/**
	 * We need to animate head death on the client
	 */
	protected void clientAnimateHeadDeath() {
    	// this will start the animation
    	if (headEntity.getState() == HydraHeadContainer.STATE_DYING)
    	{
    		// several things, like head visibility animate off this
    		this.headEntity.deathTime++;

    		// make explosion particles and stuff
    		if (headEntity.deathTime > 0)
    		{
    			if (headEntity.deathTime < 20)
    			{
    				doExplosionOn(headEntity, true);
    			}
    			else if (headEntity.deathTime < 30)
    			{
    				doExplosionOn(necka, false);
    			}
    			else if (headEntity.deathTime < 40)
    			{
    				doExplosionOn(neckb, false);
    			}
    			else if (headEntity.deathTime < 50)
    			{
    				doExplosionOn(neckc, false);
    			}
    			else if (headEntity.deathTime < 60)
    			{
    				doExplosionOn(neckd, false);
    			}
    			else if (headEntity.deathTime < 70)
    			{
    				doExplosionOn(necke, false);
    			}
    		}

    		// turn necks red
    		necka.hurtTime = 20;
    		neckb.hurtTime = 20;
    		neckc.hurtTime = 20;
    		neckd.hurtTime = 20;
    		necke.hurtTime = 20;
    		
    	}
    	else
    	{
    		this.headEntity.deathTime = 0;
    		this.headEntity.setHealth((float) this.headEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
    	}		
	}

	private void doExplosionOn(EntityTFHydraPart part, boolean large) {
        for (int i = 0; i < 10; ++i)
        {
            double var8 = part.getRNG().nextGaussian() * 0.02D;
            double var4 = part.getRNG().nextGaussian() * 0.02D;
            double var6 = part.getRNG().nextGaussian() * 0.02D;
            String particle = large && part.getRNG().nextInt(5) == 0 ? "largeexplode" : "explode";
            part.worldObj.spawnParticle(particle, part.posX + part.getRNG().nextFloat() * part.width * 2.0F - part.width, part.posY + part.getRNG().nextFloat() * part.height, part.posZ + part.getRNG().nextFloat() * part.width * 2.0F - part.width, var8, var4, var6);
        }
		
	}

	/**
	 * Move the state counter along
	 * This is only called on the server, the client just receives the resulting data
	 */
	protected void advanceHeadState() {
		// check head state
		if (++ticksProgress >= this.ticksNeeded)
		{
			int myNext;
			
			// advance state
			if (this.nextState == NEXT_AUTOMATIC) 
			{
				myNext = nextStates[this.currentState];
				if (myNext != currentState)
				{
					//System.out.println("Automatically advancing head " + this.headNum + " to state " + myNext);
					
					// when returning from a secondary attack, no attack cooldown
					if (this.isSecondaryAttacking && myNext == HydraHeadContainer.STATE_ATTACK_COOLDOWN)
					{
						this.isSecondaryAttacking = false;
						myNext = HydraHeadContainer.STATE_IDLE;
					}
				}
			}
			else
			{
				myNext = this.nextState;
				this.nextState = NEXT_AUTOMATIC;
				//System.out.println("Manually advancing head " + this.headNum + " to state " + myNext);
			}
			
			this.ticksNeeded = this.ticksProgress = stateDurations[myNext];
			this.ticksProgress = 0;
			
			this.prevState = this.currentState;
			this.currentState = myNext;
		}
		// set datawatcher so client can properly animate
		if (headEntity.getState() != this.currentState)
		{
			headEntity.setState(this.currentState);
		}

	}

	/**
	 * Set the direction the head is facing.  It mostly faces the targetEntity, but that depends on the state
	 */
	protected void setHeadFacing() {
		if (this.currentState == HydraHeadContainer.STATE_BITE_READY)
		{
			// face target within certain constraints
			this.faceEntity(targetEntity, 5F, hydraObj.getVerticalFaceSpeed());
			
			// head 0 and 1 max yaw
			float biteMaxYaw = -60;
			float biteMinYaw = -90;
			
			if (headNum == 2)
			{
				// head 2 max/min yaw
				biteMaxYaw = 60;
				biteMinYaw = 90;
			}

			float yawOffOffset = MathHelper.wrapAngleTo180_float(headEntity.rotationYaw - hydraObj.renderYawOffset);
			
			//System.out.println("biting head yaw = " +  yawOffOffset);
			
			if (yawOffOffset > biteMaxYaw)
			{
				headEntity.rotationYaw = hydraObj.renderYawOffset + biteMaxYaw;
			}
			if (yawOffOffset < biteMinYaw)
			{
				headEntity.rotationYaw = hydraObj.renderYawOffset + biteMinYaw;
			}
			
			// make the target vector be a point off in the distance in the direction we're already facing
			Vec3d look = this.headEntity.getLookVec();
			double distance = 16.0D;
			this.targetX = headEntity.posX + look.xCoord * distance;
			this.targetY = headEntity.posY + 1.5 + look.yCoord * distance;
			this.targetZ = headEntity.posZ + look.zCoord * distance;
		}
		else if (this.currentState == HydraHeadContainer.STATE_BITE_BITING || this.currentState == HydraHeadContainer.STATE_BITE_ENDING)
		{
			this.faceEntity(targetEntity, 5F, hydraObj.getVerticalFaceSpeed());
			headEntity.rotationPitch += Math.PI / 4;
		}
		else if (this.currentState == HydraHeadContainer.STATE_ROAR_RAWR)
		{
			// keep facing target vector, don't move
			this.faceVec(this.targetX, this.targetY, this.targetZ, 10F, hydraObj.getVerticalFaceSpeed());
		}
		else if (this.currentState == HydraHeadContainer.STATE_FLAME_BREATHING || (this.currentState == HydraHeadContainer.STATE_FLAME_BEGINNING))
		{
			// move flame breath slowly towards the player
			moveTargetCoordsTowardsTargetEntity(FLAME_BREATH_TRACKING_SPEED);
			// face the target coordinates
			this.faceVec(this.targetX, this.targetY, this.targetZ, 5F, hydraObj.getVerticalFaceSpeed());
		}
		else {
			if (this.isActive())
			{
				if (this.targetEntity != null)
				{
					// watch the target entity
					this.faceEntity(targetEntity, 5F, hydraObj.getVerticalFaceSpeed());
				}
				else
				{
					// while idle, look where the body is looking?
					faceIdle(1.5F, hydraObj.getVerticalFaceSpeed());
				}
			}
		}
	}

	/**
	 * Slowly track our target coords towards the target entity
	 */
	protected void moveTargetCoordsTowardsTargetEntity(double distance)
	{
		if (this.targetEntity != null)
		{
			Vec3d vect = Vec3d.createVectorHelper(this.targetEntity.posX - this.targetX, this.targetEntity.posY - this.targetY, this.targetEntity.posZ - this.targetZ);

			vect = vect.normalize();

			this.targetX += vect.xCoord * distance;
			this.targetY += vect.yCoord * distance;
			this.targetZ += vect.zCoord * distance;
		}
	}

	/**
	 * During certain states, animate particles coming out of the mouth
	 */
	protected void addMouthParticles() {
		Vec3d vector = headEntity.getLookVec();
	
		double dist = 3.5;
		double px = headEntity.posX + vector.xCoord * dist;
		double py = headEntity.posY + 1 + vector.yCoord * dist;
		double pz = headEntity.posZ + vector.zCoord * dist;
		
		//hydraObj.worldObj.spawnParticle("mobSpell", px, py, pz, 0.3, 0.9, 0.1);

		if (headEntity.getState() == STATE_FLAME_BEGINNING)
		{
			headEntity.worldObj.spawnParticle("flame", px + headEntity.getRNG().nextDouble() - 0.5, py + headEntity.getRNG().nextDouble() - 0.5, pz + headEntity.getRNG().nextDouble() - 0.5, 0, 0, 0);
			headEntity.worldObj.spawnParticle("smoke", px + headEntity.getRNG().nextDouble() - 0.5, py + headEntity.getRNG().nextDouble() - 0.5, pz + headEntity.getRNG().nextDouble() - 0.5, 0, 0, 0);
		}
		
		if (headEntity.getState() == STATE_FLAME_BREATHING)
		{
			Vec3d look = headEntity.getLookVec();
			for (int i = 0; i < 5; i++)
			{
				double dx = look.xCoord;
				double dy = look.yCoord;
				double dz = look.zCoord;
				
				double spread = 5 + headEntity.getRNG().nextDouble() * 2.5;
				double velocity = 1.0 + headEntity.getRNG().nextDouble();
				
				// spread flame
		        dx += headEntity.getRNG().nextGaussian() * 0.007499999832361937D * spread;
		        dy += headEntity.getRNG().nextGaussian() * 0.007499999832361937D * spread;
		        dz += headEntity.getRNG().nextGaussian() * 0.007499999832361937D * spread;
		        dx *= velocity;
		        dy *= velocity;
		        dz *= velocity;
				
				TwilightForestMod.proxy.spawnParticle(headEntity.worldObj, "largeflame", px, py, pz, dx, dy, dz);
			}
		}
		
		if (headEntity.getState() == STATE_BITE_BEGINNING || headEntity.getState() == STATE_BITE_READY)
		{
			headEntity.worldObj.spawnParticle("splash", px + headEntity.getRNG().nextDouble() - 0.5, py + headEntity.getRNG().nextDouble() - 0.5, pz + headEntity.getRNG().nextDouble() - 0.5, 0, 0, 0);
		}
		
		if (headEntity.getState() == STATE_MORTAR_BEGINNING)
		{
			headEntity.worldObj.spawnParticle("largesmoke", px + headEntity.getRNG().nextDouble() - 0.5, py + headEntity.getRNG().nextDouble() - 0.5, pz + headEntity.getRNG().nextDouble() - 0.5, 0, 0, 0);
		}
	}

	/**
	 * Generate any sounds we need to go along with head animations
	 */
	protected void playSounds() {
		if (headEntity.getState() == STATE_FLAME_BREATHING && headEntity.ticksExisted % 5 == 0)
		{
			// fire breathing!
			headEntity.worldObj.playSoundEffect(headEntity.posX + 0.5, headEntity.posY + 0.5, headEntity.posZ + 0.5, "mob.ghast.fireball", 0.5F + headEntity.getRNG().nextFloat(), headEntity.getRNG().nextFloat() * 0.7F + 0.3F);
		}
		if (headEntity.getState() == STATE_ROAR_RAWR)
		{
			headEntity.worldObj.playSoundEffect(headEntity.posX + 0.5, headEntity.posY + 0.5, headEntity.posZ + 0.5, TwilightForestMod.ID + ":mob.hydra.roar", 1.25F, headEntity.getRNG().nextFloat() * 0.3F + 0.7F);
		}
		if (headEntity.getState() == STATE_BITE_READY && this.ticksProgress == 60)
		{
			headEntity.worldObj.playSoundEffect(headEntity.posX + 0.5, headEntity.posY + 0.5, headEntity.posZ + 0.5, TwilightForestMod.ID + ":mob.hydra.warn", 2.0F, headEntity.getRNG().nextFloat() * 0.3F + 0.7F);
		}
		if (headEntity.getState() == STATE_IDLE)
		{
			this.didRoar = false;
		}
	}

	/**
	 * Put the neck entities into the proper place, based on the body and head positions
	 * Done on both server and client.
	 */
	protected void setNeckPosition() {
		// set neck positions
		Vec3d vector = null;
		float neckRotation = 0;
		
		if (headNum == 0)
		{
			vector = Vec3d.createVectorHelper(0, 3, -1);
			neckRotation = 0;
		}
		if (headNum == 1)
		{
			vector = Vec3d.createVectorHelper(-1, 3, 3);
			neckRotation = 90;
		}
		if (headNum == 2)
		{
			vector = Vec3d.createVectorHelper(1, 3, 3);
			neckRotation = -90;
		}
		if (headNum == 3)
		{
			vector = Vec3d.createVectorHelper(-1, 3, 3);
			neckRotation = 135;
		}
		if (headNum == 4)
		{
			vector = Vec3d.createVectorHelper(1, 3, 3);
			neckRotation = -135;
		}
		
		if (headNum == 5)
		{
			vector = Vec3d.createVectorHelper(-1, 3, 5);
			neckRotation = 135;
		}
		if (headNum == 6)
		{
			vector = Vec3d.createVectorHelper(1, 3, 5);
			neckRotation = -135;
		}
		
		
		vector.rotateAroundY((-(hydraObj.renderYawOffset + neckRotation) * 3.141593F) / 180F);
		setNeckPositon(hydraObj.posX + vector.xCoord, hydraObj.posY + vector.yCoord, hydraObj.posZ + vector.zCoord, hydraObj.renderYawOffset, 0);
	}

	/**
	 * Position the head object appropriately
	 * This is only called on the server, the client just receives the resulting data
	 */
	protected void setHeadPosition() {
		// set head positions
		Vec3d vector;
		double dx, dy, dz;

		// head1 is above
		// head2 is to the right
		// head3 is to the left
		setupStateDurations();
		setupStateRotations(); // temporary, for debugging
		
		float neckLength = getCurrentNeckLength();
		float xRotation = getCurrentHeadXRotation();
		float yRotation = getCurrentHeadYRotation();
		
		
		float periodX = (headNum == 0 || headNum == 3) ? 20F : ((headNum == 1 || headNum == 4) ? 5.0f : 7.0F);
		float periodY = (headNum == 0 || headNum == 4) ? 10F : ((headNum == 1 || headNum == 6) ? 6.0f : 5.0F);
		
		float xSwing = MathHelper.sin(hydraObj.ticksExisted / periodX) * 3.0F;
		float ySwing = MathHelper.sin(hydraObj.ticksExisted / periodY) * 5.0F;
		
		if (!this.isActive())
		{
			xSwing = ySwing = 0;
		}
		
		vector = Vec3d.createVectorHelper(0, 0, neckLength); // -53 = 3.3125
		vector.rotateAroundX((xRotation * 3.141593F + xSwing) / 180F);
		vector.rotateAroundY((-(hydraObj.renderYawOffset + yRotation + ySwing) * 3.141593F) / 180F);

		dx = hydraObj.posX + vector.xCoord;
		dy = hydraObj.posY + vector.yCoord + 3;
		dz = hydraObj.posZ + vector.zCoord;

		headEntity.setPosition(dx, dy, dz);
		headEntity.setMouthOpen(getCurrentMouthOpen());
		
	}

	/**
	 * Execute whatever effect we need.  Deal damage with the bite, the breath weapon, or launch mortars when appropriate
	 */
	@SuppressWarnings("unchecked")
	protected void executeAttacks()
	{
		if (this.currentState == HydraHeadContainer.STATE_MORTAR_LAUNCH && this.ticksProgress % 10 == 0)
		{
			Entity lookTarget = getHeadLookTarget();

			if (lookTarget != null && (lookTarget instanceof EntityTFHydraPart || lookTarget instanceof EntityDragonPart))
			{
				// stop hurting yourself!
				this.endCurrentAction();
			}
			else
			{
				EntityTFHydraMortar mortar = new EntityTFHydraMortar(headEntity.worldObj, this.headEntity);

				Vec3d vector = headEntity.getLookVec();

				double dist = 3.5;
				double px = headEntity.posX + vector.xCoord * dist;
				double py = headEntity.posY + 1 + vector.yCoord * dist;
				double pz = headEntity.posZ + vector.zCoord * dist;

				mortar.setLocationAndAngles(px, py, pz, 0, 0);
				
				// launch blasting mortars if the player is hiding
				if (this.targetEntity != null && !headEntity.canEntityBeSeen(this.targetEntity))
				{
					//System.out.println("Launching blasting mortar at hiding target.");
					mortar.setToBlasting();
				}

				headEntity.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)headEntity.posX, (int)headEntity.posY, (int)headEntity.posZ, 0);

				headEntity.worldObj.spawnEntityInWorld(mortar);
			}
		}
		if (headEntity.getState() == STATE_BITE_BITING)
		{
	        // damage nearby things
	        List<Entity> nearbyList = headEntity.worldObj.getEntitiesWithinAABBExcludingEntity(headEntity, headEntity.boundingBox.expand(0.0, 1.0, 0.0));

	        for (Entity nearby : nearbyList)
	        {
	        	if (nearby instanceof EntityLivingBase && !(nearby instanceof EntityTFHydraPart) && !(nearby instanceof EntityTFHydra) && !(nearby instanceof EntityDragonPart))
	        	{
	        		// bite it!
	        		nearby.attackEntityFrom(DamageSource.causeMobDamage(hydraObj), BITE_DAMAGE);
	        	}
	        }
		}
		
		if (headEntity.getState() == STATE_FLAME_BREATHING)
		{
	        Entity target = getHeadLookTarget();

	        if (target != null)
	        {
	        	if (target instanceof EntityTFHydraPart || target instanceof EntityDragonPart)
	        	{
	        		// stop hurting yourself!
	        		this.endCurrentAction();
	        		
	        		//System.out.println("Stopping breath from head " + headNum + " because I am about to hit" + target);
	        	}
	        	else if (!target.isImmuneToFire() && target.attackEntityFrom(DamageSource.inFire, FLAME_DAMAGE))
	        	{
	        		target.setFire(FLAME_BURN_FACTOR);
	        	}
	        }
		}

	}

	
	protected void setDifficultyVariables() {
		if (this.hydraObj.worldObj.difficultySetting != EnumDifficulty.HARD)
		{
			HydraHeadContainer.FLAME_BREATH_TRACKING_SPEED = 0.04D;

		}
		else
		{
			// hard mode!
			HydraHeadContainer.FLAME_BREATH_TRACKING_SPEED = 0.1D;  // higher is harder
		}
		
	}

	/**
	 * What, if anything, is the head currently looking at?
	 */
	@SuppressWarnings("unchecked")
	private Entity getHeadLookTarget() {
		Entity pointedEntity = null;
		double range = 30.0D;
        Vec3d srcVec = Vec3d.createVectorHelper(headEntity.posX, headEntity.posY + 1.0, headEntity.posZ);
        Vec3d lookVec = headEntity.getLook(1.0F);
        Vec3d destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
        float var9 = 3.0F;
        List<Entity> possibleList = headEntity.worldObj.getEntitiesWithinAABBExcludingEntity(headEntity, headEntity.boundingBox.addCoord(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range).expand(var9, var9, var9));
        double hitDist = 0;

        for (Entity possibleEntity : possibleList)
        {
            if (possibleEntity.canBeCollidedWith() && possibleEntity != headEntity && possibleEntity != necka && possibleEntity != neckb && possibleEntity != neckc)
            {
                float borderSize = possibleEntity.getCollisionBorderSize();
                AxisAlignedBB collisionBB = possibleEntity.boundingBox.expand((double)borderSize, (double)borderSize, (double)borderSize);
                RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);

                if (collisionBB.isVecInside(srcVec))
                {
                    if (0.0D < hitDist || hitDist == 0.0D)
                    {
                        pointedEntity = possibleEntity;
                        hitDist = 0.0D;
                    }
                }
                else if (interceptPos != null)
                {
                    double possibleDist = srcVec.distanceTo(interceptPos.hitVec);

                    if (possibleDist < hitDist || hitDist == 0.0D)
                    {
                        pointedEntity = possibleEntity;
                        hitDist = possibleDist;
                    }
                }
            }
        }
		return pointedEntity;
	}
	
	/**
	 * What state should we go to next?
	 */
	public void setNextState(int next)
	{
		this.nextState = next;
	}
	
	/**
	 * 
	 */
	public void endCurrentAction()
	{
		this.ticksProgress = this.ticksNeeded;
	}
	
	/**
	 * Search for nearby heads with the string as their name
	 */
	@SuppressWarnings("unchecked")
	private EntityTFHydraHead findNearbyHead(String string)
	{
		
		List<EntityTFHydraHead> nearbyHeads = hydraObj.worldObj.getEntitiesWithinAABB(EntityTFHydraHead.class, AxisAlignedBB.getBoundingBox(hydraObj.posX, hydraObj.posY, hydraObj.posZ, hydraObj.posX + 1, hydraObj.posY + 1, hydraObj.posZ + 1).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityTFHydraHead nearbyHead : nearbyHeads)
		{
			
			if (nearbyHead.getPartName().equals(string)) {
				nearbyHead.hydraObj = hydraObj;
				
				//System.out.println("Reconnected hydra with head named " + string + " in world " + hydraObj.worldObj);
				
				return nearbyHead;
			}
			
		}

		//System.out.println("Did not find head named " + string);
		return null;
	}
	
	/**
	 * The current neck length depends on the current state, and how far along are we from the previous state
	 * Other factors include which head it is.
	 */
	protected float getCurrentNeckLength()
	{
		float prevLength = stateNeckLength[this.headNum][this.prevState];
		float curLength = stateNeckLength[this.headNum][this.currentState];
		float progress = (float)ticksProgress / (float)ticksNeeded;

		return prevLength + (curLength - prevLength) * progress;
	}
	
	/**
	 * The current x rotation depends on the current state, and how far along are we from the previous state
	 * Other factors include which head it is.
	 */
	protected float getCurrentHeadXRotation()
	{
		float prevRotation = stateXRotations[this.headNum][this.prevState];
		float currentRotation = stateXRotations[this.headNum][this.currentState];
		float progress = (float)ticksProgress / (float)ticksNeeded;

		return prevRotation + (currentRotation - prevRotation) * progress;
	}
	

	protected float getCurrentHeadYRotation()
	{
		float prevRotation = stateYRotations[this.headNum][this.prevState];
		float currentRotation = stateYRotations[this.headNum][this.currentState];
		float progress = (float)ticksProgress / (float)ticksNeeded;

		return prevRotation + (currentRotation - prevRotation) * progress;
	}


	protected float getCurrentMouthOpen()
	{
		float prevOpen = stateMouthOpen[this.headNum][this.prevState];
		float curOpen = stateMouthOpen[this.headNum][this.currentState];
		float progress = (float)ticksProgress / (float)ticksNeeded;

		return prevOpen + (curOpen - prevOpen) * progress;
	}

	/**
	 * Sets the four neck positions ranging from the start position to the head position.
	 * 
	 * @param hi head index
	 * @param startX
	 * @param startY
	 * @param startZ
	 * @param startYaw
	 * @param startPitch
	 */
	protected void setNeckPositon(double startX, double startY, double startZ, float startYaw, float startPitch) {

		double endX = headEntity.posX;
		double endY = headEntity.posY;
		double endZ = headEntity.posZ;
		float endYaw = headEntity.rotationYaw;
		float endPitch = headEntity.rotationPitch;
		
        for (; startYaw - endYaw < -180F; endYaw -= 360F) { }
        for (; startYaw - endYaw >= 180F; endYaw += 360F) { }
        for (; startPitch - endPitch < -180F; endPitch -= 360F) { }
        for (; startPitch - endPitch >= 180F; endPitch += 360F) { }

		
		// translate the end position back 1 unit
		if (endPitch > 0) 
		{
			// if we are looking down, don't raise the first neck position, it looks weird
			Vec3d vector = Vec3d.createVectorHelper(0, 0, -1.0);
			vector.rotateAroundY((-endYaw * 3.141593F) / 180F);
			endX += vector.xCoord;
			endY += vector.yCoord;
			endZ += vector.zCoord;
		}
		else
		{
			// but if we are looking up, lower it or it goes through the crest
			Vec3d vector = headEntity.getLookVec();
			float dist = 1.0f;
			
			endX -= vector.xCoord * dist;
			endY -= vector.yCoord * dist;
			endZ -= vector.zCoord * dist;

		}
		

		float factor = 0F;

		factor = 0.00F; 
		necka.setPosition(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
		necka.rotationYaw = endYaw + (startYaw - endYaw) * factor;
		necka.rotationPitch = endPitch + (startPitch - endPitch) * factor;

		factor = 0.25F; 
		neckb.setPosition(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
		neckb.rotationYaw = endYaw + (startYaw - endYaw) * factor;
		neckb.rotationPitch = endPitch + (startPitch - endPitch) * factor;

		factor = 0.50F; 
		neckc.setPosition(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
		neckc.rotationYaw = endYaw + (startYaw - endYaw) * factor;
		neckc.rotationPitch = endPitch + (startPitch - endPitch) * factor;

		factor = 0.75F; 
		neckd.setPosition(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
		neckd.rotationYaw = endYaw + (startYaw - endYaw) * factor;
		neckd.rotationPitch = endPitch + (startPitch - endPitch) * factor;

		factor = 1.0F; 
		necke.setPosition(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
		necke.rotationYaw = endYaw + (startYaw - endYaw) * factor;
		necke.rotationPitch = endPitch + (startPitch - endPitch) * factor;

	}
     
	/**
	 * Face a vector in front of the hydra entity.  
	 * This is used to give the heads something to look at when we don't have an acutal target.
	 */
	protected void faceIdle(float yawConstraint, float pitchConstraint) {
		//headEntity.rotationPitch = hydraObj.rotationPitch;
		//headEntity.rotationYaw = hydraObj.rotationYaw;
		
		float angle = (((hydraObj.rotationYaw) * 3.141593F) / 180F);
		float distance = 30.0F;

    	double dx = hydraObj.posX - MathHelper.sin(angle) * distance;
    	double dy = hydraObj.posY + 3.0;
    	double dz = hydraObj.posZ + MathHelper.cos(angle) * distance;
		
        faceVec(dx, dy, dz, yawConstraint, pitchConstraint);
	}

	/**
	 * Face this head towards an entity
	 */
	public void faceEntity(Entity entity, float yawConstraint, float pitchConstraint) {
		double yTarget;
        if (entity instanceof EntityLivingBase)
        {
        	EntityLivingBase entityliving = (EntityLivingBase)entity;
            yTarget = entityliving.posY + entityliving.getEyeHeight();
        }
        else
        {
        	yTarget = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2D;
        }
        
        faceVec(entity.posX, yTarget, entity.posZ, yawConstraint, pitchConstraint);
        
        // let's just set the target vector here
        this.targetX = entity.posX;
        this.targetY = entity.posY;
        this.targetZ = entity.posZ;
	}
	
	/**
	 * Face this head towards a specific Vector
	 */
	public void faceVec(double xCoord, double yCoord, double zCoord, float yawConstraint, float pitchConstraint) {
		double xOffset = xCoord - headEntity.posX;
		double zOffset = zCoord - headEntity.posZ;
		double yOffset = (headEntity.posY + 1.0) - yCoord;

		double distance = MathHelper.sqrt_double(xOffset * xOffset + zOffset * zOffset);
		float xyAngle = (float)((Math.atan2(zOffset, xOffset) * 180D) / Math.PI) - 90F;
		float zdAngle = (float)(-((Math.atan2(yOffset, distance) * 180D) / Math.PI));
		headEntity.rotationPitch = -updateRotation(headEntity.rotationPitch, zdAngle, pitchConstraint);
		headEntity.rotationYaw = updateRotation(headEntity.rotationYaw, xyAngle, yawConstraint);
        
//        System.out.println("Updating head " + head + " with rotationPitch " + head.rotationPitch);
//        System.out.println("Updating head " + head + " with rotationYaw " + head.rotationYaw);

	}
	
    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation(float current, float intended, float increment)
    {
        float delta = MathHelper.wrapAngleTo180_float(intended - current);

        if (delta > increment)
        {
            delta = increment;
        }

        if (delta < -increment)
        {
            delta = -increment;
        }

        return  MathHelper.wrapAngleTo180_float(current + delta);
    }
	
	public Entity getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(Entity targetEntity) {
		this.targetEntity = targetEntity;
	}

	public void setHurtTime(int hurtTime) {
		if (headEntity != null) {
			headEntity.hurtTime = hurtTime;
		}
		necka.hurtTime = hurtTime;
		neckb.hurtTime = hurtTime;
		neckc.hurtTime = hurtTime;
		neckd.hurtTime = hurtTime;
		necke.hurtTime = hurtTime;
	}
	
	/**
	 * At certain times, some of the heads are "dead" and hidden
	 */
	public boolean shouldRenderHead()
	{
		return this.headEntity.getState() != STATE_DEAD && this.headEntity.deathTime < 20;
	}

	/**
	 * Same with the neck parts
	 */
	public boolean shouldRenderNeck(int neckNum)
	{
		int time = 30 + 10 * neckNum; 
		return this.headEntity.getState() != STATE_DEAD && this.headEntity.deathTime < time;
	}

	/**
	 * Is this head active, that is, not dying or dead?
	 */
	public boolean isActive() {
		return this.currentState != STATE_DYING && this.currentState != STATE_DEAD;
	}

	/**
	 * Add to our damage taken counter
	 */
	public void addDamage(float damageAmount) {
		this.damageTaken += damageAmount;
	}

	/**
	 * How much damage has this head taken?
	 */
	public int getDamageTaken() {
		return this.damageTaken;
	}

	public void setRespawnCounter(int count) {
		this.respawnCounter = count;
	}

}
