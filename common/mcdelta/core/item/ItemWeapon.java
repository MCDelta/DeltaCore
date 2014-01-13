package mcdelta.core.item;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
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
     private final List<Block>    harvest        = new ArrayList<Block>();
     private final List<Material> effective      = new ArrayList<Material>();
     
     @SideOnly (Side.CLIENT)
     protected Icon               itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon               overrideIcon;
     
     private boolean              overrideExists = false;
     
     public ModDelta              mod;
     public String                name;
     private final float          weaponDamage;
     private final String         toolName;
     public ItemMaterial          itemMaterial;
     
     
     
     
     public ItemWeapon (final String toolName, final ModDelta mod, final ItemMaterial mat, final float f)
     {
          super(mod.config().getItemID(mat.name() + "." + toolName), mat.getToolMaterial());
          
          this.toolName = toolName;
          itemMaterial = mat;
          maxStackSize = 1;
          setMaxDamage(mat.maxUses());
          setCreativeTab(CreativeTabs.tabCombat);
          
          harvest.add(Block.web);
          
          effective.add(Material.plants);
          effective.add(Material.vine);
          effective.add(Material.coral);
          effective.add(Material.leaves);
          effective.add(Material.pumpkin);
          
          weaponDamage = f;
          
          // ItemDelta code
          this.mod = mod;
          name = mat.name() + "." + toolName;
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
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + itemMaterial.name().toLowerCase() + "_" + toolName + ".png"));
          
          if (overrideExists)
          {
               overrideIcon = ItemDelta.doRegister(mod.id().toLowerCase(), "override/" + itemMaterial.name().toLowerCase() + "_" + toolName, register);
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
     public float func_82803_g ()
     {
          return itemMaterial.getDamageVsEntity();
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block)
     {
          if (harvest.contains(block))
          {
               return 15.0F;
          }
          return effective.contains(block.blockMaterial) ? 1.5F : 1.0F;
     }
     
     
     
     
     @Override
     public boolean hitEntity (final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
     {
          stack.damageItem(1, attacker);
          return true;
     }
     
     
     
     
     @Override
     public boolean onBlockDestroyed (final ItemStack stack, final World world, final int blockID, final int x, final int y, final int z, final EntityLivingBase living)
     {
          if (Block.blocksList[blockID].getBlockHardness(world, x, y, z) != 0.0D)
          {
               stack.damageItem(2, living);
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
     public int getMaxItemUseDuration (final ItemStack stack)
     {
          return 72000;
     }
     
     
     
     
     @Override
     public ItemStack onItemRightClick (final ItemStack stack, final World world, final EntityPlayer player)
     {
          if (getItemUseAction(stack) != null)
          {
               final ArrowNockEvent event = new ArrowNockEvent(player, stack);
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
     public boolean canHarvestBlock (final Block block)
     {
          return harvest.contains(block);
     }
     
     
     
     
     @Override
     public int getItemEnchantability ()
     {
          return itemMaterial.enchantability();
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
          multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
          multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) weaponDamage + itemMaterial.getDamageVsEntity(), 0));
          
          return multimap;
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
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return null;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void getSubItems (final int id, final CreativeTabs tab, final List list)
     {
          final ItemStack stack = new ItemStack(id, 1, 0);
          if (itemMaterial.weaponEnchant() != null)
          {
               stack.addEnchantment(itemMaterial.weaponEnchant(), itemMaterial.weaponEnchantLvl());
          }
          list.add(stack);
     }
}
