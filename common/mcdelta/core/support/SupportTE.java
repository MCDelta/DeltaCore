package mcdelta.core.support;

import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;

public class SupportTE implements ILimitedModSupport
{
     public static ItemMaterial INVAR;
     
     
     
     
     @Override
     public String modid ()
     {
          return "ThermalExpansion";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          INVAR = MaterialRegistry.add(new DeltaInfo("invar", 0xcbcccb, "ingotInvar", false, true, false, false), new ToolInfo(2, 450, 7.0F, 2.0F, 16));
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          
     }
}
