package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;


public class StepSoundTFInsect extends SoundType {

	public StepSoundTFInsect(String par1Str, float par2, float par3) {
		super(par1Str, par2, par3);
	}
	
    @Override
	public String getBreakSound()
    {
        return "mob.slime.big";
    }

    @Override
    public String getStepSound()
    {
        return "mob.slime.big";
    }

    @Override
    public String getPlaceSound()
    {
        return "mob.slime.big";
    }

}
