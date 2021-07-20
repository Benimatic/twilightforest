package twilightforest.client.particle.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import twilightforest.client.particle.TFParticleType;

public class PinnedFireflyData implements IParticleData {

	public final int follow;

	public PinnedFireflyData(int follow) {
		this.follow = follow;
	}

	@Override
	public ParticleType<?> getType() {
		return TFParticleType.FIREFLY_PINNED.get();
	}

	public static Codec<PinnedFireflyData> codecFirefly() {
		return Codec.INT.xmap(PinnedFireflyData::new, (obj) -> obj.follow);
	}

	@Override
	public void write(PacketBuffer buf) {
		buf.writeVarInt(follow);
	}

	@Override
	public String getParameters() {
		return Integer.toString(follow);
	}

	public static class Deserializer implements IParticleData.IDeserializer<PinnedFireflyData> {
		@Override
		public PinnedFireflyData deserialize(ParticleType<PinnedFireflyData> type, StringReader rdr) throws CommandSyntaxException {
			rdr.skipWhitespace();
			return new PinnedFireflyData(rdr.readInt());
		}

		@Override
		public PinnedFireflyData read(ParticleType<PinnedFireflyData> type, PacketBuffer buf) {
			return new PinnedFireflyData(buf.readVarInt());
		}
	}
}
