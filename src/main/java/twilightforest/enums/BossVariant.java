package twilightforest.enums;

import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.util.StringRepresentable;
import twilightforest.tileentity.spawner.AlphaYetiSpawnerTileEntity;
import twilightforest.tileentity.spawner.BossSpawnerTileEntity;
import twilightforest.tileentity.spawner.FinalBossSpawnerTileEntity;
import twilightforest.tileentity.spawner.HydraSpawnerTileEntity;
import twilightforest.tileentity.spawner.KnightPhantomSpawnerTileEntity;
import twilightforest.tileentity.spawner.LichSpawnerTileEntity;
import twilightforest.tileentity.spawner.MinoshroomSpawnerTileEntity;
import twilightforest.tileentity.spawner.NagaSpawnerTileEntity;
import twilightforest.tileentity.spawner.SnowQueenSpawnerTileEntity;
import twilightforest.tileentity.spawner.UrGhastSpawnerTileEntity;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Supplier;

public enum BossVariant implements StringRepresentable, SkullBlock.Type {

	NAGA          (TrophyType.GOLD    , NagaSpawnerTileEntity::new),
	LICH          (TrophyType.GOLD    , LichSpawnerTileEntity::new),
	HYDRA         (TrophyType.GOLD    , HydraSpawnerTileEntity::new),
	UR_GHAST      (TrophyType.GOLD    , UrGhastSpawnerTileEntity::new),
	KNIGHT_PHANTOM(TrophyType.IRON    , KnightPhantomSpawnerTileEntity::new),
	SNOW_QUEEN    (TrophyType.GOLD    , SnowQueenSpawnerTileEntity::new),
	MINOSHROOM    (TrophyType.IRON    , MinoshroomSpawnerTileEntity::new),
	ALPHA_YETI    (TrophyType.IRON    , AlphaYetiSpawnerTileEntity::new),
	QUEST_RAM     (TrophyType.IRONWOOD, null),
	FINAL_BOSS    (TrophyType.GOLD    , FinalBossSpawnerTileEntity::new);

	private final Supplier<? extends BossSpawnerTileEntity<?>> factory;
	private final TrophyType trophyType;

	public static final BossVariant[] VARIANTS = values();

	BossVariant(TrophyType trophyType, @Nullable Supplier<? extends BossSpawnerTileEntity<?>> factory) {
		this.factory = factory;
		this.trophyType = trophyType;
	}

	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT);
	}

	public TrophyType getTrophyType() {
		return this.trophyType;
	}

	public boolean hasSpawner() {
		return factory != null;
	}

	@Nullable
	public BossSpawnerTileEntity<?> getSpawner() {
		return factory != null ? factory.get() : null;
	}

	public enum TrophyType {
		GOLD("trophy"),
		IRON("trophy_minor"),
		IRONWOOD("trophy_quest");

		private final String modelName;

		TrophyType(String modelName) {
			this.modelName = modelName;
		}

		public String getModelName() {
			return this.modelName;
		}
	}
}
