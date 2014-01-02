package mcdelta.core.proxy;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.client.block.RenderSidedBlock;
import mcdelta.core.client.item.RenderExtraPasses;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    public static List<Item> extraPasses = new ArrayList<Item>();
    public static int typeBlockSided;

    @Override
    public void registerRenderers()
    {
        typeBlockSided = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderSidedBlock());

        for (Item item : extraPasses)
        {
            MinecraftForgeClient.registerItemRenderer(item.itemID, new RenderExtraPasses());
        }
    }
}