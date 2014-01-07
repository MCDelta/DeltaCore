package mcdelta.core.support;

import mcdelta.core.material.ArmorInfo;
import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;

public class SupportEssentialAlloys implements ILimitedModSupport
{
     public static ItemMaterial BRONZE;
     public static ItemMaterial MAGIC;
     public static ItemMaterial STEEL;
     
     
     
     
     @Override
     public String modid ()
     {
          return "essentialalloys";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          BRONZE = MaterialRegistry.add(new DeltaInfo("bronze", 0xd3b838, "ingotBronze", true, true, false, false), new ToolInfo(2, 418, 9.0F, 2.0F, 22), null, null);
          MAGIC = MaterialRegistry.add(new DeltaInfo("magic", 0x7340ad, "ingotMagic", true, true, false, true), new ToolInfo(3, 205, 12.0F, 1.0F, 44), null, null);
          STEEL = MaterialRegistry.add(new DeltaInfo("steel", 0x637080, "ingotSteel", true, true, true, false), new ToolInfo(4, 1111, 4.0F, 3.0F, 12), new ArmorInfo(33, new int[]
          { 3, 8, 6, 3 }, 10), null);
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          
     }
}
