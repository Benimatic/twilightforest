package twilightforest.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.client.particle.data.LeafParticleData;
import twilightforest.client.particle.data.PinnedFireflyData;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TFParticleType {

	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TwilightForestMod.ID);

	public static final RegistryObject<BasicParticleType> LARGE_FLAME = PARTICLE_TYPES.register("large_flame", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> LEAF_RUNE = PARTICLE_TYPES.register("leaf_rune", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> BOSS_TEAR = PARTICLE_TYPES.register("boss_tear", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> GHAST_TRAP = PARTICLE_TYPES.register("ghast_trap", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> PROTECTION = PARTICLE_TYPES.register("protection", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> SNOW = PARTICLE_TYPES.register("snow", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> SNOW_WARNING = PARTICLE_TYPES.register("snow_warning", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> SNOW_GUARDIAN = PARTICLE_TYPES.register("snow_guardian", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> ICE_BEAM = PARTICLE_TYPES.register("ice_beam", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> ANNIHILATE = PARTICLE_TYPES.register("annihilate", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> HUGE_SMOKE = PARTICLE_TYPES.register("huge_smoke", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> FIREFLY = PARTICLE_TYPES.register("firefly", () -> new BasicParticleType(false));
	public static final RegistryObject<ParticleType<PinnedFireflyData>> FIREFLY_PINNED = PARTICLE_TYPES.register("firefly_pinned", () -> new ParticleType<PinnedFireflyData>(false, new PinnedFireflyData.Deserializer()) {
		@Override
		public Codec<PinnedFireflyData> func_230522_e_() {
			return PinnedFireflyData.codecFirefly();
		}
	});
	public static final RegistryObject<ParticleType<LeafParticleData>> FALLEN_LEAF = PARTICLE_TYPES.register("fallen_leaf", () -> new ParticleType<LeafParticleData>(false, new LeafParticleData.Deserializer()) {
		@Override
		public Codec<LeafParticleData> func_230522_e_() {
			return LeafParticleData.codecLeaf();
		}
	});

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent event) {
		ParticleManager particles = Minecraft.getInstance().particles;

		particles.registerFactory(TFParticleType.LARGE_FLAME.get(), LargeFlameParticle.Factory::new);
		particles.registerFactory(TFParticleType.LEAF_RUNE.get(), LeafRuneParticle.Factory::new);
		particles.registerFactory(TFParticleType.BOSS_TEAR.get(), new GhastTearParticle.Factory());
		particles.registerFactory(TFParticleType.GHAST_TRAP.get(), GhastTrapParticle.Factory::new);
		particles.registerFactory(TFParticleType.PROTECTION.get(), ProtectionParticle.Factory::new); //probably not a good idea, but worth a shot
		particles.registerFactory(TFParticleType.SNOW.get(), SnowParticle.Factory::new);
		particles.registerFactory(TFParticleType.SNOW_GUARDIAN.get(), SnowGuardianParticle.Factory::new);
		particles.registerFactory(TFParticleType.SNOW_WARNING.get(), SnowWarningParticle.Factory::new);
		particles.registerFactory(TFParticleType.ICE_BEAM.get(), IceBeamParticle.Factory::new);
		particles.registerFactory(TFParticleType.ANNIHILATE.get(), AnnihilateParticle.Factory::new);
		particles.registerFactory(TFParticleType.HUGE_SMOKE.get(), SmokeScaleParticle.Factory::new);
		particles.registerFactory(TFParticleType.FIREFLY.get(), FireflyParticle.Factory::new);
		particles.registerFactory(TFParticleType.FIREFLY_PINNED.get(), PinnedFireflyParticle.Factory::new);
		particles.registerFactory(TFParticleType.FALLEN_LEAF.get(), LeafParticle.Factory::new);
	}
}
