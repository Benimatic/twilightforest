package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

/**
 * Created by Drullkus on 12/12/17.
 */
public class BlockTFNagastonePillar extends BlockTFDirectionalRotatedPillar {

    protected BlockTFNagastonePillar() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
        //this.setCreativeTab(TFItems.creativeTab); TODO 1.14
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y).with(REVERSED, false));
    }
}
