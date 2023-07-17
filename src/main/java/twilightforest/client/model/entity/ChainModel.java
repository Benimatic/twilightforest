package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class ChainModel extends ListModel<Entity> {
	private final ModelPart chain;

	public ChainModel(ModelPart root) {
		this.chain = root.getChild("chain");
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition partRoot = mesh.getRoot();

		partRoot.addOrReplaceChild("chain", CubeListBuilder.create()
						.texOffs(56, 36)
						.addBox(-1F, -1F, -1F, 2, 2, 2),
				PartPose.ZERO);

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(chain);
	}

	@Override
	public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {

	}
}
