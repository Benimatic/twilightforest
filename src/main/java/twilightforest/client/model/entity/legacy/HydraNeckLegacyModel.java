package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.boss.HydraNeckEntity;

public class HydraNeckLegacyModel extends ListModel<HydraNeckEntity> {

    ModelPart neck;

    public HydraNeckLegacyModel() {
        texWidth = 512;
        texHeight = 256;

        neck = new ModelPart(this/*, "neck"*/);
        neck.texOffs(128, 136).addBox(-16F, -16F, -16F, 32, 32, 32);
        neck.texOffs(128, 200).addBox(-2F, -23F, 0F, 4, 24, 24);
        neck.setPos(0F, 0F, 0F);
    }

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//	}

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(neck);
    }

    @Override
    public void setupAnim(HydraNeckEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        neck.yRot = netHeadYaw / 57.29578F;
        neck.xRot = headPitch / 57.29578F;
    }
}