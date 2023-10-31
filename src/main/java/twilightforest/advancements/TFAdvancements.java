package twilightforest.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class TFAdvancements {
    public static final MakePortalTrigger MADE_TF_PORTAL = CriteriaTriggers.register(MakePortalTrigger.ID.toString(), new MakePortalTrigger());
    public static final HydraChopTrigger CONSUME_HYDRA_CHOP = CriteriaTriggers.register(HydraChopTrigger.ID.toString(), new HydraChopTrigger());
    public static final QuestRamCompletionTrigger QUEST_RAM_COMPLETED = CriteriaTriggers.register(QuestRamCompletionTrigger.ID.toString(), new QuestRamCompletionTrigger());
    public static final TrophyPedestalTrigger PLACED_TROPHY_ON_PEDESTAL = CriteriaTriggers.register(TrophyPedestalTrigger.ID.toString(), new TrophyPedestalTrigger());
    public static final ActivateGhastTrapTrigger ACTIVATED_GHAST_TRAP = CriteriaTriggers.register(ActivateGhastTrapTrigger.ID.toString(), new ActivateGhastTrapTrigger());
    public static final StructureClearedTrigger STRUCTURE_CLEARED = CriteriaTriggers.register(StructureClearedTrigger.ID.toString(), new StructureClearedTrigger());
    public static final DrinkFromFlaskTrigger DRINK_FROM_FLASK = CriteriaTriggers.register(DrinkFromFlaskTrigger.ID.toString(), new DrinkFromFlaskTrigger());
    public static final KillBugTrigger KILL_BUG = CriteriaTriggers.register(KillBugTrigger.ID.toString(), new KillBugTrigger());
    public static final HurtBossTrigger HURT_BOSS = CriteriaTriggers.register(HurtBossTrigger.ID.toString(), new HurtBossTrigger());
    public static final KillAllPhantomsTrigger KILL_ALL_PHANTOMS = CriteriaTriggers.register(KillAllPhantomsTrigger.ID.toString(), new KillAllPhantomsTrigger());
    public static final UncraftItemTrigger UNCRAFT_ITEM = CriteriaTriggers.register(UncraftItemTrigger.ID.toString(), new UncraftItemTrigger());

    public static void init() {}
}
