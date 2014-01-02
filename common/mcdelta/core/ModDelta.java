package mcdelta.core;

import java.io.File;

import mcdelta.core.config.ConfigWrapper;
import mcdelta.core.config.IConfig;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModDelta
{
     protected ConfigWrapper config;
     
     
     
     
     public ModContainer mod ()
     {
          return Loader.instance().getIndexedModList().get(this.id());
     }
     
     
     
     
     public String id ()
     {
          return this.getClass().getAnnotation(Mod.class).modid();
     }
     
     
     
     
     public String name ()
     {
          return this.mod().getName();
     }
     
     
     
     
     public String version ()
     {
          return this.mod().getVersion();
     }
     
     
     
     
     public ConfigWrapper config ()
     {
          return this.config;
     }
     
     
     
     
     protected void initConfig (final FMLPreInitializationEvent evt)
     {
          // Get all the files
          final File configFolder = new File(evt.getModConfigurationDirectory().getAbsolutePath() + "/MCDelta/");
          configFolder.mkdirs();
          final File configFile = new File(configFolder.getAbsolutePath() + "/" + this.name() + ".cfg");
          
          // Create the config handler
          this.config = new ConfigWrapper();
          
          // Set the Configuration inside the Handler
          this.config.setConfiguration(new Configuration(configFile, true));
     }
     
     
     
     
     protected void init (final FMLPreInitializationEvent evt)
     {
          this.init(evt, null);
     }
     
     
     
     
     protected void init (final FMLPreInitializationEvent evt, final IConfig config)
     {
          this.initConfig(evt);
          if (config != null)
          {
               config.init(this.config);
          }
     }
}
