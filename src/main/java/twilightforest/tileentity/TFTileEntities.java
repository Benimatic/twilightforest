package twilightforest.tileentity;

import com.google.common.collect.Sets;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.tileentity.critters.*;
import twilightforest.tileentity.spawner.*;

public class TFTileEntities {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, TwilightForestMod.ID);

	//TODO: Are we using DataFixers? If so, replace null with DataFixerType
	//Actually, DataFixers might not even be used. Verify

	public static final RegistryObject<TileEntityType<TileEntityTFAntibuilder>> ANTIBUILDER               = TILE_ENTITIES.register("antibuilder", () ->
			new TileEntityType<>(TileEntityTFAntibuilder::new, Sets.newHashSet(TFBlocks.antibuilder.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFCinderFurnace>> CINDER_FURNACE          = TILE_ENTITIES.register("cinder_furnace", () ->
			new TileEntityType<>(TileEntityTFCinderFurnace::new, Sets.newHashSet(TFBlocks.cinder_furnace.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFCReactorActive>> CARMINITE_REACTOR      = TILE_ENTITIES.register("carminite_reactor", () ->
			new TileEntityType<>(TileEntityTFCReactorActive::new, Sets.newHashSet(TFBlocks.carminite_reactor.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFFlameJet>> FLAME_JET                    = TILE_ENTITIES.register("flame_jet", () ->
			new TileEntityType<>(TileEntityTFFlameJet::new, Sets.newHashSet(TFBlocks.fire_jet.get(), TFBlocks.encased_fire_jet.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFGhastTrapActive>> GHAST_TRAP_ACTIVE     = TILE_ENTITIES.register("ghast_trap_active", () ->
			new TileEntityType<>(TileEntityTFGhastTrapActive::new, Sets.newHashSet(TFBlocks.ghast_trap.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFGhastTrapInactive>> GHAST_TRAP_INACTIVE = TILE_ENTITIES.register("ghast_trap_inactive", () ->
			new TileEntityType<>(TileEntityTFGhastTrapInactive::new, Sets.newHashSet(TFBlocks.ghast_trap.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFPoppingJet>> POPPING_JET                = TILE_ENTITIES.register("popping_jet", () ->
			new TileEntityType<>(TileEntityTFPoppingJet::new, Sets.newHashSet(TFBlocks.fire_jet.get(), TFBlocks.encased_fire_jet.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFSmoker>> SMOKER                           = TILE_ENTITIES.register("smoker", () ->
			new TileEntityType<>(TileEntityTFSmoker::new, Sets.newHashSet(TFBlocks.smoker.get(), TFBlocks.encased_smoker.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFTowerBuilder>> TOWER_BUILDER            = TILE_ENTITIES.register("tower_builder", () ->
			new TileEntityType<>(TileEntityTFTowerBuilder::new, Sets.newHashSet(TFBlocks.carminite_builder.get()), null));
	public static final RegistryObject<TileEntityType<TileEntityTFTrophy>> TROPHY                         = TILE_ENTITIES.register("trophy", () ->
			new TileEntityType<>(TileEntityTFTrophy::new, Sets.newHashSet(TFBlocks.naga_trophy.get(), TFBlocks.lich_trophy.get(), TFBlocks.minoshroom_trophy.get(), TFBlocks.hydra_trophy.get(), TFBlocks.knight_phantom_trophy.get(), TFBlocks.ur_ghast_trophy.get(), TFBlocks.snow_queen_trophy.get(), TFBlocks.quest_ram_trophy.get()), null));

	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> ALPHA_YETI_SPAWNER     = TILE_ENTITIES.register("alpha_yeti_spawner", () ->
			new TileEntityType<>(TileEntityTFAlphaYetiSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> FINAL_BOSS_SPAWNER     = TILE_ENTITIES.register("final_boss_spawner", () ->
			new TileEntityType<>(TileEntityTFFinalBossSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> HYDRA_SPAWNER          = TILE_ENTITIES.register("hydra_boss_spawner", () ->
			new TileEntityType<>(TileEntityTFHydraSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> KNIGHT_PHANTOM_SPAWNER = TILE_ENTITIES.register("knight_phantom_spawner", () ->
			new TileEntityType<>(TileEntityTFKnightPhantomsSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> LICH_SPAWNER           = TILE_ENTITIES.register("lich_spawner", () ->
			new TileEntityType<>(TileEntityTFLichSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> MINOSHROOM_SPAWNER     = TILE_ENTITIES.register("minoshroom_spawner", () ->
			new TileEntityType<>(TileEntityTFMinoshroomSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> NAGA_SPAWNER           = TILE_ENTITIES.register("naga_spawner", () ->
			new TileEntityType<>(TileEntityTFNagaSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> SNOW_QUEEN_SPAWNER     = TILE_ENTITIES.register("snow_queen_spawner", () ->
			new TileEntityType<>(TileEntityTFSnowQueenSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFBossSpawner>> TOWER_BOSS_SPAWNER     = TILE_ENTITIES.register("tower_boss_spawner", () ->
			new TileEntityType<>(TileEntityTFTowerBossSpawner::new, Sets.newHashSet(TFBlocks.boss_spawner.get()), null));

	public static final RegistryObject<TileEntityType<? extends TileEntityTFCicada>> CICADA     = TILE_ENTITIES.register("cicada", () ->
			new TileEntityType<>(TileEntityTFCicadaTicking::new, Sets.newHashSet(TFBlocks.cicada.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFFirefly>> FIREFLY   = TILE_ENTITIES.register("firefly", () ->
			new TileEntityType<>(TileEntityTFFireflyTicking::new, Sets.newHashSet(TFBlocks.cicada.get()), null));
	public static final RegistryObject<TileEntityType<? extends TileEntityTFMoonworm>> MOONWORM = TILE_ENTITIES.register("moonworm", () ->
			new TileEntityType<>(TileEntityTFMoonwormTicking::new, Sets.newHashSet(TFBlocks.cicada.get()), null));
}
