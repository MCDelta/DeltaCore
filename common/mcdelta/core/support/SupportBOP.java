package mcdelta.core.support;

import java.util.AbstractMap.SimpleEntry;

import mcdelta.core.assets.Assets;
import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ExtraInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SupportBOP implements LimitedModSupport
{
     public static ItemMaterial MUD;
     public static ItemMaterial AMETHYST;
     
     
     
     
     @Override
     public String modid ()
     {
          return "BiomesOPlenty";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          MUD = MaterialRegistry.add(new DeltaInfo("mud", 6638381, "ballMud", false, true, false, false), new ToolInfo(0, 32, 0.5F, 0.0F, 1), null, null);
          AMETHYST = MaterialRegistry.add(new DeltaInfo("amethyst", 16733439, "gemAmethyst", false, true, false, false), new ToolInfo(4, 2013, 15.0F, 5.0F, 16), null, new ExtraInfo(new SimpleEntry<String, Integer>("ingotIron", MaterialRegistry.IRON.color())));
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          final Item mudball = Assets.findItemWithName("bop.mudball");
          if (mudball != null)
               OreDictionary.registerOre("ballMud", new ItemStack(mudball));
          
          final Item miscItems = Assets.findItemWithName("bop.miscItems");
          if (miscItems != null)
               OreDictionary.registerOre("gemAmethyst", new ItemStack(miscItems, 1, 2));
     }
}
