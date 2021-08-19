package twilightforest.world.components;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

//[VanillaCopy] of DefaultWorldCarver, but caves change size when in highlands, caves wont put air blocks under streams, and caves have dirt roofs
//FIXME for now, this is completely a vanilla copy. Im gonna have to completely rework caves AGAIN, too much changed
public class TFCavesCarver extends WorldCarver<CaveCarverConfiguration> {

	private final boolean isHighlands;

	public TFCavesCarver(Codec<CaveCarverConfiguration> codec, boolean isHighlands) {
		super(codec);
		this.liquids = ImmutableSet.of();
		this.isHighlands = isHighlands;
	}

	public boolean isStartChunk(CaveCarverConfiguration p_159263_, Random p_159264_) {
		return p_159264_.nextFloat() <= p_159263_.probability;
	}

	public boolean carve(CarvingContext p_159254_, CaveCarverConfiguration p_159255_, ChunkAccess p_159256_, Function<BlockPos, Biome> p_159257_, Random p_159258_, Aquifer p_159259_, ChunkPos p_159260_, BitSet p_159261_) {
		int i = SectionPos.sectionToBlockCoord(this.getRange() * 2 - 1);
		int j = p_159258_.nextInt(p_159258_.nextInt(p_159258_.nextInt(this.getCaveBound()) + 1) + 1);

		for(int k = 0; k < j; ++k) {
			double d0 = p_159260_.getBlockX(p_159258_.nextInt(16));
			double d1 = p_159255_.y.sample(p_159258_, p_159254_);
			double d2 = p_159260_.getBlockZ(p_159258_.nextInt(16));
			double d3 = p_159255_.horizontalRadiusMultiplier.sample(p_159258_);
			double d4 = p_159255_.verticalRadiusMultiplier.sample(p_159258_);
			//FIXME floorLevel cant be used here
			//double d5 = p_159255_.floorLevel.sample(p_159258_);
			WorldCarver.CarveSkipChecker worldcarver$carveskipchecker = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> shouldSkip(p_159203_, p_159204_, p_159205_, /*d5*/ 0);
			int l = 1;
			if (p_159258_.nextInt(4) == 0) {
				double d6 = p_159255_.yScale.sample(p_159258_);
				float f1 = 1.0F + p_159258_.nextFloat() * 6.0F;
				this.createRoom(p_159254_, p_159255_, p_159256_, p_159257_, p_159258_.nextLong(), p_159259_, d0, d1, d2, f1, d6, p_159261_, worldcarver$carveskipchecker);
				l += p_159258_.nextInt(4);
			}

			for(int k1 = 0; k1 < l; ++k1) {
				float f = p_159258_.nextFloat() * ((float)Math.PI * 2F);
				float f3 = (p_159258_.nextFloat() - 0.5F) / 4.0F;
				float f2 = this.getThickness(p_159258_);
				int i1 = i - p_159258_.nextInt(i / 4);
				this.createTunnel(p_159254_, p_159255_, p_159256_, p_159257_, p_159258_.nextLong(), p_159259_, d0, d1, d2, d3, d4, f2, f, f3, 0, i1, this.getYScale(), p_159261_, worldcarver$carveskipchecker);
			}
		}

		return true;
	}

	protected int getCaveBound() {
		return 15;
	}

	protected float getThickness(Random p_64834_) {
		float f = p_64834_.nextFloat() * 2.0F + p_64834_.nextFloat();
		if (p_64834_.nextInt(10) == 0) {
			f *= p_64834_.nextFloat() * p_64834_.nextFloat() * 3.0F + 1.0F;
		}

		return f;
	}

	protected double getYScale() {
		return 1.0D;
	}

	protected void createRoom(CarvingContext p_159240_, CaveCarverConfiguration p_159241_, ChunkAccess p_159242_, Function<BlockPos, Biome> p_159243_, long p_159244_, Aquifer p_159245_, double p_159246_, double p_159247_, double p_159248_, float p_159249_, double p_159250_, BitSet p_159251_, WorldCarver.CarveSkipChecker p_159252_) {
		double d0 = 1.5D + (double)(Mth.sin(((float)Math.PI / 2F)) * p_159249_);
		double d1 = d0 * p_159250_;
		this.carveEllipsoid(p_159240_, p_159241_, p_159242_, p_159243_, p_159244_, p_159245_, p_159246_ + 1.0D, p_159247_, p_159248_, d0, d1, p_159251_, p_159252_);
	}

	protected void createTunnel(CarvingContext p_159220_, CaveCarverConfiguration p_159221_, ChunkAccess p_159222_, Function<BlockPos, Biome> p_159223_, long p_159224_, Aquifer p_159225_, double p_159226_, double p_159227_, double p_159228_, double p_159229_, double p_159230_, float p_159231_, float p_159232_, float p_159233_, int p_159234_, int p_159235_, double p_159236_, BitSet p_159237_, WorldCarver.CarveSkipChecker p_159238_) {
		Random random = new Random(p_159224_);
		int i = random.nextInt(p_159235_ / 2) + p_159235_ / 4;
		boolean flag = random.nextInt(6) == 0;
		float f = 0.0F;
		float f1 = 0.0F;

		for(int j = p_159234_; j < p_159235_; ++j) {
			double d0 = 1.5D + (double)(Mth.sin((float)Math.PI * (float)j / (float)p_159235_) * p_159231_);
			double d1 = d0 * p_159236_;
			float f2 = Mth.cos(p_159233_);
			p_159226_ += Mth.cos(p_159232_) * f2;
			p_159227_ += Mth.sin(p_159233_);
			p_159228_ += Mth.sin(p_159232_) * f2;
			p_159233_ = p_159233_ * (flag ? 0.92F : 0.7F);
			p_159233_ = p_159233_ + f1 * 0.1F;
			p_159232_ += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
			if (j == i && p_159231_ > 1.0F) {
				this.createTunnel(p_159220_, p_159221_, p_159222_, p_159223_, random.nextLong(), p_159225_, p_159226_, p_159227_, p_159228_, p_159229_, p_159230_, random.nextFloat() * 0.5F + 0.5F, p_159232_ - ((float)Math.PI / 2F), p_159233_ / 3.0F, j, p_159235_, 1.0D, p_159237_, p_159238_);
				this.createTunnel(p_159220_, p_159221_, p_159222_, p_159223_, random.nextLong(), p_159225_, p_159226_, p_159227_, p_159228_, p_159229_, p_159230_, random.nextFloat() * 0.5F + 0.5F, p_159232_ + ((float)Math.PI / 2F), p_159233_ / 3.0F, j, p_159235_, 1.0D, p_159237_, p_159238_);
				return;
			}

			if (random.nextInt(4) != 0) {
				if (!canReach(p_159222_.getPos(), p_159226_, p_159228_, j, p_159235_, p_159231_)) {
					return;
				}

				this.carveEllipsoid(p_159220_, p_159221_, p_159222_, p_159223_, p_159224_, p_159225_, p_159226_, p_159227_, p_159228_, d0 * p_159229_, d1 * p_159230_, p_159237_, p_159238_);
			}
		}

	}

	private static boolean shouldSkip(double p_159196_, double p_159197_, double p_159198_, double p_159199_) {
		if (p_159197_ <= p_159199_) {
			return true;
		} else {
			return p_159196_ * p_159196_ + p_159197_ * p_159197_ + p_159198_ * p_159198_ >= 1.0D;
		}
	}
}
