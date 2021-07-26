package twilightforest.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;

public class TFModelLayers extends ModelLayers {

	public static final ModelLayerLocation ARCTIC_ARMOR = register("arctic_armor");
	public static final ModelLayerLocation FIERY_ARMOR = register("fiery_armor");
	public static final ModelLayerLocation KNIGHTMETAL_ARMOR = register("knightmetal_armor");
	public static final ModelLayerLocation PHANTOM_ARMOR = register("phantom_armor");
	public static final ModelLayerLocation YETI_ARMOR = register("yeti_armor");

	public static final ModelLayerLocation ALPHA_YETI_TROPHY = register("alpha_yeti_trophy");
	public static final ModelLayerLocation HYDRA_TROPHY = register("hydra_trophy");
	public static final ModelLayerLocation KNIGHT_PHANTOM_TROPHY = register("knight_phantom_trophy");
	public static final ModelLayerLocation KNIGHT_PHANTOM_ARMOR_TROPHY = register("knight_phantom_armor_trophy", "outer");
	public static final ModelLayerLocation LICH_TROPHY = register("lich_trophy");
	public static final ModelLayerLocation MINOSHROOM_TROPHY = register("minoshroom_trophy");
	public static final ModelLayerLocation NAGA_TROPHY = register("naga_trophy");
	public static final ModelLayerLocation QUEST_RAM_TROPHY = register("quest_ram_trophy");
	public static final ModelLayerLocation SNOW_QUEEN_TROPHY = register("snow_queen_trophy");
	public static final ModelLayerLocation UR_GHAST_TROPHY = register("ur_ghast_trophy");

	private static ModelLayerLocation register(String p_171294_) {
		return register(p_171294_, "main");
	}

	private static ModelLayerLocation register(String p_171301_, String p_171302_) {
		return new ModelLayerLocation(new ResourceLocation(TwilightForestMod.ID, p_171301_), p_171302_);
	}
}
