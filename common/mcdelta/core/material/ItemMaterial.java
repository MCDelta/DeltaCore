package mcdelta.core.material;

import mcdelta.core.ModDelta;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

public class ItemMaterial
{
     // Format: { name, color, ore, needsTools, needsWeapons, defaultShiny }
     public Object[]          deltaInfo;
     
     // Format: { harvest, uses, efficiency, damage, enchant }
     public Object[]          toolInfo;
     
     // Format: { maxDamageFactor, damageReductionAmountArray, enchantability }
     public Object[]          armorInfo;
     
     public EnumToolMaterial  toolMaterial;
     public EnumArmorMaterial armorMaterial;
     
     protected ModDelta       owner;
     
     
     
     
     public ItemMaterial (final ModDelta owner, final Object[] delta, final Object[] tool, final Object[] armor, final EnumToolMaterial toolMat, final EnumArmorMaterial armorMat)
     {
          this.deltaInfo = delta;
          this.toolInfo = tool;
          this.armorInfo = armor;
          this.owner = owner;
          
          if (toolMat == null)
          {
               this.toolMaterial = EnumHelper.addToolMaterial(this.getName(), this.getHarvestLevel(), this.getMaxUses(), this.getEfficiencyOnProperMaterial(), this.getDamageVsEntity(), this.getEnchantability());
          }
          else
          {
               this.toolMaterial = toolMat;
          }
          if (armorMat == null)
          {
               // DO SOMETHING
          }
          else
          {
               this.armorMaterial = armorMat;
          }
     }
     
     
     
     
     public ModDelta owner ()
     {
          return this.owner;
     }
     
     
     
     
     public String getName ()
     {
          return (String) this.deltaInfo[0];
     }
     
     
     
     
     public int getColor ()
     {
          return (Integer) this.deltaInfo[1];
     }
     
     
     
     
     public String getOreDictionaryName ()
     {
          return (String) this.deltaInfo[2];
     }
     
     
     
     
     public boolean needsTools ()
     {
          return (Boolean) this.deltaInfo[3];
     }
     
     
     
     
     public boolean needsWeapons ()
     {
          return (Boolean) this.deltaInfo[4];
     }
     
     
     
     
     public boolean isShinyDefault ()
     {
          return (Boolean) this.deltaInfo[5];
     }
     
     
     
     
     public int getHarvestLevel ()
     {
          return (Integer) this.toolInfo[0];
     }
     
     
     
     
     public int getMaxUses ()
     {
          return (Integer) this.toolInfo[1];
     }
     
     
     
     
     public float getEfficiencyOnProperMaterial ()
     {
          return (Float) this.toolInfo[2];
     }
     
     
     
     
     public float getDamageVsEntity ()
     {
          return (Float) this.toolInfo[3];
     }
     
     
     
     
     public int getEnchantability ()
     {
          return (Integer) this.toolInfo[4];
     }
     
     
     
     
     public int getDurability (final int i)
     {
          return ((int[]) this.armorInfo[1])[i] * (Integer) this.armorInfo[0];
     }
     
     
     
     
     public int getDamageReductionAmount (final int i)
     {
          return ((int[]) this.armorInfo[1])[i];
     }
     
     
     
     
     public int getArmorEnchantability ()
     {
          return (Integer) this.armorInfo[2];
     }
     
     
     
     
     public EnumArmorMaterial getArmorMaterial ()
     {
          return this.armorMaterial;
     }
}
