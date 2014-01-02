package mcdelta.core.block;

import mcdelta.core.DeltaCore;
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
    private final ModDelta mod;
    public String name;

    public BlockDelta(String s, Material mat)
    {
        this(DeltaCore.instance, s, mat);
    }

    public BlockDelta(ModDelta mod, String name, Material mat)
    {
        super(mod.config().getBlockID(name), mat);

        this.mod = mod;
        this.name = name;
        String unlocalized = mod.id().toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);
        setCreativeTab(CreativeTabs.tabAllSearch);

        GameRegistry.registerBlock(this, name);

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
        ResourceLocation loc = new ResourceLocation(mod.id().toLowerCase(), "textures/blocks/" + s + ".png");

        if (Assets.resourceExists(loc))
        {
            return register.registerIcon(mod.id() + ":" + s);
        }
        return register.registerIcon(DeltaCore.MOD_ID + ":null");
    }

    protected void setBlockBounds(float[] shape)
    {
        this.setBlockBounds(shape[0], shape[1], shape[2], shape[3], shape[4], shape[5]);
    }

    public String getid()
    {
        return mod.id();
    }
}