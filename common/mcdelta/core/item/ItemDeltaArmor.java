package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ToolMaterial;
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
    private final ToolMaterial toolMaterial;
    private int armorType;
    private int renderIndex;
    private final int damageReduceAmount;

    public ModDelta mod;
    public String name;

    public ItemDeltaArmor(ModDelta mod, ToolMaterial mat, int type)
    {
        super(mod.config().getItemID(getArmorType(type) + "." + mat.getName().toLowerCase()), EnumArmorMaterial.CHAIN, mat.index() + 4, type);

        this.mod = mod;
        name = getArmorType(type) + "." + mat.getName().toLowerCase();
        String unlocalized = mod.id().toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);
        setCreativeTab(CreativeTabs.tabCombat);

        toolMaterial = mat;
        damageReduceAmount = mat.getDamageReductionAmount(type);
        setMaxDamage(mat.getDurability(type));

        if (!StatCollector.func_94522_b("item." + unlocalized + ".name"))
        {
            DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
        }

        ClientProxy.extraPasses.add(this);
    }

    private static String getArmorType(int i)
    {
        switch (i)
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
    public int getPasses(ItemStack stack)
    {
        return 1;
    }

    @Override
    public Icon getIconFromPass(ItemStack stack, int pass)
    {
        return itemIcon;
    }

    @Override
    public int getColorFromPass(ItemStack stack, int pass)
    {
        return toolMaterial.getColor();
    }

    @Override
    public boolean getShinyFromPass(ItemStack stack, int pass)
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
    public boolean getIsRepairable(ItemStack repair, ItemStack gem)
    {
        if ((OreDictionary.getOres(toolMaterial.getOreDictionaryName()) != null) && !OreDictionary.getOres(toolMaterial.getOreDictionaryName()).isEmpty())
        {
            return OreDictionary.itemMatches(OreDictionary.getOres(toolMaterial.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
        }

        return super.getIsRepairable(repair, gem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
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
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
    {
        // TODO Auto-generated method stub

    }
}