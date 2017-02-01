package twilightforest.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.entity.ai.EntityAITFRedcapPlantTNT;
import twilightforest.item.TFItems;

import java.util.UUID;

public class EntityTFRedcapSapper extends EntityTFRedcap {

	private static final AttributeModifier ARMOR_BOOST =
			new AttributeModifier(UUID.fromString("8a0e8db7-20d3-49f5-bdd7-e10dc509d9fc"), "RedCap sapper permanent armor boost", 2, 0);

	public EntityTFRedcapSapper(World world) {
		super(world);
		
        //texture = TwilightForestMod.MODEL_DIR + "redcapsapper.png";
        
        this.setTntLeft(3);

        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(TFItems.ironwoodBoots));
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.ironwoodPick, 1));
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(4, new EntityAITFRedcapPlantTNT(this)); // plant TNT
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_BOOST);
    }

	@Override
	public ItemStack getPick()
	{
		return new ItemStack(TFItems.ironwoodPick);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			// are we in a level 2 hill?
			int chunkX = MathHelper.floor(posX) >> 4;
			int chunkZ = MathHelper.floor(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, world) == TFFeature.hill2) {
				// award level 2 hill cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHill2);
			}

		}
	}
}
