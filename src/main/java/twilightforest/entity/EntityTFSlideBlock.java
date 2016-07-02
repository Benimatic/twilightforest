package twilightforest.entity;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;

public class EntityTFSlideBlock extends Entity implements IEntityAdditionalSpawnData {

	private static final int WARMUP_TIME = 20;
	private IBlockState myState;
	private short slideTime;
	private boolean canDropItem = true;
	
	private float moveX;
	private float moveY;
	private float moveZ;
	
	public EntityTFSlideBlock(World world) {
		super(world);
        this.preventEntitySpawning = true;
        this.entityCollisionReduction = 1F;
        this.setSize(0.98F, 0.98F);
        //this.yOffset = this.height / 2.0F;
	}


	public EntityTFSlideBlock(World world, double x, double y, double z, Block block, int meta) {
		super(world);

        this.myBlock = block;
        this.myMeta = meta;
        this.preventEntitySpawning = true;
        this.entityCollisionReduction = 1F;
        this.setSize(0.98F, 0.98F);
        //this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        
        this.determineMoveDirection();
	}

	/**
	 * Called when creating the entity to determine which direction the block will slide.  The logic here is a little tricky
	 */
	private void determineMoveDirection() {
		this.moveX = 0F;
		this.moveY = 0F;
		this.moveZ = 0F;
		
        BlockPos pos = new BlockPos(this);
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
		
        if ((this.myMeta & 12) == 4) {
        	// horizontal blocks will go up or down if there is a block on one side and air on the other
        	if (!this.worldObj.isAirBlock(up) && this.worldObj.isAirBlock(down)) {
        		this.moveY = -1F;
        	} else if (!this.worldObj.isAirBlock(down) && this.worldObj.isAirBlock(up)) {
        		this.moveY = 1F;
        	} else if (!this.worldObj.isAirBlock(south) && this.worldObj.isAirBlock(north)) { // then try Z
        		this.moveZ = -1F;
        	} else if (!this.worldObj.isAirBlock(north) && this.worldObj.isAirBlock(south)) {
        		this.moveZ = 1F;
        	} else if (this.worldObj.isAirBlock(down)) { // if no wall, travel towards open air
        		this.moveY = -1F;
        	} else if (this.worldObj.isAirBlock(up)) {
        		this.moveY = 1F;
        	} else if (this.worldObj.isAirBlock(north)) {
        		this.moveZ = -1F;
        	} else if (this.worldObj.isAirBlock(south)) {
        		this.moveZ = 1F;
        	}
        } else if ((this.myMeta & 12) == 8) {
        	// horizontal blocks will go up or down if there is a block on one side and air on the other
        	if (!this.worldObj.isAirBlock(up) && this.worldObj.isAirBlock(down)) {
        		this.moveY = -1F;
        	} else if (!this.worldObj.isAirBlock(down) && this.worldObj.isAirBlock(up)) {
        		this.moveY = 1F;
        	} else if (!this.worldObj.isAirBlock(east) && this.worldObj.isAirBlock(west)) { // then try X
        		this.moveX = -1F;
        	} else if (!this.worldObj.isAirBlock(west) && this.worldObj.isAirBlock(east)) {
        		this.moveX = 1F;
        	} else if (this.worldObj.isAirBlock(down)) { // if no wall, travel towards open air
        		this.moveY = -1F;
        	} else if (this.worldObj.isAirBlock(up)) {
        		this.moveY = 1F;
        	} else if (this.worldObj.isAirBlock(west)) {
        		this.moveX = -1F;
        	} else if (this.worldObj.isAirBlock(east)) {
        		this.moveX = 1F;
        	}
        } else if ((this.myMeta & 12) == 0) {
        	// vertical blocks priority is -x, +x, -z, +z
        	if (!this.worldObj.isAirBlock(east) && this.worldObj.isAirBlock(west)) {
        		this.moveX = -1F;
        	} else if (!this.worldObj.isAirBlock(west) && this.worldObj.isAirBlock(east)) {
        		this.moveX = 1F;
        	} else if (!this.worldObj.isAirBlock(south) && this.worldObj.isAirBlock(north)) {
        		this.moveZ = -1F;
        	} else if (!this.worldObj.isAirBlock(north) && this.worldObj.isAirBlock(south)) {
        		this.moveZ = 1F;
        	} else if (this.worldObj.isAirBlock(west)) { // if no wall, travel towards open air
        		this.moveX = -1F;
        	} else if (this.worldObj.isAirBlock(east)) {
        		this.moveX = 1F;
        	} else if (this.worldObj.isAirBlock(north)) {
        		this.moveZ = -1F;
        	} else if (this.worldObj.isAirBlock(south)) {
        		this.moveZ = 1F;
        	}
        	
        }

	}

