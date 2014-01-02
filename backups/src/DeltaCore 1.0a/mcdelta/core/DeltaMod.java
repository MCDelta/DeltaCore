package mcdelta.core;

import mcdelta.core.block.DeltaBlocks;
import mcdelta.core.config.DeltaConfig;
import mcdelta.core.entity.DeltaEntities;
import mcdelta.core.item.DeltaItems;

public interface DeltaMod
{
     String getModid ();
     
     
     
     
     DeltaItems getItems ();
     
     
     
     
     DeltaBlocks getBlocks ();
     
     
     
     
     DeltaEntities getEntities ();
     
     
     
     
     DeltaConfig getConfig ();
     
}
