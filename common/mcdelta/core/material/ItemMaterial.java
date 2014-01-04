package mcdelta.core.material;

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
     
     
     
     
     public ItemMaterial (final DeltaInfo delta, final ToolInfo tool, final ArmorInfo armor, ExtraInfo extra, final EnumToolMaterial toolMat, final EnumArmorMaterial armorMat)
     {
          this.deltaInfo = delta;
          this.toolInfo = tool;
          this.armorInfo = armor;
          this.extraInfo = extra;
          
          if (toolMat == null)
          {
               if (tool != null)
               {
                    this.toolMaterial = EnumHelper.addToolMaterial(this.name(), this.toolInfo.harvestLvL(), this.toolInfo.maxUses(), this.toolInfo.efficiency(), this.toolInfo.damageVsEntity(), this.toolInfo.enchantability());
               }
               else
               {
                    this.toolMaterial = EnumHelper.addToolMaterial(this.name(), 0, 0, 0, 0, 0);
               }
          }
          else
          {
               this.toolMaterial = toolMat;
          }
          if (armorMat == null)
          {
               if (this.armorInfo != null)
               {
                    this.armorMaterial = EnumHelper.addArmorMaterial(this.name(), this.armorInfo.maxUses(), this.armorInfo.shieldBarArray(), this.armorInfo.enchantability());
               }
               else
               {
                    this.armorMaterial = EnumHelper.addArmorMaterial(this.name(), 0, null, 0);
               }
          }
          else
          {
               this.armorMaterial = armorMat;
          }
     }
     
     
     
     
     public String name ()
     {
          return this.deltaInfo.name();
     }
     
     
     
     
     public int color ()
     {
          return this.deltaInfo.color();
     }
     
     
     
     
     public String oreName ()
     {
          return this.deltaInfo.oreName();
     }
     
     
     
     
     public boolean needsTools ()
     {
          return this.deltaInfo.needsTools();
     }
     
     
     
     
     public boolean needsWeapons ()
     {
          return this.deltaInfo.needsWeapons();
     }
     
     
     
     
     public boolean needsArmor ()
     {
          return this.deltaInfo.needsArmor();
     }
     
     
     
     
     public boolean defaultShiny ()
     {
          return this.deltaInfo.defaultShiny();
     }
     
     
     
     
     public int getHarvestLevel ()
     {
          return this.toolInfo.harvestLvL();
     }
     
     
     
     
     public int maxUses ()
     {
          return this.toolInfo.maxUses();
     }
     
     
     
     
     public EnumToolMaterial getToolMaterial ()
     {
          return this.toolMaterial;
     }
     
     
     
     
     public EnumArmorMaterial getArmorMaterial ()
     {
          return this.armorMaterial;
     }
     
     
     
     
     public float getDamageVsEntity ()
     {
          return this.toolInfo.damageVsEntity();
     }
     
     
     
     
     public float getEfficiencyOnProperMaterial ()
     {
          return this.toolInfo.efficiency();
     }
     
     
     
     
     public int enchantability ()
     {
          return this.toolInfo.enchantability();
     }
     
     
     
     
     public EnumChatFormatting getNameColor ()
     {
          if (this.extraInfo == null)
          {
               return EnumChatFormatting.WHITE;
          }
          
          return this.extraInfo.nameColor();
     }
     
     
     
     
     public Enchantment weaponEnchant ()
     {
          if (this.extraInfo == null)
          {
               return null;
          }
          
          return extraInfo.weaponEnchant().getKey();
     }
     
     
     
     
     public int weaponEnchantLvl ()
     {
          if (this.extraInfo == null)
          {
               return 0;
          }
          
          return extraInfo.weaponEnchant().getValue();
     }
     
     
     
     
     public Enchantment toolEnchant ()
     {
          if (this.extraInfo == null)
          {
               return null;
          }
          
          return extraInfo.toolEnchant().getKey();
     }
     
     
     
     
     public int toolEnchantLvl ()
     {
          if (this.extraInfo == null)
          {
               return 0;
          }
          
          return extraInfo.toolEnchant().getValue();
     }
}
