package twilightforest;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public final class TFSounds {

	public static final SoundEvent KOBOLD_DEATH = createEvent("mob.kobold.die");
	public static final SoundEvent KOBOLD_AMBIENT = createEvent("mob.kobold.kobold");
	public static final SoundEvent KOBOLD_HURT = createEvent("mob.kobold.hurt");
	public static final SoundEvent CICADA = createEvent("mob.cicada");
	public static final SoundEvent NAGA_HISS = createEvent("mob.naga.hiss");
	public static final SoundEvent NAGA_HURT = createEvent("mob.naga.hurt");
	public static final SoundEvent NAGA_RATTLE = createEvent("mob.naga.rattle");
	public static final SoundEvent RAVEN_CAW = createEvent("mob.raven.caw");
	public static final SoundEvent RAVEN_SQUAWK = createEvent("mob.raven.squawk");
	public static final SoundEvent REDCAP_DEATH = createEvent("mob.redcap.die");
	public static final SoundEvent REDCAP_AMBIENT = createEvent("mob.redcap.redcap");
	public static final SoundEvent REDCAP_HURT = createEvent("mob.redcap.hurt");
	public static final SoundEvent TINYBIRD_CHIRP = createEvent("mob.tinybird.chirp");
	public static final SoundEvent TINYBIRD_HURT = createEvent("mob.tinybird.hurt");
	public static final SoundEvent TINYBIRD_SONG = createEvent("mob.tinybird.song");
	public static final SoundEvent URGHAST_TRAP_ACTIVE = createEvent("mob.urghast.trapactive");
	public static final SoundEvent URGHAST_TRAP_ON = createEvent("mob.urghast.trapon");
	public static final SoundEvent URGHAST_TRAP_SPINDOWN = createEvent("mob.urghast.trapspindown");
	public static final SoundEvent URGHAST_TRAP_WARMUP = createEvent("mob.urghast.trapwarmup");
	public static final SoundEvent WRAITH = createEvent("mob.wraith.wraith");
	public static final SoundEvent HYDRA_HURT = createEvent("mob.hydra.hurt");
	public static final SoundEvent HYDRA_DEATH = createEvent("mob.hydra.death");
	public static final SoundEvent HYDRA_GROWL = createEvent("mob.hydra.growl");
	public static final SoundEvent HYDRA_ROAR = createEvent("mob.hydra.roar");
	public static final SoundEvent HYDRA_WARN = createEvent("mob.hydra.warn");
	public static final SoundEvent MOSQUITO = createEvent("mob.mosquito.mosquito");
	public static final SoundEvent ICE_AMBIENT = createEvent("mob.ice.noise");
	public static final SoundEvent ICE_HURT = createEvent("mob.ice.hurt");
	public static final SoundEvent ICE_DEATH = createEvent("mob.ice.death");
	public static final SoundEvent SLIDER = createEvent("random.slider");

	public static final SoundEvent MUSIC = createEvent("music.bg");

	private static SoundEvent createEvent(String sound) {
		ResourceLocation name = new ResourceLocation(TwilightForestMod.ID, sound);
		return new SoundEvent(name).setRegistryName(name);
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		evt.getRegistry().register(KOBOLD_DEATH);
		evt.getRegistry().register(KOBOLD_AMBIENT);
		evt.getRegistry().register(KOBOLD_HURT);
		evt.getRegistry().register(CICADA);
		evt.getRegistry().register(NAGA_HISS);
		evt.getRegistry().register(NAGA_HURT);
		evt.getRegistry().register(NAGA_RATTLE);
		evt.getRegistry().register(RAVEN_CAW);
		evt.getRegistry().register(RAVEN_SQUAWK);
		evt.getRegistry().register(REDCAP_DEATH);
		evt.getRegistry().register(REDCAP_AMBIENT);
		evt.getRegistry().register(REDCAP_HURT);
		evt.getRegistry().register(TINYBIRD_CHIRP);
		evt.getRegistry().register(TINYBIRD_HURT);
		evt.getRegistry().register(TINYBIRD_SONG);
		evt.getRegistry().register(URGHAST_TRAP_ACTIVE);
		evt.getRegistry().register(URGHAST_TRAP_ON);
		evt.getRegistry().register(URGHAST_TRAP_SPINDOWN);
		evt.getRegistry().register(URGHAST_TRAP_WARMUP);
		evt.getRegistry().register(WRAITH);
		evt.getRegistry().register(HYDRA_DEATH);
		evt.getRegistry().register(HYDRA_GROWL);
		evt.getRegistry().register(HYDRA_HURT);
		evt.getRegistry().register(HYDRA_ROAR);
		evt.getRegistry().register(HYDRA_WARN);
		evt.getRegistry().register(MOSQUITO);
		evt.getRegistry().register(ICE_AMBIENT);
		evt.getRegistry().register(ICE_DEATH);
		evt.getRegistry().register(ICE_HURT);
		evt.getRegistry().register(SLIDER);

		evt.getRegistry().register(MUSIC);
	}

	private TFSounds() {
	}

}
