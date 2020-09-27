package twilightforest.world;

import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomes;
import twilightforest.client.TFClientProxy;
import twilightforest.client.renderer.TFSkyRenderer;
import twilightforest.client.renderer.TFWeatherRenderer;

import javax.annotation.Nullable;

/**
 * @author Ben
 */
public class WorldProviderTwilightForest extends WorldProviderSurface {

	private static final String SEED_KEY = "CustomSeed";
	private static final String SKYLIGHT_KEY = "HasSkylight";

	private static volatile boolean skylightEnabled = true;

	private long seed;

	public static void syncFromConfig() {
		skylightEnabled = TFConfig.performance.enableSkylight;
	}

	public static void setSkylightEnabled(boolean enabled) {
		skylightEnabled = enabled;
	}

	public static boolean isSkylightEnabled(NBTTagCompound data) {
		return data.hasKey(SKYLIGHT_KEY, Constants.NBT.TAG_BYTE) ? data.getBoolean(SKYLIGHT_KEY) : skylightEnabled;
	}

	public WorldProviderTwilightForest() {
		setDimension(TFConfig.dimension.dimensionID);
	}

	/* TODO Breaking change. Uncomment for 1.13.
	Reason for adding ID is if we want multiple TF worlds for servers in future.

	@Override
	public String getSaveFolder() {
		return "twilightforest" + getDimension();
	}*/

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public MusicTicker.MusicType getMusicType() {
		return TFClientProxy.TFMUSICTYPE;
	}

	@Override
	public float[] calcSunriseSunsetColors(float celestialAngle, float f1) {
		return null;
	}

	@Override
	public Vec3d getFogColor(float f, float f1) {
		float bright = MathHelper.cos(0.25f * 3.141593F * 2.0F) * 2.0F + 0.5F;
		if (bright < 0.0F) {
			bright = 0.0F;
		}
		if (bright > 1.0F) {
			bright = 1.0F;
		}
		float red = 0.7529412F; // 192
		float green = 1.0F; // 255
		float blue = 0.8470588F; // 216
		red *= bright * 0.94F + 0.06F;
		green *= bright * 0.94F + 0.06F;
		blue *= bright * 0.91F + 0.09F;
		return new Vec3d(red, green, blue);
	}

	// Pin the celestial angle at night/evening so things that use it see night
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.225f;
	}

	@Override
	public void init() {
		NBTTagCompound data = TFWorld.getDimensionData(world);
		seed = data.hasKey(SEED_KEY, Constants.NBT.TAG_LONG) ? data.getLong(SEED_KEY) : loadSeed();
		hasSkyLight = isSkylightEnabled(data);
		biomeProvider = new TFBiomeProvider(world);
	}

	@Override
	protected void generateLightBrightnessTable() {
		float f = this.hasSkyLight ? 0.0F : 0.1F;
		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float)i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return TFConfig.dimension.skylightForest
				? new ChunkGeneratorTwilightVoid(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled())
				: new ChunkGeneratorTwilightForest(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
	}

	/**
	 * This seems to be a function checking whether we have an ocean.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isSkyColored() {
		return false;
	}

	@Override
	public int getAverageGroundLevel() {
		return 30;
	}

	@Override
	public double getVoidFogYFactor() {
		// allow for terrain squashing
		return super.getVoidFogYFactor() * 2.0;
	}

	@Override
	public boolean canRespawnHere() {
		// lie about this until the world is initialized
		// otherwise the server will try to generate enough terrain for a spawn point and that's annoying
		return world.getWorldInfo().isInitialized();
	}

	@Override
	public DimensionType getDimensionType() {
		return TwilightForestMod.dimType;
	}

	@Override
	public boolean isDaytime() {
		return false;
	}

	@Override
	public boolean shouldMapSpin(String entityName, double x, double z, double rotation) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		// TODO Maybe in the future we can get the return of sky color by biome?
		return new Vec3d(32 / 256.0, 34 / 256.0, 74 / 256.0);
	}

	@Override
	public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
		final float r = 64f / 255f, g = 85f / 255f, b = 72f / 255f;
		if (!hasSkyLight) {
			colors[0] = r + blockLight * (1.0f - r);
			colors[1] = g + blockLight * (1.0f - g);
			colors[2] = b + blockLight * (1.0f - b);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float partialTicks) {
		return 1.0F;
	}

	@Override
	public double getHorizon() {
		return TFWorld.SEALEVEL;
	}

	@Override
	public Biome getBiomeForCoords(BlockPos pos) {
		Biome biome = super.getBiomeForCoords(pos);
		if (biome == null) {
			biome = TFBiomes.twilightForest;
		}
		return biome;
	}

	/**
	 * If there is a specific twilight forest seed set, use that.  Otherwise use the world seed.
	 */
	@Override
	public long getSeed() {
		return seed == 0L ? super.getSeed() : seed;
	}

	private long loadSeed() {
		String seed = TFConfig.dimension.twilightForestSeed;
		if (seed != null && !seed.isEmpty()) {
			try {
				return Long.parseLong(seed);
			} catch (NumberFormatException e) {
				return seed.hashCode();
			}
		}
		return 0L;
	}

	@Override
	public void onWorldSave() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong(SEED_KEY, seed);
		// TODO: decide on persisting this
		//data.setBoolean(SKYLIGHT_KEY, hasSkyLight);
		TFWorld.setDimensionData(world, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		if (super.getSkyRenderer() == null) {
			this.setSkyRenderer(new TFSkyRenderer());
		}
		return super.getSkyRenderer();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getWeatherRenderer() {
		if (super.getWeatherRenderer() == null) {
			this.setWeatherRenderer(new TFWeatherRenderer());
		}
		return super.getWeatherRenderer();
	}

	// no sideonly
	@Override
	public float getCloudHeight() {
		return TFConfig.dimension.skylightForest ? -1F : 161F;
	}
}
