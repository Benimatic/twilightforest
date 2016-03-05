package twilightforest.entity;

import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;

/**
 * Very similar to EntityEggInfo, but registers the stat info differently
 */
public class TFEntityEggInfo {
	/**
     * The entityID of the spawned mob
     */
    public final int spawnedID;
    /**
     * Base color of the egg
     */
    public final int primaryColor;
    /**
     * Color of the egg spots
     */
    public final int secondaryColor;
    public final StatBase killedStat;
    public final StatBase killedByStat;

    public TFEntityEggInfo(int par1, int par2, int par3)
    {
        this.spawnedID = par1;
        this.primaryColor = par2;
        this.secondaryColor = par3;
        this.killedStat = TFEntityEggInfo.makeEntityKillStat(this);
        this.killedByStat = TFEntityEggInfo.makeEntityKilledByStat(this);
    }
    
    public static StatBase makeEntityKillStat(TFEntityEggInfo eggInfo)
    {
        String s = TFCreatures.getStringFromID(eggInfo.spawnedID);
        return s == null ? null : (new StatBase("stat.killEntity." + s, new ChatComponentTranslation("stat.entityKill", new Object[] {new ChatComponentTranslation("entity." + s + ".name", new Object[0])}))).registerStat();
    }

    public static StatBase makeEntityKilledByStat(TFEntityEggInfo p_151176_0_)
    {
        String s = TFCreatures.getStringFromID(p_151176_0_.spawnedID);
        return s == null ? null : (new StatBase("stat.entityKilledBy." + s, new ChatComponentTranslation("stat.entityKilledBy", new Object[] {new ChatComponentTranslation("entity." + s + ".name", new Object[0])}))).registerStat();
    }
}
