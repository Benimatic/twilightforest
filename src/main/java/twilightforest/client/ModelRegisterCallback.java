package twilightforest.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ModelRegisterCallback {
    /**
     * Do whatever is necessary to register your models. (Statemappers, custom meshers, etc.)
     */
    @SideOnly(Side.CLIENT)
    void registerModel();
}
