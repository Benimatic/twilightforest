package twilightforest.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import javax.annotation.Nonnull;

public class LeafParticleData implements IParticleData {
	public final int r;
	public final int g;
	public final int b;

	public LeafParticleData(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Nonnull
	@Override
	public ParticleType<?> getType() {
		return TFParticleType.FALLEN_LEAF.get();
	}

	@Override
	public void write(@Nonnull PacketBuffer buf) {
		buf.writeVarInt(r);
		buf.writeVarInt(g);
		buf.writeVarInt(b);
	}

	@Nonnull
	@Override
	public String getParameters() {
		return String.format("%d %d %d", r, g, b);
	}

	public static class Deserializer implements IParticleData.IDeserializer<LeafParticleData> {
		@Nonnull
		@Override
		public LeafParticleData deserialize(@Nonnull ParticleType<LeafParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
			reader.skipWhitespace();
			int r = reader.readInt();
			reader.skipWhitespace();
			int g = reader.readInt();
			reader.skipWhitespace();
			int b = reader.readInt();
			return new LeafParticleData(r, g, b);
		}

		@Nonnull
		@Override
		public LeafParticleData read(@Nonnull ParticleType<LeafParticleData> type, PacketBuffer buf) {
			return new LeafParticleData(buf.readVarInt(), buf.readVarInt(), buf.readVarInt());
		}
	}
}
