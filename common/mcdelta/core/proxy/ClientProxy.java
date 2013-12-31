package mcdelta.core.proxy;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.client.block.RenderSidedBlock;
import mcdelta.core.client.item.RenderExtraPasses;
import mcdelta.core.client.particle.EnumParticles;
import mcdelta.core.item.ItemDelta;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
     public static List<Item> extraPasses = new ArrayList();
     public static int        typeBlockSided;
     
     
     
     
     @Override
     public void registerRenderers ()
     {
          typeBlockSided = RenderingRegistry.getNextAvailableRenderId();
          RenderingRegistry.registerBlockHandler(new RenderSidedBlock());
          
          for (Item item : extraPasses)
          {
               MinecraftForgeClient.registerItemRenderer(item.itemID, new RenderExtraPasses());
          }
     }
}
