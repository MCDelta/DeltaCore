package mcdelta.core.material;

import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EnumChatFormatting;

public class ExtraInfo
{
     private EnumChatFormatting          nameColor;
     private Entry<Enchantment, Integer> weaponEnchant;
     private Entry<Enchantment, Integer> toolEnchant;
     private Entry<String, Integer>      nonStickCrafter;
     
     
     
     
     public ExtraInfo (EnumChatFormatting e)
     {
          this(e, null, null);
     }
     
     
     
     
     public ExtraInfo (EnumChatFormatting e, Entry<Enchantment, Integer> entr1, Entry<Enchantment, Integer> entr2)
     {
          this(e, entr1, entr2, null);
     }
     
     
     
     
     public ExtraInfo (EnumChatFormatting e, Entry<Enchantment, Integer> entr1, Entry<Enchantment, Integer> entr2, Entry<String, Integer> entr3)
     {
          this.nameColor = e;
          this.weaponEnchant = entr1;
          this.toolEnchant = entr2;
          this.nonStickCrafter = entr3;
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
     
     
     
     
     public Entry<String, Integer> nonStickCrafter ()
     {
          return nonStickCrafter;
     }
}
