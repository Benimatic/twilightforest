package twilightforest.compat;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import twilightforest.compat.tcon.TraitPrecipitate;
import twilightforest.compat.tcon.TraitSynergy;
import twilightforest.compat.tcon.TraitTwilit;
import twilightforest.compat.tcon.TraitStalwart;

@SuppressWarnings("WeakerAccess")
public class TConObjects {
    public static Material nagascale    = new Material("nagascale"    , 0x32_5D_25);
    public static Material steeleaf     = new Material("steeleaf"     , 0x52_87_3A);
    public static Material fierymetal   = new Material("fierymetal"   , 0xFD_D4_5D);
    public static Material knightmetal  = new Material("knightmetal"  , 0xC4_E6_AE);
    public static Material ravenFeather = new Material("raven_feather", 0x47_4C_52);

    public static final AbstractTrait twilit      = new TraitTwilit();
    public static final AbstractTrait precipitate = new TraitPrecipitate();
    public static final AbstractTrait valiant     = new TraitStalwart();
    public static final AbstractTrait synergy     = new TraitSynergy();
}
