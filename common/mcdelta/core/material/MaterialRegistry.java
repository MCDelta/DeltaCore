package mcdelta.core.material;

import java.util.ArrayList;
import java.util.List;

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
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(new Object[]
          { name, color, oreName, false, true, false }, tool, uses, armor);
     }
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final EnumToolMaterial tool)
     {
          return add(new Object[]
          { name, color, oreName, false, true, false }, tool);
     }
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final boolean needTool, final boolean needWeapon, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(new Object[]
          { name, color, oreName, needTool, needWeapon, false }, tool, uses, armor);
     }
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final boolean needTool, final boolean needWeapon, final EnumToolMaterial tool)
     {
          return add(new Object[]
          { name, color, oreName, needTool, needWeapon, false }, tool);
     }
     
     
     
     
     public static ItemMaterial add (final Object[] delta, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(delta, new Object[]
          { tool.getHarvestLevel(), tool.getMaxUses(), tool.getEfficiencyOnProperMaterial(), tool.getDamageVsEntity(), tool.getEnchantability() }, new Object[]
          { uses, new int[]
          { armor.getDamageReductionAmount(0), armor.getDamageReductionAmount(1), armor.getDamageReductionAmount(2), armor.getDamageReductionAmount(3) }, armor.getEnchantability() }, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final Object[] delta, final EnumToolMaterial tool)
     {
          return add(delta, new Object[]
          { tool.getHarvestLevel(), tool.getMaxUses(), tool.getEfficiencyOnProperMaterial(), tool.getDamageVsEntity(), tool.getEnchantability() }, null, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final Object[] delta, final Object[] tool, final Object[] armor)
     {
          return add(delta, tool, armor, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final Object[] delta, final Object[] tool, final Object[] armor, final EnumToolMaterial toolMat, final EnumArmorMaterial armorMat)
     {
          final ItemMaterial tmp = new ItemMaterial(delta, tool, armor, toolMat, armorMat);
          mats.add(tmp);
          for (final ModDelta mod : ModDelta.deltaMods)
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
          IRON = add("iron", 0xffffff, "ingotIron", EnumToolMaterial.IRON);
          WOOD = add("wood", 0x866526, "plankWood", EnumToolMaterial.WOOD);
          STONE = add("stone", 0x9a9a9a, "cobblestone", EnumToolMaterial.STONE);
          DIAMOND = add("diamond", 0x33ebcb, "gemDiamond", EnumToolMaterial.EMERALD);
          GOLD = add("gold", 0xeaee57, "ingotGold", EnumToolMaterial.GOLD);
     }
}
