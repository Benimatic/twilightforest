package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class UnfinishedBlock extends Block {
    private final boolean nyi;

    public UnfinishedBlock(Properties properties, boolean nyi) {
        super(properties);
        this.nyi = nyi;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(nyi) {
            tooltip.add(new TranslatableComponent("twilightforest.misc.nyi"));
        } else {
            tooltip.add(new TranslatableComponent("twilightforest.misc.wip0"));
            tooltip.add(new TranslatableComponent("twilightforest.misc.wip1"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
