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
     public static ItemMaterial FIERY;
     public static ItemMaterial STEELEAF;
     public static ItemMaterial KNIGHTMETAL;
     
     
     
     
     @Override
     public String modid ()
     {
          return "TwilightForest";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          IRONWOOD = MaterialRegistry.add(new DeltaInfo("ironwood", 8944764, "ingotIronwood", false, true, false, false), new ToolInfo(2, 512, 6.5F, 2.0F, 25), null, new ExtraInfo(EnumChatFormatting.WHITE, new SimpleEntry<Enchantment, Integer>(Enchantment.knockback, 1), new SimpleEntry<Enchantment, Integer>(Enchantment.unbreaking, 1)));
          FIERY = MaterialRegistry.add(new DeltaInfo("fiery", 3941155, "ingotFiery", false, true, false, false), new ToolInfo(4, 1024, 9.0F, 4.0F, 10), null, new ExtraInfo(EnumChatFormatting.AQUA, new SimpleEntry<Enchantment, Integer>(Enchantment.fireAspect, 1), null, new SimpleEntry<String, Integer>("blazeRod", 16501540)));
          STEELEAF = MaterialRegistry.add(new DeltaInfo("stealeaf", 7184990, "ingotSteeleaf", false, true, false, false), new ToolInfo(3, 131, 8.0F, 3.0F, 9), null, new ExtraInfo(EnumChatFormatting.YELLOW, new SimpleEntry<Enchantment, Integer>(Enchantment.looting, 2), new SimpleEntry<Enchantment, Integer>(Enchantment.efficiency, 2)));
          KNIGHTMETAL = MaterialRegistry.add(new DeltaInfo("niteMetal", 12900014, "ingotKnightMetal", false, true, false, false), new ToolInfo(3, 512, 8.0F, 3.0F, 8), null, new ExtraInfo(EnumChatFormatting.AQUA));
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          final Item ironwoodIngot = Assets.findItemWithName("ironwoodIngot");
          if (ironwoodIngot != null)
          {
               OreDictionary.registerOre("ingotIronwood", ironwoodIngot);
          }
          
          final Item fieryIngot = Assets.findItemWithName("fieryIngot");
          if (fieryIngot != null)
          {
               OreDictionary.registerOre("ingotFiery", fieryIngot);
          }
          
          final Item steeleafIngot = Assets.findItemWithName("steeleafIngot");
          if (steeleafIngot != null)
          {
               OreDictionary.registerOre("ingotSteeleaf", steeleafIngot);
          }
          
          final Item knightMetal = Assets.findItemWithName("knightMetal");
          if (knightMetal != null)
          {
               OreDictionary.registerOre("ingotKnightMetal", knightMetal);
          }
          
          OreDictionary.registerOre("blazeRod", Item.blazeRod);
     }
}
