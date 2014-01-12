package mcdelta.core.logging;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import mcdelta.core.config.Settings;

public class Logger
{
     public static java.util.logging.Logger logger;
     
     
     
     
     public static void blank ()
     {
          log("\n");
     }
     
     
     
     
     public static void log (final Object... message)
     {
          if (Settings.LOG_MASTER || !Settings.LOG_INFO)
          {
               return;
          }
          log(Level.INFO, message);
     }
     
     
     
     
     public static void debug (final Object... message)
     {
          if (Settings.LOG_MASTER || !Settings.LOG_DEBUG)
          {
               return;
          }
          log(Debug.DEBUG, message);
     }
     
     
     
     
     public static void config (final Object... message)
     {
          if (Settings.LOG_MASTER || !Settings.LOG_CONFIG)
          {
               return;
          }
          log(Level.CONFIG, message);
     }
     
     
     
     
     public static void severe (final Object... message)
     {
          if (Settings.LOG_MASTER || !Settings.LOG_SEVERE)
          {
               return;
          }
          log(Level.SEVERE, message);
     }
     
     
     
     
     public static void warning (final Object... message)
     {
          if (Settings.LOG_MASTER || !Settings.LOG_WARNING)
          {
               return;
          }
          log(Level.WARNING, message);
     }
     
     
     
     
     public static void log (final Level level, final Object... message)
     {
          if (Settings.LOG_MASTER)
          {
               return;
          }
          
          final StringBuilder sb = new StringBuilder();
          
          if (level != Level.INFO && level != Debug.DEBUG)
          {
               sb.append("\n");
               sb.append("\n");
          }
          
          if (level == Level.SEVERE)
          {
               sb.append("!!!!!");
               sb.append("\n");
          }
          
          int index = -1;
          
          for (final Object obj : message)
          {
               index++;
               
               if (obj instanceof List)
               {
                    sb.append(list((List<?>) obj));
               }
               
               else if (obj instanceof Object[])
               {
                    sb.append(list(Arrays.asList(obj)));
               }
               
               else
               {
                    if (index != 0)
                    {
                         sb.append("  :  ");
                    }
                    sb.append(obj);
               }
          }
          
          if (level == Level.SEVERE)
          {
               sb.append("\n");
               sb.append("!!!!!");
          }
          
          if (level != Level.INFO && level != Debug.DEBUG)
          {
               sb.append("\n");
          }
          
          logger.log(level, sb.toString());
     }
     
     
     
     
     private static StringBuilder list (final List<?> list)
     {
          final StringBuilder sb = new StringBuilder();
          
          for (final Object obj : list)
          {
               sb.append("\n");
               sb.append("- " + obj);
          }
          return sb;
     }
}
