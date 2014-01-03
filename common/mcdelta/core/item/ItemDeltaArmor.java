package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaArmor extends ItemArmor implements IExtraPasses, ISpecialArmor
{
    private static final int[] maxDamageArray = new int[]
    { 11, 16, 15, 13 };
    private final ItemMaterial toolMaterial;
    private int armorType;
    public ModDelta mod;
    public String name;

    public ItemDeltaArmor(final ModDelta mod, final ItemMaterial mat, final int type)
    {
        super(mod.config().getItemID(getArmorType(type) + "." + mat.getName().toLowerCase()), EnumArmorMaterial.CHAIN, 1, type);

        this.mod = mod;
        name = getArmorType(type) + "." + mat.getName().toLowerCase();
        final String unlocalized = mod.id().toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);
        setCreativeTab(CreativeTabs.tabCombat);

        toolMaterial = mat;
        mat.getDamageReductionAmount(type);
        setMaxDamage(mat.getDurability(type));

        if (!StatCollector.func_94522_b("item." + unlocalized + ".name"))
        {
            DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
        }
        ClientProxy.extraPasses.add(this);
    }

    private static String getArmorType(final int type)
    {
        switch (type)
        {
            default:
                return "helmet";
            case 1:
                return "plate";
            case 2:
                return "legs";
            case 3:
                return "boots";
        }
    }

    @Override
    public int getPasses(final ItemStack stack)
    {
        return 1;
    }

    @Override
    public Icon getIconFromPass(final ItemStack stack, final int pass)
    {
        return itemIcon;
    }

    @Override
    public int getColorFromPass(final ItemStack stack, final int pass)
    {
        return toolMaterial.getColor();
    }

    @Override
    public boolean getShinyFromPass(final ItemStack stack, final int pass)
    {
        return toolMaterial.isShinyDefault();
    }

    @Override
    public int getItemEnchantability()
    {
        return toolMaterial.getArmorEnchantability();
    }

    @Override
    public EnumArmorMaterial getArmorMaterial()
    {
        return EnumArmorMaterial.CHAIN;
    }

    @Override
    public boolean getIsRepairable(final ItemStack repair, final ItemStack gem)
    {
        if ((OreDictionary.getOres(toolMaterial.getOreDictionaryName()) != null) && !OreDictionary.getOres(toolMaterial.getOreDictionaryName()).isEmpty())
        {
            return OreDictionary.itemMatches(OreDictionary.getOres(toolMaterial.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
        }
        return super.getIsRepairable(repair, gem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister register)
    {
        super.registerIcons(register);
        itemIcon = ItemDelta.doRegister("deltacore", "armor_" + armorType, register);
    }

    static int[] getMaxDamageArray()
    {
        return maxDamageArray;
    }

    // ARMOR PROPS

    @Override
    public ArmorProperties getProperties(final EntityLivingBase player, final ItemStack armor, final DamageSource source, final double damage, final int slot)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getArmorDisplay(final EntityPlayer player, final ItemStack armor, final int slot)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void damageArmor(final EntityLivingBase entity, final ItemStack stack, final DamageSource source, final int damage, final int slot)
    {
        // TODO Auto-generated method stub
    }
}
