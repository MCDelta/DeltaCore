package mcdelta.core.support;

import java.util.AbstractMap.SimpleEntry;

import mcdelta.core.assets.Assets;
import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ExtraInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class SupportTwilightForest implements LimitedModSupport
{
     public static ItemMaterial IRONWOOD;
     
     
     
     
     @Override
     public String modid ()
     {
          return "TwilightForest";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          IRONWOOD = MaterialRegistry.add(new DeltaInfo("ironwood", 8944764, "ingotIronwood", false, true, false, false), new ToolInfo(2, 512, 6.5F, 2.0F, 25), null, new ExtraInfo(EnumChatFormatting.WHITE, new SimpleEntry<Enchantment, Integer>(Enchantment.knockback, 1), new SimpleEntry<Enchantment, Integer>(Enchantment.unbreaking, 1)));
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          Item ironWood = Assets.findItemWithName("ironwoodIngot");
          
          if (ironWood != null)
               OreDictionary.registerOre("ingotIronwood", ironWood);
     }
}
