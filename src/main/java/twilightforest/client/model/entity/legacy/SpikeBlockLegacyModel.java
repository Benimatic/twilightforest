package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.SpikeBlockEntity;

public class SpikeBlockLegacyModel extends ListModel<SpikeBlockEntity> {
	ModelPart block;
	ModelPart[] spikes = new ModelPart[27];

	public SpikeBlockLegacyModel() {

		block = new ModelPart(this, 32, 16);
		block.addBox(-4F, -8F, -4F, 8, 8, 8, 0F);
		block.setPos(0F, 0F, 0F);

		for (int i = 0; i < spikes.length; i++) {
			spikes[i] = new ModelPart(this, 56, 16);
			spikes[i].addBox(-1F, -1F, -1F, 2, 2, 2, 0F);
			block.addChild(spikes[i]);
		}

		// X
		spikes[2].x = 4;
		spikes[3].x = 4;
		spikes[4].x = 4;
		spikes[11].x = 4;
		spikes[12].x = 5;
		spikes[13].x = 4;
		spikes[20].x = 4;
		spikes[21].x = 4;
		spikes[22].x = 4;

		spikes[6].x = -4;
		spikes[7].x = -4;
		spikes[8].x = -4;
		spikes[15].x = -4;
		spikes[16].x = -5;
		spikes[17].x = -4;
		spikes[24].x = -4;
		spikes[25].x = -4;
		spikes[26].x = -4;

		// Y
		spikes[0].y = -9;
		spikes[1].y = -8;
		spikes[2].y = -8;
		spikes[3].y = -8;
		spikes[4].y = -8;
		spikes[5].y = -8;
		spikes[6].y = -8;
		spikes[7].y = -8;
		spikes[8].y = -8;

		spikes[9].y = -4; // this spike is not really there
		spikes[10].y = -4;
		spikes[11].y = -4;
		spikes[12].y = -4;
		spikes[13].y = -4;
		spikes[14].y = -4;
		spikes[15].y = -4;
		spikes[16].y = -4;
		spikes[17].y = -4;

		spikes[18].y = 1;

		// Z
		spikes[1].z = 4;
		spikes[2].z = 4;
		spikes[8].z = 4;
		spikes[10].z = 4;
		spikes[11].z = 5;
		spikes[17].z = 4;
		spikes[19].z = 4;
		spikes[20].z = 4;
		spikes[26].z = 4;

		spikes[4].z = -4;
		spikes[5].z = -4;
		spikes[6].z = -4;
		spikes[13].z = -4;
		spikes[14].z = -5;
		spikes[15].z = -4;
		spikes[22].z = -4;
		spikes[23].z = -4;
		spikes[24].z = -4;

		// rotation
		float fourtyFive = (float) (Math.PI / 4F);

		spikes[1].xRot = fourtyFive;
		spikes[5].xRot = fourtyFive;
		spikes[19].xRot = fourtyFive;
		spikes[23].xRot = fourtyFive;

		spikes[11].yRot = fourtyFive;
		spikes[13].yRot = fourtyFive;
		spikes[15].yRot = fourtyFive;
		spikes[17].yRot = fourtyFive;

		spikes[3].zRot = fourtyFive;
		spikes[7].zRot = fourtyFive;
		spikes[21].zRot = fourtyFive;
		spikes[25].zRot = fourtyFive;

		spikes[2].xRot = -55F / (180F / (float) Math.PI);
		spikes[2].yRot = fourtyFive;
		spikes[24].xRot = -55F / (180F / (float) Math.PI);
		spikes[24].yRot = fourtyFive;

		spikes[4].xRot = -35F / (180F / (float) Math.PI);
		spikes[4].yRot = -fourtyFive;
		spikes[26].xRot = -35F / (180F / (float) Math.PI);
		spikes[26].yRot = -fourtyFive;

		spikes[6].yRot = fourtyFive;
		spikes[6].xRot = -35F / (180F / (float) Math.PI);
		spikes[20].yRot = fourtyFive;
		spikes[20].xRot = -35F / (180F / (float) Math.PI);

		spikes[8].xRot = -55F / (180F / (float) Math.PI);
		spikes[8].yRot = -fourtyFive;
		spikes[22].xRot = -55F / (180F / (float) Math.PI);
		spikes[22].yRot = -fourtyFive;
	}

	@Override
	public void setupAnim(SpikeBlockEntity entity, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(block);
	}
}
