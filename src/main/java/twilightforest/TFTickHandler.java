package twilightforest;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.BlockTFPortal;
import twilightforest.block.TFBlocks;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.WorldProviderTwilightForest;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

import twilightforest.TwilightForestMod;

/**
 * This class listens for ticks in the world.  If the player is near a diamond in the water, this class attempts to open a portal.
 * 
 * @author Ben
 *
 */
public class TFTickHandler 
{

	public Item portalItem = null;


    /**
     * On the tick, we check for eligible portals
	 */
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {

		EntityPlayer player = event.player;
		World world = player.worldObj;

		if (!world.isRemote && event.phase == TickEvent.Phase.END && world.getWorldTime() % 20 == 0) {
		// check for portal creation, at least if it's not disabled
		if (!TwilightForestMod.disablePortalCreation) {
			// skip non admin players when the option is on
			if (TwilightForestMod.adminOnlyPortals) {
				try {
					//if (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(player.getCommandSenderName().toString())) {
					if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile())) {
						// reduce range to 4.0 when the option is on
						checkForPortalCreation(player, world, 4.0F);

					}
				} catch (NoSuchMethodError ex) {
					// stop checking admin
					FMLLog.warning("[TwilightForest] Could not determine op status for adminOnlyPortals option, ignoring option.");
					TwilightForestMod.adminOnlyPortals = false;
				}
			} else {
				// normal check, no special options
				checkForPortalCreation(player, world, 32.0F);
				
			}
		}

		if (world.getGameRules().getGameRuleBooleanValue(TwilightForestMod.ENFORCED_PROGRESSION_RULE) && world.provider instanceof WorldProviderTwilightForest) {
		// check the player for being in a forbidden progression area, only every 20 ticks
			if (!player.capabilities.isCreativeMode) {
				checkBiomeForProgression(player, world);
			}
		
		// check and send nearby forbidden structures, every 100 ticks or so
		if (world.getWorldTime() % 100 == 0) {
				if (!player.capabilities.isCreativeMode) {
					checkForLockedStructuresSendPacket(player, world);
				} else {
					sendAllClearPacket(world, player);
				}
		}
		}
		}
	}
	
	private void sendStructureProtectionPacket(World world, EntityPlayer player, StructureBoundingBox sbb) {
		// send packet
		FMLProxyPacket message = TFGenericPacketHandler.makeStructureProtectionPacket(sbb);
		if (player instanceof EntityPlayerMP) {
			TwilightForestMod.genericChannel.sendTo(message, (EntityPlayerMP) player);
			//System.out.println("Sent structure protection");
		}// else {
			//System.err.println("Can't sent packet to player, not an EntityPlayerMP");
		//}
	}
	
	private void sendAllClearPacket(World world, EntityPlayer player) {
		FMLProxyPacket message = TFGenericPacketHandler.makeStructureProtectionClearPacket();
		if (player instanceof EntityPlayerMP) {
			TwilightForestMod.genericChannel.sendTo(message, (EntityPlayerMP) player);
			//System.out.println("Sent structure all clear");
		}// else {
			//System.err.println("Can't sent packet to player, not an EntityPlayerMP");
		//}
	}

	private boolean checkForLockedStructuresSendPacket(EntityPlayer player, World world) {
		ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)world.provider).getChunkProvider();
		
		int px = MathHelper.floor_double(player.posX);
		int py = MathHelper.floor_double(player.posY);
		int pz = MathHelper.floor_double(player.posZ);
		
		if (chunkProvider != null && chunkProvider.isBlockNearFullStructure(px, pz, 100)) {
			
			StructureBoundingBox fullSBB = chunkProvider.getFullSBBNear(px, pz, 100);
			
			TFFeature nearFeature = TFFeature.getFeatureForRegion(fullSBB.getCenterX() >> 4, fullSBB.getCenterZ() >> 4, world);
			
			if (!nearFeature.hasProtectionAura || nearFeature.doesPlayerHaveRequiredAchievement(player)) {
				sendAllClearPacket(world, player);
				return false;
			} else {
				sendStructureProtectionPacket(world, player, fullSBB);
				return true;
			}

			
		} else {
			return false;
		}
	}

	@SubscribeEvent
	public void tickStart(ItemTossEvent event) {
		cpw.mods.fml.common.FMLLog.info("ItemTossEvent Tick");
	}



	private void checkForPortalCreation(EntityPlayer player, World world, float rangeToCheck) {
		// make sure we are allowed to make a portal in this dimension
		if (world != null && player != null 
				&& (world.provider.dimensionId == 0 || world.provider.dimensionId == TwilightForestMod.dimensionID 
				|| TwilightForestMod.allowPortalsInOtherDimensions)) 
		{
			@SuppressWarnings("unchecked")
			List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(rangeToCheck, rangeToCheck, rangeToCheck));
			
			// do we have the item set?  if not, can we set it?
			/*if (this.portalItem == null) {

				
				
			}*/
			
			// check to see if someone's thrown the portal item into the water
			for (EntityItem entityItem : itemList) 
			{
				if (entityItem.getEntityItem().getItem() == portalItem && world.isMaterialInBB(entityItem.boundingBox, Material.water))
				{
					//System.out.println("There is a diamond in the water");
	
					// make sparkles in the area
					Random rand = new org.bogdang.modifications.random.XSTR();
					for (int k = 0; k < 2; k++)
					{
						double d = rand.nextGaussian() * 0.02D;
						double d1 = rand.nextGaussian() * 0.02D;
						double d2 = rand.nextGaussian() * 0.02D;
	
						world.spawnParticle("spell", entityItem.posX, entityItem.posY + 0.2, entityItem.posZ, d, d1, d2);
					}
	
					// try to make a portal
					int dx = MathHelper.floor_double(entityItem.posX);
					int dy = MathHelper.floor_double(entityItem.posY);
					int dz = MathHelper.floor_double(entityItem.posZ);
	
					if (((BlockTFPortal)TFBlocks.portal).tryToCreatePortal(world, dx, dy, dz)) {
						player.triggerAchievement(TFAchievementPage.twilightPortal);
					}
				}
			}
		}
	}

	/**
	 * Check what biome the player is in, and see if current progression allows that biome.  If not, take appropriate action
	 */
	private void checkBiomeForProgression(EntityPlayer player, World world) {
		BiomeGenBase currentBiome = world.getBiomeGenForCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));
		
		if (currentBiome instanceof TFBiomeBase) {
			TFBiomeBase tfBiome = (TFBiomeBase)currentBiome;
			
			boolean dangerousBiome = !tfBiome.doesPlayerHaveRequiredAchievement(player);
			if (dangerousBiome) {
				tfBiome.enforceProgession(player, world);
			}
		}
	}


}
