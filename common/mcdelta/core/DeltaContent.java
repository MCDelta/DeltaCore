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
import net.minecraftforge.common.MinecraftForge;

public class DeltaContent implements IContent
{
     public static Map<ItemMaterial, ItemWeapon>       swords   = new HashMap<ItemMaterial, ItemWeapon>();
     public static Map<ItemMaterial, ItemDeltaPickaxe> pickaxes = new HashMap<ItemMaterial, ItemDeltaPickaxe>();
     public static Map<ItemMaterial, ItemDeltaShovel>  shovels  = new HashMap<ItemMaterial, ItemDeltaShovel>();
     public static Map<ItemMaterial, ItemDeltaAxe>     axes     = new HashMap<ItemMaterial, ItemDeltaAxe>();
     public static Map<ItemMaterial, ItemDeltaHoe>     hoes     = new HashMap<ItemMaterial, ItemDeltaHoe>();
     
     public static Map<ItemMaterial, ItemDeltaArmor>   helmets  = new HashMap<ItemMaterial, ItemDeltaArmor>();
     
     
     
     
     @Override
     public void addContent ()
     {
     }
     
     
     
     
     public void addMaterialBasedContent (final ItemMaterial mat)
     {
          if (mat.needsTools())
          {
               final ItemDeltaShovel shovel = new ItemDeltaShovel(mat.owner(), mat);
               MinecraftForge.setToolClass(shovel, "shovel", mat.getHarvestLevel());
               shovels.put(mat, shovel);
               
               final ItemDeltaPickaxe pick = new ItemDeltaPickaxe(mat.owner(), mat);
               MinecraftForge.setToolClass(pick, "pickaxe", mat.getHarvestLevel());
               pickaxes.put(mat, pick);
               
               final ItemDeltaAxe axe = new ItemDeltaAxe(mat.owner(), mat);
               MinecraftForge.setToolClass(axe, "axe", mat.getHarvestLevel());
               axes.put(mat, axe);
               
               final ItemWeapon sword = new ItemWeapon("sword", mat.owner(), mat, 4.0F);
               swords.put(mat, sword);
               
               final ItemDeltaHoe hoe = new ItemDeltaHoe(mat.owner(), mat);
               hoes.put(mat, hoe);
          }
          if (mat.armorInfo != null)
          {
               //final ItemDeltaArmor helmet = new ItemDeltaArmor(mat.owner(), mat, 0);
               //helmets.put(mat, helmet);
          }
     }
     
     
}
