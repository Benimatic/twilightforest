package twilightforest.block;

import net.minecraft.block.Block;


public class StepSoundTFInsect extends Block.SoundType {

	public StepSoundTFInsect(String par1Str, float par2, float par3) {
		super(par1Str, par2, par3);
	}
	
    /**
     * Used when a block breaks, EXA: Player break, Shep eating grass, etc..
     */
    @Override
	public String getBreakSound()
    {
        return "mob.slime.big";
    }

    /**
     * Used when a entity walks over, or otherwise interacts with the block.
     */
    public String getStepSound()
    {
        return "mob.slime.big";
    }

    /**
     * Used when a player places a block.
     */
    public String getPlaceSound()
    {
        return "mob.slime.big";
    }

}
