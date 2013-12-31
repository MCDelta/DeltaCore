package mcdelta.core.block;

import java.util.logging.Level;

import mcdelta.core.DeltaCore;
import mcdelta.core.EnumMCDMods;
import mcdelta.core.Logger;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockDelta extends Block
{
    private final EnumMCDMods mod;
    public String name;

    public BlockDelta(String s, Material mat)
    {
        this(EnumMCDMods.DELTA_CORE, s, mat);
    }

    public BlockDelta(EnumMCDMods m, String s, Material mat)
    {
        super(DeltaCore.config.getBlockID(m, s), mat);

        mod = m;
        name = s;
        String unlocalized = mod.modid.toLowerCase() + ":" + s;
        setUnlocalizedName(unlocalized);
        setCreativeTab(CreativeTabs.tabAllSearch);

        GameRegistry.registerBlock(this, s);

        if (!StatCollector.func_94522_b("tile." + unlocalized + ".name"))
        {
            DeltaCore.localizationWarnings.append("- tile." + unlocalized + ".name \n");
        }
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        String s = name.replace(".", "_");

        blockIcon = doRegister(s, register);
    }

    protected Icon doRegister(String s, IconRegister register)
    {
        ResourceLocation loc = new ResourceLocation(mod.modid.toLowerCase(), "textures/blocks/" + s + ".png");

        if (Assets.resourceExists(loc))
        {
            return register.registerIcon(mod.modid + ":" + s);
        }
        return register.registerIcon(DeltaCore.MOD_ID + ":null");
    }

    protected void setBlockBounds(float[] shape)
    {
        this.setBlockBounds(shape[0], shape[1], shape[2], shape[3], shape[4], shape[5]);
    }

    public String getModid()
    {
        return mod.modid;
    }

    public static void log(Object... message)
    {
        Logger.log(Level.INFO, message);
    }

    public static void loadBlocks()
    {
        for (ModDelta mod : ModDelta.mods)
        {
            mod.doThings(ModDelta.Stage.LOAD_BLOCKS);
        }
    }
}
