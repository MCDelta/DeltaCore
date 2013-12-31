package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.EnumMCDMods;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaArmor extends ItemArmor implements IExtraPasses
{
    private static final int[] maxDamageArray = new int[]
    { 11, 16, 15, 13 };
    private final ToolMaterial toolMaterial;
    private int armorType;
    private int renderIndex;
    private final int damageReduceAmount;

    public EnumMCDMods mod;
    public String name;

    public ItemDeltaArmor(EnumMCDMods m, ToolMaterial mat, int i)
    {
        super(DeltaCore.config.getItemID(m, getArmorType(i) + "." + mat.getName().toLowerCase()), EnumArmorMaterial.CHAIN, mat.index() + 4, i);

        mod = m;
        name = getArmorType(i) + "." + mat.getName().toLowerCase();
        String unlocalized = mod.modid.toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);
        setCreativeTab(CreativeTabs.tabCombat);

        toolMaterial = mat;
        damageReduceAmount = mat.getDamageReductionAmount(i);
        setMaxDamage(mat.getDurability(i));

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

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        int i = EntityLiving.getArmorPosition(stack) - 1;
        ItemStack itemstack1 = player.getCurrentArmor(i);

        if (itemstack1 == null)
        {
            player.setCurrentItemOrArmor(i + 1, stack.copy()); // Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
            stack.stackSize = 0;
        }

        return stack;
    }

    static int[] getMaxDamageArray()
    {
        return maxDamageArray;
    }
}