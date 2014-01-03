package mcdelta.core.material;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

public class ToolMaterial
{
     public static List<ToolMaterial> mats = new ArrayList<ToolMaterial>();
     
     public static ToolMaterial       IRON    = new ToolMaterial("iron", 0xffffff, "ingotIron", EnumToolMaterial.IRON);
     public static ToolMaterial       WOOD    = new ToolMaterial("wood", 0x866526, "plankWood", EnumToolMaterial.WOOD);
     public static ToolMaterial       STONE   = new ToolMaterial("stone", 0x9a9a9a, "cobblestone", EnumToolMaterial.STONE);
     public static ToolMaterial       DIAMOND = new ToolMaterial("diamond", 0x33ebcb, "gemDiamond", EnumToolMaterial.EMERALD);
     public static ToolMaterial       GOLD    = new ToolMaterial("gold", 0xeaee57, "ingotGold", EnumToolMaterial.GOLD);
     
     // Format: { name, color, ore, needsTools, needsWeapons, mod, defaultShiny }
     public Object[]                  deltaInfo;
     
     // Format: { harvest, uses, efficiency, damage, enchant }
     public Object[]                  toolInfo;
     
     // Format: { maxDamageFactor, damageReductionAmountArray, enchantability }
     public Object[]                  armorInfo;
     
     public EnumToolMaterial          toolMaterial;
     public EnumArmorMaterial         armorMaterial;
     
     
     
     
     public ToolMaterial (final String s, final int i, final String s2, final EnumToolMaterial mat, final int i2, final EnumArmorMaterial mat2)
     {
          this(new Object[]
          { s, i, s2, false, true, DeltaCore.instance, false }, mat, i2, mat2);
     }
     
     
     
     
     public ToolMaterial (final String s, final int i, final String s2, final EnumToolMaterial mat)
     {
          this(new Object[]
          { s, i, s2, false, true, DeltaCore.instance, false }, mat);
     }
     
     
     
     
     public ToolMaterial (final String s, final int i, final String s2, final boolean b, final boolean b2, final EnumToolMaterial mat, final int i2, final EnumArmorMaterial mat2)
     {
          this(new Object[]
          { s, i, s2, b, b2 }, mat, i2, mat2);
     }
     
     
     
     
     public ToolMaterial (final String s, final int i, final String s2, final boolean b, final boolean b2, final EnumToolMaterial mat)
     {
          this(new Object[]
          { s, i, s2, b, b2 }, mat);
     }
     
     
     
     
     public ToolMaterial (final Object[] arr1, final EnumToolMaterial mat, final int i, final EnumArmorMaterial mat2)
     {
          this(arr1, new Object[]
          { mat.getHarvestLevel(), mat.getMaxUses(), mat.getEfficiencyOnProperMaterial(), mat.getDamageVsEntity(), mat.getEnchantability() }, new Object[]
          { i, new int[]
          { mat2.getDamageReductionAmount(0), mat2.getDamageReductionAmount(1), mat2.getDamageReductionAmount(2), mat2.getDamageReductionAmount(3) }, mat2.getEnchantability() });
          
          this.toolMaterial = mat;
          this.armorMaterial = mat2;
     }
     
     
     
     
     public ToolMaterial (final Object[] arr1, final EnumToolMaterial mat)
     {
          this(arr1, new Object[]
          { mat.getHarvestLevel(), mat.getMaxUses(), mat.getEfficiencyOnProperMaterial(), mat.getDamageVsEntity(), mat.getEnchantability() }, null);
          
          this.toolMaterial = mat;
     }
     
     
     
     
     public ToolMaterial (final Object[] arr1, final Object[] arr2, final Object[] arr3)
     {
          this.deltaInfo = arr1;
          this.toolInfo = arr2;
          this.armorInfo = arr3;
          
          mats.add(this);
          
          if (this.toolMaterial == null)
          {
               this.toolMaterial = EnumHelper.addToolMaterial(this.getName(), this.getHarvestLevel(), this.getMaxUses(), this.getEfficiencyOnProperMaterial(), this.getDamageVsEntity(), this.getEnchantability());
          }
          if (this.armorMaterial == null)
          {
          }
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
     
     
     
     
     public ModDelta getMod ()
     {
          return (ModDelta) this.deltaInfo[5];
     }
     
     
     
     
     public boolean isShinyDefault ()
     {
          return (Boolean) this.deltaInfo[6];
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
          return null;
     }
     
     
     
     
     public int index ()
     {
          return mats.indexOf(this);
     }
}
