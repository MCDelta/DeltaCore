package mcdelta.core.utils.handler.compatibility;

import mcdelta.core.logging.Logger;
import cpw.mods.fml.common.Loader;

/**
 * Internal Helper used to keep data values and run the init method
 * 
 * @author Captain_Shadows
 */
final class Handler
{
     private final String name;
     private final String handler;
     
     
     
     
     public Handler (final String name, final String handler)
     {
          super();
          this.name = name;
          this.handler = handler;
     }
     
     
     
     
     public void init ()
     {
          // Check if the API is loaded
          if (Loader.isModLoaded(this.name))
          {
               // Create the temporal variables for internal usage
               Class<? extends ICompatibility> tmpHandler = null;
               ICompatibility tmp = null;
               
               try
               {
                    // Try to find the Handler Class
                    tmpHandler = (Class<? extends ICompatibility>) Class.forName(this.handler, false, Loader.instance().getModClassLoader());
               }
               catch (final ClassNotFoundException e)
               {
                    Logger.severe(e, String.format("MC Delta has failed to find a compatibility class with %s, please inform the CCM Team", this.name));
                    return;
               }
               try
               {
                    // Try to crate a new instance of said handler
                    tmp = tmpHandler.newInstance();
               }
               catch (final Exception e)
               {
                    Logger.severe(e, String.format("MC Delta has failed to create a new instance of a compatibility with %s, please inform the CCM Team", this.name));
                    return;
               }
               try
               {
                    // Initialize the compatibility features
                    tmp.init();
               }
               catch (final Exception e)
               {
                    Logger.severe(e, String.format("MC Delta has failed to load a compatibility with %s, please inform the CCM Team", this.name));
                    return;
               }
          }
     }
}
