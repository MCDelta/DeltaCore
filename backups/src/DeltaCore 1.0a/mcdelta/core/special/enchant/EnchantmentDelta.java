package mcdelta.core.special.enchant;

import mcdelta.core.DeltaMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentDelta extends Enchantment
{
     protected int    enchantLvlMin = 0;
     protected int    enchantLvlMax = 5;
     private DeltaMod mod;
     
     
     
     
     public EnchantmentDelta (DeltaMod deltaMod, int id, String name, int weight, EnumEnchantmentType type)
     {
          super(id, weight, type);
          this.setName(name);
          this.name = name;
          this.mod = deltaMod;
     }
     
     
     
     
     public EnchantmentDelta setMinMax (int i, int i2)
     {
          this.enchantLvlMin = i;
          this.enchantLvlMax = i2;
          return this;
     }
     
     
     
     
     @Override
     public int getMinEnchantability (int enchLevel)
     {
          return enchantLvlMin;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (int enchLevel)
     {
          return enchantLvlMax;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 1;
     }
     
     
     
     
     @Override
     public String getTranslatedName (int par1)
     {
          String s = StatCollector.translateToLocal("enchant." + mod.getModid() + ":" + name);
          return s + " " + StatCollector.translateToLocal("enchantment.level." + par1);
     }
}
