package twilightforest.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.armor.ArcticArmorModel;
import twilightforest.client.model.armor.FieryArmorModel;
import twilightforest.client.model.armor.KnightmetalArmorModel;
import twilightforest.client.model.armor.YetiArmorModel;
import twilightforest.client.model.tileentity.*;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TFLayerDefinitions {

	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

		event.registerLayerDefinition(TFModelLayers.ARCTIC_ARMOR, () -> ArcticArmorModel.addPieces(CubeDeformation.NONE));
		event.registerLayerDefinition(TFModelLayers.FIERY_ARMOR, () -> LayerDefinition.create(FieryArmorModel.createMesh(CubeDeformation.NONE, 0.5F), 64, 32));
		event.registerLayerDefinition(TFModelLayers.KNIGHTMETAL_ARMOR, KnightmetalArmorModel::addPieces);
		event.registerLayerDefinition(TFModelLayers.PHANTOM_ARMOR, KnightmetalArmorModel::addPieces);
		event.registerLayerDefinition(TFModelLayers.YETI_ARMOR, () -> YetiArmorModel.addPieces(CubeDeformation.NONE, 0.5F));

		event.registerLayerDefinition(TFModelLayers.ALPHA_YETI_TROPHY, AlphaYetiTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.HYDRA_TROPHY, HydraTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.KNIGHT_PHANTOM_TROPHY, KnightPhantomTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.KNIGHT_PHANTOM_ARMOR_TROPHY, PhantomArmorTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.LICH_TROPHY, LichTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.MINOSHROOM_TROPHY, MinoshroomTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.NAGA_TROPHY, NagaTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.QUEST_RAM_TROPHY, QuestRamTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.SNOW_QUEEN_TROPHY, SnowQueenTrophyModel::createHead);
		event.registerLayerDefinition(TFModelLayers.UR_GHAST_TROPHY, UrGhastTrophyModel::createHead);
	}
}
