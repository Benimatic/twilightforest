package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MiniatureStructureBlock extends Block {
    public MiniatureStructureBlock() {
        super(Properties.of(Material.BARRIER).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(0.75F).noOcclusion());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("twilightforest.misc.nyi"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
