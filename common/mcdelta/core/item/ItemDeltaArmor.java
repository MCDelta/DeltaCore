package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaArmor extends ItemArmor implements IExtraPasses
{
     private final ItemMaterial itemMaterial;
     public ModDelta            mod;
     public String              name;
     
     @SideOnly (Side.CLIENT)
     protected Icon             overrideIcon;
     
     protected boolean          overrideExists = false;
     
     
     
     
     public ItemDeltaArmor (final ModDelta mod, final ItemMaterial mat, final int type)
     {
          super(mod.config().getItemID(getArmorType(type) + "." + mat.name().toLowerCase()), mat.getArmorMaterial(), 1, type);
          
          this.mod = mod;
          name = getArmorType(type) + "." + mat.name().toLowerCase();
          final String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          
          itemMaterial = mat;
          ClientProxy.extraPasses.add(this);
     }
     
     
     
     
     private static String getArmorType (final int type)
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
     public int getPasses (final ItemStack stack)
     {
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          if (overrideExists)
          {
               return overrideIcon;
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
          return itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          return itemMaterial.defaultShiny();
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
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister register)
     {
          itemIcon = ItemDelta.doRegister("deltacore", "armor_" + getArmorType(armorType), register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + itemMaterial.name().toLowerCase() + "_" + getArmorType(armorType) + ".png"));
          
          if (overrideExists)
          {
               overrideIcon = ItemDelta.doRegister(mod.id(), "override/" + itemMaterial.name().toLowerCase() + "_" + getArmorType(armorType), register);
          }
     }
     
     
     
     
     @Override
     public String getArmorTexture (final ItemStack stack, final Entity entity, final int slot, final String type)
     {
          return mod.id() + ":textures/models/armor/" + itemMaterial.name() + (slot == 2 ? "_2" : "") + ".png";
     }
     
     
     
     
     @Override
     public String getItemDisplayName (final ItemStack stack)
     {
          final ItemMaterial mat = itemMaterial;
          
          final String weapon = StatCollector.translateToLocal("armor." + getArmorType(armorType));
          final String material = StatCollector.translateToLocal("material." + mat.name());
          
          return mat.getNameColor() + String.format(weapon, material);
     }
     
     
     
     
     @Override
     public Multimap<String, AttributeModifier> getItemAttributeModifiers ()
     {
          final Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
          
          if (itemMaterial.armorSharedAttrKey() == null)
          {
               return multimap;
          }
          
          multimap.put(itemMaterial.armorSharedAttrKey(), new AttributeModifier(field_111210_e, "Armor modifier", itemMaterial.armorSharedAttrValue(), 0));
          
          return multimap;
     }
}
