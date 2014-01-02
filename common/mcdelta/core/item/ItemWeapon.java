package mcdelta.core.item;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWeapon extends ItemSword implements IExtraPasses
{
    private final List<Block> harvest = new ArrayList<Block>();
    private final List<Material> effective = new ArrayList<Material>();

    @SideOnly(Side.CLIENT)
    protected Icon itemOverlay;

    @SideOnly(Side.CLIENT)
    protected Icon overrideIcon;

    private boolean overrideExists = false;

    public ModDelta mod;
    public String name;
    private final float weaponDamage;
    private final String toolName;
    public ToolMaterial toolMaterialDelta;

    public ItemWeapon(String s, ModDelta m, ToolMaterial mat, float f)
    {
        super(m.config().getItemID(mat.getName() + "." + s), mat.toolMaterial);

        toolName = s;
        toolMaterialDelta = mat;
        maxStackSize = 1;
        setMaxDamage(mat.getMaxUses());
        setCreativeTab(CreativeTabs.tabCombat);

        harvest.add(Block.web);

        effective.add(Material.plants);
        effective.add(Material.vine);
        effective.add(Material.coral);
        effective.add(Material.leaves);
        effective.add(Material.pumpkin);

        weaponDamage = f;

        // ItemDelta code
        mod = m;
        name = mat.getName() + "." + toolName;
        String unlocalized = mod.id().toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);

        String weapon = "tool." + toolName;
        String material = "material." + mat.getName();

        if (!StatCollector.func_94522_b(weapon))
        {
            DeltaCore.localizationWarnings.append("- " + weapon + " \n");
        }

        if (!StatCollector.func_94522_b(material))
        {
            DeltaCore.localizationWarnings.append("- " + material + " \n");
        }

        ClientProxy.extraPasses.add(this);
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        if (toolName == "sword")
        {
            itemIcon = ItemDelta.doRegister("deltacore", toolName + "_1", register);
            itemOverlay = ItemDelta.doRegister("deltacore", toolName + "_2", register);
        }

        else
        {
            itemIcon = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_1", register);
            itemOverlay = ItemDelta.doRegister(mod.id().toLowerCase(), toolName + "_2", register);
        }

        overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + toolMaterialDelta.getName().toLowerCase() + "_" + toolName
                + ".png"));

        if (overrideExists)
        {
            overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + toolMaterialDelta.getName().toLowerCase() + "_" + toolName, register);
        }
    }

    @Override
    public int getPasses(ItemStack stack)
    {
        if (overrideExists)
        {
            return 1;
        }

        return 2;
    }

    @Override
    public Icon getIconFromPass(ItemStack stack, int pass)
    {
        if (overrideExists)
        {
            return overrideIcon;
        }

        if (pass == 2)
        {
            return itemOverlay;
        }

        return itemIcon;
    }

    @Override
    public int getColorFromPass(ItemStack stack, int pass)
    {
        if (overrideExists)
        {
            return 0xffffff;
        }

        if (pass == 2)
        {
            return ToolMaterial.WOOD.getColor();
        }

        return toolMaterialDelta.getColor();
    }

    @Override
    public boolean getShinyFromPass(ItemStack stack, int pass)
    {
        if ((pass == 1) && toolMaterialDelta.isShinyDefault())
        {
            return true;
        }

        return false;
    }

    @Override
    public float func_82803_g()
    {
        return toolMaterialDelta.getDamageVsEntity();
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        if (harvest.contains(block))
        {
            return 15.0F;
        }
        return effective.contains(block.blockMaterial) ? 1.5F : 1.0F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityLivingBase living)
    {
        if (Block.blocksList[blockID].getBlockHardness(world, x, y, z) != 0.0D)
        {
            stack.damageItem(2, living);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (getItemUseAction(stack) != null)
        {
            ArrowNockEvent event = new ArrowNockEvent(player, stack);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
            {
                return event.result;
            }

            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }

        return stack;
    }

    @Override
    public boolean canHarvestBlock(Block block)
    {
        return harvest.contains(block);
    }

    @Override
    public int getItemEnchantability()
    {
        return toolMaterialDelta.getEnchantability();
    }

    @Override
    public boolean getIsRepairable(ItemStack repair, ItemStack gem)
    {
        if ((OreDictionary.getOres(toolMaterialDelta.getOreDictionaryName()) != null) && !OreDictionary.getOres(toolMaterialDelta.getOreDictionaryName()).isEmpty())
        {
            return OreDictionary.itemMatches(OreDictionary.getOres(toolMaterialDelta.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
        }

        return super.getIsRepairable(repair, gem);
    }

    @Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) weaponDamage
                + toolMaterialDelta.getDamageVsEntity(), 0));

        return multimap;
    }

    @Override
    public String getItemDisplayName(ItemStack stack)
    {
        ToolMaterial mat = toolMaterialDelta;

        String weapon = StatCollector.translateToLocal("tool." + toolName);
        String material = StatCollector.translateToLocal("material." + mat.getName());

        return material + " " + weapon;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return null;
    }
}
