package twilightforest.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
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
import twilightforest.client.model.entity.*;
import twilightforest.client.model.tileentity.*;
import twilightforest.client.renderer.tileentity.CasketTileEntityRenderer;

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

		event.registerLayerDefinition(TFModelLayers.ADHERENT, AdherentModel::create);
		event.registerLayerDefinition(TFModelLayers.ALPHA_YETI, AlphaYetiModel::create);
		event.registerLayerDefinition(TFModelLayers.BIGHORN_SHEEP, BighornModel::create);
		event.registerLayerDefinition(TFModelLayers.BIGHORN_SHEEP_FUR, BighornFurLayer::create);
		event.registerLayerDefinition(TFModelLayers.BLOCKCHAIN_GOBLIN, BighornFurLayer::create);
		event.registerLayerDefinition(TFModelLayers.BOAR, BighornFurLayer::create);
		event.registerLayerDefinition(TFModelLayers.BUNNY, BighornFurLayer::create);
		event.registerLayerDefinition(TFModelLayers.CARMINITE_GOLEM, BighornFurLayer::create);
		event.registerLayerDefinition(TFModelLayers.CHAIN, ChainModel::create);
		event.registerLayerDefinition(TFModelLayers.CUBE_OF_ANNIHILATION, CubeOfAnnihilationModel::create);
		event.registerLayerDefinition(TFModelLayers.DEATH_TOME, DeathTomeModel::create);
		event.registerLayerDefinition(TFModelLayers.DEER, DeerModel::create);
		event.registerLayerDefinition(TFModelLayers.FIRE_BEETLE, FireBeetleModel::create);
		event.registerLayerDefinition(TFModelLayers.HARBINGER_CUBE, HarbingerCubeModel::create);
		event.registerLayerDefinition(TFModelLayers.HELMET_CRAB, HelmetCrabModel::create);
		event.registerLayerDefinition(TFModelLayers.HYDRA_HEAD, HydraHeadModel::create);
		event.registerLayerDefinition(TFModelLayers.HYDRA, HydraModel::create);
		event.registerLayerDefinition(TFModelLayers.HYDRA_MORTAR, HydraMortarModel::create);
		event.registerLayerDefinition(TFModelLayers.HYDRA_NECK, HydraNeckModel::create);
		event.registerLayerDefinition(TFModelLayers.ICE_CRYSTAL, IceCrystalModel::create);

		event.registerLayerDefinition(TFModelLayers.REDCAP_ARMOR_INNER, () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0.7F), 64, 32));
		event.registerLayerDefinition(TFModelLayers.REDCAP_ARMOR_OUTER, () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 0.7F), 64, 32));

		event.registerLayerDefinition(TFModelLayers.CICADA, CicadaModel::create);
		event.registerLayerDefinition(TFModelLayers.FIREFLY, FireflyModel::create);
		event.registerLayerDefinition(TFModelLayers.KEEPSAKE_CASKET, CasketTileEntityRenderer::create);
		event.registerLayerDefinition(TFModelLayers.MOONWORM, MoonwormModel::create);
	}
}
