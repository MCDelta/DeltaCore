package mcdelta.core;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.item.ItemDeltaArmor;
import mcdelta.core.item.ItemDeltaAxe;
import mcdelta.core.item.ItemDeltaHoe;
import mcdelta.core.item.ItemDeltaPickaxe;
import mcdelta.core.item.ItemDeltaShovel;
import mcdelta.core.item.ItemWeapon;
import mcdelta.core.material.ToolMaterial;
import net.minecraftforge.common.MinecraftForge;

public class DeltaContent
{
     public static Map<ToolMaterial, ItemWeapon>       swords   = new HashMap<ToolMaterial, ItemWeapon>();
     public static Map<ToolMaterial, ItemDeltaPickaxe> pickaxes = new HashMap<ToolMaterial, ItemDeltaPickaxe>();
     public static Map<ToolMaterial, ItemDeltaShovel>  shovels  = new HashMap<ToolMaterial, ItemDeltaShovel>();
     public static Map<ToolMaterial, ItemDeltaAxe>     axes     = new HashMap<ToolMaterial, ItemDeltaAxe>();
     public static Map<ToolMaterial, ItemDeltaHoe>     hoes     = new HashMap<ToolMaterial, ItemDeltaHoe>();
     
     public static Map<ToolMaterial, ItemDeltaArmor>   helmets  = new HashMap<ToolMaterial, ItemDeltaArmor>();
     
     
     
     
     public static void load ()
     {
          for (final ToolMaterial mat : ToolMaterial.mats)
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
               if (mat.armorInfo != null)
               {
                    final ItemDeltaArmor helmet = new ItemDeltaArmor(DeltaCore.instance, mat, 0);
                    helmets.put(mat, helmet);
               }
          }
     }
}
