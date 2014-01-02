package mcdelta.core.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;

import mcdelta.core.DeltaCore.InitStage;
import mcdelta.core.DeltaMod;
import mcdelta.core.block.BlockDelta;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class DeltaEntities
{
     protected DeltaMod mod;
     public static List<Class<? extends EntityDelta>> entityList = new ArrayList();
     
     
     
     
     public DeltaEntities (DeltaMod deltaMod)
     {
          mod = deltaMod;
     }
     
     
     
     
     public void load (InitStage stage)
     {
          register(EntityMovingBlock.class, "movingBlock");
     }
     
     
     
     
     protected void register (Class<? extends EntityDelta> clazz, String name)
     {
          int id = EntityRegistry.findGlobalUniqueEntityId();
          EntityRegistry.registerGlobalEntityID((Class<? extends Entity>) clazz, name, id);
          entityList.add(clazz);
     }
}
