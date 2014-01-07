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
          return add(new DeltaInfo(name, color, oreName, false, true, true, false), tool, uses, armor);
     }
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final EnumToolMaterial tool)
     {
          return add(new DeltaInfo(name, color, oreName, false, true, false, false), tool);
     }
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final boolean needTool, final boolean needWeapon, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(new DeltaInfo(name, color, oreName, needTool, needWeapon, true, false), tool, uses, armor);
     }
     
     
     
     
     public static ItemMaterial add (final String name, final int color, final String oreName, final boolean needTool, final boolean needWeapon, final EnumToolMaterial tool)
     {
          return add(new DeltaInfo(name, color, oreName, needTool, needWeapon, false, false), tool);
     }
     
     
     
     
     public static ItemMaterial add (final DeltaInfo delta, final EnumToolMaterial tool, final int uses, final EnumArmorMaterial armor)
     {
          return add(delta, new ToolInfo(tool.getHarvestLevel(), tool.getMaxUses(), tool.getEfficiencyOnProperMaterial(), tool.getDamageVsEntity(), tool.getEnchantability()), new ArmorInfo(uses, new int[]
          { armor.getDamageReductionAmount(0), armor.getDamageReductionAmount(1), armor.getDamageReductionAmount(2), armor.getDamageReductionAmount(3) }, armor.getEnchantability()), null, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final DeltaInfo delta, final EnumToolMaterial tool)
     {
          return add(delta, new ToolInfo(tool.getHarvestLevel(), tool.getMaxUses(), tool.getEfficiencyOnProperMaterial(), tool.getDamageVsEntity(), tool.getEnchantability()), null, null, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final DeltaInfo delta, final ToolInfo tool)
     {
          return add(delta, tool, null, null, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final DeltaInfo delta, final ToolInfo tool, final ArmorInfo armor, final ExtraInfo extra)
     {
          return add(delta, tool, armor, extra, null, null);
     }
     
     
     
     
     public static ItemMaterial add (final DeltaInfo delta, final ToolInfo tool, final ArmorInfo armor, final ExtraInfo extra, final EnumToolMaterial toolMat, final EnumArmorMaterial armorMat)
     {
          final ItemMaterial tmp = new ItemMaterial(delta, tool, armor, extra, toolMat, armorMat);
          
          mats.add(tmp);
          
          for (final ModDelta mod : ModDelta.deltaMods)
          {
               if (mod.content() != null)
               {
                    mod.content().addMaterialBasedContent(tmp);
                    mod.content().addMaterialBasedRecipes(tmp);
               }
          }
          return tmp;
     }
     
     
     
     
     public static List<ItemMaterial> materials ()
     {
          return new ArrayList<ItemMaterial>(mats);
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
