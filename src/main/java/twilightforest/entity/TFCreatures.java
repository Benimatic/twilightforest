package twilightforest.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;

public class TFCreatures {

	/** This is a HashMap of the Creative Entity Eggs/Spawners. */
	public static HashMap<Integer, TFEntityEggInfo> entityEggs = new LinkedHashMap<Integer, TFEntityEggInfo>();


	public static void registerTFCreature(Class <? extends Entity > entityClass, String entityName, int id, int backgroundEggColour, int foregroundEggColour) {
		registerTFCreature(entityClass, entityName, id, backgroundEggColour, foregroundEggColour, 80, 3, true);
	}	
	
	public static void registerTFCreature(Class <? extends Entity > entityClass, String entityName, int id, int backgroundEggColour, int foregroundEggColour, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		if (TwilightForestMod.creatureCompatibility)
		{
			// only register global id if flag is set
			EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		}
		EntityRegistry.registerModEntity(entityClass, entityName, id, TwilightForestMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		entityEggs.put(Integer.valueOf(id), new TFEntityEggInfo(id, backgroundEggColour, foregroundEggColour));
	}

	public static void registerTFCreature(Class <? extends Entity > entityClass, String entityName, int id) {
		if (TwilightForestMod.creatureCompatibility)
		{
			// only register global id if flag is set
			EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		}
		EntityRegistry.registerModEntity(entityClass, entityName, id, TwilightForestMod.instance, 80, 3, true);
	}

	/**
	 * Create a new instance of an entity in the world by using an entity ID.
	 * This is the version that creates twilight forest creatures for use by their spawn eggs.
	 */
	public static Entity createEntityByID(int entityID, World par1World)
	{
		Entity entity = null;

		try
		{
			//Class clazz = (Class)IDtoClassMapping.get(Integer.valueOf(entityID));
			ModContainer mc = FMLCommonHandler.instance().findContainerFor(TwilightForestMod.instance);
			EntityRegistration er = EntityRegistry.instance().lookupModSpawn(mc, entityID);
			Class<?> clazz = er.getEntityClass();

			if (clazz != null)
			{
				entity = (Entity)clazz.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1World});
			}
		}
		catch (Exception var4)
		{
			var4.printStackTrace();
		}

		if (entity == null)
		{
			System.out.println("Skipping Entity with id " + entityID);
		}

		return entity;
	}

	/**
	 * Return the name of the monster with that ID.
	 */
	public static String getStringFromID(int entityID)
	{
		ModContainer mc = FMLCommonHandler.instance().findContainerFor(TwilightForestMod.instance);
		EntityRegistration er = EntityRegistry.instance().lookupModSpawn(mc, entityID);

		if (er != null) {
			return er.getEntityName();
		}

		return null;
	}

	/**
	 * Return a string suitable for setting as the mobID in spawners.  If the compatibility flag is set, then use the base monster name
	 * If the flag is false, return TwilightForest.mobName
	 */
	public static String getSpawnerNameFor(String baseName) {
		if (TwilightForestMod.creatureCompatibility)
		{
			return baseName;
		}
		else
		{
			return "TwilightForest." + baseName;
		}
	}
	
	
}
