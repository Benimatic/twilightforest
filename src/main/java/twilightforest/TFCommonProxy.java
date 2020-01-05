package twilightforest;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import twilightforest.client.particle.TFParticleType;
import twilightforest.inventory.ContainerTFUncrafting;
import twilightforest.tileentity.TileEntityTFCinderFurnace;
import twilightforest.tileentity.critters.*;

//TODO 1.14: Splinter this from Proxies. We're not using them anymore. Meantime, here's what to keep and toss:
public class TFCommonProxy implements IGuiHandler /* TODO: JESUS CHRIST WHAT THE FUCK WHY WAS THIS A IGUIHANDLER?! */ {

	public void preInit() {}

	public void init() {}

	//TODO: YEET
	@Override
	public Object getServerGuiElement(int id, PlayerEntity player, World world, int x, int y, int z) {
		if (id == TwilightForestMod.GUI_ID_UNCRAFTING) {
			return new ContainerTFUncrafting(player.inventory, world, x, y, z);
		} else if (id == TwilightForestMod.GUI_ID_FURNACE) {
			return new ContainerFurnace(player.inventory, (TileEntityTFCinderFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		} else {
			return null;
		}
	}

	//TODO: YEET
	@Override
	public Object getClientGuiElement(int id, PlayerEntity player, World world, int x, int y, int z) {
		if (id == TwilightForestMod.GUI_ID_UNCRAFTING) {
			return new twilightforest.client.GuiTFGoblinCrafting(player.inventory, world, x, y, z);
		} else if (id == TwilightForestMod.GUI_ID_FURNACE) {
			return new GuiFurnace(player.inventory, (TileEntityTFCinderFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		} else {
			return null;
		}
	}

	public boolean doesPlayerHaveAdvancement(PlayerEntity player, ResourceLocation advId) {
		if (player instanceof ServerPlayerEntity) {
			Advancement adv = ((ServerPlayerEntity) player).getServerWorld().getAdvancementManager().getAdvancement(advId);
			return adv != null && ((ServerPlayerEntity) player).getAdvancements().getProgress(adv).isDone();
		}
		return false;
	}

	//TODO: Move to TileEntityType?
	public TileEntityTFCicada getNewCicadaTE() {
		return new TileEntityTFCicada();
	}

	//TODO: Move to TileEntityType?
	public TileEntityTFFirefly getNewFireflyTE() {
		return new TileEntityTFFirefly();
	}

	//TODO: Move to TileEntityType?
	public TileEntityTFMoonworm getNewMoonwormTE() {
		return new TileEntityTFMoonworm();
	}

	//TODO: Move to TileEntityType
	public void registerCritterTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTFFirefly.class,  prefix("firefly" ));
		GameRegistry.registerTileEntity(TileEntityTFCicada.class,   prefix("cicada"  ));
		GameRegistry.registerTileEntity(TileEntityTFMoonworm.class, prefix("moonworm"));
	}

	protected static ResourceLocation prefix(String name) {
		return new ResourceLocation(TwilightForestMod.ID, name);
	}
}
