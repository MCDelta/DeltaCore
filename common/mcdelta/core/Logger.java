package mcdelta.core;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import mcdelta.core.config.Settings;

public class Logger
{
    public static java.util.logging.Logger logger;

    public static void logBlank()
    {
        log("\n");
    }
    
    public static void log(Object... message)
    {
        if (!Settings.LOG_MASTER || !Settings.LOG_INFO)
        {
            return;
        }
        log(Level.INFO, message);
    }

    public static void config(Object... message)
    {
        if (!Settings.LOG_MASTER || !Settings.LOG_CONFIG)
        {
            return;
        }
        log(Level.CONFIG, message);
    }

    public static void severe(Object... message)
    {
        if (!Settings.LOG_MASTER || !Settings.LOG_SEVERE)
        {
            return;
        }
        log(Level.SEVERE, message);
    }

    public static void warning(Object... message)
    {
        if (!Settings.LOG_MASTER || !Settings.LOG_WARNING)
        {
            return;
        }
        log(Level.WARNING, message);
    }

    public static void log(Level level, Object... message)
    {
        if (!Settings.LOG_MASTER)
        {
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (level != Level.INFO)
        {
            sb.append("\n");
            sb.append("\n");
        } else if (level == Level.SEVERE)
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
            } else if (obj instanceof Object[])
            {
                sb.append(list(Arrays.asList(obj)));
            } else
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
        } else if (level != Level.INFO)
        {
            sb.append("\n");
        }

        logger.log(level, sb.toString());
    }

    private static StringBuilder list(List<?> list)
    {
        StringBuilder sb = new StringBuilder();

        for (Object obj : list)
        {
            sb.append("/n");
            sb.append("- " + obj);
        }

        return sb;
    }
}