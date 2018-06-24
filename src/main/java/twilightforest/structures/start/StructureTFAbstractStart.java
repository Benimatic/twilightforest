package twilightforest.structures.start;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureTFAbstractStart extends StructureStart {
    public static int NUM_LOCKS = 4;
    public boolean isConquered;
    public byte[] lockBytes = new byte[NUM_LOCKS];

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setBoolean("Conquered", this.isConquered);
        par1NBTTagCompound.setByteArray("Locks", this.lockBytes);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.isConquered = nbttagcompound.getBoolean("Conquered");
        this.lockBytes = nbttagcompound.getByteArray("Locks");
    }
}
