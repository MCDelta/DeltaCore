package mcdelta.core;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;

public class ModDelta
{
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
}