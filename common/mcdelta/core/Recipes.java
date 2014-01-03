package mcdelta.core;

import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
     public static void addCraftingRecipes ()
     {
          for (final ItemMaterial mat : MaterialRegistry.materials())
          {
               final String material = mat.getOreDictionaryName();
               
               if (mat.needsTools())
               {
                    // Shovel
                    ItemStack result = new ItemStack(DeltaContent.shovels.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(result, " x ", " o ", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Pickaxe
                    result = new ItemStack(DeltaContent.pickaxes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(result, "xxx", " o ", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Axe
                    result = new ItemStack(DeltaContent.axes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(result, " xx", " ox", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Sword
                    result = new ItemStack(DeltaContent.swords.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(result, " x ", " x ", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Hoe
                    result = new ItemStack(DeltaContent.hoes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(result, " xx", " o ", " o ", 'x', material, 'o', "stickWood"));
               }
          }
     }
}
