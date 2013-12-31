package mcdelta.core.config;


public class Settings
{
     public static String DESCRIPTION_CORE = "This config file is dedicated to the DeltaCore mod specifically. Generally it will be all technical stuffs.";
     public static String DESCRIPTION_ITEM = "This config file houses options to change item IDs and other item related options.";
     public static String DESCRIPTION_BLOCK = "This config file houses options to change block IDs and other block related options.";
     public static String DESCRIPTION_ENCHANT = "This config file contains options to change enchantment IDs and other enchant related options.";
     
     
     
     
     public static String CATEGORY_LOG = "Logger";
     public static String COMMENT_LOG = "Enable or Disable different DeltaCore loggers. For debugging purposes it is helpful to have these on.";
     
     public static String CATEGORY_MISC = "Miscallaneous";
     public static String COMMENT_MISC = "Random settings that may or may not be useful to you.";
     
     
     
     
     public static String LOG_MASTER_KEY = "disable log";
     public static boolean LOG_MASTER = false;
     
     public static String LOG_INFO_KEY = "log info";
     public static boolean LOG_INFO = true;
     
     public static String LOG_CONFIG_KEY = "log config";
     public static boolean LOG_CONFIG = true;
     
     public static String LOG_SEVERE_KEY = "log severe";
     public static boolean LOG_SEVERE = true;
     
     public static String LOG_WARNING_KEY = "log warning";
     public static boolean LOG_WARNING = true;
     
     public static String RANDOM_SEED_KEY = "random seed";
     public static int RANDOM_SEED = 123456789;
     
}