	@Override
	protected void entityInit() { }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }
    
    @Override
    public void onUpdate()
    {
        if (this.myState == null || this.myState.getMaterial() == Material.AIR)
        {
            this.setDead();
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.slideTime;
            // start moving after warmup
            if (this.slideTime > WARMUP_TIME) {
	            this.motionX += this.moveX * 0.03999999910593033D;
	            this.motionY += this.moveY * 0.03999999910593033D;
	            this.motionZ += this.moveZ * 0.03999999910593033D;
	            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
            
            // sound
            if (this.slideTime % 5 == 0) {
            	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, TwilightForestMod.ID + ":random.slider", 1.0F, 0.9F + (this.rand.nextFloat() * 0.4F));
            }


            if (!this.worldObj.isRemote)
            {
                BlockPos pos = new BlockPos(this);

                if (this.slideTime == 1)
                {
                    if (this.worldObj.getBlockState(pos) != this.myState)
                    {
                        this.setDead();
                        return;
                    }

                    this.worldObj.setBlockToAir(pos);
                }
                
                // if we have not hit anything after 2 seconds of movement, reverse direction
                if (this.slideTime == WARMUP_TIME + 40) {
                	this.motionX = 0;
                	this.motionY = 0;
                	this.motionZ = 0;
                	
                	this.moveX *= -1F;
                	this.moveY *= -1F;
                	this.moveZ *= -1F;
                }

                //System.out.println("Status! onGround = " + this.onGround + ", isCollided = " + this.isCollided + ", hv = " + this.isCollidedHorizontally + ", " + this.isCollidedVertically);
                
                if (this.isCollided || this.isStopped())
                {
                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= 0.699999988079071D;

                    this.setDead();

                    if (this.worldObj.canPlaceEntityOnSide(this.myBlock, bx, by, bz, true, 1, (Entity)null, (ItemStack)null) && this.worldObj.setBlock(bx, by, bz, this.myBlock, this.myMeta, 3))
                    {
                    	// successfully set block
                    }
                    else if (this.canDropItem )
                    {
                        this.entityDropItem(new ItemStack(this.myBlock, 1, this.myBlock.damageDropped(this.myMeta)), 0.0F);
                    }
                }
                else if (this.slideTime > 100 && !this.worldObj.isRemote && (by < 1 || by > 256) || this.slideTime > 600)
                {
                    if (this.canDropItem)
                    {
                        this.entityDropItem(new ItemStack(this.myBlock, 1, this.myBlock.damageDropped(this.myMeta)), 0.0F);
                    }

                    this.setDead();
                }
                
                // push things out and damage them
                this.damageKnockbackEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox), this);
            }
        }
    }

    /**
     * Pushes all entities inside the list out and damages them
     */
    private void damageKnockbackEntities(List<Entity> par1List, Entity me)
    {
     	for (Entity entity : par1List)
    	{
    		if (entity instanceof EntityLivingBase)
    		{
    			entity.attackEntityFrom(DamageSource.generic, 5);
    			
    			double kx = (this.posX - entity.posX) * 2.0;
    			double kz = (this.posZ - entity.posZ) * 2.0;
    			
    			((EntityLivingBase) entity).knockBack(this, 5, kx, kz);
    		}
    	}
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
    {
        return null;
    }

    private boolean isStopped() {
		return this.moveX == 0 && this.moveY == 0 && this.moveZ == 0;
	}


	/**
     * Return whether this entity should be rendered as on fire.
     */
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
		this.myBlock = Block.getBlockById(nbtTagCompound.getInteger("TileID"));
		this.myMeta = nbtTagCompound.getByte("Meta");
		this.slideTime = nbtTagCompound.getShort("Time");
		this.moveX = nbtTagCompound.getFloat("MoveX");
		this.moveY = nbtTagCompound.getFloat("MoveY");
		this.moveZ = nbtTagCompound.getFloat("MoveZ");
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("TileID", Block.getIdFromBlock(this.myBlock));
        nbtTagCompound.setByte("Meta", (byte)this.myMeta);
        nbtTagCompound.setShort("Time", this.slideTime);
        nbtTagCompound.setFloat("MoveX", this.moveX);
        nbtTagCompound.setFloat("MoveY", this.moveY);
        nbtTagCompound.setFloat("MoveZ", this.moveZ);
    }
	
	public Block getBlock() {
		return this.myBlock;
	}
	
	public int getMeta() {
		return this.myMeta;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		int blockData = Block.getIdFromBlock(this.myBlock) + (this.myMeta << 16);
		
		buffer.writeInt(blockData);
		
		//System.out.println("Wrote additional spawn data as " + blockData);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		int blockData = additionalData.readInt();
		
		this.myBlock = Block.getBlockById(blockData & 65535);
		this.myMeta = blockData >> 16;
		
		//System.out.println("Read additional spawn data as " + blockData + " so my block is " + this.myBlock);

	}

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean isPushedByWater()
    {
        return false;
    }
}
