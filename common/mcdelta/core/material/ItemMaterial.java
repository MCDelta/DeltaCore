package mcdelta.core.material;

import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.EnumHelper;

public final class ItemMaterial
{
     private final DeltaInfo         deltaInfo;
     private final ToolInfo          toolInfo;
     private final ArmorInfo         armorInfo;
     private final ExtraInfo         extraInfo;
     
     private final EnumToolMaterial  toolMaterial;
     private final EnumArmorMaterial armorMaterial;
     
     
     
     
     public ItemMaterial (final DeltaInfo delta, final ToolInfo tool, final ArmorInfo armor, final ExtraInfo extra, final EnumToolMaterial toolMat, final EnumArmorMaterial armorMat)
     {
          deltaInfo = delta;
          toolInfo = tool;
          armorInfo = armor;
          extraInfo = extra;
          
          if (toolMat == null)
          {
               if (tool != null)
               {
                    toolMaterial = EnumHelper.addToolMaterial(name(), toolInfo.harvestLvL(), toolInfo.maxUses(), toolInfo.efficiency(), toolInfo.damageVsEntity(), toolInfo.enchantability());
               }
               else
               {
                    toolMaterial = EnumHelper.addToolMaterial(name(), 0, 0, 0, 0, 0);
               }
          }
          else
          {
               toolMaterial = toolMat;
          }
          if (armorMat == null)
          {
               if (armorInfo != null)
               {
                    armorMaterial = EnumHelper.addArmorMaterial(name(), armorInfo.maxUses(), armorInfo.shieldBarArray(), armorInfo.enchantability());
               }
               else
               {
                    armorMaterial = EnumHelper.addArmorMaterial(name(), 0, null, 0);
               }
          }
          else
          {
               armorMaterial = armorMat;
          }
     }
     
     
     
     
     public String name ()
     {
          return deltaInfo.name();
     }
     
     
     
     
     public int color ()
     {
          return deltaInfo.color();
     }
     
     
     
     
     public String oreName ()
     {
          return deltaInfo.oreName();
     }
     
     
     
     
     public boolean needsTools ()
     {
          return deltaInfo.needsTools();
     }
     
     
     
     
     public boolean needsWeapons ()
     {
          return deltaInfo.needsWeapons();
     }
     
     
     
     
     public boolean needsArmor ()
     {
          return deltaInfo.needsArmor();
     }
     
     
     
     
     public boolean defaultShiny ()
     {
          return deltaInfo.defaultShiny();
     }
     
     
     
     
     public int getHarvestLevel ()
     {
          return toolInfo.harvestLvL();
     }
     
     
     
     
     public int maxUses ()
     {
          return toolInfo.maxUses();
     }
     
     
     
     
     public EnumToolMaterial getToolMaterial ()
     {
          return toolMaterial;
     }
     
     
     
     
     public EnumArmorMaterial getArmorMaterial ()
     {
          return armorMaterial;
     }
     
     
     
     
     public float getDamageVsEntity ()
     {
          return toolInfo.damageVsEntity();
     }
     
     
     
     
     public float getEfficiencyOnProperMaterial ()
     {
          return toolInfo.efficiency();
     }
     
     
     
     
     public int enchantability ()
     {
          return toolInfo.enchantability();
     }
     
     
     
     
     public EnumChatFormatting getNameColor ()
     {
          if (extraInfo == null)
          {
               return EnumChatFormatting.WHITE;
          }
          
          return extraInfo.nameColor();
     }
     
     
     
     
     public Enchantment weaponEnchant ()
     {
          if (extraInfo == null)
          {
               return null;
          }
          
          return extraInfo.weaponEnchant() == null ? null : extraInfo.weaponEnchant().getKey();
     }
     
     
     
     
     public int weaponEnchantLvl ()
     {
          if (extraInfo == null)
          {
               return 0;
          }
          
          return extraInfo.weaponEnchant() == null ? 0 : extraInfo.weaponEnchant().getValue();
     }
     
     
     
     
     public Enchantment toolEnchant ()
     {
          if (extraInfo == null)
          {
               return null;
          }
          
          return extraInfo.toolEnchant() == null ? null : extraInfo.toolEnchant().getKey();
     }
     
     
     
     
     public int toolEnchantLvl ()
     {
          if (extraInfo == null)
          {
               return 0;
          }
          
          return extraInfo.toolEnchant() == null ? 0 : extraInfo.toolEnchant().getValue();
     }
     
     
     
     
     public Entry<String, Integer> nonStickCrafter ()
     {
          if (extraInfo == null)
          {
               return null;
          }
          
          return extraInfo.nonStickCrafter();
     }
}
