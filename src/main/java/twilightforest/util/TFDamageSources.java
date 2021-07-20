package twilightforest.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class TFDamageSources {
    public static final DamageSource GHAST_TEAR = new DamageSource(tfSource("ghastTear")); //ur-ghast
    public static final DamageSource HYDRA_BITE = new DamageSource(tfSource("hydraBite")); //hydra
    public static final DamageSource HYDRA_FIRE = new DamageSource(tfSource("hydraFire")); //hydra
    public static final DamageSource LICH_BOLT = new DamageSource(tfSource("lichBolt")).setDamageBypassesArmor().setMagicDamage(); //lich
    public static final DamageSource LICH_BOMB = new DamageSource(tfSource("lichBomb")).setDamageBypassesArmor().setMagicDamage(); //lich
    public static final DamageSource CHILLING_BREATH = new DamageSource(tfSource("chillingBreath")); //snow queen
    public static final DamageSource SQUISH = new DamageSource(tfSource("squish")); //snow queen
    public static final DamageSource THROWN_AXE = new DamageSource(tfSource("thrownAxe"));
    public static final DamageSource THROWN_PICKAXE = new DamageSource(tfSource("thrownPickaxe"));
    public static final DamageSource THORNS = new DamageSource(tfSource("thorns"));
    public static final DamageSource KNIGHTMETAL = new DamageSource(tfSource("knightmetal"));
    public static final DamageSource FIERY = new DamageSource(tfSource("fiery"));
    public static final DamageSource FIRE_JET = new DamageSource(tfSource("fireJet"));
    public static final DamageSource REACTOR = new DamageSource(tfSource("reactor"));
    public static final DamageSource SLIDER = new DamageSource(tfSource("slider"));

    public static DamageSource AXING(LivingEntity mob) {
        return new EntityDamageSource(tfSource("axing"), mob); //minotaur, minoshroom
    }
    //TODO actually figure out how to implement this one
    public static DamageSource YEETED(LivingEntity mob) {
        return new EntityDamageSource(tfSource("yeeted"), mob); //yetis, alpha yeti
    }
    public static DamageSource ANT(LivingEntity mob) {
        return new EntityDamageSource(tfSource("ant"), mob); //giants
    }
    public static DamageSource HAUNT(LivingEntity mob) {
        return new EntityDamageSource(tfSource("haunt"), mob); //wraith, knight phantom
    }
    public static DamageSource CLAMPED(LivingEntity mob) {
        return new EntityDamageSource(tfSource("clamped"), mob); //pinch beetle
    }
    public static DamageSource SCORCHED(LivingEntity mob) {
        return new EntityDamageSource(tfSource("scorched"), mob); //fire beetle
    }
    public static DamageSource FROZEN(Entity source, LivingEntity mob) {
        return new IndirectEntityDamageSource(tfSource("frozen"), source, mob); //ice bomb
    }
    public static DamageSource SPIKED(Entity source, LivingEntity mob) {
        return new IndirectEntityDamageSource(tfSource("spiked"), source, mob); //block and chain
    }
    public static DamageSource LEAF_BRAIN(Entity source, LivingEntity mob) {
        return new IndirectEntityDamageSource(tfSource("leafBrain"), source, mob).setDamageBypassesArmor().setMagicDamage(); //druid
    }
    public static DamageSource LOST_WORDS(Entity source, LivingEntity mob) {
        return new IndirectEntityDamageSource(tfSource("lostWords"), source, mob).setDamageBypassesArmor().setMagicDamage(); //tome
    }
    public static DamageSource SNOWBALL_FIGHT(Entity source, LivingEntity mob) {
        return new IndirectEntityDamageSource(tfSource("snowballFight"), source, mob); //ice core
    }

    public static String tfSource(String name) {
        return "twilightforest." + name;
    }
}
