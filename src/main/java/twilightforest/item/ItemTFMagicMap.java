package twilightforest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.MapData.MapInfo;
import twilightforest.TFFeature;
import twilightforest.TFMagicMapData;
import twilightforest.TFMapPacketHandler;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.world.TFBiomeProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFMagicMap extends ItemMap
{
    public static final String STR_ID = "magicmap";

    @SideOnly(Side.CLIENT)
    public static TFMagicMapData getMPMapData(int par0, World par1World)
    {
        String mapName = STR_ID + "_" + par0;
        TFMagicMapData mapData = (TFMagicMapData)par1World.loadItemData(TFMagicMapData.class, mapName);

        if (mapData == null)
        {
            mapData = new TFMagicMapData(mapName);
            par1World.setItemData(mapName, mapData);
        }

        return mapData;
    }

    @Override
	public TFMagicMapData getMapData(ItemStack par1ItemStack, World par2World)
    {
        String mapName = STR_ID + "_" + par1ItemStack.getItemDamage();
    	TFMagicMapData mapData = (TFMagicMapData)par2World.loadItemData(TFMagicMapData.class, mapName);

        if (mapData == null && !par2World.isRemote)
        {
            par1ItemStack.setItemDamage(par2World.getUniqueDataId(STR_ID));
            mapName = STR_ID + "_" + par1ItemStack.getItemDamage();
            mapData = new TFMagicMapData(mapName);
            mapData.xCenter = par2World.getWorldInfo().getSpawnX();
            mapData.zCenter = par2World.getWorldInfo().getSpawnZ();
            mapData.scale = 4;
            mapData.dimension = par2World.provider.getDimension();
            mapData.markDirty();
            par2World.setItemData(mapName, mapData);
        }

        return mapData;
    }

    public void updateMapData(World par1World, Entity par2Entity, TFMagicMapData par3MapData)
    {
        if (par1World.provider.getDimension() == par3MapData.dimension && par2Entity instanceof EntityPlayer)
        {
            short xSize = 128;
            short zSize = 128;
            int scaleFactor = 1 << par3MapData.scale;
            int xCenter = par3MapData.xCenter;
            int zCenter = par3MapData.zCenter;
            int xDraw = MathHelper.floor(par2Entity.posX - (double)xCenter) / scaleFactor + xSize / 2;
            int zDraw = MathHelper.floor(par2Entity.posZ - (double)zCenter) / scaleFactor + zSize / 2;
            int drawSize = 512 / scaleFactor;
//            int drawSize = 1024 / scaleFactor;

            MapInfo mapInfo = par3MapData.func_82568_a((EntityPlayer)par2Entity);
            ++mapInfo.field_82569_d;

            for (int xStep = xDraw - drawSize + 1; xStep < xDraw + drawSize; ++xStep)
            {
                if ((xStep & 15) == (mapInfo.field_82569_d & 15))
                {
                    int highNumber = 255;
                    int lowNumber = 0;
                    
                    for (int zStep = zDraw - drawSize - 1; zStep < zDraw + drawSize; ++zStep)
                    {
                        if (xStep >= 0 && zStep >= -1 && xStep < xSize && zStep < zSize)
                        {
                            int xOffset = xStep - xDraw;
                            int zOffset = zStep - zDraw;
                            boolean var20 = xOffset * xOffset + zOffset * zOffset > (drawSize - 2) * (drawSize - 2);
                            int xDraw2 = (xCenter / scaleFactor + xStep - xSize / 2) * scaleFactor;
                            int zDraw2 = (zCenter / scaleFactor + zStep - zSize / 2) * scaleFactor;
                            int[] biomeFrequencies = new int[256];
                            int zStep2;
                            int xStep2;
                            
                            
                            int biomeID;
                            
                            
                            for (xStep2 = 0; xStep2 < scaleFactor; ++xStep2)
                            {
                            	for (zStep2 = 0; zStep2 < scaleFactor; ++zStep2)
                            	{
                            		biomeID = par1World.getBiome(xDraw2 + xStep2, zDraw2 + zStep2).biomeID;

                                    biomeFrequencies[biomeID]++;
                                    
                                    // make rivers and streams 3x more prominent
                                    if (biomeID == Biome.river.biomeID || biomeID == TFBiomeBase.stream.biomeID) {
                                    	biomeFrequencies[biomeID] += 2;
                                    }
                                    // add in TF features
                                    if (par1World.getBiomeProvider() instanceof TFBiomeProvider)
                                    {
                                    	TFBiomeProvider tfManager  = (TFBiomeProvider) par1World.getBiomeProvider();
                                    	
                                    	if (tfManager.isInFeatureChunk(par1World, xDraw2 + xStep2, zDraw2 + zStep2) && zStep >= 0 && xOffset * xOffset + zOffset * zOffset < drawSize * drawSize)
                                    	{
                                        	par3MapData.addFeatureToMap(TFFeature.getNearestFeature((xDraw2 + xStep2) >> 4, (zDraw2 + zStep2) >> 4, par1World), xDraw2, zDraw2);
                                    	}
                                    }
                                	                                    
//                                  // mark features we find into the mapdata, provided they are within our draw area
//                                  if (biomeID == TFBiomeBase.majorFeature.biomeID && zStep >= 0 && xOffset * xOffset + zOffset * zOffset < drawSize * drawSize) {
//                                  	par3MapData.addFeatureToMap(TFFeature.getNearestFeature((xDraw2 + xStep2) >> 4, (zDraw2 + zStep2) >> 4, par1World), xDraw2, zDraw2);
////                                  	biomeFrequencies[biomeID] += 4096; // don't bother, now the icon will show
//                                  }
                                  
//                                    // mark features we find into the mapdata, provided they are within our draw area
//                                    if (biomeID == TFBiomeBase.minorFeature.biomeID) {
//                                    	biomeFrequencies[biomeID] += 4096; // don't bother, now the icon will show
//                                    }
                            	}
                            }
                            
                            // figure out which color is the most prominent and make that one appear on the map
                            byte biomeIDToShow = 0;
                            int highestFrequency = 0;
                            
                            for (int i = 0; i < 256; i++) {
                                if (biomeFrequencies[i] > highestFrequency)
                                {
                                	biomeIDToShow = (byte)i;
                                    highestFrequency = biomeFrequencies[i];
                                }
                            }
                            
                            // increase biomeID by one so we can properly show oceans
                            biomeIDToShow++;
                            
                            if (zStep >= 0 && xOffset * xOffset + zOffset * zOffset < drawSize * drawSize && (!var20 || (xStep + zStep & 1) != 0))
                            {
                                byte existingColor = par3MapData.colors[xStep + zStep * xSize];
 
                                if (existingColor != biomeIDToShow)
                                {
                                    if (highNumber > zStep)
                                    {
                                        highNumber = zStep;
                                    }

                                    if (lowNumber < zStep)
                                    {
                                        lowNumber = zStep;
                                    }

                                    par3MapData.colors[xStep + zStep * xSize] = biomeIDToShow;
                                }
                            }
                        }
                    }

                    if (highNumber <= lowNumber)
                    {
                        par3MapData.setColumnDirty(xStep, highNumber, lowNumber);
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (!par2World.isRemote)
        {
        	TFMagicMapData mapData = this.getMapData(par1ItemStack, par2World);

            if (par3Entity instanceof EntityPlayer)
            {
                EntityPlayer var7 = (EntityPlayer)par3Entity;
                mapData.updateVisiblePlayers(var7, par1ItemStack);
            }

            if (par5)
            {
                this.updateMapData(par2World, par3Entity, mapData);
            }
        }
    }

    @Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	// we don't need to do anything here, I think
    }
    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.UNCOMMON;
	}

    @Override
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * returns null if no update is to be sent
     * 
     * We have re-written this to provide a Packet250CustomPayload to be sent, since the map data packet is only for the actual map map.
     * 
     * Also every 4 player update packets we send is actually a feature icon update packet.
     */
	@Override
    public Packet func_150911_c(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
//		System.out.println("Getting magic map packet");
		
        byte[] mapBytes = this.getMapData(par1ItemStack, par2World).getUpdatePacketData(par1ItemStack, par2World, par3EntityPlayer);
        
        if (mapBytes == null) {
        	return null;
        }
        else {
     		// hijack random player data packets for our feature data packets?
        	if (mapBytes[0] == 1 && par2World.rand.nextInt(4) == 0) {
        		this.getMapData(par1ItemStack, par2World).checkExistingFeatures(par2World);
        		mapBytes = this.getMapData(par1ItemStack, par2World).makeFeatureStorageArray();
        	}
        	
        	//short mapItemID = (short) TFItems.magicMap;
        	short uniqueID = (short)par1ItemStack.getItemDamage();
        	
        	return TFMapPacketHandler.makeMagicMapPacket(ItemTFMagicMap.STR_ID, uniqueID, mapBytes);
        }
    }

    @Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		return ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name") + " #" + par1ItemStack.getItemDamage()).trim();
    }
}
