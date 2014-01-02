package mcdelta.core.client;

import mcdelta.core.CommonProxy;
import mcdelta.core.client.block.RenderSidedBlock;
import mcdelta.core.client.entity.RenderMovingBlock;
import mcdelta.core.entity.EntityMovingBlock;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
     
     public static int typeBlockSided;
     
     
     
     
     @Override
     public void registerRenderers ()
     {
          RenderingRegistry.registerEntityRenderingHandler(EntityMovingBlock.class, new RenderMovingBlock());
          
          typeBlockSided = RenderingRegistry.getNextAvailableRenderId();
          RenderingRegistry.registerBlockHandler(new RenderSidedBlock());
     }
     
}
