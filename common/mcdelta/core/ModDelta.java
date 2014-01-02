package mcdelta.core;

import java.io.File;

import mcdelta.core.config.Config;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModDelta
{
    protected Config config;

    public ModContainer mod()
    {
        return Loader.instance().getIndexedModList().get(id());
    }

    public String id()
    {
        return this.getClass().getAnnotation(Mod.class).modid();
    }

    public String name()
    {
        return mod().getName();
    }

    public String version()
    {
        return mod().getVersion();
    }

    public Config config()
    {
        return config;
    }

    protected void initConfig(final FMLPreInitializationEvent evt)
    {
        // Get all the files
        File configFolder = new File(evt.getModConfigurationDirectory().getAbsolutePath() + "/MCDelta/");
        configFolder.mkdirs();
        File configFile = new File(configFolder.getAbsolutePath() + "/" + name() + ".cfg");

        // Create the config handler
        config = new Config();

        // Set the Configuration inside the Handler
        config.setConfiguration(new Configuration(configFile, true));
    }
}