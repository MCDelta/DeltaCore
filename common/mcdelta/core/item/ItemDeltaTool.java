package mcdelta.core.item;

import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaTool extends ItemTool implements IExtraPasses
{
     public ModDelta       mod;
     public String         name;
     public ItemMaterial   itemMaterial;
     private final Block[] blocksEffectiveAgainst;
     protected float       efficiencyOnProperMaterial;
     private final float   damageVsEntity;
     protected String      toolName;
     
     @SideOnly (Side.CLIENT)
     protected Icon        itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon        overrideIcon;
     
     protected boolean     overrideExists = false;
     
     
     
     
     public ItemDeltaTool (final ModDelta mod, final String name, final ItemMaterial mat, final Block[] effective, final float damage)
     {
          this(mod, name, mat, effective, damage, true);
     }
     
     
     
     
     public ItemDeltaTool (final ModDelta mod, final String name, final ItemMaterial mat, final Block[] effective, final float damage, final boolean b)
     {
          super(mod.config().getItemID(mat.name() + "." + name), damage, mat.getToolMaterial(), effective);
          itemMaterial = mat;
          toolName = name;
          blocksEffectiveAgainst = effective;
          maxStackSize = 1;
          setMaxDamage(mat.maxUses());
          efficiencyOnProperMaterial = mat.getEfficiencyOnProperMaterial();
          damageVsEntity = damage + mat.getDamageVsEntity();
          setCreativeTab(CreativeTabs.tabTools);
          
          // ItemDelta code
          this.mod = mod;
          this.name = mat.name() + "." + toolName;
          final String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          
          final String weapon = "tool." + toolName;
          final String material = "material." + mat.name();
          
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
     public void registerIcons (final IconRegister register)
     {
          itemIcon = ItemDelta.doRegister("deltacore", toolName + "_1", register);
          itemOverlay = ItemDelta.doRegister("deltacore", toolName + "_2", register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + itemMaterial.name().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               overrideIcon = ItemDelta.doRegister(mod.id(), "/override/" + itemMaterial.name().toLowerCase() + "_" + toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (overrideExists)
          {
               return 1;
          }
          return 2;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
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
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (overrideExists)
          {
               return 0xffffff;
          }
          if (pass == 2)
          {
               if (itemMaterial.nonStickCrafter() != null)
               {
                    return itemMaterial.nonStickCrafter().getValue();
               }
               
               return MaterialRegistry.WOOD.color();
          }
          return itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (pass == 1 && itemMaterial.defaultShiny())
          {
               return true;
          }
          return false;
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block)
     {
          for (final Block element : blocksEffectiveAgainst)
          {
               if (element == block)
               {
                    return efficiencyOnProperMaterial;
               }
          }
          return 1.0F;
     }
     
     
     
     
     @Override
     public boolean hitEntity (final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
     {
          stack.damageItem(2, attacker);
          return true;
     }
     
     
     
     
     @Override
     public boolean onBlockDestroyed (final ItemStack stack, final World world, final int id, final int x, final int y, final int z, final EntityLivingBase living)
     {
          if (Block.blocksList[id].getBlockHardness(world, x, y, z) != 0.0D)
          {
               stack.damageItem(1, living);
          }
          return true;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean isFull3D ()
     {
          return true;
     }
     
     
     
     
     @Override
     public int getItemEnchantability ()
     {
          return itemMaterial.enchantability();
     }
     
     
     
     
     public String getToolMaterialName ()
     {
          return itemMaterial.toString();
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack repair, final ItemStack gem)
     {
          if (OreDictionary.getOres(itemMaterial.oreName()) != null && !OreDictionary.getOres(itemMaterial.oreName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(itemMaterial.oreName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          return super.getIsRepairable(repair, gem);
     }
     
     
     
     
     @Override
     public Multimap<String, AttributeModifier> getItemAttributeModifiers ()
     {
          final Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
          multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", damageVsEntity, 0));
          return multimap;
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block, final int meta)
     {
          if (ForgeHooks.isToolEffective(stack, block, meta))
          {
               return efficiencyOnProperMaterial;
          }
          return this.getStrVsBlock(stack, block);
     }
     
     
     
     
     @Override
     public String getItemDisplayName (final ItemStack stack)
     {
          final ItemMaterial mat = itemMaterial;
          
          final String weapon = StatCollector.translateToLocal("tool." + toolName);
          final String material = StatCollector.translateToLocal("material." + mat.name());
          
          return mat.getNameColor() + String.format(weapon, material);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void getSubItems (final int id, final CreativeTabs tab, final List list)
     {
          final ItemStack stack = new ItemStack(id, 1, 0);
          if (itemMaterial.toolEnchant() != null)
          {
               stack.addEnchantment(itemMaterial.toolEnchant(), itemMaterial.toolEnchantLvl());
          }
          list.add(stack);
     }
}
