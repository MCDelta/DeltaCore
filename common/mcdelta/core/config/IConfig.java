package mcdelta.core.config;

import cpw.mods.fml.common.FMLCommonHandler;

public abstract class IConfig
{
     public static final String UNIVERSAL   = "Universal";
     
     public static final String CLIENT_SIDE = "Client";
     
     public static final String SERVER_SIDE = "Server";
     
     
     
     
     /** Initializes the configuration file */
     public void init (final ConfigWrapper config)
     {
          config.load();
          
          this.initCommon(config);
          
          if (FMLCommonHandler.instance().getSide().isServer())
          {
               this.initServer(config);
          }
          else
          {
               this.initClient(config);
          }
          config.save();
     }
     
     
     
     
     protected abstract void initCommon (ConfigWrapper config);
     
     
     
     
     protected void initClient (final ConfigWrapper config)
     {
          config.getConfiguration().addCustomCategoryComment(CLIENT_SIDE, "This Category only contains options that affect the client");
     }
     
     
     
     
     protected void initServer (final ConfigWrapper config)
     {
          config.getConfiguration().addCustomCategoryComment(SERVER_SIDE, "This Category only contains options that affect the server");
     }
}
