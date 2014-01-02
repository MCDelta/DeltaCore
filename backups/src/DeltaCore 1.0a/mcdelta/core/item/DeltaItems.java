package mcdelta.core.item;

import java.util.ArrayList;
import java.util.List;

import mcdelta.core.DeltaCore.InitStage;
import mcdelta.core.DeltaMod;
import cpw.mods.fml.common.registry.GameRegistry;

public class DeltaItems
{
     protected DeltaMod mod;
     public static List<ItemDelta> itemsList = new ArrayList();
     
     
     
     
     public DeltaItems (DeltaMod deltaMod)
     {
          mod = deltaMod;
     }
     
     
     
     
     public void load (InitStage stage)
     {
          int itemID = 0;
     }
     
     
     
     
     protected void register (ItemDelta item)
     {
          GameRegistry.registerItem(item, item.name);
          itemsList.add(item.deltaID, item);
     }
     
     public int getFirstID()
     {
          return 0;
     }
}
