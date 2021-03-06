package mcdelta.core.material;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EnumChatFormatting;

public class ExtraInfo
{
     private final EnumChatFormatting          nameColor;
     private final Entry<Enchantment, Integer> weaponEnchant;
     private final Entry<Enchantment, Integer> toolEnchant;
     private final Entry<String, Integer>      nonStickCrafter;
     private final Entry<String, Double>       armorSharedAttr;
     
     
     
     
     public ExtraInfo (final EnumChatFormatting e, final Entry<Enchantment, Integer> entr1, final Entry<Enchantment, Integer> entr2, final Entry<String, Integer> entr3, Entry<String, Double> entr4)
     {
          nameColor = e;
          weaponEnchant = entr1;
          toolEnchant = entr2;
          nonStickCrafter = entr3;
          armorSharedAttr = entr4;
     }
     
     
     
     
     public ExtraInfo (Entry<String, Double> entr)
     {
          this(EnumChatFormatting.WHITE, null, null, null, entr);
     }
     
     
     
     
     public ExtraInfo (final EnumChatFormatting e)
     {
          this(e, null, null);
     }
     
     
     
     
     public ExtraInfo (final EnumChatFormatting e, final Entry<Enchantment, Integer> entr1, final Entry<Enchantment, Integer> entr2)
     {
          this(e, entr1, entr2, null, null);
     }
     
     
     
     
     public ExtraInfo (SimpleEntry<String, Integer> entr)
     {
          this(EnumChatFormatting.WHITE, null, null, entr, null);
     }
     
     
     
     
     public EnumChatFormatting nameColor ()
     {
          return nameColor;
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
     
     
     
     
     public Entry<String, Double> armorSharedAttr ()
     {
          return armorSharedAttr;
     }
}
