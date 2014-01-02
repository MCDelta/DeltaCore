package mcdelta.core;

import java.util.Random;

import mcdelta.core.config.CoreConfig;
import mcdelta.core.logging.Logger;
import mcdelta.core.network.PacketHandler;
import mcdelta.core.proxy.CommonProxy;
import mcdelta.core.utils.handler.compatibility.CompatibilityHandler;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = DeltaCore.MOD_ID, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels =
{ DeltaCore.MOD_ID }, packetHandler = PacketHandler.class)
public class DeltaCore extends ModDelta
{
    // TODO (possible ideas)
    // - camo creeper
    // - timed potion to prevent potion effects

    public static final String MOD_ID = "deltacore";

    @Instance(MOD_ID)
    public static DeltaCore instance;

    @SidedProxy(clientSide = "mcdelta.core.proxy.ClientProxy", serverSide = "mcdelta.core.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static StringBuilder localizationWarnings = new StringBuilder();

    public static Random rand = new Random();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Logger.logger = event.getModLog();
        localizationWarnings.append("The following are missing localized names: \n");
        init(event, new CoreConfig());
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        Recipes.addCraftingRecipes();

        MinecraftForge.setBlockHarvestLevel(Block.oreNetherQuartz, "pickaxe", 2);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (localizationWarnings.length() > 45)
        {
            Logger.debug(localizationWarnings);
        }
        proxy.registerRenderers();

        CompatibilityHandler.init();
    }
}