package twilightforest.entity.boss;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;

public class HydraNeckEntity extends HydraPartEntity {

	public static final ResourceLocation RENDERER = TwilightForestMod.prefix("hydra_neck");

	public final HydraHeadEntity head;

	public HydraNeckEntity(HydraHeadEntity head) {
		super(head.getParent(), 2F, 2F);
		this.head = head;
	}

	@OnlyIn(Dist.CLIENT)
	public ResourceLocation renderer() {
		return RENDERER;
	}
}
