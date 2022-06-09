package twilightforest.entity.passive;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.item.DyeColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import twilightforest.init.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFEntities;
import twilightforest.loot.TFTreasure;

import javax.annotation.Nullable;

public class Bighorn extends Sheep {

	public Bighorn(EntityType<? extends Bighorn> type, Level world) {
		super(type, world);
	}

	public Bighorn(Level world, double x, double y, double z) {
		this(TFEntities.BIGHORN_SHEEP.get(), world);
		this.setPos(x, y, z);
	}

	@Override
	public ResourceLocation getDefaultLootTable() {
		if (this.isSheared()) {
			return this.getType().getDefaultLootTable();
		} else {
			return switch (this.getColor()) {
				case ORANGE -> TFTreasure.BIGHORN_SHEEP_ORANGE;
				case MAGENTA -> TFTreasure.BIGHORN_SHEEP_MAGENTA;
				case LIGHT_BLUE -> TFTreasure.BIGHORN_SHEEP_LIGHT_BLUE;
				case YELLOW -> TFTreasure.BIGHORN_SHEEP_YELLOW;
				case LIME -> TFTreasure.BIGHORN_SHEEP_LIME;
				case PINK -> TFTreasure.BIGHORN_SHEEP_PINK;
				case GRAY -> TFTreasure.BIGHORN_SHEEP_GRAY;
				case LIGHT_GRAY -> TFTreasure.BIGHORN_SHEEP_LIGHT_GRAY;
				case CYAN -> TFTreasure.BIGHORN_SHEEP_CYAN;
				case PURPLE -> TFTreasure.BIGHORN_SHEEP_PURPLE;
				case BLUE -> TFTreasure.BIGHORN_SHEEP_BLUE;
				case BROWN -> TFTreasure.BIGHORN_SHEEP_BROWN;
				case GREEN -> TFTreasure.BIGHORN_SHEEP_GREEN;
				case RED -> TFTreasure.BIGHORN_SHEEP_RED;
				case BLACK -> TFTreasure.BIGHORN_SHEEP_BLACK;
				default -> TFTreasure.BIGHORN_SHEEP_WHITE;
			};
		}
	}

	private static DyeColor getRandomFleeceColor(RandomSource random) {
		return random.nextBoolean()
				? DyeColor.BROWN
				: DyeColor.byId(random.nextInt(16));
	}

	@Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag dataTag) {
        livingdata = super.finalizeSpawn(accessor, difficulty, reason, livingdata, dataTag);
        this.setColor(getRandomFleeceColor(accessor.getRandom()));
        return livingdata;
    }

    @Override
	public Sheep getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		if (!(ageable instanceof Bighorn)) {
			TwilightForestMod.LOGGER.error("Code was called to breed a Bighorn with a non Bighorn! Cancelling!");
			return null;
		}

		Bighorn otherParent = (Bighorn) ageable;
		Bighorn babySheep = TFEntities.BIGHORN_SHEEP.get().create(world);
		babySheep.setColor(getOffspringColor(this, otherParent));
		return babySheep;
	}
    
    @Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.BIGHORN_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.BIGHORN_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.BIGHORN_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState block) {
		this.playSound(TFSounds.BIGHORN_STEP.get(), 0.15F, 1.0F);
	}
}
