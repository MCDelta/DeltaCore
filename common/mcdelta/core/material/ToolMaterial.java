package mcdelta.core.material;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

public class ToolMaterial
{
    public static List<ToolMaterial> mats = new ArrayList<ToolMaterial>();

    public static ToolMaterial IRON = new ToolMaterial("iron", 0xffffff, "ingotIron", EnumToolMaterial.IRON);
    public static ToolMaterial WOOD = new ToolMaterial("wood", 0x866526, "plankWood", EnumToolMaterial.WOOD);
    public static ToolMaterial STONE = new ToolMaterial("stone", 0x9a9a9a, "cobblestone", EnumToolMaterial.STONE);
    public static ToolMaterial DIAMOND = new ToolMaterial("diamond", 0x33ebcb, "gemDiamond", EnumToolMaterial.EMERALD);
    public static ToolMaterial GOLD = new ToolMaterial("gold", 0xeaee57, "ingotGold", EnumToolMaterial.GOLD);

    public static ToolMaterial BRONZE;
    public static ToolMaterial MAGIC;
    public static ToolMaterial STEEL;

    /*
     * static { EnumMCDMods ea = EnumMCDMods.ESSENTIAL_ALLOYS; if (ea.isLoaded()) { BRONZE = new ToolMaterial(new Object[] { "bronze", 0xd3b838, "ingotBronze", true, true, ea,
     * false }, new Object[] { 2, 418, 9.0F, 2.0F, 22 }, null); MAGIC = new ToolMaterial(new Object[] { "magic", 0x7340ad, "ingotMagic", true, true, ea, true }, new Object[] { 3,
     * 205, 12.0F, 1.0F, 44 }, null); STEEL = new ToolMaterial(new Object[] { "steel", 0x637080, "ingotSteel", true, true, ea, false }, new Object[] { 4, 1111, 4.0F, 3.0F, 12 },
     * new Object[] { 33, new int[] { 3, 8, 6, 3 }, 10 }); } }
     */

    // Format: { name, color, ore, needsTools, needsWeapons, mod, defaultShiny }
    public Object[] deltaInfo;

    // Format: { harvest, uses, efficiency, damage, enchant }
    public Object[] toolInfo;

    // Format: { maxDamageFactor, damageReductionAmountArray, enchantability }
    public Object[] armorInfo;

    public EnumToolMaterial toolMaterial;
    public EnumArmorMaterial armorMaterial;

    public ToolMaterial(String s, int i, String s2, EnumToolMaterial mat, int i2, EnumArmorMaterial mat2)
    {
        this(new Object[]
        { s, i, s2, false, true, DeltaCore.instance, false }, mat, i2, mat2);
    }

    public ToolMaterial(String s, int i, String s2, EnumToolMaterial mat)
    {
        this(new Object[]
        { s, i, s2, false, true, DeltaCore.instance, false }, mat);
    }

    public ToolMaterial(String s, int i, String s2, boolean b, boolean b2, EnumToolMaterial mat, int i2, EnumArmorMaterial mat2)
    {
        this(new Object[]
        { s, i, s2, b, b2 }, mat, i2, mat2);
    }

    public ToolMaterial(String s, int i, String s2, boolean b, boolean b2, EnumToolMaterial mat)
    {
        this(new Object[]
        { s, i, s2, b, b2 }, mat);
    }

    public ToolMaterial(Object[] arr1, EnumToolMaterial mat, int i, EnumArmorMaterial mat2)
    {
        this(arr1, new Object[]
        { mat.getHarvestLevel(), mat.getMaxUses(), mat.getEfficiencyOnProperMaterial(), mat.getDamageVsEntity(), mat.getEnchantability() }, new Object[]
        { i, new int[]
        { mat2.getDamageReductionAmount(0), mat2.getDamageReductionAmount(1), mat2.getDamageReductionAmount(2), mat2.getDamageReductionAmount(3) }, mat2.getEnchantability() });

        toolMaterial = mat;
        armorMaterial = mat2;
    }

    public ToolMaterial(Object[] arr1, EnumToolMaterial mat)
    {
        this(arr1, new Object[]
        { mat.getHarvestLevel(), mat.getMaxUses(), mat.getEfficiencyOnProperMaterial(), mat.getDamageVsEntity(), mat.getEnchantability() }, null);

        toolMaterial = mat;
    }

    public ToolMaterial(Object[] arr1, Object[] arr2, Object[] arr3)
    {
        deltaInfo = arr1;
        toolInfo = arr2;
        armorInfo = arr3;

        mats.add(this);

        if (toolMaterial == null)
        {
            toolMaterial = EnumHelper.addToolMaterial(getName(), getHarvestLevel(), getMaxUses(), getEfficiencyOnProperMaterial(), getDamageVsEntity(), getEnchantability());
        }

        if (armorMaterial == null)
        {

        }
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

    public ModDelta getMod()
    {
        return (ModDelta) deltaInfo[5];
    }

    public boolean isShinyDefault()
    {
        return (Boolean) deltaInfo[6];
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

    public int getDurability(int i)
    {
        return ((int[]) armorInfo[1])[i] * (Integer) armorInfo[0];
    }

    public int getDamageReductionAmount(int i)
    {
        return ((int[]) armorInfo[1])[i];
    }

    public int getArmorEnchantability()
    {
        return (Integer) armorInfo[2];
    }

    public EnumArmorMaterial getArmorMaterial()
    {
        return null;
    }

    public int index()
    {
        return mats.indexOf(this);
    }
}