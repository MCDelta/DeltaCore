package mcdelta.core.special.enchant;

import java.util.logging.Level;

import mcdelta.core.DeltaCore;
import mcdelta.core.EnumMCDMods;
import mcdelta.core.Logger;
import mcdelta.core.ModDelta;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentDelta extends Enchantment
{
    private final EnumMCDMods mod;

    public EnchantmentDelta(String s, int rarity, EnumEnchantmentType enchType)
    {
        this(EnumMCDMods.DELTA_CORE, s, rarity, enchType);
    }

    public EnchantmentDelta(EnumMCDMods m, String s, int rarity, EnumEnchantmentType enchType)
    {
        super(DeltaCore.config.getEnchantmentID(m, s), rarity, enchType);
        mod = m;
        name = s;

        if (!StatCollector.func_94522_b(getName()))
        {
            DeltaCore.localizationWarnings.append("- " + getName() + " \n");
        }
    }

    @Override
    public String getName()
    {
        return "enchantment." + getModid().toLowerCase() + ":" + name;
    }

    public String getModid()
    {
        return mod.modid;
    }

    public static void log(Object... message)
    {
        Logger.log(Level.INFO, message);
    }

    public static void loadEnchants()
    {
        for (ModDelta mod : ModDelta.mods)
        {
            mod.doThings(ModDelta.Stage.LOAD_ENCHANTS);
        }
    }
}
