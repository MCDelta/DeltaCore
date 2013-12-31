package mcdelta.core;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import mcdelta.core.config.Settings;

public class Logger
{
    public static java.util.logging.Logger logger;

    public static void log(Object... message)
    {
        log(Level.INFO, message);
    }

    public static void log(Level level, Object... message)
    {
        StringBuilder sb = new StringBuilder();

        if (level != Level.INFO)
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

        for (Object obj : message)
        {
            index++;

            if (obj instanceof List)
            {
                sb.append(list((List<?>) obj));
            }

            else if (obj instanceof Object[])
            {
                sb.append(Arrays.asList(obj));
            }

            else
            {
                if (index != 0)
                {
                    sb.append("  :  ");
                }

                try
                {
                    sb.append(obj);
                }

                catch (NullPointerException e)
                {
                    sb.append("!!!!! This would have crashed all the things  " + e + " !!!!!");
                }
            }
        }

        if (level == Level.SEVERE)
        {
            sb.append("\n");
            sb.append("!!!!!");
        }

        if (level != Level.INFO)
        {
            sb.append("\n");
        }

        if (Settings.LOG_MASTER)
        {
            return;
        } else if ((level == Level.CONFIG) && !Settings.LOG_CONFIG)
        {
            return;
        } else if ((level == Level.INFO) && !Settings.LOG_INFO)
        {
            return;
        } else if ((level == Level.SEVERE) && !Settings.LOG_SEVERE)
        {
            return;
        } else if ((level == Level.WARNING) && !Settings.LOG_WARNING)
        {
            return;
        }

        logger.log(Level.INFO, String.format(sb.toString()));
    }

    private static StringBuilder list(List<?> list)
    {
        StringBuilder sb = new StringBuilder();

        for (Object obj : list)
        {
            sb.append("/n");
            sb.append("- " + obj);
        }

        return null;
    }
}