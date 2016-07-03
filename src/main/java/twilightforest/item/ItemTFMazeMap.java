package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData.MapCoord;
import net.minecraft.world.storage.MapData.MapInfo;
import twilightforest.TFMapPacketHandler;
import twilightforest.TFMazeMapData;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFMazeMap extends ItemMap
{
    public static final String STR_ID = "mazemap";
	private static final int YSEARCH = 3;
    protected boolean mapOres;

	protected ItemTFMazeMap(boolean par2MapOres)
    {
        this.mapOres = par2MapOres;
		//this.setCreativeTab(TFItems.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public static TFMazeMapData getMPMapData(int par0, World par1World)
    {
        String mapName = STR_ID + "_" + par0;

    	TFMazeMapData mapData = (TFMazeMapData)par1World.loadItemData(TFMazeMapData.class, mapName);
    	
    	//System.out.println("Incoming maze data! = " + mapData);

        if (mapData == null)
        {
            mapData = new TFMazeMapData(mapName);
            par1World.setItemData(mapName, mapData);
        }

        return mapData;
    }

    @Override
	public TFMazeMapData getMapData(ItemStack par1ItemStack, World par2World)
    {
    	TFMazeMapData mapData = (TFMazeMapData)par2World.loadItemData(TFMazeMapData.class, STR_ID + "_" + par1ItemStack.getItemDamage());

        if (mapData == null && !par2World.isRemote)
        {
            par1ItemStack.setItemDamage(par2World.getUniqueDataId(STR_ID));
            String mapName = STR_ID + "_" + par1ItemStack.getItemDamage();
            mapData = new TFMazeMapData(mapName);
            mapData.xCenter = par2World.getWorldInfo().getSpawnX();
            mapData.zCenter = par2World.getWorldInfo().getSpawnZ();
            mapData.scale = 0;
            mapData.dimension = par2World.provider.dimensionId;
            mapData.markDirty();
            par2World.setItemData(mapName, mapData);
        }

        return mapData;
    }

    /**
     * Maze map update data.  Look at scale 0 for walls, and maybe ores.
     */
    public void updateMapData(World par1World, Entity par2Entity, TFMazeMapData par3MapData)
    {
    	int yDraw = MathHelper.floor_double(par2Entity.posY - (double)par3MapData.yCenter);
    	
        if (par1World.provider.dimensionId == par3MapData.dimension && yDraw > -YSEARCH && yDraw < YSEARCH)
        {
            short xSize = 128;
            short zSize = 128;
            int xCenter = par3MapData.xCenter;
            int zCenter = par3MapData.zCenter;
            int xDraw = MathHelper.floor_double(par2Entity.posX - (double)xCenter)  + xSize / 2;
            int zDraw = MathHelper.floor_double(par2Entity.posZ - (double)zCenter)  + zSize / 2;
            int drawSize = 16;

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
                            int xDraw2 = (xCenter + xStep - xSize / 2);
                            int zDraw2 = (zCenter + zStep - zSize / 2);
                            Chunk chunk = par1World.getChunkFromBlockCoords(xDraw2, zDraw2);
                            int x15 = xDraw2 & 15;
                            int z15 = zDraw2 & 15;
                            int heightValue;
                            int colorIndex;

                            heightValue = par3MapData.yCenter;
                            Block blockID = chunk.getBlock(x15, heightValue, z15);

                            byte tint = 1;

                            colorIndex = 0;
                            
                            // for stone, search up and down for ores
                            
                            if (blockID == Blocks.STONE && mapOres) {
                            	for (int i = -YSEARCH; i <= YSEARCH; i++) {
                            		Block searchID = chunk.getBlock(x15, heightValue + i, z15);
                            		if (searchID != Blocks.STONE) {
                            			blockID = searchID;
                            			if (i > 0) {
                            				tint = 2;
                            			}
                            			if (i < 0) {
                            				tint = 0;
                            			}
                            			// stop searching
                            			break;
                            		}
                            	}
                            	
                            	
                            }
                            

                            if (blockID != Blocks.AIR)
                            {
                                MapColor mapColor = blockID.getMaterial().getMaterialMapColor();
                                colorIndex = mapColor.colorIndex;
                            }

                            if (mapOres) {
                            	// need to reobfuscate
                            	// recolor ores
                            	if (blockID == Blocks.COAL_ORE) {
                            		colorIndex = MapColor.obsidianColor.colorIndex;
                            	}
                            	else if (blockID == Blocks.GOLD_ORE) {
                            		colorIndex = MapColor.goldColor.colorIndex;
                            	}
                            	else if (blockID == Blocks.IRON_ORE) {
                            		colorIndex = MapColor.ironColor.colorIndex;
                            	}
                            	else if (blockID == Blocks.LAPIS_ORE) {
                            		colorIndex = MapColor.lapisColor.colorIndex;
                            	}
                            	else if (blockID == Blocks.REDSTONE_ORE || blockID == Blocks.LIT_REDSTONE_ORE) {
                            		colorIndex = MapColor.redColor.colorIndex;
                            	}
                            	else if (blockID == Blocks.DIAMOND_ORE) {
                            		colorIndex = MapColor.diamondColor.colorIndex;
                            	}
                            	else if (blockID == Blocks.EMERALD_ORE) {
                            		colorIndex = MapColor.emeraldColor.colorIndex;
                            	}
                            	else if (blockID != Blocks.AIR && blockID.getUnlocalizedName().toLowerCase().contains("ore"))
                            	{
                            		// any other ore, catchall
                            		colorIndex = MapColor.pinkColor.colorIndex;
                            	}
                            }

                            if (zStep >= 0 && xOffset * xOffset + zOffset * zOffset < drawSize * drawSize && (!var20 || (xStep + zStep & 1) != 0))
                            {
                                byte existingColor = par3MapData.colors[xStep + zStep * xSize];
                                byte tintedColor = (byte)(colorIndex * 4 + tint);

                                if (existingColor != tintedColor)
                                {
                                    if (highNumber > zStep)
                                    {
                                        highNumber = zStep;
                                    }

                                    if (lowNumber < zStep)
                                    {
                                        lowNumber = zStep;
                                    }

                                    par3MapData.colors[xStep + zStep * xSize] = tintedColor;
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
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean isActiveItem)
    {
        if (!par2World.isRemote)
        {
        	TFMazeMapData mapData = this.getMapData(par1ItemStack, par2World);

            if (par3Entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)par3Entity;
            	mapData.updateVisiblePlayers(player, par1ItemStack);

            	int yProximity = MathHelper.floor_double(player.posY - mapData.yCenter);
            	if (yProximity < -YSEARCH || yProximity > YSEARCH) {
            		// fix player icon so that it's a dot
            		
            		MapCoord mapCoord = (MapCoord) mapData.playersVisibleOnMap.get(player.getName());
            		if (mapCoord != null)
            		{
            			mapCoord.iconSize = 6;
            		}
            	}
            }

            if (isActiveItem)
            {
                this.updateMapData(par2World, par3Entity, mapData);
            }
        }
    }

    @Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par1ItemStack.setItemDamage(par2World.getUniqueDataId(STR_ID));
        String mapName = STR_ID + "_" + par1ItemStack.getItemDamage();
        TFMazeMapData mapData = new TFMazeMapData(mapName);
        par2World.setItemData(mapName, mapData);
        mapData.xCenter = MathHelper.floor_double(par3EntityPlayer.posX);
        mapData.yCenter = MathHelper.floor_double(par3EntityPlayer.posY);
        mapData.zCenter = MathHelper.floor_double(par3EntityPlayer.posZ);
        mapData.scale = 0;
        mapData.dimension = par2World.provider.getDimension();
        mapData.markDirty();
    }
    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return mapOres ? EnumRarity.EPIC : EnumRarity.UNCOMMON;
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
     */
	@Override
    public Packet func_150911_c(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		//System.out.println("Making maze map packet");
		//System.out.println("yCenter = " + this.getMapData(par1ItemStack, par2World).yCenter);
		
        byte[] mapBytes = this.getMapData(par1ItemStack, par2World).getUpdatePacketData(par1ItemStack, par2World, par3EntityPlayer);
        
        if (mapBytes == null) {
        	return null;
        }
        else {
        	short uniqueID = (short)par1ItemStack.getItemDamage();
        	
        	return TFMapPacketHandler.makeMagicMapPacket(ItemTFMazeMap.STR_ID, uniqueID, mapBytes);
        }
    }

    @Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		return ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name") + " #" + par1ItemStack.getItemDamage()).trim();
    }
}
