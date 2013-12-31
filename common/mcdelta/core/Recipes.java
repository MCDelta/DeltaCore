package mcdelta.core;

import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
     public static void addCraftingRecipes ()
     {
          for (ToolMaterial mat : ToolMaterial.mats)
          {
               String material = mat.getOreDictionaryName();
               
               if (mat.needsTools())
               {
                    // Shovel
                    ItemStack shovel = new ItemStack(ItemDelta.shovels.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(shovel, " x ", " o ", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Pickaxe
                    ItemStack pickaxe = new ItemStack(ItemDelta.pickaxes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(pickaxe, "xxx", " o ", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Axe
                    ItemStack axe = new ItemStack(ItemDelta.axes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(axe, " xx", " ox", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Sword
                    ItemStack sword = new ItemStack(ItemDelta.swords.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(sword, " x ", " x ", " o ", 'x', material, 'o', "stickWood"));
                    
                    // Hoe
                    ItemStack hoe = new ItemStack(ItemDelta.hoes.get(mat));
                    GameRegistry.addRecipe(new ShapedOreRecipe(hoe, " xx", " o ", " o ", 'x', material, 'o', "stickWood"));
               }
          }
     }
}
