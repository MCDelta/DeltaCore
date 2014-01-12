package mcdelta.core;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.item.ItemDeltaArmor;
import mcdelta.core.item.ItemDeltaAxe;
import mcdelta.core.item.ItemDeltaHoe;
import mcdelta.core.item.ItemDeltaPickaxe;
import mcdelta.core.item.ItemDeltaShovel;
import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ItemMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class DeltaContent implements IContent
{
     public static Map<ItemMaterial, ItemWeapon>       swords   = new HashMap<ItemMaterial, ItemWeapon>();
     public static Map<ItemMaterial, ItemDeltaPickaxe> pickaxes = new HashMap<ItemMaterial, ItemDeltaPickaxe>();
     public static Map<ItemMaterial, ItemDeltaShovel>  shovels  = new HashMap<ItemMaterial, ItemDeltaShovel>();
     public static Map<ItemMaterial, ItemDeltaAxe>     axes     = new HashMap<ItemMaterial, ItemDeltaAxe>();
     public static Map<ItemMaterial, ItemDeltaHoe>     hoes     = new HashMap<ItemMaterial, ItemDeltaHoe>();
     
     public static Map<ItemMaterial, ItemDeltaArmor>   helmets  = new HashMap<ItemMaterial, ItemDeltaArmor>();
     public static Map<ItemMaterial, ItemDeltaArmor>   chests   = new HashMap<ItemMaterial, ItemDeltaArmor>();
     public static Map<ItemMaterial, ItemDeltaArmor>   pants    = new HashMap<ItemMaterial, ItemDeltaArmor>();
     public static Map<ItemMaterial, ItemDeltaArmor>   boots    = new HashMap<ItemMaterial, ItemDeltaArmor>();
     
     
     
     
     @Override
     public void addContent ()
     {
     }
     
     
     
     
     @Override
     public void addMaterialBasedContent (final ItemMaterial mat)
     {
          if (mat.needsTools())
          {
               final ItemDeltaShovel shovel = new ItemDeltaShovel(DeltaCore.instance, mat);
               MinecraftForge.setToolClass(shovel, "shovel", mat.getHarvestLevel());
               shovels.put(mat, shovel);
               
               final ItemDeltaPickaxe pick = new ItemDeltaPickaxe(DeltaCore.instance, mat);
               MinecraftForge.setToolClass(pick, "pickaxe", mat.getHarvestLevel());
               pickaxes.put(mat, pick);
               
               final ItemDeltaAxe axe = new ItemDeltaAxe(DeltaCore.instance, mat);
               MinecraftForge.setToolClass(axe, "axe", mat.getHarvestLevel());
               axes.put(mat, axe);
               
               final ItemWeapon sword = new ItemWeapon("sword", DeltaCore.instance, mat, 4.0F);
               swords.put(mat, sword);
               
               final ItemDeltaHoe hoe = new ItemDeltaHoe(DeltaCore.instance, mat);
               hoes.put(mat, hoe);
          }
          
          if (mat.needsArmor())
          {
               final ItemDeltaArmor helmet = new ItemDeltaArmor(DeltaCore.instance, mat, 0);
               helmets.put(mat, helmet);
               
               final ItemDeltaArmor chest = new ItemDeltaArmor(DeltaCore.instance, mat, 1);
               chests.put(mat, chest);
               
               final ItemDeltaArmor pant = new ItemDeltaArmor(DeltaCore.instance, mat, 2);
               pants.put(mat, pant);
               
               final ItemDeltaArmor boot = new ItemDeltaArmor(DeltaCore.instance, mat, 3);
               boots.put(mat, boot);
          }
     }
     
     
     
     
     @Override
     public void addRecipes ()
     {
          
     }
     
     
     
     
     @Override
     public void addMaterialBasedRecipes (final ItemMaterial mat)
     {
          final String material = mat.oreName();
          String stickMaterial = "stickWood";
          
          if (mat.nonStickCrafter() != null)
          {
               stickMaterial = mat.nonStickCrafter().getKey();
          }
          
          if (mat.needsTools())
          {
               // Shovel
               ItemStack result = new ItemStack(DeltaContent.shovels.get(mat));
               if (mat.toolEnchant() != null)
               {
                    result.addEnchantment(mat.toolEnchant(), mat.toolEnchantLvl());
               }
               GameRegistry.addRecipe(new ShapedOreRecipe(result, " x ", " o ", " o ", 'x', material, 'o', stickMaterial));
               
               // Pickaxe
               result = new ItemStack(DeltaContent.pickaxes.get(mat));
               if (mat.toolEnchant() != null)
               {
                    result.addEnchantment(mat.toolEnchant(), mat.toolEnchantLvl());
               }
               GameRegistry.addRecipe(new ShapedOreRecipe(result, "xxx", " o ", " o ", 'x', material, 'o', stickMaterial));
               
               // Axe
               result = new ItemStack(DeltaContent.axes.get(mat));
               if (mat.toolEnchant() != null)
               {
                    result.addEnchantment(mat.toolEnchant(), mat.toolEnchantLvl());
               }
               GameRegistry.addRecipe(new ShapedOreRecipe(result, " xx", " ox", " o ", 'x', material, 'o', stickMaterial));
               
               // Sword
               result = new ItemStack(DeltaContent.swords.get(mat));
               if (mat.weaponEnchant() != null)
               {
                    result.addEnchantment(mat.weaponEnchant(), mat.weaponEnchantLvl());
               }
               GameRegistry.addRecipe(new ShapedOreRecipe(result, " x ", " x ", " o ", 'x', material, 'o', stickMaterial));
               
               // Hoe
               result = new ItemStack(DeltaContent.hoes.get(mat));
               if (mat.toolEnchant() != null)
               {
                    result.addEnchantment(mat.toolEnchant(), mat.toolEnchantLvl());
               }
               GameRegistry.addRecipe(new ShapedOreRecipe(result, " xx", " o ", " o ", 'x', material, 'o', stickMaterial));
          }
          
          if (mat.needsArmor())
          {
               // Helmet
               ItemStack result = new ItemStack(DeltaContent.helmets.get(mat));
               GameRegistry.addRecipe(new ShapedOreRecipe(result, "xxx", "x x", "   ", 'x', material));
               
               // Plate
               result = new ItemStack(DeltaContent.chests.get(mat));
               GameRegistry.addRecipe(new ShapedOreRecipe(result, "x x", "xxx", "xxx", 'x', material));
               
               // Legs
               result = new ItemStack(DeltaContent.pants.get(mat));
               GameRegistry.addRecipe(new ShapedOreRecipe(result, "xxx", "x x", "x x", 'x', material));
               
               // Boots
               result = new ItemStack(DeltaContent.boots.get(mat));
               GameRegistry.addRecipe(new ShapedOreRecipe(result, "   ", "x x", "x x", 'x', material));
          }
     }
     
     
}
