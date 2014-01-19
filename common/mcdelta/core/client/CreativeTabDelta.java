package mcdelta.core.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabDelta extends CreativeTabs
{
     public ItemStack iconStack;
     
     
     
     
     public CreativeTabDelta (String s)
     {
          super(s);
     }
     
     
     
     
     @Override
     public ItemStack getIconItemStack ()
     {
          return iconStack;
     }
}
