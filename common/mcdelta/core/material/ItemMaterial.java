package mcdelta.core.material;

import mcdelta.core.ModDelta;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

public class ItemMaterial
{
    // Format: { name, color, ore, needsTools, needsWeapons, defaultShiny }
    public Object[] deltaInfo;

    // Format: { harvest, uses, efficiency, damage, enchant }
    public Object[] toolInfo;

    // Format: { maxDamageFactor, damageReductionAmountArray, enchantability }
    public Object[] armorInfo;

    public EnumToolMaterial toolMaterial;
    public EnumArmorMaterial armorMaterial;
    
    protected ModDelta owner;

    public ItemMaterial(final ModDelta owner, final Object[] delta, final Object[] tool, final Object[] armor, EnumToolMaterial toolMat, EnumArmorMaterial armorMat)
    {
        deltaInfo = delta;
        toolInfo = tool;
        armorInfo = armor;
        this.owner = owner;

        if (toolMat == null)
        {
            toolMaterial = EnumHelper.addToolMaterial(getName(), getHarvestLevel(), getMaxUses(), getEfficiencyOnProperMaterial(), getDamageVsEntity(), getEnchantability());
        } else
        {
            toolMaterial = toolMat;
        }
        if (armorMat == null)
        {
            // DO SOMETHING
        } else
        {
            armorMaterial = armorMat;
        }
    }

    public ModDelta owner()
    {
        return owner;
    }
    
    public String getName()
    {
        return (String) deltaInfo[0];
    }

    public int getColor()
    {
        return (Integer) deltaInfo[1];
    }

    public String getOreDictionaryName()
    {
        return (String) deltaInfo[2];
    }

    public boolean needsTools()
    {
        return (Boolean) deltaInfo[3];
    }

    public boolean needsWeapons()
    {
        return (Boolean) deltaInfo[4];
    }

    public boolean isShinyDefault()
    {
        return (Boolean) deltaInfo[5];
    }

    public int getHarvestLevel()
    {
        return (Integer) toolInfo[0];
    }

    public int getMaxUses()
    {
        return (Integer) toolInfo[1];
    }

    public float getEfficiencyOnProperMaterial()
    {
        return (Float) toolInfo[2];
    }

    public float getDamageVsEntity()
    {
        return (Float) toolInfo[3];
    }

    public int getEnchantability()
    {
        return (Integer) toolInfo[4];
    }

    public int getDurability(final int i)
    {
        return ((int[]) armorInfo[1])[i] * (Integer) armorInfo[0];
    }

    public int getDamageReductionAmount(final int i)
    {
        return ((int[]) armorInfo[1])[i];
    }

    public int getArmorEnchantability()
    {
        return (Integer) armorInfo[2];
    }

    public EnumArmorMaterial getArmorMaterial()
    {
        return armorMaterial;
    }
}