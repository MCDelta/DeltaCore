package mcdelta.core.config;

public class Settings
{
     public static String  CATEGORY_LOG       = "Logger";
     public static String  COMMENT_LOG        = "Enable or Disable different DeltaCore loggers. For debugging purposes it is helpful to have these on.";
     
     public static String  CATEGORY_MISC      = "Miscallaneous";
     public static String  COMMENT_MISC       = "Random settings that may or may not be useful to you.";
     
     public static String  LOG_MASTER_KEY     = "disable logging";
     public static boolean LOG_MASTER;
     
     public static String  LOG_INFO_KEY       = "log info";
     public static boolean LOG_INFO;
     
     public static String  LOG_CONFIG_KEY     = "log config";
     public static boolean LOG_CONFIG;
     
     public static String  LOG_SEVERE_KEY     = "log severe";
     public static boolean LOG_SEVERE;
     
     public static String  LOG_WARNING_KEY    = "log warning";
     public static boolean LOG_WARNING;
     
     public static String  LOG_DEBUG_KEY      = "log debug";
     public static boolean LOG_DEBUG;
     
     public static String  RANDOM_SEED_KEY    = "random seed";
     public static int     RANDOM_SEED        = 123456789;
     
     public static String  OREDIC_TOOLTIP_KEY = "add oredictionary name to tooltip";
     public static boolean OREDIC_TOOLTIP;
}
