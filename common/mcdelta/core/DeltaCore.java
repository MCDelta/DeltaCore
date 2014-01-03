package mcdelta.core;

import java.io.IOException;
import java.util.Random;

import mcdelta.core.config.CoreConfig;
import mcdelta.core.logging.Logger;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.network.PacketHandler;
import mcdelta.core.proxy.CommonProxy;
import mcdelta.core.support.LimitedModSupport;
import mcdelta.core.support.compatibility.CompatibilityHandler;
import net.minecraft.block.Block;
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
     // TODO TOP PRIORITY
     // - Fix Item ID overlap
     // - create an item that can have multiple Tool mats via NBT or meta
     // - Use item to allow for dynamically adding materials
     // 
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
     
     
     
     
     @EventHandler
     public void preInit (final FMLPreInitializationEvent event)
     {
          Logger.logger = event.getModLog();
          localizationWarnings.append("The following are missing localized names: \n");
          this.init(event, new CoreConfig());
          
          OreDictionary.registerOre("ingotIron", new ItemStack(Item.ingotIron));
          OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));
          OreDictionary.registerOre("gemDiamond", new ItemStack(Item.diamond));
          
          MaterialRegistry.addVanillaMaterials();
          doLimitedModSupport();
     }
     
     
     
     
     @EventHandler
     public void load (final FMLInitializationEvent event)
     {
          for (ModDelta mod : ModDelta.deltaMods)
          {
               mod.content().addContent();
          }
          
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
     
     
     
     
     /**
      * A simple if statement to check if a mod is loaded. Should NOT be used
      * for instance to instance communication. Use the CompatibilityHandler for
      * that.
      */
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
     
     private IContent content = new DeltaContent();
     
     
     
     
     public IContent content ()
     {
          return content;
     }
}
