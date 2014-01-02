package mcdelta.core.config;

public class CoreConfig extends IConfig
{
     @Override
     protected void initCommon (final ConfigWrapper config)
     {
          config.getConfiguration().addCustomCategoryComment(Settings.CATEGORY_LOG, Settings.COMMENT_LOG);
          Settings.LOG_MASTER = config.get(Settings.CATEGORY_LOG, Settings.LOG_MASTER_KEY, false);
          Settings.LOG_INFO = config.get(Settings.CATEGORY_LOG, Settings.LOG_INFO_KEY, true);
          Settings.LOG_CONFIG = config.get(Settings.CATEGORY_LOG, Settings.LOG_CONFIG_KEY, true);
          Settings.LOG_SEVERE = config.get(Settings.CATEGORY_LOG, Settings.LOG_SEVERE_KEY, true);
          Settings.LOG_WARNING = config.get(Settings.CATEGORY_LOG, Settings.LOG_WARNING_KEY, true);
          Settings.LOG_DEBUG = config.get(Settings.CATEGORY_LOG, Settings.LOG_DEBUG_KEY, false);
          
          config.getConfiguration().addCustomCategoryComment(Settings.CATEGORY_MISC, Settings.COMMENT_MISC);
          Settings.RANDOM_SEED = config.get(Settings.CATEGORY_MISC, Settings.RANDOM_SEED_KEY, 123456789);
     }
}
