package mcdelta.core.block;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore.InitStage;
import mcdelta.core.DeltaMod;
import cpw.mods.fml.common.registry.GameRegistry;

public class DeltaBlocks
{
     protected DeltaMod mod;
     public static List<BlockDelta> blocksList = new ArrayList();
     
     
     
     public DeltaBlocks (DeltaMod deltaMod)
     {
          mod = deltaMod;
     }
     
     
     
     
     public void load (InitStage stage)
     {
          int itemID = 0;
     }
     
     
     
     
     protected void register (BlockDelta block)
     {
          GameRegistry.registerBlock(block, block.name);
          blocksList.add(block);
     }
     
     public int getFirstID()
     {
          return 0;
     }
}
