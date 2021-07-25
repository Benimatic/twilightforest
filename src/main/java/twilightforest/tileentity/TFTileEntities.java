package twilightforest.tileentity;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.client.renderer.tileentity.*;
import twilightforest.tileentity.spawner.*;

public class TFTileEntities {

	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TwilightForestMod.ID);

	public static final RegistryObject<BlockEntityType<AntibuilderTileEntity>> ANTIBUILDER               = TILE_ENTITIES.register("antibuilder", () ->
			BlockEntityType.Builder.of(AntibuilderTileEntity::new, TFBlocks.antibuilder.get()).build(null));
	public static final RegistryObject<BlockEntityType<CinderFurnaceTileEntity>> CINDER_FURNACE          = TILE_ENTITIES.register("cinder_furnace", () ->
			BlockEntityType.Builder.of(CinderFurnaceTileEntity::new, TFBlocks.cinder_furnace.get()).build(null));
	public static final RegistryObject<BlockEntityType<ActiveCarminiteReactorTileEntity>> CARMINITE_REACTOR      = TILE_ENTITIES.register("carminite_reactor", () ->
			BlockEntityType.Builder.of(ActiveCarminiteReactorTileEntity::new, TFBlocks.carminite_reactor.get()).build(null));
	public static final RegistryObject<BlockEntityType<FireJetTileEntity>> FLAME_JET                    = TILE_ENTITIES.register("flame_jet", () ->
			BlockEntityType.Builder.of(FireJetTileEntity::new, TFBlocks.fire_jet.get(), TFBlocks.encased_fire_jet.get()).build(null));
	public static final RegistryObject<BlockEntityType<ActiveGhastTrapTileEntity>> GHAST_TRAP = TILE_ENTITIES.register("ghast_trap", () ->
			BlockEntityType.Builder.of(ActiveGhastTrapTileEntity::new, TFBlocks.ghast_trap.get()).build(null));
	public static final RegistryObject<BlockEntityType<TFSmokerTileEntity>> SMOKER                           = TILE_ENTITIES.register("smoker", () ->
			BlockEntityType.Builder.of(TFSmokerTileEntity::new, TFBlocks.smoker.get(), TFBlocks.encased_smoker.get()).build(null));
	public static final RegistryObject<BlockEntityType<CarminiteBuilderTileEntity>> TOWER_BUILDER            = TILE_ENTITIES.register("tower_builder", () ->
			BlockEntityType.Builder.of(CarminiteBuilderTileEntity::new, TFBlocks.carminite_builder.get()).build(null));
	public static final RegistryObject<BlockEntityType<TrophyTileEntity>> TROPHY                         = TILE_ENTITIES.register("trophy", () ->
			BlockEntityType.Builder.of(TrophyTileEntity::new, TFBlocks.naga_trophy.get(), TFBlocks.lich_trophy.get(), TFBlocks.minoshroom_trophy.get(),
					TFBlocks.hydra_trophy.get(), TFBlocks.knight_phantom_trophy.get(), TFBlocks.ur_ghast_trophy.get(), TFBlocks.yeti_trophy.get(),
					TFBlocks.snow_queen_trophy.get(), TFBlocks.quest_ram_trophy.get(), TFBlocks.naga_wall_trophy.get(), TFBlocks.lich_wall_trophy.get(),
					TFBlocks.minoshroom_wall_trophy.get(), TFBlocks.hydra_wall_trophy.get(), TFBlocks.knight_phantom_wall_trophy.get(), TFBlocks.ur_ghast_wall_trophy.get(),
					TFBlocks.yeti_wall_trophy.get(), TFBlocks.snow_queen_wall_trophy.get(), TFBlocks.quest_ram_wall_trophy.get()).build(null));
	public static final RegistryObject<BlockEntityType<AlphaYetiSpawnerTileEntity>> ALPHA_YETI_SPAWNER     = TILE_ENTITIES.register("alpha_yeti_spawner", () ->
			BlockEntityType.Builder.of(AlphaYetiSpawnerTileEntity::new, TFBlocks.boss_spawner_alpha_yeti.get()).build(null));
	public static final RegistryObject<BlockEntityType<FinalBossSpawnerTileEntity>> FINAL_BOSS_SPAWNER     = TILE_ENTITIES.register("final_boss_spawner", () ->
			BlockEntityType.Builder.of(FinalBossSpawnerTileEntity::new, TFBlocks.boss_spawner_final_boss.get()).build(null));
	public static final RegistryObject<BlockEntityType<HydraSpawnerTileEntity>> HYDRA_SPAWNER          = TILE_ENTITIES.register("hydra_boss_spawner", () ->
			BlockEntityType.Builder.of(HydraSpawnerTileEntity::new, TFBlocks.boss_spawner_hydra.get()).build(null));
	public static final RegistryObject<BlockEntityType<KnightPhantomSpawnerTileEntity>> KNIGHT_PHANTOM_SPAWNER = TILE_ENTITIES.register("knight_phantom_spawner", () ->
			BlockEntityType.Builder.of(KnightPhantomSpawnerTileEntity::new, TFBlocks.boss_spawner_knight_phantom.get()).build(null));
	public static final RegistryObject<BlockEntityType<LichSpawnerTileEntity>> LICH_SPAWNER           = TILE_ENTITIES.register("lich_spawner", () ->
			BlockEntityType.Builder.of(LichSpawnerTileEntity::new, TFBlocks.boss_spawner_lich.get()).build(null));
	public static final RegistryObject<BlockEntityType<MinoshroomSpawnerTileEntity>> MINOSHROOM_SPAWNER     = TILE_ENTITIES.register("minoshroom_spawner", () ->
			BlockEntityType.Builder.of(MinoshroomSpawnerTileEntity::new, TFBlocks.boss_spawner_minoshroom.get()).build(null));
	public static final RegistryObject<BlockEntityType<NagaSpawnerTileEntity>> NAGA_SPAWNER           = TILE_ENTITIES.register("naga_spawner", () ->
			BlockEntityType.Builder.of(NagaSpawnerTileEntity::new, TFBlocks.boss_spawner_naga.get()).build(null));
	public static final RegistryObject<BlockEntityType<SnowQueenSpawnerTileEntity>> SNOW_QUEEN_SPAWNER     = TILE_ENTITIES.register("snow_queen_spawner", () ->
			BlockEntityType.Builder.of(SnowQueenSpawnerTileEntity::new, TFBlocks.boss_spawner_snow_queen.get()).build(null));
	public static final RegistryObject<BlockEntityType<UrGhastSpawnerTileEntity>> UR_GHAST_SPAWNER     = TILE_ENTITIES.register("tower_boss_spawner", () ->
			BlockEntityType.Builder.of(UrGhastSpawnerTileEntity::new, TFBlocks.boss_spawner_ur_ghast.get()).build(null));

	public static final RegistryObject<BlockEntityType<CicadaTileEntity>> CICADA     = TILE_ENTITIES.register("cicada", () ->
			BlockEntityType.Builder.of(CicadaTileEntity::new, TFBlocks.cicada.get()).build(null));
	public static final RegistryObject<BlockEntityType<FireflyTileEntity>> FIREFLY   = TILE_ENTITIES.register("firefly", () ->
			BlockEntityType.Builder.of(FireflyTileEntity::new, TFBlocks.firefly.get()).build(null));
	public static final RegistryObject<BlockEntityType<MoonwormTileEntity>> MOONWORM = TILE_ENTITIES.register("moonworm", () ->
			BlockEntityType.Builder.of(MoonwormTileEntity::new, TFBlocks.moonworm.get()).build(null));

	public static final RegistryObject<BlockEntityType<KeepsakeCasketTileEntity>> KEEPSAKE_CASKET          = TILE_ENTITIES.register("keepsake_casket", () ->
			BlockEntityType.Builder.of(KeepsakeCasketTileEntity::new, TFBlocks.keepsake_casket.get()).build(null));

	public static final RegistryObject<BlockEntityType<TFSignTileEntity>> TF_SIGN = TILE_ENTITIES.register("tf_sign", () ->
			BlockEntityType.Builder.of(TFSignTileEntity::new,
					TFBlocks.twilight_oak_sign.get(), TFBlocks.twilight_wall_sign.get(),
					TFBlocks.canopy_sign.get(), TFBlocks.canopy_wall_sign.get(),
					TFBlocks.mangrove_sign.get(), TFBlocks.mangrove_wall_sign.get(),
					TFBlocks.darkwood_sign.get(), TFBlocks.darkwood_wall_sign.get(),
					TFBlocks.time_sign.get(), TFBlocks.time_wall_sign.get(),
					TFBlocks.trans_sign.get(), TFBlocks.trans_wall_sign.get(),
					TFBlocks.mine_sign.get(), TFBlocks.mine_wall_sign.get(),
					TFBlocks.sort_sign.get(), TFBlocks.sort_wall_sign.get()).build(null));

	@OnlyIn(Dist.CLIENT)
	public static void registerTileEntityRenders() {
		// tile entities
		BlockEntityRenderers.register(FIREFLY.get(), FireflyTileEntityRenderer::new);
		BlockEntityRenderers.register(CICADA.get(), CicadaTileEntityRenderer::new);
		BlockEntityRenderers.register(MOONWORM.get(), MoonwormTileEntityRenderer::new);
		BlockEntityRenderers.register(TROPHY.get(), TrophyTileEntityRenderer::new);
		BlockEntityRenderers.register(TF_SIGN.get(), SignRenderer::new);
		BlockEntityRenderers.register(KEEPSAKE_CASKET.get(), CasketTileEntityRenderer::new);
	}
}
