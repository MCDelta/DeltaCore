package mcdelta.core.special.enchant;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentDelta extends Enchantment
{
     private final ModDelta mod;
     
     
     
     
     public EnchantmentDelta (final String name, final int rarity, final EnumEnchantmentType enchType)
     {
          this(DeltaCore.instance, name, rarity, enchType);
     }
     
     
     
     
     public EnchantmentDelta (final ModDelta mod, final String name, final int rarity, final EnumEnchantmentType enchType)
     {
          super(mod.config().getEnchantmentID(name), rarity, enchType);
          this.mod = mod;
          this.name = name;
          
          if (!StatCollector.func_94522_b(this.getName()))
          {
               DeltaCore.localizationWarnings.append("- " + this.getName() + " \n");
          }
     }
     
     
     
     
     @Override
     public String getName ()
     {
          return "enchantment." + this.getModid().toLowerCase() + ":" + this.name;
     }
     
     
     
     
     public String getModid ()
     {
          return this.mod.id();
     }
}
