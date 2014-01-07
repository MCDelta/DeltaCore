package mcdelta.core.support;

import mcdelta.core.assets.Assets;
import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ExtraInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class SupportThaumcraft implements ILimitedModSupport
{
     public static ItemMaterial THAUMIUM;
     
     
     
     
     @Override
     public String modid ()
     {
          return "Thaumcraft";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          THAUMIUM = MaterialRegistry.add(new DeltaInfo("thaumium", 7232680, "ingotThaumium", false, true, false, false), new ToolInfo(3, 400, 7.0F, 2.0F, 22), null, new ExtraInfo(EnumChatFormatting.YELLOW));
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          final Item thaumiumIngot = Assets.findItemWithName("ItemResource");
          
          if (thaumiumIngot != null)
          {
               OreDictionary.registerOre("ingotThaumium", new ItemStack(thaumiumIngot, 1, 2));
          }
     }
}
