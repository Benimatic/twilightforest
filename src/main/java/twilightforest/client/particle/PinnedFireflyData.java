package twilightforest.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class PinnedFireflyData implements IParticleData {
	public final int follow;

	public PinnedFireflyData(int follow) {
		this.follow = follow;
	}

	@Override
	public ParticleType<?> getType() {
		return TFParticleType.FIREFLY_PINNED.get();
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
