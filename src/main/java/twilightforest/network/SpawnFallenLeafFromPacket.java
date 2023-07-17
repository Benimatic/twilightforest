package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.client.particle.data.LeafParticleData;

import java.util.Random;
import java.util.function.Supplier;

public class SpawnFallenLeafFromPacket {

	private final BlockPos pos;
	private final Vec3 motion;

	public SpawnFallenLeafFromPacket(FriendlyByteBuf buf) {
		pos = buf.readBlockPos();
		motion = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	public SpawnFallenLeafFromPacket(BlockPos pos, Vec3 motion) {
		this.pos = pos;
		this.motion = motion;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(this.pos);
		buf.writeDouble(this.motion.x);
		buf.writeDouble(this.motion.y);
		buf.writeDouble(this.motion.z);
	}

	public static class Handler {

		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(SpawnFallenLeafFromPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {
					Random rand = new Random();
					Level world = Minecraft.getInstance().level;
					int color = Minecraft.getInstance().getBlockColors().getColor(Blocks.OAK_LEAVES.defaultBlockState(), world, message.pos, 0);
					int r = Mth.clamp(((color >> 16) & 0xFF) + rand.nextInt(0x22) - 0x11, 0x00, 0xFF);
					int g = Mth.clamp(((color >> 8) & 0xFF) + rand.nextInt(0x22) - 0x11, 0x00, 0xFF);
					int b = Mth.clamp((color & 0xFF) + rand.nextInt(0x22) - 0x11, 0x00, 0xFF);
					world.addParticle(new LeafParticleData(r, g, b),
							message.pos.getX() + world.getRandom().nextFloat(),
							message.pos.getY(),
							message.pos.getZ() + world.getRandom().nextFloat(),

							(world.getRandom().nextFloat() * -0.5F) * message.motion.x(),
							world.getRandom().nextFloat() * 0.5F + 0.25F,
							(world.getRandom().nextFloat() * -0.5F) * message.motion.z()
					);
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
