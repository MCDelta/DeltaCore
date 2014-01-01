package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaTool extends ItemDelta implements IExtraPasses
{
    protected ToolMaterial toolMaterial;
    private final Block[] blocksEffectiveAgainst;
    protected float efficiencyOnProperMaterial;
    private final float damageVsEntity;
    protected String toolName;

    @SideOnly(Side.CLIENT)
    protected Icon itemOverlay;

    @SideOnly(Side.CLIENT)
    protected Icon overrideIcon;

    private boolean overrideExists = false;

    public ItemDeltaTool(ModDelta m, String s, ToolMaterial mat, Block[] arr, float damage)
    {
        super(m, mat.getName() + "." + s);
        toolMaterial = mat;
        toolName = s;
        blocksEffectiveAgainst = arr;
        maxStackSize = 1;
        setMaxDamage(mat.getMaxUses());
        efficiencyOnProperMaterial = mat.getEfficiencyOnProperMaterial();
        damageVsEntity = damage + mat.getDamageVsEntity();
        setCreativeTab(CreativeTabs.tabTools);

        ClientProxy.extraPasses.add(this);
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        itemIcon = doRegister("deltacore", toolName + "_1", register);
        itemOverlay = doRegister("deltacore", toolName + "_2", register);

        overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + toolMaterial.getName().toLowerCase() + "_" + toolName
                + ".png"));

        if (overrideExists)
        {
            overrideIcon = doRegister("/override/" + toolMaterial.getName().toLowerCase() + "_" + toolName, register);
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

        return toolMaterial.getColor();
    }

    @Override
    public boolean getShinyFromPass(ItemStack stack, int pass)
    {
        if ((pass == 1) && toolMaterial.isShinyDefault())
        {
            return true;
        }

        return false;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        for (Block element : blocksEffectiveAgainst)
        {
            if (element == block)
            {
                return efficiencyOnProperMaterial;
            }
        }

        return 1.0F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(2, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int id, int x, int y, int z, EntityLivingBase living)
    {
        if (Block.blocksList[id].getBlockHardness(world, x, y, z) != 0.0D)
        {
            stack.damageItem(1, living);
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
    public int getItemEnchantability()
    {
        return toolMaterial.getEnchantability();
    }

    public String getToolMaterialName()
    {
        return toolMaterial.toString();
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
    public Multimap getItemAttributeModifiers()
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", damageVsEntity, 0));
        return multimap;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block, int meta)
    {
        if (ForgeHooks.isToolEffective(stack, block, meta))
        {
            return efficiencyOnProperMaterial;
        }

        return getStrVsBlock(stack, block);
    }
}
