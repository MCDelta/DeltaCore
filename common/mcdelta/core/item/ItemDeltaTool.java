package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
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
          super(mod, mat.getName() + "." + name, b);
          this.itemMaterial = mat;
          this.toolName = name;
          this.blocksEffectiveAgainst = effective;
          this.maxStackSize = 1;
          this.setMaxDamage(mat.getMaxUses());
          this.efficiencyOnProperMaterial = mat.getEfficiencyOnProperMaterial();
          this.damageVsEntity = damage + mat.getDamageVsEntity();
          this.setCreativeTab(CreativeTabs.tabTools);
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          this.itemIcon = doRegister("deltacore", this.toolName + "_1", register);
          this.itemOverlay = doRegister("deltacore", this.toolName + "_2", register);
          
          this.overrideExists = Assets.resourceExists(new ResourceLocation(this.mod.id().toLowerCase(), "textures/items/override/" + this.itemMaterial.getName().toLowerCase() + "_" + this.toolName + ".png"));
          
          if (this.overrideExists)
          {
               this.overrideIcon = this.doRegister("/override/" + this.itemMaterial.getName().toLowerCase() + "_" + this.toolName, register);
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
               return MaterialRegistry.WOOD.getColor();
          }
          return this.itemMaterial.getColor();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (pass == 1 && this.itemMaterial.isShinyDefault())
          {
               return true;
          }
          return false;
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block)
     {
          for (final Block element : this.blocksEffectiveAgainst)
          {
               if (element == block)
               {
                    return this.efficiencyOnProperMaterial;
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
          return this.itemMaterial.getEnchantability();
     }
     
     
     
     
     public String getToolMaterialName ()
     {
          return this.itemMaterial.toString();
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack repair, final ItemStack gem)
     {
          if (OreDictionary.getOres(this.itemMaterial.getOreDictionaryName()) != null && !OreDictionary.getOres(this.itemMaterial.getOreDictionaryName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(this.itemMaterial.getOreDictionaryName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          return super.getIsRepairable(repair, gem);
     }
     
     
     
     
     @Override
     public Multimap<String, AttributeModifier> getItemAttributeModifiers ()
     {
          final Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
          multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
          return multimap;
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block, final int meta)
     {
          if (ForgeHooks.isToolEffective(stack, block, meta))
          {
               return this.efficiencyOnProperMaterial;
          }
          return this.getStrVsBlock(stack, block);
     }
}
