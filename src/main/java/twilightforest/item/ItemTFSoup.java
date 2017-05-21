package twilightforest.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemSoup;
import net.minecraftforge.client.model.ModelLoader;
import twilightforest.client.ModelRegisterCallback;

public class ItemTFSoup extends ItemSoup implements ModelRegisterCallback {
    public ItemTFSoup(int healAmount) {
        super(healAmount);
    }

    @Override
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
