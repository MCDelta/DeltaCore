package mcdelta.core;

import cpw.mods.fml.common.Loader;

public enum EnumMCDMods
{
     DELTA_CORE ("DeltaCore"), ESSENTIAL_ALLOYS ("EssentialAlloys"), TUXWEAPONS ("TuxWeapons");
     
     public final String modid;
     
     
     
     
     EnumMCDMods (String s)
     {
          this.modid = s;
     }
     
     
     
     
     public boolean isLoaded ()
     {
          return Loader.isModLoaded(modid);
     }
}
