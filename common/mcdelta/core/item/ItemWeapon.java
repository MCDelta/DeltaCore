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
     public ItemMaterial          toolMaterialDelta;
     
     
     
     
     public ItemWeapon (final String toolName, final ModDelta mod, final ItemMaterial mat, final float f)
     {
          super(mod.config().getItemID(mat.name() + "." + toolName), mat.getToolMaterial());
          
          this.toolName = toolName;
          this.toolMaterialDelta = mat;
          this.maxStackSize = 1;
          this.setMaxDamage(mat.maxUses());
          this.setCreativeTab(CreativeTabs.tabCombat);
          
          this.harvest.add(Block.web);
          
          this.effective.add(Material.plants);
          this.effective.add(Material.vine);
          this.effective.add(Material.coral);
          this.effective.add(Material.leaves);
          this.effective.add(Material.pumpkin);
          
          this.weaponDamage = f;
          
          // ItemDelta code
          this.mod = mod;
          this.name = mat.name() + "." + toolName;
          final String unlocalized = mod.id().toLowerCase() + ":" + this.name;
          this.setUnlocalizedName(unlocalized);
          
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
          if (this.toolName == "sword")
          {
               this.itemIcon = ItemDelta.doRegister("deltacore", this.toolName + "_1", register);
               this.itemOverlay = ItemDelta.doRegister("deltacore", this.toolName + "_2", register);
          }
          else
          {
               this.itemIcon = ItemDelta.doRegister(this.mod.id().toLowerCase(), this.toolName + "_1", register);
               this.itemOverlay = ItemDelta.doRegister(this.mod.id().toLowerCase(), this.toolName + "_2", register);
          }
          this.overrideExists = Assets.resourceExists(new ResourceLocation(this.mod.id().toLowerCase(), "textures/items/override/" + this.toolMaterialDelta.name().toLowerCase() + "_" + this.toolName + ".png"));
          
          if (this.overrideExists)
          {
               this.overrideIcon = ItemDelta.doRegister(this.mod.id().toLowerCase(), "override/" + this.toolMaterialDelta.name().toLowerCase() + "_" + this.toolName, register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (this.overrideExists)
          {
               return 1;
          }
          return 2;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          if (this.overrideExists)
          {
               return this.overrideIcon;
          }
          if (pass == 2)
          {
               return this.itemOverlay;
          }
          return this.itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (this.overrideExists)
          {
               return 0xffffff;
          }
          if (pass == 2)
          {
               return MaterialRegistry.WOOD.color();
          }
          return this.toolMaterialDelta.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (pass == 1 && this.toolMaterialDelta.defaultShiny())
          {
               return true;
          }
          return false;
     }
     
     
     
     
     @Override
     public float func_82803_g ()
     {
          return this.toolMaterialDelta.getDamageVsEntity();
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block)
     {
          if (this.harvest.contains(block))
          {
               return 15.0F;
          }
          return this.effective.contains(block.blockMaterial) ? 1.5F : 1.0F;
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
          if (this.getItemUseAction(stack) != null)
          {
               final ArrowNockEvent event = new ArrowNockEvent(player, stack);
               MinecraftForge.EVENT_BUS.post(event);
               if (event.isCanceled())
               {
                    return event.result;
               }
               player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
          }
          return stack;
     }
     
     
     
     
     @Override
     public boolean canHarvestBlock (final Block block)
     {
          return this.harvest.contains(block);
     }
     
     
     
     
     @Override
     public int getItemEnchantability ()
     {
          return this.toolMaterialDelta.enchantability();
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack repair, final ItemStack gem)
     {
          if (OreDictionary.getOres(this.toolMaterialDelta.oreName()) != null && !OreDictionary.getOres(this.toolMaterialDelta.oreName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(this.toolMaterialDelta.oreName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          return super.getIsRepairable(repair, gem);
     }
     
     
     
     
     @Override
     public Multimap<String, AttributeModifier> getItemAttributeModifiers ()
     {
          final Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
          multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
          multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) this.weaponDamage + this.toolMaterialDelta.getDamageVsEntity(), 0));
          
          return multimap;
     }
     
     
     
     
     @Override
     public String getItemDisplayName (final ItemStack stack)
     {
          final ItemMaterial mat = this.toolMaterialDelta;
          
          final String weapon = StatCollector.translateToLocal("tool." + this.toolName);
          final String material = StatCollector.translateToLocal("material." + mat.name());
          
          return mat.getNameColor() + material + " " + weapon;
     }
     
     
     
     
     @Override
     public EnumAction getItemUseAction (final ItemStack stack)
     {
          return null;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void getSubItems (int id, CreativeTabs tab, List list)
     {
          ItemStack stack = new ItemStack(id, 1, 0);
          if(toolMaterialDelta.weaponEnchant() != null)
               stack.addEnchantment(toolMaterialDelta.weaponEnchant(), toolMaterialDelta.weaponEnchantLvl());
          list.add(stack);
     }
}
