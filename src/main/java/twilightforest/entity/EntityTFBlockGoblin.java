package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAIThrowSpikeBlock;

import java.util.List;
import java.util.UUID;

public class EntityTFBlockGoblin extends EntityMob implements IEntityMultiPart {
	private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "speedPenalty", -0.25D, 0)).setSaved(false);


	public static final ResourceLocation LOOT_TABLE = TwilightForestMod.prefix("entities/block_goblin");
	private static final float CHAIN_SPEED = 16F;
	private static final DataParameter<Byte> DATA_CHAINLENGTH = EntityDataManager.createKey(EntityTFBlockGoblin.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> DATA_CHAINPOS = EntityDataManager.createKey(EntityTFBlockGoblin.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> IS_THROWING = EntityDataManager.createKey(EntityTFBlockGoblin.class, DataSerializers.BOOLEAN);

	private int recoilCounter;
	private float chainAngle;

	private float chainMoveLength;

	public final EntityTFSpikeBlock block = new EntityTFSpikeBlock(this);
	public final EntityTFGoblinChain chain1 = new EntityTFGoblinChain(this);
	public final EntityTFGoblinChain chain2 = new EntityTFGoblinChain(this);
	public final EntityTFGoblinChain chain3 = new EntityTFGoblinChain(this);

	private final Entity[] partsArray = new Entity[]{block, chain1, chain2, chain3};

	public EntityTFBlockGoblin(World world) {
		super(world);
		setSize(0.9F, 1.4F);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityTNTPrimed.class, 2.0F, 0.8F, 1.5F));
		this.tasks.addTask(4, new EntityAIThrowSpikeBlock(this, this.block));
		this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0F, false));
		this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(DATA_CHAINLENGTH, (byte) 0);
		dataManager.register(DATA_CHAINPOS, (byte) 0);
		dataManager.register(IS_THROWING, false);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(11.0D);
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.78F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.REDCAP_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.REDCAP_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.REDCAP_AMBIENT;
	}

	@Override
	public ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}

	/**
	 * How high is the chain
	 */
	public double getChainYOffset() {
		return 1.5 - this.getChainLength() / 4.0;
	}

	/**
	 * Get the block & chain position
	 */
	public Vec3d getChainPosition() {
		return this.getChainPosition(getChainAngle(), getChainLength());
	}

	/**
	 * Get the block & chain position
	 */
	public Vec3d getChainPosition(float angle, float distance) {
		double dx = Math.cos((angle) * Math.PI / 180.0D) * distance;
		double dz = Math.sin((angle) * Math.PI / 180.0D) * distance;

		return new Vec3d(this.posX + dx, this.posY + this.getChainYOffset(), this.posZ + dz);
	}

	public boolean isSwingingChain() {
		return this.isSwingInProgress || (this.getAttackTarget() != null && this.recoilCounter == 0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		swingArm(EnumHand.MAIN_HAND);
		return false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		block.onUpdate();
		chain1.onUpdate();
		chain2.onUpdate();
		chain3.onUpdate();

		if (recoilCounter > 0) {
			recoilCounter--;
		}


		chainAngle += CHAIN_SPEED;
		chainAngle %= 360;

		if (!this.world.isRemote) {
			dataManager.set(DATA_CHAINLENGTH, (byte) Math.floor(getChainLength() * 127.0F));
			dataManager.set(DATA_CHAINPOS, (byte) Math.floor(getChainAngle() / 360.0F * 255.0F));
		} else {
			// synch chain pos if it's wrong
			if (Math.abs(this.chainAngle - this.getChainAngle()) > CHAIN_SPEED * 2) {
				//FMLLog.info("Fixing chain pos on client");
				this.chainAngle = getChainAngle();
			}
		}

		if (this.chainMoveLength > 0) {

			Vec3d blockPos = this.getThrowPos();

			double sx2 = this.posX;
			double sy2 = this.posY + this.height - 0.1;
			double sz2 = this.posZ;

			double ox2 = sx2 - blockPos.x;
			double oy2 = sy2 - blockPos.y - 0.25F;
			double oz2 = sz2 - blockPos.z;

			//When the thrown chainblock exceeds a certain distance, return to the owner
			if (this.chainMoveLength >= 6.0F || !this.isEntityAlive()) {
				this.setThrowing(false);
			}

			this.chain1.setPosition(sx2 - ox2 * 0.25, sy2 - oy2 * 0.25, sz2 - oz2 * 0.25);
			this.chain2.setPosition(sx2 - ox2 * 0.5, sy2 - oy2 * 0.5, sz2 - oz2 * 0.5);
			this.chain3.setPosition(sx2 - ox2 * 0.85, sy2 - oy2 * 0.85, sz2 - oz2 * 0.85);

			this.block.setPosition(sx2 - ox2 * 1.0, sy2 - oy2 * 1.0, sz2 - oz2 * 1.0);
		} else {


			// set block position
			Vec3d blockPos = this.getChainPosition();
			this.block.setPosition(blockPos.x, blockPos.y, blockPos.z);
			this.block.rotationYaw = getChainAngle();

			// interpolate chain position
			double sx = this.posX;
			double sy = this.posY + this.height - 0.1;
			double sz = this.posZ;

			double ox = sx - blockPos.x;
			double oy = sy - blockPos.y - (block.height / 3D);
			double oz = sz - blockPos.z;

			this.chain1.setPosition(sx - ox * 0.4, sy - oy * 0.4, sz - oz * 0.4);
			this.chain2.setPosition(sx - ox * 0.5, sy - oy * 0.5, sz - oz * 0.5);
			this.chain3.setPosition(sx - ox * 0.6, sy - oy * 0.6, sz - oz * 0.6);

		}

		// collide things with the block
		if (!world.isRemote && (this.isThrowing() || this.isSwingingChain())) {
			this.applyBlockCollisions(this.block);
		}
		this.chainMove();
	}

	private Vec3d getThrowPos() {
		Vec3d vec3d = this.getLook(1.0F);
		return new Vec3d(this.posX + vec3d.x * this.chainMoveLength, this.posY + this.getEyeHeight(), this.posZ + vec3d.z * this.chainMoveLength);
	}

	private void chainMove() {

		if (this.isThrowing()) {

			this.chainMoveLength = MathHelper.clamp(this.chainMoveLength + 0.5F, 0.0F, 6.0F);
		} else {

			this.chainMoveLength = MathHelper.clamp(this.chainMoveLength - 1.5F, 0.0F, 6.0F);

		}
	}

	public float getChainMoveLength() {
		return chainMoveLength;
	}

	/**
	 * Check if the block is colliding with any nearby entities
	 */
	protected void applyBlockCollisions(Entity collider) {
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(collider, collider.getEntityBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));

		for (Entity entity : list) {
			if (entity.canBePushed()) {
				applyBlockCollision(collider, entity);
			}
		}
	}

	/**
	 * Do the effect where the block hits something
	 */
	protected void applyBlockCollision(Entity collider, Entity collided) {
		if (collided != this) {
			collided.applyEntityCollision(collider);
			if (collided instanceof EntityLivingBase) {
				if (super.attackEntityAsMob(collided)) {
					collided.motionY += 0.4;
					this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
					this.recoilCounter = 40;
					if (this.isThrowing()) {
						this.setThrowing(false);
					}
				}

			}
		}
	}

	public boolean isThrowing() {
		return this.dataManager.get(IS_THROWING);
	}

	public void setThrowing(boolean isThrowing) {
		this.dataManager.set(IS_THROWING, isThrowing);
	}

	/**
	 * Angle between 0 and 360 to place the chain at
	 */
	private float getChainAngle() {
		if (!this.world.isRemote) {
			return this.chainAngle;
		} else {
			return (dataManager.get(DATA_CHAINPOS) & 0xFF) / 255.0F * 360.0F;
		}
	}

	/**
	 * Between 0.0F and 2.0F, how long is the chain right now?
	 */
	private float getChainLength() {
		if (!this.world.isRemote) {
			if (isSwingingChain()) {
				return 0.9F;
			} else {
				return 0.3F;
			}
		} else {
			return (dataManager.get(DATA_CHAINLENGTH) & 0xFF) / 127.0F;
		}
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
		return false;
	}

	/**
	 * We need to do this for the bounding boxes on the parts to become active
	 */
	@Override
	public Entity[] getParts() {
		return partsArray;
	}
}
