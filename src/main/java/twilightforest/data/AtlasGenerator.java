package twilightforest.data;

import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.client.resources.model.Material;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;
import twilightforest.TwilightForestMod;
import twilightforest.client.renderer.tileentity.TwilightChestRenderer;

import java.util.Optional;

public class AtlasGenerator extends SpriteSourceProvider {

	public AtlasGenerator(PackOutput output, ExistingFileHelper helper) {
		super(output, helper, TwilightForestMod.ID);
	}

	@Override
	protected void addSources() {
		TwilightChestRenderer.MATERIALS.values().stream().flatMap(e -> e.values().stream()).map(Material::texture)
				.forEach(resourceLocation -> this.atlas(CHESTS_ATLAS).addSource(new SingleFile(resourceLocation, Optional.empty())));
		this.atlas(SHIELD_PATTERNS_ATLAS).addSource(new SingleFile(TwilightForestMod.prefix("model/knightmetal_shield"), Optional.empty()));
	}
}
