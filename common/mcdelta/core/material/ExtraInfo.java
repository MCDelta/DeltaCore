package mcdelta.core.material;

import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EnumChatFormatting;

public class ExtraInfo
{
     private EnumChatFormatting          nameColor;
     private Entry<Enchantment, Integer> weaponEnchant;
     private Entry<Enchantment, Integer> toolEnchant;
     
     
     
     
     public ExtraInfo (EnumChatFormatting e)
     {
          this(e, null, null);
     }
     
     
     
     
     public ExtraInfo (EnumChatFormatting e, Entry<Enchantment, Integer> entr1, Entry<Enchantment, Integer> entr2)
     {
          this.nameColor = e;
          this.weaponEnchant = entr1;
          this.toolEnchant = entr2;
     }
     
     
     
     
     public EnumChatFormatting nameColor ()
     {
          return this.nameColor;
     }
     
     
     
     
     public Entry<Enchantment, Integer> weaponEnchant ()
     {
          return weaponEnchant;
     }
     
     
     
     
     public Entry<Enchantment, Integer> toolEnchant ()
     {
          return toolEnchant;
     }
}
