package mcdelta.core.item;

import static mcdelta.core.assets.Assets.log;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import mcdelta.core.DeltaCore;
import mcdelta.core.EnumMCDMods;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

public class ItemDelta extends Item
{
     public static Map<ToolMaterial, ItemWeapon>       swords           = new HashMap();
     public static Map<ToolMaterial, ItemDeltaPickaxe> pickaxes         = new HashMap();
     public static Map<ToolMaterial, ItemDeltaShovel>  shovels          = new HashMap();
     public static Map<ToolMaterial, ItemDeltaAxe>     axes             = new HashMap();
     public static Map<ToolMaterial, ItemDeltaHoe>     hoes             = new HashMap();
     
     public static Map<ToolMaterial, ItemDeltaArmor>   helmets          = new HashMap();
     
     static
     {
          for (ToolMaterial mat : ToolMaterial.mats)
          {
               if (mat.needsTools())
               {
                    ItemDeltaShovel shovel = new ItemDeltaShovel(mat.getMod(), mat);
                    MinecraftForge.setToolClass(shovel, "shovel", mat.getHarvestLevel());
                    shovels.put(mat, shovel);
                    
                    ItemDeltaPickaxe pick = new ItemDeltaPickaxe(mat.getMod(), mat);
                    MinecraftForge.setToolClass(pick, "pickaxe", mat.getHarvestLevel());
                    pickaxes.put(mat, pick);
                    
                    ItemDeltaAxe axe = new ItemDeltaAxe(mat.getMod(), mat);
                    MinecraftForge.setToolClass(axe, "axe", mat.getHarvestLevel());
                    axes.put(mat, axe);
                    
                    ItemWeapon sword = new ItemWeapon("sword", mat.getMod(), mat, 4.0F);
                    swords.put(mat, sword);
                    
                    ItemDeltaHoe hoe = new ItemDeltaHoe(mat.getMod(), mat);
                    hoes.put(mat, hoe);
               }
               
               if (mat.armorInfo != null)
               {
                    ItemDeltaArmor helmet = new ItemDeltaArmor(mat.getMod(), mat, 0);
                    helmets.put(mat, helmet);
               }
          }
     }
     
     public EnumMCDMods                                mod;
     public String                                     name;
     private boolean                                   checkUnlocalized = true;
     
     
     
     
     public ItemDelta (String s)
     {
          this(EnumMCDMods.DELTA_CORE, s);
     }
     
     
     
     
     public ItemDelta (EnumMCDMods m, String s)
     {
          this(m, s, true);
     }
     
     
     
     
     public ItemDelta (EnumMCDMods m, String s, boolean b)
     {
          super(DeltaCore.config.getItemID(m, s));
          this.maxStackSize = 64;
          this.setCreativeTab(CreativeTabs.tabAllSearch);
          
          this.checkUnlocalized = b;
          
          // ItemDelta code
          this.mod = m;
          this.name = s;
          String unlocalized = mod.modid.toLowerCase() + ":" + name;
          this.setUnlocalizedName(unlocalized);
          
          if (checkUnlocalized && !StatCollector.func_94522_b("item." + unlocalized + ".name"))
          {
               DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
          }
          
          if (this instanceof IExtraPasses)
          {
               ClientProxy.extraPasses.add(this);
          }
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          String s = this.name.replace(".", "_");
          
          this.itemIcon = doRegister(s, register);
     }
     
     
     
     
     protected Icon doRegister (String s, IconRegister register)
     {
          return doRegister(mod.modid, s, register);
     }
     
     
     
     
     public static Icon doRegister (String modid, String s, IconRegister register)
     {
          ResourceLocation loc = new ResourceLocation(modid.toLowerCase(), "textures/items/" + s + ".png");
          
          if (Assets.rescourceExists(loc))
          {
               return register.registerIcon(modid.toLowerCase() + ":" + s);
          }
          
          else
          {
               log(Level.SEVERE, "missing icon! " + loc);
               return register.registerIcon(DeltaCore.MOD_ID + ":null");
          }
     }
     
     
     
     
     public String getModid ()
     {
          return mod.modid;
     }
     
     
     
     
     public static void loadItems ()
     {
          for (ModDelta mod : ModDelta.mods)
          {
               mod.doThings(ModDelta.Stage.LOAD_ITEMS);
          }
     }
}
