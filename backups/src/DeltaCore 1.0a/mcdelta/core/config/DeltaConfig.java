package mcdelta.core.config;

import java.io.File;

import mcdelta.core.DeltaMod;
import mcdelta.core.assets.Assets;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class DeltaConfig
{
     private DeltaMod      mod;
     private Configuration configDefault;
     private Configuration configItems;
     private Configuration configBlocks;
     
     
     
     
     public DeltaConfig (DeltaMod deltaMod)
     {
          mod = deltaMod;
     }
     
     
     
     
     public void load (FMLPreInitializationEvent event)
     {
          String filePath = event.getSuggestedConfigurationFile().toPath().toString().replace(this.mod.getModid() + ".cfg", "MCDelta/");
          
          configDefault = new Configuration(new File(filePath + "core.cfg"));
          configItems = new Configuration(new File(filePath + "items.cfg"));
          configBlocks = new Configuration(new File(filePath + "blocks.cfg"));
     }
     
     
     
     
     public int getItemID (String s, int i)
     {
          int id = 0;
          
          configItems.load();
          
          id = configItems.get(this.mod.getModid() + "_itemIDs", s + " ID", this.mod.getItems().getFirstID() + i).getInt();
          
          configItems.save();
          
          return id;
     }
     
     
     
     
     public int getBlockID (String s, int i)
     {
          int id = 0;
          
          configBlocks.load();
          
          id = configBlocks.get(this.mod.getModid() + "_blockIDs", s + " ID", this.mod.getBlocks().getFirstID() + i).getInt();
          
          configBlocks.save();
          
          return id;
     }
     
     
     
     
     public DeltaMod getMod ()
     {
          return mod;
     }
}
