package mcdelta.core.support;

import net.minecraft.util.EnumChatFormatting;
import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ExtraInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;

public class ThaumcraftSupport implements LimitedModSupport
{
     public static ItemMaterial THAUMIUM;
     
     
     
     
     @Override
     public String modid ()
     {
          return "Thaumcraft";
     }
     
     
     
     
     @Override
     public void modLoaded ()
     {
          THAUMIUM = MaterialRegistry.add(new DeltaInfo("thaumium", 7232680, "ingotMagic", false, true, false, false), new ToolInfo(3, 400, 7.0F, 2.0F, 22), null, new ExtraInfo(EnumChatFormatting.YELLOW));
     }
}
