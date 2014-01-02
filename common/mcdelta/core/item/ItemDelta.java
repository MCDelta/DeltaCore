package mcdelta.core.item;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.logging.Logger;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

public class ItemDelta extends Item
{
    public static Map<ToolMaterial, ItemWeapon> swords = new HashMap<ToolMaterial, ItemWeapon>();
    public static Map<ToolMaterial, ItemDeltaPickaxe> pickaxes = new HashMap<ToolMaterial, ItemDeltaPickaxe>();
    public static Map<ToolMaterial, ItemDeltaShovel> shovels = new HashMap<ToolMaterial, ItemDeltaShovel>();
    public static Map<ToolMaterial, ItemDeltaAxe> axes = new HashMap<ToolMaterial, ItemDeltaAxe>();
    public static Map<ToolMaterial, ItemDeltaHoe> hoes = new HashMap<ToolMaterial, ItemDeltaHoe>();

    public static Map<ToolMaterial, ItemDeltaArmor> helmets = new HashMap<ToolMaterial, ItemDeltaArmor>();

    static
    {
        for (ToolMaterial mat : ToolMaterial.mats)
        {
            if (mat.needsTools())
            {
                ItemDeltaShovel shovel = new ItemDeltaShovel(mat.getMod(), mat);
                MinecraftForge.setToolClass(shovel, "shovel", mat.getHarvestLevel());
                shovels.put(mat, shovel);

                ItemDeltaPickaxe pick = new ItemDeltaPickaxe(mat.getMod(), mat);
                MinecraftForge.setToolClass(pick, "pickaxe", mat.getHarvestLevel());
                pickaxes.put(mat, pick);

                ItemDeltaAxe axe = new ItemDeltaAxe(mat.getMod(), mat);
                MinecraftForge.setToolClass(axe, "axe", mat.getHarvestLevel());
                axes.put(mat, axe);

                ItemWeapon sword = new ItemWeapon("sword", mat.getMod(), mat, 4.0F);
                swords.put(mat, sword);

                ItemDeltaHoe hoe = new ItemDeltaHoe(mat.getMod(), mat);
                hoes.put(mat, hoe);
            }
            if (mat.armorInfo != null)
            {
                ItemDeltaArmor helmet = new ItemDeltaArmor(mat.getMod(), mat, 0);
                helmets.put(mat, helmet);
            }
        }
    }

    public ModDelta mod;
    public String name;
    private boolean checkUnlocalized = true;

    public ItemDelta(String name)
    {
        this(DeltaCore.instance, name);
    }

    public ItemDelta(ModDelta mod, String name)
    {
        this(mod, name, true);
    }

    public ItemDelta(ModDelta mod, String name, boolean checkLoc)
    {
        super(mod.config().getItemID(name));
        maxStackSize = 64;
        setCreativeTab(CreativeTabs.tabAllSearch);

        checkUnlocalized = checkLoc;

        // ItemDelta code
        this.mod = mod;
        this.name = name;
        String unlocalized = mod.id().toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);

        if (checkUnlocalized && !StatCollector.func_94522_b("item." + unlocalized + ".name"))
        {
            DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
        }
        if (this instanceof IExtraPasses)
        {
            ClientProxy.extraPasses.add(this);
        }
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        String s = name.replace(".", "_");

        itemIcon = doRegister(s, register);
    }

    protected Icon doRegister(String s, IconRegister register)
    {
        return doRegister(mod.id(), s, register);
    }

    public static Icon doRegister(String modid, String s, IconRegister register)
    {
        ResourceLocation loc = new ResourceLocation(modid.toLowerCase(), "textures/items/" + s + ".png");

        if (Assets.resourceExists(loc))
        {
            return register.registerIcon(modid.toLowerCase() + ":" + s);
        }
        Logger.severe("missing icon! " + loc);
        return register.registerIcon(DeltaCore.MOD_ID + ":null");
    }

    public String getid()
    {
        return mod.id();
    }
}