package mcdelta.core;

import java.io.IOException;
import java.util.Random;

import mcdelta.core.config.CoreConfig;
import mcdelta.core.logging.Logger;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.network.PacketHandler;
import mcdelta.core.proxy.CommonProxy;
import mcdelta.core.support.LimitedModSupport;
import mcdelta.core.support.compatibility.CompatibilityHandler;
import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.reflect.ClassPath;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod (modid = DeltaCore.MOD_ID, useMetadata = true)
@NetworkMod (clientSideRequired = true, serverSideRequired = false, channels =
{ DeltaCore.MOD_ID }, packetHandler = PacketHandler.class)
public class DeltaCore extends ModDelta
{
     // TODO (possible ideas)
     // - camo creeper
     // - timed potion to prevent potion effects
     
     public static final String  MOD_ID               = "deltacore";
     
     @Instance (MOD_ID)
     public static DeltaCore     instance;
     
     @SidedProxy (clientSide = "mcdelta.core.proxy.ClientProxy", serverSide = "mcdelta.core.proxy.CommonProxy")
     public static CommonProxy   proxy;
     
     public static StringBuilder localizationWarnings = new StringBuilder();
     
     public static Random        rand                 = new Random();
     
     public static ToolMaterial  IRON;
     public static ToolMaterial  WOOD;
     public static ToolMaterial  STONE;
     public static ToolMaterial  DIAMOND;
     public static ToolMaterial  GOLD;
     
     
     
     
     @EventHandler
     public void preInit (final FMLPreInitializationEvent event)
     {
          OreDictionary.registerOre("ingotIron", new ItemStack(Item.ingotIron));
          OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));
          OreDictionary.registerOre("gemDiamond", new ItemStack(Item.diamond));
          
          Logger.logger = event.getModLog();
          localizationWarnings.append("The following are missing localized names: \n");
          this.init(event, new CoreConfig());
          
          addVanillaToolMaterials();
          doLimitedModSupport();
          
          DeltaContent.load();
     }
     
     
     
     
     private void addVanillaToolMaterials ()
     {
          IRON = new ToolMaterial("iron", 0xffffff, "ingotIron", EnumToolMaterial.IRON);
          WOOD = new ToolMaterial("wood", 0x866526, "plankWood", EnumToolMaterial.WOOD);
          STONE = new ToolMaterial("stone", 0x9a9a9a, "cobblestone", EnumToolMaterial.STONE);
          DIAMOND = new ToolMaterial("diamond", 0x33ebcb, "gemDiamond", EnumToolMaterial.EMERALD);
          GOLD = new ToolMaterial("gold", 0xeaee57, "ingotGold", EnumToolMaterial.GOLD);
     }
     
     
     
     
     @EventHandler
     public void load (final FMLInitializationEvent event)
     {
          Recipes.addCraftingRecipes();
          
          MinecraftForge.setBlockHarvestLevel(Block.oreNetherQuartz, "pickaxe", 2);
     }
     
     
     
     
     @EventHandler
     public void postInit (final FMLPostInitializationEvent event)
     {
          if (localizationWarnings.length() > 45)
          {
               Logger.debug(localizationWarnings);
          }
          proxy.registerRenderers();
          
          CompatibilityHandler.init();
     }
     
     
     
     
     private void doLimitedModSupport ()
     {
          ClassPath cp = null;
          try
          {
               cp = ClassPath.from(this.getClass().getClassLoader());
          }
          catch (IOException e)
          {
               e.printStackTrace();
          }
          
          for (ClassPath.ClassInfo info : cp.getTopLevelClassesRecursive("mcdelta.core.support"))
          {
               Class<?> c = null;
               try
               {
                    c = Class.forName(info.getName());
               }
               catch (ClassNotFoundException e)
               {
                    e.printStackTrace();
               }
               
               if (LimitedModSupport.class.isAssignableFrom(c))
               {
                    try
                    {
                         String name = null;
                         
                         if (c != LimitedModSupport.class)
                         {
                              name = ((LimitedModSupport) c.newInstance()).modid();
                         }
                         
                         if (name != null)
                         {
                              if ((Loader.isModLoaded(name)))
                              {
                                   Logger.log("Found mod support for " + name);
                                   ((LimitedModSupport) c.newInstance()).modLoaded();
                              }
                         }
                         
                    }
                    catch (InstantiationException e)
                    {
                         e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                         e.printStackTrace();
                    }
               }
          }
     }
}
