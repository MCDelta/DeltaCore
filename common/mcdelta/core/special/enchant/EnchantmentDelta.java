package mcdelta.core.special.enchant;

import java.util.logging.Level;

import mcdelta.core.DeltaCore;
import mcdelta.core.EnumMCDMods;
import mcdelta.core.Logger;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.ModContainer;

public class EnchantmentDelta extends Enchantment
{
     private EnumMCDMods                 mod;



     public EnchantmentDelta (String s, int rarity, EnumEnchantmentType enchType)
     {
          this(EnumMCDMods.DELTA_CORE, s, rarity, enchType);
     }



     public EnchantmentDelta (EnumMCDMods m, String s, int rarity, EnumEnchantmentType enchType)
     {
          super(DeltaCore.config.getEnchantmentID(m, s), rarity, enchType);
          this.mod = m;
          this.name = s;

          if (!StatCollector.func_94522_b(this.getName()))
          {
               DeltaCore.localizationWarnings.append("- " + this.getName() + " \n");
          }
     }



     @Override
     public String getName ()
     {
          return "enchantment." + getModid().toLowerCase() + ":" + this.name;
     }



     public String getModid ()
     {
          return mod.modid;
     }



     public static void log (Object... message)
     {
          Logger.log(Level.INFO, message);
     }



     public static void loadEnchants ()
     {
          for (ModDelta mod : ModDelta.mods)
          {
               mod.doThings(ModDelta.Stage.LOAD_ENCHANTS);
          }
     }
}
