package mcdelta.core;

import mcdelta.core.assets.Assets;
import mcdelta.core.block.DeltaBlocks;
import mcdelta.core.config.DeltaConfig;
import mcdelta.core.entity.DeltaEntities;
import mcdelta.core.item.DeltaItems;
import mcdelta.core.network.PacketHandler;
import mcdelta.tuxweapons.TuxWeaponsCore;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod (modid = DeltaCore.MOD_ID, name = "DeltaCore", useMetadata = true, version = "1.0")
@NetworkMod (clientSideRequired = true, serverSideRequired = false, channels = { DeltaCore.MOD_ID }, packetHandler = PacketHandler.class)
public class DeltaCore implements DeltaMod
{
     public static final String    MOD_ID   = "deltacore";
     
     @Instance (MOD_ID)
     public static DeltaCore       instance;
     
     @SidedProxy (clientSide = "mcdelta.core.client.ClientProxy", serverSide = "mcdelta.core.CommonProxy")
     public static CommonProxy     proxy;
     
     private static DeltaMod       theMod;
     
     protected final DeltaItems    items    = new DeltaItems(this);
     protected final DeltaBlocks   blocks   = new DeltaBlocks(this);
     protected final DeltaEntities entities = new DeltaEntities(this);
     protected final DeltaConfig   config   = new DeltaConfig(this);
     
     public static enum InitStage
     {
          PRE, LOAD, POST;
     }
     
     
     
     
     @EventHandler
     public void preInit (FMLPreInitializationEvent event)
     {
          theMod = this;
          
          if (event.getModMetadata().dependencies.isEmpty())
          {
               Assets.log = event.getModLog();
               proxy.registerRenderers();
          }
          
          InitStage stage = InitStage.PRE;
          
          getItems().load(stage);
          getBlocks().load(stage);
          getEntities().load(stage);
          
          getConfig().load(event);
     }
     
     
     
     
     @EventHandler
     public void load (FMLInitializationEvent event)
     {
          InitStage stage = InitStage.LOAD;
          
          getItems().load(stage);
          getBlocks().load(stage);
          getEntities().load(stage);
     }
     
     
     
     
     @EventHandler
     public void postInit (FMLPostInitializationEvent event)
     {
          InitStage stage = InitStage.POST;
          
          getItems().load(stage);
          getBlocks().load(stage);
          getEntities().load(stage);
     }
     
     
     
     
     @Override
     public DeltaItems getItems ()
     {
          return items;
     }
     
     
     
     
     @Override
     public DeltaBlocks getBlocks ()
     {
          return blocks;
     }
     
     
     
     
     @Override
     public DeltaConfig getConfig ()
     {
          return config;
     }
     
     
     
     
     @Override
     public DeltaEntities getEntities ()
     {
          return entities;
     }
     
     
     
     
     @Override
     public String getModid ()
     {
          return MOD_ID;
     }
     
     
     
     
     public static DeltaMod getInstance ()
     {
          return theMod;
     }
}
