package mcdelta.core;

import mcdelta.core.item.ItemDelta;
import mcdelta.core.material.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
    public static void addCraftingRecipes()
    {
        for (ToolMaterial mat : ToolMaterial.mats)
        {
            String material = mat.getOreDictionaryName();

            if (mat.needsTools())
            {
                // Shovel
                ItemStack result = new ItemStack(ItemDelta.shovels.get(mat));
                GameRegistry.addRecipe(new ShapedOreRecipe(result, " x ", " o ", " o ", 'x', material, 'o', "stickWood"));

                // Pickaxe
                result = new ItemStack(ItemDelta.pickaxes.get(mat));
                GameRegistry.addRecipe(new ShapedOreRecipe(result, "xxx", " o ", " o ", 'x', material, 'o', "stickWood"));

                // Axe
                result = new ItemStack(ItemDelta.axes.get(mat));
                GameRegistry.addRecipe(new ShapedOreRecipe(result, " xx", " ox", " o ", 'x', material, 'o', "stickWood"));

                // Sword
                result = new ItemStack(ItemDelta.swords.get(mat));
                GameRegistry.addRecipe(new ShapedOreRecipe(result, " x ", " x ", " o ", 'x', material, 'o', "stickWood"));

                // Hoe
                result = new ItemStack(ItemDelta.hoes.get(mat));
                GameRegistry.addRecipe(new ShapedOreRecipe(result, " xx", " o ", " o ", 'x', material, 'o', "stickWood"));
            }
        }
    }
}
