package mcdelta.core.material;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;

public final class MaterialRegistry
{
     private static List<ItemMaterial> mats = new ArrayList<ItemMaterial>();
     
     public static ItemMaterial        IRON;
     public static ItemMaterial        WOOD;
     public static ItemMaterial        STONE;
     public static ItemMaterial        DIAMOND;
     public static ItemMaterial        GOLD;
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final String name, final int color, final String oreName, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(owner, new Object[]
          { name, color, oreName, false, true, false }, tool, uses, armor);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final String name, final int color, final String oreName, final EnumToolMaterial tool)
     {
          return add(owner, new Object[]
          { name, color, oreName, false, true, false }, tool);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final String name, final int color, final String oreName, final boolean needTool, final boolean needWeapon, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(owner, new Object[]
          { name, color, oreName, needTool, needWeapon, false }, tool, uses, armor);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final String name, final int color, final String oreName, final boolean needTool, final boolean needWeapon, final EnumToolMaterial tool)
     {
          return add(owner, new Object[]
          { name, color, oreName, needTool, needWeapon, false }, tool);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final Object[] delta, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(owner, delta, new Object[]
          { tool.getHarvestLevel(), tool.getMaxUses(), tool.getEfficiencyOnProperMaterial(), tool.getDamageVsEntity(), tool.getEnchantability() }, new Object[]
          { uses, new int[]
          { armor.getDamageReductionAmount(0), armor.getDamageReductionAmount(1), armor.getDamageReductionAmount(2), armor.getDamageReductionAmount(3) }, armor.getEnchantability() }, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final Object[] delta, final EnumToolMaterial tool)
     {
          return add(owner, delta, new Object[]
          { tool.getHarvestLevel(), tool.getMaxUses(), tool.getEfficiencyOnProperMaterial(), tool.getDamageVsEntity(), tool.getEnchantability() }, null, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final Object[] delta, final Object[] tool, final Object[] armor)
     {
          return add(owner, delta, tool, armor, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final ModDelta owner, final Object[] delta, final Object[] tool, final Object[] armor, final EnumToolMaterial toolMat, final EnumArmorMaterial armorMat)
     {
          final ItemMaterial tmp = new ItemMaterial(owner, delta, tool, armor, toolMat, armorMat);
          mats.add(tmp);
          for (ModDelta mod : ModDelta.deltaMods)
          {
               if (mod.content() != null)
               {
                    mod.content().addMaterialBasedContent(tmp);
               }
          }
          return tmp;
     }
     
     
     
     
     public static List<ItemMaterial> materials ()
     {
          return mats;
     }
     
     
     
     
     public static void addVanillaMaterials ()
     {
          IRON = add(DeltaCore.instance, "iron", 0xffffff, "ingotIron", EnumToolMaterial.IRON);
          WOOD = add(DeltaCore.instance, "wood", 0x866526, "plankWood", EnumToolMaterial.WOOD);
          STONE = add(DeltaCore.instance, "stone", 0x9a9a9a, "cobblestone", EnumToolMaterial.STONE);
          DIAMOND = add(DeltaCore.instance, "diamond", 0x33ebcb, "gemDiamond", EnumToolMaterial.EMERALD);
          GOLD = add(DeltaCore.instance, "gold", 0xeaee57, "ingotGold", EnumToolMaterial.GOLD);
     }
}
