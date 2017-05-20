package twilightforest.entity;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import twilightforest.TFSounds;
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
	private int slideTime;
	private EnumFacing moveDirection = null;

	public EntityTFSlideBlock(World world) {
		super(world);
        this.preventEntitySpawning = true;
        this.entityCollisionReduction = 1F;
        this.setSize(0.98F, 0.98F);
        //this.yOffset = this.height / 2.0F;
	}


	public EntityTFSlideBlock(World world, double x, double y, double z, IBlockState state) {
		super(world);

        this.myState = state;
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

	private void determineMoveDirection() {
		this.moveDirection = null;
		
        BlockPos pos = new BlockPos(this);
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
		
        if (myState.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.X) {
        	// horizontal blocks will go up or down if there is a block on one side and air on the other
        	if (!this.world.isAirBlock(up) && this.world.isAirBlock(down)) {
        	    this.moveDirection = EnumFacing.DOWN;
        	} else if (!this.world.isAirBlock(down) && this.world.isAirBlock(up)) {
                this.moveDirection = EnumFacing.UP;
        	} else if (!this.world.isAirBlock(south) && this.world.isAirBlock(north)) { // then try Z
                this.moveDirection = EnumFacing.NORTH;
        	} else if (!this.world.isAirBlock(north) && this.world.isAirBlock(south)) {
                this.moveDirection = EnumFacing.SOUTH;
        	} else if (this.world.isAirBlock(down)) { // if no wall, travel towards open air
                this.moveDirection = EnumFacing.DOWN;
        	} else if (this.world.isAirBlock(up)) {
                this.moveDirection = EnumFacing.UP;
        	} else if (this.world.isAirBlock(north)) {
                this.moveDirection = EnumFacing.NORTH;
        	} else if (this.world.isAirBlock(south)) {
                this.moveDirection = EnumFacing.SOUTH;
        	}
        } else if (myState.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.Z) {
        	// horizontal blocks will go up or down if there is a block on one side and air on the other
        	if (!this.world.isAirBlock(up) && this.world.isAirBlock(down)) {
        		this.moveDirection = EnumFacing.DOWN;
        	} else if (!this.world.isAirBlock(down) && this.world.isAirBlock(up)) {
        		this.moveDirection = EnumFacing.UP;
        	} else if (!this.world.isAirBlock(east) && this.world.isAirBlock(west)) { // then try X
        		this.moveDirection = EnumFacing.WEST;
        	} else if (!this.world.isAirBlock(west) && this.world.isAirBlock(east)) {
        		this.moveDirection = EnumFacing.EAST;
        	} else if (this.world.isAirBlock(down)) { // if no wall, travel towards open air
        		this.moveDirection = EnumFacing.DOWN;
        	} else if (this.world.isAirBlock(up)) {
        		this.moveDirection = EnumFacing.UP;
        	} else if (this.world.isAirBlock(west)) {
        		this.moveDirection = EnumFacing.WEST;
        	} else if (this.world.isAirBlock(east)) {
        		this.moveDirection = EnumFacing.EAST;
        	}
        } else if (myState.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.Y) {
        	// vertical blocks priority is -x, +x, -z, +z
        	if (!this.world.isAirBlock(east) && this.world.isAirBlock(west)) {
        		this.moveDirection = EnumFacing.WEST;
        	} else if (!this.world.isAirBlock(west) && this.world.isAirBlock(east)) {
        		this.moveDirection = EnumFacing.EAST;
        	} else if (!this.world.isAirBlock(south) && this.world.isAirBlock(north)) {
        		this.moveDirection = EnumFacing.NORTH;
        	} else if (!this.world.isAirBlock(north) && this.world.isAirBlock(south)) {
        		this.moveDirection = EnumFacing.SOUTH;
        	} else if (this.world.isAirBlock(west)) { // if no wall, travel towards open air
        		this.moveDirection = EnumFacing.WEST;
        	} else if (this.world.isAirBlock(east)) {
        		this.moveDirection = EnumFacing.EAST;
        	} else if (this.world.isAirBlock(north)) {
        		this.moveDirection = EnumFacing.NORTH;
        	} else if (this.world.isAirBlock(south)) {
        		this.moveDirection = EnumFacing.SOUTH;
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
	            this.motionX += moveDirection.getFrontOffsetX() * 0.03999999910593033D;
	            this.motionY += moveDirection.getFrontOffsetY() * 0.03999999910593033D;
	            this.motionZ += moveDirection.getFrontOffsetZ() * 0.03999999910593033D;
	            this.move(this.motionX, this.motionY, this.motionZ);
            }
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
            
            if (!this.world.isRemote)
            {
                if (this.slideTime % 5 == 0) {
                    playSound(TFSounds.SLIDER, 1.0F, 0.9F + (this.rand.nextFloat() * 0.4F));
                }

                BlockPos pos = new BlockPos(this);

                if (this.slideTime == 1)
                {
                    if (this.world.getBlockState(pos) != this.myState)
                    {
                        this.setDead();
                        return;
                    }

                    this.world.setBlockToAir(pos);
                }
                
                if (this.slideTime == WARMUP_TIME + 40) {
                	this.motionX = 0;
                	this.motionY = 0;
                	this.motionZ = 0;

                	this.moveDirection = this.moveDirection.getOpposite();
                }

                if (this.isCollided || this.isStopped())
                {
                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= 0.699999988079071D;

                    this.setDead();

                    if (this.world.canBlockBePlaced(myState.getBlock(), pos, true, EnumFacing.UP, null, null)) {
                        world.setBlockState(pos, myState);
                    } else {
                        this.entityDropItem(new ItemStack(myState.getBlock(), 1, myState.getBlock().damageDropped(myState)), 0.0F);
                    }
                }
                else if (this.slideTime > 100 && (pos.getY() < 1 || pos.getY() > 256) || this.slideTime > 600)
                {
                    this.entityDropItem(new ItemStack(this.myState.getBlock(), 1, this.myState.getBlock().damageDropped(this.myState)), 0.0F);
                    this.setDead();
                }
                
                // push things out and damage them
                this.damageKnockbackEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
            }
        }
    }

    private void damageKnockbackEntities(List<Entity> par1List)
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

    @Override
    public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
    {
        return null;
    }

    private boolean isStopped() {
		return moveDirection == null;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
		Block b = Block.REGISTRY.getObject(new ResourceLocation(nbtTagCompound.getString("TileID")));
		int meta = nbtTagCompound.getByte("Meta");
        this.myState = b.getStateFromMeta(meta);
		this.slideTime = nbtTagCompound.getInteger("Time");
		if (nbtTagCompound.hasKey("Direction")) {
		    moveDirection = EnumFacing.getFront(nbtTagCompound.getByte("Direction"));
        }
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("TileID", myState.getBlock().getRegistryName().toString());
        nbtTagCompound.setByte("Meta", (byte)this.myState.getBlock().getMetaFromState(myState));
        nbtTagCompound.setInteger("Time", this.slideTime);
        if (moveDirection != null) {
            nbtTagCompound.setByte("Direction", (byte) moveDirection.getIndex());
        }
    }
	
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(Block.getStateId(myState));
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
        myState = Block.getStateById(additionalData.readInt());
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

    public IBlockState getBlockState() {
		return myState;
    }
}
