package mcdelta.core.enchant;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentDelta extends Enchantment
{
    private final ModDelta mod;

    public EnchantmentDelta(final String name, final int rarity, final EnumEnchantmentType enchType)
    {
        this(DeltaCore.instance, name, rarity, enchType);
    }

    public EnchantmentDelta(final ModDelta mod, final String name, final int rarity, final EnumEnchantmentType enchType)
    {
        super(mod.config().getEnchantmentID(name), rarity, enchType);
        this.mod = mod;
        this.name = name;

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
        return mod.id();
    }
}
