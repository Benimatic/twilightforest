package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import twilightforest.block.BlockTFMazestone;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.MazestoneVariant;
import twilightforest.item.TFItems;

public class EntityTFMazeSlime extends EntitySlime
{

	private String slimeParticleString;

	public EntityTFMazeSlime(World par1World) {
		super(par1World);
        //texture = TwilightForestMod.MODEL_DIR + "mazeslime.png";
        this.setSlimeSize(1 << (1 + this.rand.nextInt(2)));
	}

    @Override
	protected EntitySlime createInstance()
    {
        return new EntityTFMazeSlime(this.world);
    }
    
    @Override
	public void setSlimeSize(int par1)
    {
        super.setSlimeSize(par1);
        this.experienceValue = par1 + 3;
    }

    @Override
	public boolean getCanSpawnHere()
    {
    	return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.checkNoEntityCollision(getEntityBoundingBox())
        		&& this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
        		&& !this.world.containsAnyLiquid(getEntityBoundingBox()) && this.isValidLightLevel();
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        int size = this.getSlimeSize();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D * size * size);
    }

    @Override
	protected boolean canDamagePlayer()
    {
        return true;
    }

    @Override
	protected int getAttackStrength()
    {
        return super.getAttackStrength() + 3;
    }

    @Override
    protected boolean spawnCustomParticles() {
        // [VanillaCopy] from super onUpdate with own particles
        int i = getSlimeSize();
        for (int j = 0; j < i * 8; ++j)
        {
            float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
            float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
            World world = this.world;
            // EnumParticleTypes enumparticletypes = this.getParticleType();
            double d0 = this.posX + (double)f2;
            double d1 = this.posZ + (double)f3;
            IBlockState state = TFBlocks.mazestone.getDefaultState().withProperty(BlockTFMazestone.VARIANT, MazestoneVariant.BRICK);
            world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
        return true;
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        int var1 = MathHelper.floor(this.posX);
        int var2 = MathHelper.floor(this.boundingBox.minY);
        int var3 = MathHelper.floor(this.posZ);

        if (this.world.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int var4 = this.world.getBlockLightValue(var1, var2, var3);

            if (this.world.isThundering())
            {
                int var5 = this.world.skylightSubtracted;
                this.world.skylightSubtracted = 10;
                var4 = this.world.getBlockLightValue(var1, var2, var3);
                this.world.skylightSubtracted = var5;
            }

            return var4 <= this.rand.nextInt(8);
        }
    }

    @Override
	protected float getSoundVolume()
    {
    	// OH MY GOD, SHUT UP
        return 0.1F * this.getSlimeSize();
    }
    
    
    @Override
	protected void dropRareDrop(int par1)
    {
        this.dropItem(TFItems.charmOfKeeping1, 1);
    }

}
